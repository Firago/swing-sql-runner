package com.dfirago.swing.sql.runner.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * Created by dmfi on 21/02/2016.
 */
public class PasswordDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser,
                              DeserializationContext context)
            throws IOException {
        String encoded = jsonParser.getText();
        byte[] password = Base64.decodeBase64(encoded);
        return new String(password);
    }
}