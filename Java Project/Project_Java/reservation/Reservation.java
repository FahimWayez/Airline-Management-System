package reservation;

import java.io.*;
import java.util.*;

public class Reservation 
{
   private String ID;
   private String name;
   private String seat;
   private String flight;
   private static Reservation[] reservations = new Reservation[100];
  
   public Reservation(String passengerID, String passengerName, String seatNumber, String flightNumber) 
   {
       this.ID    = passengerID.trim();
       this.name   = passengerName.trim();
       this.seat   = seatNumber.trim();
       this.flight   = flightNumber.trim();
   }
  
   public Reservation(String reservationInformation) 
   {
       String[] reservationData = reservationInformation.split("\t");
       this.ID    = reservationData[0].trim();
       this.name   = reservationData[1].trim();
       this.seat   = reservationData[2].trim();
       this.flight   = reservationData[3].trim();
   }
  
   
   public String getID() 
   {
       return ID;
   }
   
   public String getName() 
   {
       return name;
   }
   
   public String getSeat() 
   {
       return seat;
   }
   
   public String getFlight() 
   {
       return flight;
   }
  
   public static Reservation[] getReservations() 
   {
       Reservation[] tempArray = new Reservation[100];
      
       for(int i = 0; i<100; i++) 
       {
           if(reservations[i] == null)
               break;
          
           tempArray[i] = new Reservation(reservations[i].toString());
       }
       return tempArray;
   }
  
   public String toString() 
   {
       return getID() + "\t" + getName() + "\t" + getSeat() + "\t" + getFlight();
   }
  
   
   /*--------------------------------------------------------------------------------------------------
   * Loads the reservations stored inside the reservaions file into an array of 100 reservations objects
   * filePath is the path to the reservations databse
   * returns a message by informing the possible issues with the files or data
   ---------------------------------------------------------------------------------------------------*/
   public static int load() 
   {
       final String filePath = "reservations.txt";
       int errorCode = 0; // Indicates if there is any error in the program.
       final File location = new File(filePath);
       Scanner file = null;

       try 
       {
           file = new Scanner(location);
       } 
       catch (FileNotFoundException e) 
       {
           System.out.print("\n\nThere are no Reservations data found at that exact file location. ");
				int timeToWait = 3; // second
				System.out.print("Creating reservation database");
				try 
				{
					for (int i = 0; i < timeToWait; i++) 
					{
						Thread.sleep(1000);
						System.out.print(".");
					}
				} 
				catch (InterruptedException ie) 
				{
					Thread.currentThread().interrupt();
				}
           System.out.print("\nDatabase has been created successfully.\n");

           try 
           {
               PrintWriter writer = new PrintWriter(new FileWriter(location));
               writer.println("Passenger ID    \tPassenger Name\t     Seat Number \tFlight ID");
               writer.close();
           } 
           catch (IOException e1) { errorCode = 2; } // If can not write to the file.
           if(errorCode == 0)
               errorCode = 1; // No data exists as the database is newly created.
       }

       
       if(location.length() == 0 && errorCode == 0) 
       {
           System.out.println("\n\nThere are no database found in the exact file location.Creating reservations databse....\nDatabase created successfully.\n");           System.out.print("\n\nThere are no Reservations data found at that exact file location. ");
				int timeToWait = 3; // second
				System.out.print("Creating reservation database");
				try 
				{
					for (int i = 0; i < timeToWait; i++) 
					{
						Thread.sleep(1000);
						System.out.print(".");
					}
				} 
				catch (InterruptedException ie) 
				{
					Thread.currentThread().interrupt();
				}
           System.out.print("\nDatabase has been created successfully.\n");

           try 
           {
               PrintWriter writer = new PrintWriter(new FileWriter(location));
               writer.println("Passenger ID    \tPassenger Name\t     Seat Number \tFlight ID");
               writer.close();
           } 
           catch (IOException e) { errorCode = 2; } // If can not write to the file
           if(errorCode == 0)
               errorCode = 1; // No data exists as the database is newly created.
       }
      
  
       if(errorCode == 0) // If the data exists
       {
           file.nextLine();
           int numberOfLines = 0;
           while(numberOfLines < 100 && file.hasNextLine()) 
           {  
               Reservation.reservations[numberOfLines] = new Reservation(file.nextLine());
               numberOfLines++;
           }
           if(file.hasNextLine() && numberOfLines == 100) // If there is already 100 reservations
               errorCode = 4; 

           file.close();
       }
       return errorCode;
   }
  
  
   public void save() //Taking the current reservation and appending it to the reservation ".txt" file
   {
       try 
       {   
           final String fileName = "reservations.txt";
           File file = new File(fileName);
           boolean fileDidNotExist = !file.exists();      
           PrintWriter writer = new PrintWriter(new FileWriter(file, true));
          
           if(fileDidNotExist)
               writer.println("Passenger ID\tPassenger Name\tSeat Number\tFlight ID");
          
           writer.println(this.toString());
           writer.close();
       } 
       catch (IOException e) {}
       Reservation.load();
   }
   
   public boolean equals(Object otherPassenger) 
   {
       return this.toString().equals(otherPassenger.toString());
   }
}