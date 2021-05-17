package se.melsom.warehouse.settings;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.application.ApplicationPresentationModel;

import java.util.HashMap;
import java.util.Map;

public class WindowsBean {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationPresentationModel.class);
    Map<String, WindowBean> windows = new HashMap<>();

    @JsonAnyGetter
    public Map<String, WindowBean> getWindows() {
        return windows;
    }

    public WindowBean get(String key) {
        return windows.get(key);
    }

    @JsonAnySetter
    public void add(String key, WindowBean value) {
        logger.trace("Before adding to map: {}", value);
        windows.put(key, value);
        logger.trace("After adding to map: {}", value);
    }
}
