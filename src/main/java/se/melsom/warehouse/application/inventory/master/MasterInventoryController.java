package se.melsom.warehouse.application.inventory.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.melsom.warehouse.application.ApplicationPresentationModel;
import se.melsom.warehouse.data.vo.NominalInventoryVO;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;

@Deprecated
public class MasterInventoryController extends ViewController implements TableModelListener, ListSelectionListener {
	private static final Logger logger = LoggerFactory.getLogger(MasterInventoryController.class);

    public static final String EXTENDED_EDIT_ACTION = "ExtendedEdit";
    public static final String SELECT_ACTION = "SelectAction";
    public static final String INSERT_ACTION = "InsertAction";
    public static final String EDIT_ACTION = "EditAction";
    public static final String REMOVE_ACTION = "RemoveAction";

	private final ApplicationPresentationModel controller;
	private InventoryAccounting inventoryAccounting;
	private final ContentModel tableModel;
	private MasterInventoryView view;

	@Autowired
	private PersistentSettings persistentSettings;

    public MasterInventoryController(ApplicationPresentationModel controller) {
		this.controller = controller;

		// inventoryAccounting = controller.getInventoryAccounting();

		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowBean(120, 93, 619, 260, false);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
		
		tableModel = new ContentModel();
		tableModel.addTableModelListener(this);

//		view = new MasterInventoryView(controller.getDesktopView(), this, tableModel);
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
				
//		tableModel.setInstances(inventoryAccounting.getMasterInventory(EntityName.NULL_ID, null));
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
			break;
		}
			
		case EDIT_ACTION: {
			break;
		}
		
		case REMOVE_ACTION:
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
			NominalInventoryVO updated = tableModel.getInstances().get(rowIndex);
//			inventoryAccounting.updateInventory(updated);
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
		persistentSettings.setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JDialog == false) {
			return;
		}
		
		JDialog frame = (JDialog) event.getSource();
		persistentSettings.setWindowLocation(getWindowName(), frame.getX(), frame.getY());
	}

    public String getWindowName() {
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
