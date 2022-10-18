
package SpaceObjects;

import SpaceInterfaces.Drawable;
import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public abstract class GameObject implements Drawable{
    protected int xPosition;
    protected int yPosition;
    protected Color color;
    
    public GameObject(int xPosistion, int yPosistion, Color color) {
        this.xPosition = xPosistion;
        this.yPosition = yPosistion;
        this.color = color;
    }

    public abstract Rectangle getBounds();

    public int getXPosistion() {
        return xPosition;
    }

    public int getYPosistion() {
        return yPosition;
    }

    public Color getColor() {
        return color;
    }

    public void setXPosistion(int Xposistion) {
        this.xPosition = Xposistion;
    }

    public void setYPosistion(int yPosistion) {
        this.yPosition = yPosistion;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isColliding(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }
}
