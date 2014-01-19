package com.hooper.boundedBuffer;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;
import com.hooper.boundedBuffer.control.Utilities;
import static com.hooper.boundedBuffer.control.Utilities.*;

/**
 * Class Scenario - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class Scenario 
extends Applet 
implements ActionListener
{
    private Painter painter;
    private Buffer buffer;
    private Button produce;
    private Button consume;
    private Button pause;
    private int producers;
    private int consumers;
    private int delay;
    int paused;
    String plabels [ ] = { "||", ">" };

     /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        painter = new Painter();
        buffer = new BoundedBuffer(15);
        
        produce = new Button("add Producer");
        this.add(produce);
        produce.addActionListener(this);
        
        pause = new Button("||");
        this.add(pause);
        pause.addActionListener(this);
        
        consume = new Button("add Consumer");
        this.add(consume);
        consume.addActionListener(this);
        
        delay = 1000;
        paused = 0;
    }  
    
    public void actionPerformed(ActionEvent e)
    {
         Object obj = e.getSource();
         
         if(obj == produce)
         {
             Producer p = new Producer(buffer, delay);
             p.start();
             producers++;
         }
         
         if(obj == consume)
         {
             Consumer c = new Consumer(buffer, delay);
             c.start();
             consumers++;
         }
         
         if(obj == pause)
         {
             paused = 1 - paused;
             pause.setLabel(plabels[paused]);
         }
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
//         g.setColor(Color.white);
//         g.fillRect(0, 0, 200, 100);
        g.setColor(Color.black);
        int x = 50;
        g.drawString((new Date()).toString(), 20, x);
        g.drawString("Producers = " + producers, 20, x += 20);
        g.drawString("Consumers = " + consumers, 20, x += 20);
        g.drawString(" Total In = " + buffer.getTotalIn(), 20, x += 20);
        g.drawString("Total Out = " + buffer.getTotalOut(), 20, x += 20);
        g.drawString("    Count = " + buffer.getCount(), 20, x += 20);
        g.drawRect(197, 97, 106, 306);
        buffer.paint(g);
    }

    public void start()
    {
        done = false;
        painter.start();
    }
    
    public void stop()
    {
        done = true;
    }
    
    class Painter
    extends Thread
    {
        public void run()
        {
            while(!done)
            {
                while (paused > 0) 
                    Thread.yield();
                delay(50);
                repaint();
            }
        }
    }

    public void destroy()
    {
        // provide code to be run when JApplet is about to be destroyed.
    }
}
