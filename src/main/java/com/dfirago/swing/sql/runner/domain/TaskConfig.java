package com.dfirago.swing.sql.runner.domain;

import com.dfirago.swing.sql.runner.validation.Required;

/**
 *
 * @author diankasol
 */
public class TaskConfig implements Restorable {

    @Required("interval")
    private Long interval;

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

}
