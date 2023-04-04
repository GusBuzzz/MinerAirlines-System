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
 * @version 4.0 This International class object extends to the Airport class. This class is responsible for storing all necessary items that describe an international flight such as,
 * flight number, destination country, the number of passengers, and the surcharges.
 */
public class International extends Airport{
    private String flightNumber;
    private String destinationCountry;
    private int numOfPassengers;
    private int surcharge;
    /**
     * Default constructor
     */
    public International() {
    }
    /**
     * 
     * @return This method returns the current flight number in a String data type.
     */
    public String getFlightNumber() {
        return flightNumber;
    }
    /**
     * 
     * @param flightNumber This variable will store the current flight's number.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    /**
     * 
     * @return This method returns the destination's country of the current flight.
     */
    public String getDestinationCountry() {
        return destinationCountry;
    }
    /**
     * 
     * @param destinationCountry This variable will store the destination's country name in a String data type.
     */
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
    /**
     * 
     * @return This method returns the number of passengers of the current flight.
     */
    public int getNumOfPassengers() {
        return numOfPassengers;
    }
    /**
     * 
     * @param passengers This variable will store the number of passengers the current flight has in an integer data type.
     */
    public void setNumOfPassengers(int passengers) {
        this.numOfPassengers = passengers;
    }
    /**
     * 
     * @param chargeIn This variable will store the fee amount the current flight's surcharge in an integer data type.
     */
    public void setSurcharge(int chargeIn){
        this.surcharge = chargeIn;
    }
    /**
     * 
     * @return This method returns the total surcharge of the current flight.
     */
    public int getSurcharge(){
        return this.surcharge;
    }
    /**
     * 
     * @param flightScheduel This variarible contains an array of all of the flight objects.
     * @return This method will return an array list of all the international flights.
     */
    public ArrayList<Flight> listOFInternational(Flight [] flightScheduel){
        ArrayList<Flight> internationalList = new ArrayList<Flight>();
        int i = 0;
        while(i < flightScheduel.length){
            if(flightScheduel[i].getFlightType().equalsIgnoreCase("International")){
                internationalList.add(flightScheduel[i]);
            }
            i++;
        }

        return internationalList;
    }
}
