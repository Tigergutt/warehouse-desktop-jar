package se.melsom.warehouse.application.holdings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.melsom.warehouse.settings.PersistentSettings;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

@Component
public class StockLocationHoldingView implements AbstractStockLocationHoldingView, ComponentListener {
    @Autowired private PersistentSettings persistentSettings;

    public StockLocationHoldingView() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public void showView() {

    }

    @Override
    public void initialize(ContentModel tableModel) {

    }

    @Override
    public void updateState(ViewState state) {

    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
