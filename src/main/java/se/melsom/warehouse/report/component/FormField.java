package se.melsom.warehouse.report.component;

import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.Position;

public class FormField extends Frame {
	private final TextBox caption;
	private final TextBox value;

    public FormField(float x, float y, float width, float captionHeight, float valueHeight) {
		super(x, y, width, captionHeight + valueHeight);
		
		caption = new TextBox(x, y, width, captionHeight);
		value = new TextBox(x, y + captionHeight, width, valueHeight);
		
		setLines(0.5f);
		
		caption.setInsets(1);
		caption.setInsets(2, Position.LEFT);
		caption.setLines(0.1f);
		addComponent(caption);
		
		value.setInsets(1);		
		value.setInsets(2, Position.LEFT);
		addComponent(value);
	}

    public TextBox getCaption() {
		return caption;
	}

    public void setCaptionText(String text) {
		caption.setText(text);
	}

    public void setCaptionAlignment(Alignment alignment) {
		caption.setAlignment(alignment);
	}

    public TextBox getValue() {
		return value;
	}

    public void setValueText(String text) {
		value.setText(text);
	}

    public void setValueAlignment(Alignment alignment) {
		value.setAlignment(alignment);
	}
}
