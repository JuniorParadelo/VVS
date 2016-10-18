
package es.udc.ws.app.client.service.soap.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OfertsProviderService", targetNamespace = "http://soap.ws.udc.es/", wsdlLocation = "file:/C:/Users/Kike/defensa/ws-app/ws-app-service/target/generated-sources/wsdl/OfertsProviderService.wsdl")
public class OfertsProviderService
    extends Service
{

    private final static URL OFERTSPROVIDERSERVICE_WSDL_LOCATION;
    private final static WebServiceException OFERTSPROVIDERSERVICE_EXCEPTION;
    private final static QName OFERTSPROVIDERSERVICE_QNAME = new QName("http://soap.ws.udc.es/", "OfertsProviderService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Users/Kike/defensa/ws-app/ws-app-service/target/generated-sources/wsdl/OfertsProviderService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        OFERTSPROVIDERSERVICE_WSDL_LOCATION = url;
        OFERTSPROVIDERSERVICE_EXCEPTION = e;
    }

    public OfertsProviderService() {
        super(__getWsdlLocation(), OFERTSPROVIDERSERVICE_QNAME);
    }

    public OfertsProviderService(WebServiceFeature... features) {
        super(__getWsdlLocation(), OFERTSPROVIDERSERVICE_QNAME, features);
    }

    public OfertsProviderService(URL wsdlLocation) {
        super(wsdlLocation, OFERTSPROVIDERSERVICE_QNAME);
    }

    public OfertsProviderService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, OFERTSPROVIDERSERVICE_QNAME, features);
    }

    public OfertsProviderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OfertsProviderService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OfertsProvider
     */
    @WebEndpoint(name = "OfertsProviderPort")
    public OfertsProvider getOfertsProviderPort() {
        return super.getPort(new QName("http://soap.ws.udc.es/", "OfertsProviderPort"), OfertsProvider.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OfertsProvider
     */
    @WebEndpoint(name = "OfertsProviderPort")
    public OfertsProvider getOfertsProviderPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soap.ws.udc.es/", "OfertsProviderPort"), OfertsProvider.class, features);
    }

    private static URL __getWsdlLocation() {
        if (OFERTSPROVIDERSERVICE_EXCEPTION!= null) {
            throw OFERTSPROVIDERSERVICE_EXCEPTION;
        }
        return OFERTSPROVIDERSERVICE_WSDL_LOCATION;
    }

}