package se.melsom.warehouse.report.component;

/**
 * The type Page.
 */
public class Page extends Component {
    /**
     * The enum Orientation.
     */
    public enum Orientation {
        /**
         * Portrait orientation.
         */
        PORTRAIT,
        /**
         * Landscape orientation.
         */
        LANDSCAPE
	}
	
	private Orientation orientation;
	private int pageNumber = 0; 
	private int pageCount = 0;

    /**
     * Instantiates a new Page.
     *
     * @param orientation the orientation
     */
    public Page(Orientation orientation) {
		super(0, 0, 0, 0);
		setOrientation(orientation);
	}

    /**
     * Gets orientation.
     *
     * @return the orientation
     */
    public Orientation getOrientation() {
		return orientation;
	}

    /**
     * Sets orientation.
     *
     * @param orientation the orientation
     */
    public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
		switch (orientation) {
		case LANDSCAPE:
			setWidth(297);
			setHeight(210);
			break;
		
		case PORTRAIT:
			setWidth(210);
			setHeight(297);
			break;
		}
	}

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
		return pageNumber;
	}

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

    /**
     * Gets page count.
     *
     * @return the page count
     */
    public int getPageCount() {
		return pageCount;
	}

    /**
     * Sets page count.
     *
     * @param pageCount the page count
     */
    public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
