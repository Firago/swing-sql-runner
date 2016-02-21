package com.dfirago.swing.sql.runner.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * Created by dmfi on 21/02/2016.
 */
public class PasswordSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String password,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        String encoded = Base64.encodeBase64String(password.getBytes());
        jsonGenerator.writeObject(encoded);
    }
}