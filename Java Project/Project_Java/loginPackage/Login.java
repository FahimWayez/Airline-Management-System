package loginPackage;

import java.util.*;

public class Login extends StartingPage
{	
	/*--------------------------------------
		  The Login System
   	---------------------------------------*/
	public void loginInfo()
    {
        System.out.println("\n\n                                                                          ----------------------------------------------");
        System.out.println("                                                                          |  Welcome to the flight reservation system  |");
        System.out.println("                                                                          ----------------------------------------------");
        Scanner scan = new Scanner(System.in);
        {
            System.out.print("\n                                                                            Press enter to continue to the login page\n\n ");
            scan.nextLine();
        }

        int count = 0;
        while (count <= 1) 
        {
            Scanner input = new Scanner(System.in);
            String username;
            String password;

            System.out.print("Enter admin Username: ");
            username = input.next();
            System.out.print("\nEnter admin Password: ");
            password = input.next();
            if ("airport".equals(username) && "manager".equals(password)) //Username and Password 
            {
                System.out.println("\nUser successfully logged-in. ");
            }          
            else 
            {
                System.out.println("Invalid Username or Password. Please try again from start.\n");
                System.exit(0);
            }
   
            count++;
            break;
        }
    }
}