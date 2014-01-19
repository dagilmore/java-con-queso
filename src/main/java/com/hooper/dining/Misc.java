package com.hooper.dining;

import java.util.Random;
import java.util.EnumSet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Polygon;
import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Misc. constants and methods for the Scenario
 * 
 * @author William H. Hooper
 * @version 2004-05-10
 */
public class Misc
{
    private enum Debug {
        chopsticks, philosophers
    }
    
    private final EnumSet<Debug> debugging = EnumSet.of(
//         Debug.chopsticks,
        Debug.philosophers
        );

/**************************************************
GUI kid stuff.
Do not work below this line.
**************************************************/

    // to draw the table and philosophers
    public final double centerX = 250;
    public final double centerY = 250;
    public final double radius = 120;

    // to randomize event timing
    private int nPhil;
    private int nChop;
    private Random gen;
    private final int interval;
    
    public Misc(int c, int p)
    {
        nChop = c;
        nPhil = p;
        gen = new Random();
        interval = 1000;
    }
    
    /**
     * sleep for a fixed interval
     * 
     * @param n the number of milliseconds to sleep;
     */
    public static void msDelay(int n)
    {            
        try { Thread.sleep(n);}
        catch (InterruptedException e) { }
    }
    
    /**
     * Sleep for a random amount of time, 
     * less than 
     * a multiple of the basic interval.
     * 
     * @param n the multiple of time to delay
     */
    public void randomDelay(int n)
    {
        msDelay(gen.nextInt(n * interval));
    }
    
    /**
     * Sleep for a random amount of time, 
     * less than a basic interval.
     */
    public void randomDelay()
    {
        randomDelay(1);
    }
    
    /**
     * Draw a circle, with a black outline and 
     * the specified interior color
     * 
     * @param g the Graphics frame on which to draw
     * @param x center coordinate
     * @param y center coordinate
     * @param r radius
     * @param c interior color
     */
    public static void drawCircle(Graphics g, 
        double x, double y, double r, Color c)
    {
        g.setColor(c);
        g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
        g.setColor(Color.black);
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }
    
    /**
     * return the nth letter
     * 
     * @param n the index, a = 0, b = 1, etc.
     */
    public static String label (int n)
    {
        return "abcdefghijklmnopqrstuvwxyz"
            .substring(n, n + 1);
    }

    public enum PhilosopherState {
        beginning(Color.white), 
        thinking(new Color(0, 204, 102)), 
        looking(Color.red), 
        eating(Color.yellow);
        
        public final Color color;
        
        PhilosopherState(Color c)
        {
            color = c;
        }
    }
    
    /**
     * Draw the background for the applet
     * 
     * @param g the Graphics frame on which to draw
     */
    public void decorate(Graphics g)
    {        
        // clear the background
        g.setColor(Color.white);
        g.fillRect(0, 0, 500, 500);
        
        // draw the table
        drawCircle(g, centerX, centerY, radius, 
            Color.orange);
        
        // label the philosopher states
        drawCircle(g,  50, 450, 10, 
            PhilosopherState.looking.color);
        g.drawString("Looking", 65, 455);
        
        drawCircle(g, 200, 450, 10, 
            PhilosopherState.eating.color);
        g.drawString("Eating", 215, 455);
        
        drawCircle(g, 350, 450, 10, 
            PhilosopherState.thinking.color);
        g.drawString("Thinking", 365, 455);
    }
    
    public void paintPhilosopher(Graphics g,
        int number, PhilosopherState state)
    {
        Color color = state.color;
        String name = label(number);
        int lastChop = nChop - 1;
        int nSlots = nChop * 2;
        
        double angle = 2*PI*number / nChop - PI/2;

        double r = 1.25 * radius / nChop;
        double x = centerX + (radius + 1.5*r) * cos(angle);
        double y = centerY + (radius + 1.5*r) * sin(angle);
        
        drawCircle(g, x, y, r, color);
        if (debugging.contains(Debug.philosophers)) {
            g.drawString(name, (int) x - 3, (int) y - 5);
        }
    }
    
    // colors the chopsticks use
    public final Color freeColor = Color.black;
    public final Color takenColor = Color.red;
    
    public void paintChopstick (Graphics g, 
        int taken, int number)
    {
        int lastChop = nChop - 1;
        int nSlots = nChop * 2;
        
        double angle = (2*PI*number) / nChop - PI/2 - PI/nChop;
        double increment = PI/2/nChop;
        
        if (taken >= 0)
        {
            // paint it red, next to the owner
            if (taken == number)
            {
                angle += increment; 
            }
            else
            {
                angle -= increment; 
            }
            g.setColor(takenColor);
        }
        else
        {
            // paint it black, in between
            g.setColor(freeColor);
        }
        
        double rd = radius;
        double cx = centerX;
        double cy = centerY;
        
        double x0 = cx + rd * 0.5 * cos(angle);
        double y0 = cy + rd * 0.5 * sin(angle);
        double x1 = cx + rd * 0.6 * cos(angle + 0.01);
        double y1 = cy + rd * 0.6 * sin(angle + 0.01);
        double x2 = cx + rd * 0.6 * cos(angle - 0.01);
        double y2 = cy + rd * 0.6 * sin(angle - 0.01);
        double x3 = cx + rd * 0.9 * cos(angle - 0.01);
        double y3 = cy + rd * 0.9 * sin(angle - 0.01);
        double x4 = cx + rd * 0.9 * cos(angle + 0.01);
        double y4 = cy + rd * 0.9 * sin(angle + 0.01);

        Polygon chopstick = new Polygon();
        chopstick.addPoint((int)x1, (int)y1);
        chopstick.addPoint((int)x2, (int)y2);
        chopstick.addPoint((int)x3, (int)y3);
        chopstick.addPoint((int)x4, (int)y4);
        g.drawPolygon(chopstick);
        g.fillPolygon(chopstick);
        
        if (debugging.contains(Debug.chopsticks)) {
            if(taken >= 0)
                g.drawString(number + "->" 
                    + Misc.label(taken), 
                        (int)(x0 - 15), (int)(y0 + 5));
            else
                g.drawString(number + "", 
                        (int)(x0 - 5), (int)(y0 + 5));
        }
    }
}
