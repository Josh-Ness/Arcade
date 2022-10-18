

package Game;

/**
 *
 * @author Josh Ness
 */
public class PanelAdjustments {
    private static int unitSize =25;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGTH = 600;
    
    
    public void setUnitSize(int unitSize){
        this.unitSize = unitSize;
    }
    
    
    public static int getUnitSize(){
        return unitSize;
    }
    
    public static int getWidth(){
        return GAME_WIDTH;
    }
    public static int getHeight(){
        return GAME_HEIGTH;
    }
}
