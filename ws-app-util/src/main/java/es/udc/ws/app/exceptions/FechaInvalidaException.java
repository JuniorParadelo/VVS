package es.udc.ws.app.exceptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class FechaInvalidaException extends Exception{

	private Calendar fecha;
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


    public FechaInvalidaException(Calendar fecha) {
        super("No se puede adelantar la fecha limite ["+ formato.format(fecha.getTime())+" ]");
        
        this.fecha=fecha;
    }

    public Calendar getFecha() {
        return fecha;
    }
    
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    
}
