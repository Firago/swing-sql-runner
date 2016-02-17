package com.dfirago.swing.sql.runner.tasks;

import com.dfirago.swing.sql.runner.domain.QueryConfig;
import com.dfirago.swing.sql.runner.services.ConfigurationService;
import com.dfirago.swing.sql.runner.services.DatabaseService;
import com.dfirago.swing.sql.runner.services.ProgressService;
import com.dfirago.swing.sql.runner.services.TaskSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

/**
 * @author diankasol
 */
@Component
@Scope("prototype")
public class QueryExecutorTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(QueryExecutorTask.class);

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private TaskSchedulerService taskSchedulerService;
    @Autowired
    private ProgressService progressService;

    @Override
    public void run() {
        QueryConfig config = (QueryConfig) configurationService.get(QueryConfig.class);
        BlockingQueue<String> queries = config.getQueries();
        Integer batchSize = config.getBatchSize();
        logger.debug("Processing queries: batch size - {}, queries left - {}", batchSize, queries.size());
        for (int i = 0; i < batchSize; i++) {
            String query = queries.poll();
            if (query != null) {
                processQuery(query);
                configurationService.save(config);
            }
        }

        if (queries.isEmpty()) {
            logger.debug("Processing finished");
            taskSchedulerService.cancel(this);
        }
    }

    private void processQuery(String query) {
        try {
            logger.debug("Processing query [{}]", query);
            databaseService.execute(query);
            logger.debug("Query executed successfully");
            progressService.handleSuccess(query);
        } catch (DataAccessException e) {
            logger.debug("Query execution failed");
            progressService.handleFailure(query);
        }
    }

}
