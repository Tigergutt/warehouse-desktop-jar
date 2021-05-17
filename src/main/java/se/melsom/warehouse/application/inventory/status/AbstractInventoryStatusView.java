package se.melsom.warehouse.application.inventory.status;

import javax.swing.*;

public interface AbstractInventoryStatusView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel contentModel);
}

