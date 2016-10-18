package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapReservaNoValidaException",
    targetNamespace="http://soap.ws.udc.es/" /*esto no sé si es así */
)
public class SoapReservaNoValidaException extends Exception {

	private SoapReservaNoValidaExceptionInfo faultInfo;

    protected SoapReservaNoValidaException(
    		SoapReservaNoValidaExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapReservaNoValidaExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
