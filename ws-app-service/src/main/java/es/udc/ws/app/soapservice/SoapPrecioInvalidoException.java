package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapPrecioInvalidoException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapPrecioInvalidoException extends Exception{
	
	public SoapPrecioInvalidoException(float precioDescontado){
		  super("No se puede aumentar el precio descontado [ "+precioDescontado+" ]");
	}
	
	public String getFaultInfo() {
		return getMessage();
	}
	
}
