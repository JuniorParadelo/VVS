package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
		name="SoapYaReclamadaException",
	    targetNamespace="http://soap.ws.udc.es/")

public class SoapYaReclamadaException extends Exception{

	public SoapYaReclamadaException(Long codigo){
		super("La reserva con el codigo "+codigo+" ya estaba reclamada");		
	}
	
	public String getFaultInfo() {
		return getMessage();
	}
	
	
}
