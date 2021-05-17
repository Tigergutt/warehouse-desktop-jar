package se.melsom.warehouse.settings;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersistentSettingsTest {
    @Test
    public void testingLoadSettings() {
        String json = "";
        json += String.format("{%n");
        json += String.format("  \"properties\":%n");
        json += String.format("     {\"A\":\"0\",\"B\":\"1\"}%n");
        json += String.format("     ,%n");
        json += String.format("  \"windows\":%n");
        json += String.format("  {%n");
        json += String.format("     \"C\":%n");
        json += String.format("     {\"x\":1,\"y\":2,\"width\":3,\"height\":4,\"visible\":false}%n");
        json += String.format("   }%n");
        json += String.format("}%n");
        PersistentSettings settings = new PersistentSettings();
        assertNull(settings.getProperty("A"));
        assertNull(settings.getProperty("B"));
        assertNull(settings.getWindowSettings("C"));
        ByteArrayInputStream input = new ByteArrayInputStream(json.getBytes());
        settings.loadData(input);
    }

    @Test
    public void testingSaveSettings() throws IOException {
        PersistentSettings settings = new PersistentSettings();
        settings.setProperty("A", "1");
        settings.setProperty("B", "2");
        settings.addWindowSettings("C", new WindowBean(10, 20, 30, 40, true));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        settings.saveData(output);
        String json = "";
        json += String.format("{%n");
        json += String.format("  \"properties\" : {%n");
        json += String.format("    \"A\" : \"1\",%n");
        json += String.format("    \"B\" : \"2\"%n");
        json += String.format("  },%n");
        json += String.format("  \"windows\" : {%n");
        json += String.format("    \"C\" : {%n");
        json += String.format("      \"x\" : 10,%n");
        json += String.format("      \"y\" : 20,%n");
        json += String.format("      \"width\" : 30,%n");
        json += String.format("      \"height\" : 40,%n");
        json += String.format("      \"visible\" : true%n");
        json += String.format("    }%n");
        json += String.format("  }%n");
        json += String.format("}");
        assertEquals(json, output.toString());
    }
}
