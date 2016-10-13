package es.udc.ws.app.exceptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class FechaReservaExpiradaException extends Exception{

	private Calendar fecha;
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


    public FechaReservaExpiradaException(Calendar fecha) {
        super("La fecha de reserva ha expirado");
        
        this.fecha=fecha;
    }

    public Calendar getFecha() {
        return fecha;
    }
    
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
}
