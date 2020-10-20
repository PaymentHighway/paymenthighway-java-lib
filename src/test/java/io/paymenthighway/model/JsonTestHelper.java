package io.paymenthighway.model;

import static org.junit.Assert.assertTrue;

public class JsonTestHelper {

    public static void testJson(String json, String key, String value) {
        assertTrue(json.contains(String.format("\"%s\":\"%s\"", key, value)));
    }

    public static void testJson(String json, String key, Long value) {
        assertTrue(json.contains(String.format("\"%s\":%s", key, value)));
    }

    public static void keyExists(String json, String key) {
      assertTrue(json.contains(String.format("\"%s\":", key)));
    }
}
