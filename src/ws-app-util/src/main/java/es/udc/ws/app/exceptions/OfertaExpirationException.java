package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class OfertaExpirationException extends Exception {

    private Long ofertaId;
    private Calendar fechaReservar;

    public OfertaExpirationException(Long ofertaId, Calendar fechaReservar) {
        super("Oferta con id=\"" + ofertaId + 
              "\" ha expirado (Fecha límite para reservar = \"" + 
              fechaReservar + "\")");
        this.ofertaId = ofertaId;
        this.fechaReservar = fechaReservar;
    }

	public Long getOfertaId() {
		return ofertaId;
	}

	public Calendar getFechaReservar() {
		return fechaReservar;
	}
    
}