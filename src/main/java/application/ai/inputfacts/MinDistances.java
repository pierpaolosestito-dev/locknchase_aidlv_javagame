package application.ai.inputfacts;


import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *mindistances(Xp,Yp,Xg,Yg,D)/
package application.ai;

/**
 *
 * @author Utente
 */
@Id("mindistances")
public class MinDistances {
    @Param(0)
    private Integer xLock;
    @Param(1)
    private Integer yLock;
    @Param(2)
    private Integer xPm;
    @Param(3)
    private Integer yPm;
    @Param(4)
    private Integer distanceBetweenLocknPoliceMan;

    public MinDistances() {
        //Default constructor is required to be a Java Beans Class
    }
    
    public MinDistances(Integer xLock, Integer yLock, Integer xPm, Integer yPm, Integer distance) {
    	this.xLock = xLock;
    	this.yLock = yLock;
    	this.xPm = xPm;
    	this.yPm = yPm;
    	this.distanceBetweenLocknPoliceMan = distance;
    }

    public void setxLock(Integer xLock) {
        this.xLock = xLock;
    }

    public Integer getXLock() {
        return xLock;
    }

    public void setyLock(Integer yLock) {
        this.yLock = yLock;
    }

    public Integer getYLock() {
        return yLock;
    }

    public void setxPm(Integer xPm) {
        this.xPm = xPm;
    }

    public Integer getXPm() {
        return xPm;
    }

    public void setyPm(Integer yPm) {
        this.yPm = yPm;
    }

    public Integer getYPm() {
        return yPm;
    }

    public void setDistanceBetweenLocknPoliceMan(Integer distanceBetweenLocknPoliceMan) {
        this.distanceBetweenLocknPoliceMan = distanceBetweenLocknPoliceMan;
    }

    public Integer getDistanceBetweenLocknPoliceMan() {
        return distanceBetweenLocknPoliceMan;
    }
    
    
    
    
    
    
}
