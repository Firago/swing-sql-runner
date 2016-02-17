package com.dfirago.swing.sql.runner.utils;

import com.dfirago.swing.sql.runner.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dmfi on 17/02/2016.
 */
public class CommonUtils {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static List<Class<? extends Restorable>> getRestorableClasses() {
        return new ArrayList<Class<? extends Restorable>>() {
            {
                add(ConncetionConfig.class);
                add(QueryConfig.class);
                add(TaskConfig.class);
                add(Progress.class);
            }
        };
    }

    public static void configureSwing() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Unable to use system look and feel theme, using default instead", e);
        }
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
