
package GameEntities;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


/**
 *
 * @author Josh Ness
 */
public class Apple {

    public static void createApple(int appleX, int appleY, int width, int height, int unitSize){
        Random rand = new Random();
        appleX = rand.nextInt((int)(width/unitSize))*unitSize;
        appleY = rand.nextInt((int)(height/unitSize)) * unitSize;
    }
    
    public static void draw(Graphics g, int appleX, int appleY, int unitSize){
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, unitSize, unitSize);
    }
}
