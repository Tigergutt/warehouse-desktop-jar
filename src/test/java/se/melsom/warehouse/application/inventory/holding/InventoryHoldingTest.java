package se.melsom.warehouse.application.inventory.holding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.main.AbstractDesktopView;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ActualInventoryService;
import se.melsom.warehouse.data.service.HoldingService;
import se.melsom.warehouse.data.service.OrganizationService;
import se.melsom.warehouse.settings.PersistentSettings;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {
        InventoryHolding.class,
        ApplicationPresentationModel.class,
        DesktopPresentationModel.class,
        PersistentSettings.class})
public class InventoryHoldingTest {
    @MockBean
    AbstractDesktopView desktopView;

    @MockBean
    AbstractInventoryHoldingView inventoryHoldingView;

    @Autowired
    InventoryHolding presentationModel;

    @MockBean
    HoldingService holdingService;

    @MockBean
    OrganizationService organizationService;

    @MockBean
    ActualInventoryService actualInventoryService;

    @BeforeEach
    public void setUp() {
    }

    /**
     * Är fortfarande rudis på Spring Boot så det är lika bra att kolla att injection funkar.
     */
    @Test
    public void testingAutowired() {
        assertNotNull(presentationModel);
    }

}
