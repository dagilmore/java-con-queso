package com.hooper.criticalSection.control;
/**
 * HardwareData.java
 *
 * Generic data structure that is used by the test-and-set and swap
 * instructions for hardware solutions of the critical section problem.
 *
 * Note these solutions are not thread-safe and are intended entirely
 * for purposes of demonstrating the get-and-set functionality provided
 * by certain architectures.
 *
 * Thread safety would require these operations perform atomically.
 *
 * Figure 7.7
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public class HardwareData
{
    // if we do not use a lock, data must be declared as volatile
    private volatile boolean data;
    //     private boolean data;

    private Object lock;

    public HardwareData(boolean data) {
        this.data = data;
    }

    public boolean get() {
        synchronized(this) {
            return data;
        }
    }

    public void set(boolean data) {
        synchronized(this) {
            this.data = data;
        }
    }

    public static boolean testAndSet(HardwareData target) {
        synchronized(target) {
            boolean rv = target.data;
            target.data = true;
            return rv;
        }
    }

    /**
     * swap other with this.
     */
    public static boolean compareAndSwap(HardwareData value, 
        boolean expected, boolean newValue) {
        synchronized(value) {
            boolean temp = value.get();
            
            if(value.get() == expected) {
                value.set(newValue);
            }
            
            return temp;
        }
    }

    /**
     * swap other with this.
     */
    public void swap(HardwareData other) {
        synchronized(other) {
            boolean temp = this.get();
            this.set(other.get());
            other.set(temp);
        }
    }
}
