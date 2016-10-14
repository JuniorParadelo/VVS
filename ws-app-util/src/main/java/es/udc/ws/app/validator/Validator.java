package es.udc.ws.app.validator;

import java.util.Calendar;

import es.udc.ws.util.exceptions.InputValidationException;

public final class Validator {

    private Validator() {}

    public static void validateNotNegativeFloat(String propertyName,
            float value) throws InputValidationException {

        if (value < 0) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than 0 : " + value);
        }

    }
    
    public static void validateFutureDate(String propertyName,Calendar date) throws InputValidationException{
    	Calendar hoy= Calendar.getInstance();
    	
    	if (date.before(hoy)){
            throw new InputValidationException("Invalid "+propertyName+": must be future date");
    	}
    }
}