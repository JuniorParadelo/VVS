package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class OfertaReservedByUserException extends Exception {

    private Long ofertaId;
    private String email;

    public OfertaReservedByUserException(Long ofertaId, String email) {
        super("Oferta con id=\"" + ofertaId + 
              "\" ya ha sido reservada por el usuario" + email);
        this.ofertaId = ofertaId;
        this.email = email;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public String getEmail() {
        return email;
    }

}