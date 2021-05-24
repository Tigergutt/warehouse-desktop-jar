package se.melsom.warehouse.common.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ViewState {
    Map<String, Object> data = new HashMap<>();

    public Boolean getBoolean(String name) {
        return (Boolean) data.get(name);
    }

    public void setBoolean(String name, Boolean value) {
        data.put(name, value);
    }

    public Integer getInteger(String name) {
        return (Integer) data.get(name);
    }

    public void setInteger(String name, Integer value) {
        data.put(name, value);
    }


    public String getString(String name) {
        return (String) data.get(name);
    }

    public void setString(String name, String value) {
        data.put(name, value);
    }

    public Collection<String> getCollection(String name) {
        return (Collection<String>) data.get(name);
    }

    public void setCollection(String name, Collection<String> value) {
        data.put(name, value);
    }
}
