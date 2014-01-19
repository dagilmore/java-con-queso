package com.hooper.boundedBuffer;

import java.awt.*;
/**
 * Write a description of class BoundedBuffer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BoundedBuffer
implements Buffer
{
    private int count;
    private int in;
    private int out;
    private int totalIn;
    private int totalOut;
    private Item buffer[ ];

    /**
     * Constructor for objects of class BoundedBuffer
     */
    public BoundedBuffer(int size)
    {
        count = in = out = 0;
        buffer = new Item[size];
    }
    
    public void insert(Item item)
    {
        item.setLocation(in);
        
        while(count >= buffer.length) 
            Thread.yield();
            
        ++count;
        buffer[in] = item;
        in = (in + 1) % buffer.length;
        totalIn++;
    }
    
    public Item remove()
    {
        Item item;
        while (count <= 0)
            Thread.yield();
        
        --count;
        item = buffer[out];
        buffer[out] = null;
        out = (out + 1) % buffer.length;
        totalOut++;
        return item;
    }
    
    // --- items added for diagnostics ---
    public int getCount()
    {
        return count;
    }
    
    public int getTotalIn()
    {
        return totalIn;
    }
    
    public int getTotalOut()
    {
        return totalOut;
    }
    
    public void paint(Graphics g)
    {
        for (int i = 0; i < buffer.length; i++)
        {
            if(buffer[i] != null)
                buffer[i].paint(g);
        }
    }
}
