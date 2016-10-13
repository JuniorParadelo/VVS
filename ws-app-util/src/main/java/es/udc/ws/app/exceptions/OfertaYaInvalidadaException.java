package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class OfertaYaInvalidadaException extends Exception{
	
	private Long idOferta;

	
	public OfertaYaInvalidadaException(Long idOferta){
		super("La oferta con idOferta "+idOferta+" ya estaba invalidada");
        this.idOferta=idOferta;
	}


	public Long getIdOferta() {
		return idOferta;
	}


	public void setIdOferta(Long idOferta) {
		this.idOferta = idOferta;
	}
}
