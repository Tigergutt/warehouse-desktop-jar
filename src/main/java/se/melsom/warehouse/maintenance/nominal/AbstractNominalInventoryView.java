package se.melsom.warehouse.maintenance.nominal;

import javax.swing.*;

public interface AbstractNominalInventoryView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel contentModel);
    void updateState(ViewState state);
}
