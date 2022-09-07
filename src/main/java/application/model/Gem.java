
package application.model;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Gem {
    private int x,y;
    private Image img;
    private int value;

    public Gem(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.img = new ImageIcon("src/main/java/application/resources/gameobject/gem"+type+".png").getImage();
        this.value = type * 10;           
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public Image getImg() {
        return img;
    }
    
    
    
    

    
}
