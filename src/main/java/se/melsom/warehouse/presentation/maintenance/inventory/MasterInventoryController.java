package se.melsom.warehouse.presentation.maintenance.inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.entity.inventory.MasterInventory;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.edit.EditMasterInventoryController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class MasterInventoryController extends ViewController implements TableModelListener, ListSelectionListener {
	private static Logger logger = Logger.getLogger(MasterInventoryController.class);

	public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
	public static final String SELECT_ACTION = "SelectAction";
	public static final String INSERT_ACTION = "InsertAction";
	public static final String EDIT_ACTION = "EditAction";
	public static final String REMOVE_ACTION = "RemoveAction";

	private ApplicationController controller;
	private InventoryAccounting inventoryAccounting;
	private MasterInventoryTableModel tableModel;
	private MasterInventoryView view;

	public MasterInventoryController(ApplicationController controller) {
		this.controller = controller;

		inventoryAccounting = controller.getInventoryAccounting();

		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 120, 93, 619, 260, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}
		
		tableModel = new MasterInventoryTableModel();
		tableModel.addTableModelListener(this);

		view = new MasterInventoryView(controller.getDesktopView(), this, tableModel);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setExtendedEditEnabled(false);
		view.setExtendedEditAction(EXTENDED_EDIT_ACTION, this);
		view.setInstanceTableAction(SELECT_ACTION, this);
		view.setInsertButtonEnabled(false);
		view.setInsertButtonAction(INSERT_ACTION, this);
		view.setEditButtonEnabled(false);
		view.setEditButtonAction(EDIT_ACTION, this);
		view.setRemoveButtonEnabled(false);
		view.setRemoveButtonAction(REMOVE_ACTION, this);
				
		tableModel.setInstances(inventoryAccounting.getMasterInventory(EntityName.NULL_ID, null));		
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.trace("Received action=" + e.getActionCommand() + ",source=" + e.getSource() + ",event=" + e);
		switch (e.getActionCommand()) {
		case EXTENDED_EDIT_ACTION: 
			checkEditButtons();
		break;
		
		case INSERT_ACTION: {
			EditMasterInventoryController instanceEditor = new EditMasterInventoryController(inventoryAccounting, controller.getDesktopView());
			MasterInventory instance = new MasterInventory();
			instance.setId(inventoryAccounting.getNextMasterInventoryId());
			MasterInventory editedInstance = instanceEditor.editInstance(instance);
			
			if (editedInstance != null) {
				int rowIndex = tableModel.insert(editedInstance);
				
				view.setSelectedTableRow(rowIndex);
				inventoryAccounting.addInventory(editedInstance);
			}
			break;
		}
			
		case EDIT_ACTION: {
			EditMasterInventoryController instanceEditor = new EditMasterInventoryController(inventoryAccounting, controller.getDesktopView());
			int rowIndex = view.getSelectedTableRow();
			MasterInventory instance = tableModel.getInstance(rowIndex);
			MasterInventory editedInstance = instanceEditor.editInstance(instance);
			
			if (editedInstance != null) {
				rowIndex = tableModel.update(editedInstance, rowIndex);
 				view.setSelectedTableRow(rowIndex);
				inventoryAccounting.updateInventory(editedInstance);
			}
			break;
		}
		
		case REMOVE_ACTION:
			int selectedIndex = view.getSelectedTableRow();
			MasterInventory removedInstance = tableModel.remove(selectedIndex);
			
			inventoryAccounting.removeInventory(removedInstance);
			break;
		}
	}	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		checkEditButtons();
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
			MasterInventory updated = tableModel.getInstances().get(rowIndex);
			inventoryAccounting.updateInventory(updated);
			return;
			
		case TableModelEvent.INSERT:
		case TableModelEvent.DELETE:
			return;
			
		default:
			break;
		}
		
	}

	@Override
	public JComponent getView() {
		return null;
	}

	@Override
	public void finalize() throws Throwable {
		super.finalize();
		logger.debug("Finalize.");
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}

	String getWindowName() {
		return MasterInventoryView.class.getSimpleName();
	}
	
	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean isEditEnabled = view.isExtendedEditEnabled();
		
		int rowIndex = view.getSelectedTableRow();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}
		
		view.setInsertButtonEnabled(isEditEnabled);
		view.setRemoveButtonEnabled(isRowSelected && isEditEnabled);
		view.setEditButtonEnabled(isRowSelected && isEditEnabled);
	}
}
