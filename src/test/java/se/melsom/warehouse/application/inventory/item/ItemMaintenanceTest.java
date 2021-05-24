package se.melsom.warehouse.application.inventory.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.edit.item.EditItem;
import se.melsom.warehouse.edit.item.EditItemView;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.maintenance.items.AbstractItemMaintenanceView;
import se.melsom.warehouse.maintenance.items.ItemMaintenance;
import se.melsom.warehouse.settings.PersistentSettings;

@SpringBootTest(classes = {
        EditItem.class,
        ItemMaintenance.class,
        ApplicationPresentationModel.class,
        DesktopPresentationModel.class,
        PersistentSettings.class})
public class ItemMaintenanceTest {
    @MockBean
    AbstractDesktopView desktopView;

    @MockBean
    EditItemView editItemView;

    @MockBean
    AbstractItemMaintenanceView itemMaintenanceView;

    @Autowired
    ItemMaintenance presentationModel;

    @MockBean
    ItemService itemService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testing() {
    }
}
