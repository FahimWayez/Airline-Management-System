import java.util.*;
import loginPackage.*;
import accessories.*;
import reservation.*;
import endingPackage.*;
import flight.*;

public class Start 
{  
   public static void main(String[] args) 
   {
        Login login = new Login();
		login.loginInfo();
       
       Flight.start();
       ExitSystem exitSystem = new ExitSystem();
       boolean userWantsToContinue = true;
       String flightNumber;
         
       do 
       {
			Scanner scan = new Scanner(System.in);
           {
               System.out.print("\n\n\n\n\n\n\n                                                                            Press enter to continue to the next page\n\n ");
               scan.nextLine();
           }
           {
               ClearConsole clsconsole = new ClearConsole();
               clsconsole.clearConsole();
           }
           {
               Sleep slp = new Sleep();
               slp.sleep(null, 0);
           }
           System.out.println("\n\n                                                                                  AIRPLANE RESERVATION SYSTEM");
           System.out.println("                                                                              -----------------------------------\n\n");
           System.out.println("\t\t\t\t\t\t\t\t-----------------------------------------------------------------");
           String answer = ask("\t\t\t\t\t\t\t\t| Press 1 to see the list of all flights \t\t\t|\n\t\t\t\t\t\t\t\t| Press 2 to add a new flight to the list \t\t\t|\n\t\t\t\t\t\t\t\t| Press 3  to see the seat map \t\t\t\t\t|\n\t\t\t\t\t\t\t\t| Press 4 to make a new reservation \t\t\t\t|\n\t\t\t\t\t\t\t\t| Press 5 to see the list of reservations for all flights       |\n\t\t\t\t\t\t\t\t| Press 6 to see the list of reservations for a specific flight |\n\t\t\t\t\t\t\t\t| Press 7 to exit the system \t\t\t\t\t|\n\nPlease enter your choice: ");
           answer = answer.toLowerCase();
           switch(answer) 
           {
            case "1.":  case "1": case "list all flight": listAllFlights();
                        break;
           
            case "2.":  case "2": case "add a new flight": addANewFlight(null);
                        break;
            
            case "3.":  case "3":case "display passengers seats map": flightNumber = ask("\nPlease enter the flight ID to see the seat map: ");
                        displayPassengersSeatsMap(flightNumber);
                        break;
            
            case "4.": case "4": case "make a new reservation": makeANewReservation();
                        break;

            case "5.": case "5": case "list all passenger reservations for all flights": case "list all passenger reservations": listAllReservations();
                        break;
            
            case "6.": case "6": case "list all passenger reservations for specific flight": flightNumber = ask("\nPlease enter the flight ID to see the seat map: ");
                        listAllReservations(flightNumber);
                        break;
            
            case "7.": case "7": case "exit": exitSystem.exit();
                        break;
            
            default: System.out.println("\nSorry. Invalid input. Please try again.\n\n");
                        break;
           }
       } 
       while (userWantsToContinue);
   }

   private static String ask(String question) 
   {
       Scanner input = new Scanner(System.in);
       System.out.println(question);
       String response = input.nextLine();
       return response;
   }  
   
   /*-------------------------------------------
    Method for listing all the flights
   -------------------------------------------*/
   public static void listAllFlights()
   {    
       System.out.println("\t\t\t\t\t\t\t\t\t\t\tFlight lists");
       System.out.println("\t\t\t\t\t\t\t\t\t\t-----------------------------------\n\n");
       System.out.println("Flight ID\tFlight Date\tDeparture Time\tArrival Time\tDeparting Cityt\tDestintation City\tAvailableSeats");
       for (Flight flightSearch : Flight.getFlights()) 
       {
           if (flightSearch == null)
               return;

           System.out.println(flightSearch);
       }
   }
  
   /*-------------------------------------------
    Method for adding a new flight to the list
   -------------------------------------------*/
   public static void addANewFlight(Flight newFlight) 
   {
       if(newFlight==null) 
       {
           Scanner input = new Scanner(System.in);
           String flightInformation = "";
           String info = "";
          
           do 
           {
               System.out.print("\n\nPlease enter the flight number: ");
               info = input.nextLine();
                              
               if(info.length() != 5)
                   System.out.println("\nInvalid Flight ID. (A valid Flight ID is made of 5 charecters.)\n\n");
              
               if(Flight.findFlight(info) != null) 
               {
                   System.out.println("\nInvalidThe Flight is already in the list. Thank you.\n\n");
                   info = "";
               }          
           } 
           while(info.length() != 5);
           flightInformation += info + "\t";
          
           do 
           {
               System.out.print("\nPlease enter the date of departure (Format: 12-12-12, 12/12/12): ");
               info = input.nextLine();
              
               if(!(info.length() ==  8|| info.length() == 10))
                   System.out.println("\nSorry! That is not a valid date. Please try again.\n\n");
           } 
           while(!(info.length() == 8 || info.length() == 10));
           flightInformation += info;
           
	   do 
           {
               System.out.print("\nPlease enter the time of departure (Format: 12:12, 12.12): ");
               info = input.nextLine();
              
               if(info.length() != 5 && info.charAt(2) != ':' && info.charAt(2) != '.')
               System.out.println("\nThat is not a valid time. Please try again\n\n");
           } 
           while(info.length() != 5 && info.charAt(2) != ':' && info.charAt(2) != '.');
           flightInformation += "\t" + info;
          
           do 
           {
               System.out.print("\nPlease enter the time of arrival (Format: 12:12, 12.12): ");
               info = input.nextLine();
              
               if(info.length() != 5 && info.charAt(2) != ':' && info.charAt(2) != '.')
                   System.out.println("\nThat is not a valid time. Please try again\n\n");
           } 
           while(info.length() != 5 && info.charAt(2) != ':' && info.charAt(2) != '.');
           flightInformation += "\t" + info;
          
           String[] requests = 
										{
											"\nPlease enter the Departing city: ",
											"\nPlease enter the Arriving city: "
										};
          
           for(String request : requests) 
           {
               System.out.print(request);
               flightInformation += "\t" + input.nextLine();
           }
          
           int seats; 
           do 
           {
               System.out.print("\nPlease enter the amount of seats available: ");
               seats = input.nextInt();
			   
			   System.out.println("\n\nThe flight has been added to the list successfully.\n");
              
               if(seats < 0)
                   System.out.println("\nThat is not a valid number. Please try again.\n\n");
           } 
           
           while(seats < 0);
           flightInformation += "\t" + seats;
           newFlight = new Flight(flightInformation);
       }
       newFlight.saveLayout();
       newFlight.save();
   }
   
   /*-------------------------------------------
    Method for displaying the seat map
   -------------------------------------------*/  
   public static boolean displayPassengersSeatsMap(String flightNumber) 
   {
       System.out.println("\n\n\nThe seatmap for the given flight: \n");
       Flight flight = Flight.findFlight(flightNumber);
       if(flight!=null) 
       {
           flight.loadReservations();
           System.out.println(flight.getLayout());
           return true;
       }
       return false;
   }
  
   /*-------------------------------------------
    Method for making a new reservation
   -------------------------------------------*/ 	
   public static boolean makeANewReservation() 
   {
       Scanner input = new Scanner(System.in);
       String flightNumber = null;
       boolean theFlightExists = false;

       do 
       {
           System.out.print("\n\nPlease enter the flight ID in which you want to reserve a new seat: ");
           flightNumber = input.nextLine();

           if (flightNumber.length() != 5)
               System.out.println("\nInvalid Flight ID. (A valid Flight ID is made of 5 charecters.)\n\n");

           theFlightExists = Flight.findFlight(flightNumber) != null;
           if (!theFlightExists) 
           {
               System.out.println("\nThat flight does not exist.\n\n");
           } 
           else 
           {
               theFlightExists = true;
           }
       } 

       while (!theFlightExists);
       Flight flight = Flight.findFlight(flightNumber);
       boolean found = flight != null;

       if (found) 
       {
           System.out.print("\nPlease enter the Passenger ID: ");
           String passengerID = input.nextLine();
           System.out.print("\nPlease enter the Passenger name: ");
           String passengerName = input.nextLine();
           System.out.println(flight.getLayout() + '\n');
           System.out.print("\nPlease enter the seat you want to reserve (Format: 1A, 6D): ");
           String givenSeatNumber = input.nextLine().toUpperCase();
		   

           while (!flight.makeReservation(givenSeatNumber, true)) 
           {
               System.out.print("\nPlease enter the seat you want to reserve (Format: 1A, 6D): ");
               givenSeatNumber = input.nextLine().toUpperCase();
           }
           Reservation passenger = new Reservation(passengerID, passengerName, givenSeatNumber, flightNumber);
           passenger.save();
       }
       return found;
   }
  
   /*---------------------------------------------------------------
     Method for listing all the reservations made in all the flights
   ---------------------------------------------------------------*/ 
   public static void listAllReservations()
   {
       System.out.println("Passenger ID\tPassenger Name\tSeat Number\tFlight ID");
       for(Reservation passengerSearch : Reservation.getReservations()) 
       {
           if(passengerSearch == null)
               return;
          
           System.out.println(passengerSearch);
       }
   }
  
   /*----------------------------------------------------------------
    Method for listing all the reservations made in a specific flight
   ----------------------------------------------------------------*/
   public static void listAllReservations(String flightNumber) 
   {
       boolean found = false;
       for(Reservation passenger : Reservation.getReservations()) 
       {
           if(passenger == null)
               break;

           if(passenger.getFlight().equals(flightNumber)) 
           {
               found = true;
               break;
           }
       }
       if(!found) 
       {
           System.out.println("\nSorry! That flight is not in our database. Please try again.\n\n");
           return;
       }
      
       System.out.println("Passenger ID\tPassenger Name\tSeat Number\tFlight ID");
       for(Reservation passenger : Reservation.getReservations()) 
	{
           if(passenger == null)
               break;

           if(passenger.getFlight().equals(flightNumber))
               System.out.println(passenger);
       }
   }
}