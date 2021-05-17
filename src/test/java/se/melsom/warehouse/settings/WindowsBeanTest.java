package se.melsom.warehouse.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WindowsBeanTest {
    @Test
    public void testingSerialize() throws JsonProcessingException {
        WindowsBean bean = new WindowsBean();
        WindowBean window = new WindowBean();
        bean.add("dora", window);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bean);
        String expected = "";
        expected += "{";
        expected += "\"dora\":";
        expected += "{\"x\":0,\"y\":0,\"width\":500,\"height\":200,\"visible\":true}";
        expected += "}";
        assertEquals(expected, json);
    }
}
