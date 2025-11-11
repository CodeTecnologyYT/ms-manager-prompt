package com.kaust.ms.manager.prompt.shared.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    /* SENSITIVE_KEYS. */
    // Name parameters sensitive that must be hidden
    private static final Set<String> SENSITIVE_KEYS = new HashSet<>(Arrays.asList("token", "authorization", "password"));

    /**
     * Aspect for logging.
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return {@link Object}
     */
    @Around("execution(public * com.kaust.ms.manager.prompt..application..*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        final var className = joinPoint.getTarget().getClass().getSimpleName();
        final var methodName = joinPoint.getSignature().getName();
        final var methodArgs = sanitizeArgs(joinPoint.getArgs());

        log.info("[{}][{}][BDFC] request: {}", className, methodName, methodArgs);

        final var startTime = System.currentTimeMillis();

        try {
            final var result = joinPoint.proceed();

            if (result instanceof Mono) {
                return ((Mono<?>) result)
                        .doOnSuccess(res -> logSuccess(className, methodName, startTime, res))
                        .doOnError(e -> logError(className, methodName, e));
            } else if (result instanceof Flux) {
                return ((Flux<?>) result)
                        .doOnNext(res -> logSuccess(className, methodName, startTime, res))
                        .doOnError(e -> logError(className, methodName, e));
            } else {
                logSuccess(className, methodName, startTime, result);
                return result;
            }
        } catch (Exception e) {
            log.error("[{}][{}][KAUST_FINEX] Exception: {}", className, methodName, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Log success.
     *
     * @param className {@link String}
     * @param methodName {@link String}
     * @param result {@link Object}
     */
    private void logSuccess(String className, String methodName, long startTime, Object result) {
        final var timeTaken = System.currentTimeMillis() - startTime;
        final var sanitizedResult = sanitizeObject(result);
        log.info("[{}][{}][KAUST_FINOK] timeTaken: [{} ms], response: {}", className, methodName, timeTaken, sanitizedResult);
    }

    /**
     * Log error.
     *
     * @param className {@link String}
     * @param methodName {@link String}
     * @param e {@link Throwable}
     */
    private void logError(String className, String methodName, Throwable e) {
        log.error("[{}][{}][KAUST_FINEX] Exception: {}", className, methodName, e.getMessage(), e);
    }

    /**
     * Sanitization for parameters
     *
     * @param args {@link Object[]}
     * @return {@link String}
     */
    private String sanitizeArgs(Object[] args) {
        if (args == null) {
            return "[]";
        }
        return Arrays.stream(args)
                .map(this::sanitizeObject)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Sanitization for objects
     *
     * @param arg {@link Object}
     * @return {@link String}
     */
    private String sanitizeObject(Object arg) {
        if (arg == null) {
            return "null";
        }

        // Verify if it's a number, boolean or character'
        if (arg instanceof Number ||
                arg instanceof Boolean ||
                arg instanceof Character) {
            return arg.toString();
        }

        // Verify if it's a string'
        if (arg instanceof String) {
            return sanitizeString((String) arg);
        }

        // Verify if it's a list or set'
        if (arg instanceof Iterable<?>) {
            return sanitizeIterable((Iterable<?>) arg);
        }

        // Verify if it's a map'
        if (arg instanceof Map<?,?>) {
            return sanitizeMap((Map<?, ?>) arg);
        }

        // Verify if it's a Mono'
        if (arg instanceof Mono) {
            return "Mono<" + arg.getClass().getSimpleName() + ">";
        }

        // Verify if it's a Flux'
        if (arg instanceof Flux) {
            return "Flux<" + arg.getClass().getSimpleName() + ">";
        }

        // If it not cases above, it's a custom object'
        return sanitizeCustomObject(arg);
    }

    /**
     * Sanitization for lists and sets
     *
     * @param iterable {@link Iterable}
     * @return {@link String}
     */
    private String sanitizeIterable(Iterable<?> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(this::sanitizeObject)  // Satisfy each element of the list
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Sanitization for maps
     *
     * @param map {@link Map}
     * @return {@link String}
     */
    private String sanitizeMap(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(entry -> sanitizeObject(entry.getKey()) + "=" + sanitizeObject(entry.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    /**
     * Sanitization for strings
     *
     * @param argStr {@link String}
     * @return {@link String}
     */
    private String sanitizeString(String argStr) {
        for (String sensitiveKey : SENSITIVE_KEYS) {
            if (argStr.toLowerCase().contains(sensitiveKey)) {
                return sensitiveKey + "=*******";
            }
        }
        return argStr;  // If no sensitive key is found, return the string as it is
    }

    /**
     * Sanitization for custom objects
     *
     * @param arg {@link Object}
     * @return {@link String}
     */
    private String sanitizeCustomObject(Object arg) {
        try {
            final var fields = arg.getClass().getDeclaredFields();
            final var sanitizedObject = new StringBuilder(arg.getClass().getSimpleName() + "{");

            for (final var field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue; // Omit irrelevant fields
                }

                field.setAccessible(true);  // Allow access to private fields
                final var value = field.get(arg);
                final var fieldName = field.getName();

                if (SENSITIVE_KEYS.contains(fieldName.toLowerCase())) {
                    sanitizedObject.append(fieldName).append("=*******");
                } else {
                    sanitizedObject.append(fieldName).append("=").append(value);
                }
                sanitizedObject.append(", ");
            }
            // Delete spacing finish
            if (sanitizedObject.length() > 2) {
                sanitizedObject.setLength(sanitizedObject.length() - 2);
            }
            sanitizedObject.append("}");
            return sanitizedObject.toString();
        } catch (IllegalAccessException e) {
            log.error("Error filter objects: {}", e.getMessage(), e);
            return arg.toString();  // In case of error, return the object as a string
        }
    }

}
