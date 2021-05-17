package se.melsom.warehouse.application.inventory.actual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.common.table.SortableJTable;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Collection;

@Component
public class ActualInventoryView extends JInternalFrame implements AbstractActualInventoryView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(ActualInventoryView.class);

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private ActualInventory presentationModel;

	private JCheckBox extendedEditCheckBox;
	private JComboBox<String> sectionSelector;
	private JComboBox<String> slotSelector;
	private JLabel rackingLabel;
	private JLabel palletLabel;
	private JButton generateReportButton;
	private SortableJTable inventoryTable;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

    public ActualInventoryView() {
		logger.debug("Execute constructor.");
	}

	public String getWindowName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public JInternalFrame getInternalFrame() {
		return this;
	}

	@Override
	public void showView() {
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

		if (settings == null) {
			settings = new WindowBean(21, 33, 778, 264, true);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}
		setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		setVisible(settings.isVisible());
		setVisible(true);
	}

	@Override
	public void initialize(ContentModel tableModel) {
		addComponentListener(this);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Materiellista för vald lagerplats");
		initializeView(tableModel);
		sectionSelector.addActionListener(this::handleSectionSelectorAction);
		slotSelector.addActionListener(this::handleSlotSelectorAction);
		generateReportButton.addActionListener(this::handleGenerateReportAction);
		extendedEditCheckBox.addActionListener(this::handleExtendedEditAction);
		insertButton.addActionListener(this::handleInsertItemAction);
		removeButton.addActionListener(this::handleRemoveItemAction);
		editButton.addActionListener(this::handleEditItemAction);
		inventoryTable.getModel().addTableModelListener(this::handleInventoryTableModelEvent);
		inventoryTable.getSelectionModel().addListSelectionListener(this::handleInventorySelectedEvent);
	}

	@Override
	public void updateState(ViewState state) {
    	logger.debug("Update state ...");
    	state.setUpdating(true);
		generateReportButton.setEnabled(state.isGenerateReportButtonEnabled());
		extendedEditCheckBox.setEnabled(state.isExtendedEditEnabled());
		extendedEditCheckBox.setSelected(state.isExtendedEditSelected());
		editButton.setEnabled(state.isEditButtonEnabled());
		insertButton.setEnabled(state.isInsertButtonEnabled());
		removeButton.setEnabled(state.isRemoveButtonEnabled());
		setSectionSelectorItems(state.getStockLocationSections());
		sectionSelector.setSelectedIndex(state.getCurrentSectionIndex());
		setSlotSelectorItems(state.getStockLocationSlots());
		slotSelector.setSelectedIndex(state.getCurrentSlotIndex());
		state.setUpdating(false);
	}

	private void setSectionSelectorItems(Collection<String> sections) {
		logger.trace("Set section selector items.");
		slotSelector.removeAllItems();
		sectionSelector.removeAllItems();

		for (String item : sections) {
			sectionSelector.addItem(item);
		}
	}

	private void setSlotSelectorItems(Collection<String> slots) {
		logger.trace("Set slot selector items.");
		slotSelector.removeAllItems();

		for (String item : slots) {
			slotSelector.addItem(item);
		}
	}

	private void handleSectionSelectorAction(ActionEvent e) {
    	int sectionIndex = getSelectedSectionIndex();

		if (sectionIndex < 0) {
			return;
		}

		String section = getSelectedSectionItem();

		logger.debug("Section selected=[{}:{}].", sectionIndex, section);
    	presentationModel.handleSectionSelected(sectionIndex, section);
	}

	private void handleSlotSelectorAction(ActionEvent e) {
    	int slotIndex = getSelectedSlotIndex();

		if (slotIndex < 0) {
			return;
		}

		String slot = getSelectedSlotItem();

		logger.debug("Slot selected=[{}:{}].", slotIndex, slot);
		presentationModel.handleSlotSelected(getSelectedSectionItem(), slotIndex, slot);
	}

	private void handleGenerateReportAction(ActionEvent e) {
	}

	private void handleExtendedEditAction(ActionEvent e) {
    	presentationModel.extendedEdit(extendedEditCheckBox.isSelected());
	}

	private void handleInsertItemAction(ActionEvent e) {
		presentationModel.handleInsert();
	}

	private void handleRemoveItemAction(ActionEvent e) {
		presentationModel.handleRemove(getSelectedTableRow());
	}

	private void handleEditItemAction(ActionEvent e) {
    	presentationModel.handleEdit(getSelectedTableRow());
	}

	private void handleInventoryTableModelEvent(TableModelEvent e) {
		int rowIndex = e.getFirstRow();
		int columnIndex = e.getColumn();

		if (rowIndex < 0 || columnIndex < 0) {
			return;
		}

		switch (e.getType()) {
			case TableModelEvent.UPDATE:
				logger.debug("Updating inventory at row={}.", rowIndex);
				break;

			default:
				logger.warn("Unhandled event at row={}, column={}.", rowIndex, columnIndex);
				break;
		}
	}

	private void handleInventorySelectedEvent(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		logger.debug("List section first index={}, last index={}.", e.getFirstIndex(), e.getLastIndex());
		logger.debug("Selected row={}.", getSelectedTableRow());
		presentationModel.itemSelected(getSelectedTableRow());
	}

//	public boolean isExtendedEditEnabled() {
//		return extendedEditCheckBox.isSelected();
//	}

//    public void setExtendedEditEnabled(boolean isEnabled) {
//		extendedEditCheckBox.setSelected(isEnabled);
//	}

//    void setGenerateButtonEnabled(boolean value) {
//		generateReportButton.setEnabled(value);
//	}

    int getSelectedSectionIndex() {
		return sectionSelector.getSelectedIndex();
	}

    String getSelectedSectionItem() {
		return (String) sectionSelector.getSelectedItem();
	}

    int getSelectedSlotIndex() {
		return slotSelector.getSelectedIndex();
	}

    String getSelectedSlotItem() {
		return (String) slotSelector.getSelectedItem();
	}

	public int getSelectedTableRow() {
		return inventoryTable.getSelectedRow();
	}

    private void setSelectedTableRow(int rowIndex) {
		inventoryTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
	}

//    public int getSelectedInventoryColumn() {
//		return inventoryTable.getSelectedColumn();
//	}

//    public void setCellEditor(TableCellEditor editor, int atColumnIndex) {
//		TableColumnModel columnModel = inventoryTable.getColumnModel();
//		TableColumn nameColumn = columnModel.getColumn(atColumnIndex);
//
//		nameColumn.setCellEditor(editor);
//	}

	@Override
	public void componentResized(ComponentEvent event) {
		logger.trace("Component resized [{}].", event);
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}

		if (isVisible()) {
			logger.trace("[{}] width={}, height={}.", getWindowName(), getWidth(), getHeight());
			persistentSettings.setWindowDimension(getWindowName(), getWidth(), getHeight());
		}
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		logger.trace("Component moved [{}].", event);
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}

		if (isVisible()) {
			logger.trace("[{}] x={}, y={}.", getWindowName(), getX(), getY());
			persistentSettings.setWindowLocation(getWindowName(), getX(), getY());
		}
	}

	@Override
	public void componentShown(ComponentEvent event) {
		logger.trace("Component shown [{}].", event);
		logger.trace("[{}] x={}, y={}.", getWindowName(), getX(), getY());
		logger.trace("[{}] width={}, height={}.", getWindowName(), getWidth(), getHeight());
		logger.trace("[{}] isVisible={},.", getWindowName(), isVisible());
	}

	@Override
	public void componentHidden(ComponentEvent event) {
		logger.trace("Component hitten [{}].", event);
		logger.trace("[{}] x={}, y={}.", getWindowName(), getX(), getY());
	}

	private void initializeView(SortableTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controller = new GridBagLayout();
		gbl_controller.columnWidths = new int[] { 0, 52, 0, 52, 0, 0, 0, 0 };
		gbl_controller.rowHeights = new int[] { 29, 0 };
		gbl_controller.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_controller.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		controlPanel.setLayout(gbl_controller);

		slotSelector = new JComboBox<>();

		rackingLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SECTION);
		GridBagConstraints gbc_rackingLabel = new GridBagConstraints();
		gbc_rackingLabel.anchor = GridBagConstraints.WEST;
		gbc_rackingLabel.insets = new Insets(0, 5, 0, 5);
		gbc_rackingLabel.gridx = 0;
		gbc_rackingLabel.gridy = 0;
		controlPanel.add(rackingLabel, gbc_rackingLabel);

		sectionSelector = new JComboBox<>();

		GridBagConstraints gbc_rackingSelector = new GridBagConstraints();
		gbc_rackingSelector.anchor = GridBagConstraints.WEST;
		gbc_rackingSelector.insets = new Insets(0, 0, 0, 5);
		gbc_rackingSelector.gridx = 1;
		gbc_rackingSelector.gridy = 0;
		controlPanel.add(sectionSelector, gbc_rackingSelector);

		palletLabel = new JLabel(EntityName.STOCK_LOCATION_DESIGNATION_SLOT);
		GridBagConstraints gbc_palletLabel = new GridBagConstraints();
		gbc_palletLabel.anchor = GridBagConstraints.WEST;
		gbc_palletLabel.insets = new Insets(0, 0, 0, 5);
		gbc_palletLabel.gridx = 2;
		gbc_palletLabel.gridy = 0;
		controlPanel.add(palletLabel, gbc_palletLabel);

		GridBagConstraints gbc_palletSelector = new GridBagConstraints();
		gbc_palletSelector.anchor = GridBagConstraints.WEST;
		gbc_palletSelector.insets = new Insets(0, 0, 0, 5);
		gbc_palletSelector.gridx = 3;
		gbc_palletSelector.gridy = 0;
		controlPanel.add(slotSelector, gbc_palletSelector);

		generateReportButton = new JButton("Ta ut lista...");

		extendedEditCheckBox = new JCheckBox("Utökad redigering");
		String toolTipText = "";

		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att lägga till och ta bort rader.";
		toolTipText += "</html>";

		extendedEditCheckBox.setToolTipText(toolTipText);

		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.EAST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxNewCheckBox.gridx = 5;
		gbc_chckbxNewCheckBox.gridy = 0;
		controlPanel.add(extendedEditCheckBox, gbc_chckbxNewCheckBox);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 0;
		controlPanel.add(generateReportButton, gbc_btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		inventoryTable = new SortableJTable(tableModel);
		scrollPane.setViewportView(inventoryTable);
		editPanel = new JPanel();

		FlowLayout flowLayout = (FlowLayout) editPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(editPanel, BorderLayout.SOUTH);

		removeButton = new JButton("Ta bort");
		editPanel.add(removeButton);

		editButton = new JButton("Redigera...");
		editPanel.add(editButton);

		insertButton = new JButton("Lägg till...");
		editPanel.add(insertButton);
	}
}
