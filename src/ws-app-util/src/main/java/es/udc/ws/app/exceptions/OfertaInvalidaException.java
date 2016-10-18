package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class OfertaInvalidaException extends Exception {

    private Long ofertaId;

    public OfertaInvalidaException(Long ofertaId) {
        super("Oferta con id=\"" + ofertaId + 
              "\" no válida");
        this.ofertaId = ofertaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

   
}