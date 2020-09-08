package se.melsom.warehouse.presentation.importer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import se.melsom.warehouse.importer.ImportType;

/**
 * The type Import wizard view.
 */
@SuppressWarnings("serial")
public class ImportWizardView extends JDialog {
	private JTable table;
	private JButton cancelButton;
	private JButton continueButton;
	private JButton completeButton;

    /**
     * Instantiates a new Import wizard view.
     *
     * @param controller   the controller
     * @param parent       the parent
     * @param tableModel   the table model
     * @param cellRenderer the cell renderer
     */
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

    /**
     * Sets import type.
     *
     * @param type the type
     */
    public void setImportType(ImportType type) {
		setTitle("Importerar data : " + type.getName());
	}

    /**
     * Sets cancel action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setCancelAction(String actionCommand, ActionListener actionListener) {
		cancelButton.setActionCommand(actionCommand);
		cancelButton.addActionListener(actionListener);
	}

    /**
     * Sets continue action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setContinueAction(String actionCommand, ActionListener actionListener) {
		continueButton.setActionCommand(actionCommand);
		continueButton.addActionListener(actionListener);
	}

    /**
     * Sets continue enabled.
     *
     * @param enabled the enabled
     */
    public void setContinueEnabled(boolean enabled) {
		continueButton.setEnabled(enabled);
	}

    /**
     * Sets continue text.
     *
     * @param text the text
     */
    public void setContinueText(String text) {
		continueButton.setText(text);
	}

    /**
     * Sets complete action.
     *
     * @param actionCommand  the action command
     * @param actionListener the action listener
     */
    public void setCompleteAction(String actionCommand, ActionListener actionListener) {
		completeButton.setActionCommand(actionCommand);
		completeButton.addActionListener(actionListener);
	}

    /**
     * Sets complete enabled.
     *
     * @param enabled the enabled
     */
    public void setCompleteEnabled(boolean enabled) {
		completeButton.setEnabled(enabled);
	}
	

}
