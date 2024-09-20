package com.micro.discussions.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class JsonUtil {
    public static Map<String, Object> stringToJson(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
