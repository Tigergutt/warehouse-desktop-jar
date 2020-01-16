package se.melsom.warehouse.presentation.common;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class QuestionView {
	public static final int NO = 1;
	public static final int YES = 0;
	private String title;
	private String message;

	public QuestionView(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public int show(JFrame parent) {
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
	}

}
