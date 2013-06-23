package essential.game;

import java.util.TimerTask;

public class TickTask extends TimerTask {

	
	GameLoop gameLoop;
	/**
	 * 
	 * @param pingTarget The object in which to ping on tick
	 */
	public TickTask(GameLoop pingTarget) 
	{
		gameLoop = pingTarget;
	}
	
	@Override
	public void run() {
		gameLoop.update();
	}

}
