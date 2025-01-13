/*
 Here are all the methods that will help with making a String suitable for SQL
 queries.
 */
package StringHelper;

public class StringForSQLQuery {

    /*This method make every:  '  in the given String into:  ''
      *This method make sure that any given string can be used in a Statment query*/
    public static String makeEachApostropheIntoTwoApostrophe(String inputString) {
        if (inputString == null) {
            return null;
        }
        return inputString.replace("'", "''");
    }
}
