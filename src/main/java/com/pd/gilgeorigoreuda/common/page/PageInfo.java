package com.pd.gilgeorigoreuda.common.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@Getter
public class PageInfo {

    private int currentPage;
    private int totalPages;
    private int pageSize;
    private long totalSize;

    public PageInfo(final int currentPage, final int totalPages, final int pageSize, final long totalSize) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    public static PageInfo of(Page<?> data) {
        int currentPage = data.getPageable().getPageNumber() + 1;
        int totalPages = data.getTotalPages();
        int pageSize = data.getPageable().getPageSize();
        long totalSize = data.getTotalElements();

        return new PageInfo(currentPage, totalPages, pageSize, totalSize);
    }
}
