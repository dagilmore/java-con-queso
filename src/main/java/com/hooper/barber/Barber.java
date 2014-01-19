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
public class Barber
extends Thread
{
    private enum Status {
        waiting, shaving
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
    public Barber(int b, Shared s)
    {
        id = b;
        status = Status.waiting;
        shared = s;
    }

    private void cutHair()
    {
    }

    public void walk()
    throws InterruptedException
    {
        status = Status.waiting;
        shared.custReady.acquire();
        shared.barberReady.release();
        
        status = Status.shaving;
        shared.custHappy.acquire();
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
        switch(status) {
            case shaving:
                g.setColor(Color.blue);
                g.fillOval(x, y, h, h);
                break;
            case waiting:
                g.setColor(Color.red);
                g.fillOval(x + w - h, y, h, h);
                break;
            default:
                g.setColor(Color.black);
                g.fillOval(x, y, h, h);
        }
    }
}
