package se.melsom.warehouse.application.inventory.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.common.MessageView;
import se.melsom.warehouse.application.edit.item.EditItem;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.ItemMasterFile;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Component
public class ItemMaintenance extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(ItemMaintenance.class);

    public static final String GENERATE_REPORT_ACTION = "GenerateReport";
    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    public static final String SELECT_ITEM_ACTION = "SelectAction";
    public static final String EDIT_ITEM_ACTION = "EditItem";
    public static final String INSERT_ITEM_ACTION = "AddItem";
    public static final String REMOVE_ITEM_ACTION = "DeleteItem";
	
	private ApplicationPresentationModel controller;
	private InventoryAccounting inventoryAccounting;
	private ItemMasterFile itemMasterFile;
	private final ContentModel tableModel = new ContentModel();
	private final Map<String, Command> actionCommands = new HashMap<>();

	@Autowired private ItemService itemService;
	@Autowired private AbstractItemMaintenanceView itemMaintenanceView;
	@Autowired private EditItem editItem;
	@Autowired private DesktopPresentationModel desktopPresentationModel;

	private final ViewState viewState = new ViewState();

	@Override
	public void initialize() {
		logger.debug("Execute initialize().");
		tableModel.setItems(itemService.getItems());
    	itemMaintenanceView.initialize(tableModel);
    	itemMaintenanceView.updateState(viewState);
		desktopPresentationModel.addInternalFrame(itemMaintenanceView.getInternalFrame());
	}

	@Override
	public void showView() {
		logger.debug("showView()");
		itemMaintenanceView.showView();
	}

	public void handleExtendedEdit(boolean isEditEnabled) {
		viewState.setExtendedEditSelected(isEditEnabled);
		updateButtonStates();
	}

	public void handleGenerateReport() {
		Command command = actionCommands.get(GENERATE_REPORT_ACTION);
		if (command == null) {
			logger.warn("Action command for " + GENERATE_REPORT_ACTION);
			return;
		}

		logger.warn("Generate report is not implemened.");
	}

	public void handleEditItem() {
		logger.debug("Show edit dialog.");
		int selectedItemIndex = viewState.getSelectedRowIndex();
		ItemVO selectedItem = tableModel.getItem(selectedItemIndex);
		ItemVO editedItem = editItem.editItem(selectedItem);

		if (editedItem != null) {
			logger.debug("Edited existing item=" + editedItem);
			if (!selectedItem.getNumber().equals(editedItem.getNumber())) {
				if (itemMasterFile.getItem(editedItem.getNumber()) != null) {

				}
			}

			int rowIndex = tableModel.updateItem(editedItem, selectedItemIndex);
			viewState.setSelectedRowIndex(rowIndex);
			itemService.updateItem(editedItem);
			itemMaintenanceView.updateState(viewState);
		}
	}

	public void handleInsertItem() {
		logger.debug("Show edit dialog.");
		ItemVO newItem = new ItemVO("Y0000-000000", "Ny artikel", "ST");
		ItemVO editedItem = editItem.editItem(newItem);

		if (editedItem != null) {
			logger.debug("Edited new item=" + editedItem);
			int rowIndex = tableModel.insertItem(editedItem);
			viewState.setSelectedRowIndex(rowIndex);
			itemService.addItem(newItem);
			itemMaintenanceView.updateState(viewState);
		}
	}

	public void handleRemoveItem() {
		int selectedItemIndex = viewState.getSelectedRowIndex();
		logger.debug("Delete row=" + selectedItemIndex);
		ItemVO item = tableModel.getItem(selectedItemIndex);
		if (inventoryAccounting.isUnitReferenced(item.getId())) {
			String title = item.getNumber() + ":" + item.getName();
			String message = "<html>";

			message += "<b>Kan inte ta bort denna artikel</b><br/>";
			message += "Den används. Du måste se till att den<br/>";
			message += "inte används innan du kan ta bort den.";
			message += "</html>";

			MessageView messageView = new MessageView(title, message);

			messageView.show(controller.getDesktopView());
		} else {
			ItemVO removedItem = tableModel.removeItem(selectedItemIndex);

			itemService.removeItem(removedItem);
		}
	}

	public void handleRowSelected(int rowIndex) {
		viewState.setSelectedRowIndex(rowIndex);
		updateButtonStates();
	}

	public Vector<ItemVO> getItems() {
		return tableModel.getItems();
	}

    public void setItems(Collection<ItemVO> collection) {
		tableModel.setItems(collection);
	}

	private void updateButtonStates() {
		boolean isRowSelected = false;

		int rowIndex = viewState.getSelectedRowIndex();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}

		boolean isEditEnabled = viewState.isExtendedEditSelected();

		viewState.setInsertButtonEnabled(isEditEnabled);
		viewState.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		viewState.setEditButtonEnabled(isRowSelected && isEditEnabled);

		itemMaintenanceView.updateState(viewState);
	}

//	@Override
//	public void handleEvent(ModelEvent event) {
//		logger.debug("Receved model event=" + event);
//		switch (event.getType()) {
//		case STOCK_LOCATIONS_RELOADED:
//		case STOCK_LOCATIONS_MODIFIED:
//			break;
//
//		default:
//			break;
//		}
//
//		logger.debug("Ignored the event.");
//	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		logger.info("Element {} selected.", e.getFirstIndex());
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int rowIndex = e.getFirstRow();
		int columnIndex = e.getColumn();
		
		if (rowIndex < 0 || columnIndex < 0) {
			return;
		}
		
		switch (e.getType()) {
		case TableModelEvent.UPDATE:
			ItemVO updated = tableModel.getItems().get(rowIndex);
			itemService.updateItem(updated);
			return;
			
		case TableModelEvent.INSERT:
		case TableModelEvent.DELETE:
			return;
			
		default:
			break;
		}
		
		logger.warn("Unhandled event at row=" + e.getFirstRow() + ",column=" + e.getColumn());
	}
}
