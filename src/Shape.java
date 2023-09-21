import java.awt.Color;
import java.io.Serializable;

class Shape implements Serializable {
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private Color color;
	private String shapeName;
	private String textToDisplay;

	public Shape(int x1, int y1, int x2, int y2, String shapeName, Color color) {
		super();
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.shapeName = shapeName;
		this.color = color;
	}
	
	public Shape(int x1, int y1, String shapeName, Color color, String textToDisplay) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.shapeName = shapeName;
		this.color = color;
		this.textToDisplay = textToDisplay;
	}
	
	public String getTextToDisplay() {
		return textToDisplay;
	}

	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}
			
}