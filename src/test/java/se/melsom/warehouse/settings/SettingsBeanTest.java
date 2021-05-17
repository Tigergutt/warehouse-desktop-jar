package se.melsom.warehouse.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettingsBeanTest {
    @Test
    public void testingSerialization() throws JsonProcessingException {
        SettingsBean bean = new SettingsBean();
        bean.properties.add("A", "0");
        bean.windows.add("B", new WindowBean());
        String result = new ObjectMapper().writeValueAsString(bean);
        String expected = "";
        expected += "{";
        expected += "\"properties\":";
        expected += "{\"A\":\"0\"}";
        expected += ",";
        expected += "\"windows\":";
        expected += "{";
        expected += "\"B\":";
        expected += "{\"x\":0,\"y\":0,\"width\":500,\"height\":200,\"visible\":true}";
        expected += "}";
        expected += "}";
        assertEquals(expected, result);
    }

    @Test
    public void testingDeserialization() throws JsonProcessingException {
        String json = "";
        json += "{";
        json += "\"properties\":";
        json += "{\"A\":\"0\",\"B\":\"1\"}";
        json += ",";
        json += "\"windows\":";
        json += "{";
        json += "\"C\":";
        json += "{\"x\":1,\"y\":2,\"width\":3,\"height\":4,\"visible\":false}";
        json += "}";
        json += "}";
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(SettingsBean.class);
        SettingsBean bean = reader.readValue(json);
        assertEquals("0", bean.properties.get("A"));
        assertEquals("1", bean.properties.get("B"));
        assertEquals(1, bean.windows.get("C").getX());
        assertEquals(2, bean.windows.get("C").getY());
        assertEquals(3, bean.windows.get("C").getWidth());
        assertEquals(4, bean.windows.get("C").getHeight());
        assertFalse(bean.windows.get("C").isVisible());
    }
}
