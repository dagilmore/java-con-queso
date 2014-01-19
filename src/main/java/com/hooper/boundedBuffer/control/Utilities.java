package com.hooper.boundedBuffer.control;

/**
 * Write a description of class Utilities here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Utilities
{
    public static boolean done;
    
    public static void delay(int n)
    {
        try { Thread.sleep(n);}
        catch (InterruptedException e) { }
    }
}
