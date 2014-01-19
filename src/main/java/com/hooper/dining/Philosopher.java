package com.hooper.dining;

import java.awt.Graphics;
/**
 * The ideal dinner guest.
 * 
 * @author William H. Hooper
 * @version 2004-05-10
 */
public class Philosopher
extends Thread
{
    int number;
    Chopstick left, right;
    Misc shared;

    /**
     * Constructor for objects of class Philosopher
     * 
     * @param n identifies this philosopher, 0-4
     * @param r right chopstick
     * @param l left chopstick
     */
    public Philosopher(int n, Chopstick r, Chopstick l)
    {
        number = n;
        right = r;
        left = l;
    }

    public void run()
    {
        shared.randomDelay(10);
        while (true)
        {
            look();
            right.acquire(number);
            left.acquire(number);
            eat();
            left.release();
            right.release();
            think();
        }
    }

    /**************************************************
    GUI kid stuff.
    Do not work below this line.
     **************************************************/
    Misc.PhilosopherState state;

    void initGUI(Misc s)
    {
        shared = s;
        state = Misc.PhilosopherState.beginning;
    }

    void look()
    {
        state = Misc.PhilosopherState.looking;
    }

    void eat()
    {
        state = Misc.PhilosopherState.eating;
        shared.randomDelay();
    }

    void think()
    {
        state = Misc.PhilosopherState.thinking;
        shared.randomDelay(3);
    }

    public void paint(Graphics g)
    {
        shared.paintPhilosopher(g, number, state);
    }
}
