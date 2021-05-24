package se.melsom.warehouse.maintenance.nominal;

public class ViewState {
    private boolean isExtendedEditEnabled = true;
    private boolean isExtendedEditSelected = false;
    private boolean isEditButtonEnabled = false;
    private boolean isInsertButtonEnabled = false;
    private boolean isRemoveButtonEnabled = false;
    private int selectedRowIndex = -1;

    public boolean isExtendedEditEnabled() {
        return isExtendedEditEnabled;
    }

    public void setExtendedEditEnabled(boolean extendedEditEnabled) {
        isExtendedEditEnabled = extendedEditEnabled;
    }

    public boolean isExtendedEditSelected() {
        return isExtendedEditSelected;
    }

    public void setExtendedEditSelected(boolean extendedEditSelected) {
        isExtendedEditSelected = extendedEditSelected;
    }

    public boolean isEditButtonEnabled() {
        return isEditButtonEnabled;
    }

    public void setEditButtonEnabled(boolean editButtonEnabled) {
        isEditButtonEnabled = editButtonEnabled;
    }

    public boolean isInsertButtonEnabled() {
        return isInsertButtonEnabled;
    }

    public void setInsertButtonEnabled(boolean insertButtonEnabled) {
        isInsertButtonEnabled = insertButtonEnabled;
    }

    public boolean isRemoveButtonEnabled() {
        return isRemoveButtonEnabled;
    }

    public void setRemoveButtonEnabled(boolean removeButtonEnabled) {
        isRemoveButtonEnabled = removeButtonEnabled;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }
}
