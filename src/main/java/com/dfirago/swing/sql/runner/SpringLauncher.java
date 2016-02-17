package com.dfirago.swing.sql.runner;

import com.dfirago.swing.sql.runner.config.AppConfig;
import com.dfirago.swing.sql.runner.utils.CommonUtils;
import com.dfirago.swing.sql.runner.views.MainFrame;
import jsyntaxpane.DefaultSyntaxKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import javax.swing.*;

/**
 * Created by dmfi on 17/02/2016.
 */
public class SpringLauncher {

    private static final Logger logger = LoggerFactory.getLogger(SpringLauncher.class);

    public static void main(String... args) {
        logger.info("Loading application context...");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();
        CommonUtils.configureSwing();
        DefaultSyntaxKit.initKit();
        JFrame frame = context.getBean(MainFrame.class);
        frame.setVisible(true);
    }


}
