package com.hooper.barber.control;

import java.util.Random;

/**
 * a class of static variables and methods to control
 * the progress and logging of the simulation
 * 
 * @author William H. Hooper
 * @version 2006-05-07
 * @version revised 2010-02-16
 */
public class Common
{
    private static final int minDelay = 30;
    private static final int interval = 1000;
    private static Random gen;
    
    /**
     * sleep for a fixed interval
     * 
     * @param n the number of milliseconds to sleep;
     */
    public static void msDelay(int n)
    {            
        try { Thread.sleep(n);}
        catch (InterruptedException e) { }
    }
    
    /**
     * Sleep for a random amount of time, 
     * less than 
     * a multiple of the basic interval.
     * 
     * @param n the multiple of time to delay
     */
    public static void randomDelay(int n)
    {
        if(gen == null) {
            gen = new Random();
        }
        msDelay(gen.nextInt(n * interval));
    }
    
    /**
     * Sleep for a random amount of time, 
     * less than a basic interval.
     */
    public static void randomDelay()
    {
        randomDelay(1);
    }
}
