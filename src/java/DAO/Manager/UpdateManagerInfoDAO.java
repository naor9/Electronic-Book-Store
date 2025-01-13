/*
  Here are all the method that handle updating of manager table in the database
 */
package DAO.Manager;


import static constants.ConstantsForTablesInTheDatabase.MANAGER_PASSWORD_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_USER_NAME_COLUMN_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class UpdateManagerInfoDAO {

    /*This method gets a new manager password and the manager username, and change
     the manager password in the database (in the 'manager' table) into this new 
     password. 
     This method return true if it succeded and false otherwise. 
     This method assume the new password is diffrent than the last one.
     The 'synchronized' is to make sure that the password isn't changed by another
     person at the same time*/
    public static synchronized boolean updateTheManagerPassworInTheDatabase(String managerUserName,
            String newPassword) {
        String updatePasswordQuery = getQueryForUpdateTheManagerPassword();
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            try {
                PreparedStatement preparedStatementForUpdatePassword = connection.prepareStatement(updatePasswordQuery);
                setPreparedStatementForUpdateManagerPassword(preparedStatementForUpdatePassword, managerUserName, newPassword);
                preparedStatementForUpdatePassword.executeUpdate();
                return true;
            } catch (SQLException exeption) {
                handleCatchWithSQLException(exeption);
                return false;  // if could't update the database.
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  // if could't make connection to the database.
        }
    }
    
    /*return a String that is the query for update the new manager password in the database.*/
    private static String getQueryForUpdateTheManagerPassword() {
        return "UPDATE " + MANAGER_TABLE_NAME 
                + " SET " + MANAGER_PASSWORD_COLUMN_NAME + " = ? "
                + "WHERE " + MANAGER_USER_NAME_COLUMN_NAME + " = ?";
    }

    /*This method set all the info for the PreparedStatement.*/
    private static void setPreparedStatementForUpdateManagerPassword(
            PreparedStatement preparedStatementForUpdatePassword, String managerUserName, String newPassword)
            throws SQLException {
                preparedStatementForUpdatePassword.setString(1, newPassword);
                preparedStatementForUpdatePassword.setString(2, managerUserName);
    }
}
