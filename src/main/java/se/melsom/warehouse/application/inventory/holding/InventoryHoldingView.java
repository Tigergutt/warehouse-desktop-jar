package se.melsom.warehouse.application.inventory.holding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.common.table.SortableJTable;
import se.melsom.warehouse.common.table.SortableTableModel;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Collection;

@Component
public class InventoryHoldingView extends JInternalFrame implements AbstractInventoryHoldingView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(InventoryHoldingView.class);

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private InventoryHolding presentationModel;

	private JCheckBox extendedEditCheckBox;
	private JComboBox<String> superiorUnitSelector;
	private JComboBox<String> unitSelector;
	private JLabel superiorUnitLabel;
	private JLabel unitLabel;
	private JButton generateReportButton;
	private SortableJTable inventoryTable;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

    public InventoryHoldingView() {
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
		setTitle("Materiellista för vald enhet");

		initializeView(tableModel);
		initializeListeners();
	}

	@Override
	public void updateState(ViewState state) {
    	state.setUpdating(true);
		setSuperiorUnitSelectorItems(state.getSuperiors());
		setUnitSelectorItems(state.getUnits());
		superiorUnitSelector.setSelectedIndex(state.getSelectedSuperiorUnitIndex());
		unitSelector.setSelectedIndex(state.getSelectedUnitIndex());
		extendedEditCheckBox.setEnabled(state.isExtendedEditEnabled());
    	extendedEditCheckBox.setSelected(state.isExtendedEditSelected());
    	generateReportButton.setEnabled(state.isGenerateReportEnabled());
    	insertButton.setEnabled(state.isInsertButtonEnabled());
    	removeButton.setEnabled(state.isRemoveButtonEnabled());
    	editButton.setEnabled(state.isEditButtonEnabled());
    	state.setUpdating(false);
	}

	public String getWindowName() {
		return this.getClass().getSimpleName();
	}

	private void handleExtendedEditAction(ActionEvent actionEvent) {
    	presentationModel.setExtendedEditEnabled(extendedEditCheckBox.isSelected());
	}

	private void handleSuperiorUnitSelectedAction(ActionEvent actionEvent) {
    	int index = superiorUnitSelector.getSelectedIndex();
    	String item = (String) superiorUnitSelector.getSelectedItem();

    	presentationModel.selectSuperiorUnit(index, item);
	}

	private void handleUnitSelectedAction(ActionEvent actionEvent) {
		String superiorItem = (String) superiorUnitSelector.getSelectedItem();
		int index = unitSelector.getSelectedIndex();
		String item = (String) unitSelector.getSelectedItem();

    	presentationModel.selectSubordinateUnit(superiorItem, index, item);
	}

	private void handleGenerateReportAction(ActionEvent actionEvent) {
    	presentationModel.generateReport();
	}

	private void handleEditAction(ActionEvent actionEvent) {
    	presentationModel.edit();
	}

	private void handleListSelectionEvent(ListSelectionEvent listSelectionEvent) {
    	presentationModel.selectItem(inventoryTable.getSelectedRow());
	}

	private void handleInsertAction(ActionEvent actionEvent) {
		presentationModel.insert();
	}

	private void handleRemoveAction(ActionEvent actionEvent) {
		presentationModel.remove();
	}

	public boolean isExtendedEditEnabled() {
		return extendedEditCheckBox.isSelected();
	}

    public void setExtendedEditEnabled(boolean isEnabled) {
		extendedEditCheckBox.setSelected(isEnabled);
	}

	public void setSuperiorUnitSelectorItems(Collection<String> units) {
		logger.trace("Set section selector items.");
		unitSelector.removeAllItems();
		superiorUnitSelector.removeAllItems();

		for (String item : units) {
			superiorUnitSelector.addItem(item);
		}
	}

	public void setUnitSelectorItems(Collection<String> slots) {
		logger.trace("Set slot selector items.");
		unitSelector.removeAllItems();

		for (String item : slots) {
			unitSelector.addItem(item);
		}
	}

    public String getSelectedUnitItem() {
		return (String) unitSelector.getSelectedItem();
	}

	public int getSelectedTableRow() {
		return inventoryTable.getSelectedRow();
	}

    public void setSelectedTableRow(int rowIndex) {
		inventoryTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
	}

    public int getSelectedInventoryColumn() {
		return inventoryTable.getSelectedColumn();
	}

    public void setCellEditor(TableCellEditor editor, int atColumnIndex) {
		TableColumnModel columnModel = inventoryTable.getColumnModel();
		TableColumn nameColumn = columnModel.getColumn(atColumnIndex);
		
		nameColumn.setCellEditor(editor);
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

		superiorUnitLabel = new JLabel(EntityName.ORGANIZATIONAL_SUPERIOR_UNIT);
		GridBagConstraints gbc_superiorUnitLabel = new GridBagConstraints();
		gbc_superiorUnitLabel.anchor = GridBagConstraints.WEST;
		gbc_superiorUnitLabel.insets = new Insets(0, 5, 0, 5);
		gbc_superiorUnitLabel.gridx = 0;
		gbc_superiorUnitLabel.gridy = 0;
		controlPanel.add(superiorUnitLabel, gbc_superiorUnitLabel);

		superiorUnitSelector = new JComboBox<>();

		GridBagConstraints gbc_superiorUnitSelector = new GridBagConstraints();
		gbc_superiorUnitSelector.anchor = GridBagConstraints.WEST;
		gbc_superiorUnitSelector.insets = new Insets(0, 0, 0, 5);
		gbc_superiorUnitSelector.gridx = 1;
		gbc_superiorUnitSelector.gridy = 0;
		controlPanel.add(superiorUnitSelector, gbc_superiorUnitSelector);

		unitLabel = new JLabel(EntityName.ORGANIZATIONAL_UNIT);
		
		GridBagConstraints gbc_unitLabel = new GridBagConstraints();
		gbc_unitLabel.anchor = GridBagConstraints.WEST;
		gbc_unitLabel.insets = new Insets(0, 0, 0, 5);
		gbc_unitLabel.gridx = 2;
		gbc_unitLabel.gridy = 0;
		controlPanel.add(unitLabel, gbc_unitLabel);

		unitSelector = new JComboBox<>();

		GridBagConstraints gbc_unitSelector = new GridBagConstraints();
		gbc_unitSelector.anchor = GridBagConstraints.WEST;
		gbc_unitSelector.insets = new Insets(0, 0, 0, 5);
		gbc_unitSelector.gridx = 3;
		gbc_unitSelector.gridy = 0;
		controlPanel.add(unitSelector, gbc_unitSelector);

		generateReportButton = new JButton("Ta ut lista...");
		generateReportButton.setEnabled(false);
		
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

	private void initializeListeners() {
		extendedEditCheckBox.addActionListener(this::handleExtendedEditAction);
		superiorUnitSelector.addActionListener(this::handleSuperiorUnitSelectedAction);
		unitSelector.addActionListener(this::handleUnitSelectedAction);
		generateReportButton.addActionListener(this::handleGenerateReportAction);
		inventoryTable.getSelectionModel().addListSelectionListener(this::handleListSelectionEvent);
		insertButton.addActionListener(this::handleInsertAction);
		removeButton.addActionListener(this::handleRemoveAction);
		editButton.addActionListener(this::handleEditAction);
	}

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
}
