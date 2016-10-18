package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class OfertaReservedException extends Exception {

    private Long ofertaId;

    public OfertaReservedException(Long ofertaId) {
        super("Oferta con id=\"" + ofertaId + 
              "\" contiene reservas");
        this.ofertaId = ofertaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

}