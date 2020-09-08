package se.melsom.warehouse.presentation.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;

/**
 * The type Desktop view.
 */
@SuppressWarnings("serial")
public class DesktopView extends JFrame {
	private static Logger logger = Logger.getLogger(DesktopView.class);
	
	// Importer menu items
	private JMenuItem menuImportInventory;
	private JMenuItem menuImportLocations;
	private JMenuItem menuImportUnits;
	private JMenuItem menuImportMasterInventory;

	// Exporter menu items
	private JMenuItem mntmExport;

	// File maintenance menu items
	private JMenuItem editItemsMenu;
	private JMenuItem editInstancesMenu;
	private JMenuItem editStockLocations;
	private JMenuItem editApplications;

    /**
     * The Generate stock location inventory menu.
     */
// Report menu items
	JMenuItem generateStockLocationInventoryMenu;
    /**
     * The Generate stock on hand menu.
     */
    JMenuItem generateStockOnHandMenu;	
	
	// Window/show menu items
	private JCheckBoxMenuItem showInventoryViewMenu;
	private JCheckBoxMenuItem showInventoryHoldingView;
	private JCheckBoxMenuItem showSearchViewMenu;
	private JCheckBoxMenuItem showStockOnHandViewMenu;
	
	// Help menu items
	private JMenuItem loggerViewMenu;
	
	private JDesktopPane desktopPane;
	private DesktopController presenter;

    /**
     * Instantiates a new Desktop view.
     *
     * @param presenter the presenter
     */
    public DesktopView(DesktopController presenter) {
		this.presenter = presenter;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				quit(false);
			}
		});
		
		setTitle("Warhorse Logistics: Lagerstatus 162");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Arkiv");
		menuBar.add(mnFile);
		
		JMenu mnImport = new JMenu("Importera");
		mnFile.add(mnImport);
		
		menuImportInventory = new JMenuItem("Materiellista...");
		menuImportInventory.addActionListener(presenter);
		mnImport.add(menuImportInventory);
		 
		JSeparator separator = new JSeparator();
		mnImport.add(separator);
		
		menuImportMasterInventory = new JMenuItem("Materiellista VNG...");
		menuImportMasterInventory.addActionListener(presenter);
		mnImport.add(menuImportMasterInventory);

		menuImportLocations = new JMenuItem("Lagerplatser...");
		menuImportLocations.addActionListener(presenter);
		mnImport.add(menuImportLocations);
		
		menuImportUnits = new JMenuItem("Organisation...");
		menuImportUnits.addActionListener(presenter);
		mnImport.add(menuImportUnits);
		
		JSeparator separator_1 = new JSeparator();
		mnImport.add(separator_1);
		
		JMenuItem mntmDatabasexport = new JMenuItem("Databasexport...");
		mntmDatabasexport.setEnabled(false);
		mnImport.add(mntmDatabasexport);

		mntmExport = new JMenuItem("Exportera...");
		mntmExport.addActionListener(presenter);		
		mnFile.add(mntmExport);
		
		mnFile.add(new JSeparator());
		
		JMenuItem mntmQuit = new JMenuItem("Avsluta");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit(true);
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnTools = new JMenu("Verktyg");
		menuBar.add(mnTools);
		
		JMenu mnReport = new JMenu("Rapporter");
		mnTools.add(mnReport);
		
		generateStockLocationInventoryMenu = new JMenuItem("Materiellista för respektive lagerplats...");
		generateStockLocationInventoryMenu.addActionListener(presenter);		
		mnReport.add(generateStockLocationInventoryMenu);

		generateStockOnHandMenu = new JMenuItem("Aktuellt lagersaldo...");
		generateStockOnHandMenu.addActionListener(presenter);		
		mnReport.add(generateStockOnHandMenu);

		JMenu mnFileMaintenance = new JMenu("Registervård");
		mnTools.add(mnFileMaintenance);
		
		editItemsMenu = new JMenuItem("Redigera artikellista...");
		editItemsMenu.addActionListener(presenter);		
		mnFileMaintenance.add(editItemsMenu);
		
		editInstancesMenu = new JMenuItem("Redigera materiellista VNG...");
		editInstancesMenu.addActionListener(presenter);		
		mnFileMaintenance.add(editInstancesMenu);
		
		editStockLocations = new JMenuItem("Redigera lagerplats...");
		editStockLocations.setEnabled(false);		
		mnFileMaintenance.add(editStockLocations);

		editApplications = new JMenuItem("Redigera användning...");
		editApplications.addActionListener(presenter);		
		mnFileMaintenance.add(editApplications);

		JMenuItem mntm14 = new JMenuItem("Redigera organisation...");
		mntm14.setEnabled(false);		
		mnFileMaintenance.add(mntm14);

		JMenu mnWindow = new JMenu("Fönster");
		menuBar.add(mnWindow);
		
		showStockOnHandViewMenu = new JCheckBoxMenuItem("Lagersaldo");
		showStockOnHandViewMenu.addActionListener(presenter);
		mnWindow.add(showStockOnHandViewMenu);
				
		showInventoryViewMenu = new JCheckBoxMenuItem("Lagerplats");
		showInventoryViewMenu.addActionListener(presenter);
		mnWindow.add(showInventoryViewMenu);
		
		showInventoryHoldingView = new JCheckBoxMenuItem("Ansvarsområde");
		showInventoryHoldingView.addActionListener(presenter);		
		mnWindow.add(showInventoryHoldingView);

		showSearchViewMenu = new JCheckBoxMenuItem("Sökfönster");
		showSearchViewMenu.addActionListener(presenter);		
		mnWindow.add(showSearchViewMenu);

		JMenu mnHelp = new JMenu("Hjälp");
		menuBar.add(mnHelp);
		
		loggerViewMenu = new JMenuItem("Visa loggfönster...");
		loggerViewMenu.addActionListener(presenter);
		mnHelp.add(loggerViewMenu);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(132, 189, 0));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
	}

    /**
     * Gets desktop.
     *
     * @return the desktop
     */
    public JDesktopPane getDesktop() {
		return desktopPane;
	}

    /**
     * Sets show stock on hand view checked.
     *
     * @param isChecked the is checked
     */
//
	public void setShowStockOnHandViewChecked(boolean isChecked) {
		logger.trace("showStockOnHandViewMenu isChecked=" + isChecked);
		showStockOnHandViewMenu.setSelected(isChecked);		
	}

    /**
     * Sets show stock on hand view action.
     *
     * @param name the name
     */
    public void setShowStockOnHandViewAction(String name) {
		showStockOnHandViewMenu.setActionCommand(name);
	}

    /**
     * Sets show inventory view checked.
     *
     * @param isChecked the is checked
     */
//
	public void setShowInventoryViewChecked(boolean isChecked) {
		logger.trace("showInventoryViewMenu isChecked=" + isChecked);
		showInventoryViewMenu.setSelected(isChecked);		
	}

    /**
     * Sets show inventory view action.
     *
     * @param name the name
     */
    public void setShowInventoryViewAction(String name) {
		showInventoryViewMenu.setActionCommand(name);
	}

    /**
     * Sets show inventory holding view checked.
     *
     * @param isChecked the is checked
     */
//
	public void setShowInventoryHoldingViewChecked(boolean isChecked) {
		logger.trace("showInventoryHoldingView isChecked=" + isChecked);
		showInventoryHoldingView.setSelected(isChecked);		
	}

    /**
     * Sets show inventory holding view action.
     *
     * @param name the name
     */
    public void setShowInventoryHoldingViewAction(String name) {
		showInventoryHoldingView.setActionCommand(name);
	}

    /**
     * Sets show search view menu item checked.
     *
     * @param isChecked the is checked
     */
//
	public void setShowSearchViewMenuItemChecked(boolean isChecked) {
		logger.trace("showSearchViewMenu isChecked=" + isChecked);
		showSearchViewMenu.setSelected(isChecked);		
	}

    /**
     * Sets show search view action.
     *
     * @param name the name
     */
    public void setShowSearchViewAction(String name) {
		showSearchViewMenu.setActionCommand(name);
	}

    /**
     * Sets import inventory action.
     *
     * @param name the name
     */
//
	public void setImportInventoryAction(String name) {
		menuImportInventory.setActionCommand(name);
	}

    /**
     * Sets import master inventory action.
     *
     * @param name the name
     */
    public void setImportMasterInventoryAction(String name) {
		menuImportMasterInventory.setActionCommand(name);
	}

    /**
     * Sets import stock locations action.
     *
     * @param name the name
     */
//
	public void setImportStockLocationsAction(String name) {
		menuImportLocations.setActionCommand(name);
	}

    /**
     * Sets import organizational utits action.
     *
     * @param name the name
     */
//
	public void setImportOrganizationalUtitsAction(String name) {
		menuImportUnits.setActionCommand(name);
	}


    /**
     * Sets export database action.
     *
     * @param name the name
     */
    public void setExportDatabaseAction(String name) {
		mntmExport.setActionCommand(name);
	}

    /**
     * Sets edit items menu item checked.
     *
     * @param isChecked the is checked
     */
//
	public void setEditItemsMenuItemChecked(boolean isChecked) {
		logger.trace("editItemsMenu isChecked=" + isChecked);
		editItemsMenu.setEnabled(isChecked);
	}

    /**
     * Sets edit items action.
     *
     * @param name the name
     */
    public void setEditItemsAction(String name) {
		editItemsMenu.setActionCommand(name);
	}

    /**
     * Sets edit instances menu item checked.
     *
     * @param isChecked the is checked
     */
//
	public void setEditInstancesMenuItemChecked(boolean isChecked) {
		logger.trace("editInstancesMenu isChecked=" + isChecked);
		editInstancesMenu.setEnabled(isChecked);
	}

    /**
     * Sets edit instances action.
     *
     * @param name the name
     */
    public void setEditInstancesAction(String name) {
		editInstancesMenu.setActionCommand(name);
	}

    /**
     * Sets edit applications action.
     *
     * @param name the name
     */
    public void setEditApplicationsAction(String name) {
		editApplications.setActionCommand(name);
	}

    /**
     * Sets generate stock location inventor action.
     *
     * @param name the name
     */
//
	public void setGenerateStockLocationInventorAction(String name) {
		generateStockLocationInventoryMenu.setActionCommand(name);
	}


    /**
     * Sets generate stock on hand action.
     *
     * @param name the name
     */
    public void setGenerateStockOnHandAction(String name) {
		generateStockOnHandMenu.setActionCommand(name);
	}

    /**
     * Sets show log view checked.
     *
     * @param isChecked the is checked
     */
//
	public void setShowLogViewChecked(boolean isChecked) {
		logger.trace("loggerViewMenu isChecked=" + isChecked);
		loggerViewMenu.setEnabled(isChecked);
	}

    /**
     * Sets show log view action.
     *
     * @param name the name
     */
    public void setShowLogViewAction(String name) {
		loggerViewMenu.setActionCommand(name);
	}

    /**
     * Quit.
     *
     * @param shouldDispose the should dispose
     */
    void quit(boolean shouldDispose) {
		logger.debug("quit(" + shouldDispose + ")");
		presenter.quitApplication();
		
		if (shouldDispose) {
			super.dispose();
		}
	}
}
