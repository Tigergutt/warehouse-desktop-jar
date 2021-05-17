package se.melsom.warehouse.application.inventory.master;

public interface AbstractMasterInventoryView {
    void showView();
    void initialize(ContentModel contentModel);
    void updateState(ViewState state);
}
