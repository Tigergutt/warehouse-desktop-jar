package se.melsom.warehouse.maintenance.holdings;

public interface AbstractStockLocationHoldingView {
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
