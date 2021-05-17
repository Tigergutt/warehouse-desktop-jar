package se.melsom.warehouse.application.search;

import se.melsom.warehouse.application.inventory.actual.ViewState;

import javax.swing.*;

public interface AbstractSearchView {
    JInternalFrame getInternalFrame();
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
