package com.dfirago.swing.sql.runner.validation;

import com.dfirago.swing.sql.runner.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfi on 17/02/2016.
 */
public class ValidationResult {

    private static final String SEPARATOR = ", ";

    private final List<String> invalidFields = new ArrayList<String>();

    public void add(String field) {
        invalidFields.add(field);
    }

    public boolean isFailed() {
        return !invalidFields.isEmpty();
    }

    public String getErrorMessage() {
        String fields = CommonUtils.convertCollectionToString(invalidFields, SEPARATOR, "");
        return String.format("Missing required fields: %s", fields);
    }
}
