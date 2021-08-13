package flight;

import reservation.*;
import java.io.*;
import java.util.*;

public class Flight 
{
   private final String number;
   private final String date;
   private final String departureTime;
   private final String arrivalTime;
   private final String departureCity;
   private final String destinationCity;
   private int seatsAvailable;
   private static Flight[] flights = new Flight[100];
   private char[][] layout =
											{
												{'1','A','B','C','D','E','F','G'},
												{'2','A','B','C','D','E','F','G'},
												{'3','A','B','C','D','E','F','G'},
												{'4','A','B','C','D','E','F','G'},
												{'5','A','B','C','D','E','F','G'},
												{'6','A','B','C','D','E','F','G'},
												{'7','A','B','C','D','E','F','G'},
												{'8','A','B','C','D','E','F','G'},
												{'9','A','B','C','D','E','F','G'},
												{'0','A','B','C','D','E','F','G'}
											}; 

   public Flight(final String flightInformation) 
   {
       final String[] flightData = flightInformation.split("\t");
       this.number        = flightData[0].trim();
       this.date           = flightData[1].trim();
       this.departureTime    = flightData[2].trim();
       this.arrivalTime    = flightData[3].trim();
       this.departureCity    = flightData[4].trim();
       this.destinationCity = flightData[5].trim();
       this.seatsAvailable = Integer.parseInt(flightData[6].trim()) < 0 ? 0 : Integer.parseInt(flightData[6].trim()) ;
      
       this.loadReservations();
   }

   
   public String getFlightNumber() 
   {
       return number;
   }

   public String getFlightDate() 
   {
       return date;
   }
   
   public String getDepartureTime() 
   {
       return departureTime;
   }
   
   public String getArrivalTime() 
   {
       return arrivalTime;
   }
   
   public String getDepartureCity() 
   {
       return departureCity;
   }
   
   public String getDestinationCity() 
   {
       return destinationCity;
   }
   
   private boolean setSeatsAvailable(int deltaSeats) 
   {     
       if(this.seatsAvailable == 0) 
       {
           System.out.println("\nSorry! There are no more seats available on this flight.\n\n");
           return false;
       }
      
   
       if(deltaSeats == -1) 
       {
           this.seatsAvailable--;
       } 
       
       else 
       {
           this.seatsAvailable = deltaSeats;
       }
       
       return true;
   }

   public int    getSeatsAvailable() 
   {
       return seatsAvailable;
   }

   public static Flight[] getFlights() 
   {
       Flight[] tempArray = new Flight[100];
      
       for(int i = 0; i<100; i++) 
       {
           if(flights[i] == null)
               break;
          
           tempArray[i] = new Flight(flights[i].toString());
       }
          
       return tempArray;
   }
    
  
   public boolean makeReservation(String seat, final boolean detailedOutput) // returning true or false represents if the reservation is made successfully or not
   {									    																   // detailedOutput represents if the message for different errors or success should be desplayed or not
       seat = seat.toUpperCase();
       boolean onLastRow = seat.length() == 3;
       int col;
       int row;
      
       if(this.seatsAvailable == 0) 
       {
           if(detailedOutput)
               System.out.println("\nSorry! Flight number " + this.getFlightNumber() + " has no more seats available.\n\n");
           return false;
       } 
       
       else if(seat.length() > 3 || seat.length() < 1) 
       {
           if(detailedOutput)
               System.out.println("\nWrong Input. (Format: 1A, 9F)\n\n");
           return false;
       } 
       
       else if(onLastRow && seat.substring(0, 2).equals("10")) 
       {
           row = 9;
           col = seat.charAt(2) - 'A' + 1;
       } 
       
       else if (onLastRow) 
       {
           if(detailedOutput)
               System.out.println("\nPlease enter a valid seat number. (Format: 1A, 9F)\n\n");
           return false;
       } 
       
       else 
       {
           row = seat.charAt(0) - '0' - 1;
           col = seat.charAt(1) - 'A' + 1;
       }
      
      
       if(row < 0 || row > 10 || col < 1 || col > 7) 
       {
           if(detailedOutput)
           	System.out.println("\nPlease enter a valid seat number. (Format: 1A, 9F)\n\n");
           return false;
       } 
       
       else if(layout[row][col] == 'X') 
       {
           if(detailedOutput)
               System.out.println("\nThis space is already reserved. Please choose another seat to reesrerve.\n\n");
           return false;
       }
      
       layout[row][col] = 'X';
       setSeatsAvailable(-1);
       saveLayout();
       if(detailedOutput)
           System.out.println("\n\nResevation is made successfulyl! Thank you for being with us. Please come again.\n\n");
       return true;
   }

   /*--------------------------------------------------------------------------------------------------
   * Goes through the array of flights and tries to find the specific flight as the provided flight ID
   * If the flight does not exist it will return null, or else it will return the flight object
   ---------------------------------------------------------------------------------------------------*/
   public static Flight findFlight(String flightNumber) 
   {
       Flight flight = null;
       for(Flight flightSearch : Flight.flights) 
       {
           if(flightSearch == null) 
           {
               break;
           }
           if(flightSearch.getFlightNumber().equals(flightNumber)) 
           {
               flight = flightSearch;
               break;
           }
       }
       return flight;
   }
  
   /*--------------------------------------------------------------------------------------------------
   * Goes through the current flight's local seat map array and returns it as a string type
   * The seat map of the current flight will be returned
   ---------------------------------------------------------------------------------------------------*/
   public String getLayout() 
   {
       String seatLayout = "";
       for(int row = 0; row < 10; row++) 
       {
           seatLayout += row + 1 + "\t" + layout[row][1] + "   " + layout[row][2] + " " + layout[row][3] + " " + layout[row][4] + "   " + layout[row][5] + " " + layout[row][6] + " " + layout[row][7] + "\n\n";
       }
       return seatLayout.trim();
   }
  
   /*--------------------------------------------------------------------------------------------------
   * Reads through all the passenger reservation and mark off any one
   * Reservation listed for the current flight on the seat layout
   ---------------------------------------------------------------------------------------------------*/
   public void loadReservations() 
   {
       for(Reservation passenger : Reservation.getReservations()) 
       {
           if(passenger == null) // Checks if the application has gone through all the passengers
               break;
          
           if(passenger.getFlight().equals(this.number)) // If the passenger has a reservation for this flight and the seat number listed is correct, the reservation will be made.
               this.makeReservation(passenger.getSeat(), false);
       }
   }
  
  
   public static void start() // Loads all the data from all the files
   {
       Reservation.load();
       Flight.load();
   }
  
   
   public String toString() 
   {
       return getFlightNumber() + "\t" + getFlightDate() + "\t" + getDepartureTime() + "\t" + getArrivalTime() + "\t" + getDepartureCity() + "\t" + getDestinationCity() + "\t" + getSeatsAvailable();
   }
  

   /*----------------------------------------
           File Interaction Starts
   ------------------------------------------*/  
   public void saveLayout() // Saves the seat map of the current flight to a ".txt" file that shares its flight number
   {
       try 
       {
           PrintWriter writer = new PrintWriter(this.getFlightNumber() + ".txt");
           writer.println(this.getLayout());
           writer.close();
       } 
       catch (FileNotFoundException e) {}
   }
  
   public void save() 
   {
       try 
       {        
           final String fileName = "flights.txt";
           File file = new File(fileName);
           boolean fileDidNotExist = !file.exists();          
           PrintWriter writer = new PrintWriter(new FileWriter(fileName, true));
          
           if(fileDidNotExist)
               writer.println("Flight ID\tDeparture Date\tDeparture Time\tArrival Time\tDeparting City\tDestination City\tAvailable Seats");
          
           writer.println(this.toString());
           writer.close(); 
       } 
       catch (IOException e) {}
       Flight.load();
   }
  

   /*--------------------------------------------------------------------------------------------------
   * Loads the flights stored inside the flights file into an array of 100 Flight objects
   * filePath is the path to the flights database
   * An alertCode which informs the user of possible issues with the files or data will be returned
   ---------------------------------------------------------------------------------------------------*/
   public static int load() 
   {
       final String filePath = "flights.txt";
       int errorCode    = 0; // Indicates if there are any error within the program
      
       File location = new File(filePath);
       Scanner file = null;
       try 
       {
           file = new Scanner(location);
       } 
       
       catch (FileNotFoundException e) 
       {
           System.out.print("\n\nThere are no Flight data found at that exact file location. ");
                int timeToWait = 3; // second
                System.out.print("Creating flight database");
                try {
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
               PrintWriter writer = new PrintWriter(new FileWriter("flights.txt"));
               writer.println("Flight ID\tDeparture Date\tDeparture Time\tArrival Time\tDeparting City\tDestination City\tAvailable Seats");
               writer.close();
           }        
           catch (IOException e1) { errorCode = 2; } // If can not write to the file.          
           if(errorCode == 0)
               errorCode = 1; // No data exists because the database is newly created.
       }

       
       if(location.length() == 0 && errorCode == 0) 
       {
          System.out.print("\n\nThere are no Flight data found at that exact file location. ");
                int timeToWait = 3; // second
                System.out.print("Creating flight database");
                try {
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
               writer.println("Flight ID\tDeparture Date\tDeparture Time\tArrival Time\tDeparting City\tDestination City\tAvailable Seats");
               writer.close();
           } 
           catch (IOException e) { errorCode = 2; } // If can not write to the file
           if(errorCode == 0)
               errorCode = 1; // No data exists because the database is newly created.  
       }

       
       if(errorCode == 0) //If the data exists.
       {
           file.nextLine();
           int numberOfLines = 0;
           while(numberOfLines < 100 && file.hasNextLine()) // Assigning the data to the correct variables
           {  
               Flight.flights[numberOfLines] = new Flight(file.nextLine());
               numberOfLines++;  
           }
          
           if(file.hasNextLine() && numberOfLines == 100)
               errorCode = 3; 
          
           file.close();
       }
       return errorCode;
   }  
}