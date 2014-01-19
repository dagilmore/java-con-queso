
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Class RWApplet - a demonstration of the 
 * Readers and Writers problem
 * 
 * @author William H. Hooper
 * @version 2010-02-16
 */
public class RWApplet extends JApplet
{
    JButton stopStart;
    DrawingPanel drawing;
    
    ArrayList<Reader> readers;
    ArrayList<Writer> writers;
    Semaphore mutex, wrt;
    String[] rlabels = { 
        "A", "B", "C", "D", "E", "F", "G",
        "H", "I", "J", "K", "L", "M", "N",
        "O", "P", "Q", "R", "S", "T", "U",
        "V", "W", "X", "Y", "Z", 
    };
    
    String[] wlabels = { 
        "Z", "Y", "X", "W", "V", "U", "T",
        "S", "R", "Q", "P", "O", "N", "M",
        "L", "K", "J", "I", "H", "G", "F",
        "E", "D", "C", "B", "A", 
    };
    
    public void initRW() {
        readers = new ArrayList<Reader>();
        writers = new ArrayList<Writer>();
        mutex = new Semaphore(1);
        wrt = new Semaphore(1);
        Common.phase = Common.pause;
    }
    
    class ReadAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = readers.size();
            String label = rlabels[index];
            Reader reader = new Reader(label, 50, mutex, wrt);
            reader.start();
            readers.add(reader);
            
            // GUI
            int totalWidth = drawing.getWidth();
            int x = totalWidth / 7;
            int y = index * 25 + 50;
            int w = 2 * totalWidth / 7;
            reader.setBounds(x, y, w, 20);
            repaint();
        }
    }
    
    class WriteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = writers.size();
            String label = wlabels[index];
            Writer writer = new Writer(label, 50, wrt);
            writer .start();
            writers .add(writer);
            
            // GUI
            int totalWidth = drawing.getWidth();
            int x = 4 * totalWidth / 7;
            int y = index * 25 + 50;
            int w = 2 * totalWidth / 7;
            writer.setBounds(x, y, w, 20);
            repaint();
        }
    }
    
    class SSAction implements ActionListener {
        String[] labels = { ">", "||" };
        int index = 0;
        public void actionPerformed(ActionEvent e) {
            index = 1 - index;
            stopStart .setText(labels[index]);
            if(index == 0) {
                Common .phase = Common .pause;
            } else {
                Common .phase = Common .go;
            }
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

        JButton addReader = new JButton("Add Reader");
        GridBagConstraints arc = new GridBagConstraints();
        arc .gridx = 1;
        arc .gridy = 0;
        cp .add(addReader, arc);
        addReader .addActionListener(new ReadAction());
        
        stopStart = new JButton(" > ");
        GridBagConstraints ssc = new GridBagConstraints();
        ssc .gridx = 2;
        ssc .gridy = 0;
        cp .add(stopStart, ssc);
        stopStart .addActionListener(new SSAction());
        
        JButton addWriter = new JButton("Add Writer");
        GridBagConstraints awc = new GridBagConstraints();
        awc .gridx = 3;
        awc .gridy = 0;
        cp .add(addWriter, awc);
        addWriter .addActionListener(new WriteAction());
        
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
        dc .gridwidth = 5;
        dc .fill = GridBagConstraints.BOTH;
        cp .add(drawing, dc);
        
        initRW();
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
//             g.setColor(Color.black);
//             g.drawString("Sample Applet", 20, 20);
//             g.setColor(Color.blue);
//             g.drawString("created by BlueJ", 20, 40);
            
            g.setColor(Color.black);
            g.drawRect(2 * getWidth() / 7, 25, 
                3 * getWidth() / 7, getHeight() - 50);
            
            for(Reader r : readers) {
                r.paint(g);
            }
            
            for(Writer w : writers) {
                w.paint(g);
            }
        }
    }
    
    public void start()
    {
        // provide any code requred to run each time 
        // web page is visited
        (new Painter()) . start();
    }
}
