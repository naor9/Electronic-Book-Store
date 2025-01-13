/*
 This class is responsible for all the insert into the order table DAO methods, 
 for example when the customer wants to buy a book and click the "check out".
 This class make sure that there is enougth quantity in the store for the transaction,
 and that all the info is locked for other customers while one customer is checking out. 
 It insert a new orders into 'orders' table, and update the quantity in the store for
 the 'books' table.
 */
package DAO.orders;

import DAO.books.GetBookInfoFromDatabaseDAO;
import beans.order.customer.CustomerOrderLineBean;
import beans.order.customer.OrderForCustomerBean;
import static constants.ConstantsForTablesInTheDatabase.BOOKS_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.BOOK_TITLE;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_BOOK_TITLE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_ORDER_DATE_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_QUANTITY_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_TOTAL_PAYMENT_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.IN_ORDERS_TABLE_USER_NAME_COULMN_NUMBER;
import static constants.ConstantsForTablesInTheDatabase.ORDER_TABLE_NAME;
import static constants.ConstantsForTablesInTheDatabase.QUANTITY_IN_THE_STORE;
import constants.constant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import static utils.ConnectionToDatabase.handleCatchWithSQLException;

public class InsertOrderIntoTheDatabaseDAO {

    /*This class is responsible for adding a new Order to the database.
    The database saves each order as seperates lines for each book that was bought and return true,
    but only if the quantity in the store for each book is enough for the new order, otherwise
    it return false.
    This method DOES NOT change the new totalPayment of all the order, it should be 
    done in the 'OrderForCustomerBean' (that this method gets)*/
    public static synchronized boolean addOrderToTheDatabaseIfLegit(String userName, OrderForCustomerBean customerOrderInfo) {
        try {
            Connection connection = DriverManager.getConnection(constant.DB_URL, constant.DB_USER, constant.DB_PASSWORD);
            try {
                //The next line is to check if the quantity in the store is enogth for the transaction:
                if (!CheckIfQuantityInTheOrderIsLegit(customerOrderInfo)) {
                    return false;
                }
                connection.setAutoCommit(false);
                executePreparedStatementForBooksUpdateCommand(connection, customerOrderInfo.getOrder());
                executePreparedStatementForOrderInsertCommand(connection, userName, customerOrderInfo.getOrder());
                connection.commit();
                return true;
            } catch (SQLException exeption) {
                handleCatchWithSQLException(exeption);
                connection.rollback();
                return false;  // if could't update the database.
            }
        } catch (SQLException exeption) {
            handleCatchWithSQLException(exeption);
            return false;  // if could't make connection to the database.
        }
    }

    //******************************************************************************************************************************
    //******************************************************************************************************************************
    //******************************************************************************************************************************
    //the next methods are for handelling the 'books' table queries for the new added order:

    /*This method is part of the construction of the query that update the quantity of the books in the store 
    after order transaction and udpate the order in the database. 
    This method creates and execute a PreparedStatement for update 'QuantityInTheStore' in the 'books' table
    after a customer purchase book/s.
    This method assumes there is at least 1 book in the order.*/
    private static synchronized void executePreparedStatementForBooksUpdateCommand(Connection connection,
            ArrayList<CustomerOrderLineBean> order) throws SQLException {
        String updateBookQuery = "UPDATE " + BOOKS_TABLE_NAME + " SET "
                + QUANTITY_IN_THE_STORE + " = " + QUANTITY_IN_THE_STORE + " -  ? WHERE "
                + BOOK_TITLE + " = ?";
        PreparedStatement preparedStatementForOrderTable = connection.prepareStatement(updateBookQuery);
        for (int i = 0; i < order.size(); i++) {
            //add update command for orderLine i:
            preparedStatementForOrderTable.setInt(1, order.get(i).getQuantity());
            preparedStatementForOrderTable.setString(2, order.get(i).getBookTitle());
            preparedStatementForOrderTable.executeUpdate();
        }
    }

    //******************************************************************************************************************************
    //******************************************************************************************************************************
    //******************************************************************************************************************************
    //the next method is for handelling the insert command into the 'orders' table:
    /*This method creates and execute a PreparedStatement for update the 'orders' table.
    The PreparedStatement is for the insert of the given orders Lines Beans into 'orders' table.*/
    private static synchronized void executePreparedStatementForOrderInsertCommand(Connection connection,
            String userName, ArrayList<CustomerOrderLineBean> order) throws SQLException {
        String insertQuery = "INSERT INTO " + ORDER_TABLE_NAME
                + " (UserName, BookTitle, Quantity, TotalPayment, OrderDate) VALUES (?, ?, ?, ?, ?)";
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        PreparedStatement preparedStatementForOrderTable = connection.prepareStatement(insertQuery);
        for (int i = 0; i < order.size(); i++) {
            /*All the next 'sets' are with '-1' in the column because in the query we
             skeep on the first column: the 'OrderNumber' column.*/
            preparedStatementForOrderTable.setString(IN_ORDERS_TABLE_USER_NAME_COULMN_NUMBER - 1, userName);
            preparedStatementForOrderTable.setString(IN_ORDERS_TABLE_BOOK_TITLE_COULMN_NUMBER - 1, order.get(i).getBookTitle());
            preparedStatementForOrderTable.setInt(IN_ORDERS_TABLE_QUANTITY_COULMN_NUMBER - 1, order.get(i).getQuantity());
            preparedStatementForOrderTable.setDouble(IN_ORDERS_TABLE_TOTAL_PAYMENT_COULMN_NUMBER - 1, order.get(i).getTotalPayment());
            preparedStatementForOrderTable.setTimestamp(IN_ORDERS_TABLE_ORDER_DATE_COULMN_NUMBER - 1, currentTimestamp);
            preparedStatementForOrderTable.executeUpdate();
        }
    }

    /*This method checks when the customer wanted to check out, if he bought a book
    that its quantity is higher then the quantity of that book in the store.
     If he does this method return true, and return false otherwise.
     This method DOES NOT change the new totalPayment of all the order, it should be 
     done in the 'OrderForCustomerBean'.*/
    private static boolean CheckIfQuantityInTheOrderIsLegit(OrderForCustomerBean customerOrderInfo) {
        int qurrentQuantityInTheStore;
        for (CustomerOrderLineBean orderLine : customerOrderInfo.getOrder()) {
            qurrentQuantityInTheStore = GetBookInfoFromDatabaseDAO.getQuantityInTheStore(orderLine.getBookTitle());
            //if the next line is true then the quantity in the store of the book had changed:(now is lower)
            if (qurrentQuantityInTheStore < orderLine.getQuantity()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Book '" + orderLine.getBookTitle() + "' have only " + orderLine.getQuantity()
                        + " books left in stock. Please note the quantity in your order had changed.", null));
                //update the quantity in the store:
                orderLine.setQuantityInTheStore(qurrentQuantityInTheStore);
                orderLine.setQuantity(qurrentQuantityInTheStore);
                return false;
            }
        }
        return true;
    }

}
