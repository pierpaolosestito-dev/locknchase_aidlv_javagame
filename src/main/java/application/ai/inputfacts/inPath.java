
package application.ai.inputfacts;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("inPath")
public class inPath {
    @Param(0)
    private Integer xP;
    @Param(1)
    private Integer yP;
    
    public inPath(){
        //Default constructor is required to be Java Beans Class
    }
    
    public inPath(Integer xP, Integer yP) {
    	this.xP = xP;
    	this.yP = yP;
    }

    public void setxP(Integer xP) {
        this.xP = xP;
    }

    public Integer getXP() {
        return xP;
    }

    public void setyP(Integer yP) {
        this.yP = yP;
    }

    public Integer getYP() {
        return yP;
    }

    
}
