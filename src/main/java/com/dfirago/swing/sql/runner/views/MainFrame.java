package com.dfirago.swing.sql.runner.views;

import com.dfirago.swing.sql.runner.domain.ConnectionConfig;
import com.dfirago.swing.sql.runner.domain.Progress;
import com.dfirago.swing.sql.runner.domain.QueryConfig;
import com.dfirago.swing.sql.runner.domain.TaskConfig;
import com.dfirago.swing.sql.runner.events.ProgressChangedEvent;
import com.dfirago.swing.sql.runner.events.TaskFinishedEvent;
import com.dfirago.swing.sql.runner.events.TaskPausedEvent;
import com.dfirago.swing.sql.runner.events.TaskStartedEvent;
import com.dfirago.swing.sql.runner.services.ConfigurationService;
import com.dfirago.swing.sql.runner.services.DatabaseService;
import com.dfirago.swing.sql.runner.services.TaskSchedulerService;
import com.dfirago.swing.sql.runner.utils.CommonUtils;
import com.dfirago.swing.sql.runner.validation.ValidationResult;
import com.dfirago.swing.sql.runner.validation.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * @author diankasol
 */
@Lazy
@Component
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 3896182280800127652L;

    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

    private LinkedBlockingQueue<String> queries;

    private Runnable task;

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private TaskSchedulerService taskSchedulerService;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel batchSizeLabel;
    private javax.swing.JSpinner batchSizeSpinner;
    private javax.swing.JButton chooseFileButton;
    private javax.swing.JPanel configurationPanel;
    private javax.swing.JPanel connectionPanel;
    private javax.swing.JComboBox driverComboBox;
    private javax.swing.JLabel driverLabel;
    private javax.swing.JPanel editQueriesPanel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JTextField filePathField;
    private javax.swing.JLabel filePathLabel;
    private javax.swing.JLabel intervalLabel;
    private javax.swing.JSpinner intervalSpinner;
    private javax.swing.JPanel loadQueriesPanel;
    private javax.swing.JTextField loginField;
    private javax.swing.JProgressBar mainProgressBar;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JEditorPane queriesEditorPane;
    private javax.swing.JScrollPane queriesScrollPane;
    private javax.swing.JButton runButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton testConnectionButton;
    private javax.swing.JTextField urlField;
    private javax.swing.JLabel urlLabel;
    private javax.swing.JLabel usernameLabel;

    @PostConstruct
    public void init() {
        initComponents();
        initEditor();
        restore();
        setRunning(false);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            resumeOrClear();
        }
    }

    private void resumeOrClear() {
        if (configurationService.hasConfig()) {
            int result = JOptionPane.showConfirmDialog(this, "Would you like to restore previous task?", "", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                restoreAll();
            } else {
                configurationService.clear();
            }
        }
    }

    private void initEditor() {
        queriesEditorPane.setContentType("text/sql");
    }

    private void restore() {
        ConnectionConfig dbConfig = (ConnectionConfig) configurationService.get(ConnectionConfig.class);
        if (dbConfig != null) {
            urlField.setText(dbConfig.getUrl());
            driverComboBox.setSelectedItem(dbConfig.getDriver());
        }
    }

    private void restoreAll() {
        ConnectionConfig dbConfig = (ConnectionConfig) configurationService.get(ConnectionConfig.class);
        TaskConfig taskConfig = (TaskConfig) configurationService.get(TaskConfig.class);
        QueryConfig queryConfig = (QueryConfig) configurationService.get(QueryConfig.class);
        Progress progress = (Progress) configurationService.get(Progress.class);

        if (dbConfig != null) {
            urlField.setText(dbConfig.getUrl());
            loginField.setText(dbConfig.getLogin());
            driverComboBox.setSelectedItem(dbConfig.getDriver());
        }

        if (taskConfig != null) {
            intervalSpinner.setValue(taskConfig.getInterval() / 1000);
        }

        if (queryConfig != null) {
            batchSizeSpinner.setValue(queryConfig.getBatchSize());
            loadQueriesFromConfig();
        }

        if (progress != null) {
            mainProgressBar.setValue(progress.calculateRate());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        connectionPanel = new javax.swing.JPanel();
        urlField = new javax.swing.JTextField();
        loginField = new javax.swing.JTextField();
        testConnectionButton = new javax.swing.JButton();
        urlLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        driverComboBox = new javax.swing.JComboBox();
        driverLabel = new javax.swing.JLabel();
        configurationPanel = new javax.swing.JPanel();
        intervalLabel = new javax.swing.JLabel();
        intervalSpinner = new javax.swing.JSpinner();
        batchSizeLabel = new javax.swing.JLabel();
        batchSizeSpinner = new javax.swing.JSpinner();
        loadQueriesPanel = new javax.swing.JPanel();
        filePathField = new javax.swing.JTextField();
        filePathLabel = new javax.swing.JLabel();
        chooseFileButton = new javax.swing.JButton();
        progressPanel = new javax.swing.JPanel();
        mainProgressBar = new javax.swing.JProgressBar();
        runButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        editQueriesPanel = new javax.swing.JPanel();
        queriesScrollPane = new javax.swing.JScrollPane();
        queriesEditorPane = new javax.swing.JEditorPane();

        fileChooser.setCurrentDirectory(new java.io.File("C:\\"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        connectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));

        testConnectionButton.setText("Test connection");
        testConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testConnectionButtonActionPerformed(evt);
            }
        });

        urlLabel.setText("URL");

        usernameLabel.setText("Username");

        passwordLabel.setText("Password");

        driverComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"com.mysql.jdbc.Driver", "org.postgresql.Driver"}));

        driverLabel.setText("Driver");

        javax.swing.GroupLayout connectionPanelLayout = new javax.swing.GroupLayout(connectionPanel);
        connectionPanel.setLayout(connectionPanelLayout);
        connectionPanelLayout.setHorizontalGroup(
                connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(connectionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(urlLabel)
                                        .addComponent(usernameLabel)
                                        .addComponent(passwordLabel)
                                        .addComponent(driverLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, connectionPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(driverComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(testConnectionButton))
                                        .addComponent(passwordField)
                                        .addComponent(loginField)
                                        .addComponent(urlField))
                                .addContainerGap())
        );
        connectionPanelLayout.setVerticalGroup(
                connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, connectionPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(urlLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(driverComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(driverLabel)
                                        .addComponent(testConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        testConnectionButton.getAccessibleContext().setAccessibleName("testConnectionButton");

        configurationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));

        intervalLabel.setText("Interval [s]");

        intervalSpinner.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(1L), Long.valueOf(1L), null, Long.valueOf(1L)));

        batchSizeLabel.setText("Batch size");

        batchSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        javax.swing.GroupLayout configurationPanelLayout = new javax.swing.GroupLayout(configurationPanel);
        configurationPanel.setLayout(configurationPanelLayout);
        configurationPanelLayout.setHorizontalGroup(
                configurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(configurationPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(intervalLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intervalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(batchSizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(batchSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        configurationPanelLayout.setVerticalGroup(
                configurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(configurationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(configurationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(intervalLabel)
                                        .addComponent(intervalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(batchSizeLabel)
                                        .addComponent(batchSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        loadQueriesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Load queries"));

        filePathField.setEditable(false);

        filePathLabel.setText("File path");

        chooseFileButton.setText("Choose...");
        chooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loadQueriesPanelLayout = new javax.swing.GroupLayout(loadQueriesPanel);
        loadQueriesPanel.setLayout(loadQueriesPanelLayout);
        loadQueriesPanelLayout.setHorizontalGroup(
                loadQueriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(loadQueriesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(filePathLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filePathField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseFileButton)
                                .addContainerGap())
        );
        loadQueriesPanelLayout.setVerticalGroup(
                loadQueriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(loadQueriesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(loadQueriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(filePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(filePathLabel)
                                        .addComponent(chooseFileButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Progress"));

        mainProgressBar.setStringPainted(true);

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout progressPanelLayout = new javax.swing.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
                progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(progressPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mainProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        progressPanelLayout.setVerticalGroup(
                progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(progressPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(progressPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mainProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(stopButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editQueriesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit queries"));

        queriesScrollPane.setViewportView(queriesEditorPane);

        javax.swing.GroupLayout editQueriesPanelLayout = new javax.swing.GroupLayout(editQueriesPanel);
        editQueriesPanel.setLayout(editQueriesPanelLayout);
        editQueriesPanelLayout.setHorizontalGroup(
                editQueriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(queriesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );
        editQueriesPanelLayout.setVerticalGroup(
                editQueriesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(queriesScrollPane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(loadQueriesPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(configurationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(progressPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(connectionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editQueriesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(editQueriesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(connectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(configurationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(loadQueriesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(progressPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testConnectionButtonActionPerformed
        testConnection();
    }//GEN-LAST:event_testConnectionButtonActionPerformed

    private void testConnection() {
        ConnectionConfig dbConfig = getConnectionConfig();
        if (validate(dbConfig)) {
            boolean success = databaseService.testConnection(dbConfig);
            if (success) {
                showInfo("Test connection", "Succeeded");
            } else {
                showError("Test connection", "Failed");
            }
        }
    }

    private void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showWarn(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private void chooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseFileButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            filePathField.setText(file.getPath());
            queriesEditorPane.setText(CommonUtils.loadFile(file));
        }
    }//GEN-LAST:event_chooseFileButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        if (configurationService.hasConfig()) {
            resume();
        } else {
            start();
        }
    }//GEN-LAST:event_runButtonActionPerformed

    private void start() {
        queries = CommonUtils.parseQueries(queriesEditorPane.getText());
        ConnectionConfig dbConfig = getConnectionConfig();
        QueryConfig sqlConfig = getSqlBatchConfig();
        TaskConfig taskConfig = getTaskConfig();
        Progress progress = getProgress();
        run(dbConfig, sqlConfig, progress, taskConfig);
    }

    private void resume() {
        ConnectionConfig dbConfig = getConnectionConfig();
        queries = CommonUtils.parseQueries(queriesEditorPane.getText());
        QueryConfig sqlConfig = (QueryConfig) configurationService.get(QueryConfig.class);
        sqlConfig.setQueries(queries);
        Progress progress = (Progress) configurationService.get(Progress.class);
        progress.setTotalCount(queries.size() + progress.getFinishedCount());
        TaskConfig taskConfig = getTaskConfig();
        run(dbConfig, sqlConfig, progress, taskConfig);
    }

    private void run(ConnectionConfig dbConfig, QueryConfig sqlConfig, Progress progress, TaskConfig taskConfig) {
        if (validate(dbConfig, sqlConfig, taskConfig)) {
            configurationService.save(dbConfig, sqlConfig, taskConfig, progress);
            taskSchedulerService.run();
        }
    }

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        taskSchedulerService.pause(task);
    }//GEN-LAST:event_stopButtonActionPerformed

    private Progress getProgress() {
        Progress progress = new Progress();
        progress.setTotalCount(queries.size());
        return progress;
    }

    private TaskConfig getTaskConfig() {
        TaskConfig taskConfig = new TaskConfig();
        taskConfig.setInterval((Long) intervalSpinner.getValue() * 1000);
        return taskConfig;
    }

    private QueryConfig getSqlBatchConfig() {
        QueryConfig queryConfig = new QueryConfig();
        queryConfig.setQueries(queries);
        queryConfig.setBatchSize((Integer) batchSizeSpinner.getValue());
        return queryConfig;
    }

    private ConnectionConfig getConnectionConfig() {
        ConnectionConfig dbConfig = new ConnectionConfig();
        dbConfig.setDriver((String) driverComboBox.getSelectedItem());
        dbConfig.setUrl(urlField.getText());
        dbConfig.setLogin(loginField.getText());
        dbConfig.setPassword(new String(passwordField.getPassword()));
        return dbConfig;
    }

    private boolean validate(Object... objects) {
        ValidationResult validationResult = new ValidationResult();
        ValidationUtils.validate(validationResult, objects);
        if (validationResult.isFailed()) {
            showError("Validation failed", validationResult.getErrorMessage());
        }
        return !validationResult.isFailed();
    }

    private void updateEditorPane(ProgressChangedEvent event) {
        String lastQuery = event.getLastQuery();
        String oldQueries = queriesEditorPane.getText();
        String regex = Pattern.quote(lastQuery) + ";\\s*";
        String newQueries = oldQueries.replaceFirst(regex, "");
        queriesEditorPane.setText(newQueries);
    }

    @EventListener
    public void handleProgressChangedEvent(final ProgressChangedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int progress = event.getProgress().calculateRate();
                mainProgressBar.setValue(progress);
                updateEditorPane(event);
            }
        });
    }

    @EventListener
    public void handleTaskStartedEvent(final TaskStartedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                task = event.getTask();
                if (queriesEditorPane.getText().isEmpty()) {
                    loadQueriesFromConfig();
                }
                disablePanels(connectionPanel, configurationPanel, loadQueriesPanel, editQueriesPanel);
                queriesEditorPane.setEditable(false);
                setRunning(true);
            }
        });
    }

    @EventListener
    public void handleTaskPausedEvent(final TaskPausedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                enablePanels(connectionPanel, configurationPanel, loadQueriesPanel, editQueriesPanel);
                queriesEditorPane.setEditable(true);
                setRunning(false);
            }
        });
    }

    @EventListener
    public void handleTaskFinishedEvent(final TaskFinishedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Progress progress = (Progress) configurationService.get(Progress.class);
                queriesEditorPane.setText(progress.getFailedQueriesFormatted());
                showFinishedMessage(progress);
                configurationService.clear();
                enablePanels(connectionPanel, configurationPanel, loadQueriesPanel, editQueriesPanel);
                queriesEditorPane.setEditable(true);
                setRunning(false);
            }
        });
    }

    private void showFinishedMessage(Progress progress) {
        if (progress.getFailedQueries().size() == 0) {
            showInfo("Process finished", "All queries executed successfully");
        } else {
            showWarn("Process finished", "Process finished with errors. Check failed queries in the editor");
        }
    }

    private void disablePanels(JPanel... panels) {
        for (JPanel panel : panels) {
            disablePanel(panel);
        }
    }

    private void disablePanel(JPanel panel) {
        for (java.awt.Component component : panel.getComponents()) {
            component.setEnabled(false);
        }
    }

    private void enablePanels(JPanel... panels) {
        for (JPanel panel : panels) {
            enablePanel(panel);
        }
    }

    private void enablePanel(JPanel panel) {
        for (java.awt.Component component : panel.getComponents()) {
            component.setEnabled(true);
        }
    }

    private void loadQueriesFromConfig() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QueryConfig sqlBatchConfig = ((QueryConfig) configurationService.get(QueryConfig.class));
                String queries = CommonUtils.convertCollectionToString(sqlBatchConfig.getQueries(), "\r\n", ";");
                queriesEditorPane.setText(queries);
            }
        });
    }

    public void setRunning(boolean running) {
        if (running) {
            runButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else {
            runButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    // End of variables declaration//GEN-END:variables
}
