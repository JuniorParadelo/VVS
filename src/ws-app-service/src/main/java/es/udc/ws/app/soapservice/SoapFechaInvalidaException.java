package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapFechaInvalidaException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapFechaInvalidaException extends Exception {

	public SoapFechaInvalidaException() {
        super("No se permite adelantar fecha y hora límite para disfrutar la oferta");
    }
    
    public String getFaultInfo() {
        return getMessage();
    } 
}
