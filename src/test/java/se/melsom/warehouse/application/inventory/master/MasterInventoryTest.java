package se.melsom.warehouse.application.inventory.master;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.maintenance.nominal.AbstractNominalInventoryView;
import se.melsom.warehouse.maintenance.nominal.NominalInventory;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.data.service.NominalInventoryService;
import se.melsom.warehouse.settings.PersistentSettings;

@SpringBootTest(classes = {
        NominalInventory.class,
        ApplicationPresentationModel.class,
        DesktopPresentationModel.class,
        PersistentSettings.class})
public class MasterInventoryTest {
    @MockBean
    AbstractDesktopView desktopView;

    @MockBean
    AbstractNominalInventoryView masterInventoryView;

    @Autowired
    NominalInventory masterInventory;

    @MockBean
    NominalInventoryService nominalInventoryService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testing() {
    }
}
