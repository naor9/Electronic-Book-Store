/*
 Here are the methods that get the lines (orderLIne) from the 'orders' table in the database.
 */
package DAO.orders;

import static StringHelper.StringForSQLQuery.makeEachApostropheIntoTwoApostrophe;
import beans.order.manager.ManagerOrderLineBean;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_BOOK_TITLE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_ORDER_DATE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_ORDER_NUMBER_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_QUANTITY_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_TOTAL_PAYMENT_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_USER_NAME_COULMN_NAME;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_USER_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.ORDER_TABLE_NAME;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.rowset.CachedRowSet;
import utils.ConnectionToDatabase;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class GetOrdersInfoFromTheDatabase {

    /*This method return all the orders in the 'orders' table as an array list.*/
    public static ArrayList<ManagerOrderLineBean> getAllTheOrders() {
        String queryToGetAllOrdes = "SELECT * FROM " + ORDER_TABLE_NAME;
        CachedRowSet resultAllOrders = ConnectionToDatabase.getCachedRowSetFromQuery(queryToGetAllOrdes);
        ArrayList<ManagerOrderLineBean> order = fillArrayListOrderLinesFromQueryResult(resultAllOrders);
        return order;
    }

    /*This method create and return ArrayList<OrderLineBean> from the query result
      this method get.*/
    private static ArrayList<ManagerOrderLineBean> fillArrayListOrderLinesFromQueryResult(CachedRowSet resultAllOrders) {
        ArrayList<ManagerOrderLineBean> order = new ArrayList<ManagerOrderLineBean>();
        ManagerOrderLineBean orderLine;
        try {
            while (resultAllOrders.next()) {
                orderLine = new ManagerOrderLineBean(resultAllOrders.getInt(IN_ORDERS_TABLE_ORDER_NUMBER_COULMN_NUMBER),
                        resultAllOrders.getString(IN_ORDERS_TABLE_USER_NAME_COULMN_NUMBER),
                        resultAllOrders.getString(IN_ORDERS_TABLE_BOOK_TITLE_COULMN_NUMBER),
                        resultAllOrders.getInt(IN_ORDERS_TABLE_QUANTITY_COULMN_NUMBER),
                        resultAllOrders.getDouble(IN_ORDERS_TABLE_TOTAL_PAYMENT_COULMN_NUMBER),
                        resultAllOrders.getDate(IN_ORDERS_TABLE_ORDER_DATE_COULMN_NUMBER));
                order.add(orderLine);
            }
        } catch (SQLException sqlException) {
            handleCatchWithSQLException(sqlException);
        }
        return order;
    }

    /*this method gets a user name and return all the orders of that username in the 
     database in the 'orders' table.*/
    public static ArrayList<ManagerOrderLineBean> getAllTheOrdersOfTheGivenUserName(String customerUserName) {
        String queryToGetAllTheCustomerOrdes = getQueryToGetAllTheCustomerOrders(customerUserName);
        CachedRowSet resultAllTheCustomerOrdes = ConnectionToDatabase.getCachedRowSetFromQuery(queryToGetAllTheCustomerOrdes);
        ArrayList<ManagerOrderLineBean> order = fillArrayListOrderLinesFromQueryResult(resultAllTheCustomerOrdes);
        return order;
    }

    /*This method return a String that is the query to get all the given user name 
     orders from the database from the 'orders' table.*/
    private static String getQueryToGetAllTheCustomerOrders(String customerUserName) {
        customerUserName = makeEachApostropheIntoTwoApostrophe(customerUserName);
        return "SELECT * FROM " + ORDER_TABLE_NAME +
                " WHERE "+IN_ORDERS_TABLE_USER_NAME_COULMN_NAME + " = " + "'" + customerUserName +"'";
    }
}
