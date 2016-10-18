package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;





@SuppressWarnings("serial")
@WebFault(
    name="SoapOfertaReservedByUserException",
    targetNamespace="http://soap.ws.udc.es/" /*esto no sé si es así */
)
public class SoapOfertaReservedByUserException extends Exception {

	private SoapOfertaReservedByUserExceptionInfo faultInfo;

    protected SoapOfertaReservedByUserException(
    		SoapOfertaReservedByUserExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapOfertaReservedByUserExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
