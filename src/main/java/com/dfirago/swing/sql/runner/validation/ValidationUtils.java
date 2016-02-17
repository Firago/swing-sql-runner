package com.dfirago.swing.sql.runner.validation;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by dmfi on 17/02/2016.
 */
public class ValidationUtils {

    public static void validate(ValidationResult validationResult, Object... objects) {
        for (Object object : objects) {
            validate(validationResult, object);
        }
    }

    public static void validate(ValidationResult validationResult, Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Annotation annotation = field
                    .getAnnotation(Required.class);
            if (annotation != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value == null || value.toString().trim().isEmpty()) {
                        String alias = (String) AnnotationUtils.getValue(annotation);
                        validationResult.add(alias);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
