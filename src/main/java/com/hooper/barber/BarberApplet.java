package com.hooper.barber;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import com.hooper.barber.control.Common;

/**
 * Class RWApplet - a demonstration of the 
 * Customers and Barbers problem
 * 
 * @author William H. Hooper
 * @version 2010-02-16
 */
public class BarberApplet extends JApplet
{
    public static void main(String argv)
    {
        BarberApplet ba = new BarberApplet();
        
    }
    JButton stopStart;
    DrawingPanel drawing;
    
    Shared shared;
    ArrayList<Customer> customers;
    ArrayList<Barber> barbers;
    
    public void initBarber() {
        shared = new Shared(3);
        customers = new ArrayList<Customer>();
        barbers = new ArrayList<Barber>();
    }
    
    class ReadAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = customers.size();
            Customer customer = new Customer(index, shared);
            customer.start();
            customers.add(customer);
            
            // GUI
            int totalWidth = drawing.getWidth();
            int x = totalWidth / 15;
            int y = index * 25 + 10;
            int w = 2 * totalWidth / 15;
            customer.setBounds(x, y, w, 20);
            repaint();
        }
    }
    
    class WriteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = barbers.size();
            Barber barber = new Barber(index, shared);
            barber.start();
            barbers.add(barber);
            
            // GUI
            int totalWidth = drawing.getWidth();
            int x = 10 * totalWidth / 15;
            int y = index * 25 + 50;
            int w = 2 * totalWidth / 15;
            barber.setBounds(x, y, w, 20);
            repaint();
        }
    }
    
    class SSAction implements ActionListener {
        String[] labels = { ">", "||" };
        int index = 0;
        public void actionPerformed(ActionEvent e) {
            index = 1 - index;
            stopStart .setText(labels[index]);
        }
    }
    
    public void init() {
        Container cp = getContentPane();
        cp .setLayout(new GridBagLayout());         
        GridBagLayout gridbag = new GridBagLayout();
        
        JLabel spaceLeft = new JLabel();
        GridBagConstraints slc = new GridBagConstraints();
        slc .gridx = 0;
        slc .gridy = 0;
        slc .weightx = 0.5;
        cp .add(spaceLeft, slc);

        JButton addCustomer = new JButton("Add Customer");
        GridBagConstraints arc = new GridBagConstraints();
        arc .gridx = 1;
        arc .gridy = 0;
        cp .add(addCustomer, arc);
        addCustomer .addActionListener(new ReadAction());
        
//         stopStart = new JButton(" > ");
//         GridBagConstraints ssc = new GridBagConstraints();
//         ssc .gridx = 2;
//         ssc .gridy = 0;
//         cp .add(stopStart, ssc);
//         stopStart .addActionListener(new SSAction());
        
        JButton addBarber = new JButton("Add Barber");
        GridBagConstraints awc = new GridBagConstraints();
        awc .gridx = 3;
        awc .gridy = 0;
        cp .add(addBarber, awc);
        addBarber .addActionListener(new WriteAction());
        
        JLabel spaceRight = new JLabel();
        GridBagConstraints src = new GridBagConstraints();
        src .gridx = 4;
        src .gridy = 0;
        src .weightx = 0.5;
        cp .add(spaceRight, src);
        
        drawing = new DrawingPanel();
        GridBagConstraints dc = new GridBagConstraints();
        dc .gridx = 0;
        dc .gridy = 1;
        dc .weightx = 1.0;
        dc .weighty = 1.0;
        dc .gridwidth = 7;
        dc .fill = GridBagConstraints.BOTH;
        cp .add(drawing, dc);
        
        initBarber();
    }
    
    class Painter extends Thread {
        public void run() {
            while(true) {
                try { Thread.sleep(30); }
                catch(Exception e) { }
                repaint();
            }
        }
    }
    
    class DrawingPanel extends JPanel {
        public void paint(Graphics g)
        {
            // simple text displayed on applet
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(),  getHeight());
            
            drawLabel(g, "Customers:",   0, 0);
            paintDot(g, Color.green, 0);
            drawLabel(g, "Happy",   1, 0);
            paintDot(g, Color.yellow, 1);
            drawLabel(g, "Scruffy", 1, 1);
            paintDot(g, Color.red, 2);
            drawLabel(g, "Waiting", 1, 2);
            paintDot(g, Color.blue, 3);
            drawLabel(g, "Shaving", 1, 3);
            
            drawLabel(g, "Barbers:",   0, 5);
            paintDot(g, Color.blue, 5);
            drawLabel(g, "Shaving",   1, 5);
            paintDot(g, Color.red, 6);
            drawLabel(g, "Waiting", 1, 6);
            
            for(Customer r : customers) {
                r.paint(g);
            }
            
            for(Barber w : barbers) {
                w.paint(g);
            }
        }
        
        private void paintDot(Graphics g, Color c, int i)
        {
            g.setColor(c);
            g.fillOval(
                20 + i * 60, 
                getHeight() - 30,
                20, 20);
        }
        
        private void drawLabel(Graphics g, String s, int r, int c)
        {
            g.setColor(Color.black);
            g.drawString(s,
                20 + c * 60, 
                getHeight() - 55 + r * 20);
        }
    }
    
    public void start()
    {
        // provide any code requred to run each time 
        // web page is visited
        (new Painter()) . start();
    }
}
