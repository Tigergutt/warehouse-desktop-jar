package se.melsom.warehouse.application.inventory.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.common.table.SortableJTable;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@Component
public class ItemMaintenanceView extends JInternalFrame implements AbstractItemMaintenanceView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(ItemMaintenanceView.class);

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private ItemMaintenance presentationModel;

	private SortableJTable itemsTable;
	private JButton generateReportButton;
	private JCheckBox extendedEditCheckBox;
	private JPanel editPanel;
	private JButton editButton;
	private JButton insertButton;
	private JButton removeButton;

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
		setIconifiable(true);
		addComponentListener(this);
		setClosable(true);
		setResizable(true);
		setTitle("Registervård: Artikellista");
		initializeView(tableModel);
		generateReportButton.addActionListener(this::handleGenerateReportAction);
		extendedEditCheckBox.addActionListener(this::handleExtendedEditAction);
		editButton.addActionListener(this::handleEditAction);
		insertButton.addActionListener(this::handleInsertAction);
		removeButton.addActionListener(this::handleRemoveAction);
		itemsTable.getSelectionModel().addListSelectionListener(this::handleItemSelected);
	}

	@Override
	public void updateState(ViewState state) {
    	extendedEditCheckBox.setEnabled(state.isExtendedEditEnabled());
		extendedEditCheckBox.setSelected(state.isExtendedEditSelected());
		generateReportButton.setEnabled(state.isGenerateReportButtonEnabled());
		editButton.setEnabled(state.isEditButtonEnabled());
		insertButton.setEnabled(state.isInsertButtonEnabled());
		removeButton.setEnabled(state.isRemoveButtonEnabled());
	}

	private void handleGenerateReportAction(ActionEvent actionEvent) {
		presentationModel.handleGenerateReport();
	}

	private void handleExtendedEditAction(ActionEvent actionEvent) {
		presentationModel.handleExtendedEdit(extendedEditCheckBox.isSelected());
	}

	private void handleEditAction(ActionEvent actionEvent) {
		presentationModel.handleEditItem();
	}

	private void handleInsertAction(ActionEvent actionEvent) {
		presentationModel.handleInsertItem();
	}

	private void handleRemoveAction(ActionEvent actionEvent) {
		presentationModel.handleRemoveItem();
	}

	private void handleItemSelected(ListSelectionEvent event) {
		if (event.getValueIsAdjusting()) {
			return;
		}

		presentationModel.handleRowSelected(itemsTable.getSelectedRow());
	}


//	public void setExtendedEditButtonAction(String name, ActionListener listener) {
//		extendedEditCheckBox.setActionCommand(name);
//		extendedEditCheckBox.addActionListener(listener);
//	}

//	void setGenerateReportAction(String name, ActionListener listener) {
//		generateReportButton.setActionCommand(name);
//		generateReportButton.addActionListener(listener);
//	}

//    void setGenerateButtonEnabled(boolean value) {
//		logger.trace("Set generate report button enabled=" + value);
//		generateReportButton.setEnabled(value);
//	}

//	void setSelectedItemAction(String name, ListSelectionListener listener) {
//		itemsTable.setName(name);
//		itemsTable.getSelectionModel().addListSelectionListener(listener);;
//	}

//    public int getSelectedProductTableRow() {
//		return itemsTable.getSelectedRow();
//	}

//    public int getSelectedArticleTableColumn() {
//		return itemsTable.getSelectedColumn();
//	}

//	public int getSelectedItemRow() {
//		return itemsTable.getSelectedRow();
//	}

    public void setSelectedItemRow(int rowIndex) {
		itemsTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		itemsTable.scrollRectToVisible(new Rectangle(itemsTable.getCellRect(rowIndex, 0, true)));
	}

    public int getSelectedArticleColumn() {
		return itemsTable.getSelectedColumn();
	}

//	public void setEditButtonEnabled(boolean isEnabled) {
//		editButton.setEnabled(isEnabled);
//	}

//    public void setEditButtonAction(String name, ActionListener listener) {
//		editButton.setActionCommand(name);
//		editButton.addActionListener(listener);
//	}

//	public void setInsertButtonEnabled(boolean isEnabled) {
//		insertButton.setEnabled(isEnabled);
//	}

//    public void setInsertButtonAction(String name, ActionListener listener) {
//		insertButton.setActionCommand(name);
//		insertButton.addActionListener(listener);
//	}

//	public void setRemoveButtonEnabled(boolean isEnabled) {
//		removeButton.setEnabled(isEnabled);
//	}

//   public void setRemoveButtonAction(String name, ActionListener listener) {
//		removeButton.setActionCommand(name);
//		removeButton.addActionListener(listener);
//	}

	private void initializeView(SortableTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{0, 0, 119, 0};
		gbl_controlPanel.rowHeights = new int[]{29, 0};
		gbl_controlPanel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controlPanel);
		
		generateReportButton = new JButton("Ta ut lista...");

		extendedEditCheckBox = new JCheckBox("Redigering");
		String toolTipText = "";
		
		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att redigera F-beteckning,<br/>";
		toolTipText += "F-benämning, och även lägga till och ta bort rader.";
		toolTipText += "</html>";
		
		extendedEditCheckBox.setToolTipText(toolTipText);
		GridBagConstraints gbc_extendedEditCheckBox = new GridBagConstraints();
		gbc_extendedEditCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_extendedEditCheckBox.anchor = GridBagConstraints.EAST;
		gbc_extendedEditCheckBox.gridx = 1;
		gbc_extendedEditCheckBox.gridy = 0;
		controlPanel.add(extendedEditCheckBox, gbc_extendedEditCheckBox);

		GridBagConstraints gbc_generateReportButton = new GridBagConstraints();
		gbc_generateReportButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_generateReportButton.gridx = 2;
		gbc_generateReportButton.gridy = 0;
		controlPanel.add(generateReportButton, gbc_generateReportButton);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		itemsTable = new SortableJTable(tableModel);
		
		scrollPane.setViewportView(itemsTable);

		editPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) editPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(editPanel, BorderLayout.SOUTH);
		
		removeButton = new JButton("Ta bort");
		editPanel.add(removeButton);

		editButton = new JButton("Redigera...");
		editPanel.add(editButton);
		
		insertButton = new JButton("Lägg till ny...");
		editPanel.add(insertButton);
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
