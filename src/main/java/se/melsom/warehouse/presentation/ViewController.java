package se.melsom.warehouse.presentation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.*;

@Deprecated
public abstract class ViewController implements ActionListener, FocusListener, KeyListener, ComponentListener, TableModelListener, ListSelectionListener {
    public abstract JComponent getView();

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
