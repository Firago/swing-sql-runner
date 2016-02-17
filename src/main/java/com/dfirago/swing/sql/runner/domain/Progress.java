package com.dfirago.swing.sql.runner.domain;

import com.dfirago.swing.sql.runner.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankasol on 11/02/2016.
 */
public class Progress implements Restorable {

    private static final long serialVersionUID = -2388588102969191079L;

    private Integer totalCount = 0;
    private Integer finishedCount = 0;
    private List<String> failedQueries = new ArrayList<String>();

    public Progress() {

    }

    public Progress(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(Integer finishedCount) {
        this.finishedCount = finishedCount;
    }

    public List<String> getFailedQueries() {
        return failedQueries;
    }

    public void setFailedQueries(List<String> failedQueries) {
        this.failedQueries = failedQueries;
    }

    public void incrementFinishedCount() {
        this.finishedCount++;
    }

    public void addFailedQuery(String query) {
        this.failedQueries.add(query);
    }

    public int calculateRate() {
        return (int) (finishedCount * 100f / totalCount);
    }

    @JsonIgnore
    public String getFailedQueriesFormatted() {
        return CommonUtils.convertCollectionToString(failedQueries, "\r\n", ";");
    }
}
