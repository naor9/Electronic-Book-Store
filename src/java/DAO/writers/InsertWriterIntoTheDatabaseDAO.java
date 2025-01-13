/*
 Here are all the method that handle insert a new writer into the database
 */
package DAO.writers;

import beans.writer.WriterBean;
import static constants.ConstantsForTablesInTheDatabase.WRITER_TABLE_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class InsertWriterIntoTheDatabaseDAO {

    /*This method insert a new given writer into the database.
     It assumes the data of the new given writer is correct.
     It return true if it succeeded and false otherwise.*/
    public static synchronized boolean insertANewWriterIntoTheDatabase(WriterBean newWriter) {
        String insertWriterQuery = "INSERT INTO " + WRITER_TABLE_NAME
                + " (writerfirstname, writerlastname)"
                + " VALUES (?, ?)";
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            PreparedStatement preparedStatementForInsertWriter = connection.prepareStatement(insertWriterQuery);
            preparedStatementForInsertWriter.setString(1, newWriter.getWriterFirstName());
            preparedStatementForInsertWriter.setString(2, newWriter.getWriterLastName());
            preparedStatementForInsertWriter.executeUpdate();
            return true;
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  //  // if could't insert the writer into the database.
        }
    }
}
