package se.melsom.warehouse.report.component;

import se.melsom.warehouse.report.component.property.Alignment;
import se.melsom.warehouse.report.component.property.Position;

/**
 * The type Form field.
 */
public class FormField extends Frame {
	private TextBox caption;
	private TextBox value;

    /**
     * Instantiates a new Form field.
     *
     * @param x             the x
     * @param y             the y
     * @param width         the width
     * @param captionHeight the caption height
     * @param valueHeight   the value height
     */
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

    /**
     * Gets caption.
     *
     * @return the caption
     */
    public TextBox getCaption() {
		return caption;
	}

    /**
     * Sets caption text.
     *
     * @param text the text
     */
    public void setCaptionText(String text) {
		caption.setText(text);
	}

    /**
     * Sets caption alignment.
     *
     * @param alignment the alignment
     */
    public void setCaptionAlignment(Alignment alignment) {
		caption.setAlignment(alignment);
	}

    /**
     * Gets value.
     *
     * @return the value
     */
    public TextBox getValue() {
		return value;
	}

    /**
     * Sets value text.
     *
     * @param text the text
     */
    public void setValueText(String text) {
		value.setText(text);
	}

    /**
     * Sets value alignment.
     *
     * @param alignment the alignment
     */
    public void setValueAlignment(Alignment alignment) {
		value.setAlignment(alignment);
	}
}
