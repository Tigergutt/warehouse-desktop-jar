package se.melsom.warehouse.application.organization;


public interface AbstractOrganizationUnitView {
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
