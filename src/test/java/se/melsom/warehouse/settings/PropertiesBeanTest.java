package se.melsom.warehouse.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertiesBeanTest {
    @Test
    public void testingSerialization() throws JsonProcessingException {
        PropertiesBean bean = new PropertiesBean();

        bean.add("key1", "val1");
        bean.add("key2", "val2");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bean);
        String expected = "";
        expected += "{";
        expected += "\"key1\":\"val1\"";
        expected += ",";
        expected += "\"key2\":\"val2\"";
        expected += "}";
        assertEquals(expected, json);
    }

    @Test
    public void testingDeserialization() throws JsonProcessingException {
        String json = "{\"key1\":\"val1\",\"key2\":\"val2\"}";
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerFor(PropertiesBean.class);
        PropertiesBean bean = reader.readValue(json);
        assertEquals("val1", bean.get("key1"));
        assertEquals("val2", bean.get("key2"));
    }
}
