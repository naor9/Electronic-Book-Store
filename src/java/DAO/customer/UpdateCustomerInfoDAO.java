/*
 Here are all the method that handle updating the customer info in the database
 in 'customer' table.
 */
package DAO.customer;

import beans.customer.CustomerBean;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_TABLE_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class UpdateCustomerInfoDAO {

    /*This method gets a new customer info and an old customer info and update 
     the new customer info instead of the old one in the database. 
     This method assume the new customer user name does NOT exist in 'customer'
     table and the new customer is NOT the same as the old customer.*/
    public static synchronized boolean updateCustomer(CustomerBean oldCustomer, CustomerBean newCustomer) {
        String updateCustomerQuery;
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            try {
                /*The next line is to make sure that both the writer first and 
                last name will be updated together or none will be updated (if 
                smething went wrong):*/
                connection.setAutoCommit(false);
                updateCustomerQuery = getQueryForUdpateCustomer();
                PreparedStatement preparedStatementForUpdateCustomer = connection.prepareStatement(updateCustomerQuery);
                setPreparedStatementForUpdateQueryAndAddToBatch(preparedStatementForUpdateCustomer, 
                        oldCustomer.getCustomerUserName(), newCustomer);
                preparedStatementForUpdateCustomer.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException exeption) {
                connection.rollback();
                handleCatchWithSQLException(exeption);
                return false;  // if could't update the database.
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  // if could't make connection to the database.
        }
    }

    /*return a String that is the query for update the customer info in the database.*/
    private static String getQueryForUdpateCustomer() {
        return "UPDATE " + CUSTOMER_TABLE_NAME + " SET "
                +"username = ?, "
                + "password = ?, "
                + "customerfirstName = ?, "
                + "customerlastName = ?, "
                + "address = ?, "
                + "phonenumber = ?, "
                + "creditcardnumber = ?, "
                + "creditcardexpirationdate = ? "
                + "WHERE "
                + "username = ?";
    }

    /*This method set all the info for the PreparedStatement, and add it to the
     batch.*/
    private static void setPreparedStatementForUpdateQueryAndAddToBatch(PreparedStatement preparedStatementForUpdateCustomer, 
            String oldUserName, CustomerBean newCustomer) throws SQLException {
                preparedStatementForUpdateCustomer.setString(1, newCustomer.getCustomerUserName());
                preparedStatementForUpdateCustomer.setString(2, newCustomer.getCustomerPassword());
                preparedStatementForUpdateCustomer.setString(3, newCustomer.getCustomerFirstName());
                preparedStatementForUpdateCustomer.setString(4, newCustomer.getCustomerLastName());
                preparedStatementForUpdateCustomer.setString(5, newCustomer.getAddress());
                preparedStatementForUpdateCustomer.setString(6, newCustomer.getPhoneNumber());
                preparedStatementForUpdateCustomer.setString(7, newCustomer.getCreditCardNumber());
                java.sql.Date sqlDateCreditCardExpirationDate = new java.sql.Date(newCustomer.getCreditCardExpirationDate().getTime());
                preparedStatementForUpdateCustomer.setDate(8, sqlDateCreditCardExpirationDate);
                preparedStatementForUpdateCustomer.setString(9, oldUserName);
                preparedStatementForUpdateCustomer.addBatch();
    }
}
