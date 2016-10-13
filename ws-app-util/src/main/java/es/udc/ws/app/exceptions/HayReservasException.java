package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class HayReservasException extends Exception{


	private Long idOferta;
	
    public HayReservasException(Long idOferta) {
        super("La oferta "+idOferta+" contiene reservas, no se puede eliminar");
        this.idOferta = idOferta;
    }
    
    
    public Long getIDOferta() {
    	return idOferta;
    }
    
    public void setIDOferta(Long idOferta) {
    	this.idOferta = idOferta;
    }

}
