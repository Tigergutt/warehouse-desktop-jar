package se.melsom.warehouse.report.component.property;

/**
 * The enum True type font.
 */
public enum TrueTypeFont {
    /**
     * Fm sans bold true type font.
     */
    FM_SANS_BOLD("/ttf/forsvarsmakten/ForsvarsmaktenSans-Bold.ttf"),
    /**
     * Fm sans bold italic true type font.
     */
    FM_SANS_BOLD_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-BoldItalic.ttf"),
    /**
     * Fm sans condensed true type font.
     */
    FM_SANS_CONDENSED("/ttf/forsvarsmakten/ForsvarsmaktenSans-Condensed.ttf"),
    /**
     * Fm sans light true type font.
     */
    FM_SANS_LIGHT("/ttf/forsvarsmakten/ForsvarsmaktenSans-Light.ttf"),
    /**
     * Fm sans light italic true type font.
     */
    FM_SANS_LIGHT_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-LightItalic.ttf"),
    /**
     * Fm sans regular true type font.
     */
    FM_SANS_REGULAR("/ttf/forsvarsmakten/ForsvarsmaktenSans-Regular.ttf"),
    /**
     * Fm sans regular italic true type font.
     */
    FM_SANS_REGULAR_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-RegularItalic.ttf"),
    /**
     * Fm sans stencil true type font.
     */
    FM_SANS_STENCIL("/ttf/forsvarsmakten/ForsvarsmaktenSans-Stencil.ttf");
	
	private String path;
	
	TrueTypeFont(String path) {
		this.path = path;
	}

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
		return path;
	}
}
