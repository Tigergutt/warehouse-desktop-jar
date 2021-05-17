package se.melsom.warehouse.report.component.property;

public enum TrueTypeFont {
    FM_SANS_BOLD("/ttf/forsvarsmakten/ForsvarsmaktenSans-Bold.ttf"),
    FM_SANS_BOLD_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-BoldItalic.ttf"),
    FM_SANS_CONDENSED("/ttf/forsvarsmakten/ForsvarsmaktenSans-Condensed.ttf"),
    FM_SANS_LIGHT("/ttf/forsvarsmakten/ForsvarsmaktenSans-Light.ttf"),
    FM_SANS_LIGHT_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-LightItalic.ttf"),
    FM_SANS_REGULAR("/ttf/forsvarsmakten/ForsvarsmaktenSans-Regular.ttf"),
    FM_SANS_REGULAR_ITALIC("/ttf/forsvarsmakten/ForsvarsmaktenSans-RegularItalic.ttf"),
    FM_SANS_STENCIL("/ttf/forsvarsmakten/ForsvarsmaktenSans-Stencil.ttf");
	
	private final String path;
	
	TrueTypeFont(String path) {
		this.path = path;
	}

    public String getPath() {
		return path;
	}
}
