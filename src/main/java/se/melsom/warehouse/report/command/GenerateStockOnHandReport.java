package se.melsom.warehouse.report.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.application.desktop.DesktopPresentationModel;
import se.melsom.warehouse.application.desktop.DesktopView;
import se.melsom.warehouse.application.inventory.status.InventoryStatus;
import se.melsom.warehouse.data.vo.StockOnHandVO;
import se.melsom.warehouse.model.EntityName;
import se.melsom.warehouse.report.Report;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.inventory.status.StockOnHandPage;
import se.melsom.warehouse.report.inventory.status.StockOnHandReport;

import java.util.Vector;

@Component
public class GenerateStockOnHandReport extends Command {
	private static final Logger logger = LoggerFactory.getLogger(GenerateStockOnHandReport.class);
	private final InventoryStatus inventoryStatus;
	
	private static final String[] tableColumnNames = {
			EntityName.ITEM_NUMBER, EntityName.ITEM_NAME, 			
			EntityName.INVENTORY_NOMINAL_QUANTITY, EntityName.INVENTORY_ACTUAL_QUANTITY, EntityName.ITEM_PACKAGING,
			EntityName.INVENTORY_IDENTITY, EntityName.INVENTORY_ANNOTATION
		};

	@Autowired
    public GenerateStockOnHandReport(InventoryStatus inventoryStatus, DesktopPresentationModel desktopPresentationModel) {
		this.inventoryStatus = inventoryStatus;
		desktopPresentationModel.addActionCommand(DesktopView.GENERATE_STOCK_ON_HAND_REPORT, this);
	}
	
	@Override
	public void execute() {
		logger.debug("Generate stock on hand report.");
		Vector<String> tableHeader = new Vector<>();
		
		for (String columnName : tableColumnNames) {
			tableHeader.addElement(columnName);
		}
		
		Vector<StockOnHandVO> data = inventoryStatus.getStockOnHand();
		StockOnHandReport report = new StockOnHandReport(tableHeader, data);
		
		for (int pageIndex = 0; pageIndex < report.getPages().size(); pageIndex++) {
			StockOnHandPage page = (StockOnHandPage) report.getPages().get(pageIndex);
			
			page.setPageNumber(pageIndex + 1);
			page.setPageCount(report.getPages().size());
		}

		for (Page page : report.getPages()) {
			((StockOnHandPage) page).updatePageNumberField();
		}

		// TODO: set up parent chain
		Report.save(report, null);
	}
}
