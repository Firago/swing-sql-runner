package com.dfirago.swing.sql.runner.services;

import com.dfirago.swing.sql.runner.domain.ConncetionConfig;
import com.dfirago.swing.sql.runner.exceptions.DataSourceUndefinedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author diankasol
 */
@Service
public class DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Autowired
    private ConfigurationService configurationService;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void register(ConncetionConfig config) {
        register(createDataSource(config));
    }

    public void register(DataSource dataSorce) {
        this.dataSource = dataSorce;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean testConnection(String driver, String url, String login, String password) {
        return testConnection(new ConncetionConfig(driver, url, login, password));
    }

    public boolean testConnection(ConncetionConfig config) {
        return testConnection(createDataSource(config));
    }

    public boolean testConnection(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.warn("Database access error occurs", e);
        }
        return connection != null;
    }

    public void execute(String query) {
        if (jdbcTemplate == null) {
            throw new DataSourceUndefinedException();
        }
        jdbcTemplate.execute(query);
    }


    private DataSource createDataSource(ConncetionConfig config) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(config.getDriver());
        ds.setUrl(config.getUrl());
        ds.setUsername(config.getLogin());
        ds.setPassword(config.getPassword());
        return ds;
    }

    public void resetCredentials() {
        ConncetionConfig dbConfig = ((ConncetionConfig) configurationService
                .get(ConncetionConfig.class));
        dbConfig.setLogin(null);
        dbConfig.setPassword(null);
        configurationService.save(dbConfig);
    }

}
