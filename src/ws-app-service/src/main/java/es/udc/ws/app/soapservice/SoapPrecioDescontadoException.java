package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapPrecioDescontadoException",
    targetNamespace="http://soap.ws.udc.es/" /*esto no sé si es así */
)
public class SoapPrecioDescontadoException extends Exception {
	
	private SoapPrecioDescontadoExceptionInfo faultInfo;

    protected SoapPrecioDescontadoException(
    		SoapPrecioDescontadoExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapPrecioDescontadoExceptionInfo getFaultInfo() {
        return faultInfo;
    }

}
