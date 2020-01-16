package se.melsom.warehouse.presentation.common;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageView {
	private String title;
	private String message;

	public MessageView(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public void show(JFrame parent) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
