package se.melsom.warehouse.report;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import se.melsom.warehouse.report.component.Component;
import se.melsom.warehouse.report.component.Frame;
import se.melsom.warehouse.report.component.Page;
import se.melsom.warehouse.report.component.Table;
import se.melsom.warehouse.report.component.TableRow;
import se.melsom.warehouse.report.component.TextBox;
import se.melsom.warehouse.report.component.property.Line;
import se.melsom.warehouse.report.component.property.Point;
import se.melsom.warehouse.report.component.property.Position;
import se.melsom.warehouse.report.component.property.TrueTypeFont;

public class PdfReportRenderer {
	private static Logger logger = Logger.getLogger(PdfReportRenderer.class);
	private PDDocument document;
	private PDPage currentPage = null;
	private float pageHeight;
	private Map<TrueTypeFont, PDFont> fontsInUse = new HashMap<>();

	public PdfReportRenderer() {
		document = new PDDocument();
	}
	
	public void save(String path) throws IOException {
		document.save(path);
		document.close();
	}

	public void render(Vector<Page> pages) {
		logger.debug("Render pages count=" + pages.size());
		
		for (Page page : pages) {
			render(page);
		}
	}

	public void render(Page page) {
		logger.debug("Render page.");
		pageHeight = page.getHeight();
		
		switch (page.getOrientation()) {
		case LANDSCAPE:
			currentPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
			break;
			
		case PORTRAIT:
			currentPage = new PDPage(PDRectangle.A4);
			break;
		}
		
		document.addPage(currentPage);
		
		for (Component component : page.getComponents()) {
			render(component);
		}
	}
	
	private void render(Component component) {
		logger.trace("Render component=" + component);
		if (component instanceof Frame) {
			Frame frame = (Frame) component;
			
			render(frame.getLines());
		}
		
		if (component instanceof Table) {
			render((Table) component);
		} else if (component instanceof TableRow) {
			render((TableRow) component);
		} else if (component instanceof TextBox) {
			render((TextBox) component);
		} else if (component instanceof Frame) {
			render((Frame) component);
		}
		
		for (Component child : component.getComponents()) {
			render(child);
		}
	}

	private void render(Frame component) {
		logger.trace("Render frame (nothing).");
	}
	
	private void render(TextBox component) {
		logger.trace("Render text box content.");
		logger.trace("Text=" + component.getText());

		if (component.getText() == null) {
			logger.warn("Null text in text box.");
			return;
		}

		try (PDPageContentStream contents = createContentStream()) {
			PDFont font = getPDFont(component.getFont());
			
			contents.beginText();	
			float leftInset = 0;
			if (component.getInset(Position.LEFT) != null) {
				leftInset = millimetersToPoints(component.getInset(Position.LEFT).getWidth());
			}

			float rightInset = 0;
			if (component.getInset(Position.RIGHT) != null) {
				rightInset = millimetersToPoints(component.getInset(Position.RIGHT).getWidth());
			}

			float topInset = 0;
			if (component.getInset(Position.TOP) != null) {
				topInset = millimetersToPoints(component.getInset(Position.TOP).getWidth());
			}

			float bottomInset = 0;
			if (component.getInset(Position.BOTTOM) != null) {
				bottomInset = millimetersToPoints(component.getInset(Position.BOTTOM).getWidth());
			}

			float availableVerticalGap = millimetersToPoints(component.getHeight());
			availableVerticalGap -= topInset;
			availableVerticalGap -= bottomInset;

			float availableHorizontalGap = millimetersToPoints(component.getWidth());
			availableHorizontalGap -= leftInset;
			availableHorizontalGap -= rightInset;
			
			float scaledFontSize = availableVerticalGap / getFontTotalHeight(font, 1);

			contents.setFont(font, scaledFontSize);
			
			logger.trace("Font total=" + font.getFontDescriptor().getFontBoundingBox().getHeight());
			logger.trace("Font ascent=" + font.getFontDescriptor().getAscent());
			logger.trace("Font xheight=" + font.getFontDescriptor().getXHeight());
			logger.trace("Font height=" + font.getFontDescriptor().getCapHeight());
			logger.trace("Font descent=" + font.getFontDescriptor().getDescent());

			float top = getFontMaxGap(font, scaledFontSize);
			float ascent = getFontAscentGap(font, scaledFontSize);
			float xHeight = getFontXHeight(font, scaledFontSize);
			float descent = getFontDescentGap(font, scaledFontSize);

			// Regarding text as a rectangle drawn from [left,bottom]
			float x = leftInset + millimetersToPoints(component.getX());
			float textHeight = getFontTotalHeight(font, scaledFontSize);
			float baseLine = millimetersToPoints(pageHeight - component.getY());
			
			switch (component.getAlignment()) {
			case CENTER:
				x += (availableHorizontalGap - getStringWidth(font, component.getText(), scaledFontSize)) / 2;
				break;
			
			case RIGHT:
				x += availableHorizontalGap - getStringWidth(font, component.getText(), scaledFontSize);
				break;
				
			default:
				break;
			}
			
			baseLine -= topInset;
			baseLine -= getFontBaseGap(font, scaledFontSize);

			contents.newLineAtOffset(x, baseLine);
			contents.showText(component.getText());
			contents.endText();
			
			if (logger.isDebugEnabled()) {
				float width = getStringWidth(font, component.getText(), scaledFontSize);
				contents.setLineWidth(1);

				float aboveline = millimetersToPoints(pageHeight - component.getY()) - topInset;;
				float topline = aboveline - top;
				contents.setStrokingColor(Color.MAGENTA);
				contents.moveTo(x, topline);
				contents.lineTo(x + width, topline);				
				contents.stroke();

				float ascentline = topline - ascent;
				contents.setStrokingColor(Color.GREEN);
				contents.moveTo(x, ascentline);
				contents.lineTo(x + width, ascentline);				
				contents.stroke();

				float baseline = ascentline - xHeight;
				contents.setStrokingColor(Color.RED);
				contents.moveTo(x, baseline);
				contents.lineTo(x + width, baseline);				
				contents.stroke();
				
				float descentline = baseline - descent;
				contents.setStrokingColor(Color.BLUE);
				contents.addRect(x, descentline, width, textHeight);
				contents.stroke();

			}
		} catch (IOException e) {
			logger.warn("", e);
		}
	}
	
	private void render(Table component) {
		logger.trace("Render table (nothing).");
	}
	
	private void render(TableRow component) {
		logger.trace("Render table row (nothing).");
	}
	
	private void render(Line[] lines) {
		logger.trace("Render lines.");
		for (Position position : Position.values()) {
			Line line = lines[position.ordinal()];
			
			if (line == null) {
				continue;
			}
			
//			if (position != Position.BOTTOM) {
//				continue;
//			}
			
			float pageTop = millimetersToPoints(pageHeight);
			float width = millimetersToPoints(line.getWidth());

			Point from = line.getFrom();
			float fromX = millimetersToPoints(from.getX());
			float fromY = pageTop - millimetersToPoints(from.getY());

			Point to = line.getTo();
			float toX = millimetersToPoints(to.getX());
			float toY = pageTop - millimetersToPoints(to.getY());
			
			// Ensuring that we get a nice rectangle.
			switch (position) {
			case TOP:
			case BOTTOM:
				fromX -= width / 2;
				toX += width / 2;
				break;
				
			case RIGHT:
			case LEFT:
				fromY += width / 2;
				toY -= width / 2;
				break;
			}
			
			try (PDPageContentStream contents = createContentStream()) {
				contents.setStrokingColor(Color.BLACK);
				contents.setLineWidth(width);
				contents.moveTo(fromX, fromY);
				contents.lineTo(toX, toY);
				contents.stroke();
				logger.trace("line([" + fromX + "," + fromY + "],[" + toX + "," + toX + "])");
			} catch (IOException e) {
				logger.warn("", e);
			}
		}
	}
	
	PDPageContentStream createContentStream() throws IOException {
		return new PDPageContentStream(document, currentPage, PDPageContentStream.AppendMode.APPEND, false);
	}
	
	
	/**
	 * Calculates the width of a string.
	 * 
	 * @param font
	 * @param text 
	 * @param fontSize fontSize in points.
	 * @return the calculated width in points.
	 */
	float getStringWidth(PDFont font, String text, float fontSize) {
		float width = 0;
		
		try {
			width = font.getStringWidth(text) / 1000f * fontSize;
		} catch (IOException e) {
			logger.warn("", e);
		}
		
		return width;
	}

	/**
	 * Calculates the total height acquired by a specific font.
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return the calculated height in points.
	 */
	float getFontTotalHeight(PDFont font, float fontSize) {
		float height = 0;
		
		height += font.getFontDescriptor().getFontBoundingBox().getHeight();
		
		return height / 1000f * fontSize;
	}

	/**
	 * Calculates acquired gap of a specific font.
	 * The gap is the distance between the base line and the max height.
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return gap in points.
	 */
	float getFontBaseGap(PDFont font, float fontSize) {
		float height = 0;
		
		height += font.getFontDescriptor().getFontBoundingBox().getHeight();
		height += font.getFontDescriptor().getDescent();
		
		return height / 1000f * fontSize;
	}

	/**
	 * Calculates the gap above the font ascent line.
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return the gap in points.
	 */
	float getFontMaxGap(PDFont font, float fontSize) {
		float height = 0;
		
		height += font.getFontDescriptor().getFontBoundingBox().getHeight();
		height -= font.getFontDescriptor().getAscent();
		height += font.getFontDescriptor().getDescent();
		
		return height / 1000f * fontSize;
	}

	/**
	 * Calculates the font ascent gap.
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return the gap in points.
	 */
	float getFontAscentGap(PDFont font, float fontSize) {
		float height = 0;
		
		height += font.getFontDescriptor().getAscent();
		height -= font.getFontDescriptor().getXHeight();
		
		return height / 1000f * fontSize;
	}

	/**
	 * Calculates the font x-height (height, above base line, of lower case letters).
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return the height in points.
	 */
	float getFontXHeight(PDFont font, float fontSize) {
		float height = 0;
		
		height += font.getFontDescriptor().getXHeight();
		
		return height / 1000f * fontSize;
	}

	/**
	 * Calculates the gap below the font base line.
	 * 
	 * @param font
	 * @param fontSize in points.
	 * @return the gap in points.
	 */
	float getFontDescentGap(PDFont font, float fontSize) {
		float height = 0;
		
		height -= font.getFontDescriptor().getDescent();
		
		return height / 1000f * fontSize;
	}
	
	/**
	 * Converting milimeters to points.
	 * 
	 * @param mm
	 * @return
	 */
	float millimetersToPoints(float mm) {
		return mm * 72f / 25.4f;
	}
	
	PDFont getPDFont(TrueTypeFont fontName) {
		PDFont font = fontsInUse.get(fontName);
		
		if (font == null) {
			logger.debug("Loading font=" + fontName);
			try {
				InputStream stream = PdfReportRenderer.class.getResourceAsStream(fontName.getPath());
				font = PDType0Font.load(document, stream);
				
				fontsInUse.put(fontName, font);
			} catch (IOException e) {
				logger.warn("Failed to load font=" + fontName , e);
				font = null;
			}
		}
		
		if (font == null) {
			switch (fontName) {
			case FM_SANS_BOLD:
			case FM_SANS_STENCIL:
				font = PDType1Font.HELVETICA_BOLD;
				break;
				
			case FM_SANS_BOLD_ITALIC:
				font = PDType1Font.HELVETICA_BOLD_OBLIQUE;
				break;
				
			case FM_SANS_LIGHT_ITALIC:
			case FM_SANS_REGULAR_ITALIC:
				font = PDType1Font.HELVETICA_OBLIQUE;
				break;
				
			default:
				font = PDType1Font.HELVETICA;
				break;
			}
			
			logger.debug("Using default font=" + font);
		}
		
		return font;
	}
}
