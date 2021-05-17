package se.melsom.warehouse.settings;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class PropertiesBean {
    Map<String, String> properties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public String get(String key) {
        return properties.get(key);
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }
}
