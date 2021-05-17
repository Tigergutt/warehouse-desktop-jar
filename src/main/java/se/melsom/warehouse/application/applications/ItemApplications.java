package se.melsom.warehouse.application.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.edit.application.EditItemApplication;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.model.entity.AccumulatedApplication;
import se.melsom.warehouse.model.entity.Item;
import se.melsom.warehouse.model.entity.ItemApplication;
import se.melsom.warehouse.model.entity.OrganizationalUnit;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

@Component
public class ItemApplications extends AbstractPresentationModel {
	private static final Logger logger = LoggerFactory.getLogger(ItemApplications.class);

    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    public static final String PACKING_SLIP_ACTION = "PackingSlipAction";
    public static final String TREE_ACTION = "TreeAction";
    public static final String TABLE_ACTION = "TableAction";
    public static final String ACCUMULATE_ACTION = "AccAction";
    public static final String INSERT_ACTION = "InsertAction";
    public static final String EDIT_ACTION = "EditAction";
    public static final String REMOVE_ACTION = "RemoveAction";

	private ApplicationPresentationModel controller;
	private InventoryAccounting inventoryAccounting;
	private TreeModel treeModel;
	private MutableTreeNode rootNode;
	private ContentModel tableModel;

	@Autowired private ItemApplicationsView view;
	@Autowired private PersistentSettings persistentSettings;

    public ItemApplications() {
		logger.debug("Executing constructor.");
	}

	@Override
	public void initialize() {
//		rootNode = new DefaultMutableTreeNode();
//
////		UnitsMasterFile unitsMasterfile = inventoryAccounting.getUnitsMasterFile();
////
////		for (OrganizationalUnit aUnit : unitsMasterfile.getUnits()) {
////			if (aUnit.getLevel() == 0) {
////				logger.debug("Root object=" + aUnit);
////				rootNode.setUserObject(aUnit);
////
////				for (int subordinateIndex = 0; subordinateIndex < aUnit.getSubordinates().size(); subordinateIndex++) {
////					OrganizationalUnit subordinate = aUnit.getSubordinates().get(subordinateIndex);
////					DefaultMutableTreeNode subordinateNode = new DefaultMutableTreeNode(subordinate);
////
////					rootNode.insert(subordinateNode, subordinateIndex);
////
////					for (int subSubordinateIndex = 0; subSubordinateIndex < subordinate.getSubordinates().size(); subSubordinateIndex++) {
////						OrganizationalUnit subSubordinate = subordinate.getSubordinates().get(subSubordinateIndex);
////						DefaultMutableTreeNode subSubordinateNode = new DefaultMutableTreeNode(subSubordinate);
////
////						subordinateNode.insert(subSubordinateNode, subSubordinateIndex);
////					}
////				}
////				break;
////			}
////		}
//
//		treeModel = new DefaultTreeModel(rootNode);
//		tableModel = new ContentModel();
//
//		tableModel.addTableModelListener(this);
//
//		view = new ItemApplicationsView();
//		view.initializeView(controller.getDesktopView(),this, treeModel, tableModel);
//		view.addComponentListener(this);
//		view.setAccumulateAction(ACCUMULATE_ACTION, this);
////		view.setPackingSlipButtonAction(PACKING_SLIP_ACTION, this);
////		view.setTreeAction(TREE_ACTION, this);
//		view.setTableAction(TABLE_ACTION, this);
////		view.setInsertButtonEnabled(false);
//		view.setInsertButtonAction(INSERT_ACTION, this);
//		view.setEditButtonEnabled(false);
//		view.setEditButtonAction(EDIT_ACTION, this);
//		view.setRemoveButtonEnabled(false);
//		view.setRemoveButtonAction(REMOVE_ACTION, this);
//
//		view.setVisible(true);
	}

//	@Override
//	public void valueChanged(TreeSelectionEvent e) {
//		collectApplications();
//	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		checkEditButtons();
	}

	@Override
	public void showView() {

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
			EditItemApplication applicationEditor = new EditItemApplication();
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
			EditItemApplication applicationEditor = new EditItemApplication();

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
