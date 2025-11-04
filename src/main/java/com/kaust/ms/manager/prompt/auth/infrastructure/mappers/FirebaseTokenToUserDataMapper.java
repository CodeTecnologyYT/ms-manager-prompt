package com.kaust.ms.manager.prompt.auth.infrastructure.mappers;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.kaust.ms.manager.prompt.auth.domain.models.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FirebaseTokenToUserDataMapper {

    @Mapping(target = "roles", expression = "java(getDefaultRoles())")
    UserData transformFirebaseTokenToUserData(FirebaseToken userRecord);

    /**
     * Get default roles.
     *
     * @return lista {@link String}
     */
    default List<String> getDefaultRoles() {
        return List.of("ROLE_USER");
    }

}
