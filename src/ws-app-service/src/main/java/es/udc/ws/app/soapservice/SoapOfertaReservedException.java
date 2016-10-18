package es.udc.ws.app.soapservice;
import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapOfertaReservedException",
    targetNamespace="http://soap.ws.udc.es/" /*esto no sé si es así */
)
public class SoapOfertaReservedException extends Exception {
	private SoapOfertaReservedExceptionInfo faultInfo;

    protected SoapOfertaReservedException(
            SoapOfertaReservedExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapOfertaReservedExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
