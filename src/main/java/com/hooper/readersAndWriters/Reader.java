import java.util.concurrent.*;
import java.awt.*;

/**
 * a Reader class to explore 
 * the Readers-Writers Problem
 * 
 * @author William H. Hooper
 * @version 2006-05-07
 * @version revised 2010-02-16
 */
public class Reader
extends Thread
{
    private String name;
    private int delay;
    private Semaphore mutex;
    private Semaphore wrt;
    private static volatile int readCount = 0;
    
    // allows host to check my status
    public boolean running;

    /**
     * create a Reader
     * 
     * @param n the name
     * @param m the mutex semaphore
     * @param d the shortest delay
     * @param w the wrt semaphore
     */
    public Reader(String n, int d,
        Semaphore m, Semaphore w)
    {
        name = n;
        delay = d;
        mutex = m;
        wrt = w;
        running = false;
    }
    
    /**
     * the main method of the class
     * allows the reader to read and think, 
     * logging his progress
     */
    private void readAndThink()
    throws InterruptedException
    {
        mutex. acquire();
            readCount++;
            if (readCount == 1) {
                wrt. acquire();
            }
        mutex. release();
            
        read();
            
        mutex. acquire();
            readCount--;
            if (readCount == 0) {
                wrt. release();
            }
        mutex. release();
                
        think();
    }
    
    /**
     * log progress to standard output
     * @param s the status to log
     */
    private void log(String s)
    {
        Common.log(name, s, delay);
    }

    /**
     * called by start() to manage the thread
     */
    public void run()
    {
        running = true;
        
        while (Common. phase == Common. setup) ;
        
        try {
            while (Common .phase != Common .cleanup) {
                if(Common .phase == Common .pause) {
                    Thread.yield();
                    continue;
                }
            
                readAndThink();
            }
        } catch (InterruptedException e) {
            log("i");
        }
        running = false;
    }
    
    /**
     * a stub to simulate reading
     */
    public void read()
    {
        color = Color .red;
        log("r");
    }
    
    
    /**
     * a stub to simulate thinking
     */
    public void think()
    {
        color = Color .green;
        log("t");
    }
    
    /**
     * GUI kid stuff
     */
    int x, y, w, h;
    Color color = Color.green;
    
    public void setBounds(int nx, int ny, int nw, int nh) {
        x = nx;
        y = ny;
        w = nw;
        h = nh;
    }
    
    public void paint(Graphics g) {
        g.setColor(color);
        if(color == Color.green) {
            g.fillOval(x, y, h, h);
        } else {
            g.fillOval(x + w - h, y, h, h);
        }
    }
}
