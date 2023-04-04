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
 * @version 4.0 This Domestic class object extends to the Airport class. This class is responsible for storing all necessary items that describe a domestic flight such as,
 * flight number, destination country, and the number of passengers.
 */
public class Domestic extends Airport{
    private String flightNumber;
    private String destinationCountry;
    private int numOfPassengers;
    /**
     * Default contructor
     */
    public Domestic() {
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
    
}
