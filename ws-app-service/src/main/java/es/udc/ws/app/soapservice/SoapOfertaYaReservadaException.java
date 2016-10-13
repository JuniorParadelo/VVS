package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapOfertaYaReservadaException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapOfertaYaReservadaException extends Exception{
	
	
	public SoapOfertaYaReservadaException(Long idOferta){
		super("La oferta con idOferta "+idOferta+" ya estaba reservada");
	}
	
	
	public String getFaultInfo() {
		return getMessage();
	}
	
	
}
