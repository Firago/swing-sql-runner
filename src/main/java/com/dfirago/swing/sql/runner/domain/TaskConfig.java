package com.dfirago.swing.sql.runner.domain;

/**
 *
 * @author diankasol
 */
public class TaskConfig implements Restorable {

    private long interval;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

}
