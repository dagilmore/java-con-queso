package com.hooper.semaphoreSyntax;

import java.util.concurrent.*;

public class Setup
{
    private Semaphore s;
    public Process gaggle[];
    
    public Setup(int n)
    {
        s = new Semaphore(1);
        
        gaggle = new Process[n];
        for (int i = 0; i < n; i++) {
            gaggle[i] = new Process(s);
        }
    }
    
    public void start()
    {
        for (Process p: gaggle) {
            p.start();
        }
    }
}
