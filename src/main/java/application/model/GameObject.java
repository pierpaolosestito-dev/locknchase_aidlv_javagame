
package application.model;

public class GameObject {
    private Coordinates position;
    //dist -> da trasferire solo in Lock e Poliziotto
   

    public GameObject(){
        position = new Coordinates(-1,-1);
    }
    public GameObject(Coordinates position) {
        this.position = position;
       
    }
    
    public Coordinates getPosition(){
        return position;
    }
    public void setPosition(Coordinates position){
        this.position = position;
    }
    
}
