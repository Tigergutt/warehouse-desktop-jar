package se.melsom.warehouse.maintenance.items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.edit.item.EditItem;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.data.service.ItemService;
import se.melsom.warehouse.data.vo.ItemVO;
import se.melsom.warehouse.edit.item.EditItemListener;

import javax.annotation.PostConstruct;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Component
public class ItemMaintenance extends AbstractPresentationModel implements EditItemListener {
	private static final Logger logger = LoggerFactory.getLogger(ItemMaintenance.class);

	@Autowired private ItemService itemService;
	@Autowired private AbstractItemMaintenanceView itemMaintenanceView;
	@Autowired private DesktopPresentationModel desktopPresentationModel;
	@Autowired private AbstractDesktopView desktopView;

	public static final String GENERATE_REPORT_ACTION = "GenerateReport";

	private final ContentModel contentModel = new ContentModel();
	private final Map<String, Command> actionCommands = new HashMap<>();
	private final ViewState viewState = new ViewState();

	private ItemVO selectedItem = null;

	@PostConstruct
	@Override
	public void initialize() {
		logger.debug("Execute initialize().");
		contentModel.setItems(itemService.getItems());
    	itemMaintenanceView.initialize(contentModel);
    	itemMaintenanceView.updateState(viewState);
		desktopPresentationModel.addInternalFrame(itemMaintenanceView.getInternalFrame());
	}

	@Override
	public void showView() {
		logger.debug("showView()");
		itemMaintenanceView.showView();
	}

	public void updateExtendedEdit(boolean isEditEnabled) {
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
		EditItem editItem = new EditItem(itemService, desktopView.getFrame());

		selectedItem = contentModel.getItem(selectedItemIndex);
		editItem.editItem(selectedItem, this);
	}

	public void handleInsertItem() {
		logger.debug("Show edit dialog.");
		ItemVO newItem = new ItemVO("Y0000-000000", "Ny artikel", "ST");
		EditItem editItem = new EditItem(itemService, desktopView.getFrame());
		editItem.editItem(newItem, this);
	}

	@Override
	public void save(ItemVO editedItem) {
		logger.debug("Edited existing item={}.", editedItem);
		if (selectedItem == null) {
			logger.debug("Edited new item=" + editedItem);
			int rowIndex = contentModel.insertItem(editedItem);

			viewState.setSelectedRowIndex(rowIndex);
			itemService.addItem(editedItem);
		} else {
			if (!selectedItem.getNumber().equals(editedItem.getNumber())) {
				if (itemService.getItem(editedItem.getNumber()) != null) {

				}
			}

			selectedItem = null;

			int selectedItemIndex = viewState.getSelectedRowIndex();
			int rowIndex = contentModel.updateItem(editedItem, selectedItemIndex);

			viewState.setSelectedRowIndex(rowIndex);
			itemService.updateItem(editedItem);
		}

		itemMaintenanceView.updateState(viewState);
	}

	public void handleRemoveItem() {
		int selectedItemIndex = viewState.getSelectedRowIndex();
		logger.debug("Delete row=" + selectedItemIndex);
		ItemVO item = contentModel.getItem(selectedItemIndex);
//		if (inventoryAccounting.isUnitReferenced(item.getId())) {
//			String title = item.getNumber() + ":" + item.getName();
//			String message = "<html>";
//
//			message += "<b>Kan inte ta bort denna artikel</b><br/>";
//			message += "Den används. Du måste se till att den<br/>";
//			message += "inte används innan du kan ta bort den.";
//			message += "</html>";
//
//			MessageView messageView = new MessageView(title, message);
//
//			messageView.show(controller.getDesktopView());
//		} else {
//			ItemVO removedItem = tableModel.removeItem(selectedItemIndex);
//
//			itemService.removeItem(removedItem);
//		}
	}

	public void handleRowSelected(int rowIndex) {
		viewState.setSelectedRowIndex(rowIndex);
		updateButtonStates();
	}

	public Vector<ItemVO> getItems() {
		return contentModel.getItems();
	}

    public void setItems(Collection<ItemVO> collection) {
		contentModel.setItems(collection);
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
			ItemVO updated = contentModel.getItems().get(rowIndex);
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
