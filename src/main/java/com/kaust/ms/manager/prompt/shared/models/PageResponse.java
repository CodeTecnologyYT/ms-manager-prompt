package com.kaust.ms.manager.prompt.shared.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    /** content. */
    private List<T> content;
    /** page. */
    private int page;
    /** size. */
    private int size;
    /** total. */
    private long total;
    /** totalPages. */
    private int totalPages;

    /**
     * Constructor.
     */
    public PageResponse(List<T> content, int page, int size, long total) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
    }

}
