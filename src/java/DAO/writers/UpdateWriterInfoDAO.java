/*
 Here are all the method that handle updating of a writer in the database 
 in the 'writers' table
 */
package DAO.writers;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.writer.WriterBean;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_FIRST_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_LAST_NAME;
import static constants.ConstantsForTablesInTheDatabase.WRITER_TABLE_NAME;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class UpdateWriterInfoDAO {

    /*This method gets a new writer info and an old writer info and update the new 
     writer info instead of the old one. 
     This method assume the new writer does NOT exist in 'writers' table and the new writer
     is NOT the same as the old writer.*/
    public static synchronized boolean updateWriter(WriterBean oldWriter, WriterBean newWriter) {
        String updateWriterQuery;
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            try {
                /*The next line is to make sure that both the writer first and 
                last name will be updated together or none will be updated (if 
                smething went wrong):*/
                connection.setAutoCommit(false);
                updateWriterQuery = getQueryForUdpateWriterInWriterTable(newWriter.getWriterFirstName(),
                        newWriter.getWriterLastName(), oldWriter.getWriterFirstName(), oldWriter.getWriterLastName());
                Statement statementForUpdateWriter = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statementForUpdateWriter.addBatch(updateWriterQuery);
                statementForUpdateWriter.executeBatch();
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

    /*return a String that is the query for update a new writer in the database.*/
    private static String getQueryForUdpateWriterInWriterTable(String writerFirstName, String writerLastName,
            String oldWriterFirstName, String oldWriterLastName) {
        writerFirstName = makeEachApostropheIntoTwoApostrophe(writerFirstName);
        writerLastName = makeEachApostropheIntoTwoApostrophe(writerLastName);
        oldWriterFirstName = makeEachApostropheIntoTwoApostrophe(oldWriterFirstName);
        oldWriterLastName = makeEachApostropheIntoTwoApostrophe(oldWriterLastName);
        return "UPDATE " + WRITER_TABLE_NAME + " SET " + IN_WRITERS_TABLE_WRITER_FIRST_NAME
                + " = '" + writerFirstName + "', " + IN_WRITERS_TABLE_WRITER_LAST_NAME
                + " = '" + writerLastName + "' WHERE " + IN_WRITERS_TABLE_WRITER_FIRST_NAME
                + " = " + "'" + oldWriterFirstName + "' AND " + IN_WRITERS_TABLE_WRITER_LAST_NAME
                + " = '" + oldWriterLastName + "'";
    }
}
