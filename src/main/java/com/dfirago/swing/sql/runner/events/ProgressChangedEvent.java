package com.dfirago.swing.sql.runner.events;

import com.dfirago.swing.sql.runner.domain.Progress;
import org.springframework.context.ApplicationEvent;

/**
 * Created by dmfi on 17/02/2016.
 */
public class ProgressChangedEvent extends ApplicationEvent {

    private Progress progress;
    private String lastQuery;
    private boolean failed;

    public ProgressChangedEvent(Object source, Progress progress, String lastQuery, boolean failed) {
        super(source);
        this.progress = progress;
        this.lastQuery = lastQuery;
        this.failed = failed;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public String getLastQuery() {
        return lastQuery;
    }

    public void setLastQuery(String lastQuery) {
        this.lastQuery = lastQuery;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
