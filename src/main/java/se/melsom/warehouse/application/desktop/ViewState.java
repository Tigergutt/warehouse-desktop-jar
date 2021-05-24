package se.melsom.warehouse.application.desktop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewState {
    private static final Logger logger = LoggerFactory.getLogger(ViewState.class);

    private boolean isGenerateStockOnHandReportEnabled = false;
    private boolean isGenerateStockLocationReportEnabled = false;

    private boolean isEditItemsEnabled = false;
    private boolean isEditInstancesEnabled = false;
    private boolean isEditApplicationsEnabled = false;
    private boolean isEditHoldingsEnabled = false;
    private boolean isEditOrganizationEnabled = false;
    private boolean isLoggerViewEnabled = false;

    private boolean isShowStockOnHandViewChecked = false;
    private boolean isShowStockOnHandViewEnabled = false;
    private boolean isShowInventoryViewChecked = false;

    private boolean isShowInventoryViewEnabled = false;
    private boolean isShowInventoryHoldingViewChecked = false;
    private boolean isShowInventoryHoldingViewEnabled = false;
    private boolean isShowSearchViewChecked = false;
    private boolean isShowSearchViewEnabled = false;

    public ViewState() {
        logger.debug("Execute constructor.");
    }

    public boolean isGenerateStockOnHandReportEnabled() {
        return isGenerateStockOnHandReportEnabled;
    }

    public boolean isGenerateStockLocationReportEnabled() {
        return isGenerateStockLocationReportEnabled;
    }

    public void setGenerateStockLocationReportEnabled(boolean generateStockLocationReportEnabled) {
        isGenerateStockLocationReportEnabled = generateStockLocationReportEnabled;
    }

    public void setGenerateStockOnHandReportEnabled(boolean generateStockOnHandReportEnabled) {
        isGenerateStockOnHandReportEnabled = generateStockOnHandReportEnabled;
    }

    public boolean isEditItemsEnabled() {
        return isEditItemsEnabled;
    }

    public void setEditItemsEnabled(boolean isEnabled) {
        isEditItemsEnabled = isEnabled;
    }

    public boolean isEditInstancesEnabled() {
        return isEditInstancesEnabled;
    }

    public void setEditInstancesEnabled(boolean isEnabled) {
        isEditInstancesEnabled = isEnabled;
    }

    public boolean isEditApplicationsEnabled() {
        return isEditApplicationsEnabled;
    }

    public void setEditApplicationsEnabled(boolean editApplicationsEnabled) {
        isEditApplicationsEnabled = editApplicationsEnabled;
    }

    public boolean isEditHoldingsEnabled() {
        return isEditHoldingsEnabled;
    }

    public void setEditHoldingsEnabled(boolean editHoldingsEnabled) {
        isEditHoldingsEnabled = editHoldingsEnabled;
    }

    public boolean isEditOrganizationEnabled() {
        return isEditOrganizationEnabled;
    }

    public void setEditOrganizationEnabled(boolean editOrganizationEnabled) {
        isEditOrganizationEnabled = editOrganizationEnabled;
    }

    public boolean isShowStockOnHandViewEnabled() {
        return isShowStockOnHandViewEnabled;
    }

    public void setShowStockOnHandViewEnabled(boolean isEnabled) {
        isShowStockOnHandViewEnabled = isEnabled;
    }

    public boolean isShowStockOnHandViewChecked() {
        return isShowStockOnHandViewChecked;
    }

    public void setShowStockOnHandViewChecked(boolean isChecked) {
        isShowStockOnHandViewChecked = isChecked;
    }

    public boolean isShowInventoryViewChecked() {
        return isShowInventoryViewChecked;
    }

    public void setShowInventoryViewChecked(boolean isChecked) {
        isShowInventoryViewChecked = isChecked;
    }

    public boolean isShowInventoryViewEnabled() {
        return isShowInventoryViewEnabled;
    }

    public void setShowInventoryViewEnabled(boolean isEnabled) {
        isShowInventoryViewEnabled = isEnabled;
    }

    public boolean isShowInventoryHoldingViewEnabled() {
        return isShowInventoryHoldingViewEnabled;
    }

    public void setShowInventoryHoldingViewEnabled(boolean isEnabled) {
        isShowInventoryHoldingViewEnabled = isEnabled;
    }

    public boolean isShowInventoryHoldingViewChecked() {
        return isShowInventoryHoldingViewChecked;
    }

    public void setShowInventoryHoldingViewChecked(boolean isChecked) {
        isShowInventoryHoldingViewChecked = isChecked;
    }

    public boolean isShowSearchViewChecked() {
        return isShowSearchViewChecked;
    }

    public void setShowSearchViewChecked(boolean isChecked) {
        isShowSearchViewChecked = isChecked;
    }

    public boolean isShowSearchViewEnabled() {
        return isShowSearchViewEnabled;
    }

    public void setShowSearchViewEnabled(boolean isEnabled) {
        isShowSearchViewEnabled = isEnabled;
    }

    public boolean isLoggerViewEnabled() {
        return isLoggerViewEnabled;
    }

    public void setLoggerViewEnabled(boolean isEnabled) {
        isLoggerViewEnabled = isEnabled;
    }
}
