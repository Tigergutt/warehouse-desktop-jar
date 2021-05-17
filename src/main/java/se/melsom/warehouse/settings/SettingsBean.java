package se.melsom.warehouse.settings;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "properties", "windows" })
public class SettingsBean {
    public PropertiesBean properties = new PropertiesBean();
    public WindowsBean windows = new WindowsBean();
}
