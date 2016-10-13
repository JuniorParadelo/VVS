package es.udc.ws.app.exceptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class ReclamacionAnteriorReservaException extends Exception{

	private Calendar fechaLimReserva;
	private Calendar fechaLimReclamacion;
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	
    public ReclamacionAnteriorReservaException(Calendar fechaLimReclamacion,Calendar fechaLimReserva) {
        super("La fecha de Reclamacion [ "+formato.format(fechaLimReclamacion.getTime())+" ] no puede ser anterior a la de Reserva [ "+formato.format(fechaLimReserva.getTime())+" ]");
        
        this.fechaLimReserva=fechaLimReserva;
        this.fechaLimReclamacion=fechaLimReclamacion;        
    }

	public Calendar getFechaLimReserva() {
		return fechaLimReserva;
	}

	public void setFechaLimReserva(Calendar fechaLimReserva) {
		this.fechaLimReserva = fechaLimReserva;
	}

	public Calendar getFechaLimReclamacion() {
		return fechaLimReclamacion;
	}

	public void setFechaLimReclamacion(Calendar fechaLimReclamacion) {
		this.fechaLimReclamacion = fechaLimReclamacion;
	}
	
	
	
	
	
	
}
