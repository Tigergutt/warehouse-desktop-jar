package se.melsom.warehouse.application.inventory.actual;

import java.util.Collection;
import java.util.Vector;

public class ViewState {
    private boolean isExtendedEditEnabled = true;
    private boolean isExtendedEditSelected = false;
    private boolean isGenerateReportButtonEnabled = false;
    private boolean isEditButtonEnabled = false;
    private boolean isInsertButtonEnabled = false;
    private boolean isRemoveButtonEnabled = false;
    private final Vector<String> sections = new Vector<>();
    private int currentSectionIndex = -1;
    private final Vector<String> slots = new Vector<>();
    private int currentSlotIndex = -1;
    private int currentItemIndex = -1;
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

    public boolean isGenerateReportButtonEnabled() {
        return isGenerateReportButtonEnabled;
    }

    public void setGenerateReportButtonEnabled(boolean generateReportButtonEnabled) {
        isGenerateReportButtonEnabled = generateReportButtonEnabled;
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

    public Collection<String> getStockLocationSections() {
        return sections;
    }

    public void setStockLocationSections(Collection<String> locationSections) {
        sections.clear();
        sections.addAll(locationSections);
    }

    public Collection<String> getStockLocationSlots() {
        return slots;
    }
    public void setStockLocationSlots(Collection<String> locationSlots) {
        slots.clear();
        slots.addAll(locationSlots);
    }

    public int getCurrentSectionIndex() {
        return currentSectionIndex;
    }

    public void setCurrentSectionIndex(int currentSectionIndex) {
        this.currentSectionIndex = currentSectionIndex;
    }

    public int getCurrentSlotIndex() {
        return currentSlotIndex;
    }

    public void setCurrentSlotIndex(int currentSlotIndex) {
        this.currentSlotIndex = currentSlotIndex;
    }

    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    public void setCurrentItemIndex(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
    }

    public boolean isUpdating() {
        return isUpdating;
    }

    public void setUpdating(boolean updating) {
        isUpdating = updating;
    }
}
