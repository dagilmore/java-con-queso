package com.hooper.criticalSection;

import javax.swing.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;
import com.hooper.criticalSection.control.Utilities;
import static com.hooper.criticalSection.control.Utilities.*;

/**
 * Class Scenario - write a description of the class here
 * 
 * @author (your name) 
 * @version (a version number)
 */
public class Scenario 
extends JApplet 
implements ActionListener
{
    private Painter painter;
    private Shared shared;
    private ArrayList<Display> displays;
    private Button clone;
    private Button pause;
    private int consumers;
    String plabels [ ] = { "||", ">" };

     /**
     * Called by the browser or applet viewer to inform 
     * this JApplet that it
     * has been loaded into the system. 
     * It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        painter = new Painter();
        shared = new Shared();
        displays = new ArrayList<Display>();
        
        clone = new Button("add Process");
        this.add(clone);
        clone.addActionListener(this);

        pause = new Button("||");
        this.add(pause);
        pause.addActionListener(this);
        
        delay = 1000;
        paused = 0;
    }  
    
    public void actionPerformed(ActionEvent e)
    {
         Object obj = e.getSource();
         
         if(obj == clone)
         {
             int xPos = shared.processCount * 30 + 50;
             Display d = new Display(xPos);
             displays.add(d);
             Process p = new Process(shared, d, 
                shared.processCount);
             p.start();
             shared.processCount++;
             shared.flags = new boolean[shared.processCount];
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
        int y = 30;
        g.drawString((new Date()).toString(), 20, y += 20);
        g.drawString("Processes = " 
            + shared.processCount, 20, y += 20);
        for (Display d : displays)
            d.paint(g);
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
                    delay(50);
                delay(50);
                repaint();
            }
        }
    }
        
    /*
     * under development
    public static void debug() {
        int width = 500;
        int height = 500;
        JApplet applet = new Scenario();
        //debugging = true;
        
        String windowTitle = applet.getClass().getName();
        System.out.println(windowTitle + " created " + new Date());
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height + 20);
        applet.setSize(width, height);
        applet.setLayout(null);
        frame.add(applet);
        applet.init();      // simulate browser call(1)
        applet.start();      // simulate browser call(2)
        frame.setVisible(true);
    }  
     */

}
