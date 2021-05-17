package se.melsom.logging;

//public class TextAreaAppender extends AppenderSkeleton implements DocumentListener {
//	private static JTextArea textArea;
//	private static TextAreaAppender instance;
//
//    public TextAreaAppender() {
//		instance = this;
//	}
//
//    public static void setTextArea(JTextArea textArea) {
//		TextAreaAppender.textArea = textArea;
//
//		textArea.getDocument().addDocumentListener(TextAreaAppender.instance);
//	}
//
//	public void append(LoggingEvent loggingEvent) {
//		if (textArea == null) {
//			return;
//		}
//
//		textArea.append(layout.format(loggingEvent));
//	}
//
//	public boolean requiresLayout() {
//		return true;
//	}
//
//	public void close() {
//	}
//
//	public void changedUpdate(DocumentEvent event) {
//	}
//
//	public void removeUpdate(DocumentEvent event) {
//	}
//
//	public void insertUpdate(DocumentEvent event) {
//	}
//}