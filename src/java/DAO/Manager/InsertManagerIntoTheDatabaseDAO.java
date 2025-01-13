/*
 This class is responsible for all the insert into the 'manager' table methods, 
 For example when the manager wants to add a new manager into the database.
 */
package DAO.Manager;

import beans.manager.ManagerBean;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_PASSWORD_COLUMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.MANAGER_USER_NAME_COLUMN_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class InsertManagerIntoTheDatabaseDAO {

    /*This method gets a new "ManagerBean' and insert this new manager data into
     the database.
     This method return true if the insert was successful and false otherwise.*/
    public static synchronized boolean addNewManagerIntoTheDatabase(ManagerBean newManager) {
        String insertWriterQuery = "INSERT INTO " + MANAGER_TABLE_NAME
                + " ("+MANAGER_USER_NAME_COLUMN_NAME+", "+ MANAGER_PASSWORD_COLUMN_NAME+")"
                + " VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement preparedStatementForInsertWriter = connection.prepareStatement(insertWriterQuery);
            preparedStatementForInsertWriter.setString(1, newManager.getManagerUserName());
            preparedStatementForInsertWriter.setString(2, newManager.getManagerPassword());
            preparedStatementForInsertWriter.executeUpdate();
            return true;
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false; // if could't insert the new manager into the database.
        }
    }
}
