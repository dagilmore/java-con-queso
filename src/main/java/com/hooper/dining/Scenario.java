package com.hooper.dining;

import javax.swing.JApplet;
import javax.swing.JRootPane;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * Class Scenario - the Dining Philosopher problem
 * 
 * @author William H. Hooper
 * @version 2004-05-10
 */
public class Scenario 
extends JApplet
{
    Chopstick[] chop;
    Philosopher[] phil;
    Misc shared;
    
    public Scenario()
    {
        this(5, 5);
    }
    
    public Scenario(int c, int p)
    {
        chop = new Chopstick[c];
        phil = new Philosopher[p];
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it
     * has been loaded into the system. It is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {
        
        initGUI();  //ignore this, but leave it in.

        for (int i = 0; i < chop.length; i++)
        {
            chop[i] = new Chopstick(i);
            chop[i].initGUI(shared);
        }

        for (int i = 0; i < phil.length; i++)
        {
            phil[i] = new Philosopher(i, chop[i], 
                chop[(i+1) % chop.length]);
            phil[i].initGUI(shared);
        }
    }

    /**
     * Called by the browser or applet viewer to inform this JApplet that it 
     * should start its execution. It is called after the init method and 
     * each time the JApplet is revisited in a Web page. 
     */
    public void start()
    {
        startGUI();  //ignore this, but leave it in.

        for (Philosopher p : phil)
            p.start();
    }

    /**************************************************
    GUI kid stuff.
    Do not work below this line.
     **************************************************/    
    // for smoother animation, use a separate thread 
    // to repaint the screen
    class Painter
    extends Thread
    {   public void run()
        {   while(true)
            {   shared.msDelay(100);
                repaint();
            }
        }
    }

    Painter paint;

    public void initGUI()
    {
        // this is a workaround for a security conflict with current browsers
        // including Netscape & Internet Explorer which do not allow access to 
        // AWT system event queuen which JApplets do on startup to check access. 
        JRootPane rootPane = this.getRootPane();    
        rootPane.putClientProperty("defeatSystemEventQueueCheck", 
            Boolean.TRUE);

        paint = new Painter();
        shared = new Misc(chop.length, phil.length);
    }

    public void startGUI()
    {
        paint.start();
    }

    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {
        shared.decorate(g);

        for (Chopstick c : chop)
            c.paint(g);

        for (Philosopher p : phil)
            p.paint(g);
    }
    
    public static void play() {
        play(5);
    }
    
    public static void play(int c) {
        play(c, c);
    }
    
    public static void play(int c, int p) {
        int width = 500;
        int height = 600;
        Scenario applet = new Scenario(c, p);
        JFrame frame = new JFrame("Dining Philosophers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // resize the frame to fit the applet
        applet.setSize(width, height);
        Insets insets = frame.getInsets();
        int fWidth = width - insets.left - insets.right;
        int fHeight = height - insets.top - insets.bottom;
        frame.setSize(fWidth, fHeight);
        frame.add(applet);
        applet.init();      // simulate browser call(1)
        frame.setVisible(true);
        applet.start();      // simulate browser call(2)
    } 
    
    public static void main(String[] argv) 
    { 
        play(7, 7); 
    }
}
