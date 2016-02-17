package com.dfirago.swing.sql.runner.services;

import com.dfirago.swing.sql.runner.domain.Progress;
import com.dfirago.swing.sql.runner.domain.QueryConfig;
import com.dfirago.swing.sql.runner.domain.Restorable;
import com.dfirago.swing.sql.runner.domain.TaskConfig;
import com.dfirago.swing.sql.runner.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author diankasol
 */
@Service
public class ConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

    @Autowired
    private DatabaseService databaseService;

    private ObjectMapper mapper;

    private Map<Class<? extends Restorable>, Restorable> cache;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        cache = new HashMap<Class<? extends Restorable>, Restorable>();
        for (Class<? extends Restorable> clazz : CommonUtils.getRestorableClasses()) {
            Restorable config = get(clazz);
            if (config != null) {
                cache.put(clazz, config);
            }
        }
    }

    public void clear() {
        databaseService.resetCredentials();
        clear(QueryConfig.class);
        clear(TaskConfig.class);
        clear(Progress.class);
    }

    public void clear(Class<? extends Restorable> clazz) {
        cache.remove(clazz);
        String fileName = getFilePath(clazz);
        File file = new File(fileName);
        file.delete();
    }

    public boolean hasConfig() {
        return cache.get(QueryConfig.class) != null
                && cache.get(TaskConfig.class) != null
                && cache.get(Progress.class) != null;
    }

    public void save(Restorable... objects) {
        for (Restorable config : objects) {
            save(config);
        }
    }

    public void save(Restorable object) {
        try {
            Class clazz = object.getClass();
            cache.put(clazz, object);
            String fileName = getFilePath(clazz);
            FileOutputStream fos = new FileOutputStream(fileName);
            mapper.writeValue(fos, object);
        } catch (IOException e) {
            logger.error("Failed to save configuration object", e);
        }
    }

    public Restorable get(Class<? extends Restorable> clazz) {
        Object object = cache.get(clazz);
        if (object == null) {
            object = load(clazz);
        }
        return (Restorable) object;
    }

    private Object load(Class<? extends Restorable> clazz) {
        Object object = null;
        try {
            String fileName = getFilePath(clazz);
            FileInputStream fis = new FileInputStream(fileName);
            object = mapper.readValue(fis, clazz);
        } catch (FileNotFoundException e) {
            logger.warn("Restorable file of type {} does not exist", clazz.getSimpleName());
        } catch (IOException e) {
            logger.error("Failed to load restorable file");
        }
        return object;
    }

    private String getFilePath(Class<? extends Restorable> clazz) {
        return String.format("src/main/resources/restorable/%s.json", clazz.getSimpleName());
    }

}
