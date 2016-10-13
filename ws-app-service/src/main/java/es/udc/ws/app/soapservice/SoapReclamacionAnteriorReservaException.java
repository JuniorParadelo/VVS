package es.udc.ws.app.soapservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapReclamacionAnteriorReserva",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapReclamacionAnteriorReservaException extends Exception{
	

	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
    public SoapReclamacionAnteriorReservaException(Calendar fechaLimReclamacion,Calendar fechaLimReserva) {
    	super("La fecha de Reclamacion [ "+formato.format(fechaLimReclamacion.getTime())+" ] no puede ser anterior a la de Reserva [ "+formato.format(fechaLimReserva.getTime())+" ]");
              
    }
	
    public String getFaultInfo() {
    	return getMessage();
    }
	
	
	
	
}
