package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class FechaInvalidaException extends Exception {

    public FechaInvalidaException() {
        super("No se permite adelantar fecha y hora límite para disfrutar la oferta");
    }
    
}