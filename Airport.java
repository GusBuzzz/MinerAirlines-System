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
 * @version 4.0 This abstract class object is called Airport, it is responsible for storing all necessary items that describe an airport such as,
 * airport fees, flight type, and number of flights in the schedule.
 */
public abstract class Airport {
    private boolean isDomestic;
    private int numOfFlights;
    private float orginAirportFee;            //New attribute
    private float destinationAirportFee;      //New attribute
    private boolean destinationAirportLounge; //New attribute
    private boolean originAirportLounge;      //New attribute
    private String destinationAirportState;   //New attribute
    private String destinationAirportCountry; //New attribute
    private String destinationAirportCity;    //New attribute
    private String originAirportState;        //New attribute
    private String originAirportCity;         //New attribute
    private String originAirportCountry;      //New attribute
    private String originAirport;       //Now in Airport.java
    private String originCode;          //Now in Airport.java
    private String destinationAirport;  //Now in Airport.java
    private String destinationCode;     //Now in Airport.java
    private double airportCollectedFees;
    /**
     * Default constructor
     */
    public Airport() {
    }
    /**
     * 
     * @param originAirporIn In this variable you will pass on the String name of the orgin airport.
     * @param destinationAirportIn In this variable you will pass on the String name of the destination airport.
     */
    public Airport(String originAirporIn, String destinationAirportIn){
        this.originAirport = originAirporIn;
        this.destinationAirport = destinationAirportIn;
    }
    /**
     * 
     * @param destinationStateIn In this variable you will pass on the String name of the destination airport state.
     * @param originCodeIn In this variable you will pass on the String code of the orgin airport.
     * @param destinationCodeIn In this variable you will pass on the String code of the destination airport.
     * @param originAirportCityIn In this variable you will pass on the String name of city of the orgin airport.
     * @param originAirportCountryIn  In this variable you will pass on the String name of the orgin airport country.
     * @param orginAirportFeeIn  In this variable you will pass on the fee of the orgin airport in a float.
     * @param destinationAirportCity In this variable you will pass on the String name of city of the destination airport.
     * @param destinationAiportCountry In this variable you will pass on the String name of the destination airport country.
     * @param destinationAirportFee In this variable you will pass on the fee of the destination airport in a float.
     * @param destinationAirportLounge In this variable you will pass on the boolean value if the destination airport offers a lounge.
     * @param originAirport In this variable you will pass on the String name of the orgin airport.
     * @param originAirportState In this variable you will pass on the String name of the orgin airport state.
     * @param destinationAirportIn In this variable you will pass on the String name of the destination airport.
     * @param originAirportLoungeIn this variable you will pass on the boolean value if the origin airport offers a lounge.
     */
    public Airport(String destinationStateIn, String originCodeIn, String destinationCodeIn, String originAirportCityIn, String originAirportCountryIn, float orginAirportFeeIn,
    String destinationAirportCity, String destinationAiportCountry, float destinationAirportFee, boolean destinationAirportLounge, String originAirport, String originAirportState,
    String destinationAirportIn, boolean originAirportLounge){
        this.destinationAirportState = destinationStateIn;
        this.originCode = originCodeIn;
        this.destinationCode = destinationCodeIn;
        this.originAirportCity = originAirportCityIn;
        this.originAirportCountry = originAirportCountryIn;
        this.destinationAirportCity = destinationAirportCity;
        this.orginAirportFee = orginAirportFeeIn;
        this.destinationAirportCountry = destinationAiportCountry;
        this.destinationAirportFee = destinationAirportFee;
        this.destinationAirportLounge = destinationAirportLounge;
        this.originAirport = originAirport;
        this.originAirportState = originAirportState;
        this.destinationAirport = destinationAirportIn;
        this.originAirportLounge = originAirportLounge;
    }
    /**
     * 
     * @return This method returns the boolean value if the airport object is domestic.
     */
    public boolean getIsDomestic() {
        return isDomestic;
    }
    /**
     * 
     * @param isDomestic In this variable you will pass on the boolean value of the airport if it's a domestic flight. 
     */
    public void setIsDomestic(boolean isDomestic) {
        this.isDomestic = isDomestic;
    }
    /**
     * 
     * @return This method returns the number of flights the airport has.
     */
    public int getNumOfFlights() {
        return numOfFlights;
    }
    /**
     * 
     * @param numOfFlights In this variable you will pass on the number of scheduled flights.
     */
    public void setNumOfFlights(int numOfFlights) {
        this.numOfFlights = numOfFlights;
    }
    /**
     * 
     * @return This method returns the fee of the origin airport in a float data type.
     */
    public float getOrginAirportFee() {
        return orginAirportFee;
    }
    /**
     * 
     * @param orginAirportFee In this variable you will pass on the fee of the orgin airport in a float data type.
     */
    public void setOrginAirportFee(float orginAirportFee) {
        this.orginAirportFee = orginAirportFee;
    }
    /**
     * 
     * @return This method returns the fee of the destination airport in a float data type.
     */
    public float getDestinationAirportFee() {
        return destinationAirportFee;
    }
    /**
     * 
     * @param destinationAirportFee In this variable you will pass on the fee of the destination airport in a float data type.
     */
    public void setDestinationAirportFee(float destinationAirportFee) {
        this.destinationAirportFee = destinationAirportFee;
    }
    /**
     * 
     * @return This method returns the boolean value of the destination airport if it has a lounge.
     */
    public boolean isDestinationAirportLounge() {
        return destinationAirportLounge;
    }
    /**
     * 
     * @param destinationAirportLounge In this variable you will pass on the boolean value of the destination airport if it has a lounge.
     */
    public void setDestinationAirportLounge(boolean destinationAirportLounge) {
        this.destinationAirportLounge = destinationAirportLounge;
    }
    /**
     * 
     * @return This method returns the String name of the destination airport state.
     */
    public String getDestinationAirportState() {
        return destinationAirportState;
    }
    /**
     * 
     * @param destinationAirportState In this variable you will pass on the String name of the destination airport state.
     */
    public void setDestinationAirportState(String destinationAirportState) {
        this.destinationAirportState = destinationAirportState;
    }
    /**
     * 
     * @return This method returns the String name of the orgin airport city.
     */
    public String getOriginAirportCity() {
        return originAirportCity;
    }
    /**
     * 
     * @param originAirportCity In this variable you will pass on the String name of the orgin airport city.
     */
    public void setOriginAirportCity(String originAirportCity) {
        this.originAirportCity = originAirportCity;
    }
    /**
     * 
     * @return This method returns the String name of the orgin airport country.
     */
    public String getOriginAirportCountry() {
        return originAirportCountry;
    }
    /**
     * 
     * @param originAirportCountry In this variable you will pass on the String name of the orgin airport country.
     */
    public void setOriginAirportCountry(String originAirportCountry) {
        this.originAirportCountry = originAirportCountry;
    }
    /**
     * 
     * @return This method returns the String name of the orgin airport.
     */
    public String getOriginAirport() {
        return originAirport;
    }
    /**
     * 
     * @param originAirport In this variable you will pass on the String name of the orgin airport.
     */
    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }
    /**
     * 
     * @return This method returns the String code of the orgin airport.
     */
    public String getOriginCode() {
        return originCode;
    }
    /**
     * 
     * @param originCode In this variable you will pass on the String code of the orgin airport.
     */
    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }
    /**
     * 
     * @return This method returns the String name of the destination airport.
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }
    /**
     * 
     * @param destinationAirport In this variable you will pass on the String name of the destination airport.
     */
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    /**
     * 
     * @return This method returns the String code of the destination airport.
     */
    public String getDestinationCode() {
        return destinationCode;
    }
    /**
     * 
     * @param destinationCode In this variable you will pass on the String code of the destination airport.
     */
    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }
    /**
     * 
     * @param isDomestic In this variable you will pass on the boolean if the airport is domestic.
     */
    public void setDomestic(boolean isDomestic) {
        this.isDomestic = isDomestic;
    }
    /**
     * 
     * @return This method returns the String name of the orgin airport state.
     */
    public String getOriginAirportState() {
        return originAirportState;
    }
    /**
     * 
     * @param originAirportState In this variable you will pass on the String name of the orgin airport state.
     */
    public void setOriginAirportState(String originAirportState) {
        this.originAirportState = originAirportState;
    }
    /**
     * 
     * @return This method returns the String name of the destination airport city.
     */
    public String getDestinationAirportCity() {
        return destinationAirportCity;
    }
    /**
     * 
     * @param destinationAirportCity In this variable you will pass on the String name of the destination airport city.
     */
    public void setDestinationAirportCity(String destinationAirportCity) {
        this.destinationAirportCity = destinationAirportCity;
    }
    /**
     * 
     * @return This method returns the String name of the destination airport country.
     */
    public String getDestinationAirportCountry() {
        return destinationAirportCountry;
    }
    /**
     * 
     * @param destinationAirportCountry In this variable you will pass on the String name of the destination airport country.
     */
    public void setDestinationAirportCountry(String destinationAirportCountry) {
        this.destinationAirportCountry = destinationAirportCountry;
    }
    /**
     * 
     * @return This method returns the boolean value of the orgin airport if it has a lounge.
     */
    public boolean isOriginAirportLounge() {
        return originAirportLounge;
    }
    /**
     * 
     * @param originAirportLounge In this variable you will pass on the boolean value of the orgin airport if it has a lounge.
     */
    public void setOriginAirportLounge(boolean originAirportLounge) {
        this.originAirportLounge = originAirportLounge;
    }
    /**
     * 
     * @return This method returns the amunt of money that has been collected in airport fees.
     */
    public double getAirportCollectedFees() {
        return airportCollectedFees;
    }
    /**
     * 
     * @param airportCollectedFees In this variable you will pass on the amount of collected airport fees.
     */
    public void setAirportCollectedFees(double airportCollectedFees) {
        this.airportCollectedFees = airportCollectedFees;
    }
    
}
