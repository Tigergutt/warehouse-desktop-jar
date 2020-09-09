package se.melsom.warehouse.presentation.stock;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;

import se.melsom.warehouse.presentation.common.table.SortedTable;
import se.melsom.warehouse.presentation.common.table.SortedTableModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

/**
 * The type Stock on hand view.
 */
@SuppressWarnings("serial")
public class StockOnHandView extends JInternalFrame {
	private JCheckBox shortfallCheckBox;
	private JCheckBox balancesCheckBox;
	private JCheckBox overplusCheckBox;
	private SortedTable stockOnHandtTable;

    /**
     * Instantiates a new Stock on hand view.
     *
     * @param controller the controller
     * @param tableModel the table model
     */
    public StockOnHandView(StockOnHandController controller, SortedTableModel tableModel) {
		addComponentListener(controller);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Lagersaldo");
		
		JPanel controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		
		JLabel filterLabel = new JLabel("Dölj:");
		controlPanel.add(filterLabel);
		
		shortfallCheckBox = new JCheckBox("underskott");
		controlPanel.add(shortfallCheckBox);
		
		balancesCheckBox = new JCheckBox("balanser");
		controlPanel.add(balancesCheckBox);
		
		overplusCheckBox = new JCheckBox("överskott");
		controlPanel.add(overplusCheckBox);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		stockOnHandtTable = new SortedTable(tableModel);
		scrollPane.setViewportView(stockOnHandtTable);
	}

    /**
     * Sets cell renderer.
     *
     * @param columnIndex the column index
     * @param renderer    the renderer
     */
    public void setCellRenderer(int columnIndex, TableCellRenderer renderer) {
		stockOnHandtTable.addCellRenderer(columnIndex, renderer);
	}

    /**
     * Gets filter shortfall checked.
     *
     * @return the filter shortfall checked
     */
    /*
	 * Shortfall filter check box
	 */
	public boolean getFilterShortfallChecked() {
		return shortfallCheckBox.isSelected();
	}

    /**
     * Sets filter shortfall checked.
     *
     * @param checked the checked
     */
    public void setFilterShortfallChecked(boolean checked) {
		shortfallCheckBox.setSelected(checked);
	}

    /**
     * Sets filter shortfall action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setFilterShortfallAction(String name, ActionListener listener) {
		shortfallCheckBox.setActionCommand(name);
		shortfallCheckBox.addActionListener(listener);
	}

    /**
     * Gets filter balances checked.
     *
     * @return the filter balances checked
     */
    /*
	 * Balances filter check box
	 */
	public boolean getFilterBalancesChecked() {
		return balancesCheckBox.isSelected();
	}

    /**
     * Sets filter balances checked.
     *
     * @param checked the checked
     */
    public void setFilterBalancesChecked(boolean checked) {
		balancesCheckBox.setSelected(checked);
	}

    /**
     * Sets filter balances action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setFilterBalancesAction(String name, ActionListener listener) {
		balancesCheckBox.setActionCommand(name);
		balancesCheckBox.addActionListener(listener);
	}

    /**
     * Gets filter overplus checked.
     *
     * @return the filter overplus checked
     */
    /*
	 * Overplus filter check box
	 */
	public boolean getFilterOverplusChecked() {
		return overplusCheckBox.isSelected();
	}

    /**
     * Sets filter overplus checked.
     *
     * @param checked the checked
     */
    public void setFilterOverplusChecked(boolean checked) {
		overplusCheckBox.setSelected(checked);
	}

    /**
     * Sets filter overplus action.
     *
     * @param name     the name
     * @param listener the listener
     */
    public void setFilterOverplusAction(String name, ActionListener listener) {
		overplusCheckBox.setActionCommand(name);
		overplusCheckBox.addActionListener(listener);
	}
}
