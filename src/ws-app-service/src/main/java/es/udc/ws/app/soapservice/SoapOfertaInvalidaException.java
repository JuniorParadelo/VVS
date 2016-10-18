package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapOfertaInvalidaException",
    targetNamespace="http://soap.ws.udc.es/" /*esto no sé si es así */
)
public class SoapOfertaInvalidaException extends Exception {

	private SoapOfertaInvalidaExceptionInfo faultInfo;

    protected SoapOfertaInvalidaException(
            SoapOfertaInvalidaExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapOfertaInvalidaExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
