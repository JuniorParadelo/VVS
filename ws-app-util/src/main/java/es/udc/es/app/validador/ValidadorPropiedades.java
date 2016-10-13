package es.udc.es.app.validador;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.util.exceptions.InputValidationException;


public class ValidadorPropiedades {

	private final static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	public static void validateFloat(String propertyName,
            float value, int lowerValidLimit, int upperValidLimit)
            throws InputValidationException {

        if ( (value < lowerValidLimit) || (value > upperValidLimit) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be greater than " + lowerValidLimit +
                    " and lower than " + upperValidLimit + "): " + value);
        }
    }
	
	public static void validateEstado(String propertyName,
			String value) throws InputValidationException {
		
		if ( value==null || !value.equals("PENDIENTE") || !value.equals("RECLAMADA") || !value.equals("ANULADA") ) {
			throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be PENDIENTE,RECLAMADA or ANULADA)");
			
		}
	}
	
	public static void validateFutureDate(String propertyName,Calendar propertyValue) throws InputValidationException {

        Calendar now = Calendar.getInstance();
        if ( (propertyValue == null) || (propertyValue.before(now)) ) {
            throw new InputValidationException("Invalid " + propertyName +
                    " value (it must be a future date): " +
                    formato.format(propertyValue.getTime() ) );
        }

   }
	
	
}
