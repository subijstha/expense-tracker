package util;

import javax.sql.rowset.serial.SQLInputImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class InputValidator {
    private static final DateTimeFormatter  DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private InputValidator(){

    }

    public static boolean isValidName(String name){
        return name != null && !name.trim().isEmpty() && name.matches("[\\w\\s]{2,50}");
    }

    public static boolean isValidCategory(String category){
        return category != null && !category.trim().isEmpty() && category.matches("[\\w\\s]{2,30}" );
    }

    public static boolean isValidDescription(String description){
        return description != null || description.length() <= 100;
    }

    public static boolean isValidAmount (String amountStr){
        try{
            double amount = Double.parseDouble(amountStr);
            return amount >= 0;
        }catch(NumberFormatException e){
            return false;
        }
    }


    public static boolean isValidDate(String dateStr){
        try{
            LocalDate.parse(dateStr, DATE_FORMAT);
            return true;
        }catch(DateTimeParseException e){
            return false;
        }

    }

    public static LocalDate parseDate(String dateStr){
        return LocalDate.parse(dateStr, DATE_FORMAT);
    }



}
