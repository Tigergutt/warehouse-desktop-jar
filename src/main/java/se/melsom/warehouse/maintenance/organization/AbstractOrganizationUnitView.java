package se.melsom.warehouse.maintenance.organization;


public interface AbstractOrganizationUnitView {
    void showView();
    void initialize(ContentModel tableModel);
    void updateState(ViewState state);
}
