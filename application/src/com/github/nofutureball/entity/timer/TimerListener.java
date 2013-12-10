package com.github.nofutureball.entity.timer;

//@TODO: document
public interface TimerListener {

    /**
     * Should use getCurrentTime() and getIsTimeOver()
     * to handle events
     * @param timer The Timer instance
     */
    public void timeHasChanged(Timer timer);
    
}
