package se.melsom.warehouse.application.common;

import javax.swing.*;

public class MessageView {
	private final String title;
	private final String message;

    public MessageView(String title, String message) {
		this.title = title;
		this.message = message;
	}

    public void show(JFrame parent) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
