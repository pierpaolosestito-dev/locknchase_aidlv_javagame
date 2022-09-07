package application.model;

import application.ai.UniqueBrain;

public class LockPowerfulTimer implements Runnable{
private Game game;
	public LockPowerfulTimer(Game game) {
		this.game = game;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(30000);
			game.getLock().resetBlackHat();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
