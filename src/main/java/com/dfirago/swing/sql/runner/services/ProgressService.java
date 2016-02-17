package com.dfirago.swing.sql.runner.services;

import com.dfirago.swing.sql.runner.domain.Progress;
import com.dfirago.swing.sql.runner.events.ProgressChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Created by diankasol on 17/02/2016.
 */
@Service
public class ProgressService {

    private static final Logger logger = LoggerFactory.getLogger(ProgressService.class);

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void handleSuccess(String query) {
        Progress progress = (Progress) configurationService.get(Progress.class);
        progress.incrementFinishedCount();
        configurationService.save(progress);
        updateProgress(progress, query, false);
    }

    public void handleFailure(String query) {
        Progress progress = (Progress) configurationService.get(Progress.class);
        progress.incrementFinishedCount();
        progress.addFailedQuery(query);
        configurationService.save(progress);
        updateProgress(progress, query, true);
    }

    private void updateProgress(Progress progress, String lastQuery, boolean failed) {
        logger.info("Current progress is {}%", progress.calculateRate());
        eventPublisher.publishEvent(new ProgressChangedEvent(this, progress, lastQuery, failed));
    }
}
