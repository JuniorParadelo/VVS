package es.udc.ws.app.soapservice;



import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapOfertaYaInvalidadaException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapOfertaYaInvalidadaException extends Exception{

	public SoapOfertaYaInvalidadaException(Long idOferta){
		super("La oferta con idOferta "+idOferta+" ya estaba invalidada");
	}
	
	public String getFaultInfo() {
		return getMessage();
	}
	
	
}
