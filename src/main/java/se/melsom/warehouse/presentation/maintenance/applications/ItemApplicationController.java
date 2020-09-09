package se.melsom.warehouse.presentation.maintenance.applications;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import se.melsom.warehouse.application.ApplicationController;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.UnitsMasterFile;
import se.melsom.warehouse.model.entity.AccumulatedApplication;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.edit.EditItemApplicationController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

public class ItemApplicationController extends ViewController implements TableModelListener, TreeSelectionListener {
	private static Logger logger = Logger.getLogger(ItemApplicationController.class);

	public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
	public static final String PACKING_SLIP_ACTION = "PackingSlipAction";
	public static final String TREE_ACTION = "TreeAction";
	public static final String TABLE_ACTION = "TableAction";
	public static final String ACCUMULATE_ACTION = "AccAction";
	public static final String INSERT_ACTION = "InsertAction";
	public static final String EDIT_ACTION = "EditAction";
	public static final String REMOVE_ACTION = "RemoveAction";

	private ApplicationController controller;
	private InventoryAccounting inventoryAccounting;
	private TreeModel treeModel;
	private MutableTreeNode rootNode;
	private ItemApplicationsTableModel tableModel;
	private ItemApplicationView view;
	
	public ItemApplicationController(ApplicationController controller) {
		logger.debug("Executing constructor.");
		this.controller = controller;
		
		inventoryAccounting = controller.getInventoryAccounting();
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 120, 93, 619, 260, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}

		rootNode = new DefaultMutableTreeNode();

		UnitsMasterFile unitsMasterfile = inventoryAccounting.getUnitsMasterFile();
		
		for (OrganizationalUnit aUnit : unitsMasterfile.getUnits()) {
			if (aUnit.getLevel() == 0) {
				logger.debug("Root object=" + aUnit);
				rootNode.setUserObject(aUnit);
				
				for (int subordinateIndex = 0; subordinateIndex < aUnit.getSubordinates().size(); subordinateIndex++) {
					OrganizationalUnit subordinate = aUnit.getSubordinates().get(subordinateIndex);
					DefaultMutableTreeNode subordinateNode = new DefaultMutableTreeNode(subordinate);
					
					rootNode.insert(subordinateNode, subordinateIndex);
					
					for (int subSubordinateIndex = 0; subSubordinateIndex < subordinate.getSubordinates().size(); subSubordinateIndex++) {
						OrganizationalUnit subSubordinate = subordinate.getSubordinates().get(subSubordinateIndex);
						DefaultMutableTreeNode subSubordinateNode = new DefaultMutableTreeNode(subSubordinate);
						
						subordinateNode.insert(subSubordinateNode, subSubordinateIndex);
					}
				}
				break;
			}
		}

		treeModel = new DefaultTreeModel(rootNode);
		tableModel = new ItemApplicationsTableModel();
		
		tableModel.addTableModelListener(this);
		
		view = new ItemApplicationView(controller.getDesktopView(),this, treeModel, tableModel);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setVisible(settings.isVisible());
		view.addComponentListener(this);
		view.setAccumulateAction(ACCUMULATE_ACTION, this);
		view.setPackingSlipButtonAction(PACKING_SLIP_ACTION, this);
		view.setTreeAction(TREE_ACTION, this);
		view.setTableAction(TABLE_ACTION, this);
//		view.setInsertButtonEnabled(false);
		view.setInsertButtonAction(INSERT_ACTION, this);
		view.setEditButtonEnabled(false);
		view.setEditButtonAction(EDIT_ACTION, this);
		view.setRemoveButtonEnabled(false);
		view.setRemoveButtonAction(REMOVE_ACTION, this);
		
		view.setVisible(true);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		collectApplications();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		
		checkEditButtons();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		logger.trace("Received action=" + e.getActionCommand() + ",source=" + e.getSource() + ",event=" + e);
		
		switch (e.getActionCommand()) {
		case PACKING_SLIP_ACTION:
			logger.warn("Packing Slip Action is not implemented.");
			return;

		case ACCUMULATE_ACTION:
			checkEditButtons();
			collectApplications();
			return;
			
		case EDIT_ACTION: {
			int rowIndex = view.getSelectedTableRow();
			
			if (view.getSelectedTableRow() < 0) {
				return;
			}
			
			ItemApplication application = tableModel.getItems().get(rowIndex);
			ItemApplication original = new ItemApplication(application);
			EditItemApplicationController applicationEditor = new EditItemApplicationController(inventoryAccounting, controller.getDesktopView());
			ItemApplication edited = applicationEditor.editApplication(application);
			
			if (edited == null) {
				return;
			}
			
			if (original.equals(edited)) {
				if (original.getQuantity() != edited.getQuantity()) {
					inventoryAccounting.updateApplication(edited);
					tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
				}
			} else {
				inventoryAccounting.removeApplication(original);
				inventoryAccounting.addApplication(edited);
				tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
			}
			
			return;
		}
			
		case INSERT_ACTION: {
			OrganizationalUnit selectedUnit = getSelectedUnit();
			
			if (selectedUnit == null) {
				return;
			}
			
			ItemApplication application = new ItemApplication(selectedUnit);
			EditItemApplicationController applicationEditor = new EditItemApplicationController(inventoryAccounting, controller.getDesktopView());
			
			application = applicationEditor.editApplication(application);
			
			if (application != null) {
				inventoryAccounting.addApplication(application);
				tableModel.insertItem(application, tableModel.getRowCount());
			}
			
			return;
		}
			
		case REMOVE_ACTION: {
			int rowIndex = view.getSelectedTableRow();
			
			if (view.getSelectedTableRow() < 0) {
				return;
			}
			
			ItemApplication application = tableModel.removeItem(rowIndex);
			
			inventoryAccounting.removeApplication(application);
			return;
		}
		
		default:
			break;
		}
		
		logger.warn("Unknown action event=" + e);
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
		return ItemApplicationView.class.getSimpleName();
	}
	
	@Override
	public JComponent getView() {
		return null;
	}
	
	private void collectApplications() {
		OrganizationalUnit aUnit = getSelectedUnit();
		
		if (aUnit == null) {
			return;
		}
		
		logger.trace("Selected unit=" + aUnit);

		if (view.isAccumulated()) {
			Map<String, AccumulatedApplication> accumulatedApplications = new TreeMap<>();
			for (ItemApplication application : inventoryAccounting.getItemApplications(aUnit, null)) {
				Item item = application.getItem();
				String category = application.getCategory();
				AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
				
				accumulated.addApplication(application);
				accumulatedApplications.put(item.getNumber(), accumulated);
			}
			
			for (OrganizationalUnit subUnit : aUnit.getSubordinates()) {
				for (ItemApplication application : inventoryAccounting.getItemApplications(subUnit, null)) {
					Item item = application.getItem();
					String category = application.getCategory();
					
					if (!accumulatedApplications.containsKey(item.getNumber())) {
						AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
						
						accumulatedApplications.put(item.getNumber(), accumulated);
					}
					
					accumulatedApplications.get(item.getNumber()).addApplication(application);
				}

				for (OrganizationalUnit subSubUnit : subUnit.getSubordinates()) {
					for (ItemApplication application : inventoryAccounting.getItemApplications(subSubUnit, null)) {
						Item item = application.getItem();
						String category = application.getCategory();
						
						if (!accumulatedApplications.containsKey(item.getNumber())) {
							AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
							
							accumulatedApplications.put(item.getNumber(), accumulated);
						}
						
						accumulatedApplications.get(item.getNumber()).addApplication(application);
					}				
				}
			}
			
			tableModel.setItems(new Vector<ItemApplication>(accumulatedApplications.values()));
		} else {
			tableModel.setItems(inventoryAccounting.getItemApplications(aUnit, null));
		}
	}

	private OrganizationalUnit getSelectedUnit() {
		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) view.getSelectedTreeNode();
		
		if (aNode == null) {
			return null;
		}
		
		return (OrganizationalUnit) aNode.getUserObject();
	}
	
	private void checkEditButtons() {
		boolean isRowSelected = false;
		boolean canEdit = !view.isAccumulated();
		
		int rowIndex = view.getSelectedTableRow();

		if (rowIndex >= 0) {
			isRowSelected = true;
		}
		
		view.setRemoveButtonEnabled(isRowSelected && canEdit);
		view.setEditButtonEnabled(isRowSelected && canEdit);
		view.setInsertButtonEnabled(canEdit);
	}
}
