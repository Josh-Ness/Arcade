
package SpaceObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public class Shield extends GameObject {

    private int width;
    private int height;

    public Shield(int xPosition, int yPosition, int width, int height, Color color) {
        super(xPosition, yPosition, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xPosition, yPosition, width, height);
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition, yPosition, width, height);
        return bounds;
    }
}
