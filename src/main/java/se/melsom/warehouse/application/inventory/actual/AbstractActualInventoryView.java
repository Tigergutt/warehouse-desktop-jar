package se.melsom.warehouse.application.inventory.actual;

import javax.swing.*;

public interface AbstractActualInventoryView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
