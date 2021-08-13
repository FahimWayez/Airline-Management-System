package accessories;

import java.io.*;

public class ClearConsole 
{
    public void clearConsole() 
    {
        try 
        {  
            ProcessBuilder pBuilder = new ProcessBuilder("cmd", "/c", "cls"); //Clearing a new cmd process to execute "CLS" command
            pBuilder.inheritIO(); //Allowing the new process to use IO of the current java process
            Process process = pBuilder.start(); //Starting the process
            process.waitFor(); //Waitingfor the process to be completed
        } 
        catch (InterruptedException ex) 
        {
            System.err.println(ex);
        } 
        catch (IOException ex) 
        {
            System.err.println(ex);
        }
    }
}


