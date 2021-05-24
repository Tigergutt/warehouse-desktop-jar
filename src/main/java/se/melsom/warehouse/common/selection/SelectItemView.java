package se.melsom.warehouse.common.selection;

import se.melsom.warehouse.common.table.SortableJTable;
import se.melsom.warehouse.common.table.SortableTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SelectItemView extends JDialog {
	private JTextField searchField;
	private SortableJTable itemTable;
	private JButton cancelButton;
	private JButton acceptButton;

	private final SelectItem selectItem;

    public SelectItemView(SelectItem selectItem, SortableTableModel tableModel, Dialog parentView) {
		super(parentView, true);

		this.selectItem = selectItem;

		initializeView(tableModel);
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				handleSearchAction(null);
			}
		});
		searchField.addActionListener(this::handleSearchAction);
		itemTable.getSelectionModel().addListSelectionListener(this::handleListSelectionEvent);
		cancelButton.addActionListener(this::handleCancelAction);
		acceptButton.addActionListener(this::handleAcceptAction);
	}

	private void handleSearchAction(ActionEvent actionEvent) {
		String searchKey = searchField.getText().toUpperCase();

		if (searchKey.length() > 1) {
			selectItem.search(searchKey);
		}
	}

	private void handleListSelectionEvent(ListSelectionEvent listSelectionEvent) {
    	selectItem.listItemSelected(itemTable.getSelectedRow());
	}

	private void handleCancelAction(ActionEvent actionEvent) {
    	dispose();
	}

	private void handleAcceptAction(ActionEvent actionEvent) {
    	dispose();
	}

	public void setSelectedItemRowIndex(int rowIndex) {
		itemTable.getSelectionModel().clearSelection();
		
		if (rowIndex >= 0 && rowIndex < itemTable.getRowCount()) {
			itemTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
			itemTable.scrollRectToVisible(new Rectangle(itemTable.getCellRect(rowIndex, 0, true)));
		}
	}

    public void setAcceptButtonEnabled(boolean b) {
		acceptButton.setEnabled(b);
	}

	private void initializeView(SortableTableModel tableModel) {
		setTitle("Välj en artikel");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel searchPanel = new JPanel();
		FlowLayout searchPanelLayout = (FlowLayout) searchPanel.getLayout();
		searchPanelLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(searchPanel, BorderLayout.NORTH);

		JLabel searchFieldLabel = new JLabel("Sökord");
		searchPanel.add(searchFieldLabel);

		searchField = new JTextField();
		String toolTipText = "";

		toolTipText += "<html>";
		toolTipText += "<b>Sök fram lämplig artikel ur artikellistan genom att mata in sökord.</b>";
		toolTipText += "Systemet plockar fram matchande artiklar utifrån artikelnummer och/eller -namn.";
		toolTipText += "Sökningen aktiveras så fort du börjar mata in text i fältet.";
		toolTipText += "</html>";

		searchField.setToolTipText(toolTipText);
		searchPanel.add(searchField);
		searchField.setColumns(20);

		itemTable = new SortableJTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(itemTable);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = (FlowLayout) buttonPanel.getLayout();
		buttonPanelLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		acceptButton = new JButton("Ok");
		buttonPanel.add(acceptButton);

		cancelButton = new JButton("Avbryt");
		buttonPanel.add(cancelButton);
		pack();
	}
}
