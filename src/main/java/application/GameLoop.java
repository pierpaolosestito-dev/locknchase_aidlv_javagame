package application;

import application.ai.AILockBrain;
import application.ai.AIPoliceManBrain;
import application.ai.Test;
import application.ai.UniqueBrain;
import application.model.Game;
import application.model.GemManager;
import application.view.GamePanel;

public class GameLoop implements Runnable{
	private AILockBrain lockBrain;
	private AIPoliceManBrain pmBrain;
	private UniqueBrain uniqueBrain;
	private GamePanel graphic;
	private Test test;
	private Game game;

	public GameLoop(AILockBrain lockBrain,AIPoliceManBrain pmBrain, GamePanel graphic) {
		this.lockBrain=lockBrain;
		this.graphic = graphic;
		this.pmBrain = pmBrain;
		this.game = Game.getInstance();
		
	}
	public GameLoop(AILockBrain lockBrain,Test pmBrain, GamePanel graphic) {
		this.lockBrain=lockBrain;
		this.graphic = graphic;
		this.test = pmBrain;
		
	}
	public GameLoop(UniqueBrain uniqueBrain, GamePanel graphic) {
		this.uniqueBrain = uniqueBrain;
		this.graphic = graphic;
		this.game = Game.getInstance();
	}

	@Override
	public void run() {
		while(true) {
			try {
				if(game.getFinished()) {
					return;
				}
				
				//Thread.sleep(500);
				//pmBrain.exec();
				uniqueBrain.exec();
				//Thread.sleep(200);
				//lockBrain.exec();
				
				Thread.sleep(500);
				//test.exec();
				graphic.repaint();
				Thread.sleep(1000);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
