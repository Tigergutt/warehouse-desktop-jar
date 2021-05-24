package se.melsom.warehouse.presentation.importer;

import se.melsom.warehouse.maintenance.importer.ImportType;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImportWizardView extends JDialog {
	private final JTable table;
	private final JButton cancelButton;
	private final JButton continueButton;
	private final JButton completeButton;

    public ImportWizardView(ImportWizardController controller, JFrame parent, TableModel tableModel, final TableCellRenderer cellRenderer) {
		super(parent);
		addComponentListener(controller);
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tableModel) {
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return cellRenderer;
			}
		};
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		FlowLayout buttonPanelLayout = (FlowLayout) buttonPanel.getLayout();
		buttonPanelLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		cancelButton = new JButton("Avbryt");
		buttonPanel.add(cancelButton);
		
		continueButton = new JButton("Validera >");
		buttonPanel.add(continueButton);
		
		completeButton = new JButton("Slutför");
		buttonPanel.add(completeButton);
	}

	public void setImportType(ImportType type) {
		setTitle("Importerar data : " + type.getName());
	}

    public void setCancelAction(String actionCommand, ActionListener actionListener) {
		cancelButton.setActionCommand(actionCommand);
		cancelButton.addActionListener(actionListener);
	}

    public void setContinueAction(String actionCommand, ActionListener actionListener) {
		continueButton.setActionCommand(actionCommand);
		continueButton.addActionListener(actionListener);
	}

    public void setContinueEnabled(boolean enabled) {
		continueButton.setEnabled(enabled);
	}

    public void setContinueText(String text) {
		continueButton.setText(text);
	}

    public void setCompleteAction(String actionCommand, ActionListener actionListener) {
		completeButton.setActionCommand(actionCommand);
		completeButton.addActionListener(actionListener);
	}

    public void setCompleteEnabled(boolean enabled) {
		completeButton.setEnabled(enabled);
	}
}
