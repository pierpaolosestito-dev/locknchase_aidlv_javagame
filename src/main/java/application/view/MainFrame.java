
package application.view;

import application.GameLoop;
import application.ai.AILockBrain;
import application.ai.AIPoliceManBrain;
import application.ai.Test;
import application.ai.UniqueBrain;
import application.model.Game;
import java.awt.Color;
import java.awt.Event;

public  class MainFrame extends javax.swing.JFrame {

    private GamePanel gamePanel = new GamePanel();
    private HomePanel homepage = new HomePanel();
    private GameFinishedPanel restartExitPanel = new GameFinishedPanel(3);
    private static MainFrame m = null;
    private MainFrame() {
        
       
        this.setContentPane(homepage);
        this.setVisible(true);
        this.setSize(ResponsiveManager.JFRAME_WIDTH,ResponsiveManager.JFRAME_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static MainFrame getInstance() {
    	if(m==null)
    		m=new MainFrame();
    	return m;
    }
   

    
    public  void switchContent(int content) {
    	this.getContentPane().removeAll();
    	Game g2 = Game.getInstance();
    	switch(content) {
    		case 0:
    			 
    		        UniqueBrain unique = new UniqueBrain(g2);
    		        GameLoop g = new GameLoop(unique,gamePanel);
    		        Thread t = new Thread(g);
    		        t.start();
    		        this.setContentPane(gamePanel);
    		        break;
    		case 1:
    			//RESTART
    			g2.revalidate();
    			UniqueBrain unique2 = new UniqueBrain(g2);
 		        GameLoop g3 = new GameLoop(unique2,gamePanel);
 		        Thread t2 = new Thread(g3);
 		        t2.start();
 		        this.setContentPane(gamePanel);
 		        break;
    		case 2:
    			this.setContentPane(homepage);
    			break;
    		case 3:
    			this.setContentPane(new GameFinishedPanel(content));
    			break;
    		case 4:
    			this.setContentPane(new GameFinishedPanel(content));
    			break;
    		
    			
    }
    	this.revalidate();
    	this.setFocusable(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
