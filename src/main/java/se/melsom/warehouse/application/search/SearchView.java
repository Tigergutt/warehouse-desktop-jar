package se.melsom.warehouse.application.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.common.table.SortableJTable;
import se.melsom.warehouse.application.common.table.SortableTableModel;
import se.melsom.warehouse.application.inventory.actual.ViewState;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@Component
public class SearchView extends JInternalFrame implements AbstractSearchView, ComponentListener {
	private static final Logger logger = LoggerFactory.getLogger(SearchView.class);

	@Autowired private PersistentSettings persistentSettings;
	@Autowired private SearchPresentationModel presentationModel;

	private SortableJTable equipmentTable;
	private JTextField searchKeyTextField;
	private JButton generateReportButton;

    public SearchView() {
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
		addComponentListener(this);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(true);
		setTitle("Fritextsökning");

		initializeView(tableModel);
		searchKeyTextField.addActionListener(this::handleSearchKeyAction);
		generateReportButton.addActionListener(this::handleGenerateReportAction);
	}

	@Override
	public void updateState(ViewState state) {
	}

	private void handleSearchKeyAction(ActionEvent actionEvent) {
    	presentationModel.searchEquipment(searchKeyTextField.getText());
	}

	private void handleGenerateReportAction(ActionEvent actionEvent) {
    	presentationModel.generateReport();
	}

	private void initializeView(SortableTableModel tableModel) {
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
		searchKeyTextField.setToolTipText("Ange sökord här. Return startar sökningen.");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		controlPanel.add(searchKeyTextField, gbc_textField);
		searchKeyTextField.setColumns(16);
		
		generateReportButton = new JButton("Ta ut lista...");
		generateReportButton.setEnabled(false);
		GridBagConstraints gbc_btnTaUtLista = new GridBagConstraints();
		gbc_btnTaUtLista.anchor = GridBagConstraints.EAST;
		gbc_btnTaUtLista.gridx = 3;
		gbc_btnTaUtLista.gridy = 0;
		controlPanel.add(generateReportButton, gbc_btnTaUtLista);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		equipmentTable = new SortableJTable(tableModel);
		scrollPane.setViewportView(equipmentTable);
	}

//	public String getSearchKey() {
//		return searchKeyTextField.getText();
//	}

    void setGenerateButtonEnabled(boolean value) {
		logger.debug("Set generate button enabled=" + value);
		generateReportButton.setEnabled(value);
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
