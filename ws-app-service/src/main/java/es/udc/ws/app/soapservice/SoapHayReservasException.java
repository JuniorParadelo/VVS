package es.udc.ws.app.soapservice;


import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapHayReservasException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapHayReservasException extends Exception{

	public SoapHayReservasException(Long idOferta) {
		super("La oferta "+idOferta+" tiene reservas asignadas, no se puede eliminar");
	}
	
	public String getFaultInfo() {
		return getMessage();
	}

}
