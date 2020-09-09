package se.melsom.warehouse.presentation.common;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The type Message view.
 */
public class MessageView {
	private String title;
	private String message;

    /**
     * Instantiates a new Message view.
     *
     * @param title   the title
     * @param message the message
     */
    public MessageView(String title, String message) {
		this.title = title;
		this.message = message;
	}

    /**
     * Show.
     *
     * @param parent the parent
     */
    public void show(JFrame parent) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
