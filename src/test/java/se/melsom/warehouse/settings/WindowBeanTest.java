package se.melsom.warehouse.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WindowBeanTest {
    @Test
    public void testingSerialization() throws JsonProcessingException {
        WindowBean bean = new WindowBean();
        bean.setWidth(100);
        bean.setHeight(50);
        bean.setX(125);
        bean.setY(250);
        bean.setVisible(true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bean);
        assertEquals("{\"x\":125,\"y\":250,\"width\":100,\"height\":50,\"visible\":true}", json);
    }
}
