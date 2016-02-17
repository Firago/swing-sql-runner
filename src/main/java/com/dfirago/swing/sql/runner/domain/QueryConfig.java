package com.dfirago.swing.sql.runner.domain;

import com.dfirago.swing.sql.runner.validation.Required;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author diankasol
 */
public class QueryConfig implements Restorable {

    private LinkedBlockingQueue<String> queries;
    @Required("batch size")
    private Integer batchSize;

    public LinkedBlockingQueue<String> getQueries() {
        return queries;
    }

    public void setQueries(LinkedBlockingQueue<String> queries) {
        this.queries = queries;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

}
