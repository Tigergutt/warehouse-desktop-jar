package se.melsom.warehouse.application.inventory.holding;

import javax.swing.*;

public interface AbstractInventoryHoldingView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
