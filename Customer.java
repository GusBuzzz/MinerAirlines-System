import java.util.ArrayList;

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
/**
 * @version 3.0 This Customer class object,is responsible for storing all necessary items that describe a customer such as,
 * customer ID, role type, available money, ect.
 */
public class Customer extends Person{
    //Attributes
    private int ID;
    private String role;        //New attribute
    private float moneyAvailable;
    private int flightsPurchased;
    private boolean minerAirMembership;

    private String username;
    private String password;

    private ArrayList<Ticket> ticketCollection = new ArrayList<Ticket>();

    /**
     * Default constructor
     */
    Customer(){

    }

    //PA3 customer constructor
    /**
     * 
     * @param dateOfBirth In this variable you will pass on the date of birth of the current customer in a String data type.
     * @param usernameIn In this variable you will pass on the username of the current customer in a String data type.
     * @param moneyAvailableIn In this variable you will pass on the amount of money the current customer has in a float data type.
     * @param lastName In this variable you will pass on the last name of the current customer in a String data type.
     * @param passwordIn In this variable you will pass on the password of the current customer in a String data type.
     * @param roleIn In this variable you will pass on the role type of the current customer in a String data type.
     * @param idIn In this variable you will pass on the ID number of the current customer in an integer  data type.
     * @param firstName In this variable you will pass on the first name of the current customer in a String data type.
     * @param tickets In this variable you will pass on the number of tickets the current customer  had bought in an integer data type.
     * @param membership In this variable you will pass on the boolean value of the current customer it has a membership with the airport.
     */
    Customer(String dateOfBirth, String usernameIn, float moneyAvailableIn, String lastName, String passwordIn, String roleIn, int idIn, String firstName,
     int tickets, boolean membership){
        super(firstName, lastName, dateOfBirth);
        this.ID = idIn;
        this.role = roleIn;
        this.moneyAvailable = moneyAvailableIn;
        this.flightsPurchased = tickets;
        this.minerAirMembership = membership;
        this.username = usernameIn;
        this.password = passwordIn;

    }


   //Setters
   /**
    * 
    * @param iD In this variable you will pass on the integer ID number of the current customer.
    */
    public void setID(int iD) {
        this.ID = iD;
    }
    /**
     * 
     * @param roleIn In this variable you will pass on the String role of the current customer.
     */
    public void setRole(String roleIn){  //New setter
        this.role = roleIn;
    }
    /**
     * 
     * @param moneyAvailable In this variable you will pass on the float number of the available money the current customer has.
     */
    public void setMoneyAvailable(float moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }
    /**
     * 
     * @param ticketsPurchased In this variable you will pass on the number of tickets the current customer has bought.
     */
    public void setFlightsPurchased(int ticketsPurchased) {
        this.flightsPurchased = ticketsPurchased;
    }
    /**
     * 
     * @param isMember In this variable you will pass on the boolean value if the current customer is a member.
     */
    public void setIsTicketMinerMembership(boolean isMember) {
        this.minerAirMembership = isMember;
    }
    /**
     * 
     * @param username In this variable you will pass on the username of the current custmer in a String data type.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * 
     * @param password In this variable you will pass on the password of the current custmer in a String data type.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    //Getters
    /**
     * 
     * @return This method will return the current customer's ID.
     */
    public int getID() {
        return this.ID;
    }
    /**
     * 
     * @return This method will return the current role of the customer.
     */
    public String getRole(){  //New getter
        return this.role;
    }
    /**
     * 
     * @return This method will return the current customer's available balance in a float data type.
     */
    public float getMoneyAvailable() {
        return this.moneyAvailable;
    }
    /**
     * 
     * @return This method will return the number of tickets the current customer has bought.
     */
    public int getFlightsPurchased() {
        return this.flightsPurchased;
    }
    /**
     * 
     * @returnThis method will return the boolean value if the customer is a member.
     */
    public boolean getMinerAirMembership(){
        return this.minerAirMembership;
    }
    /**
     * 
     * @return This method will return the current customer's username in a String data type.
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * 
     * @returnThis method will return the current customer's password in a String data type.
     */
    public String getPassword() {
        return this.password;
    }

    public ArrayList<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(ArrayList<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }
    
}
