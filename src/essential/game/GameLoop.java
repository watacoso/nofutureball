package essential.game;

import java.util.Timer;

public abstract class GameLoop implements Runnable{
	
	public boolean running = false;
	protected int targetFps = 60;
	
	private Timer timer;
	
	public void run()
	{
		running = true;
		long prevFrame = System.nanoTime();
		while (running == true)
		{
			System.out.println("Frame: " + (System.nanoTime() - prevFrame)/1000000);
			prevFrame = System.nanoTime();
			sync();
		}
	}
	
	
	@SuppressWarnings({ "static-access" })
	
	protected void sync() {
        long timeLeft = 0;
        final long TARGET_FRAME_TIME = 1000000000 / targetFps;
        final long thisFrameStart = System.nanoTime();
        do {
			timeLeft = (thisFrameStart + TARGET_FRAME_TIME) - System.nanoTime();
			if (timeLeft > 10000000) {  //10 ms
				try {
						Thread.currentThread().sleep(2);
					} catch (InterruptedException e) {}
					} else {
						Thread.currentThread().yield();
				}
			} while (timeLeft > 0);
		}
	
	public void stop()
	{
		if (timer != null) {
			timer.cancel();
		}
		running = false;
	}

	
	/**
	 * To be overridden; This function gets called [targetFps] times per second.
	 */
	protected abstract void update();
}
