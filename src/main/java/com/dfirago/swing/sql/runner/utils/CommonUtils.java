package com.dfirago.swing.sql.runner.utils;

import com.dfirago.swing.sql.runner.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

/**
 * @author diankasol
 */
public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static final Pattern QUERIES_PATTERN = Pattern.compile(";\r?\n?(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

    public static String loadFile(File file) {
        String content = "";
        try {
            Reader reader = new FileReader(file);
            content = FileCopyUtils.copyToString(reader);
        } catch (FileNotFoundException e) {
            logger.error("Failed to load selected file", e);
        } catch (IOException e) {
            logger.error("I/O error occured while reading selected file", e);
        }
        return content;
    }

    public static LinkedBlockingQueue<String> parseQueries(String content) {
        LinkedBlockingQueue<String> queries = new LinkedBlockingQueue<String>();
        String[] queriesArray = QUERIES_PATTERN.split(content);
        queries.addAll(Arrays.asList(queriesArray));
        return queries;
    }

    public static void configureSwing() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Unable to use system look and feel theme, using default instead", e);
        }
    }

    public static List<Class<? extends Restorable>> getRestorableClasses() {
        return new ArrayList<Class<? extends Restorable>>() {
            {
                add(ConnectionConfig.class);
                add(QueryConfig.class);
                add(TaskConfig.class);
                add(Progress.class);
            }
        };
    }

    public static String convertCollectionToString(Collection<String> list, String separator, String ending) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next()).append(ending);
            if (iter.hasNext()) {
                builder.append(separator);
            }
        }
        return builder.toString();
    }
}
