import java.util.concurrent.*;
import java.awt.*;

/**
 * a Writer class to explore 
 * the Readers-Writers Problem
 * 
 * @author William H. Hooper
 * @version 2006-05-07
 */
public class Writer
extends Thread
{
    private String name;
    private int delay;
    private Semaphore wrt;
    
    // allows host to check my status
    public boolean running;

    /**
     * create a Writer
     * 
     * @param n the name
     * @param w the wrt semaphore
     */
    public Writer(String n, int d,
        Semaphore w)
    {
        name = n;
        delay = d;
        wrt = w;
        running = false;
    }
    
    /**
     * the main method of the class
     * allows the writer to write and think, 
     * logging her progress
     */
    private void writeAndThink()
    throws InterruptedException
    {
        wrt. acquire();
        
        write();
        
        wrt. release();
        
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
            
                writeAndThink();
            }
        } catch (InterruptedException e) {
            log("i");
        }
        running = false;
    }
    
    /**
     * a stub to simulate writing
     */
    public void write()
    {
        color = Color .red;
        log("w");
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
    
    public void setBounds(int nx, int ny, 
        int nw, int nh) {
        x = nx;
        y = ny;
        w = nw;
        h = nh;
    }
    
    public void paint(Graphics g) {
        g.setColor(color);
        if(color == Color.green) {
            g.fillOval(x + w - h, y, h, h);
        } else {
            g.fillOval(x, y, h, h);
        }
    }
}
