package com.dfirago.swing.sql.runner.exceptions;

/**
 * Created by Дмитрий on 17.02.2016.
 */
public class DataSourceUndefinedException extends RuntimeException {

    public DataSourceUndefinedException() {
    }

    public DataSourceUndefinedException(String message) {
        super(message);
    }
}
