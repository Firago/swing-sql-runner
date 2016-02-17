package com.dfirago.swing.sql.runner.events;

/**
 * Created by dmfi on 17/02/2016.
 */
public class TaskPausedEvent extends TaskAbstractEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param task
     */
    public TaskPausedEvent(Object source, Runnable task) {
        super(source, task);
    }
}
