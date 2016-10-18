
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "SoapPrecioDescontadoException", targetNamespace = "http://soap.ws.udc.es/")
public class SoapPrecioDescontadoException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SoapPrecioDescontadoExceptionInfo faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public SoapPrecioDescontadoException(String message, SoapPrecioDescontadoExceptionInfo faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public SoapPrecioDescontadoException(String message, SoapPrecioDescontadoExceptionInfo faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: es.udc.ws.app.client.service.soap.wsdl.SoapPrecioDescontadoExceptionInfo
     */
    public SoapPrecioDescontadoExceptionInfo getFaultInfo() {
        return faultInfo;
    }

}