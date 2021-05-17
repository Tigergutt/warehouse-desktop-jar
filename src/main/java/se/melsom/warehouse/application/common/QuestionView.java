package se.melsom.warehouse.application.common;

import javax.swing.*;

public class QuestionView {
    public static final int NO = 1;
    public static final int YES = 0;
	private final String title;
	private final String message;

    public QuestionView(String title, String message) {
		this.title = title;
		this.message = message;
	}

    public int show(JFrame parent) {
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
	}
}
