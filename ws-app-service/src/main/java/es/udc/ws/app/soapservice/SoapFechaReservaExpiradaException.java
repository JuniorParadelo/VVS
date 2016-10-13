package es.udc.ws.app.soapservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.ws.WebFault;


@SuppressWarnings("serial")
@WebFault(
	    name="SoapFechaReservaExpiradaException",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapFechaReservaExpiradaException extends Exception{


	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	
	public SoapFechaReservaExpiradaException(Calendar fecha) {
		super("La fecha de reserva ha expirado");
	}
	
	public String getFaultInfo() {
		return getMessage();
	}

}
