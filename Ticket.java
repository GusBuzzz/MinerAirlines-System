import java.util.ArrayList;
/*
 * Name: Gustavo Rubio
 * Date: April 4, 2023
 * CS 3331 – Advanced Object-Oriented Programming – Spring 2023
 * Instructor: Dr. Mejia
 * Assignment: PA4
 * This work was done individually and completely on my own. I did not share, reproduce, or alter any part of this assignment for any purpose.
 * I did not share code, upload this assignment online in any form, or view/received/modified code written from anyone else.
 * All deliverables were produced entirely on my own. This assignment is part of an academic course at The University of Texas at El Paso 
 * and a grade will be assigned for the work I produced.
 */
/**
 * @version 4.0 This Ticket class object extends the Flight class, it is responsible for storing all necessary items that describe a ticket such as,
 *  ticket first name, ticket last name, class type, ect.
 */
public class Ticket extends Flight{
    private String ticketFirstName;
    private String tickeLastName;
    private String status;
    private String classType;
    private float classCost;
    private int numOfTicketsPurchased;
    private int numOfTicketsAllowed;
    private int numberOfSeats;
    private long comfirmationNum;
    private static Ticket ticketObj; //Singleton first design pattern
    private ArrayList<Ticket> ticketCollection = new ArrayList<Ticket>();
    /**
     * Default contructor
     */
    public Ticket() {
    }
    /**
     * 
     * @return This constructor returns the singleton of the ticket class
     */
    public static Ticket getInstance(){
        if (ticketObj == null){
            ticketObj = new Ticket();
        }
        return ticketObj;
    }
    /**
     * 
     * @param flightIDin In this variable you will pass on the current flight ID number in an integer data type.
     * @param flightNumIn In this variable you will pass on the current flight number in an integer data type.
     * @param airportOrginIn In this variable you will pass on the name current orgin airport in a string data type.
     * @param airportDestinationIn In this variable you will pass on the name current destination airport in a string data type.
     * @param departureDateIn In this variable you will pass on the departure date of the current airport in a string data type.
     * @param boardingTime In this variable you will pass on the boarding time of the current airport in a string data type.
     * @param numberOfSeatsIn In this variable you will pass on the number of seats in the flight in an integer data type.
     * @param ticketCost In this variable you will pass on the total ticket cost in a float data type.
     * @param classType In this variable you will pass on the class type name of the current flight in a string data type.
     * @param statusIn In this variable you will pass on the status of the ticket in a string data type.
     * @param comfirmationNum In this variable you will pass on the comfirmation number of the ticket in a long data type.
     * @param firstName In this variable you will pass on the first name of the current customer in a string data type.
     * @param lastName In this variable you will pass on the last name of the current customer in a string data type.
     */
    public Ticket(int flightIDin, String flightNumIn, String airportOrginIn, String airportDestinationIn,String departureDateIn, 
    String boardingTime,int numberOfSeatsIn, float ticketCost, String classType, String statusIn, long comfirmationNum, String firstName, String lastName){
        super(flightIDin,flightNumIn,airportOrginIn,airportDestinationIn,departureDateIn,boardingTime);
        this.classCost = ticketCost;
        this.classType = classType;
        this.numberOfSeats = numberOfSeatsIn;
        this.status = statusIn;
        this.comfirmationNum = comfirmationNum;
        this.ticketFirstName = firstName;
        this.tickeLastName = lastName;

    }
    /**
     * 
     * @param ticketsBoughtIn In this variable you will pass on the number of tickets the current customer has bought in an integer data type.
     * @param comfirmationNumIn In this variable you will pass on the comfirmation number of the ticket in a long data type.
     */
    public Ticket(int ticketsBoughtIn, long comfirmationNumIn){
        this.numOfTicketsPurchased = ticketsBoughtIn;
        this.comfirmationNum = comfirmationNumIn;
    }
    /**
     * 
     * @param ticketsAllowedIn In this variable you will pass on the number of total tickets the customer is allowed to buy, in an integer data type.
     * @param seatsIn In this variable you will pass on the number of seats in the class type, in an integer data type.
     * @param confirmNum In this variable you will pass on the comfirmation number of the ticket in a long data type.
     */
    public Ticket(int ticketsAllowedIn, int seatsIn, long confirmNum){
        this.numOfTicketsAllowed = ticketsAllowedIn;
        this.numberOfSeats = seatsIn;
        this.comfirmationNum = confirmNum;
    }
    /**
     * 
     * @param firstClassPrice In this variable you will pass on the price of the first class ticket, in an integer data type.
     * @param businessClassPrice In this variable you will pass on the price of the business class ticket, in an integer data type.
     * @param mainCabinPrice In this variable you will pass on the price of the main cabin class ticket, in an integer data type.
     * @param ticketsIn In this variable you will pass on the number of tickets the current customer has bought in an integer data type.
     * @param numOfTicketsAllowed In this variable you will pass on the number of total tickets the customer is allowed to buy, in an integer data type.
     */
    public Ticket(int firstClassPrice, int businessClassPrice, int mainCabinPrice, int ticketsIn, int numOfTicketsAllowed){
        super(firstClassPrice, businessClassPrice, mainCabinPrice);
        this.numOfTicketsPurchased = ticketsIn;
        this.numOfTicketsAllowed = numOfTicketsAllowed;
    }
    /**
     * 
     * @param flightId In this variable you will pass on the current flight ID number in an integer data type.
     * @param firstName In this variable you will pass on the first name of the current customer in a string data type.
     * @param lastName In this variable you will pass on the last name of the current customer in a string data type.
     * @param classType In this variable you will pass on the class type name of the current flight in a string data type.
     * @param numSeats In this variable you will pass on the total number of seats the current flight has in a integer data type.
     * @param classCost In this variable you will pass on the total class cost of the the current flight in a float data type.
     */
    public Ticket(int flightId, String firstName, String lastName, String classType, int numSeats, float classCost ){
        super(flightId);
        this.ticketFirstName = firstName;
        this.tickeLastName = lastName;
        this.classType = classType;
        this.numberOfSeats = numSeats;
        this.classCost = classCost;
    }
    /**
     * 
     * @return This method will return the total number of tickets that have been purchased.
     */
    public int getNumOfTicketsPurchased() {
        return numOfTicketsPurchased;
    }
    /**
     * 
     * @param numOfTickets This variable will store the number of tickets that the current customer has purchased.
     */
    public void setNumOfTicketsPurchased(int numOfTickets) {
        this.numOfTicketsPurchased = numOfTickets;
    }
    /**
     * 
     * @return This method will return the total number of tickets that the customers are allowed to purchase.
     */
    public int getNumOfTicketsAllowed() {
        return numOfTicketsAllowed;
    }
    /**
     * 
     * @param numOfTicketsAllowed This variable will store the number of tickets that the customers are allowed to purchase.
     */
    public void setNumOfTicketsAllowed(int numOfTicketsAllowed) {
        this.numOfTicketsAllowed = numOfTicketsAllowed;
    }
    /**
     * 
     * @return This method will return the total number of seats that the current flight has.
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    /**
     * 
     * @param numberOfSeats This variable will store the number of seats that the current flight has.
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    /**
     * 
     * @return This method will return the comfirmation number of ticket that the current customer has bought.
     */
    public long getComfirmationNum() {
        return comfirmationNum;
    }
    /**
     * 
     * @param comfirmationNum This variable will store the comfirmation number of ticket that the current customer has bought.
     */
    public void setComfirmationNum(Long comfirmationNum) {
        this.comfirmationNum = comfirmationNum;
    }
    /**
     * 
     * @return This method will return the singleton of the ticket that the current customer has bought.
     */
    public static Ticket getTicketObj() {
        return ticketObj;
    }
    /**
     * 
     * @param obj This variable will store the ticket object of the singleton.
     */
    public static void setTicketObj(Ticket obj) {
        Ticket.ticketObj = obj;
    }
    /**
     * 
     * @param obj This variable will store all ticket objects in an array list.
     */
    public void addTicket(Ticket obj){
        ticketCollection.add(obj);
    }
    /**
     * 
     * @param numOfTickets This variable will store the max number of tickets a customer can purchase.
     * @return This method will return true or false if the customer has not reach its max number of tickets that can be purchased.
     */
    public boolean checkNumOfTickets(int numOfTickets){
        boolean verifyTickets = false;
        if(numOfTickets == 0){
            return verifyTickets;
        }
        else if(numOfTickets <= this.numOfTicketsAllowed){
          verifyTickets = true;
        }
        return verifyTickets;
    }
    /**
     * 
     * @return This method will return the total cost of the current class cost.
     */
    public float getClassCost() {
        return classCost;
    }
    /**
     * 
     * @param classCost This variable will store the total class cost in an integer data type.
     */
    public void setClassCost(int classCost) {
        this.classCost = classCost;
    }
    /**
     * 
     * @return This method will return the class type of the ticket in a string data type.
     */
    public String getClassType() {
        return classType;
    }
    /**
     * 
     * @param classType This variable will store the name of the class type in a string data type.
     */
    public void setClassType(String classType) {
        this.classType = classType;
    }
    /**
     * 
     * @return This method will return the status of the ticket in a string data type.
     */
    public String getStatus() {
        return status;
    }
    /**
     * 
     * @param status This variable will set the status of the ticket in a string data type.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 
     * @return This method will return the first name of the ticket in a string data type.
     */
    public String getTicketFirstName() {
        return ticketFirstName;
    }
    /**
     * 
     * @param ticketFirstName This variable will set the first name of the ticket in a string data type.
     */
    public void setTicketFirstName(String ticketFirstName) {
        this.ticketFirstName = ticketFirstName;
    }
    /**
     * 
     * @return This method will return the last name of the ticket in a string data type.
     */
    public String getTickeLastName() {
        return tickeLastName;
    }
    /**
     * 
     * @param tickeLastName This variable will set the last name of the ticket in a string data type.
     */
    public void setTickeLastName(String tickeLastName) {
        this.tickeLastName = tickeLastName;
    }
}
