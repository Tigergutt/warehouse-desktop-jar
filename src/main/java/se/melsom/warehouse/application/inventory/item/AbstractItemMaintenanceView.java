package se.melsom.warehouse.application.inventory.item;

import javax.swing.*;

public interface AbstractItemMaintenanceView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}

