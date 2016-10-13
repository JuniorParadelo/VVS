package es.udc.ws.app.soapservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
	    name="SoapFechaInvalidaException",
	    targetNamespace="http://soap.ws.udc.es/"
	)

public class SoapFechaInvalidaException extends Exception{


	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	
	public SoapFechaInvalidaException(Calendar fecha) {
		super("La fecha introducida ["+formato.format(fecha.getTime())+"] es invalida");
	}
	
	public String getFaultInfo() {
		return getMessage();
	}
	
}
