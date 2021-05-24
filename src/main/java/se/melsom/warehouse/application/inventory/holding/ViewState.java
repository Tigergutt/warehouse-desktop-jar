package se.melsom.warehouse.application.inventory.holding;

import java.util.Collection;
import java.util.Vector;

public class ViewState {
    private boolean isExtendedEditEnabled = true;
    private boolean isExtendedEditSelected = false;
    private boolean isGenerateReportEnabled = true;
    private boolean isInsertButtonEnabled = false;
    private boolean isRemoveButtonEnabled = false;
    private boolean isEditButtonEnabled = false;
    private int selectedItemIndex = -1;
    private final Vector<String> superiors = new Vector<>();
    private int selectedSuperiorUnitIndex = -1;
    private final Vector<String> units = new Vector<>();
    private int selectedUnitIndex = -1;
    private boolean isUpdating = false;

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

    public boolean isGenerateReportEnabled() {
        return isGenerateReportEnabled;
    }

    public void setGenerateReportEnabled(boolean generateReportEnabled) {
        isGenerateReportEnabled = generateReportEnabled;
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

    public boolean isEditButtonEnabled() {
        return isEditButtonEnabled;
    }

    public void setEditButtonEnabled(boolean editButtonEnabled) {
        isEditButtonEnabled = editButtonEnabled;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int index) {
        selectedItemIndex = index;
    }

    public Vector<String> getSuperiors() {
        return superiors;
    }

    public void setSuperiors(Collection<String> superiors) {
        this.superiors.clear();
        this.superiors.addAll(superiors);
    }

    public int getSelectedSuperiorUnitIndex() {
        return selectedSuperiorUnitIndex;
    }

    public void setSelectedSuperiorUnitIndex(int index) {
        selectedSuperiorUnitIndex = index;
    }

    public Vector<String> getUnits() {
        return units;
    }

    public void setUnits(Collection<String> units) {
        this.units.clear();
        this.units.addAll(units);
    }

    public int getSelectedUnitIndex() {
        return selectedUnitIndex;
    }

    public void setSelectedUnitIndex(int index) {
        selectedUnitIndex = index;
    }

    public boolean isUpdating() {
        return isUpdating;
    }

    public void setUpdating(boolean updating) {
        isUpdating = updating;
    }
}
