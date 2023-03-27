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

import java.util.ArrayList;
/**
 * @version 3.0 This Flight class object extends to the Airport class. This class is responsible for storing all necessary items that describe a flight such as 
 * storing the flight ID, flight number, services, ect.
 */
public class Flight extends Airport{
    //Attributes
    private int ID;
    private String flightNum;
    //private String originAirport;       //Now in Airport.java
    //private String originCode;          //Now in Airport.java
    //private String destinationAirport;  //Now in Airport.java
    //private String destinationCode;     //Now in Airport.java
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String flightType;
    private int surcharge;      
    private boolean foodServed;
    private int routeCost;      
    private int minerPoints;    
    private int duration;
    private int distance;
    private int timeZoneDifference;
    private int firstClassPrice;
    private int businessClassPrice;
    private int mainCabinPrice;
    private int firstClassSeats;
    private int businessClassSeats;
    private int mainCabinSeats;
    private int totalSeats;
    private int firstClassSeatsSold;
    private int businessClassSeatsSold;
    private int mainCabinClassSeatsSold;
    private int firstClassRevenue;
    private int businessClassRevenue;
    private int mainCabinClassRevenue;
    private int securityFee; //This information should be maintained in the flight


    private Domestic domistic;
    private International international;

    private boolean firstClassSoldOut = false;
    private boolean businessClassSoldOut = false;
    private boolean mainCabinClassSoldOut = false;

    private ArrayList<Ticket> listOfTickets = new ArrayList<Ticket>();

    //default constructor
    /**
     * Default constructor
     */
    Flight(){

    }
    /**
     * 
     * @param flightId In this variable you will pass on the flight id in an integer data type.
     */
    Flight(int flightId){
        this.ID = flightId;
    }
    /**
     * 
     * @param flightId In this variable you will pass on the flight id in an integer data type.
     * @param flightNumIn In this variable you will pass on the flight number in a string data type.
     * @param airportOrginIn In this variable you will pass on the name of the orgin airport in a string data type.
     * @param airportDestinationIn In this variable you will pass on the name of the destination airport in a string data type.
     * @param departureDateIn In this variable you will pass on the departure date in a string data type.
     * @param departureTimeIn In this variable you will pass on the departure time in a string data type.
     */
    Flight(int flightId, String flightNumIn, String airportOrginIn, String airportDestinationIn,String departureDateIn, String departureTimeIn){
        super(airportOrginIn,airportDestinationIn);
        this.ID = flightId;
        this.flightNum = flightNumIn;
        this.departureDate = departureDateIn;
        this.departureTime = departureTimeIn;
    }
    //Flight consrustor for Tickets 
    /**
     * 
     * @param firstClassPrice In this variable you will pass on the first class price in an integer data type.
     * @param businessClassPrice In this variable you will pass on the business class price in an integer data type.
     * @param mainCabinPrice In this variable you will pass on the main cabin class price in an integer data type.
     */
    Flight(int firstClassPrice, int businessClassPrice, int mainCabinPrice){
        this.firstClassPrice = firstClassPrice;
        this.businessClassPrice = businessClassPrice;
        this.mainCabinPrice = mainCabinPrice;

    }
    //PA3 Flight object
    /**
     * 
     * @param arrivalDate In this variable you will pass on the arrival date of the flight in a string data type.
     * @param destinationState In this variable you will pass on the destination state of the flight in a string data type.
     * @param originCode In this variable you will pass on the orgin code of the flight in a string data type.
     * @param destinationCode In this variable you will pass on the destination code of the flight in a string data type.
     * @param departureDate In this variable you will pass on the departure date of the flight in a string data type.
     * @param firstClassPrice In this variable you will pass on the first class price in an integer data type.
     * @param departrueTime In this variable you will pass on the departure time of the flight in an integer data type.
     * @param arrivalTime In this variable you will pass on the arrival time of the flight in an integer data type.
     * @param duration In this variable you will pass on the duration of the flight in an integer data type.
     * @param idIn In this variable you will pass on the ID of the flight in an integer data type.
     * @param originAirportCity In this variable you will pass on the origin airport city of the flight in a string data type.
     * @param originAirportCountry In this variable you will pass on the origin airport country of the flight in a string data type.
     * @param orginAirportFee In this variable you will pass on the origin airport fee of the flight in a float data type.
     * @param timeZoneDifference In this variable you will pass on the time zone difference of the flight in an integer data type.
     * @param destinationAirportCity In this variable you will pass on the destination airport city of the flight in a string data type.
     * @param distanceIn In this variable you will pass on the distance of the flight in an integer data type.
     * @param destinationAirportCountry In this variable you will pass on the destination airport country of the flight in a string data type.
     * @param destinationAirportFee In this variable you will pass on the destination airport fee of the flight in a float data type.
     * @param destinationAirportLounge In this variable you will pass on the boolean value of the destination airport lounge.
     * @param originAirport In this variable you will pass on the name of the origin airport in a string data type.
     * @param flightType In this variable you will pass on the flight type of the flight in a string data type.
     * @param mainCabinPrice In this variable you will pass on the main cabin class price in an integer data type.
     * @param flightNumber In this variable you will pass on the flight number of the flight in a string data type.
     * @param businessClassSeats In this variable you will pass on the business seats of the flight in an integer data type.
     * @param surcharge In this variable you will pass on the surcharge of the flight in an integer data type.
     * @param foodServed In this variable you will pass on the boolean value if the current flight offers food.
     * @param routeCost In this variable you will pass on the total route cost of the current flight, in an integer data type.
     * @param originAirportState In this variable you will pass on the name of the orgin airport state of the current flight, in a string data type.
     * @param minerPoints In this variable you will pass on the total miner points of the current flight, in an integer data type.
     * @param totalSeats In this variable you will pass on the total number of seats of the current flight, in an integer data type.
     * @param firstClassSeats In this variable you will pass on the total number of first class seats of the current flight, in an integer data type.
     * @param destinationAirport In this variable you will pass on the name of the destination airport in a string data type.
     * @param mainCabinSeats In this variable you will pass on the total number of main cabin class seats of the current flight, in an integer data type.
     * @param originAirportLounge In this variable you will pass on the boolean value of the origin airport lounge.
     * @param businessClassPrice In this variable you will pass on the business class price in an integer data type.
     */
    Flight(String arrivalDate, String destinationState, String originCode, String destinationCode, String departureDate, int firstClassPrice, String departrueTime, String arrivalTime, 
    int duration, int idIn, String originAirportCity, String originAirportCountry, float orginAirportFee, int timeZoneDifference,String destinationAirportCity, int distanceIn, String destinationAirportCountry, float destinationAirportFee,
    boolean destinationAirportLounge, String originAirport, String flightType, int mainCabinPrice, String flightNumber, int businessClassSeats, int surcharge, boolean foodServed,
    int routeCost, String originAirportState, int minerPoints, int totalSeats, int firstClassSeats, String destinationAirport, int mainCabinSeats, boolean originAirportLounge, int businessClassPrice){
        super(destinationState, originCode, destinationCode, originAirportCity, originAirportCountry, orginAirportFee, destinationAirportCity, destinationAirportCountry, destinationAirportFee,
        destinationAirportLounge, originAirport, originAirportState, destinationAirport, originAirportLounge);
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.departureDate = departureDate;
        this.departureTime = departrueTime;
        this.timeZoneDifference = timeZoneDifference;
        this.duration = duration;
        this.surcharge = surcharge;
        this.foodServed = foodServed;
        this.routeCost = routeCost;
        this.firstClassPrice = firstClassPrice;
        this.businessClassPrice = businessClassPrice;
        this.mainCabinPrice = mainCabinPrice;
        this.firstClassSeats = firstClassSeats;
        this.businessClassSeats = businessClassSeats;
        this.mainCabinSeats = mainCabinSeats;
        this.totalSeats = totalSeats;
        this.ID = idIn;
        this.flightNum = flightNumber;
        this.distance = distanceIn;
        this.flightType = flightType;
        this.minerPoints = minerPoints;
    }

    //setters
    /**
     * 
     * @param id In this variable you will pass on the ID of the flight in an integer data type.
     */
    public void setID(int id){
        this.ID = id;
    }
    /**
     * 
     * @param fn In this variable you will pass on the flight number of the flight in a string data type.
     */
    public void setFlightNum(String fn){
        this.flightNum = fn;
    }
    /**
     * 
     * @param departureDate In this variable you will pass on the departure date of the flight in a string data type.
     */
    public void setDepartureDate(String departureDate){
        this.departureDate = departureDate;
    }
    /**
     * 
     * @param departureTime In this variable you will pass on the departure time of the flight in a string data type.
     */
    public void setDepartureTime(String departureTime){
        this.departureTime = departureTime;
    }
    /**
     * 
     * @param duration In this variable you will pass on the duration of the flight in an integer data type.
     */
    public void setDuration(int duration){
        this.duration = duration;
    }
    /**
     * 
     * @param distance In this variable you will pass on the distance of the flight in an integer data type.
     */
    public void setDistance(int distance){
        this.distance = distance;
    }
    /**
     * 
     * @param time In this variable you will pass on the time zone difference of the flight in an integer data type.
     */
    public void setTimeZoneDiff(int time){
        this.timeZoneDifference = time;
    }
    /**
     * 
     * @param arrivalDate In this variable you will pass on the arrival date of the flight in a string data type.
     */
    public void setArrivalDate(String arrivalDate){
        this.arrivalDate = arrivalDate;
    }
    /**
     * 
     * @param arrivalTime In this variable you will pass on the arrival time of the flight in a string data type.
     */
    public void setArrivalTime(String arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    // Start of new setters
    /**
     * 
     * @param flightType In this variable you will pass on the flight type of the flight in a string data type.
     */
    public void setFlightType(String flightType){
        this.flightType = flightType;
        if(flightType.equalsIgnoreCase("Domestic")){
            this.domistic = new Domestic();
        }
        else{
            this.international = new International();
        }
    }
    /**
     * 
     * @param chargeIn In this variable you will pass on the surcharge of the flight in an integer data type.
     */
    public void setSurcharge(int chargeIn){
        this.surcharge = chargeIn;
    }
    /**
     * 
     * @param foodServed In this variable you will pass on the boolean value if the current flight offers food.
     */
    public void setFoodService(boolean foodServed){
        this.foodServed = foodServed;
    }
    /**
     * 
     * @param routeCost In this variable you will pass on the total route cost of the current flight, in an integer data type.
     */
    public void setRouteCost(int routeCost){
        this.routeCost = routeCost;
    }
    /**
     * 
     * @param points In this variable you will pass on the total miner points of the current flight, in an integer data type.
     */
    public void setMinerPoints(int points){
        this.minerPoints = points;
    }
    /**
     * 
     * @param firstClassSoldOut In this variable you will pass on the boolean value if the first class is sold out.
     */
    public void setFirstClassSoldOut(boolean firstClassSoldOut) {
        this.firstClassSoldOut = firstClassSoldOut;
    }
    /**
     * 
     * @param businessClassSoldOut In this variable you will pass on the boolean value if the business class is sold out.
     */
    public void setBusinessClassSoldOut(boolean businessClassSoldOut) {
        this.businessClassSoldOut = businessClassSoldOut;
    }
    /**
     * 
     * @param mainCabinClassSoldOut In this variable you will pass on the boolean value if the main cabin class is sold out.
     */
    public void setMainCabinClassSoldOut(boolean mainCabinClassSoldOut) {
        this.mainCabinClassSoldOut = mainCabinClassSoldOut;
    }
    /**
     * 
     * @param totalSeats In this variable you will pass on the total number of seats the current flight has.
     */
    public void setTotalSeats(int totalSeats){
        this.totalSeats = totalSeats;
    }
    /**
     * 
     * @param firstClassSeats In this variable you will pass on the number of first class seats the current flight has.
     */
    public void setFirstClassSeats(int firstClassSeats){
        this.firstClassSeats = firstClassSeats;
    }
    /**
     * 
     * @param businessClassSeats In this variable you will pass on the total number of business class seats the current flight has.
     */
    public void setBusinessClassSeats(int businessClassSeats){
        this.businessClassSeats = businessClassSeats;
    }
    /**
     * 
     * @param mainCabinSeats In this variable you will pass on the total number of main cabin seats the current flight has.
     */
    public void setMainCabinSeats(int mainCabinSeats){
        this.mainCabinSeats = mainCabinSeats;
    }
    /**
     * 
     * @param firstClassPrice In this variable you will pass on the total first class price of the current flight ticket.
     */
    public void setFirstClassPrice(int firstClassPrice){
        this.firstClassPrice = firstClassPrice;
    }
    /**
     * 
     * @param businessClassPrice In this variable you will pass on the total business class price of the current flight ticket.
     */
    public void setBusinessClassPrice(int businessClassPrice){
        this.businessClassPrice = businessClassPrice;
    }
    /**
     * 
     * @param mainCabinPrice In this variable you will pass on the total main cabin class price of the current flight ticket.
     */
    public void setMainCabinPrice(int mainCabinPrice){
        this.mainCabinPrice = mainCabinPrice;
    }

    //getters
    /**
     * 
     * @return This method returns the current flight ID in an integer data type.
     */
    public int getID(){
        return ID;
    }
    /**
     * 
     * @return This method returns the current flight number in a string data type.
     */
    public String getFlightNum(){
        return flightNum;
    }
    /**
     * 
     * @return This method returns the current flight's departure date in a string data type.
     */
    public String getDepartureDate(){
        return departureDate;
    }
    /**
     * 
     * @return This method returns the current flight's departure time in a string data type.
     */
    public String getDepartureTime(){
        return departureTime;
    }
    /**
     * 
     * @return This method returns the current flight's arrival date in a string data type.
     */
    public String getArrivalDate(){
        return arrivalDate;
    }
    /**
     * 
     * @return This method returns the current flight's arrival time in a string data type.
     */
    public String getArrivalTime(){
        return arrivalTime;
    }
    /**
     * 
     * @return This method returns the current flight type in a string data type.
     */
    public String getFlightType(){
        return flightType;
    }
    /**
     * 
     * @return This method returns the current flight's surcharge in an integer data type.
     */
    public int getSurcharge(){
        return surcharge;
    }
    /**
     * 
     * @return This method returns the boolean value if the current flight offers food service.
     */
    public boolean getFoodService(){
        return foodServed;
    }
    /**
     * 
     * @return This method returns the current flight's route cost in an integer data type.
     */
    public int getRouteCost(){
        return routeCost;
    }
    /**
     * 
     * @return This method returns the miner points in an integer data type.
     */
    public int getMinerPoints(){
        return minerPoints;
    }
    /**
     * 
     * @return This method returns the current flight's duration in an integer data type.
     */
    public int getDuration(){
        return duration;
    }
    /**
     * 
     * @return This method returns the current flight's distance in an integer data type.
     */
    public int getDistance(){
        return distance;
    }
    /**
     * 
     * @return This method returns the current flight's time zone differece in an integer data type.
     */
    public int getTimeZoneDifference(){
        return timeZoneDifference;
    }
    /**
     * 
     * @return This method returns the total cost of the first class tickets of the current flight in an integer data type.
     */
    public int getFirstClassPrice(){
        return firstClassPrice;
    }
    /**
     * 
     * @return This method returns the total cost of the business class tickets of the current flight in an integer data type.
     */
    public int getBusinessClassPrice(){
        return businessClassPrice;
    }
    /**
     * 
     * @return This method returns the total cost of the main cabin class tickets of the current flight in an integer data type.
     */
    public int getMainCabinPrice(){
        return mainCabinPrice;
    }
    /**
     * 
     * @return This method returns the total number of first class seats of the current flight in an integer data type.
     */
    public int getFirstClassSeats(){
        return firstClassSeats;
    }
    /**
     * 
     * @return This method returns the total number of business class seats of the current flight in an integer data type.
     */
    public int getBusinessClassSeats(){
        return businessClassSeats;
    }
    /**
     * 
     * @return This method returns the total number of main cabin class seats of the current flight in an integer data type.
     */
    public int getMainCabinSeats(){
        return mainCabinSeats;
    }
    /**
     * 
     * @return This method returns the total number of seats of the current flight in an integer data type.
     */
    public int getTotalSeats(){
        return totalSeats;
    }
    /**
     * 
     * @return This method returns the boolean value if the first class is sold out.
     */
    public boolean isFirstClassSoldOut() {
        return firstClassSoldOut;
    }
    /**
     * 
     * @return This method returns the boolean value if the business class is sold out.
     */
    public boolean isBusinessClassSoldOut() {
        return businessClassSoldOut;
    }
    /**
     * 
     * @return This method returns the boolean value if the main cabin class is sold out.
     */
    public boolean isMainCabinClassSoldOut() {
        return mainCabinClassSoldOut;
    }
    //Print Flight
    /**
     * This method prints the current flight's information
     */
    public void printFlight(){
        System.out.println("Flight ID: " + this.ID +"\nFlight Number: " + this.flightNum + "\nAirport Origin: " + getOriginAirport() + "\nAirport Origin City: " + getOriginAirportCity()+ "\nAirport Origin State: " + getOriginAirportState() + "\nAirport Origin Country: " + getOriginAirportCountry()+ "\nOrigin Code: " + getOriginCode() +
         "\nAirport Destination: " + getDestinationAirport() + "\nAirport Destination City: " + getDestinationAirportCity() + "\nAirport Destination State: " + getDestinationAirportState() + "\nAirport Destination Country: " + getDestinationAirportCountry() + "\nDestination Code: " + getDestinationCode() + "\nDeparture Date: " + this.departureDate +
         "\nDeparture Time: " + this.departureTime + "\nArrival Date: " + this.arrivalDate + "\nArrival Time: " + this.arrivalTime + "\nDuration: " + this.duration + "\nDistance: " + this.distance + "\nTime Zone Diff: " + this.timeZoneDifference + "\nFlight Type: " + this.flightType + "\nFirst Class Price: " + this.firstClassPrice +
         "\nBusiness Class Price: " + this.businessClassPrice + "\nMain Cabin Price: " + this.mainCabinPrice + "\nFirst Class Seats: " + this.firstClassSeats +  "\nBusiness Class Seats: " + this.businessClassSeats + "\nMain Cabin Seats: " + this.mainCabinSeats + "\nTotal Seats: " + this.totalSeats);
    }

    //change departure date and arrival date
    /**
     * 
     * @param newDate Given the new departure date, this method will change the new departure date and arrival date appropriately
     */
    public void changeDepartureDate(String newDate){
        departureDate = newDate;
        arrivalDate = newDate;
    }

    //change departure time and arrival time
    /**
     * 
     * @param newDepartureTime Given the new departure time, this method will change the new departure time and arrival time appropriately
     */
    public void changeDepartureTime(String newDepartureTime){
        String [] changeTime = newDepartureTime.split(":");
        int hours = Integer.valueOf(changeTime[0]);
        int minutes = Integer.valueOf(changeTime[1]);


        int extraMins = 0;
        int extraHours = 0;
        int tempTime = this.duration;
        String newArrivalTime = "";

        while(tempTime >= 60){
            extraHours += 1;
            tempTime = tempTime - 60;
        }
        extraMins = (this.duration % 60);
        hours += extraHours + this.timeZoneDifference;
        minutes = minutes + extraMins;
        while(minutes >= 60){ //check is the addition of minuts add up to an hour
            hours += 1;
            minutes = minutes - 60;
        }

        //newArrivalTime = Integer.toString(hours) + ":" + Integer.toString(minutes);
        if(hours >= 12){
            hours = hours - 12;
            if (minutes < 10){ //Fix formatting of the minute time
                newArrivalTime = Integer.toString(hours) + ":0" + Integer.toString(minutes) + " PM";
            }
            else{
                newArrivalTime = Integer.toString(hours) + ":" + Integer.toString(minutes) + " PM";
                this.arrivalTime = newArrivalTime;
            }
        }
        else{
            if (minutes < 10){ //Fix formatting of the minute time
                newArrivalTime = Integer.toString(hours) + ":0" + Integer.toString(minutes) + " AM";
            }
            else{
                newArrivalTime = Integer.toString(hours) + ":" + Integer.toString(minutes) + " AM";
                this.arrivalTime = newArrivalTime;
            }
        }
        
        this.arrivalTime = newArrivalTime;
        if(Integer.valueOf(changeTime[0]) > 12){
            int depHours = Integer.valueOf(changeTime[0]) - 12;
            newDepartureTime = Integer.toString(depHours) + ":" + changeTime[1] + " PM";
            this.departureTime = newDepartureTime;
        }
        else{
            this.departureTime = newDepartureTime + " AM";
        }
        
    }
    /**
     * 
     * @param ticketIn In this variable you will pass the ticket object, which will then be added to an array list.
     */
    public void listOfPurchasedTickets(Ticket ticketIn){
        this.listOfTickets.add(ticketIn);
    }
    /**
     * 
     * @return This method returns true or false if the current flight object is an international fight.
     */
    public boolean isInternational(){
        if(this.flightType.equalsIgnoreCase("International")){
            return true;
        }
        return false;
    }
    /**
     * 
     * @return This method returns the number of first class seats sold in an integer data type.
     */
    public int getFirstClassSeatsSold() {
        return firstClassSeatsSold;
    }
    /**
     * 
     * @param firstClassSeatsSold In this variable you will pass the number of first class seats sold in an integer data type.
     */
    public void setFirstClassSeatsSold(int firstClassSeatsSold) {
        this.firstClassSeatsSold = firstClassSeatsSold;
    }
    /**
     * 
     * @return This method returns the number of business class seats sold in an integer data type.
     */
    public int getBusinessClassSeatsSold() {
        return businessClassSeatsSold;
    }
    /**
     * 
     * @param businessClassSeatsSold In this variable you will pass the number of business class seats sold in an integer data type.
     */
    public void setBusinessClassSeatsSold(int businessClassSeatsSold) {
        this.businessClassSeatsSold = businessClassSeatsSold;
    }
    /**
     * 
     * @return This method returns the number of main cabin class seats sold in an integer data type.
     */
    public int getMainCabinClassSeatsSold() {
        return mainCabinClassSeatsSold;
    }
    /**
     * 
     * @param mainCabinClassSeatsSold In this variable you will pass the number of main cabin class seats sold in an integer data type.
     */
    public void setMainCabinClassSeatsSold(int mainCabinClassSeatsSold) {
        this.mainCabinClassSeatsSold = mainCabinClassSeatsSold;
    }
    /**
     * 
     * @return This method returns the total first class revenue in an integer data type.
     */
    public int getFirstClassRevenue() {
        return firstClassRevenue;
    }
    /**
     * 
     * @param firstClassRevenue In this variable you will pass the total firsl class revenue in an integer data type.
     */
    public void setFirstClassRevenue(int firstClassRevenue) {
        this.firstClassRevenue = firstClassRevenue;
    }
    /**
     * 
     * @return This method returns the total business class revenue in an integer data type.
     */
    public int getBusinessClassRevenue() {
        return businessClassRevenue;
    }
    /**
     * 
     * @param businessClassRevenue In this variable you will pass the total business class revenue in an integer data type.
     */
    public void setBusinessClassRevenue(int businessClassRevenue) {
        this.businessClassRevenue = businessClassRevenue;
    }
    /**
     * 
     * @return This method returns the total main cabin class revenue in an integer data type.
     */
    public int getMainCabinClassRevenue() {
        return mainCabinClassRevenue;
    }
    /**
     * 
     * @param mainCabinClassRevenue In this variable you will pass the total main cabin class revenue in an integer data type.
     */
    public void setMainCabinClassRevenue(int mainCabinClassRevenue) {
        this.mainCabinClassRevenue = mainCabinClassRevenue;
    }
    /**
     * 
     * @return This method returns the boolean value if the current flight offers food service.
     */
    public boolean isFoodServed() {
        return foodServed;
    }
    /**
     * 
     * @param foodServed In this variable you will pass the boolean value if the current flight offers food service.
     */
    public void setFoodServed(boolean foodServed) {
        this.foodServed = foodServed;
    }
    /**
     * 
     * @param timeZoneDifference In this variable you will pass time zone difference of the current flight in an integer data type.
     */
    public void setTimeZoneDifference(int timeZoneDifference) {
        this.timeZoneDifference = timeZoneDifference;
    }
    /**
     * 
     * @return This method returns the total security fee if the tickets for the current flight in an integer data type.
     */
    public int getSecurityFee() {
        return securityFee;
    }
    /**
     * 
     * @param securityFee In this variable you will pass time zone difference of the current flight in an integer data type.
     */
    public void setSecurityFee(int securityFee) {
        this.securityFee = securityFee;
    }
    /**
     * 
     * @return This method returns the domestic object if the current flight is a domestic flight type.
     */
    public Domestic getDomistic() {
        return domistic;
    }
    /**
     * 
     * @param domistic In this variable you will pass the domestic object of the current flight with the necessary information of the domestic flight.
     */
    public void setDomistic(Domestic domistic) {
        this.domistic = domistic;
    }
    /**
     * 
     * @return This method returns the international object if the current flight is an international flight type.
     */
    public International getInternational() {
        return international;
    }
    /**
     * 
     * @param international In this variable you will pass the international object of the current flight with the necessary information of the international flight.
     */
    public void setInternational(International international) {
        this.international = international;
    }
    /**
     * 
     * @return This method returns the array list that stores all of the tickets that have been bought.
     */
    public ArrayList<Ticket> getListOfTickets() {
        return listOfTickets;
    }
    /**
     * 
     * @param listOfTickets In this variable you will pass the array list of tickets that have been bought.
     */
    public void setListOfTickets(ArrayList<Ticket> listOfTickets) {
        this.listOfTickets = listOfTickets;
    }

}