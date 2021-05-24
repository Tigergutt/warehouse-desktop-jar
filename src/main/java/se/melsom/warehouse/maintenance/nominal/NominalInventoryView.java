package se.melsom.warehouse.maintenance.nominal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@Component
public class NominalInventoryView extends JInternalFrame implements AbstractNominalInventoryView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(NominalInventoryView.class);

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private NominalInventory nominalInventory;

	private JPanel controlPanel;
	private JTable instancesTable;
	private JCheckBox extendedEditCheckBox;
	private JPanel editPanel;
	private JButton insertButton;
	private JButton removeButton;
	private JButton editButton;

    public NominalInventoryView() {
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
		setIconifiable(false);
		addComponentListener(this);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Registervård: Artikellista");
		initializeView(tableModel);
		extendedEditCheckBox.addActionListener(this::handleExtendedEditAction);
		instancesTable.getSelectionModel().addListSelectionListener(this::handleItemSelected);
		editButton.addActionListener(this::handleEditAction);
		insertButton.addActionListener(this::handleInsertAction);
		removeButton.addActionListener(this::handleRemoveAction);
	}

	@Override
	public void updateState(ViewState state) {
		extendedEditCheckBox.setEnabled(state.isExtendedEditEnabled());
		extendedEditCheckBox.setSelected(state.isExtendedEditSelected());
		editButton.setEnabled(state.isEditButtonEnabled());
		insertButton.setEnabled(state.isInsertButtonEnabled());
		removeButton.setEnabled(state.isRemoveButtonEnabled());
	}

	private void handleExtendedEditAction(ActionEvent actionEvent) {
		nominalInventory.updateExtendedEdit(extendedEditCheckBox.isSelected());
	}

	private void handleItemSelected(ListSelectionEvent listSelectionEvent) {
	}

	private void handleRemoveAction(ActionEvent actionEvent) {
	}

	private void handleInsertAction(ActionEvent actionEvent) {
    	nominalInventory.insertNominalInventory();
	}

	private void handleEditAction(ActionEvent actionEvent) {
	}

	boolean isExtendedEditEnabled() {
		return extendedEditCheckBox.isSelected();
	}

    void setExtendedEditEnabled(boolean isEnabled) {
		extendedEditCheckBox.setSelected(isEnabled);
	}

    void setExtendedEditAction(String name, ActionListener listener) {
		extendedEditCheckBox.setActionCommand(name);
		extendedEditCheckBox.addActionListener(listener);
	}

	int getSelectedTableRow() {
		return instancesTable.getSelectedRow();
	}

    void setSelectedTableRow(int rowIndex) {
		instancesTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		instancesTable.scrollRectToVisible(new Rectangle(instancesTable.getCellRect(rowIndex, 0, true)));
	}

    void setInstanceTableAction(String name, ListSelectionListener listener) {
		instancesTable.setName(name);
		instancesTable.getSelectionModel().addListSelectionListener(listener);
	}

	void setEditButtonEnabled(boolean isEnabled) {
		editButton.setEnabled(isEnabled);
	}

    void setEditButtonAction(String name, ActionListener listener) {
		editButton.setActionCommand(name);
		editButton.addActionListener(listener);
	}

	void setInsertButtonEnabled(boolean isEnabled) {
		insertButton.setEnabled(isEnabled);
	}

    void setInsertButtonAction(String name, ActionListener listener) {
		insertButton.setActionCommand(name);
		insertButton.addActionListener(listener);
	}

	public void setRemoveButtonEnabled(boolean isEnabled) {
		removeButton.setEnabled(isEnabled);
	}

    public void setRemoveButtonAction(String name, ActionListener listener) {
		removeButton.setActionCommand(name);
		removeButton.addActionListener(listener);
	}

	public String getWindowName() {
		return NominalInventoryView.class.getSimpleName();
	}

	private void initializeView(AbstractTableModel contentModel) {
		controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);

		extendedEditCheckBox = new JCheckBox("Redigera");
		String toolTipText = "";

		toolTipText += "<html>";
		toolTipText += "Utökad redigering innebär möjlighet att redigera F-beteckning,<br/>";
		toolTipText += "F-benämning, och även lägga till och ta bort rader.";
		toolTipText += "</html>";

		extendedEditCheckBox.setToolTipText(toolTipText);
		// TODO:
//		extendedEditCheckBox.addActionListener(controller);
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		controlPanel.add(extendedEditCheckBox);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		instancesTable = new JTable(contentModel) {
			public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				java.awt.Component returnComp = super.prepareRenderer(renderer, row, column);
				if (!returnComp.getBackground().equals(getSelectionBackground())) {
					Color bg = (row % 2 == 0 ? EntityName.TABLE_EVEN_ROW_COLOR : EntityName.TABLE_ODD_ROW_COLOR);
					returnComp.setBackground(bg);
				}
				return returnComp;
			}
		};

		scrollPane.setViewportView(instancesTable);

		TableColumnModel columnModel = instancesTable.getColumnModel();
		columnModel.setColumnMargin(5);
		instancesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			columnModel.getColumn(columnIndex).setPreferredWidth(ContentModel.columnWidts[columnIndex]);
		}

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
