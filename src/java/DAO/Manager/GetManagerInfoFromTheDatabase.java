/*
 Here are all the methods that gets info from the 'manager' table in the database.
 */
package DAO.Manager;

import static constants.ConstantsForTablesInTheDatabase.MANAGER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_USER_NAME_COLUMN_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetManagerInfoFromTheDatabase {

    /*This method gets a manager user name and return true if the manager user 
     name exist in the database and false otherwise.*/
    public static boolean checkIfTheManagerUserNameExistInTheDatabase(String managerUserName) {
        String query = "SELECT * FROM " + MANAGER_TABLE_NAME + " WHERE "
                + MANAGER_USER_NAME_COLUMN_NAME + " = ?";
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, managerUserName);
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
