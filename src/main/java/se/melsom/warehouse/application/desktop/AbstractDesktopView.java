package se.melsom.warehouse.application.desktop;

import javax.swing.*;

public interface AbstractDesktopView {
    void initialize();
    JFrame getFrame();
    void showView();
    void addView(JInternalFrame view);
    void updateState(ViewState viewState);
}
