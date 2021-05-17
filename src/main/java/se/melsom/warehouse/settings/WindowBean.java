package se.melsom.warehouse.settings;

public class WindowBean {
    int x = 0;
    int y = 0;
    int width = 500;
    int height = 200;
    boolean isVisible = true;

    public WindowBean() {}

    public WindowBean(int x, int y, int width, int height, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isVisible = isVisible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d, %d, %d]:%s", x, y, width, height, isVisible ? "Visible" : "Hidden");
    }
}
