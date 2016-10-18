package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ReservaExpirationException extends Exception {

    private Long reservaId;
    private Calendar fecha_limite;

    public ReservaExpirationException(Long reservaId, Calendar fecha_limite) {
        super("Reserva con id=\"" + reservaId + 
              "\" ha expirado (Fecha límite = \"" + 
              fecha_limite + "\")");
        this.reservaId = reservaId;
        this.fecha_limite = fecha_limite;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public Calendar getFechaLimite() {
        return fecha_limite;
    }

    public void setFechaLimite(Calendar fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }
}