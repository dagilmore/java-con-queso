package com.hooper.barber;

import java.util.concurrent.*;
import java.awt.*;
import com.hooper.barber.control.Common;

/**
 * a Barber class to explore 
 * the Sleeping Barber Problem
 * 
 * @author William H. Hooper
 * @version 2014-01-15
 */
public class Customer
extends Thread
{
    private enum Status {
        happy, needShave, waiting, gettingShaved
    };

    int id;
    Status status;
    Shared shared;

    /**
     * create a Writer
     * 
     * @param b the unique ID
     * @param s any shared data.
     */
    public Customer(int b, Shared s)
    {
        id = b;
        status = Status.happy;
        shared = s;
    }

    public void walk()
    throws InterruptedException
    {
        status = Status.needShave;
        
        shared.custReady.release();
        
        status = Status.waiting;
        shared.barberReady.acquire();

        status = Status.gettingShaved;

        Common.randomDelay();
        shared.custHappy.release();

        status = Status.happy;
        Common.randomDelay(5);
    }

    /**
     * called by start() to manage the thread
     */
    public void run()
    {
        try {
            while(true) {
                walk();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * GUI kid stuff
     */
    int x, y, w, h;
    Color color = Color.green;

    public void setBounds(int nx, int ny, 
    int nw, int nh) {
        x = nx;
        y = ny;
        w = nw;
        h = nh;
    }

    public void paint(Graphics g) {
        // happy, needShave, waiting, gettingShaved
        switch(status) {
            case happy:
            g.setColor(Color.green);
            g.fillOval(x + 0 * (w - h), y, h, h);
            break;
            case needShave:
            g.setColor(Color.yellow);
            g.fillOval(x + 1 * (w - h), y, h, h);
            break;
            case waiting:
            g.setColor(Color.red);
            g.fillOval(x + 2 * (w - h), y, h, h);
            break;
            case gettingShaved:
            g.setColor(Color.blue);
            g.fillOval(x + 3 * (w - h), y, h, h);
            break;
            default:
            g.setColor(Color.black);
            g.fillOval(x, y, h, h);
        }
        if(color == Color.green) {
        } else {
        }
    }
}
