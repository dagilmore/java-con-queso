package com.hooper.criticalSection.control;

/**
 * Write a description of class Utilities here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Utilities
{
    public static boolean done;
    public static int paused;
    public static int delay;
    
    public static void delay(int n)
    {
        try { Thread.sleep(n);}
        catch (InterruptedException e) { }
    }
}
