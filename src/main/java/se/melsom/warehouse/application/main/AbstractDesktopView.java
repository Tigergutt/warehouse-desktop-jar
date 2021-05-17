package se.melsom.warehouse.application.main;

import javax.swing.*;

public interface AbstractDesktopView {
    void initialize();
    void showView();
    void addView(JInternalFrame view);
    void updateState(ViewState viewState);
}
