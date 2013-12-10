package com.github.nofutureball.entity.timer;

import java.util.ArrayList;

import com.github.nofutureball.entity.Entity;
import com.github.nofutureball.states.GameLevel;

/**
 * Timer, that counts from 0 to given finalTime
 * fires every update to its Listeners
 * 
 * Infinite timer have to be initialized with a finaltime
 * that is <= 0
 * You can init a timer with either Seconds or MiliSeconds
 * 
 * In order to use a Timer, make a new Timer instance and init
 * with the finaltime and so on.
 * Then the class that uses this timer has to implement the
 * TimerListener interface and override the "timerHasChanged" function
 * with eventhandling: timer increased, timer is over, timer is paused and so on
 * Lastly, you have to do:
 * instanceOfTimer.addTimerListener(this)
 * 
 * @author hollowspecter
 *
 */
public class Timer extends Entity {
    
    private ArrayList<TimerListener> list;
    private int finalTime;
    private int currentTime;
    private boolean timeOver;
    // is true when finalTime <= 0, if true, isTimeOver never gets true
    private boolean infinite;
    private boolean paused;
    
    /**
     * Constructor of Timer
     * If you want to give the Timer the finalTime in Seconds, set
     * timeInSeconds on true.
     * If you want an infinite Timer, set the finalTime on 0 or less
     * 
     * @param finalTime int finaltime usually in Miliseconds
     * @param timeInSeconds true if the finaltime given in seconds
     * @param paused true if the timer is paused and shall not get increased every frame
     */
    public Timer(int finalTime, boolean timeInSeconds, boolean paused) {
        super(0,0);
        list = new ArrayList<TimerListener>();
        GameLevel.entities.add(this);
        
        // is the Timer infinite?
        //
        if (finalTime <= 0) {
            infinite = true;
            this.finalTime = 0;
        } else
            infinite = false;
        
        // Handles Miliseconds or Seconds
        //
        if (timeInSeconds)
            this.finalTime = finalTime*1000;
        else
            this.finalTime = finalTime;
        
        // init current time
        //
        currentTime = 0;
        timeOver = false;
        this.paused = paused;
    }
    
    /*
     * Private Functions
     */
    
    /**
     * Adds time in miliseconds to the current time
     * Checks if timer is over
     * fires the Listeners
     * @param miliseconds
     */
    private void addTime(int miliseconds) {
        currentTime += miliseconds;
        // do not update isTimeOver if it is an infinite loop
        if (infinite) {
            if (currentTime >= finalTime) {
                timeOver = true;
            }
        }
        fireTimerListener();
    }
    
    /**
     * Is called whenever the Timer has changed
     */
    private void fireTimerListener() {
        for(TimerListener t : list) {
            t.timeHasChanged(this);
        }
    }
    
    /*
     * Public Functions
     */
    
    public void addTimerListener(TimerListener t) {
        list.add(t);
    }
    
    public void removeTimerListener(TimerListener t) {
        list.remove(t);
    }
   
    /**
     * Slick function
     * @param delta passed time in miliseconds since the last frame
     */
    public void update(int delta) {
        // when the timer is over, theres no need to increase the timer anymore
        // also when timer is paused, no time shall be added to the timer
        //
        if (!isTimeOver() && !isPaused())
        {
            addTime(delta);
        }
    }
    
    /*
     * Getter and Setter
     */
    // Getter
    
    public boolean isTimeOver() {
        return timeOver;
    }
    
    public boolean isInfinite() {
        return infinite;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public int getCurrentTime() {
        return currentTime;
    }
    
    public int getFinalTime() {
        return finalTime;
    }
    
    // Setter
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public void reset(boolean paused) {
        currentTime = 0;
        timeOver = false;
        this.paused = paused;
    }
}
