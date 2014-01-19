package com.hooper.criticalSection;

import java.util.*;
import java.awt.*;
import static java.awt.Color.*;
import static com.hooper.criticalSection.control.Utilities.*;
/**
 * Write a description of class Display here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Display
{
    private Color color;
    private Random gen;
    private int x, y, w, h;
    
    public Display(int x)
    {
        color = green;
        gen = new Random();
        this.x = x;
        y = 100;
        w = 20;
        h = 20;
    }
    
    public void critical()
    {
        while (paused > 0) 
            delay(50);
        color = red;
        delay(gen.nextInt(delay));
    }
    
    public void waiting()
    {
        while (paused > 0) 
            delay(50);
        color = yellow;
        delay(gen.nextInt(delay/4));
    }
    
    public void remainder()
    {
        while (paused > 0)
            delay(50);
        color = green;
        delay(gen.nextInt(delay));
    }
    
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }
}
