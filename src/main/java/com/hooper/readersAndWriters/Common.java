import java.util.Random;

/**
 * a class of static variables and methods to control
 * the progress and logging of the simulation
 * 
 * @author William H. Hooper
 * @version 2006-05-07
 * @version revised 2010-02-16
 */
public class Common
{
    // shared status allows the host to 
    // control progress of readers and writers
    public static volatile int phase;
    public static final int setup = 101;
    public static final int go = 102;
    public static final int pause = 103;
    public static final int cleanup = 104;
    
    // shared variable to control log output
    private static int count = 0;
    private static Random gen;
    
    /**
     * log progress to standard output
     * @param s the status to log
     */
    public static void log(String name, String s, int delay)
    {
        if(gen == null) {
            gen = new Random();
        }
//         System.out.print(name + s + " ");
//         if((++count % 20) == 0)
//             System.out.println();
        int multiplier = (1 << gen.nextInt(6)) >> 1;
        delay *= multiplier;
        if (delay > 0) {
            try { Thread.sleep(delay);}
            catch (InterruptedException e) { }
        }
    }
}
