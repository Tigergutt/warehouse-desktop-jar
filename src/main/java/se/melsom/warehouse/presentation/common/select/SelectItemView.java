package se.melsom.warehouse.presentation.common.select;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;

import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SelectItemView extends JDialog {
	private JTextField searchField;
	private SortedTable itemTable;
	private JButton cancelButton;
	private JButton acceptButton;
	
	public SelectItemView(SortedTableModel tableModel, JFrame parent) {
		super(parent, true);
		setTitle("Völj en artikel");
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
		
		itemTable = new SortedTable(tableModel);

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
	}

	public void setSearchFieldListener(String actionCommand, ViewController actionListener) {
		searchField.setActionCommand(actionCommand);
		searchField.addActionListener(actionListener);
		searchField.addKeyListener(actionListener);
	}

	public void setItemTableListener(String actionCommand, ListSelectionListener actionListener) {
		itemTable.setName(actionCommand);
		itemTable.getSelectionModel().addListSelectionListener(actionListener);
	}
	
	public int getSelectedTableRowIndex() {
		return itemTable.getSelectedRow();
	}
	
	public void setSelectedItemRowIndex(int rowIndex) {
		itemTable.getSelectionModel().clearSelection();
		
		if (rowIndex >= 0 && rowIndex < itemTable.getRowCount()) {
			itemTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
			itemTable.scrollRectToVisible(new Rectangle(itemTable.getCellRect(rowIndex, 0, true)));
		}
	}
	
	public void setCancelButtonListener(String actionCommand, ActionListener actionListener) {
		cancelButton.setActionCommand(actionCommand);
		cancelButton.addActionListener(actionListener);
	}

	public void setAcceptButtonListener(String actionCommand, ActionListener actionListener) {
		acceptButton.setActionCommand(actionCommand);
		acceptButton.addActionListener(actionListener);
	}

	public void setAcceptButtonEnabled(boolean b) {
		acceptButton.setEnabled(b);
	}
}
