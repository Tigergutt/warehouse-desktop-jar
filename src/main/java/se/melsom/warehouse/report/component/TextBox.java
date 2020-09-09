package se.melsom.warehouse.report.component;

import org.apache.log4j.Logger;

import se.melsom.warehouse.report.PdfReportRenderer;
import se.melsom.warehouse.report.component.property.TrueTypeFont;

/**
 * The type Text box.
 */
public class TextBox extends Frame {
	private static Logger logger = Logger.getLogger(PdfReportRenderer.class);
	private TrueTypeFont font = TrueTypeFont.FM_SANS_REGULAR;
	private String text = "";

    /**
     * Instantiates a new Text box.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public TextBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		if (logger.isDebugEnabled()) {
			text = "ÅjX01nnnn";
		}
	}
	
//	public TextBox(float width, float height) {
//		super(0, 0, width, height);
//	}

    /**
     * Gets font.
     *
     * @return the font
     */
    public TrueTypeFont getFont() {
		return font;
	}

    /**
     * Sets font.
     *
     * @param font the font
     */
    public void setFont(TrueTypeFont font) {
		this.font = font;
	}

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
		return text;
	}

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
		this.text = text;
	}
}
