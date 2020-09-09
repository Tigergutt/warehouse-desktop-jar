package se.melsom.warehouse.presentation.importer;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import se.melsom.warehouse.importer.ImportType;
import se.melsom.warehouse.importer.Importer;
import se.melsom.warehouse.model.InventoryAccounting;
import se.melsom.warehouse.presentation.ViewController;
import se.melsom.warehouse.settings.PersistentSettings;
import se.melsom.warehouse.settings.WindowSettings;

/**
 * The type Import wizard controller.
 */
public class ImportWizardController extends ViewController {
	private static Logger logger = Logger.getLogger(ImportWizardController.class);
	
	private static final String CANCEL_ACTION = "CancelAction";
	private static final String CONTINUE_ACTION = "ContinueAction";
	private static final String COMPLETE_ACTION = "CompleteAction";
	
	private InventoryAccounting inventoryAccounting;
	private ImportWizardView view = null;
	private InputTableModel tableModel = new InputTableModel();
	private ImporterState state = ImporterState.PENDING;
	private Importer importer = null;

    /**
     * Instantiates a new Import wizard controller.
     *
     * @param inventoryAccounting the inventory accounting
     */
    public ImportWizardController(InventoryAccounting inventoryAccounting) {
		this.inventoryAccounting = inventoryAccounting;
	}

    /**
     * Import excel file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean importExcelFile(String path) {
		logger.debug("Load equipment type data from: '" + path + "'");
		InputStream input = null;
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("", e);
			return false;
		}

		Workbook wb = null;

		try {
			wb = new XSSFWorkbook(input);
		} catch (IOException e) {
			logger.error("", e);
			return false;
		}

		try {
			wb.close();
		} catch (IOException e) {
			logger.error("", e);
		}

		Sheet sheet = wb.getSheetAt(0);

		if (sheet == null) {
			logger.error("No such sheet (0).");
			return false;
		}

		Iterator<Row> rowIterator = sheet.rowIterator();
		int columnCount = 0;
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			if (row == null) {
				logger.debug("Row is null");
				continue;
			}
			
			if (row.getRowNum() == sheet.getFirstRowNum()) {
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while (cellIterator.hasNext()) {
					String text = getCellContent(cellIterator.next()).toString();
					
					if (text == null || text.length() == 0) {
						break;
					}
					
					tableModel.addColumnName(text);
					columnCount++;
				}

				continue;
			}
						
			Vector<ImportCell> tableRow = new Vector<>();
			
			for (int cellIndex = row.getFirstCellNum(); cellIndex <= row.getLastCellNum(); cellIndex++) {				
				ImportCell data = new ImportCell(ImportStatus.PENDING, getCellContent(row.getCell(cellIndex)));
				
				if (tableRow.size() < columnCount) {
					tableRow.addElement(data);
				}
			}
			
			boolean isEmpty = true;
			
			for (ImportCell cell : tableRow) {
				if (cell.getValue() != null && cell.getValue().toString().length() > 0) {
					isEmpty = false;
					break;
				}
			}
			
			if (!isEmpty) {
				if (tableRow.size() < tableModel.getColumnCount()) {
					ImportCell data = new ImportCell(ImportStatus.PENDING, null);
					
					for (int columnIndex = tableRow.size(); columnIndex < tableModel.getColumnCount(); columnIndex++) {
						tableRow.addElement(data);
					}
				}
				if (logger.isTraceEnabled()) {
					String trace = "";
					
					for (ImportCell cell : tableRow) {
						trace += "[" + cell.getValue() + "]";
					}
					
					logger.trace(trace);
				}
				
				tableModel.addRow(tableRow);
			}			
		}
		
		state = ImporterState.READY;
		
		return true;
	}

    /**
     * Show wizard view.
     *
     * @param type   the type
     * @param parent the parent
     */
    public void showWizardView(ImportType type, JFrame parent) {
		this.view = new ImportWizardView(this, parent, tableModel, new InputTableCellRenderer(tableModel));
		
		WindowSettings settings = PersistentSettings.singleton().getWindowSettings(getWindowName());
		
		if (settings == null) {
			settings = new WindowSettings(getWindowName(), 21, 33, 778, 264, false);
			
			PersistentSettings.singleton().addWindowSettings(settings);
		}

		view.setImportType(type);
		view.setBounds(settings.getX(), settings.getY(), settings.getWidth(), settings.getHeight());
		view.setCancelAction(CANCEL_ACTION, this);
		view.setContinueAction(CONTINUE_ACTION, this);
		view.setCompleteAction(COMPLETE_ACTION, this);

		importer = Importer.getImporter(type, tableModel);
		
		boolean canContinue = importer.evaluateColumnIndices();
		view.setContinueEnabled(canContinue);
		view.setContinueText("Validera >");
		view.setCompleteEnabled(canContinue);
		
		view.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.debug("Received action=" + e.getActionCommand());

		switch (e.getActionCommand()) {
		case CANCEL_ACTION:
			view.setVisible(false);
			break;
			
		case CONTINUE_ACTION:
			switch (state) {
			case PENDING:
				break;
				
			case READY:
				importer.checkValidity(inventoryAccounting);		
				tableModel.fireTableDataChanged();
				state = ImporterState.VALIDITY_CHECKED;
				view.setContinueEnabled(true);
				view.setContinueText("Konsistenskontroll >");
				view.setCompleteEnabled(true);
				break;
				
			case VALIDITY_CHECKED:
				importer.checkConsitency(inventoryAccounting);
				tableModel.fireTableDataChanged();
				state = ImporterState.CONSISTENCY_CHECKED;
				view.setContinueEnabled(importer.anythingToStore());
				view.setContinueText("Spara >");
				view.setCompleteEnabled(importer.anythingToStore());
				break;
				
			case CONSISTENCY_CHECKED:
				importer.storeData(inventoryAccounting);
				state = ImporterState.STORED;
				view.setContinueEnabled(false);
				view.setCompleteEnabled(false);
				importer.checkConsitency(inventoryAccounting);
				tableModel.fireTableDataChanged();
				break;
				
			case STORED:
				break;
			}
			break;
			
		case COMPLETE_ACTION:
			switch (state) {
			case PENDING:
			case READY:
				importer.checkValidity(inventoryAccounting);		

			case VALIDITY_CHECKED:
				importer.checkConsitency(inventoryAccounting);
				
			case CONSISTENCY_CHECKED:
				importer.storeData(inventoryAccounting);
				
			case STORED:
			}
			state = ImporterState.STORED;
			view.setContinueEnabled(false);
			view.setCompleteEnabled(false);
			importer.checkConsitency(inventoryAccounting);
			tableModel.fireTableDataChanged();
			break;
			
		default:
			break;
		}
	}
	
	private Object getCellContent(Cell cell) {
		Object content = null;
		
		if (cell != null) {
			CellType cellType = cell.getCellTypeEnum();

			switch (cellType) {
			case STRING:
				content = cell.getStringCellValue();
				break;

			case BOOLEAN:
				content = cell.getBooleanCellValue();
				break;

			case NUMERIC:
				content = cell.getNumericCellValue();
				break;

			default:
				logger.warn("Unkown cell type: " + cellType);
			case BLANK:
				content = "";
				break;
			}
		}

		return content;
	}

	@Override
	public JComponent getView() {
		return null;
	}

	@Override
	public void componentResized(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowDimension(getWindowName(), frame.getWidth(), frame.getHeight());	
	}

	@Override
	public void componentMoved(ComponentEvent event) {
		if (event.getSource() instanceof JInternalFrame == false) {
			return;
		}
		
		JInternalFrame frame = (JInternalFrame) event.getSource();
		PersistentSettings.singleton().setWindowLocation(getWindowName(), frame.getX(), frame.getY());	
	}
	
	private String getWindowName() {
		return ImportWizardView.class.getSimpleName();
	}

}
