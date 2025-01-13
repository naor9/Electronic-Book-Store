/*
 This class is responsible for the connection with the web app database, like
 creating a new connection, new CachedRowSet objects to the database with all 
 the info, etc.
 */
package utils;

import java.sql.*;
import constants.constant;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class ConnectionToDatabase implements Serializable {

    /*This method gets a query, and create (and return) a new CachedRowSet object that
    executed that query.*/
    public static CachedRowSet getCachedRowSetFromQuery(String query) {
        try {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.setUrl(constant.DB_URL);
            rowSet.setUsername(constant.DB_USER);
            rowSet.setPassword(constant.DB_PASSWORD);
            rowSet.setCommand(query);
            rowSet.execute();
            try {
                if (!rowSet.isBeforeFirst()) {
                    System.out.println("in 'getCachedRowSetFromQuery', no result was found.");
                }
            } catch (SQLException exception) {
                handleCatchWithSQLException(exception);
            }
            return rowSet;
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
        }
        //Not suppose to be here:
        return null;
    }

    /*This method close the given CachedRowSet*/
    public static void closeCachedRowSet(CachedRowSet rowSet) {
        try {
            rowSet.close();
        } catch (SQLException exception) {
            handleCatchWithSQLException(exception);
        }
    }

    /*This method creates (and return) a new CachedRowSet that is populated 
    with the resultSet this method gets as an input.*/
    public static CachedRowSet getCachedRowSetFromResultSet(ResultSet resultSet) {
        try {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowset = factory.createCachedRowSet();
            rowset.populate(resultSet);
            return rowset;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Not suppose to be here:
        return (CachedRowSet) ConnectionToDatabase.errorInMethod("getCachedRowSetFromResultSet");
    }

    /*This method is used when other method have an error and did not return what it
    was supposed to return in the try...catch. Hence this method print an appropriate error message
    with the name of the method with the error, and return null for that method to return.*/
    private static Connection errorInMethod(String methodName) {
        System.err.println("Error in " + methodName + " method.");
        return null;
    }

    /*This method handle when SQL Exception occurs (in the 'catch')*/
    public static void handleCatchWithSQLException(SQLException exception) {
        System.err.println("SQL Exception occurred!");
        System.err.println("Error Message: " + exception.getMessage());
        System.err.println("SQL State: " + exception.getSQLState());
        System.err.println("Error Code: " + exception.getErrorCode());
        exception.printStackTrace();
    }

}
