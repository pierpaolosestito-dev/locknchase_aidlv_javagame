/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package application.ai.inputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

/**
 *
 * @author Utente
 */
@Id("closestCoin")
public class ClosestCoin {
    @Param(0)
    private Integer xCoin;
    @Param(1)
    private Integer yCoin;
    /*@Param(2)
    private Integer distance;*/

    public ClosestCoin() {
		// TODO Auto-generated constructor stub
	}
    
    public ClosestCoin(Integer xCoin, Integer yCoin/*, Integer distance*/) {
    	this.xCoin = xCoin;
    	this.yCoin = yCoin;
    	//this.distance = distance;
    }

    public void setxCoin(Integer xCoin) {
        this.xCoin = xCoin;
    }

    public void setyCoin(Integer yCoin) {
        this.yCoin = yCoin;
    }

    public Integer getXCoin() {
        return xCoin;
    }

    public Integer getYCoin() {
        return yCoin;
    }
 /*   
    public Integer getDistance() {
		return distance;
	}
    
    public void setDistance(Integer distance) {
		this.distance = distance;
	}
    */
}
