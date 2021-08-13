package accessories;

public class Sleep extends ASleep 
{
    /*--------------------------------------
	          The delay
    ---------------------------------------*/

    public void sleep(String message, int sec)

    {
        int timeToWait = 2; // in second
        System.out.print("\t\t\t\t\t\t\t\t\t\t\tPlease Wait.");
        try 
		{
            for (int i = 0; i < timeToWait; i++) 
			{
                Thread.sleep(500);
                System.out.print(".");
            }
        } 
		catch (InterruptedException ie) 
		{
            Thread.currentThread().interrupt();
        }
    }
}