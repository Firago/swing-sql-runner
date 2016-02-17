package com.dfirago.swing.sql.runner.services;

import com.dfirago.swing.sql.runner.domain.ConnectionConfig;
import com.dfirago.swing.sql.runner.domain.TaskConfig;
import com.dfirago.swing.sql.runner.events.TaskFinishedEvent;
import com.dfirago.swing.sql.runner.events.TaskPausedEvent;
import com.dfirago.swing.sql.runner.events.TaskStartedEvent;
import com.dfirago.swing.sql.runner.tasks.QueryExecutorTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author diankasol
 */
@Service
public class TaskSchedulerService implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private QueryExecutorTask task;

    private Map<Runnable, ScheduledFuture> pendingTasks;

    @PostConstruct
    public void init() {
        pendingTasks = new HashMap<Runnable, ScheduledFuture>();
    }

    public void run() {
        databaseService.register(
                (ConnectionConfig) configurationService.get(ConnectionConfig.class));
        TaskConfig taskConfig = (TaskConfig) configurationService.get(TaskConfig.class);
        ScheduledFuture<?> pendingTask = taskScheduler
                .scheduleWithFixedDelay(task, taskConfig.getInterval());
        pendingTasks.put(task, pendingTask);
        eventPublisher.publishEvent(new TaskStartedEvent(this, task));
    }

    public void cancel(Runnable task) {
        ScheduledFuture pending = pendingTasks.get(task);
        pending.cancel(true);
        eventPublisher.publishEvent(new TaskFinishedEvent(this, task));
    }

    public void pause(Runnable task) {
        ScheduledFuture pending = pendingTasks.get(task);
        pending.cancel(true);
        eventPublisher.publishEvent(new TaskPausedEvent(this, task));
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        ((ThreadPoolTaskScheduler) taskScheduler).shutdown();
    }
}
