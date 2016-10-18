
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.ws.app.client.service.soap.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SoapReservaExpirationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapReservaExpirationException");
    private final static QName _ReclamarReservaResponse_QNAME = new QName("http://soap.ws.udc.es/", "reclamarReservaResponse");
    private final static QName _SoapPrecioDescontadoException_QNAME = new QName("http://soap.ws.udc.es/", "SoapPrecioDescontadoException");
    private final static QName _BuscarReservaResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservaResponse");
    private final static QName _SoapOfertaInvalidaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaInvalidaException");
    private final static QName _InvalidarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOfertaResponse");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _SoapOfertaReservedException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaReservedException");
    private final static QName _BuscarReservasDeUnUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservasDeUnUsuarioResponse");
    private final static QName _ReservarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "reservarOfertaResponse");
    private final static QName _SoapOfertaExpirationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaExpirationException");
    private final static QName _SoapOfertaReservedByUserException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaReservedByUserException");
    private final static QName _BuscarReserva_QNAME = new QName("http://soap.ws.udc.es/", "buscarReserva");
    private final static QName _BuscarOferta_QNAME = new QName("http://soap.ws.udc.es/", "buscarOferta");
    private final static QName _EliminarOferta_QNAME = new QName("http://soap.ws.udc.es/", "eliminarOferta");
    private final static QName _EliminarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "eliminarOfertaResponse");
    private final static QName _SoapReservaNoValidaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapReservaNoValidaException");
    private final static QName _InvalidarOferta_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOferta");
    private final static QName _BuscarReservasDeUnaOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservasDeUnaOfertaResponse");
    private final static QName _BuscarOfertasReservadasDeUnUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasReservadasDeUnUsuarioResponse");
    private final static QName _SoapFechaInvalidaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapFechaInvalidaException");
    private final static QName _ReservarOferta_QNAME = new QName("http://soap.ws.udc.es/", "reservarOferta");
    private final static QName _ActualizarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOfertaResponse");
    private final static QName _ActualizarOferta_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOferta");
    private final static QName _BuscarReservasDeUnUsuario_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservasDeUnUsuario");
    private final static QName _BuscarOfertasResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasResponse");
    private final static QName _BuscarOfertas_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertas");
    private final static QName _BuscarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaResponse");
    private final static QName _AnadirOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "anadirOfertaResponse");
    private final static QName _BuscarOfertasReservadasDeUnUsuario_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasReservadasDeUnUsuario");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
    private final static QName _AnadirOferta_QNAME = new QName("http://soap.ws.udc.es/", "anadirOferta");
    private final static QName _BuscarReservasDeUnaOferta_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservasDeUnaOferta");
    private final static QName _ReclamarReserva_QNAME = new QName("http://soap.ws.udc.es/", "reclamarReserva");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.app.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link SoapOfertaReservedExceptionInfo }
     * 
     */
    public SoapOfertaReservedExceptionInfo createSoapOfertaReservedExceptionInfo() {
        return new SoapOfertaReservedExceptionInfo();
    }

    /**
     * Create an instance of {@link BuscarReservasDeUnUsuarioResponse }
     * 
     */
    public BuscarReservasDeUnUsuarioResponse createBuscarReservasDeUnUsuarioResponse() {
        return new BuscarReservasDeUnUsuarioResponse();
    }

    /**
     * Create an instance of {@link ReservarOfertaResponse }
     * 
     */
    public ReservarOfertaResponse createReservarOfertaResponse() {
        return new ReservarOfertaResponse();
    }

    /**
     * Create an instance of {@link SoapOfertaExpirationExceptionInfo }
     * 
     */
    public SoapOfertaExpirationExceptionInfo createSoapOfertaExpirationExceptionInfo() {
        return new SoapOfertaExpirationExceptionInfo();
    }

    /**
     * Create an instance of {@link SoapOfertaReservedByUserExceptionInfo }
     * 
     */
    public SoapOfertaReservedByUserExceptionInfo createSoapOfertaReservedByUserExceptionInfo() {
        return new SoapOfertaReservedByUserExceptionInfo();
    }

    /**
     * Create an instance of {@link BuscarReserva }
     * 
     */
    public BuscarReserva createBuscarReserva() {
        return new BuscarReserva();
    }

    /**
     * Create an instance of {@link BuscarOferta }
     * 
     */
    public BuscarOferta createBuscarOferta() {
        return new BuscarOferta();
    }

    /**
     * Create an instance of {@link EliminarOferta }
     * 
     */
    public EliminarOferta createEliminarOferta() {
        return new EliminarOferta();
    }

    /**
     * Create an instance of {@link EliminarOfertaResponse }
     * 
     */
    public EliminarOfertaResponse createEliminarOfertaResponse() {
        return new EliminarOfertaResponse();
    }

    /**
     * Create an instance of {@link SoapReservaNoValidaExceptionInfo }
     * 
     */
    public SoapReservaNoValidaExceptionInfo createSoapReservaNoValidaExceptionInfo() {
        return new SoapReservaNoValidaExceptionInfo();
    }

    /**
     * Create an instance of {@link ReclamarReservaResponse }
     * 
     */
    public ReclamarReservaResponse createReclamarReservaResponse() {
        return new ReclamarReservaResponse();
    }

    /**
     * Create an instance of {@link SoapReservaExpirationExceptionInfo }
     * 
     */
    public SoapReservaExpirationExceptionInfo createSoapReservaExpirationExceptionInfo() {
        return new SoapReservaExpirationExceptionInfo();
    }

    /**
     * Create an instance of {@link BuscarReservaResponse }
     * 
     */
    public BuscarReservaResponse createBuscarReservaResponse() {
        return new BuscarReservaResponse();
    }

    /**
     * Create an instance of {@link SoapPrecioDescontadoExceptionInfo }
     * 
     */
    public SoapPrecioDescontadoExceptionInfo createSoapPrecioDescontadoExceptionInfo() {
        return new SoapPrecioDescontadoExceptionInfo();
    }

    /**
     * Create an instance of {@link SoapOfertaInvalidaExceptionInfo }
     * 
     */
    public SoapOfertaInvalidaExceptionInfo createSoapOfertaInvalidaExceptionInfo() {
        return new SoapOfertaInvalidaExceptionInfo();
    }

    /**
     * Create an instance of {@link InvalidarOfertaResponse }
     * 
     */
    public InvalidarOfertaResponse createInvalidarOfertaResponse() {
        return new InvalidarOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertas }
     * 
     */
    public BuscarOfertas createBuscarOfertas() {
        return new BuscarOfertas();
    }

    /**
     * Create an instance of {@link AnadirOfertaResponse }
     * 
     */
    public AnadirOfertaResponse createAnadirOfertaResponse() {
        return new AnadirOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertaResponse }
     * 
     */
    public BuscarOfertaResponse createBuscarOfertaResponse() {
        return new BuscarOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertasReservadasDeUnUsuario }
     * 
     */
    public BuscarOfertasReservadasDeUnUsuario createBuscarOfertasReservadasDeUnUsuario() {
        return new BuscarOfertasReservadasDeUnUsuario();
    }

    /**
     * Create an instance of {@link AnadirOferta }
     * 
     */
    public AnadirOferta createAnadirOferta() {
        return new AnadirOferta();
    }

    /**
     * Create an instance of {@link BuscarReservasDeUnaOferta }
     * 
     */
    public BuscarReservasDeUnaOferta createBuscarReservasDeUnaOferta() {
        return new BuscarReservasDeUnaOferta();
    }

    /**
     * Create an instance of {@link ReclamarReserva }
     * 
     */
    public ReclamarReserva createReclamarReserva() {
        return new ReclamarReserva();
    }

    /**
     * Create an instance of {@link InvalidarOferta }
     * 
     */
    public InvalidarOferta createInvalidarOferta() {
        return new InvalidarOferta();
    }

    /**
     * Create an instance of {@link BuscarReservasDeUnaOfertaResponse }
     * 
     */
    public BuscarReservasDeUnaOfertaResponse createBuscarReservasDeUnaOfertaResponse() {
        return new BuscarReservasDeUnaOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertasReservadasDeUnUsuarioResponse }
     * 
     */
    public BuscarOfertasReservadasDeUnUsuarioResponse createBuscarOfertasReservadasDeUnUsuarioResponse() {
        return new BuscarOfertasReservadasDeUnUsuarioResponse();
    }

    /**
     * Create an instance of {@link ActualizarOfertaResponse }
     * 
     */
    public ActualizarOfertaResponse createActualizarOfertaResponse() {
        return new ActualizarOfertaResponse();
    }

    /**
     * Create an instance of {@link ReservarOferta }
     * 
     */
    public ReservarOferta createReservarOferta() {
        return new ReservarOferta();
    }

    /**
     * Create an instance of {@link BuscarReservasDeUnUsuario }
     * 
     */
    public BuscarReservasDeUnUsuario createBuscarReservasDeUnUsuario() {
        return new BuscarReservasDeUnUsuario();
    }

    /**
     * Create an instance of {@link ActualizarOferta }
     * 
     */
    public ActualizarOferta createActualizarOferta() {
        return new ActualizarOferta();
    }

    /**
     * Create an instance of {@link BuscarOfertasResponse }
     * 
     */
    public BuscarOfertasResponse createBuscarOfertasResponse() {
        return new BuscarOfertasResponse();
    }

    /**
     * Create an instance of {@link ReservaDto }
     * 
     */
    public ReservaDto createReservaDto() {
        return new ReservaDto();
    }

    /**
     * Create an instance of {@link OfertaReservadaDto }
     * 
     */
    public OfertaReservadaDto createOfertaReservadaDto() {
        return new OfertaReservadaDto();
    }

    /**
     * Create an instance of {@link OfertaDto }
     * 
     */
    public OfertaDto createOfertaDto() {
        return new OfertaDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapReservaExpirationExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapReservaExpirationException")
    public JAXBElement<SoapReservaExpirationExceptionInfo> createSoapReservaExpirationException(SoapReservaExpirationExceptionInfo value) {
        return new JAXBElement<SoapReservaExpirationExceptionInfo>(_SoapReservaExpirationException_QNAME, SoapReservaExpirationExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReclamarReservaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reclamarReservaResponse")
    public JAXBElement<ReclamarReservaResponse> createReclamarReservaResponse(ReclamarReservaResponse value) {
        return new JAXBElement<ReclamarReservaResponse>(_ReclamarReservaResponse_QNAME, ReclamarReservaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapPrecioDescontadoExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapPrecioDescontadoException")
    public JAXBElement<SoapPrecioDescontadoExceptionInfo> createSoapPrecioDescontadoException(SoapPrecioDescontadoExceptionInfo value) {
        return new JAXBElement<SoapPrecioDescontadoExceptionInfo>(_SoapPrecioDescontadoException_QNAME, SoapPrecioDescontadoExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservaResponse")
    public JAXBElement<BuscarReservaResponse> createBuscarReservaResponse(BuscarReservaResponse value) {
        return new JAXBElement<BuscarReservaResponse>(_BuscarReservaResponse_QNAME, BuscarReservaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapOfertaInvalidaExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaInvalidaException")
    public JAXBElement<SoapOfertaInvalidaExceptionInfo> createSoapOfertaInvalidaException(SoapOfertaInvalidaExceptionInfo value) {
        return new JAXBElement<SoapOfertaInvalidaExceptionInfo>(_SoapOfertaInvalidaException_QNAME, SoapOfertaInvalidaExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "invalidarOfertaResponse")
    public JAXBElement<InvalidarOfertaResponse> createInvalidarOfertaResponse(InvalidarOfertaResponse value) {
        return new JAXBElement<InvalidarOfertaResponse>(_InvalidarOfertaResponse_QNAME, InvalidarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapInstanceNotFoundExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInstanceNotFoundException")
    public JAXBElement<SoapInstanceNotFoundExceptionInfo> createSoapInstanceNotFoundException(SoapInstanceNotFoundExceptionInfo value) {
        return new JAXBElement<SoapInstanceNotFoundExceptionInfo>(_SoapInstanceNotFoundException_QNAME, SoapInstanceNotFoundExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapOfertaReservedExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaReservedException")
    public JAXBElement<SoapOfertaReservedExceptionInfo> createSoapOfertaReservedException(SoapOfertaReservedExceptionInfo value) {
        return new JAXBElement<SoapOfertaReservedExceptionInfo>(_SoapOfertaReservedException_QNAME, SoapOfertaReservedExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservasDeUnUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservasDeUnUsuarioResponse")
    public JAXBElement<BuscarReservasDeUnUsuarioResponse> createBuscarReservasDeUnUsuarioResponse(BuscarReservasDeUnUsuarioResponse value) {
        return new JAXBElement<BuscarReservasDeUnUsuarioResponse>(_BuscarReservasDeUnUsuarioResponse_QNAME, BuscarReservasDeUnUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reservarOfertaResponse")
    public JAXBElement<ReservarOfertaResponse> createReservarOfertaResponse(ReservarOfertaResponse value) {
        return new JAXBElement<ReservarOfertaResponse>(_ReservarOfertaResponse_QNAME, ReservarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapOfertaExpirationExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaExpirationException")
    public JAXBElement<SoapOfertaExpirationExceptionInfo> createSoapOfertaExpirationException(SoapOfertaExpirationExceptionInfo value) {
        return new JAXBElement<SoapOfertaExpirationExceptionInfo>(_SoapOfertaExpirationException_QNAME, SoapOfertaExpirationExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapOfertaReservedByUserExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaReservedByUserException")
    public JAXBElement<SoapOfertaReservedByUserExceptionInfo> createSoapOfertaReservedByUserException(SoapOfertaReservedByUserExceptionInfo value) {
        return new JAXBElement<SoapOfertaReservedByUserExceptionInfo>(_SoapOfertaReservedByUserException_QNAME, SoapOfertaReservedByUserExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReserva")
    public JAXBElement<BuscarReserva> createBuscarReserva(BuscarReserva value) {
        return new JAXBElement<BuscarReserva>(_BuscarReserva_QNAME, BuscarReserva.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOferta")
    public JAXBElement<BuscarOferta> createBuscarOferta(BuscarOferta value) {
        return new JAXBElement<BuscarOferta>(_BuscarOferta_QNAME, BuscarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "eliminarOferta")
    public JAXBElement<EliminarOferta> createEliminarOferta(EliminarOferta value) {
        return new JAXBElement<EliminarOferta>(_EliminarOferta_QNAME, EliminarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "eliminarOfertaResponse")
    public JAXBElement<EliminarOfertaResponse> createEliminarOfertaResponse(EliminarOfertaResponse value) {
        return new JAXBElement<EliminarOfertaResponse>(_EliminarOfertaResponse_QNAME, EliminarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoapReservaNoValidaExceptionInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapReservaNoValidaException")
    public JAXBElement<SoapReservaNoValidaExceptionInfo> createSoapReservaNoValidaException(SoapReservaNoValidaExceptionInfo value) {
        return new JAXBElement<SoapReservaNoValidaExceptionInfo>(_SoapReservaNoValidaException_QNAME, SoapReservaNoValidaExceptionInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "invalidarOferta")
    public JAXBElement<InvalidarOferta> createInvalidarOferta(InvalidarOferta value) {
        return new JAXBElement<InvalidarOferta>(_InvalidarOferta_QNAME, InvalidarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservasDeUnaOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservasDeUnaOfertaResponse")
    public JAXBElement<BuscarReservasDeUnaOfertaResponse> createBuscarReservasDeUnaOfertaResponse(BuscarReservasDeUnaOfertaResponse value) {
        return new JAXBElement<BuscarReservasDeUnaOfertaResponse>(_BuscarReservasDeUnaOfertaResponse_QNAME, BuscarReservasDeUnaOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasReservadasDeUnUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasReservadasDeUnUsuarioResponse")
    public JAXBElement<BuscarOfertasReservadasDeUnUsuarioResponse> createBuscarOfertasReservadasDeUnUsuarioResponse(BuscarOfertasReservadasDeUnUsuarioResponse value) {
        return new JAXBElement<BuscarOfertasReservadasDeUnUsuarioResponse>(_BuscarOfertasReservadasDeUnUsuarioResponse_QNAME, BuscarOfertasReservadasDeUnUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapFechaInvalidaException")
    public JAXBElement<String> createSoapFechaInvalidaException(String value) {
        return new JAXBElement<String>(_SoapFechaInvalidaException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reservarOferta")
    public JAXBElement<ReservarOferta> createReservarOferta(ReservarOferta value) {
        return new JAXBElement<ReservarOferta>(_ReservarOferta_QNAME, ReservarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "actualizarOfertaResponse")
    public JAXBElement<ActualizarOfertaResponse> createActualizarOfertaResponse(ActualizarOfertaResponse value) {
        return new JAXBElement<ActualizarOfertaResponse>(_ActualizarOfertaResponse_QNAME, ActualizarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "actualizarOferta")
    public JAXBElement<ActualizarOferta> createActualizarOferta(ActualizarOferta value) {
        return new JAXBElement<ActualizarOferta>(_ActualizarOferta_QNAME, ActualizarOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservasDeUnUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservasDeUnUsuario")
    public JAXBElement<BuscarReservasDeUnUsuario> createBuscarReservasDeUnUsuario(BuscarReservasDeUnUsuario value) {
        return new JAXBElement<BuscarReservasDeUnUsuario>(_BuscarReservasDeUnUsuario_QNAME, BuscarReservasDeUnUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasResponse")
    public JAXBElement<BuscarOfertasResponse> createBuscarOfertasResponse(BuscarOfertasResponse value) {
        return new JAXBElement<BuscarOfertasResponse>(_BuscarOfertasResponse_QNAME, BuscarOfertasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertas")
    public JAXBElement<BuscarOfertas> createBuscarOfertas(BuscarOfertas value) {
        return new JAXBElement<BuscarOfertas>(_BuscarOfertas_QNAME, BuscarOfertas.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaResponse")
    public JAXBElement<BuscarOfertaResponse> createBuscarOfertaResponse(BuscarOfertaResponse value) {
        return new JAXBElement<BuscarOfertaResponse>(_BuscarOfertaResponse_QNAME, BuscarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnadirOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "anadirOfertaResponse")
    public JAXBElement<AnadirOfertaResponse> createAnadirOfertaResponse(AnadirOfertaResponse value) {
        return new JAXBElement<AnadirOfertaResponse>(_AnadirOfertaResponse_QNAME, AnadirOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasReservadasDeUnUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasReservadasDeUnUsuario")
    public JAXBElement<BuscarOfertasReservadasDeUnUsuario> createBuscarOfertasReservadasDeUnUsuario(BuscarOfertasReservadasDeUnUsuario value) {
        return new JAXBElement<BuscarOfertasReservadasDeUnUsuario>(_BuscarOfertasReservadasDeUnUsuario_QNAME, BuscarOfertasReservadasDeUnUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapInputValidationException")
    public JAXBElement<String> createSoapInputValidationException(String value) {
        return new JAXBElement<String>(_SoapInputValidationException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnadirOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "anadirOferta")
    public JAXBElement<AnadirOferta> createAnadirOferta(AnadirOferta value) {
        return new JAXBElement<AnadirOferta>(_AnadirOferta_QNAME, AnadirOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservasDeUnaOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservasDeUnaOferta")
    public JAXBElement<BuscarReservasDeUnaOferta> createBuscarReservasDeUnaOferta(BuscarReservasDeUnaOferta value) {
        return new JAXBElement<BuscarReservasDeUnaOferta>(_BuscarReservasDeUnaOferta_QNAME, BuscarReservasDeUnaOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReclamarReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reclamarReserva")
    public JAXBElement<ReclamarReserva> createReclamarReserva(ReclamarReserva value) {
        return new JAXBElement<ReclamarReserva>(_ReclamarReserva_QNAME, ReclamarReserva.class, null, value);
    }

}
