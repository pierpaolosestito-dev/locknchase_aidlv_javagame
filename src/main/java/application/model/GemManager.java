
package application.model;

import application.ai.inputfacts.inPath;
import java.util.*;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GemManager {

    private int numeroRandom;
    private Random r;
    private Game game;
    private char oldValue;
    private static GemManager instance = null;
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
 
    private GemManager() {
        this.numeroRandom = 0;
        this.r = new Random();
        this.game = Game.getInstance();
    }
    
    public static GemManager getInstance() {
    	if(instance == null)
    		instance = new GemManager();
    	return instance;
    }
    
    public boolean tryToPutGemInMap(){
       this.numeroRandom = r.nextInt(100+1);
       if(!nums.contains(numeroRandom))
           return false;
       //Se numeroRandom rispetta qualche proprietà
       System.out.println("Sto provando a inserire la gemma");
       if(game.existsGem()) {
    	   System.out.println("Esiste già una gemma");
           return false;
       }
       
       
       inPath randomPercorso = game.getPercorso().get(r.nextInt(game.getPercorso().size()));
       if(game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == 'W'||game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == 'L' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '0' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '1' || game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '2'||game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] == '3' ){
           return false;
       }
       this.oldValue = game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()];
       int sceltaGemma = r.nextInt(4-1)+1;
       game.setGem(new Gem(randomPercorso.getXP(),randomPercorso.getYP(),sceltaGemma));
       game.getMappa()[randomPercorso.getXP()][randomPercorso.getYP()] = 'G';
       
       //int random_integer = rand.nextInt(upperbound-lowerbound) + lowerbound;
      
       TimerGem g = new TimerGem(oldValue,randomPercorso.getXP(),randomPercorso.getYP());
       Thread r = new Thread(g);
       r.start();
       System.out.println("HO INSERITO LA GEMMA A: " + randomPercorso.getXP() + " " + randomPercorso.getYP());
       return true;
    }
    
 class TimerGem implements Runnable{
     private int x,y;
     private char oldValue;

        public TimerGem(char oldValue, int x, int y) {
            this.x = x;
            this.y = y;
            this.oldValue = oldValue;
        }

        @Override
        public void run() {
         try {
             Thread.sleep(20000);
             if(game.getMappa()[x][y] == 'G'){
                game.getMappa()[x][y]=oldValue;
             game.setGemNull();
             System.out.println("Sono partito");
         }
         } catch (InterruptedException ex) {
             Logger.getLogger(GemManager.class.getName()).log(Level.SEVERE, null, ex);
         }
            
        }
        
     
 } 
    
}
