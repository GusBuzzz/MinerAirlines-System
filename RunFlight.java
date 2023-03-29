/*
 * Name: Gustavo Rubio
 * Date: March 21, 2023
 * CS 3331 – Advanced Object-Oriented Programming – Spring 2023
 * Instructor: Dr. Mejia
 * Assignment: PA3
 * This work was done individually and completely on my own. I did not share, reproduce, or alter any part of this assignment for any purpose.
 * I did not share code, upload this assignment online in any form, or view/received/modified code written from anyone else.
 * All deliverables were produced entirely on my own. This assignment is part of an academic course at The University of Texas at El Paso 
 * and a grade will be assigned for the work I produced.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.xml.validation.ValidatorHandler;
/**
 * The RunFlight main class is responsible for running all necessary methods withing the PA3 folder. the system provides a user friendly 
 * interface for the customer/employee to access the MinerAirlines System. Each the customer and the employee have different use cases on
 * how to use the system; however, the employy may still purchase tickets.
 * @version 1.0
 */

public class RunFlight {
  public static void main(String[] args) throws IOException{
    String flightSchedule = "FlightSchedule.csv";
    String customerInfo = "CustomerListPA4.csv";
    String ticketPurchaseHistory = "CustomerTicketsPurchase.csv";
    String cancelFlightList = "cancelFlights.csv";
    String updatedCustomerLog = "NewCustomerListPA3.csv";
    String updatedFlightScheduleLog = "NewFlightSchedule.csv";
    String logFile = "Log.txt";
    boolean verifyInput = true;
    boolean verifyMenuOption = true;
    boolean isEmployee = false;
    boolean isEmployeeCustomer = false;
    String exitOption = "";
    String firstName = "";
    String lastName = "";
    String userName = "";
    String password = "";

    HashMap<Integer,Flight> flightLog = flights(flightSchedule);
    HashMap<Integer,Flight> flightTypeList = sortFlights(flightLog); // factory 2nd design pattern
    HashMap<Integer,Customer> customerLog = customers(customerInfo);
    ArrayList<Employee> employeeLog = employees(customerLog);
    ArrayList<Ticket> ticketsBought = new ArrayList<Ticket>();
    Scanner userInput = new Scanner(System.in);
    FileWriter myWriter = new FileWriter(logFile);
    FileWriter csvWriter = new FileWriter(ticketPurchaseHistory, true); //TRUE allows us to not override files after program ends
    FileWriter cancelFlightCsv = new FileWriter(cancelFlightList,true);
    FileWriter updateCustomerLog = new FileWriter(updatedCustomerLog);
    FileWriter updateFlightLog = new FileWriter(updatedFlightScheduleLog);

    System.out.println("\nWelcome to Miner Airport, please enter your first and last name.");

    while(verifyInput){ //Check if customer is in the list of customers
        System.out.print("First Name: "); 
        firstName = userInput.nextLine();
        System.out.print("Last Name: ");
        lastName = userInput.nextLine();
        if(isInCustomerList(customerLog, firstName, lastName)){
          verifyInput = false;
          System.out.println("\nWecome back " + firstName + " " + lastName +"!");
        }
        else{
            System.out.println("\nSorry, that name was not in our list. Please try again");
        }
    }

    verifyInput = true; //reset to true for future inputs

    Customer currCustomer = findCustomerObj(customerLog, firstName, lastName);

    isEmployee = findEmplyeeObj(employeeLog, firstName, lastName);

    while(verifyInput && isEmployee){ //Verify username and password for customer
      System.out.println("\nPlease login using your Username and Password");
      System.out.print("Username: ");
      userName = userInput.nextLine();
      System.out.print("Password: ");
      password = userInput.nextLine();
      if(currCustomer.getUsername().equals(userName) && currCustomer.getPassword().equals(password)){
        System.out.println("\nWelcome back " + currCustomer.getUsername());
        verifyInput = false;
      }
      else{
        System.out.println("\nUsername or Password is incorret, Please try again.");
      }
        
    }

    verifyInput = true; //reset to true for future inputs

    if(isEmployee){
      System.out.println("\nOur system noticed that your username is in our employee list. Please select an option below.");
      System.out.println("\n1) Make flight changes \n2) Purchase tickets\n");

      while(verifyInput){
        System.out.print("Enter option: ");
        String option = userInput.nextLine();
        if(option.equalsIgnoreCase("1")){
          verifyInput = false;
        }
        else if(option.equalsIgnoreCase("2")){
          verifyInput = false;
          isEmployee = false; //This lets us know that the employee is going to be a customer today
        }
        else{
          System.out.println("\nThat was not an option, please try again.");
        }
      }
    }
    int flightID = -1;
    int verifyFlightID = -1;
    verifyInput = true; //reset to true for future inputs
    boolean verifyIDEmployeeMenu = true;
    while (verifyInput && isEmployee) {

      while (verifyInput && isEmployee && verifyIDEmployeeMenu) { // Employee is logged in
          try {
              System.out.println("\nPlease enter a flight ID to make changes or to view flight status.");
              System.out.print("Enter flight ID: ");
  
              String input = userInput.nextLine();
  
              if (input.matches("-?\\d+")) { // Check if the input is an integer (including negative numbers)
                  flightID = Integer.parseInt(input);
                  //verifyIDEmployeeMenu = false;
                  verifyFlightID = flightID;
  
                  if (verifyFlightID > 0 && verifyFlightID <= flightLog.size()) {
                      System.out.println("\nHere is the information for flight ID " + flightID + ": \n");
                      flightLog.get(verifyFlightID).printFlight();
                      employeeMenuOptions(flightLog.get(flightID), logFile, myWriter, cancelFlightCsv, userInput, ticketHistoryCSVFileReader(ticketPurchaseHistory), customerLog);
                      verifyInput = false;
                      verifyIDEmployeeMenu = false;
                  } else {
                      System.out.println("\n* That Flight ID number does not exist. Please try again. *");
                  }
              } else {
                  System.out.println("\n* Please enter a valid integer for the employee main menu. *");
              }
          } catch (NumberFormatException e) {
              System.out.println("\n* Please enter a valid integer for the employee main menu. *");
          }
      }
  
      verifyInput = true;
  
      System.out.println("\nWould you like to make changes on a different flight?\n\nPlease enter [Y/N]");
  
      while (verifyMenuOption) {
          System.out.print("Enter option: ");
          String verifyCancelTicket = userInput.nextLine();
          if (verifyCancelTicket.equalsIgnoreCase("Y")) {
              verifyMenuOption = false;
              verifyInput = true;
              verifyIDEmployeeMenu = true; // reset the employee ID menu verification
          } else if (verifyCancelTicket.equalsIgnoreCase("N")) {
              verifyMenuOption = false;
              verifyInput = false;
          } else {
              System.out.println("\nThat was not an option. Please try again.");
          }
      }
      verifyMenuOption = true;
    }
  
    /*
    while(verifyInput && isEmployee){

      while(verifyInput && isEmployee && verifyIDEmployeeMenu){ //Employee is logged in
        //System.out.println("\nPlease enter a flight ID to make changes or to view flight status.");
        //System.out.print("Enter flight ID: ");
        try {
          /*
          flightID = Integer.parseInt(userInput.nextLine());
          verifyIDEmployeeMenu = false;
          verifyFlightID = flightID;
          
          while(verifyInput){
            System.out.println("\nPlease enter a flight ID to make changes or to view flight status.");
            System.out.print("Enter flight ID: ");
            flightID = Integer.parseInt(userInput.nextLine());
            verifyIDEmployeeMenu = false;
            verifyFlightID = flightID;
            if(verifyFlightID > 0 && verifyFlightID <= flightLog.size()){
              System.out.println("\nHere is the information for flight ID " + flightID + ": \n");
              flightLog.get(verifyFlightID).printFlight();
              employeeMenuOptions(flightLog.get(flightID), logFile, myWriter, cancelFlightCsv, userInput, ticketHistoryCSVFileReader(ticketPurchaseHistory),customerLog);
              verifyInput = false;
              verifyIDEmployeeMenu = false;
            }
            else{
              System.out.println("\n* That Flight ID number does not exists please try again *");
            }
          }

        } catch (NumberFormatException e) {
            System.out.println("\n* Please enter a valid integer for the employee main menu *");
        }
      }
      
      verifyInput = true;
      System.out.println("\nWould you like to make changes on a different flight? \n\nPleast enter [Y/N]");
      while(verifyMenuOption){
        System.out.print("Enter option: ");
        String verifyCancelTicket = userInput.nextLine();
        if(verifyCancelTicket.equalsIgnoreCase("Y")){
          verifyMenuOption = false;
          verifyInput = true;
        }
        else if(verifyCancelTicket.equalsIgnoreCase("N")){
          verifyMenuOption = false;
          verifyInput = false;
        }
        else{
          System.out.println("\nThat was not an option please try again.");
        }
      }
      verifyMenuOption = true;
    }
    */
    
    //******************************************** Customer ********************************************//
    while(verifyInput && !isEmployee){ //Verify username and password for customer
      if(currCustomer.getRole().equalsIgnoreCase("Customer")){
        System.out.println("\nPlease login using your Username and Password");
        System.out.print("Username: ");
        userName = userInput.nextLine();
        System.out.print("Password: ");
        password = userInput.nextLine();
        if(currCustomer.getUsername().equals(userName) && currCustomer.getPassword().equals(password)){
          System.out.println("\nWelcome back " + currCustomer.getUsername());
          verifyInput = false;
        }
        else{
          System.out.println("\nUsername or Password is incorret, Please try again.");
        }

      }
      else{ //Employee is already logged in
        verifyInput = false;
      }
    }

    verifyInput = true; //reset to true for future inputs


    int ticketOption = 0; //Option has not been selected


    while(verifyInput && !isEmployee){ //Verify user input when accessing main menu 
      
      while(verifyInput){
        System.out.println("\nPlease select an option before continuing.");
        System.out.print("\n1) View tickets purchased. \n2) Cancel a ticket. \n3) View flights \n4) Log out\n");
        int mainMenuOption = -1;
        
        System.out.print("\nEnter option: ");

        try {
          mainMenuOption = Integer.parseInt(userInput.nextLine());
          verifyMenuOption = false;
        } catch (NumberFormatException e) {
            System.out.println("\n* Please enter a valid integer for the main menu *");
        }
        
        if(mainMenuOption == 1){
          if(ticketsBought.size() == 0){
            System.out.println("\nIt seems that you have not bought any tickets yet.");
          }
          else{
            System.out.println("\nHere is a list of all the ticket(s) bought.");
            printTicketsBought(ticketsBought);
          }
        }
        else if(mainMenuOption == 2){
            if(ticketsBought.size() == 0){
              System.out.println("\nYou cannot cancel a ticket since you have not bought one yet.");
            }
            else{
              printTicketsBought(ticketsBought);
              System.out.println("\nBased on this list which ticket do you want to cancel?");
              System.out.println("\n* Please be aware that the MinerAirlines transaction fee WILL NOT be returned *");
              System.out.print("\nEnter option: ");
              int ticketCancel = Integer.parseInt(userInput.nextLine());
              System.out.println("\nAre you sure you want to cancel ticket number: " + ticketCancel + ". Enter Y/N.");
              while(verifyInput){
                System.out.print("Enter option: ");
                String verifyCancelTicket = userInput.nextLine();
                if(verifyCancelTicket.equalsIgnoreCase("Y")){
                  cancelTicketMenu(ticketsBought, currCustomer, flightLog, ticketCancel - 1, myWriter);
                  verifyInput = false;
                }
                else if(verifyCancelTicket.equalsIgnoreCase("N")){
                  System.out.println("\nTicket was not canceled.");
                  verifyInput = false;
                }
                else{
                  System.out.println("\nThat was not an option please try again.");
                }
              }
              verifyInput = true;
            }
        }
        else if(mainMenuOption == 3){
          verifyInput = true; //reset to true for future inputs
          flightID = -1;
          String flightNumber = "";
          String code = "";
          String originCode = "";
          String destinationCode = "";
          boolean verifyID = true;
          boolean verifyFlightNumber = true;
          HashMap<Integer, Flight> flightsByCodes = new HashMap<Integer, Flight>();
          
          System.out.println("\nHow would you like to search for your flight?" +"\n\n- To search by Flight ID enter \"FLight ID\". " + "\n- To search by Flight Number enter \"Flight Number\"." 
          + "\n- To search by Orgin/Destination Airport Code enter \"Codes\".");
          while(verifyInput){
            System.out.print("Enter option: ");
            String searchOption = userInput.nextLine();
            if (searchOption.equalsIgnoreCase("FLight ID")){
              System.out.println("\nPlease enter the flight ID number, remember you can only purchace one ticket per transaction.");
              while (verifyID) {
                System.out.print("Enter Flight ID: ");
                try {
                    flightID = Integer.parseInt(userInput.nextLine());
                    if (flightID > 0 && flightID < flightLog.size()) {
                        verifyID = false;
                    } else {
                        System.out.println("\nInvalid Flight ID number please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n* Please enter a valid integer for the Flight ID *");
                }
              }
              verifyInput = false;
            } else if (searchOption.equalsIgnoreCase("FLight Number")){
              System.out.println("\nPlease enter the flight number, remember you can only purchace one ticket per transaction.");
              System.out.print("Enter Flight Number: ");
              flightNumber = userInput.nextLine();
              flightID = searchByFlightNumber(flightLog, flightNumber, userInput);
              verifyInput = false;
            } else if (searchOption.equalsIgnoreCase("Codes")){
              
              while(verifyInput){
                System.out.println("\nRemember you can only purchace one ticket per transaction.");
                System.out.println("\nPlease enter the origin code.");
                System.out.print("Enter origin code: ");
                originCode = userInput.nextLine();
                System.out.println("\nPlease enter the destination code.");
                System.out.print("Enter destination code: ");
                destinationCode = userInput.nextLine();
                flightsByCodes = flightsByCodes(flightLog, originCode, destinationCode);
                if (flightsByCodes.size() > 0){
                  verifyInput = false;
                  printFlightsByCodes(flightLog, originCode, destinationCode, userInput);
                }
                else{
                  System.out.println("\n* Invalid Origin/Destination Code, Please try again *");
                }
              }
              System.out.println("\nBased on these flights shown, how would you like to select your flight?" + "\n- To search by Flight ID enter \"FLight ID\". " + "\n- To search by Flight Number enter \"Flight Number\".");
              verifyInput = true;
              while(verifyInput){
                System.out.print("Enter option: ");
                searchOption = userInput.nextLine();
                if (searchOption.equalsIgnoreCase("FLight ID")){
                  System.out.println("\nPlease enter the flight ID number, remember you can only purchace one ticket per transaction.");
                  while (verifyID) {
                    System.out.print("Enter Flight ID: ");
                    try {
                        flightID = Integer.parseInt(userInput.nextLine());
                        if (flightExistByID(flightsByCodes, flightID)) {
                            verifyID = false;
                        } else {
                            System.out.println("\n* Invalid Flight ID number based on orgin code:" + originCode + " and destination code: " + destinationCode + " please enter a flight ID that matches the flights above *\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\n* Please enter a valid integer for the Flight ID *");
                    }
                  }
                  verifyInput = false;
                } else if (searchOption.equalsIgnoreCase("FLight Number")){
                  System.out.println("\nPlease enter the flight number, remember you can only purchace one ticket per transaction.");
                  System.out.print("Enter Flight Number: ");
                  flightNumber = userInput.nextLine();
                  flightID = searchByFlightNumber(flightsByCodes, flightNumber, userInput);
                  verifyInput = false;
                } else{
                  System.out.println("\n* That was not an option please try again *");
                }
              }
            } else{
                System.out.println("\n* That was not an option please try again *");
            }
          }
          verifyInput = true; //reset to true for future inputs
          verifyID = true;
          verifyFlightNumber = true;
          verifyMenuOption = true;
          //System.out.println("\nPlease enter a flight ID number, remember you can only purchace one ticket per transaction.");
          //int flightID = Integer.parseInt(userInput.nextLine());
          System.out.println("\nHere is the flight's information that you have choosen.\n");
          flightLog.get(flightID).printFlight();
          if(flightLog.get(flightID).isInternational()){         //Check if this flight has a surcharge
            System.out.println("\nThe flight that you have choosen has an international destination, there will be an extra $" + flightLog.get(flightID).getSurcharge() +
            " PER SEAT that you purchase.");
          }
          System.out.println("\nHere is your available balance and the different ticket prices of Flight ID: " + flightID);
          System.out.print("\nYour available balance is: "); // + currCustomer.getMoneyAvailable()); ///fix format of money
          System.out.printf("%.2f", currCustomer.getMoneyAvailable());
          if(flightLog.get(flightID).getFirstClassSeats() > 0){
            System.out.println("\n1) First class price: " + flightLog.get(flightID).getFirstClassPrice());
          }
          else{
            System.out.println("\n1) First class SOLD OUT!");
            flightLog.get(flightID).setFirstClassSoldOut(true);
          }
          if(flightLog.get(flightID).getBusinessClassSeats() > 0){
            System.out.println("2) Business class price: " + flightLog.get(flightID).getBusinessClassPrice());
          }
          else{
            System.out.println("2) Business class SOLD OUT!");
            flightLog.get(flightID).setBusinessClassSoldOut(true);
          }
          if(flightLog.get(flightID).getMainCabinSeats() > 0){
            System.out.println("3) Main cabin price: " + flightLog.get(flightID).getMainCabinPrice());
          }
          else{
            System.out.println("3) Main cabin class SOLD OUT!");
            flightLog.get(flightID).setMainCabinClassSoldOut(true);
          }
          System.out.println("4) EXIT\n" + "\nPlease type option number:");

          while(verifyMenuOption){ //Verify customer's option input
            System.out.print("Enter option: ");
            try {
              ticketOption = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n* Please enter a valid integer for the Flight ID *");
            }
            if(ticketOption == 1 && flightLog.get(flightID).isFirstClassSoldOut()){
              System.out.println("\nFirst class SOLD OUT!");
            }
            else if (ticketOption == 2 && flightLog.get(flightID).isBusinessClassSoldOut()){
              System.out.println("\nBusiness class SOLD OUT!");

            }
            else if (ticketOption == 3 && flightLog.get(flightID).isMainCabinClassSoldOut()){
              System.out.println("\nMain cabin class SOLD OUT!");
            }
            else if(ticketOption >= 1 && ticketOption <=4){
              verifyMenuOption = false;
            }
            else{
              System.out.println("That is not an option, Please try again.");
            }

          }

          verifyInput = true; //reset to true for future inputs

          if(currCustomer.getRole().equalsIgnoreCase("Employee") && !isEmployee){
            System.out.println("\n* Remeber that you will get a 50% discount on First Class tickets and a 75% discount on all other ticket purchases * \n\n* Fees and taxes still apply *");
          }

          customerPurchaseOptions(flightLog.get(flightID), currCustomer, ticketOption, logFile, myWriter,csvWriter, ticketPurchaseHistory, userInput, ticketsBought, currCustomer.getRole()); //Select the number of purchase tickets

          if(ticketOption != 4){ //Check if the user wanted to exit, the assumption is that maybe they pressed option 4 by mistake
            verifyInput = false;
          }
          else{
            verifyMenuOption = true;

            System.out.println("\nYou have selected option 4, would you like to exit out of the entire program (enter Y) or try again (enter N)?");

            while (verifyMenuOption){ //Verify if customer really wants to exit the program
              System.out.print("\nEnter option: ");
              exitOption = userInput.nextLine();
              if (exitOption.equalsIgnoreCase("Y")){
                verifyInput = false;
                verifyMenuOption = false;
              }
              else if (exitOption.equalsIgnoreCase("N")){
                verifyMenuOption = false;

              }
              else{
                System.out.println("\nThat is not an option, please try again.");
              }
            }

            verifyMenuOption = true;

            }

          verifyMenuOption = true;//reset to true for future inputs

          if(exitOption.equalsIgnoreCase("Y")){

          }else{
            while(verifyMenuOption){ //verify if the customer wants to buy more tickets
              System.out.println("\nWould you like to go back to main menu? Y/N");
              System.out.print("Enter option: ");
              String moreTickets = userInput.nextLine();
              if (moreTickets.equalsIgnoreCase("N")){
                verifyInput = false;
                verifyMenuOption = false;
              }
              else if (moreTickets.equalsIgnoreCase("Y")){
                verifyInput = true;
                verifyMenuOption = false;
              }
              else{
                System.out.println("\nThat was not an option. Please enter Y for YES or N for NO.");
              }
      
            }
          }
          verifyMenuOption = true;
        }
        else if(mainMenuOption == 4){
          verifyInput = false;
        }
        else{
          System.out.println("\n* That was not an option please try again *");
        }
      }
      
    }
    writeUpdatedCustomerFile(customerLog,updateCustomerLog);
    writeUpdatedFlightScheduleFile(flightLog, updatedFlightScheduleLog,updateFlightLog);
    System.out.println("\nThank you for using our system " + currCustomer.getUsername() + " have a nice day!\n");
    myWriter.close();
    csvWriter.close();
    cancelFlightCsv.close();
    updateCustomerLog.close();
    updateFlightLog.close();
    printLog(logFile); //Print out all changes/purcheses that were made
      
    }
    //********************************* Methods *************************************//
    /**
     * 
     * @param currFlightObj In this variable you pass the current flight object that you want to make changes, this can be done by passing the object directly or by using the flights() method.
     * @param logText This text file will log all of the changes that the employee has made throughout this method.
     * @param myWriter In this variable you will pass on the file writter object, this will allow you to only use one file writter instead of creating one per method, all writting will go to the Log.txt file.
     * @param csvWriter In this variable you will pass on the file writter object, all writting from this object will not get overriden when the system terminates.
     * @param userInput In this variable you will pass on the scanner object for all of the incoming user inputs.
     * @param ticketsBought You will need to pass on an array list containing all on the ticket objects that the current user has bought.
     * @param customerLog In this variable you pass the current customer object so that the method can make changes, this can be done by passing the object directly or by using the customers() method.
     * @throws IOException This exception is used for the file writter objects, this will let us know if there was a problem while writting the file.
     */
    public static void employeeMenuOptions(Flight currFlightObj, String logText, FileWriter myWriter, FileWriter csvWriter, Scanner userInput, ArrayList<Ticket> ticketsBought,HashMap<Integer,Customer> customerLog) throws IOException{ //This method provides all availiable changes and options
      String newChange = "";
      String question = "";
      String changeInfo = "";
      boolean verifyInput = true;
      boolean cancelFlightInput = true;
      try {
          myWriter.write("Changes made: \n");
        } catch (IOException e) {
          System.out.println("\nAn error occurred while writting to file!");
          e.printStackTrace();
        }
      while(verifyInput){
        boolean isDigit = true;
        System.out.println("\n" + "Would you like to change any of the following? Please select an option.\n" 
                                      + "1 - Origin Airport\n" + "2 - Origin Code\n" + "3 - Destination Airport\n" + "4 - Destination Code\n"
                                      + "5 - Departure Date\n" + "6 - Departure Time\n" + "7 - First Class Price\n" + "8 - Buisness Class Price\n"
                                      + "9 - Main Cabin Price\n" + "10 - View customers on this flight and flight revenue\n"
                                      + "11 - cancel current flight\n" + "12 - Make no changes\n");
          //changeInfo = userInput.nextLine();
          while(verifyInput){
            System.out.print("Enter option below: ");
            changeInfo = userInput.nextLine();
            try {
                int option = Integer.parseInt(changeInfo);
                if (option < 1 || option > 12) {
                    System.out.println("\n* That was not an option please try again *");
                } else {
                    verifyInput = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("\n* That was not an option please try again *");
            }
          }

          verifyInput = true;
          switch(Integer.parseInt(changeInfo)){
              case 1: //Origin Airport change
                  System.out.println("\nWhat is the new Origin Airport:");
                  System.out.print("Enter new airport: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setOriginAirport(newChange);
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated origin airport to " + currFlightObj.getOriginAirport() +"\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
                  
                  break;
              case 2: //Origin Code change
                  System.out.println("\nWhat is the new Origin Code:");
                  System.out.print("Enter new code: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setOriginCode(newChange);
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated origin code to " + currFlightObj.getOriginCode() + "\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 3: //Destination Airport change
                  System.out.println("\nWhat is the new Destination Airport:");
                  System.out.print("Enter new airport: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setDestinationAirport(newChange);
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated destination airport to " + currFlightObj.getDestinationAirport() + "\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 4: //Destination Code change
                  System.out.println("\nWhat is the new Destination Code:");
                  System.out.print("Enter new code: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setDestinationCode(newChange);
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated destination code to " + currFlightObj.getDestinationCode() + "\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 5: //Departure Date change
                  System.out.println("\nWhat is the new Departure Date in MM/DD/YYYY:");
                  System.out.print("Enter new date: ");
                  newChange = userInput.nextLine();
                  currFlightObj.changeDepartureDate(newChange);; //CHANGE ARRIVAL TIME
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated departure date to " + currFlightObj.getDepartureDate() + "\n");
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated arrival date to " + currFlightObj.getArrivalDate() + "\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 6: //Departure Time change
                  System.out.println("\nWhat is the new Departure Time in HH:MM (military time):");
                  System.out.print("Enter new time: ");
                  newChange = userInput.nextLine();
                  currFlightObj.changeDepartureTime(newChange);//CHANGE ARRIVAL TIME
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated departure time to " + currFlightObj.getDepartureTime() +", new arrival time is "+ currFlightObj.getArrivalTime() +"\n"); //CHANGE
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 7: //First Class Price change
                  System.out.println("\nWhat is the new First Class Price:");
                  System.out.print("Enter new price: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setFirstClassPrice(Integer.parseInt(newChange));
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated first class price to " + currFlightObj.getFirstClassPrice() +"\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 8: //Business Class Price change
                  System.out.println("\nWhat is the new Business Class Price:");
                  System.out.print("Enter new price: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setBusinessClassPrice(Integer.parseInt(newChange));
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated business class price to " + currFlightObj.getBusinessClassPrice() + "\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 9: //Main Cabin Price change
                  System.out.println("\nWhat is the new Main Class Price:");
                  System.out.print("Enter new price: ");
                  newChange = userInput.nextLine();
                  currFlightObj.setMainCabinPrice(Integer.parseInt(newChange));
                  System.out.println("\nChange was succesful! here is the new information.\n");
                  currFlightObj.printFlight();
                  try {
                      myWriter.write("Flight ID: " + currFlightObj.getID() + " updated main cabin price to " + currFlightObj.getMainCabinPrice() +"\n");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
  
                  break;
              case 10: //View customers on this flight
                  boolean customerTicketFound = false;
                  int routeCost = currFlightObj.getRouteCost();
                  int currProfit = 0;
                  int totalProfit = 0;
                  int tickestBoughtIdx = 0;
                  int totalSeatsSold = 0;
                  int firstClassSeatsSold = 0;
                  int businessClassSeatsSold = 0;
                  int mainCabinClassSeatsSold = 0;
                  for(int i = 0; i < ticketsBought.size(); i++){
                    if(currFlightObj.getID() == ticketsBought.get(i).getID()){
                      customerTicketFound = true;
                      tickestBoughtIdx = i;
                      if(customerTicketFound){
                        System.out.println("\n" + ticketsBought.get(tickestBoughtIdx).getTicketFirstName() + " " + ticketsBought.get(tickestBoughtIdx).getTickeLastName());
                        System.out.println(ticketsBought.get(tickestBoughtIdx).getClassType() + ": " + ticketsBought.get(tickestBoughtIdx).getNumberOfSeats() + " seats");
                        System.out.println("Total Price: $" +ticketsBought.get(tickestBoughtIdx).getClassCost());
    
                        if(ticketsBought.get(tickestBoughtIdx).getClassType().equalsIgnoreCase("First Class")){
                          firstClassSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          totalSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          currProfit += ticketsBought.get(tickestBoughtIdx).getClassCost();
                        }
                        else if(ticketsBought.get(tickestBoughtIdx).getClassType().equalsIgnoreCase("Business Class")){
                          businessClassSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          totalSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          currProfit += ticketsBought.get(tickestBoughtIdx).getClassCost();
                        }
                        else{
                          mainCabinClassSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          totalSeatsSold += ticketsBought.get(tickestBoughtIdx).getNumberOfSeats();
                          currProfit += ticketsBought.get(tickestBoughtIdx).getClassCost();
                        }
    
                      }
                      //customerTicketFound = false; //reset
                    }
                  }
                  totalProfit = currProfit - routeCost;
                  if(!customerTicketFound){
                    System.out.println("\nNo one has bought any tickets for this flight yet.");
                    System.out.println("\nFirst Class seats sold: 0");
                    System.out.println("\nBusiness Class seats sold: 0");
                    System.out.println("\nMain Cabin Class seats sold: 0");
                    System.out.println("\n\nCurrent Total profit: $" + totalProfit);
                    
                  }
                  else{
                    System.out.println("\nNumber of seats sold: " + totalSeatsSold);
                    System.out.println("\nFirst Class seats sold: " + firstClassSeatsSold);
                    System.out.println("\nBusiness Class seats sold: " + businessClassSeatsSold);
                    System.out.println("\nMain Cabin Class seats sold: " + mainCabinClassSeatsSold);
                    System.out.println("\nCurrent Total profit: $" + totalProfit);
                  }
                  try {
                    myWriter.write("Employer view customer list and revenue for flight ID: " + currFlightObj.getID());
                    myWriter.write("\n");
                  } catch (IOException e) {
                    System.out.println("\nAn error occurred while writting to file!");
                    e.printStackTrace();
                  }

                  break;
              case 11: //Cancel current flight
                  System.out.println("\n* WARNING * by canceling this flight you understand that:\n"
                   + "\n1) All ticket sales will be returned to the customers.\n" + "2) This flight will no longer be available to sell tickets.\n"
                   + "\nPlease enter Y indicating that you understand these conditions, or N to not cancel this flight.");
                   while (cancelFlightInput){
                    System.out.print("Enter option: ");
                    String cancelTicket = userInput.nextLine();
                    if(cancelTicket.equalsIgnoreCase("Y")){
                      try {
                        csvWriter.write(currFlightObj.getID() + "," + "CANCELED");
                        csvWriter.write("\n");
                      } catch (IOException e) {
                        System.out.println("\nAn error occurred while writting to file!");
                        e.printStackTrace();
                      }
                      boolean foundTicket = false;
                      int i = 0;
                      tickestBoughtIdx = 0;

                      while(i < ticketsBought.size()){ //Generates information about the customer
                          if(currFlightObj.getID() == ticketsBought.get(i).getID()){
                            foundTicket = true;
                            if(foundTicket){
                              float totalTicketCostReturn = ticketsBought.get(tickestBoughtIdx).getClassCost(); //100% refund
                              String customerFirstNmae = ticketsBought.get(tickestBoughtIdx).getTicketFirstName();
                              String customerLastName = ticketsBought.get(tickestBoughtIdx).getTickeLastName();
                              ticketRefund(customerLog, customerFirstNmae, customerLastName, totalTicketCostReturn);
                              ticketsBought.get(tickestBoughtIdx).setStatus("CANCELED");
                              System.out.println("\nSystem has refunded $" + totalTicketCostReturn + " to " + customerFirstNmae + " " + customerLastName + " and status of the tickets was changed to CANCELED.");
                              try {
                                myWriter.write("System has refunded $" + totalTicketCostReturn + " to " + customerFirstNmae + " " + customerLastName + " and status of the tickets was changed to CANCELED.");
                                myWriter.write("\n");
                              } catch (IOException e) {
                                System.out.println("\nAn error occurred while writting to file!");
                                e.printStackTrace();
                              }
                            }
                            else{
                              System.out.println("\nNo one has bought a ticket for this flight, therefore no money was returned.");
                            }
                          }
                          i++;
                          tickestBoughtIdx++;
                      }
                      cancelFlightInput = false;
                    }
                    else if (cancelTicket.equalsIgnoreCase("N")){
                      System.out.println("\nFlight ID: " + currFlightObj.getID() + " was NOT canceled.");
                      try {
                        myWriter.write("Flight ID: " + currFlightObj.getID() + " was NOT canceled.");
                        myWriter.write("\n");
                      } catch (IOException e) {
                        System.out.println("\nAn error occurred while writting to file!");
                        e.printStackTrace();
                      }
                      cancelFlightInput = false;
                    }
                    else {
                      System.out.println("\nThat was not an option please try again.");
                    }
                  }

                  break;
              case 12: //No change
                  System.out.println("\nNo changes were made.");
                  try {
                      myWriter.write("No changes were made");
                      myWriter.write("\n");
                    } catch (IOException e) {
                      System.out.println("\nAn error occurred while writting to file!");
                      e.printStackTrace();
                    }
                  break;
              default:
                  System.out.print("Invalid input, please select a proper option!\n");
                  
          }

          System.out.println("\nWould you like to make another change on Flight ID " +currFlightObj.getID() + "? [Y,N]");
          System.out.print("Enter option: ");
          question = userInput.nextLine(); // flush
          if(question.equalsIgnoreCase("y")){
              System.out.println("Here is the menu again.\n");
          }
          else{
              verifyInput = false;
          }

      }
      
  }
    /**
     * 
     * @param fileName This variable will have the String name of the log file that you want the scanner to read and print the changes what were recorded.
     * @throws IOException This exception is used for when using the scanner object to read information.
     */
    public static void printLog(String fileName)throws IOException{ //Prints the log of all the changes what were recorded
      Scanner logReader = new Scanner(new File(fileName));
      String currLine;
      System.out.println();
      while(logReader.hasNext()){
          currLine = logReader.nextLine();
          System.out.println(currLine);

      }
  }
    /**
     * 
     * @param flightSchedule This variable will have the String name of the file where all of the flight's information is stored.
     * @return This method returns a HashMap containing the I.D. number of the flight along with the flight object. The flight object will store all needed information while the I.D. is used to locate the flight object within the HashMap.
     * @throws IOException This exception is used for when using the scanner object to read information.
     */
    public static HashMap<Integer,Flight> flights(String flightSchedule) throws IOException{
        Scanner fileReader = new Scanner(new File(flightSchedule));

        HashMap<Integer,Flight> flightLog = new HashMap<Integer,Flight>();
        String [] token; // Used to split information
        String currLine;

        String[] header = fileReader.nextLine().split(",");
        int typeIndex = -1 ,arrivalDateIndex = -1 ,mainCabinSeatsIndex = -1 ,flightNumberIndex = -1, destinationCodeIndex = -1, originAirportIndex = -1, minerPointsIndex = -1, originCodeIndex = -1, businessClassPriceIndex = -1, mainCabinPriceIndex = -1, departingTimeIndex = -1, durationIndex = -1,
        surchargeIndex = -1, originAirportLoungeIndex = -1, distanceIndex = -1, timeZoneDifferenceIndex = -1, arrivalTimeIndex = -1, departingDateIndex = -1, originAirportStateIndex = -1, originAirportCountryIndex = -1, originAirportFeeIndex = -1, destinationAirportCityIndex = -1,
        foodServedIndex = -1, destinationAirportStateIndex = -1, destinationAirportCountryIndex = -1, destinationAirportFeeIndex = -1, destinationAirportLoungeIndex = -1, routeCostIndex = -1, totalSeatsIndex = -1, IDIndex = -1, firstClassSeatsIndex = -1, destinationAirportIndex = -1,
        businessClassSeatsIndex = -1, originAirportCityIndex = -1, firstClassPriceIndex = -1;

        // Search for header values and store their positions
      for (int i = 0; i < header.length; i++) {
        String headerValue = header[i].trim();
        if (headerValue.equalsIgnoreCase("Type")) {
            typeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Arrival Date")) {
            arrivalDateIndex = i;
        } else if (headerValue.equalsIgnoreCase("Main Cabin Seats")) {
            mainCabinSeatsIndex = i;
        } else if (headerValue.equalsIgnoreCase("Flight Number")) {
            flightNumberIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Code")) {
            destinationCodeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport")) {
            originAirportIndex = i;
        } else if (headerValue.equalsIgnoreCase("Miner Points")) {
            minerPointsIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Code")) {
            originCodeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Business Class Price")) {
            businessClassPriceIndex = i;
        } else if (headerValue.equalsIgnoreCase("Main Cabin Price")) {
            mainCabinPriceIndex = i;
        } else if (headerValue.equalsIgnoreCase("Departing Time")) {
            departingTimeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Duration")) {
            durationIndex = i;
        } else if (headerValue.equalsIgnoreCase("Surcharge")) {
            surchargeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport Lounge")) {
            originAirportLoungeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Distance")) {
            distanceIndex = i;
        } else if (headerValue.equalsIgnoreCase("Time Zone Difference")) {
            timeZoneDifferenceIndex = i;
        } else if (headerValue.equalsIgnoreCase("Arrival Time")) {
            arrivalTimeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Departing Date")) {
            departingDateIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport State")) {
            originAirportStateIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport Country")) {
          originAirportCountryIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport Fee")) {
            originAirportFeeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Code")) {
            originCodeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport City")) {
            destinationAirportCityIndex = i;
        } else if (headerValue.equalsIgnoreCase("Food Served")) {
            foodServedIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport State")) {
            destinationAirportStateIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport Country")) {
            destinationAirportCountryIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport Fee")) {
            destinationAirportFeeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport Lounge")) {
            destinationAirportLoungeIndex = i;
        } else if (headerValue.equalsIgnoreCase("Route Cost")) {
            routeCostIndex = i;
        } else if (headerValue.equalsIgnoreCase("Total Seats")) {
            totalSeatsIndex = i;
        } else if (headerValue.equalsIgnoreCase("ID")) {
            IDIndex = i;
        } else if (headerValue.equalsIgnoreCase("First Class Seats")) {
            firstClassSeatsIndex = i;
        } else if (headerValue.equalsIgnoreCase("Destination Airport")) {
            destinationAirportIndex = i;
        } else if (headerValue.equalsIgnoreCase("Origin Airport City")) {
            originAirportCityIndex = i;
        } else if (headerValue.equalsIgnoreCase("Business Class Seats")) {
            businessClassSeatsIndex = i;
        } else if (headerValue.equalsIgnoreCase("First Class Price")) {
            firstClassPriceIndex = i;
        }
      }

      //Arrival Date	Destination Airport State	Origin Code	Destination Code	Departing Date	First Class Price	Departing Time	Arrival Time	Duration	ID	Origin Airport City	Origin Airport Country	Origin Airport Fee	Time Zone Difference	Destination Airport City	Distance	Destination Airport Country	Destination Airport Fee	Destination Airport Lounge	Origin Airport	Type	Main Cabin Price	Flight Number	Business Class Seats	Surcharge	Food Served	Route Cost	Origin Airport State	Miner Points	Total Seats	First Class Seats	Destination Airport	Main Cabin Seats	Origin Airport Lounge	Business Class Price
      while(fileReader.hasNext()){ //Generates all information for each flight
          currLine = fileReader.nextLine(); //gets next line
          token = currLine.split(",");

          // Extract the required information based on the header positions
          int ID = IDIndex != -1 ? Integer.parseInt(token[IDIndex]) : 0;
          String flightNum = flightNumberIndex != -1 ? token[flightNumberIndex] : "";
          String originAirport = originAirportIndex != -1 ? token[originAirportIndex] : "";
          String originCode = originCodeIndex != -1 ? token[originCodeIndex] : "";
          String destinationAirport = destinationAirportIndex != -1 ? token[destinationAirportIndex] : "";
          String destinationCode = destinationCodeIndex != -1 ? token[destinationCodeIndex] : "";
          String departureDate = departingDateIndex != -1 ? token[departingDateIndex] : "";
          String departureTime = departingTimeIndex != -1 ? token[departingTimeIndex] : "";
          String arrivalDate = arrivalDateIndex != -1 ? token[arrivalDateIndex] : "";
          String arrivalTime = arrivalTimeIndex != -1 ? token[arrivalTimeIndex] : "";
          String flightType = typeIndex != -1 ? token[typeIndex] : "";
          int surcharge = surchargeIndex != -1 ? Integer.parseInt(token[surchargeIndex]) : 0;
          boolean foodServed = foodServedIndex != -1 ? Boolean.parseBoolean(token[foodServedIndex]) : false;
          boolean destinationAirportLounge = destinationAirportLoungeIndex != -1 ? Boolean.parseBoolean(token[destinationAirportLoungeIndex]) : false;
          boolean originAirportLounge = originAirportLoungeIndex != -1 ? Boolean.parseBoolean(token[originAirportLoungeIndex]) : false;
          int routeCost = routeCostIndex != -1 ? Integer.parseInt(token[routeCostIndex]) : 0;  
          int minerPoints = minerPointsIndex != -1 ? Integer.parseInt(token[minerPointsIndex]) : 0;
          int duration = durationIndex != -1 ? Integer.parseInt(token[durationIndex]) : 0;
          int distance = distanceIndex != -1 ? Integer.parseInt(token[distanceIndex]) : 0;
          int timeZoneDifference = timeZoneDifferenceIndex != -1 ? Integer.parseInt(token[timeZoneDifferenceIndex]) : 0;
          int firstClassPrice = firstClassPriceIndex != -1 ? Integer.parseInt(token[firstClassPriceIndex]) : 0;
          int businessClassPrice = businessClassPriceIndex != -1 ? Integer.parseInt(token[businessClassPriceIndex]) : 0;
          int mainCabinPrice = mainCabinPriceIndex != -1 ? Integer.parseInt(token[mainCabinPriceIndex]) : 0;
          int firstClassSeats = firstClassSeatsIndex != -1 ? Integer.parseInt(token[firstClassSeatsIndex]) : 0;
          int businessClassSeats = businessClassSeatsIndex != -1 ? Integer.parseInt(token[businessClassSeatsIndex]) : 0;
          int mainCabinSeats = mainCabinSeatsIndex != -1 ? Integer.parseInt(token[mainCabinSeatsIndex]) : 0;
          int totalSeats = totalSeatsIndex != -1 ? Integer.parseInt(token[totalSeatsIndex]) : 0;
          String destinationAirportState = destinationAirportStateIndex != -1 ? token[destinationAirportStateIndex] : "";
          String destinationAirportCity = destinationAirportCityIndex != -1 ? token[destinationAirportCityIndex] : "";
          String destinationAirportCountry = destinationAirportCountryIndex != -1 ? token[destinationAirportCountryIndex] : "";
          String originAirportCity = originAirportCityIndex != -1 ? token[originAirportCityIndex] : "";
          String originAirportCountry = originAirportCountryIndex != -1 ? token[originAirportCountryIndex] : "";
          String originAirportState = originAirportStateIndex != -1 ? token[originAirportStateIndex] : ""; 
          float orginAirportFee = originAirportFeeIndex != -1 ? Float.parseFloat(token[originAirportFeeIndex]) : 0.0f;
          float destinationAirportFee = destinationAirportFeeIndex != -1 ? Float.parseFloat(token[destinationAirportFeeIndex]) : 0.0f;

          flightLog.put(ID, new Flight(arrivalDate, destinationAirportState, originCode, destinationCode, departureDate, firstClassPrice, departureTime, arrivalTime, duration, ID,
          originAirportCity, originAirportCountry, orginAirportFee, timeZoneDifference, destinationAirportCity, distance, destinationAirportCountry, destinationAirportFee, destinationAirportLounge,
          originAirport, flightType, mainCabinPrice, flightNum, businessClassSeats, surcharge, foodServed, routeCost, originAirportState, minerPoints, totalSeats, firstClassSeats, destinationAirport,
          mainCabinSeats, originAirportLounge, businessClassPrice));

      }

      return flightLog;

    }
    /**
     * 
     * @param customerList This variable will have the String name of the file where all of the customer's information is stored.
     * @return This method returns a HashMap containing the I.D. number of the customer along with its object. The customer object will store all needed information while the I.D. is used to locate the customer object within the HashMap.
     * @throws IOException This exception is used for when using the scanner object to read information.
     */
    public static HashMap<Integer,Customer> customers(String customerList) throws IOException{
      Scanner fileReader = new Scanner(new File(customerList));
  
      HashMap<Integer,Customer> customerLog = new HashMap<Integer,Customer>();
      String [] token; // Used to split information
      String currLine;
  
      String[] header = fileReader.nextLine().split(",");
      int dobIndex = -1, usernameIndex = -1, moneyIndex = -1, lastNameIndex = -1, passwordIndex = -1, roleIndex = -1, idIndex = -1, firstNameIndex = -1, flightsIndex = -1, membershipIndex = -1;
  
      // Search for header values and store their positions
      for (int i = 0; i < header.length; i++) {
          String headerValue = header[i].trim();
          if (headerValue.equalsIgnoreCase("DOB")) {
              dobIndex = i;
          } else if (headerValue.equalsIgnoreCase("Username")) {
              usernameIndex = i;
          } else if (headerValue.equalsIgnoreCase("Money Available")) {
              moneyIndex = i;
          } else if (headerValue.equalsIgnoreCase("Last Name")) {
              lastNameIndex = i;
          } else if (headerValue.equalsIgnoreCase("Password")) {
              passwordIndex = i;
          } else if (headerValue.equalsIgnoreCase("Role")) {
              roleIndex = i;
          } else if (headerValue.equalsIgnoreCase("ID")) {
              idIndex = i;
          } else if (headerValue.equalsIgnoreCase("First Name")) {
              firstNameIndex = i;
          } else if (headerValue.equalsIgnoreCase("Flights Purchased")) {
              flightsIndex = i;
          } else if (headerValue.equalsIgnoreCase("MinerAirlines Membership")) {
              membershipIndex = i;
          }
      }
  
      while(fileReader.hasNext()){ //Generates information about the customer
          currLine = fileReader.nextLine(); //gets next line
          token = currLine.split(",");
  
          // Extract the required information based on the header positions
          String dob = dobIndex != -1 ? token[dobIndex] : "";
          String username = usernameIndex != -1 ? token[usernameIndex] : "";
          float money = moneyIndex != -1 ? Float.parseFloat(token[moneyIndex]) : 0.0f;
          String lastName = lastNameIndex != -1 ? token[lastNameIndex] : "";
          String password = passwordIndex != -1 ? token[passwordIndex] : "";
          String role = roleIndex != -1 ? token[roleIndex] : "";
          int id = idIndex != -1 ? Integer.parseInt(token[idIndex]) : 0;
          String firstName = firstNameIndex != -1 ? token[firstNameIndex] : "";
          int flights = flightsIndex != -1 ? Integer.parseInt(token[flightsIndex]) : 0;
          boolean membership = membershipIndex != -1 ? Boolean.parseBoolean(token[membershipIndex]) : false;
  
          customerLog.put(id, new Customer(dob, username, money, lastName, password, role, id, firstName, flights, membership));
      }
  
      return customerLog;
  }
  
    /**
     * 
     * @param customerLog This variable will contain the HashMap that was created when using the customers() method. 
     * @param firstName This variable will have the String first name of the current customer.
     * @param lastName This variable will have the String last name of the current customer.
     * @return This method will return true or false depending if the given first and last name are within the HashMap to confirm that the current customer is in our system.
     */
    public static boolean isInCustomerList(HashMap<Integer,Customer> customerLog, String firstName, String lastName){ //This method tells us if the customer is in the list
        boolean isInlist = false;

        int i = 1;
        while(i < customerLog.size()){
            if(customerLog.get(i).getFirstName().equalsIgnoreCase(firstName) && customerLog.get(i).getLastName().equalsIgnoreCase(lastName)){
                isInlist = true;
            }
            i++;
        }
        return isInlist;
    }
    /**
     * 
     * @param customerLog This variable will contain the HashMap that was created when using the customers() method. 
     * @param firstName This variable will have the String first name of the current customer.
     * @param lastName This variable will have the String last name of the current customer.
     * @return This method will return the current customer object.
     */
    public static Customer findCustomerObj(HashMap<Integer,Customer> customerLog, String firstName, String lastName){
      int i = 1;

      Customer currCustomer = new Customer();

      while(i < customerLog.size()){
        if(customerLog.get(i).getFirstName().equalsIgnoreCase(firstName) && customerLog.get(i).getLastName().equalsIgnoreCase(lastName)){
          currCustomer = customerLog.get(i);
        }
        i++;
      }

      return currCustomer;
    }
    /**
     * 
     * @param flightLog This variable will have the HashMap that was created when using the flights() method. The Method will print the flight's information.
     */
    public static void printFlights(HashMap<Integer,Flight> flightLog){
      for(int i = 1; i < flightLog.size() + 1; i++){
        System.out.println();
        flightLog.get(i).printFlight();
      }
    }
    /**
     * 
     * @param currFlight This variable will have the current flight object that you pass on.
     * @param currCustomer This variable will have the current customer object that you pass on.
     * @param option This integer variable will be used for the inner switch case that is inside of the method.
     * @param logfile This variable will have the String name of the file where all of the logs are being recorded.
     * @param myWriter In this variable you will pass on the file writter object, this will allow you to only use one file writter instead of creating one per method, all writting will go to the Log.txt file.
     * @param csvFileWriter In this variable you will pass on the file writter object, all writting from this object will not get overriden when the system terminates.
     * @param csvFileName This variable will have the name of the file for the customer's history purchase log, this file cannot be overriden.
     * @param userInput In this variable you will pass on the scanner object for all of the incoming user inputs.
     * @param tickestBought You will need to pass on an array list containing all on the tickets that the current user has bought.
     * @param role You will need to pass on the String role name of the current customer, this will let the system know when to apply the employee discounts.
     * @throws IOException This exception is used for when using the scanner object to read information.
     */
    public static void customerPurchaseOptions(Flight currFlight, Customer currCustomer, int option, String logfile, FileWriter myWriter,FileWriter csvFileWriter, String csvFileName, Scanner userInput, ArrayList<Ticket> tickestBought, String role) throws IOException{ //Finish method
      int ticketsPurchase = 0;
      float ticketsPurchasePrice = 0; //The amount of money per ticket
      int surchargePerSeat = 0;
      int totalSeats = currFlight.getTotalSeats();
      int availableSeats = 0;
      long confirmationTicketNum = 0;
      int firstClassRevenue = 0;
      int businessClassRevenue = 0;
      int mainCabinClassRevenue = 0;
      double FIRSTCLASS_EMPLOYEE_DISCOUNT = 0.50; //Constant
      double EMPLOYEE_DISCOUNT = 0.75;            //Constant
      double MINERAIRLINES_FEE = 9.15;            //Constant
      double SECURITY_FEE = 5.60;                 //Constant
      double MEMBERSHIP_DISCOUNT = 0.05;          //Constant
      double TEXAS_SALES_TAX = 0.0825;            //Constant
      double securityFee = 0.0;
      double salesTax = 0.0;
      double membershipSaving = 0.0;
      double membershipTotalSaving = 0.0;
      double discountPrice = 0.0;

      boolean validInput = true;
      boolean checkTickets = true;

      Ticket firstClassTicketCt = new Ticket(8,0,0); //Tickets allowed for first class
      Ticket businessClassTicketCt = new Ticket(8,0,0); //Tickets allowed for business class
      Ticket mainCabinTicketCt = new Ticket(8,0,0); //Tickets allowed for main cabin 

      ArrayList<Ticket> totalTickets = new ArrayList<Ticket>();

      try {
        myWriter.write("Changes made while purchesing a ticket: \n");
      }catch (IOException e) {
        System.out.println("\nAn error occurred while writting to file!");
        e.printStackTrace();
      }
      switch(option){
        case 1:
        availableSeats = currFlight.getFirstClassSeats();
        if(currFlight.isInternational()){         //Check if this flight has a surcharge
          System.out.println("\n* The flight that you have choosen has an international destination, there will be an extra $" + currFlight.getSurcharge() +
          " PER SEAT that you purchase *");
        }
        if(currCustomer.getMinerAirMembership()){
          System.out.println("\n* Since you are a MinerAirlines Member you will receive a 5% discount on the base price of the ticket *");
        }
        System.out.println("\n* MinerAirlines has a $" + MINERAIRLINES_FEE + " fee PER transaction, in addition we charge an extra $" +SECURITY_FEE + " security fee PER ticket *");
        System.out.println("\nHow many seats would you like to buy?" + "\nRemember that you are only allowed to buy 8 tickets per class." + 
           "\nThere are " + availableSeats + " seats available for First Class.");
        while(validInput){ //Chesk is customer has enough funds
          
          while(checkTickets){ //Check is the number of tickets does not exceed 6
            System.out.print("\nNumber of tickets: ");
            ticketsPurchase = Integer.parseInt(userInput.nextLine()); //check heeeerrrr
            if (ticketsPurchase == 0){
              System.out.println("\nNo tickets were bought.");
              break;
            }
            if(firstClassTicketCt.checkNumOfTickets(ticketsPurchase)){ // checks the amount of tickets entered
              checkTickets = false;
            }
            else{
              System.out.println("\nYou can only buy 8 seats tickets per class!");
            }
          }
          if(role.equalsIgnoreCase("Employee") && currCustomer.getMinerAirMembership()){
            discountPrice += currFlight.getFirstClassPrice() - (currFlight.getFirstClassPrice() * (FIRSTCLASS_EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            membershipSaving += currFlight.getFirstClassPrice() * FIRSTCLASS_EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getFirstClassPrice() * (FIRSTCLASS_EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(role.equalsIgnoreCase("Employee")){
            discountPrice += currFlight.getFirstClassPrice() - (currFlight.getFirstClassPrice() * FIRSTCLASS_EMPLOYEE_DISCOUNT);
            membershipSaving += currFlight.getFirstClassPrice() * FIRSTCLASS_EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getFirstClassPrice() * FIRSTCLASS_EMPLOYEE_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(currCustomer.getMinerAirMembership()){ //Apply discount if member
            discountPrice += currFlight.getFirstClassPrice() - (currFlight.getFirstClassPrice() * MEMBERSHIP_DISCOUNT);
            membershipSaving += currFlight.getFirstClassPrice() * MEMBERSHIP_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getFirstClassPrice() * MEMBERSHIP_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else{
            ticketsPurchasePrice += ticketsPurchase * currFlight.getFirstClassPrice();
          }

          salesTax += (ticketsPurchasePrice * TEXAS_SALES_TAX); //Taxes before all discounts and fees
          securityFee += ticketsPurchase * SECURITY_FEE; // $5.60 security fee per ticket
          ticketsPurchasePrice += securityFee;
          ticketsPurchasePrice += MINERAIRLINES_FEE;       // $9.15 per ticket transaction
          ticketsPurchasePrice += currFlight.getOrginAirportFee();        //Orgin airport fee
          ticketsPurchasePrice += currFlight.getDestinationAirportFee();  //Destintion airport fee
          surchargePerSeat += ticketsPurchase * currFlight.getSurcharge();

          if (currFlight.isInternational()){           //Add surcharge is it is an international flight
            ticketsPurchasePrice += surchargePerSeat;
          }

          ticketsPurchasePrice += salesTax;

          if(checkBalance(currCustomer, ticketsPurchasePrice)){
            System.out.print("\nTransaction successful! Your new balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print("\nHere are your " + ticketsPurchase + " ticket(s) with a total cost of $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");

            currCustomer.setFlightsPurchased(ticketsPurchase);
            firstClassRevenue += ticketsPurchasePrice - (ticketsPurchasePrice * MEMBERSHIP_DISCOUNT)  - salesTax - securityFee;
            currFlight.setFirstClassRevenue(firstClassRevenue);

            if (currFlight.isInternational()){
              System.out.println("\n\nHere is the total surcharge that was added to your bill: $" + surchargePerSeat);
            }

            confirmationTicketNum = confirmationTicketGenerator();

            System.out.println("\nHere is your confirmation number: " + confirmationTicketNum);

            firstClassTicketCt.setNumOfTicketsPurchased(ticketsPurchase); //add ticket purchesed


            validInput = false;
          }else{
            System.out.print("\n* Insufficient funds, your current balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print(" * \nPlease try again.\n");
            System.out.print("\nYour estimated total cost is $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");
            System.out.println("\n\nIf you are out of money or do not have enough funds please enter 0.");
            ticketsPurchase = 0;
            ticketsPurchasePrice = 0;
            validInput = true;
            checkTickets = true;
          }

        }
        availableSeats = availableSeats - ticketsPurchase;
        currFlight.setFirstClassSeats(availableSeats);
        currFlight.setFirstClassSeatsSold(ticketsPurchase);

        currFlight.setTotalSeats(totalSeats - currFlight.getFirstClassSeatsSold());

        firstClassTicketCt.setNumberOfSeats(ticketsPurchase);
        firstClassTicketCt.setComfirmationNum(confirmationTicketNum);
        totalTickets.add(firstClassTicketCt);
        tickestBought.add(new Ticket(currFlight.getID(), currFlight.getFlightNum(), currFlight.getOriginAirport(),currFlight.getDestinationAirport(), currFlight.getDepartureDate(),
         currFlight.getDepartureTime(), ticketsPurchase, ticketsPurchasePrice, "First Class", "ACTIVE", confirmationTicketNum, currCustomer.getFirstName(), currCustomer.getLastName()));

        currFlight.listOfPurchasedTickets(firstClassTicketCt);

        try {
          DecimalFormat df = new DecimalFormat("0.00");
          myWriter.write("User (" + currCustomer.getUsername() +") purchased a first class ticket(s) for Flight ID: " + currFlight.getID());
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") bought " + firstClassTicketCt.getNumOfTicketsPurchased() + " tickets.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + MINERAIRLINES_FEE + " for the transaction fee.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + df.format(securityFee) + " in security fee(s).");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") saved a total of $" + df.format(membershipTotalSaving));
          myWriter.write("\nThe ticket cost is $"  + currFlight.getFirstClassPrice() + " per seat.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " has a surcharge of $" + currFlight.getSurcharge());
          myWriter.write("\nFirst class now has " + currFlight.getFirstClassSeats() + " seats available.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " now has " + currFlight.getTotalSeats() + " total seats.");
          myWriter.write("\nComfitmation number is: " + confirmationTicketNum + "\n");
          myWriter.write("\n");
        } catch (IOException e) {
          System.out.println("\nAn error occurred while writting to file!");
          e.printStackTrace();
        }
        Ticket ticketCSVFile = new Ticket(currFlight.getID(),currCustomer.getFirstName(),currCustomer.getLastName(),"First Class",firstClassTicketCt.getNumberOfSeats(),ticketsPurchasePrice);
        ticketHistoryCSVFileWriter(csvFileWriter,ticketCSVFile);

        checkTickets = true;
        
        break;
        /************************************************************************************************************************************/
        case 2:
        availableSeats = currFlight.getBusinessClassSeats();
        if(currFlight.isInternational()){         //Check if this flight has a surcharge
          System.out.println("\n* The flight that you have choosen has an international destination, there will be an extra $" + currFlight.getSurcharge() +
          " PER SEAT that you purchase *");
        }
        if(currCustomer.getMinerAirMembership()){
          System.out.println("\n* Since you are a MinerAirlines Member you will receive a 5% discount on the base price of the ticket *");
        }
        System.out.println("\n* MinerAirlines has a $" + MINERAIRLINES_FEE + " fee PER transaction, in addition we charge an extra $" +SECURITY_FEE + " security fee PER ticket *");
        System.out.println("\nHow many seats would you like to buy?" + "\nRemember that you are only allowed to buy 8 tickets per class." + 
           "\nThere are " + availableSeats + " seats available for Business Class.");
        while(validInput){ //Chesk is customer has enough funds
          
          while(checkTickets){ //Check is the number of tickets does not exceed 6
            System.out.print("\nNumber of tickets: ");
            ticketsPurchase = Integer.parseInt(userInput.nextLine()); //check heeeerrrr

            if(firstClassTicketCt.checkNumOfTickets(ticketsPurchase)){ // checks the amount of tickets entered
              checkTickets = false;
            }
            else{
              System.out.println("\nYou can only buy 8 seats tickets per class!");
            }
          }

          if(role.equalsIgnoreCase("Employee") && currCustomer.getMinerAirMembership()){
            discountPrice += currFlight.getBusinessClassPrice() - (currFlight.getBusinessClassPrice() * (EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            membershipSaving += currFlight.getBusinessClassPrice() * EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getBusinessClassPrice() * (EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(role.equalsIgnoreCase("Employee")){
            discountPrice += currFlight.getBusinessClassPrice() - (currFlight.getBusinessClassPrice() * EMPLOYEE_DISCOUNT);
            membershipSaving += currFlight.getBusinessClassPrice() * EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getBusinessClassPrice() * EMPLOYEE_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(currCustomer.getMinerAirMembership()){ //Apply discount if member
            discountPrice += currFlight.getBusinessClassPrice() - (currFlight.getBusinessClassPrice() * MEMBERSHIP_DISCOUNT);
            membershipSaving += currFlight.getBusinessClassPrice() * MEMBERSHIP_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getBusinessClassPrice() * MEMBERSHIP_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else{
            ticketsPurchasePrice += ticketsPurchase * currFlight.getBusinessClassPrice();
          }

          salesTax += (ticketsPurchasePrice * TEXAS_SALES_TAX); //Taxes before all discounts and fees
          securityFee += ticketsPurchase * SECURITY_FEE; // $5.60 security fee per ticket
          ticketsPurchasePrice += securityFee;
          ticketsPurchasePrice += MINERAIRLINES_FEE;       // $9.15 per ticket transaction
          ticketsPurchasePrice += currFlight.getOrginAirportFee();        //Orgin airport fee
          ticketsPurchasePrice += currFlight.getDestinationAirportFee();  //Destintion airport fee
          surchargePerSeat += ticketsPurchase * currFlight.getSurcharge();

          if (currFlight.isInternational()){           //Add surcharge is it is an international flight
            ticketsPurchasePrice += surchargePerSeat;
          }

          ticketsPurchasePrice += salesTax;

          if(checkBalance(currCustomer, ticketsPurchasePrice)){
            System.out.print("\nTransaction successful! Your new balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print("\nHere are your " + ticketsPurchase + " ticket(s) with a total cost of $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");

            currCustomer.setFlightsPurchased(ticketsPurchase);
            businessClassRevenue += ticketsPurchasePrice - (ticketsPurchasePrice * MEMBERSHIP_DISCOUNT)  - salesTax - securityFee;
            currFlight.setBusinessClassRevenue(businessClassRevenue);

            if (currFlight.isInternational()){
              System.out.println("\n\nHere is the total surcharge that was added to your bill: $" + surchargePerSeat);
            }

            confirmationTicketNum = confirmationTicketGenerator();

            System.out.println("\nHere is your confirmation number: " + confirmationTicketNum);

            businessClassTicketCt.setNumOfTicketsPurchased(ticketsPurchase); //add ticket purchesed


            validInput = false;
          }else{
            System.out.print("\n* Insufficient funds, your current balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print(" * \nPlease try again.\n");
            System.out.print("\nYour estimated total cost is $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");
            System.out.println("\n\nIf you are out of money or do not have enough funds please enter 0.");
            ticketsPurchase = 0;
            ticketsPurchasePrice = 0;
            validInput = true;
            checkTickets = true;
          }

        }
        availableSeats = availableSeats - ticketsPurchase;
        currFlight.setBusinessClassSeats(availableSeats);
        currFlight.setBusinessClassSeatsSold(ticketsPurchase);

        currFlight.setTotalSeats(totalSeats - currFlight.getBusinessClassSeatsSold());

        businessClassTicketCt.setNumberOfSeats(ticketsPurchase);
        businessClassTicketCt.setComfirmationNum(confirmationTicketNum);
        totalTickets.add(businessClassTicketCt);
        tickestBought.add(new Ticket(currFlight.getID(), currFlight.getFlightNum(), currFlight.getOriginAirport(),currFlight.getDestinationAirport(), currFlight.getDepartureDate(),
         currFlight.getDepartureTime(), ticketsPurchase, ticketsPurchasePrice, "Business Class", "ACTIVE", confirmationTicketNum, currCustomer.getFirstName(), currCustomer.getLastName()));

        currFlight.listOfPurchasedTickets(businessClassTicketCt);

        try {
          DecimalFormat df = new DecimalFormat("0.00");
          myWriter.write("User (" + currCustomer.getUsername() +") purchased a business class ticket(s) for Flight ID: " + currFlight.getID());
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") bought " + businessClassTicketCt.getNumOfTicketsPurchased() + " tickets.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + MINERAIRLINES_FEE + " for the transaction fee.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + df.format(securityFee) + " in security fee(s).");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") saved a total of $" + df.format(membershipTotalSaving));
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charded $" + df.format(currFlight.getOrginAirportFee()) + " in orgin airport fee.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charded $" + df.format(currFlight.getDestinationAirportFee()) + " in destination airport fee.");
          myWriter.write("\nThe ticket cost is $"  + currFlight.getBusinessClassPrice() + " per seat.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " has a surcharge of $" + currFlight.getSurcharge());
          myWriter.write("\nFirst class now has " + currFlight.getBusinessClassSeats() + " seats available.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " now has " + currFlight.getTotalSeats() + " total seats.");
          myWriter.write("\nComfitmation number is: " + confirmationTicketNum + "\n");
          myWriter.write("\n");
          myWriter.write("\n");
        } catch (IOException e) {
          System.out.println("\nAn error occurred while writting to file!");
          e.printStackTrace();
        }
        ticketCSVFile = new Ticket(currFlight.getID(),currCustomer.getFirstName(),currCustomer.getLastName(),"Business Class",businessClassTicketCt.getNumberOfSeats(),ticketsPurchasePrice);
        ticketHistoryCSVFileWriter(csvFileWriter,ticketCSVFile);

        checkTickets = true;
        
        break;
        /************************************************************************************************************************************/
        case 3:
        availableSeats = currFlight.getMainCabinSeats();
        if(currFlight.isInternational()){         //Check if this flight has a surcharge
          System.out.println("\n* The flight that you have choosen has an international destination, there will be an extra $" + currFlight.getSurcharge() +
          " PER SEAT that you purchase *");
        }
        if(currCustomer.getMinerAirMembership()){
          System.out.println("\n* Since you are a MinerAirlines Member you will receive a 5% discount on the base price of the ticket *");
        }
        System.out.println("\n* MinerAirlines has a $" + MINERAIRLINES_FEE + " fee PER transaction, in addition we charge an extra $" +SECURITY_FEE + " security fee PER ticket *");
        System.out.println("\nHow many seats would you like to buy?" + "\nRemember that you are only allowed to buy 8 tickets per class." + 
           "\nThere are " + availableSeats + " seats available for Main Cabin Class.");
        while(validInput){ //Chesk is customer has enough funds
          
          while(checkTickets){ //Check is the number of tickets does not exceed 6
            System.out.print("\nNumber of tickets: ");
            ticketsPurchase = Integer.parseInt(userInput.nextLine()); //check heeeerrrr

            if(firstClassTicketCt.checkNumOfTickets(ticketsPurchase)){ // checks the amount of tickets entered
              checkTickets = false;
            }
            else{
              System.out.println("\nYou can only buy 8 seats tickets per class!");
            }
          }
          
          if(role.equalsIgnoreCase("Employee") && currCustomer.getMinerAirMembership()){
            discountPrice += currFlight.getMainCabinPrice() - (currFlight.getMainCabinPrice() * (EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            membershipSaving += currFlight.getMainCabinPrice() * EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getMainCabinPrice() * (EMPLOYEE_DISCOUNT + MEMBERSHIP_DISCOUNT));
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(role.equalsIgnoreCase("Employee")){
            discountPrice += currFlight.getMainCabinPrice() - (currFlight.getMainCabinPrice() * EMPLOYEE_DISCOUNT);
            membershipSaving += currFlight.getBusinessClassPrice() * EMPLOYEE_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getMainCabinPrice() * EMPLOYEE_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else if(currCustomer.getMinerAirMembership()){ //Apply discount if member
            discountPrice += currFlight.getMainCabinPrice() - (currFlight.getMainCabinPrice() * MEMBERSHIP_DISCOUNT);
            membershipSaving += currFlight.getMainCabinPrice() * MEMBERSHIP_DISCOUNT;
            membershipTotalSaving += ticketsPurchase * (currFlight.getMainCabinPrice() * MEMBERSHIP_DISCOUNT);
            ticketsPurchasePrice += ticketsPurchase * discountPrice;
          }
          else{
            ticketsPurchasePrice += ticketsPurchase * currFlight.getMainCabinPrice();
          }

          salesTax += (ticketsPurchasePrice * TEXAS_SALES_TAX); //Taxes before all discounts and fees
          securityFee += ticketsPurchase * SECURITY_FEE; // $5.60 security fee per ticket
          ticketsPurchasePrice += securityFee;
          ticketsPurchasePrice += MINERAIRLINES_FEE;       // $9.15 per ticket transaction
          ticketsPurchasePrice += currFlight.getOrginAirportFee();        //Orgin airport fee
          ticketsPurchasePrice += currFlight.getDestinationAirportFee();  //Destintion airport fee
          surchargePerSeat += ticketsPurchase * currFlight.getSurcharge();

          if (currFlight.isInternational()){           //Add surcharge is it is an international flight
            ticketsPurchasePrice += surchargePerSeat;
          }

          ticketsPurchasePrice += salesTax;

          if(checkBalance(currCustomer, ticketsPurchasePrice)){
            System.out.print("\nTransaction successful! Your new balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print("\nHere are your " + ticketsPurchase + " ticket(s) with a total cost of $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");

            currCustomer.setFlightsPurchased(ticketsPurchase);
            mainCabinClassRevenue += ticketsPurchasePrice - (ticketsPurchasePrice * MEMBERSHIP_DISCOUNT)  - salesTax - securityFee;
            currFlight.setMainCabinClassRevenue(mainCabinClassRevenue);

            if (currFlight.isInternational()){
              System.out.println("\n\nHere is the total surcharge that was added to your bill: $" + surchargePerSeat);
            }

            confirmationTicketNum = confirmationTicketGenerator();

            System.out.println("\nHere is your confirmation number: " + confirmationTicketNum);

            mainCabinTicketCt.setNumOfTicketsPurchased(ticketsPurchase); //add ticket purchesed


            validInput = false;
          }else{
            System.out.print("\n* Insufficient funds, your current balance is: ");
            System.out.printf("%.2f", currCustomer.getMoneyAvailable());
            System.out.print(" * \nPlease try again.\n");
            System.out.print("\nYour estimated total cost is $");
            System.out.printf("%.2f", ticketsPurchasePrice);
            System.out.print("\n\nYou were charged $" + MINERAIRLINES_FEE +" for the transaction fee and a total of $");
            System.out.printf("%.2f", securityFee);
            System.out.print(" in security fees.");
            System.out.print("\n\n" + currFlight.getOriginAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getOrginAirportFee());
            System.out.print("\n\n" + currFlight.getDestinationAirport() + " has a fee of $");
            System.out.printf("%.2f", currFlight.getDestinationAirportFee());
            System.out.print("\n\nYou saved a total of $");
            System.out.printf("%.2f", membershipTotalSaving);
            System.out.print("\n\nYou were charged $");
            System.out.printf("%.2f", salesTax);
            System.out.print(" in sales tax.");
            System.out.println("\n\nIf you are out of money or do not have enough funds please enter 0.");
            ticketsPurchase = 0;
            ticketsPurchasePrice = 0;
            validInput = true;
            checkTickets = true;
          }

        }
        availableSeats = availableSeats - ticketsPurchase;
        currFlight.setMainCabinSeats(availableSeats);
        currFlight.setMainCabinClassSeatsSold(ticketsPurchase);

        currFlight.setTotalSeats(totalSeats - currFlight.getMainCabinClassSeatsSold());

        mainCabinTicketCt.setNumberOfSeats(ticketsPurchase);
        mainCabinTicketCt.setComfirmationNum(confirmationTicketNum);
        totalTickets.add(mainCabinTicketCt);
        tickestBought.add(new Ticket(currFlight.getID(), currFlight.getFlightNum(), currFlight.getOriginAirport(),currFlight.getDestinationAirport(), currFlight.getDepartureDate(),
         currFlight.getDepartureTime(), ticketsPurchase, ticketsPurchasePrice, "Main Cabin Class", "ACTIVE", confirmationTicketNum, currCustomer.getFirstName(), currCustomer.getLastName()));

        currFlight.listOfPurchasedTickets(mainCabinTicketCt);

        try {
          DecimalFormat df = new DecimalFormat("0.00");
          myWriter.write("User (" + currCustomer.getUsername() +") purchased a business class ticket(s) for Flight ID: " + currFlight.getID());
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") bought " + businessClassTicketCt.getNumOfTicketsPurchased() + " tickets.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + MINERAIRLINES_FEE + " for the transaction fee.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charged $" + df.format(securityFee) + " in security fee(s).");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") saved a total of $" + df.format(membershipTotalSaving));
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charded $" + df.format(currFlight.getOrginAirportFee()) + " in orgin airport fee.");
          myWriter.write("\nUser (" + currCustomer.getUsername() + ") was charded $" + df.format(currFlight.getDestinationAirportFee()) + " in destination airport fee.");
          myWriter.write("\nThe ticket cost is $"  + currFlight.getBusinessClassPrice() + " per seat.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " has a surcharge of $" + currFlight.getSurcharge());
          myWriter.write("\nFirst class now has " + currFlight.getBusinessClassSeats() + " seats available.");
          myWriter.write("\nFlight ID: " + currFlight.getID() + " now has " + currFlight.getTotalSeats() + " total seats.");
          myWriter.write("\nComfitmation number is: " + confirmationTicketNum + "\n");
          myWriter.write("\n");
        } catch (IOException e) {
          System.out.println("\nAn error occurred while writting to file!");
          e.printStackTrace();
        }
        ticketCSVFile = new Ticket(currFlight.getID(),currCustomer.getFirstName(),currCustomer.getLastName(),"Main Cabin Class",mainCabinTicketCt.getNumberOfSeats(),ticketsPurchasePrice);
        ticketHistoryCSVFileWriter(csvFileWriter,ticketCSVFile);

        checkTickets = true;
        
        break;
        /************************************************************************************************************************************/
        case 4:
         try {
          myWriter.write("User (" + currCustomer.getUsername() +") has exited out of the program.");
          myWriter.write("\n");
        } catch (IOException e) {
          System.out.println("\nAn error occurred while writting to file!");
          e.printStackTrace();
        }
         break;
      }
      
    }
    /**
     * 
     * @param currCustomer You will need to pass on the current customer object to check the avalable balance.
     * @param ticketsPurchase You will need to pass on the total cost of the tickets including the fees in a float.
     * @return This method will return true or false depending if the current customer has enough funds to cover the bill.
     */
    public static boolean checkBalance(Customer currCustomer, float ticketsPurchase){
      boolean enoughtFunds = false;
      float customerBalance = currCustomer.getMoneyAvailable();
      float checkCustBalance = customerBalance - ticketsPurchase;

      if(checkCustBalance > 0){
        enoughtFunds = true;
        currCustomer.setMoneyAvailable(checkCustBalance); //This updates the amount of money spent of th current customer.
      }

      return enoughtFunds;
    }
    /**
     * 
     * @return This method will generate a random comfirmation number in a long data type.
     */
    public static long confirmationTicketGenerator(){
      long leftLimit = 1000L;
      long rightLimit = 5000L;
      long confirmationTicketNum = new Random().nextLong(leftLimit,rightLimit);
      return confirmationTicketNum;
    }
    /**
     * 
     * @param customerLog This variable will contain the HashMap that was created when using the customers() method. 
     * @return This method will return an array list containing all of the employees from the HashMap.
     */
    public static ArrayList<Employee>employees(HashMap<Integer,Customer> customerLog){
      int customerId = 1;
  
      ArrayList<Employee> employeeList = new ArrayList<Employee>();
      while(customerId < customerLog.size()+1){
        if(customerLog.get(customerId).getRole().equalsIgnoreCase("Employee")){
          Employee newEmployee = new Employee(customerLog.get(customerId).getID(), customerLog.get(customerId).getFirstName(), customerLog.get(customerId).getLastName(), customerLog.get(customerId).getUsername(), customerLog.get(customerId).getPassword());
          employeeList.add(newEmployee);
        }
        customerId++;
      }
      
      return employeeList;
    }
    /**
     * 
     * @param employeeLog This variable will contain the HashMap that was created when using the employees() method. 
     * @param firstName This variable will have the String first name of the employee customer.
     * @param lastName This variable will have the String last name of the employee customer.
     * @return This method will return the current employee object.
     */
    public static boolean findEmplyeeObj(ArrayList<Employee> employeeLog, String firstName, String lastName){
      int i = 0;

      while(i < employeeLog.size()){
        if(employeeLog.get(i).getFirstName().equalsIgnoreCase(firstName) && employeeLog.get(i).getLastName().equalsIgnoreCase(lastName)){
          return true;
        }
        i++;
      }
      return false;
    }
    /**
     * 
     * @param ticketsBought You will need to pass on an array list containing all on the tickets that the current user has bought.
     * @param currCustomer You will need to pass on the current customer object to refund back the money from the ticket sale.
     * @param flightLog You will need to pass on the flight list to return back the seats based on the class.
     * @param ticketCancel This variable will contain the ticket integer number that is going to be canceled.
     * @param myWriterIn this variable you will pass on the file writter object, this will allow you to only use one file writter instead of creating one per method, all writting will go to the Log.txt file.
     */
    public static void cancelTicketMenu(ArrayList<Ticket> ticketsBought, Customer currCustomer, HashMap<Integer,Flight>flightLog, int ticketCancel, FileWriter myWriter){
      Ticket ticketToCancel = ticketsBought.get(ticketCancel);
      float returnMoney = ticketsBought.get(ticketCancel).getClassCost() - (float)9.15; //$9.15 will not be returned since it is a MinerAirline Fee
      float currCustomerMoney = currCustomer.getMoneyAvailable();
      currCustomer.setMoneyAvailable(currCustomerMoney + returnMoney);
      Flight flightToFix = flightLog.get(ticketToCancel.getID());
      int ticketReturnSeats = ticketToCancel.getNumberOfSeats();
      int currFlightSeats = 0;
      if(ticketToCancel.getClassType().equalsIgnoreCase("First Class")){
        currFlightSeats = flightToFix.getFirstClassSeats();
        flightToFix.setFirstClassSeats(currFlightSeats + ticketReturnSeats);
      }
      else if(ticketToCancel.getClassType().equalsIgnoreCase("Business Class")){
        currFlightSeats = flightToFix.getBusinessClassSeats();
        flightToFix.setFirstClassSeats(currFlightSeats + ticketReturnSeats);
      }
      else{
        currFlightSeats = flightToFix.getMainCabinSeats();
        flightToFix.setFirstClassSeats(currFlightSeats + ticketReturnSeats);
      }

      ticketToCancel.setStatus("CANCELED");
      ticketsBought.remove(ticketCancel); //Get rid of active ticket
      ticketsBought.add(ticketToCancel);  //Add same ticket but with canceled status


      System.out.println("\nYou succesfully canceled your ticket and your money from ticket number: " + (ticketCancel + 1) + " has been returned.");
      System.out.print("\nYour new balance is: ");
      System.out.printf("%.2f", currCustomer.getMoneyAvailable());

      try {
        myWriter.write("User (" + currCustomer.getUsername() +") has canceled a ticket for Flight ID: " + flightToFix.getID());
        
        myWriter.write("\n");
      } catch (IOException e) {
        System.out.println("\nAn error occurred while writting to file!");
        e.printStackTrace();
      }
    }
    /**
     * 
     * @param ticketListIn You will need to pass on an array list containing all on the tickets that the current user has bought.
     */
    public static void printTicketsBought(ArrayList<Ticket> ticketListIn){
      for(int i = 0; i < ticketListIn.size(); i++){
        System.out.println("\nTicket Number: " + (i + 1));
        System.out.println("Ticket Status: " + ticketListIn.get(i).getStatus());
        System.out.println("Flight Number: " + ticketListIn.get(i).getFlightNum());
        System.out.println("Airport Orgin: " + ticketListIn.get(i).getOriginAirport());
        System.out.println("Airport Destination: " + ticketListIn.get(i).getDestinationAirport());
        System.out.println("Boarding Date: " + ticketListIn.get(i).getDepartureDate() + " " + ticketListIn.get(i).getDepartureTime());

      }
    }
    /**
     * 
     * @param csvFileWriter This variable will contain the name of the file where all of the purchase tickets will be recorded.
     * @param ticketIn You will need to pass on an array list containing all on the tickets that the current user has bought.
     * @throws IOException This exception is used for when using the file writter object to write information.
     */
    public static void ticketHistoryCSVFileWriter(FileWriter csvFileWriter, Ticket ticketIn) throws IOException{
      try{
        //String header = "ID,First Name,Last Name, Class Type,Seats Purchased,Total Price";
        csvFileWriter.write(ticketIn.getID()+ "," +ticketIn.getTicketFirstName() + "," + ticketIn.getTickeLastName() +
        "," + ticketIn.getClassType() + "," +ticketIn.getNumberOfSeats()+","+ticketIn.getClassCost());
        csvFileWriter.write("\n");
      }
      catch (IOException e){
        System.out.println("\nAn error occurred while writting to csv file!");
        e.printStackTrace();
      }
    }
    /**
     * 
     * @param csvName This variable will contain the String name of the file where all of the tickets that have been purchased
     * @return This method will return an array list containing the ticket object. The object will have information about the flight such as the boarding date and time, Flight ID, First Name, Last Name, Class Type, seats, Total cost
     * @throws FileNotFoundException This exception is used if the csv file is not passed.
     */
    public static ArrayList<Ticket> ticketHistoryCSVFileReader(String csvName) throws FileNotFoundException{
      Scanner fileReader = new Scanner(new File(csvName));
      ArrayList<Ticket> ticketHistory = new ArrayList<Ticket>();
      String [] token; // Used to split information
      String currLine;
      currLine = fileReader.nextLine();// skips header
      int i = 0;

      while(fileReader.hasNext()){ //Generates information about the customer
          currLine = fileReader.nextLine(); //gets next line
          token = currLine.split(",");
          ticketHistory.add(new Ticket(Integer.parseInt(token[0]), token[1], token[2], token[3], Integer.parseInt(token[4]), Float.parseFloat(token[5])));
          i++;
      }

      return ticketHistory;
    }
    /**
     * 
     * @param customerLog This variable will contain the HashMap that was created when using the customers() method. 
     * @param firstName This variable will have the String first name of the employee customer.
     * @param lastName This variable will have the String last name of the employee customer.
     * @param returnFunds This variable will have the amount of money that will be returned back to the customer.
     */
    public static void ticketRefund(HashMap<Integer,Customer>customerLog, String firstName, String lastName, float returnFunds){
      Customer currCustomer = findCustomerObj(customerLog, firstName, lastName);
      float currCustomerMoney = currCustomer.getMoneyAvailable();
      currCustomer.setMoneyAvailable(currCustomerMoney + returnFunds);
    }
    /**
     * 
     * @param customerLog This variable will contain the HashMap that was created when using the customers() method. 
     * @param customerUpdatethis variable you will pass on the file writter object, this file writter will create an updated verion of the customer log after the program finishes.
     * @throws IOException This exception is used for when using the file writter object to write information.
     */
    public static void writeUpdatedCustomerFile(HashMap<Integer,Customer> customerLog, FileWriter customerUpdate)throws IOException{
      int i = 1;
      customerUpdate.write("ID,First Name,Last Name,DOB,Role,Money Available,Flights Purchased,MinerAir Membership,Username,Password");
      customerUpdate.write("\n");
      while(i < customerLog.size()+1){
        customerUpdate.write(customerLog.get(i).getID() + "," + customerLog.get(i).getFirstName() + "," + customerLog.get(i).getLastName()+ "," + customerLog.get(i).getDateOfBirth() + "," + customerLog.get(i).getRole()
        + "," + customerLog.get(i).getMoneyAvailable() + "," + customerLog.get(i).getFlightsPurchased() + "," + customerLog.get(i).getMinerAirMembership() + "," + customerLog.get(i).getUsername() + "," + customerLog.get(i).getPassword());
        customerUpdate.write("\n");
        i++;
      }

    }
    /**
     * 
     * @param flightLog This variable will contain the HashMap that was created when using the flights() method. 
     * @param fileName This variable will contain the String name of the file that will be used to have an updated version of the flights
     * @param flightUpdatevariable you will pass on the file writter object, this file writter will create an updated verion of the customer log after the program finishes.
     * @throws IOException This exception is used for when using the file writter object to write information.
     */
    public static void writeUpdatedFlightScheduleFile(HashMap<Integer,Flight> flightLog, String fileName, FileWriter flightUpdate)throws IOException{
      int i = 1;
      flightUpdate.write("ID,Flight Number,Origin Airport,Origin Airport City,Origin Airport State,Origin Airport Country,Origin Code,Origin Airport Fee,Destination Airport,Destination Airport City,Destination Airport State,Destination Airport Country,Destination Code,Destination Airport Fee,Departing Date,Departing Time,Duration,Distance,Time Zone Difference,Arrival Date,Arrival Time,Flight Type,Surcharge,Food Served,Route Cost,Miner Points,Total Seats,First Class Seats,Business Class Seats,Main Cabin Seats,First Class Price,Business Class Price,Main Cabin Price,First Class Seats Sold,Business Class Seats Sold,Main Cabin Seats Sold,Total First Class Revenue,Total Business Class Revenue,Total Main Cabin Revenue");
      flightUpdate.write("\n");
      while(i < flightLog.size()+1){
        flightUpdate.write(flightLog.get(i).getID() + "," + flightLog.get(i).getFlightNum() + "," + flightLog.get(i).getOriginAirport() + "," + flightLog.get(i).getOriginAirportCity() + ","+ flightLog.get(i).getOriginAirportState() + "," + flightLog.get(i).getOriginAirportCountry() + "," + flightLog.get(i).getOriginCode() + flightLog.get(i).getOrginAirportFee() + "," 
        + "," + flightLog.get(i).getDestinationAirport() + flightLog.get(i).getDestinationAirportCity() + "," + flightLog.get(i).getDestinationAirportState() + "," + flightLog.get(i).getDestinationAirportCountry() + ","+ flightLog.get(i).getDestinationCode()+ ","+ flightLog.get(i).getDestinationAirportFee()
        + "," + flightLog.get(i).getDepartureDate() + "," + flightLog.get(i).getDepartureTime() + "," + flightLog.get(i).getDuration() + "," + flightLog.get(i).getDistance()
        + "," + flightLog.get(i).getTimeZoneDifference() + "," + flightLog.get(i).getArrivalDate() + "," + flightLog.get(i).getArrivalTime() + "," + flightLog.get(i).getFlightType() + "," + flightLog.get(i).getSurcharge()
        + "," + flightLog.get(i).getFoodService() + "," + flightLog.get(i).getRouteCost() + "," + flightLog.get(i).getMinerPoints() + "," + flightLog.get(i).getTotalSeats() + "," + flightLog.get(i).getFirstClassSeats()
        + "," + flightLog.get(i).getBusinessClassSeats() + "," + flightLog.get(i).getMainCabinSeats() + "," + flightLog.get(i).getFirstClassPrice() + "," + flightLog.get(i).getBusinessClassPrice() + "," + flightLog.get(i).getMainCabinPrice()
        + "," + flightLog.get(i).getFirstClassSeatsSold() + "," + flightLog.get(i).getBusinessClassSeatsSold() + "," + flightLog.get(i).getMainCabinClassSeatsSold() + "," + flightLog.get(i).getFirstClassRevenue() 
        + "," + flightLog.get(i).getBusinessClassRevenue() + "," + flightLog.get(i).getMainCabinClassRevenue());
        flightUpdate.write("\n");
        i++;
      }
    }
    /**
     * 
     * @param flightLog This variable will contain the HashMap that was created when using the flights() method.
     * @return This method will use a factory design pattern to sort the flights based on whether it is a domestic or international flight.
     */
    public static HashMap<Integer, Flight> sortFlights(HashMap<Integer, Flight> flightLog) {
      int i = 1;

      HashMap<Integer,Flight> flightTypeList= new HashMap<Integer,Flight>();
      while(i < flightLog.size()+1){
        if(flightLog.get(i).getFlightType().equalsIgnoreCase("International")){
          flightTypeList.put(i, flightLog.get(i));
        }
        i++;
      }
      return flightTypeList;
    }

    public static int searchByFlightNumber(HashMap<Integer, Flight> flightLog, String flightNumber, Scanner userInput){
      boolean verifyInput = true;
      int i = 1;
      while(verifyInput){
        while(i < flightLog.size() + 1){
            if(flightLog.get(i).getFlightNum().equalsIgnoreCase(flightNumber)){
                verifyInput = false;
                return flightLog.get(i).getID();
            }
            i++;
        }
        System.out.println("\n* Invalid flight number please try again *");
        System.out.print("Enter Flight Number: ");
        flightNumber = userInput.nextLine();
        i = 1;
      }
      return -1;
    }
  
    public static void printFlightsByCodes(HashMap<Integer, Flight> flightLog, String originCode, String destinationCode, Scanner userInput){
      int i = 1;
      while(i < flightLog.size() + 1){
          if(flightLog.get(i).getOriginCode().equalsIgnoreCase(originCode) && flightLog.get(i).getDestinationCode().equalsIgnoreCase(destinationCode)){
              flightLog.get(i).printFlight();
              System.out.println();
          }
          i++;
      }
    }

    public static HashMap<Integer,Flight> flightsByCodes(HashMap<Integer, Flight> flightLog, String originCode, String destinationCode){
      int i = 1;
      int flightIDX = 1;
      HashMap<Integer,Flight> flightsByCode = new HashMap<Integer, Flight>();
      while(i < flightLog.size() + 1){
          if(flightLog.get(i).getOriginCode().equalsIgnoreCase(originCode) && flightLog.get(i).getDestinationCode().equalsIgnoreCase(destinationCode)){
              flightsByCode.put(flightIDX, flightLog.get(i));
              flightIDX++;
          }
          i++;
      }
      return flightsByCode;
    }

    public static boolean flightExistByID(HashMap<Integer, Flight> flightLog, int flightID){
      int i = 1;
      while(i < flightLog.size() + 1){
        if(flightLog.get(i).getID() == flightID){
          return true;
        }
        i++;
      }
      return false;
    }


}
