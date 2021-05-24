package se.melsom.warehouse.common.view;

import javax.swing.table.AbstractTableModel;

public interface AbstractView {
    void showView();
    void initialize(AbstractTableModel tableModel);
    void updateState(ViewState state);
}
