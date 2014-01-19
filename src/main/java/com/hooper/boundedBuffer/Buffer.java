package com.hooper.boundedBuffer;

import java.awt.*;
/**
 * Write a description of interface Buffer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public interface Buffer
{
    public abstract void insert(Item item);
    public abstract Item remove();
    
    public abstract int getTotalIn();
    public abstract int getTotalOut();
    public abstract int getCount();
    public abstract void paint(Graphics g);
}
