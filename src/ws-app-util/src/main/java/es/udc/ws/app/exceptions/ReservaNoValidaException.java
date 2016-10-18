package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class ReservaNoValidaException extends Exception {

    private Long reservaId;

    public ReservaNoValidaException(Long reservaId) {
        super("Reserva con id=\"" + reservaId + 
              "\" no válida");
        this.reservaId = reservaId;
    }

    public Long getReservaId() {
        return reservaId;
    }

   
}