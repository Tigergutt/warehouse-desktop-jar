package se.melsom.warehouse.maintenance.applications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.AbstractPresentationModel;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.application.desktop.AbstractDesktopView;
import se.melsom.warehouse.edit.application.EditItemApplication;
import se.melsom.warehouse.data.vo.ItemApplicationVO;
import se.melsom.warehouse.data.vo.UnitVO;
import se.melsom.warehouse.edit.application.EditItemApplicationListener;
import se.melsom.warehouse.settings.PersistentSettings;

import javax.swing.event.ListSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;

@Component
public class ItemApplications extends AbstractPresentationModel implements EditItemApplicationListener {
	private static final Logger logger = LoggerFactory.getLogger(ItemApplications.class);

	@Autowired private ItemApplicationsView view;
	@Autowired private PersistentSettings persistentSettings;
	@Autowired private AbstractDesktopView desktopView;

	public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    public static final String PACKING_SLIP_ACTION = "PackingSlipAction";
    public static final String TREE_ACTION = "TreeAction";
    public static final String TABLE_ACTION = "TableAction";
    public static final String ACCUMULATE_ACTION = "AccAction";
    public static final String INSERT_ACTION = "InsertAction";
    public static final String EDIT_ACTION = "EditAction";
    public static final String REMOVE_ACTION = "RemoveAction";

	private ApplicationPresentationModel controller;
	private TreeModel treeModel;
	private MutableTreeNode rootNode;
	private ContentModel tableModel;

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

			ItemApplicationVO application = tableModel.getItems().get(rowIndex);
			ItemApplicationVO original = new ItemApplicationVO(application);
			EditItemApplication applicationEditor = new EditItemApplication(desktopView.getFrame());
			ItemApplicationVO edited = applicationEditor.editApplication(application, this);

			if (edited == null) {
				return;
			}

//			if (original.equals(edited)) {
//				if (original.getQuantity() != edited.getQuantity()) {
//					inventoryAccounting.updateApplication(edited);
//					tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
//				}
//			} else {
//				inventoryAccounting.removeApplication(original);
//				inventoryAccounting.addApplication(edited);
//				tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
//			}

			return;
		}

		case INSERT_ACTION: {
			UnitVO selectedUnit = getSelectedUnit();

			if (selectedUnit == null) {
				return;
			}

			ItemApplicationVO application = new ItemApplicationVO(selectedUnit);
			EditItemApplication applicationEditor = new EditItemApplication(desktopView.getFrame());

			application = applicationEditor.editApplication(application, this);

//			if (application != null) {
//				inventoryAccounting.addApplication(application);
//				tableModel.insertItem(application, tableModel.getRowCount());
//			}

			return;
		}

		case REMOVE_ACTION: {
			int rowIndex = view.getSelectedTableRow();

			if (view.getSelectedTableRow() < 0) {
				return;
			}

			ItemApplicationVO application = tableModel.removeItem(rowIndex);

//			inventoryAccounting.removeApplication(application);
			return;
		}

		default:
			break;
		}

		logger.warn("Unknown action event=" + e);
	}

	@Override
	public void save(ItemApplicationVO itemApplication) {
	}

	private void collectApplications() {
		UnitVO aUnit = getSelectedUnit();

		if (aUnit == null) {
			return;
		}

		logger.trace("Selected unit=" + aUnit);

		if (view.isAccumulated()) {
//			Map<String, AccumulatedApplication> accumulatedApplications = new TreeMap<>();
//			for (ItemApplication application : inventoryAccounting.getItemApplications(aUnit, null)) {
//				Item item = application.getItem();
//				String category = application.getCategory();
//				AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
//
//				accumulated.addApplication(application);
//				accumulatedApplications.put(item.getNumber(), accumulated);
//			}

//			for (OrganizationalUnit subUnit : aUnit.getSubordinates()) {
//				for (ItemApplication application : inventoryAccounting.getItemApplications(subUnit, null)) {
//					Item item = application.getItem();
//					String category = application.getCategory();
//
//					if (!accumulatedApplications.containsKey(item.getNumber())) {
//						AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
//
//						accumulatedApplications.put(item.getNumber(), accumulated);
//					}
//
//					accumulatedApplications.get(item.getNumber()).addApplication(application);
//				}
//
//				for (OrganizationalUnit subSubUnit : subUnit.getSubordinates()) {
//					for (ItemApplication application : inventoryAccounting.getItemApplications(subSubUnit, null)) {
//						Item item = application.getItem();
//						String category = application.getCategory();
//
//						if (!accumulatedApplications.containsKey(item.getNumber())) {
//							AccumulatedApplication accumulated = new AccumulatedApplication(item, category);
//
//							accumulatedApplications.put(item.getNumber(), accumulated);
//						}
//
//						accumulatedApplications.get(item.getNumber()).addApplication(application);
//					}
//				}
//			}

//			tableModel.setItems(new Vector<ItemApplicationVO>(accumulatedApplications.values()));
		} else {
//			tableModel.setItems(inventoryAccounting.getItemApplications(aUnit, null));
		}
	}

	private UnitVO getSelectedUnit() {
		DefaultMutableTreeNode aNode = (DefaultMutableTreeNode) view.getSelectedTreeNode();

		if (aNode == null) {
			return null;
		}

		return (UnitVO) aNode.getUserObject();
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
