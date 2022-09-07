
package application.ai.inputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("coin")
public class Coin {
    @Param(0)
    private Integer xP;
    @Param(1)
    private Integer yP;
    
    public Coin(){
        
    }
    
    public Coin(Integer xP, Integer yP) {
    	this.xP = xP;
    	this.yP = yP;
    }
    
    public void setxP(Integer xP){
        this.xP = xP;
    }
    
    public Integer getXP(){
        return this.xP;
    }
    
    public void setyP(Integer yP){
        this.yP = yP;
    }
    
    public Integer getYP(){
        return this.yP;
    }
}
