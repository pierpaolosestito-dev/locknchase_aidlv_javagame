package application.model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ChangeIdentityBonus {
	private int x,y;
    private Image img;
    public ChangeIdentityBonus(int x,int y) {
    	this.x=x;
    	this.y=y;
    	this.img = new ImageIcon("src/main/java/application/resources/gameobject/bluehat.png").getImage();
    }
    public Image getImg() {
		return img;
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
}
