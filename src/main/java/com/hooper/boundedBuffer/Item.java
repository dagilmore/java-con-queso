package com.hooper.boundedBuffer;

import java.awt.*;
import static java.awt.Color.*;
import java.util.*;
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private Random gen;
    private Color color;
    private int x, y, w, h;

    /**
     * Constructor for objects of class Item
     */
    public Item()
    {
        gen = new Random();
        color = new Color(gen.nextInt());
        x = 200;
        w = 100;
        h = 18;
    }
    
    public void setLocation(int location)
    {
        y = location * 20 + 100;
    }

    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x, y, w, h);
        g.setColor(black);
        g.drawRect(x, y, w, h);
    }
}
