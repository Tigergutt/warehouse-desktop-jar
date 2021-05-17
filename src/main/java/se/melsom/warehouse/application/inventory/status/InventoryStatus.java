package se.melsom.warehouse.application.inventory.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.InventoryService;
import se.melsom.warehouse.data.vo.StockOnHandVO;

import java.util.Vector;

/**
 * StatusPresentationModel (lagersaldo) realiserar all logik och hämtande av data
 * i presentationen av lagersaldo.
 */
@Component
public class InventoryStatus extends AbstractPresentationModel {
    private static final Logger logger = LoggerFactory.getLogger(InventoryStatus.class);

    private final ContentModel contentModel = new ContentModel();

    @Autowired private AbstractInventoryStatusView inventoryStatusView;
    @Autowired private InventoryService inventoryService;
    @Autowired private DesktopPresentationModel desktopPresentationModel;

    public InventoryStatus() {
        logger.debug("Execute constructor.");
    }

    @Override
    public void initialize() {
        logger.debug("Execute initialize.");
        inventoryStatusView.initialize(contentModel);
        desktopPresentationModel.addInternalFrame(inventoryStatusView.getInternalFrame());
        contentModel.setStockOnHand(getStockOnHand());
    }

    @Override
    public void showView() {
        logger.debug("Execute showView.");
        inventoryStatusView.showView();
    }

    public ContentModel getContentModel() {
        return contentModel;
    }

    public Vector<StockOnHandVO> getStockOnHand() {
       return inventoryService.getStockOnHand();
    }

    public ItemViewOrder getItemViewOrder() {
        return contentModel.getItemViewOrder();
    }

    public void setItemViewOrder(ItemViewOrder itemViewOrder) {
        contentModel.setItemViewOrder(itemViewOrder);
    }

    public boolean isShowingShortfall() {
        return contentModel.isShowingShortfall();
    }

    public void setShowingShortfall(boolean showingShortfall) {
        logger.debug("set show shortfall={}.", showingShortfall);
        contentModel.setShowingShortfall(showingShortfall);
    }

    public boolean isShowingBalances() {
        return contentModel.isShowingBalances();
    }

    public void setShowingBalances(boolean showingBalances) {
        logger.debug("set show balances={}.", showingBalances);
        contentModel.setShowingBalances(showingBalances);
    }

    public boolean isShowingOverplus() {
        return contentModel.isShowingOverplus();
    }

    public void setShowingOverplus(boolean showingOverplus) {
        logger.debug("set show overplus={}.", showingOverplus);
        contentModel.setShowingOverplus(showingOverplus);
    }
}
