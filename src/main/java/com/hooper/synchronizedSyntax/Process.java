public class Process
extends Thread
{
    // instance variables - replace the example below with your own
    private Object s;

    /**
     * Constructor for objects of class Process
     */
    public Process(Object shared)
    {
        this.s = shared;
    }
    
    public void walk()
    throws InterruptedException
    {
        synchronized(s) {
            // Warning!  The first process to call this will hang.
            s.wait();
            s.notify();
            s.notifyAll();
        }
    }
    
    public void run()
    {
        try {
            walk();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
