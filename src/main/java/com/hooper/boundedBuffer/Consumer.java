package com.hooper.boundedBuffer;

import java.util.*;
import com.hooper.boundedBuffer.control.Utilities;
import static com.hooper.boundedBuffer.control.Utilities.*;

/**
 * Write a description of class Producer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Consumer
extends Thread
{
    private Random gen;
    private Buffer buffer;
    private int delay;
    
    /**
     * Constructor for objects of class Producer
     */
    public Consumer(Buffer buf, int d)
    {
        gen = new Random();
        buffer = buf;
        delay = d;
    }
    
    public void run()
    {
        while(!done)
        {
            delay(gen.nextInt(delay));
            buffer.remove();
        }
    }
}
