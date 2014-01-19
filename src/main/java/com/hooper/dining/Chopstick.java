package com.hooper.dining;

import java.awt.Graphics;

/**
 * Chopstick - the coveted implement.
 * 
 * @author William H. Hooper
 * @version 2004-05-10
 */
public class Chopstick
{
    private int taken;  // # of philosopher, -1 if untaken
    private int number; // identifier, 0-4
    private Misc shared;

    public Chopstick(int n)
    {
        taken = -1;
        number = n;
    }

    /**
     * acquire a chopstick, as in Figure 7.24
     * 
     * @param n the number of the acquiring philosopher
     */
    public synchronized void acquire(int n)
    {
        while (!available())
        {
            try { 
                wait(); 
            } catch (InterruptedException e) {}
        }
        taken = n;
        if(n == number)
            shared.randomDelay();
    }

    /**
     * release a chopstick, as in Figure 7.24
     * 
     * @param n the number of the releasing philosopher
     */
    public synchronized void release()
    {
        taken = -1;
        notify();
    }

    /**
     * is this chopstick avaliable?
     * WARNING: this method is NOT synchronized.  To obtain
     * reliable results, you must call this method within a 
     * critical section
     * 
     * @param n the number of the requesting philosopher
     * @return true iff it's not taken.
     */
    public boolean available()
    {
        return(taken < 0);
    }

    /**************************************************
    GUI kid stuff.
    Do not work below this line.
     **************************************************/
    public void initGUI(Misc s)
    {
        shared = s;
    }

    public void paint (Graphics g)
    {
        shared.paintChopstick(g, taken, number);
    }
}
