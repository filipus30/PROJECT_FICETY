/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author macos
 */
public class Validator {
    public boolean isAlpha(String name) {
    char[] chars = name.toCharArray();

    for (char c : chars) {
        if(!Character.isLetter(c)) {
            return false;
        }
    }

    return true;
}
    public boolean isNumber(String text)
    {
       return (text.matches("^[0-9]*$"));             
    }
    
    public static boolean isValidDate(String text) {
    Date date = null;
    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        date = dateFormat.parse(text);
    } catch (ParseException ex) {
       return false;
    }
    if (date == null) {
        return false;
    } else {
       return true;
    }
    }
}

