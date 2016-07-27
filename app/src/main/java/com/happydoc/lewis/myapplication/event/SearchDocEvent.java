package com.happydoc.lewis.myapplication.event;

import com.happydoc.lewis.myapplication.search.SearchCriteria;

/**
 * Created by Lewis on 2016/7/27.
 */
public class SearchDocEvent {
    SearchCriteria criteria;

    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }
}
