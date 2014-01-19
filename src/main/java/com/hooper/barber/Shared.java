package com.hooper.barber;

import java.util.concurrent.*;

/**
 * Write a description of class Shared here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shared
{
    public Semaphore barberReady;
    public Semaphore custReady;
    public Semaphore custHappy;
    public Semaphore accessWRSeats;
    public int numberOfFreeWRSeats;
    
    public Shared(int n)
    {
        barberReady = new Semaphore(0);
        custReady = new Semaphore(0);
        custHappy = new Semaphore(0);
        accessWRSeats = new Semaphore(1);
        numberOfFreeWRSeats = n;
    }
}
