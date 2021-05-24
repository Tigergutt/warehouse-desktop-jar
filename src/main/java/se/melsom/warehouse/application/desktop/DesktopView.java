package se.melsom.warehouse.application.desktop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;
import se.melsom.warhorse.Version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@Component
public class DesktopView extends JFrame implements AbstractDesktopView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(DesktopView.class);

	public static final String WINDOW_TITLE = "Warhorse Logistics";
	public static final String FILE_MENU = "Arkiv";
	public static final String IMPORT_SUB_MENU = "Importera";
	public static final String IMPORT_INVENTORY = "Materiellista...";
	public static final String IMPORT_MASTER_INVENTORY = "Materiellista VNG...";
	public static final String IMPORT_STOCK_LOCATIONS = "Lagerplatser...";
	public static final String IMPORT_ORGANIZATIONAL_UNITS = "Organisation...";
	public static final String IMPORT_DATABASE = "Databasexport...";
	public static final String EXPORT_DATABASE = "Exportera...";
	public static final String EXIT_APPLICATION = "Avsluta";
	public static final String TOOLS_MENU = "Verktyg";
	public static final String REPORTS_SUB_MENU = "Rapporter";
	public static final String GENERATE_LOCATION_INVENTORY_REPORT = "Materiellista för respektive lagerplats...";
	public static final String GENERATE_STOCK_ON_HAND_REPORT = "Aktuellt lagersaldo...";
	public static final String MAINTENANCE_SUB_MENU = "Registervård";
	public static final String EDIT_ITEMS = "Redigera artikellista...";
	public static final String EDIT_INSTANCES = "Redigera materiellista VNG...";
	public static final String EDIT_STOCK_LOCATIONS = "Redigera lagerplats...";
	public static final String EDIT_APPLICATIONS = "Redigera användning...";
	public static final String EDIT_ORGANIZATION = "Redigera organisation...";
	public static final String WINDOW_MENU = "Fönster";
	public static final String SHOW_STOCK_ON_HAND_VIEW = "Lagersaldo";
	public static final String SHOW_INVENTORY_VIEW = "Lagerplats";
	public static final String SHOW_INVENTORY_HOLDING_VIEW = "Ansvarsområde";
	public static final String SHOW_SEARCH_INVENTORY_VIEW = "Sökfönster";
	public static final String HELP_MENU = "Hjälp";
	public static final String SHOW_LOGGER_VIEW = "Visa loggfönster...";

	// Importer menu items
	private JMenuItem menuItemImportInventory;
	private JMenuItem menuItemImportLocations;
	private JMenuItem menuItemImportUnits;
	private JMenuItem menuItemImportMasterInventory;

	// Exporter menu items
	private JMenuItem menuItemImportDatabase;
	private JMenuItem menuItemExportDatabase;

	// File maintenance menu items
	private JMenuItem menuItemEditItems;
	private JMenuItem menuItemEditInstances;
	private JMenuItem menuItemEditStockLocations;
	private JMenuItem menuItemEditApplication;
	private JMenuItem menuItemEditOrganization;

	// Report menu items
	JMenuItem menuItemGenerateStockLocationInventory;
    JMenuItem menuItemGenerateStockOnHand;
	
	// Window/show menu items
	private JCheckBoxMenuItem checkedMenuItemShowInventoryView;
	private JCheckBoxMenuItem checkedMenuItemShowInventoryHoldingView;
	private JCheckBoxMenuItem checkedMenuItemShowSearchView;
	private JCheckBoxMenuItem checkedMenuItemShowStockOnHandView;
	
	// Help menu items
	private JMenuItem menuItemLoggerView;
	private JDesktopPane desktopPane;

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private DesktopPresentationModel presenterModel;

	public DesktopView() {
		logger.debug("Execute constructor.");
	}

	@Override
	public JFrame getFrame() {
		return this;
	}

	@Override
	public void addView(JInternalFrame view) {
		desktopPane.add(view);
	}

	@Override
	public void showView() {
		logger.debug("ShowDesktop.");
		WindowBean settings = persistentSettings.getWindowSettings(getWindowName());

		if (settings == null) {
			logger.debug("Using default settings");
			settings = new WindowBean(100, 100, 1024, 520, true);

			persistentSettings.addWindowSettings(getWindowName(), settings);
		}

		logger.debug("Setting bounds [{}].", settings);

		setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		setVisible(true);
	}

	@Override
	public void updateState(ViewState state) {
		menuItemGenerateStockOnHand.setEnabled(state.isGenerateStockOnHandReportEnabled());
		menuItemGenerateStockLocationInventory.setEnabled(state.isGenerateStockLocationReportEnabled());

		menuItemEditItems.setEnabled(state.isEditItemsEnabled());
		menuItemEditInstances.setEnabled(state.isEditInstancesEnabled());
		menuItemEditApplication.setEnabled(state.isEditApplicationsEnabled());
		menuItemEditStockLocations.setEnabled(state.isEditHoldingsEnabled());
		menuItemEditOrganization.setEnabled(state.isEditOrganizationEnabled());

		menuItemLoggerView.setEnabled(state.isLoggerViewEnabled());

		checkedMenuItemShowStockOnHandView.setEnabled(state.isShowStockOnHandViewEnabled());
		checkedMenuItemShowStockOnHandView.setSelected(state.isShowStockOnHandViewChecked());
		checkedMenuItemShowInventoryView.setSelected(state.isShowInventoryViewChecked());
		checkedMenuItemShowInventoryView.setEnabled(state.isShowInventoryViewEnabled());
		checkedMenuItemShowInventoryHoldingView.setSelected(state.isShowInventoryHoldingViewChecked());
		checkedMenuItemShowInventoryHoldingView.setEnabled(state.isShowInventoryHoldingViewEnabled());
		checkedMenuItemShowSearchView.setEnabled(state.isShowSearchViewEnabled());
		checkedMenuItemShowSearchView.setSelected(state.isShowSearchViewChecked());
	}

	@Override
	public void initialize() {
		logger.debug("Execute initializeView().");
		setTitle(String.format("%s - Version %s", WINDOW_TITLE, Version.getVersion()));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu(FILE_MENU);
		menuBar.add(mnFile);

		JMenu mnImport = new JMenu(IMPORT_SUB_MENU);
		mnFile.add(mnImport);

		menuItemImportInventory = createMenuItem(IMPORT_INVENTORY, mnImport);

		JSeparator separator = new JSeparator();
		mnImport.add(separator);

		menuItemImportMasterInventory = createMenuItem(IMPORT_MASTER_INVENTORY, mnImport);
		menuItemImportLocations = createMenuItem(IMPORT_STOCK_LOCATIONS, mnImport);
		menuItemImportUnits = createMenuItem(IMPORT_ORGANIZATIONAL_UNITS, mnImport);

		mnImport.add(new JSeparator());

		menuItemImportDatabase = createMenuItem(IMPORT_DATABASE, mnImport);
		menuItemExportDatabase = createMenuItem(EXPORT_DATABASE, mnFile);

		mnFile.add(new JSeparator());

		JMenuItem mntmQuit = new JMenuItem(EXIT_APPLICATION);
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presenterModel.quitApplication();
			}
		});
		mnFile.add(mntmQuit);

		JMenu mnTools = new JMenu(TOOLS_MENU);
		menuBar.add(mnTools);

		JMenu mnReport = new JMenu(REPORTS_SUB_MENU);
		mnTools.add(mnReport);

		menuItemGenerateStockLocationInventory = createMenuItem(GENERATE_LOCATION_INVENTORY_REPORT, mnReport);
		menuItemGenerateStockOnHand = createMenuItem(GENERATE_STOCK_ON_HAND_REPORT, mnReport);

		JMenu mnFileMaintenance = new JMenu(MAINTENANCE_SUB_MENU);
		mnTools.add(mnFileMaintenance);

		menuItemEditItems = createMenuItem(EDIT_ITEMS, mnFileMaintenance);
		menuItemEditInstances = createMenuItem(EDIT_INSTANCES, mnFileMaintenance);
		menuItemEditStockLocations = createMenuItem(EDIT_STOCK_LOCATIONS, mnFileMaintenance);
		menuItemEditApplication = createMenuItem(EDIT_APPLICATIONS, mnFileMaintenance);
		menuItemEditOrganization = createMenuItem(EDIT_ORGANIZATION, mnFileMaintenance);

		JMenu mnWindow = new JMenu(WINDOW_MENU);
		menuBar.add(mnWindow);

		checkedMenuItemShowStockOnHandView = createCheckedMenuItem(SHOW_STOCK_ON_HAND_VIEW, mnWindow);
		checkedMenuItemShowInventoryView = createCheckedMenuItem(SHOW_INVENTORY_VIEW, mnWindow);
		checkedMenuItemShowInventoryHoldingView = createCheckedMenuItem(SHOW_INVENTORY_HOLDING_VIEW, mnWindow);
		checkedMenuItemShowSearchView = createCheckedMenuItem(SHOW_SEARCH_INVENTORY_VIEW, mnWindow);

		JMenu mnHelp = new JMenu(HELP_MENU);
		menuBar.add(mnHelp);

		menuItemLoggerView = createCheckedMenuItem(SHOW_LOGGER_VIEW, mnHelp);

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(132, 189, 0));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
	}

	String getWindowName() {
		return DesktopView.class.getSimpleName();
	}

	void menuItemAction(String action) {
		presenterModel.actionPerformed(action);
	}

	JMenuItem createMenuItem(String text, JMenu menu) {
    	JMenuItem menuItem = new JMenuItem(text);

		menuItem.setEnabled(false);
    	menuItem.setActionCommand(text);
    	menuItem.addActionListener((ActionEvent e) -> menuItemAction(e.getActionCommand()));

    	menu.add(menuItem);

    	return menuItem;
	}

	JCheckBoxMenuItem createCheckedMenuItem(String text, JMenu menu) {
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(text);

		menuItem.setEnabled(false);
		menuItem.setActionCommand(text);
		menuItem.addActionListener((ActionEvent e) -> menuItemAction(e.getActionCommand()));

		menu.add(menuItem);

		return menuItem;
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}

		JFrame frame = (JFrame) event.getSource();

		persistentSettings.setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JFrame == false) {
			return;
		}

		JFrame frame = (JFrame) event.getSource();

		persistentSettings.setWindowLocation(getWindowName(), frame.getX(), frame.getY());
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}
