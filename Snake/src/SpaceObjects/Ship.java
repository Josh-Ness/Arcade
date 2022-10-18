
package SpaceObjects;


import SpaceGame.SpaceController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 *
 * @author Josh Ness
 */
public class Ship extends ControlledGameObject {

    public Ship(int xPosition, int yPosition, Color color, SpaceController control) {
        super(xPosition, yPosition, color, control);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        Polygon ship = new Polygon();
        ship.addPoint(xPosition, yPosition);
        ship.addPoint(xPosition-30, yPosition+60);
        ship.addPoint(xPosition+30, yPosition+60);
        g.fillPolygon(ship);
        Polygon p1 = new Polygon();
        p1.addPoint(xPosition-30, yPosition+60);
        p1.addPoint(xPosition -16, yPosition+60);
        p1.addPoint(xPosition-23, yPosition+75);
        g.fillPolygon(p1);
        Polygon p3 = new Polygon();
        p3.addPoint(xPosition+30, yPosition+60);
        p3.addPoint(xPosition+16, yPosition+60);
        p3.addPoint(xPosition+23, yPosition+75);
        g.fillPolygon(p3);
        Polygon p2 = new Polygon();
        p2.addPoint(xPosition-7, yPosition+60);
        p2.addPoint(xPosition+7, yPosition+60);
        p2.addPoint(xPosition, yPosition+75);
        g.fillPolygon(p2);
        g.fillRect(xPosition-3, yPosition-8, 7, 15);
    }
    


    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(xPosition-25, yPosition, 55, 55);
        return bounds;
    }

    //interacts with the keyboard controller class
    @Override
    public void move() {
        if (control.getLeftKeyStatus() == true) {
            xPosition -= 5;
        }
        if (control.getRightKeyStatus() == true) {
            xPosition += 5;
        }
    }
}
