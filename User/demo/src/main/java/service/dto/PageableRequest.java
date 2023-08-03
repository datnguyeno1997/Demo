package service.dto;

import service.dto.enums.ESortType;

public class PageableRequest {
    private String search;

    private String sortField;

    private ESortType sortType;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public PageableRequest() {
    }

    public PageableRequest(String search, String sortField, ESortType sortType) {
        this.search = search;
        this.sortField = sortField;
        this.sortType = sortType;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public ESortType getSortType() {
        return sortType;
    }

    public void setSortType(ESortType sortType) {
        this.sortType = sortType;
    }
}