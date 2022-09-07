package application.model;

import application.ai.inputfacts.ClosestCoin;
import application.ai.inputfacts.MinDistances;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;


public class Lock extends GameObject {

   
    
    private Integer previousAction;
    private Image lockImage;
    private int tentativiRimasti;
    private boolean powerful;
    //IDEA RIVISITATA:
    private ArrayList<MinDistances> minDistances;
    private int actualScore;
    private ArrayList<ClosestCoin> closestCoin;
    private ArrayList<MinDistances> minDistancesClosestCoin;
 
    
  
    public Lock(Coordinates position) {
        super(position);
        this.tentativiRimasti = 3;
        this.actualScore = 0;
        this.previousAction = 1;
        this.powerful = false;
        this.lockImage = new ImageIcon("src/main/java/application/resources/gameobject/lock_blackhat.png").getImage();
        minDistances = new ArrayList<>();
        closestCoin = new ArrayList<>();
        minDistancesClosestCoin = new ArrayList<>();
    }
    public ArrayList<MinDistances> getMinDistancesClosestCoin() {
		return minDistancesClosestCoin;
	}
    public int getTentativiRimasti() {
		return this.tentativiRimasti;
	}
    public void decrementaTentativi() {
    	this.tentativiRimasti -=1;
    }
    public Integer getPreviousAction() {
		return previousAction;
	}
    
    public void setPreviousAction(Integer previousAction) {
		this.previousAction = previousAction;
	}
    public void clearArrays() {
        minDistancesClosestCoin.clear();
        minDistances.clear();
        closestCoin.clear();
       
    }
    
    
    public void setActualScore(int actualScore) {
        this.actualScore = actualScore;
    }
    
    public int getActualScore() {
        return actualScore;
    }
   
    public void addMinDistancesClosestCoin(MinDistances c) {
    	minDistancesClosestCoin.add(c);
    }
    public void addClosestCoin(ClosestCoin c){
        closestCoin.add(c);
    }

    
    
    public void setLockImage(Image lockImage) {
        this.lockImage = lockImage;
    }

    public Image getLockImage() {
        return lockImage;
    }
    
    
    public void addMinDistances(MinDistances d){
        this.minDistances.add(d);
    }

    public ArrayList<MinDistances> getMinDistances() {
        return minDistances;
    }
    public ArrayList<ClosestCoin> getClosestCoin() {
		return closestCoin;
	}
	public void setBlueHat() {
		this.powerful=true;
		this.lockImage = new ImageIcon("src/main/java/application/resources/gameobject/lock_bluehat.png").getImage();
		
	}
	public boolean getPowerful() {
		return this.powerful;
	}
	public void resetBlackHat() {
		this.lockImage = new ImageIcon("src/main/java/application/resources/gameobject/lock_blackhat.png").getImage();
		this.powerful = false;
	}
   
    
}
