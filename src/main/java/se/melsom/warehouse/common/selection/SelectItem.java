package se.melsom.warehouse.common.selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.data.vo.ItemVO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Vector;

public class SelectItem {
	private static final Logger logger = LoggerFactory.getLogger(SelectItem.class);

	private final SelectItemTableModel tableModel;
	private final SelectItemListener selectItemListener;
	private final SelectItemView view;

    public SelectItem(SelectItemListener selectItemListener, Dialog parentView) {
    	this.selectItemListener = selectItemListener;
		this.tableModel = new SelectItemTableModel();
    	this.view = new SelectItemView(this, tableModel, parentView);
		view.setAcceptButtonEnabled(false);
	}

	public void selectItem(Vector<ItemVO> items) {
		tableModel.setItemList(items);
		view.setVisible(true);
	}

	public void search(String searchKey) {
		logger.debug("Search key=[{}]", searchKey);

		if (searchKey.length() > 1) {
			tableModel.searchFor(searchKey);
			view.setSelectedItemRowIndex(-1);
		}
	}

	public void listItemSelected(int rowIndex) {
		if (rowIndex >= 0) {
			view.setAcceptButtonEnabled(true);
			selectItemListener.handleSelectedItem(tableModel.get(rowIndex));
		} else {
			view.setAcceptButtonEnabled(false);
		}
	}

	public void valueChanged(ListSelectionEvent e) {
//		int selectedRowIndex = view.getSelectedTableRowIndex();
//
//		if (selectedRowIndex >= 0) {
//			view.setAcceptButtonEnabled(true);
//			selectedItem = tableModel.get(selectedRowIndex);
//		} else {
//			selectedItem = null;
//			view.setAcceptButtonEnabled(false);
//		}
//
//		logger.trace("Selected item=" + selectedItem);
	}
}
