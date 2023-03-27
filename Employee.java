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
 * @version 3.0 This Employee class object extends to the Person class. This class is responsible for storing all necessary items that describe an employee such as 
 * storing the employee ID, employee username, and password.
 */
public class Employee extends Person{
    //Attributes
    private int employeeID;
    private String employeeUserName;
    private String employeePassword;
    private boolean isEmployee;
    /**
     * 
     * @param employeeID In this variable you will pass on the ID number of the current employee in an integer data type.
     * @param firstNameIn In this variable you will pass on the first name of the current employee in a String data type.
     * @param lastNameIn In this variable you will pass on the last name of the current employee in a String data type.
     * @param employeeUserName In this variable you will pass on the username of the current employee in a String data type.
     * @param employeePassword In this variable you will pass on the password of the current employee in a String data type.
     */
    public Employee(int employeeID, String firstNameIn, String lastNameIn, String employeeUserName, String employeePassword) {
        super(firstNameIn, lastNameIn);
        this.employeeID = employeeID;
        this.employeeUserName = employeeUserName;
        this.employeePassword = employeePassword;
    }
    /**
     * Default constructor
     */
    public Employee(){

    }

    //Setters & Getters
    /**
     * 
     * @return This method will return the current employee's ID.
     */
    public int getEmployeeID() {
        return employeeID;
    }
    /**
     * 
     * @param employeeID In this variable you will pass on the integer ID number of the current employee.
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    /**
     * 
     * @return This method will return the current employee's username in s String data type.
     */
    public String getEmployeeUserName() {
        return employeeUserName;
    }
    /**
     * 
     * @param employeeUserName In this variable you will pass on the username of the current employee in a string data type.
     */
    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }
    /**
     * 
     * @return This method will return the current employee's password in s String data type.
     */
    public String getEmployeePassword() {
        return employeePassword;
    }
    /**
     * 
     * @param employeePassword In this variable you will pass on the password of the current employee in a string data type.
     */
    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
    /**
     * 
     * @param isEmployee In this variable you will pass on the boolean value of the current employee.
     */
    public void setEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }
    /**
     * 
     * @return This method will return the boolean value of the current employee if he's an employee.
     */
    public boolean getIsEmployee() {
        return isEmployee;
    }
}
