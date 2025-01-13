/*
 DAO for the log in of a manager or a customer. It is responsible to check if the log in is valid 
or not
 */
package DAO.logIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.constant;
import java.sql.DriverManager;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class LogInDAO {

    /*return true if the given 'userName' and 'password' is in the database 
      in the given 'tableName', and false otherwise.*/
    public static boolean isTheUserNameAndPasswordExistInTheDatabase(String userName, String password, 
            String tableName, String userNameColumnName, String passwordColumnName) {
        String query = "SELECT * FROM " + tableName + " WHERE "
                + userNameColumnName + " = ? AND " + passwordColumnName + " = ?";
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
        }
        return false;
    }
}
