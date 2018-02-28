package com.skilly.house.api.common;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author wanghongfeng
 */
public class Pagination {

    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_OFFSET = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private int queryCount;
    private int pageNum;
    private int pageSize;
    private long totalCount;
    private List<Integer> pages = Lists.newArrayList();


    public Pagination(Integer pageSize, Integer pageNum, Long totalCount, Integer queryCount) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.queryCount = queryCount;

        for (int i = 1; i <= pageNum; i++) {
            pages.add(i);
        }
        long pageCount = totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1);
        if (pageCount > pageNum) {
            for (int i = pageNum + 1; i <= pageCount; i++) {
                pages.add(i);
            }
        }

    }


    public Integer getQueryCount() {
        return queryCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    @Override
    public String toString() {
        return "Pagination [queryCount=" + queryCount + ", pageNum=" + pageNum + ", pageSize="
                + pageSize + ", totalCount=" + totalCount + ", pages=" + pages + "]";
    }


}
