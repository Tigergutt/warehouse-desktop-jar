package se.melsom.warehouse.report.component;

public class Page extends Component {
	public enum Orientation {
		PORTRAIT,
		LANDSCAPE
	}
	
	private Orientation orientation;
	private int pageNumber = 0; 
	private int pageCount = 0;
	
	public Page(Orientation orientation) {
		super(0, 0, 0, 0);
		setOrientation(orientation);
	}

	public Orientation getOrientation() {
		return orientation;
	}

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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
