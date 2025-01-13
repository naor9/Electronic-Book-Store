/*
Here are all the DAO for handelling the customer register.
 */
package DAO.customer;

import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_TABLE_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class RegisterNewCustomerDAO {

    /*This methods gets and add the new customer to the database and checks if 
    the customer was added successfully, and if so return true, otherwise 
    return false.*/
    public static synchronized boolean addNewCustomerToTheDatabase(String customerUserName,
            String customerPassword, String customerFirstName, String customerLastName,
            String address, String phoneNumber, String creditCardNumber, Date creditCardExpirationDate) {
        /*'rowsAffected' is the number of rows that was changed(suppose to be 1 
          after executeUpdate):*/
        int rowsAffected;
        String insertQuery = "INSERT INTO " + CUSTOMER_TABLE_NAME
                + " (UserName, Password, CustomerFirstName, CustomerLastName, Address,"
                + "PhoneNumber, CreditCardNumber, CreditCardExpirationDate)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement preparedStatementForNewCustomer = connection.prepareStatement(insertQuery);
            preparedStatementForNewCustomer.setString(1, customerUserName);
            preparedStatementForNewCustomer.setString(2, customerPassword);
            preparedStatementForNewCustomer.setString(3, customerFirstName);
            preparedStatementForNewCustomer.setString(4, customerLastName);
            preparedStatementForNewCustomer.setString(5, address);
            preparedStatementForNewCustomer.setString(6, phoneNumber);
            preparedStatementForNewCustomer.setString(7, creditCardNumber);
            java.sql.Date sqlDateCreditCardExpirationDate = new java.sql.Date(creditCardExpirationDate.getTime());
            preparedStatementForNewCustomer.setDate(8, sqlDateCreditCardExpirationDate);
            rowsAffected = preparedStatementForNewCustomer.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
            return false;
        }
        return false; /*If the new customer didn't add to the database*/
    }
}