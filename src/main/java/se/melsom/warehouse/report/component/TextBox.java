package se.melsom.warehouse.report.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.melsom.warehouse.report.PdfReportRenderer;
import se.melsom.warehouse.report.component.property.TrueTypeFont;

public class TextBox extends Frame {
	private static final Logger logger = LoggerFactory.getLogger(PdfReportRenderer.class);
	private TrueTypeFont font = TrueTypeFont.FM_SANS_REGULAR;
	private String text = "";

    public TextBox(float x, float y, float width, float height) {
		super(x, y, width, height);
		
		if (logger.isDebugEnabled()) {
			text = "ÅjX01nnnn";
		}
	}
	
    public TrueTypeFont getFont() {
		return font;
	}

    public void setFont(TrueTypeFont font) {
		this.font = font;
	}

    public String getText() {
		return text;
	}

    public void setText(String text) {
		this.text = text;
	}
}
