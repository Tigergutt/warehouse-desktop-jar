package se.melsom.warehouse.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.*;
import java.util.Vector;

/**
 * Base class for common Presentation Model functionality..
 */
@Component
public abstract class AbstractPresentationModel implements ActionListener, FocusListener, KeyListener, ComponentListener, TableModelListener, ListSelectionListener {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPresentationModel.class);

	static Vector<AbstractPresentationModel> registeredPresentationModels = new Vector<>();

	public AbstractPresentationModel() {
		logger.debug("Adding presentation model: {}.", this.getClass().getSimpleName());
		registeredPresentationModels.add(this);
	}

	public abstract void showView();

	public abstract void initialize();

    public boolean isValidNumber(String currentValue, char ch, int maxDigitCount) {
		if (!Character.isDigit(ch)) {
			return false;
		}
		
		if (currentValue.length() + 1 > maxDigitCount) {
			return false;
		}

        return currentValue.length() != 0 || ch != '0';
    }

    public boolean isValidMinutes(String currentValue, char ch) {
		if (!Character.isDigit(ch)) {
			return false;
		}
		
		int digit = Integer.parseInt(Character.toString(ch));

		if (currentValue.length() == 0) {
            return digit != 0 && digit <= 6;
		} else if (currentValue.length() == 1) {
            return !currentValue.equals("6") || digit == 0;
		} else {
			return false;
		}
    }

    public boolean isValidTime(String currentValue, Character ch) {
		switch (currentValue.length()) {
		case 0:
		case 1:
			try {
				Integer.parseInt(Character.toString(ch));
			} catch (Exception e) {
				logger.warn("illegal hour number [{}].", currentValue);
				return false;
			}
			break;
			
		case 2:
			if (ch != ':') {
				return false;
			}
			break;
			
		case 3:
		case 4:
			try {
				Integer.parseInt(Character.toString(ch));
			} catch (Exception e) {
				logger.warn("illegal minute number [{}].", currentValue);
				return false;
			}
			break;

		default:
			return false;
		}
		
		return true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
	}

	@Override
	public void tableChanged(TableModelEvent e) {
	}
}
