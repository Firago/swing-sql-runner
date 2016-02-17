package com.dfirago.swing.sql.runner.events;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dmfi on 17/02/2016.
 */
public abstract class TaskAbstractEvent extends ApplicationEvent {

    private final Runnable task;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param task
     */
    public TaskAbstractEvent(Object source, Runnable task) {
        super(source);
        this.task = task;
    }

    public Runnable getTask() {
        return task;
    }
}
