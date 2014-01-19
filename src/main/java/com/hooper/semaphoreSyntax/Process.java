package com.hooper.semaphoreSyntax;

import java.util.concurrent.*;

public class Process
extends Thread
{
    // instance variables - replace the example below with your own
    private Semaphore s;

    /**
     * Constructor for objects of class Process
     */
    public Process(Semaphore s)
    {
        this.s = s;
    }
    
    public void walk()
    throws InterruptedException
    {
        boolean tentative = s.tryAcquire();
        s.acquire();
        s.release();
    }
    
    public void run()
    {
        try {
            walk();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
