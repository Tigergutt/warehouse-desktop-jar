package se.melsom.warehouse.presentation.common;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The type Question view.
 */
public class QuestionView {
    /**
     * The constant NO.
     */
    public static final int NO = 1;
    /**
     * The constant YES.
     */
    public static final int YES = 0;
	private String title;
	private String message;

    /**
     * Instantiates a new Question view.
     *
     * @param title   the title
     * @param message the message
     */
    public QuestionView(String title, String message) {
		this.title = title;
		this.message = message;
	}

    /**
     * Show int.
     *
     * @param parent the parent
     * @return the int
     */
    public int show(JFrame parent) {
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
	}

}
