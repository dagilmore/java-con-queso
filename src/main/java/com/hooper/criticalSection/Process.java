package com.hooper.criticalSection;

import java.util.Random;
import com.hooper.criticalSection.control.HardwareData;
import static com.hooper.criticalSection.control.HardwareData.testAndSet;
import static com.hooper.criticalSection.control.HardwareData.compareAndSwap;
import com.hooper.criticalSection.control.Utilities;
import static com.hooper.criticalSection.control.Utilities.done;

/**
 * Process - a competitor for the Critical Section
 * 
 * @author Wiliam H. Hooper
 * @version 2014-01-11
 */
public class Process
extends Thread
{
    private Random gen;
    private int delay;
    Shared shared;
    Display display;
    int i, j;
    HardwareData lock;
    
    /**
     * Constructor for objects of class Producer
     */
    public Process(Shared s, Display d, int id)
    {
        gen = new Random();
        shared = s;
        display = d;
        i = id;
    }
    
    public void run()
    {
        while(!done) {
            j = (i + 1) % shared.processCount;  // bookkeping
           
            try {
                // bad solution, entry
                shared.flags[i] = true;
                while(shared.flags[j]) {
                    display.waiting();
                }
                
                // critical section
                display.critical();
                
                // bad solution, exit
                shared.flags[i] = false;

                // remainder
                display.remainder();
                
            } 
            catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}
