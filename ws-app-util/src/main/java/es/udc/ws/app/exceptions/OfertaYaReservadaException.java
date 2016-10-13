package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class OfertaYaReservadaException extends Exception{

	private Long idOferta;

    public OfertaYaReservadaException(Long idOferta) {
        super("Oferta con id=\"" + idOferta + 
              "\" ya ha sido reservada");
        this.idOferta=idOferta;
    }

    public Long getIdOferta() {
        return idOferta;
    }
    
    public void setIdOferta(Long idOferta) {
        this.idOferta = idOferta;
    }
	
	
	
}
