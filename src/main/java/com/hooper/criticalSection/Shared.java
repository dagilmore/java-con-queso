package com.hooper.criticalSection;

import java.util.concurrent.Semaphore;
import com.hooper.criticalSection.control.HardwareData;

/**
 * Write a description of class Shared here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shared
{
    public int processCount;
    public Semaphore mutex;
    
    // for Petersen's solution
    public int turn;
    public boolean[] flags;
    
    // for testAndSet
    public HardwareData lock;
    
    
    public Shared() {
        processCount = 0;
        mutex = new Semaphore(1);
        lock = new HardwareData(false);
    }
}
