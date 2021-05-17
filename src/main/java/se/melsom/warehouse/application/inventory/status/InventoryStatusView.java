package se.melsom.warehouse.application.inventory.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.common.table.SortableJTable;
import se.melsom.warehouse.application.main.DesktopPresentationModel;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Användargränssnitt för visning av lagersaldo.
 * Kopplingen mellan modell och vy sköts till största del av
 * StatusTableModel som kopplas till SortedTable (specialisering av JTable).
 * Vyn har även kryssrutor för att dölja artiklar utifrån:
 * - shortfall
 * - balance
 * - overplus
 */
@Component
public class InventoryStatusView extends JInternalFrame implements AbstractInventoryStatusView, ComponentListener {
    private static final Logger logger = LoggerFactory.getLogger(InventoryStatusView.class);

    @Autowired private PersistentSettings persistentSettings;
    @Autowired private InventoryStatus presentationModel;

    private JCheckBox shortfallCheckBox;
    private JCheckBox balancesCheckBox;
    private JCheckBox overplusCheckBox;
    private SortableJTable stockOnHandtTable;

    public InventoryStatusView(DesktopPresentationModel presenterModel) {
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
        logger.debug("Show view");
        WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

        if (settings == null) {
            settings = new WindowBean(500, 10, 300, 400, true);

            persistentSettings.addWindowSettings(getWindowName(), settings);
        }

        logger.debug("Set bounds: x={}, y={}, w={}, h={}.", settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
        setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
        logger.debug("Set visible: isVisible={}.", settings.isVisible());
        setVisible(settings.isVisible());
        setVisible(true);
    }

    @Override
    public void initialize(ContentModel tableModel) {
        logger.debug("Initialize.");
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
        shortfallCheckBox.addActionListener(e -> presentationModel.setShowingShortfall(!shortfallCheckBox.isSelected()));

        balancesCheckBox = new JCheckBox("balanser");
        controlPanel.add(balancesCheckBox);
        balancesCheckBox.addActionListener(e -> presentationModel.setShowingBalances(!balancesCheckBox.isSelected()));

        overplusCheckBox = new JCheckBox("överskott");
        controlPanel.add(overplusCheckBox);
        overplusCheckBox.addActionListener(e -> presentationModel.setShowingOverplus(!overplusCheckBox.isSelected()));

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        stockOnHandtTable = new SortableJTable(tableModel);
        scrollPane.setViewportView(stockOnHandtTable);
        setCellRenderer(4, new QuantityCellRenderer(tableModel));
        addComponentListener(this);
    }

    public void setCellRenderer(int columnIndex, TableCellRenderer renderer) {
        stockOnHandtTable.addCellRenderer(columnIndex, renderer);
    }

    public boolean getFilterShortfallChecked() {
        return shortfallCheckBox.isSelected();
    }

    public void setFilterShortfallChecked(boolean checked) {
        shortfallCheckBox.setSelected(checked);
    }

    public void setFilterShortfallAction(String name, ActionListener listener) {
        shortfallCheckBox.setActionCommand(name);
        shortfallCheckBox.addActionListener(listener);
    }

    public boolean getFilterBalancesChecked() {
        return balancesCheckBox.isSelected();
    }

    public void setFilterBalancesChecked(boolean checked) {
        balancesCheckBox.setSelected(checked);
    }

    public void setFilterBalancesAction(String name, ActionListener listener) {
        balancesCheckBox.setActionCommand(name);
        balancesCheckBox.addActionListener(listener);
    }

    public boolean getFilterOverplusChecked() {
        return overplusCheckBox.isSelected();
    }

    public void setFilterOverplusChecked(boolean checked) {
        overplusCheckBox.setSelected(checked);
    }

    public void setFilterOverplusAction(String name, ActionListener listener) {
        overplusCheckBox.setActionCommand(name);
        overplusCheckBox.addActionListener(listener);
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
