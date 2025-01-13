/*
  Here are all the methods that will help with the objects that represent Date.
 */
package DateHelper;

import static constants.constant.MAX_EXPIRATION_DATE_FOR_CREDIT_CARD;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;


public class DateChecker {

    /*The next method return true if the given credit card expiration date 
    is more then the maximum possible expiration date, and false otherwise.*/
    public static boolean isTheGivenDateIsMoreThanTheMaximumPossibleExpirationDate(Date creditCardExpirationDate) {
        /*make the current date and the given expiration date a YearMonth object
        (the format of a credit card expiration date):*/
        YearMonth currentYearMonth = getCurrentYearMonth();
        YearMonth creditCardExpirationDateYearMonth = getCreditCardExpirationDateAsYearMonth(creditCardExpirationDate);
        /*The next if is true if the credit card expiration date can't be legit
        since its expiration date is more then the maximum possible expiration date:*/
        if (currentYearMonth.isBefore(creditCardExpirationDateYearMonth.minusYears(MAX_EXPIRATION_DATE_FOR_CREDIT_CARD))) {
            return true;
        }
        return false;
    }


    /*The next method return true if the given credit card expiration date had 
      expired, and false otherwise.*/
    public static boolean isTheGivenDateObjectDateHadPassed(Date creditCardExpirationDate) {
        /*make the current date and the given expiration date a YearMonth object
        (the format of a credit card expiration date):*/
        YearMonth currentYearMonth = getCurrentYearMonth();
        YearMonth creditCardExpirationDateYearMonth = getCreditCardExpirationDateAsYearMonth(creditCardExpirationDate);
        /*If the next 'if' is true then the credit card is exipred:*/
        if (currentYearMonth.isAfter(creditCardExpirationDateYearMonth)) {
            return true;
        }
        return false;
    }

    /*return a 'YearMonth' object that reresent the current month and year (at the moment of creation).*/
    private static YearMonth getCurrentYearMonth() {
        LocalDate currentDate = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(currentDate);
        return currentYearMonth;
    }

    /*This method gets 'Data' object and return it as a 'YearMonth' object.*/
    private static YearMonth getCreditCardExpirationDateAsYearMonth(Date creditCardExpirationDate) {
        //The next line make the input Date object into LocalDate object:
        LocalDate localDateCreditCardExpirationDate = creditCardExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth creditCardExpirationDateYearMonth = YearMonth.from(localDateCreditCardExpirationDate);
        return creditCardExpirationDateYearMonth;
    }

}
