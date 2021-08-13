package endingPackage;

import accessories.*;
import java.util.*;

public class ExitSystem extends EndingPage
{
    /*--------------------------------------
             Quitting the system
    ---------------------------------------*/
    public void exit()
    {
        System.out.println("You have successfully logged out from the system.");
        Scanner scan = new Scanner(System.in);
        {
            System.out.print("Press enter to exit the system. ");
            scan.nextLine();
        }
		{
            ClearConsole clsconsole = new ClearConsole();
            clsconsole.clearConsole();
        }
        System.exit(0); 
    }
}