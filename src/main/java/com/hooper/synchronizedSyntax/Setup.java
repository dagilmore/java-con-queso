public class Setup
{
    private Object mutex;
    public Process gaggle[];
    
    public Setup(int n)
    {
        mutex = "Unique";
        
        gaggle = new Process[n];
        for (int i = 0; i < n; i++) {
            gaggle[i] = new Process(mutex);
        }
    }
    
    public void start()
    {
        for (Process p: gaggle) {
            p.start();
        }
    }
}
