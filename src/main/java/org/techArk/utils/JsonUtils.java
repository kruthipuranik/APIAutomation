package org.techArk.utils;

import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;

import java.io.File;
import java.util.Objects;

@Slf4j
public class JsonUtils {
    public static void validateSchema(String actualResponse , String schemaFile) {
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(Objects.requireNonNull(JsonUtils.class.getResourceAsStream("/ExpectedJsonSchema" + File.separator + schemaFile))));
        JSONObject jsonSubject = new JSONObject(new JSONTokener(actualResponse));
        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(jsonSubject);
        } catch (Exception e) {
            log.error("Schema validation failed due to :: " + e.getMessage());
            Assert.fail("Schema validation failed due to :: " + e.getMessage());
        }
    }
}
