package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.ai.inputfacts.inPath;
import application.model.GemManager.TimerGem;

public class ChangeIdentityManager {

    private int numeroRandom;
    private Random r;
    private Game game;
    private char oldValue;
    private static ChangeIdentityManager instance = null;
   static final List<Integer> nums = new ArrayList<Integer>() {{
    add(16);
    add(34);
    add(86);
    add(98);
    add(17);
    add(45);
    add(7);
    add(9);
    add(71);
    add(30);
    add(43);
    add(24);
    add(92);
    add(52);
    add(61);
    add(25);
    add(69);
    add(52);
}};
 
    private ChangeIdentityManager() {
        this.numeroRandom = 0;
        this.r = new Random();
        this.game = Game.getInstance();
    }
    
    public static ChangeIdentityManager getInstance() {
    	if(instance == null)
    		instance = new ChangeIdentityManager();
    	return instance;
    }
    
    public boolean tryToPutChangeIdentityInMap(boolean helpLock){
       this.numeroRandom = r.nextInt(100+1);
       if(!helpLock) {
    	   if(!nums.contains(numeroRandom))
           return false;
       //Se numeroRandom rispetta qualche proprietà
       }
       System.out.println("Sto provando a inserire il changeIdentity");
       if(game.existsCiB()) {
    	   System.out.println("Esiste già un changeIdentity");
           return false;
       }
       inPath randomPercorso = game.getPercorso().get(r.nextInt(game.getPercorso().size()));
       if(helpLock) {
    	   randomPercorso = game.getPercorsoVicinoLock().get(r.nextInt(game.getPercorsoVicinoLock().size()));
       }
       
       if(game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == 'W'||game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == 'L' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '0' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '1' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '2'||game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '3' ){
           return false;
       }
       this.oldValue = game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()];
       
       game.setCiB(new ChangeIdentityBonus(randomPercorso.getXP(),randomPercorso.getYP()));
       game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] = 'K';
       
       //int random_integer = rand.nextInt(upperbound-lowerbound) + lowerbound;
      
       TimerChangeIdentity g = new TimerChangeIdentity(oldValue,randomPercorso.getXP(),randomPercorso.getYP());
       Thread r = new Thread(g);
       r.start();
       System.out.println("HO INSERITO IL CAMBIO IDENTITA A: " + randomPercorso.getXP() + " " + randomPercorso.getYP());
       return true;
    }
    
 class TimerChangeIdentity implements Runnable{
     private int x,y;
     private char oldValue;
    

        public TimerChangeIdentity(char oldValue,int x, int y) {
            this.x = x;
            this.y = y;
            
        }

        @Override
        public void run() {
         try {
             Thread.sleep(30000);
             if(game.getMappa()[x][y] == 'K'){
                game.getMappa()[x][y]=oldValue;
             game.setCiBNull();
             System.out.println("Sono partito");
         }
         } catch (InterruptedException ex) {
             Logger.getLogger(GemManager.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        }
        
     
 } 
    
}

