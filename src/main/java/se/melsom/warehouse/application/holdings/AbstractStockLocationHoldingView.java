package se.melsom.warehouse.application.holdings;

public interface AbstractStockLocationHoldingView {
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
