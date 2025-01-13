/*
Here are the methods that handle getting info on writers from the database.
For example to get all the info on all the writers in the database.
 */
package DAO.writers;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.writer.WriterBean;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_FIRST_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_LAST_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_WRITERS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.WRITER_TABLE_NAME;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetWritersInfoFromDatabase {

    /*This method gets nothing and return an ArrayList of all the writers in
     the database.*/
    public static ArrayList<WriterBean> getAllTheWritersInTheDatabase() {
        String queryAllWriters = "SELECT * FROM " + WRITER_TABLE_NAME;
        ArrayList<WriterBean> writers = new ArrayList<WriterBean>();
        WriterBean tempWriter;
        CachedRowSet resultAllWriters = ConnectionToDatabase.getCachedRowSetFromQuery(queryAllWriters);
        try {
            while (resultAllWriters.next()) {
                tempWriter = new WriterBean(resultAllWriters.getString(IN_WRITERS_TABLE_WRITER_FIRST_NAME_COULMN_NUMBER),
                        resultAllWriters.getString(IN_WRITERS_TABLE_WRITER_LAST_NAME_COULMN_NUMBER));
                writers.add(tempWriter);
            }
            return writers;
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
        }
        //if got here then there is an error:
        return writers;
    }

    /*This method return true if the given writer is in the database, 
     and false otherwise*/
    public static boolean checkIfWriterExistInTheDatabase(String writerFirstName, String writerLastName) {
        writerFirstName = makeEachApostropheIntoTwoApostrophe(writerFirstName);
        writerLastName = makeEachApostropheIntoTwoApostrophe(writerLastName);
        String queryToGetWriter = "SELECT * FROM " + WRITER_TABLE_NAME + " WHERE "
                + IN_WRITERS_TABLE_WRITER_FIRST_NAME + " = " + "'" + writerFirstName + "' AND "
                + IN_WRITERS_TABLE_WRITER_LAST_NAME + " = " + "'" + writerLastName + "'";
        CachedRowSet resultWriter = ConnectionToDatabase.getCachedRowSetFromQuery(queryToGetWriter);
        try {
            if (resultWriter.next()) {
                return true;
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;
        }
        return false;
    }
}
