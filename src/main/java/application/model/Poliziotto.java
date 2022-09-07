
package application.model;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import application.ai.inputfacts.MinDistances;


public class Poliziotto extends GameObject{

    private String name;
 
    private Image img;
    private int type;
    private int previousAction;
    private char oldValue;
    private ArrayList<MinDistances> minDistances;
  
    public Poliziotto(Coordinates position,int type) {
        super(position);
        this.minDistances = new ArrayList<>();
        this.oldValue = 'T';
        switch(type) {
        case 0:
        	this.name="scaredy";
        	this.previousAction = 3;
        	break;
        case 1:
        	this.name="silly";
        	this.previousAction = 3;
        	break;
        case 2:
        	this.name="smarty";
        	this.previousAction = 3;
        	break;
        case 3:
        	this.name="stiffy";
        	this.previousAction = 3;
        	break;
        default:
        	break;
        }
       this.type=type;
       this.img = new ImageIcon("src/main/java/application/resources/gameobject/"+name+".png").getImage();
       
     
    }
    public void setOldValue(char oldValue) {
		this.oldValue = oldValue;
	}
    public char getOldValue() {
		return oldValue;
	}
    public int getPreviousAction() {
		return previousAction;
	}
    public void setPreviousAction(int previousAction) {
		this.previousAction = previousAction;
	}
    public int getType() {
		return type;
	}
    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg() {
        return img;
    }
    
    public void addMinDistances(MinDistances d) {
    	this.minDistances.add(d);
    }
    public ArrayList<MinDistances> getMinDistances() {
		return minDistances;
	}
    public void clearMinDistances() {
    	this.minDistances.clear();
    }
   
    
}
