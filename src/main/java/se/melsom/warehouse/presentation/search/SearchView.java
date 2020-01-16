package se.melsom.warehouse.presentation.search;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;

@SuppressWarnings("serial")
public class SearchView extends JInternalFrame {
	private static Logger logger = Logger.getLogger(SearchView.class);
	private SearchController controller;
	private SortedTable equipmentTable;
	private JTextField searchKeyTextField;
	private JButton generateReportButton;
	
	public SearchView(SearchController controller, SortedTableModel tableModel) {
		this.controller = controller;
		
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Fritextsökning");
		
		initislizeView(tableModel);
	}
	
	private void initislizeView(SortedTableModel tableModel) {
		JPanel controlPanel = new JPanel();
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		GridBagLayout gbl_controller = new GridBagLayout();
		gbl_controller.columnWidths = new int[]{44, 130, 0, 0, 0};
		gbl_controller.rowHeights = new int[]{26, 0};
		gbl_controller.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_controller.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controller);
		
		JLabel lblNewLabel = new JLabel("Sökord");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 5, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		controlPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		searchKeyTextField = new JTextField();
		searchKeyTextField.setToolTipText("Ange sökord här. Använd * för wildcardsökning.");		
		searchKeyTextField.addActionListener(controller);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		controlPanel.add(searchKeyTextField, gbc_textField);
		searchKeyTextField.setColumns(16);
		
		generateReportButton = new JButton("Ta ut lista...");
		generateReportButton.setEnabled(false);
		generateReportButton.addActionListener(controller);
		GridBagConstraints gbc_btnTaUtLista = new GridBagConstraints();
		gbc_btnTaUtLista.anchor = GridBagConstraints.EAST;
		gbc_btnTaUtLista.gridx = 3;
		gbc_btnTaUtLista.gridy = 0;
		controlPanel.add(generateReportButton, gbc_btnTaUtLista);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		equipmentTable = new SortedTable(tableModel);
		scrollPane.setViewportView(equipmentTable);
	}


	// Search key text field access methods
	public String getsearchKey() {
		return searchKeyTextField.getText();
	}

	void setSearchAction(String name) {
		logger.debug("Set search action=" + name);
		searchKeyTextField.setActionCommand(name);
	}

	// Generate report button access methods
	void setGenerateReportAction(String name) {
		logger.debug("Set generate action=" + name);
		generateReportButton.setActionCommand(name);
	}

	void setGenerateButtonEnabled(boolean value) {
		logger.debug("Set generate button enabled=" + value);
		generateReportButton.setEnabled(value);
	}
}
