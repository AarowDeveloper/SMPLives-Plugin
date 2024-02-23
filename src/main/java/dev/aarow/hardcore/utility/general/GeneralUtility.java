package dev.aarow.hardcore.utility.general;

public class GeneralUtility {

    public static boolean isNumber(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException exception){
            return false;
        }
    }
}
