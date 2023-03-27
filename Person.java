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
 * @version 3.0 This Person class object is responsible for storing all necessary items that describe an person such as,
 * first name, last name, date of birth.
 */
public abstract class Person{

    //Attributes
    private String firstName;
    private String lastName;
    private String dateOfBirth; //New attribut
  
    /**
     * Default contructor
     */
    public Person(){

    }
    /**
     * 
     * @param firstIn This variable will store the first name of the person in a String data type.
     * @param lastIn This variable will store the last name of the person in a String data type.
     */
    public Person(String firstIn, String lastIn){
        this.firstName = firstIn;
        this.lastName = lastIn;
    }
    /**
     * 
     * @param firstIn This variable will store the first name of the person in a String data type.
     * @param lastIn This variable will store the last name of the person in a String data type.
     * @param dateOfBirthIn This variable will store the first name of the person in a String data type.
     */
    public Person(String firstIn, String lastIn, String dateOfBirthIn){
        this.firstName = firstIn;
        this.lastName = lastIn;
        this.dateOfBirth = dateOfBirthIn;
    }

    //Setters
    /**
     * 
     * @param name This variable will store the first name of the person in a String data type.
     */
    public void setFirstName(String name){
        this.firstName = name;
    }
    /**
     * 
     * @param name This variable will store the last name of the person in a String data type.
     */
    public void setLastName(String name){
        this.lastName = name;
    }
    /**
     * 
     * @param dateOfBirth This variable will store the first name of the person in a String data type.
     */
    public void setDateOfBirth(String dateOfBirth) { //New setter
        this.dateOfBirth = dateOfBirth;
    }

    //Getters
    /**
     * 
     * @return This method will return the first name of the current person in string data type.
     */
    public String getFirstName(){
        return this.firstName;
    } 
    /**
     * 
     * @return This method will return the last name of the current person in string data type.
     */
    public String getLastName(){
        return this.lastName;
    }
    /**
     * 
     * @return This method will return the data of birth of the current person in string data type.
     */
    public String getDateOfBirth() { //New getter
        return dateOfBirth;
    }

}