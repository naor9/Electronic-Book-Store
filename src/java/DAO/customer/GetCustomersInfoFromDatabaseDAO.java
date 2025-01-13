/*
Here are the methods that handle getting info on customers from the database.
For example to check if a user name is already exist in the database.
 */
package DAO.customer;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.customer.CustomerBean;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.CUSTOMER_USER_NAME_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_ADDRESS_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_CREDIT_CARD_EXPIRATION_DATE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_CREDIT_CARD_NUMBER_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_CUSTOMER_FIRST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_CUSTOMER_LAST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_PASSWORD_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_CUSTOMER_TABLE_PHONE_NUMBER_COULMN_NUMBER;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetCustomersInfoFromDatabaseDAO {

    /*This method gets a user name and checks if it is already exist in the database.
    If it does exist it return true, otherwise it return false.*/
    public static boolean checkIfUserNameExistInTheDatabase(String userName) {
        CachedRowSet resultUserName = getCachedRowSetFromGivenUserName(userName);
        try {
            if (resultUserName.next()) {
                return true;
            }
        } catch (SQLException sqlException) {
            handleCatchWithSQLException(sqlException);
        }
        return false;
    }

    /*This method gets a user name and set the data on that user name from 
      the database on the given BookBean this method gets.*/
    public static void setTheGivenCustomerBeanWithGivenUserName(String userName, CustomerBean customer) {
        CachedRowSet resultUserName = getCachedRowSetFromGivenUserName(userName);
        try {
            resultUserName.next();
            customer.setCustomerUserName(userName);
            customer.setCustomerPassword(resultUserName.getString(IN_CUSTOMER_TABLE_PASSWORD_COULMN_NUMBER));
            customer.setCustomerFirstName(resultUserName.getString(IN_CUSTOMER_TABLE_CUSTOMER_FIRST_NAME_COULMN_NUMBER));
            customer.setCustomerLastName(resultUserName.getString(IN_CUSTOMER_TABLE_CUSTOMER_LAST_NAME_COULMN_NUMBER));
            customer.setAddress(resultUserName.getString(IN_CUSTOMER_TABLE_ADDRESS_COULMN_NUMBER));
            customer.setPhoneNumber(resultUserName.getString(IN_CUSTOMER_TABLE_PHONE_NUMBER_COULMN_NUMBER));
            customer.setCreditCardNumber(resultUserName.getString(IN_CUSTOMER_TABLE_CREDIT_CARD_NUMBER_COULMN_NUMBER));
            customer.setCreditCardExpirationDate(resultUserName.getTimestamp(IN_CUSTOMER_TABLE_CREDIT_CARD_EXPIRATION_DATE_COULMN_NUMBER));
        } catch (SQLException sqlException) {
            handleCatchWithSQLException(sqlException);
        }
    }

    /*This method gets a user name and return the CachedRowSet with the data from the 
     'customers' table from the database, on that use name.*/
    private static CachedRowSet getCachedRowSetFromGivenUserName(String userName) {
        userName = makeEachApostropheIntoTwoApostrophe(userName);
        String queryToGetUserName = "SELECT * FROM "
                + CUSTOMER_TABLE_NAME + " WHERE " + CUSTOMER_USER_NAME_COLUMN_NAME + " = " + "'" + userName + "'";
        CachedRowSet resultUserName = ConnectionToDatabase.getCachedRowSetFromQuery(queryToGetUserName);
        return resultUserName;
    }
}
