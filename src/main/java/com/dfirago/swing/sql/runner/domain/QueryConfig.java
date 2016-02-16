package com.dfirago.swing.sql.runner.domain;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author diankasol
 */
public class QueryConfig implements Restorable {

    private LinkedBlockingQueue<String> queries;
    private int batchSize;

    public LinkedBlockingQueue<String> getQueries() {
        return queries;
    }

    public void setQueries(LinkedBlockingQueue<String> queries) {
        this.queries = queries;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

}
