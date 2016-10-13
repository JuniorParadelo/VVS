
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

    private final static QName _ReclamarReservaResponse_QNAME = new QName("http://soap.ws.udc.es/", "reclamarReservaResponse");
    private final static QName _SoapHayReservasException_QNAME = new QName("http://soap.ws.udc.es/", "SoapHayReservasException");
    private final static QName _BuscarOfertaIDResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaIDResponse");
    private final static QName _BuscarOfertasUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasUsuarioResponse");
    private final static QName _InvalidarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOfertaResponse");
    private final static QName _SoapOfertaYaInvalidadaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaYaInvalidadaException");
    private final static QName _SoapFechaReservaExpiradaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapFechaReservaExpiradaException");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _BorrarOferta_QNAME = new QName("http://soap.ws.udc.es/", "borrarOferta");
    private final static QName _ReservarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "reservarOfertaResponse");
    private final static QName _BuscarReservaIDOfResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservaIDOfResponse");
    private final static QName _SoapReclamacionAnteriorReserva_QNAME = new QName("http://soap.ws.udc.es/", "SoapReclamacionAnteriorReserva");
    private final static QName _BuscarOfertaPclave_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaPclave");
    private final static QName _BuscarOfertaPclaveResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaPclaveResponse");
    private final static QName _InvalidarOferta_QNAME = new QName("http://soap.ws.udc.es/", "invalidarOferta");
    private final static QName _AnnadirOferta_QNAME = new QName("http://soap.ws.udc.es/", "annadirOferta");
    private final static QName _BorrarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "borrarOfertaResponse");
    private final static QName _SoapOfertaYaReservadaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapOfertaYaReservadaException");
    private final static QName _SoapFechaInvalidaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapFechaInvalidaException");
    private final static QName _ReservarOferta_QNAME = new QName("http://soap.ws.udc.es/", "reservarOferta");
    private final static QName _ActualizarOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOfertaResponse");
    private final static QName _BuscarReservaUsuarioResponse_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservaUsuarioResponse");
    private final static QName _SoapPrecioInvalidoException_QNAME = new QName("http://soap.ws.udc.es/", "SoapPrecioInvalidoException");
    private final static QName _ActualizarOferta_QNAME = new QName("http://soap.ws.udc.es/", "actualizarOferta");
    private final static QName _SoapYaReclamadaException_QNAME = new QName("http://soap.ws.udc.es/", "SoapYaReclamadaException");
    private final static QName _BuscarOfertaID_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertaID");
    private final static QName _BuscarReservaUsuario_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservaUsuario");
    private final static QName _AnnadirOfertaResponse_QNAME = new QName("http://soap.ws.udc.es/", "annadirOfertaResponse");
    private final static QName _BuscarReservaIDOf_QNAME = new QName("http://soap.ws.udc.es/", "buscarReservaIDOf");
    private final static QName _BuscarOfertasUsuario_QNAME = new QName("http://soap.ws.udc.es/", "buscarOfertasUsuario");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
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
     * Create an instance of {@link BorrarOferta }
     * 
     */
    public BorrarOferta createBorrarOferta() {
        return new BorrarOferta();
    }

    /**
     * Create an instance of {@link BuscarReservaIDOfResponse }
     * 
     */
    public BuscarReservaIDOfResponse createBuscarReservaIDOfResponse() {
        return new BuscarReservaIDOfResponse();
    }

    /**
     * Create an instance of {@link ReservarOfertaResponse }
     * 
     */
    public ReservarOfertaResponse createReservarOfertaResponse() {
        return new ReservarOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertaPclave }
     * 
     */
    public BuscarOfertaPclave createBuscarOfertaPclave() {
        return new BuscarOfertaPclave();
    }

    /**
     * Create an instance of {@link BuscarOfertaPclaveResponse }
     * 
     */
    public BuscarOfertaPclaveResponse createBuscarOfertaPclaveResponse() {
        return new BuscarOfertaPclaveResponse();
    }

    /**
     * Create an instance of {@link ReclamarReservaResponse }
     * 
     */
    public ReclamarReservaResponse createReclamarReservaResponse() {
        return new ReclamarReservaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertaIDResponse }
     * 
     */
    public BuscarOfertaIDResponse createBuscarOfertaIDResponse() {
        return new BuscarOfertaIDResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertasUsuarioResponse }
     * 
     */
    public BuscarOfertasUsuarioResponse createBuscarOfertasUsuarioResponse() {
        return new BuscarOfertasUsuarioResponse();
    }

    /**
     * Create an instance of {@link InvalidarOfertaResponse }
     * 
     */
    public InvalidarOfertaResponse createInvalidarOfertaResponse() {
        return new InvalidarOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarOfertaID }
     * 
     */
    public BuscarOfertaID createBuscarOfertaID() {
        return new BuscarOfertaID();
    }

    /**
     * Create an instance of {@link BuscarReservaUsuario }
     * 
     */
    public BuscarReservaUsuario createBuscarReservaUsuario() {
        return new BuscarReservaUsuario();
    }

    /**
     * Create an instance of {@link BuscarOfertasUsuario }
     * 
     */
    public BuscarOfertasUsuario createBuscarOfertasUsuario() {
        return new BuscarOfertasUsuario();
    }

    /**
     * Create an instance of {@link AnnadirOfertaResponse }
     * 
     */
    public AnnadirOfertaResponse createAnnadirOfertaResponse() {
        return new AnnadirOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarReservaIDOf }
     * 
     */
    public BuscarReservaIDOf createBuscarReservaIDOf() {
        return new BuscarReservaIDOf();
    }

    /**
     * Create an instance of {@link ReclamarReserva }
     * 
     */
    public ReclamarReserva createReclamarReserva() {
        return new ReclamarReserva();
    }

    /**
     * Create an instance of {@link AnnadirOferta }
     * 
     */
    public AnnadirOferta createAnnadirOferta() {
        return new AnnadirOferta();
    }

    /**
     * Create an instance of {@link InvalidarOferta }
     * 
     */
    public InvalidarOferta createInvalidarOferta() {
        return new InvalidarOferta();
    }

    /**
     * Create an instance of {@link BorrarOfertaResponse }
     * 
     */
    public BorrarOfertaResponse createBorrarOfertaResponse() {
        return new BorrarOfertaResponse();
    }

    /**
     * Create an instance of {@link ActualizarOfertaResponse }
     * 
     */
    public ActualizarOfertaResponse createActualizarOfertaResponse() {
        return new ActualizarOfertaResponse();
    }

    /**
     * Create an instance of {@link BuscarReservaUsuarioResponse }
     * 
     */
    public BuscarReservaUsuarioResponse createBuscarReservaUsuarioResponse() {
        return new BuscarReservaUsuarioResponse();
    }

    /**
     * Create an instance of {@link ReservarOferta }
     * 
     */
    public ReservarOferta createReservarOferta() {
        return new ReservarOferta();
    }

    /**
     * Create an instance of {@link ActualizarOferta }
     * 
     */
    public ActualizarOferta createActualizarOferta() {
        return new ActualizarOferta();
    }

    /**
     * Create an instance of {@link ReservaDto }
     * 
     */
    public ReservaDto createReservaDto() {
        return new ReservaDto();
    }

    /**
     * Create an instance of {@link MixtoDto }
     * 
     */
    public MixtoDto createMixtoDto() {
        return new MixtoDto();
    }

    /**
     * Create an instance of {@link OfertaDto }
     * 
     */
    public OfertaDto createOfertaDto() {
        return new OfertaDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapHayReservasException")
    public JAXBElement<String> createSoapHayReservasException(String value) {
        return new JAXBElement<String>(_SoapHayReservasException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaIDResponse")
    public JAXBElement<BuscarOfertaIDResponse> createBuscarOfertaIDResponse(BuscarOfertaIDResponse value) {
        return new JAXBElement<BuscarOfertaIDResponse>(_BuscarOfertaIDResponse_QNAME, BuscarOfertaIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasUsuarioResponse")
    public JAXBElement<BuscarOfertasUsuarioResponse> createBuscarOfertasUsuarioResponse(BuscarOfertasUsuarioResponse value) {
        return new JAXBElement<BuscarOfertasUsuarioResponse>(_BuscarOfertasUsuarioResponse_QNAME, BuscarOfertasUsuarioResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaYaInvalidadaException")
    public JAXBElement<String> createSoapOfertaYaInvalidadaException(String value) {
        return new JAXBElement<String>(_SoapOfertaYaInvalidadaException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapFechaReservaExpiradaException")
    public JAXBElement<String> createSoapFechaReservaExpiradaException(String value) {
        return new JAXBElement<String>(_SoapFechaReservaExpiradaException_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "borrarOferta")
    public JAXBElement<BorrarOferta> createBorrarOferta(BorrarOferta value) {
        return new JAXBElement<BorrarOferta>(_BorrarOferta_QNAME, BorrarOferta.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservaIDOfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservaIDOfResponse")
    public JAXBElement<BuscarReservaIDOfResponse> createBuscarReservaIDOfResponse(BuscarReservaIDOfResponse value) {
        return new JAXBElement<BuscarReservaIDOfResponse>(_BuscarReservaIDOfResponse_QNAME, BuscarReservaIDOfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapReclamacionAnteriorReserva")
    public JAXBElement<String> createSoapReclamacionAnteriorReserva(String value) {
        return new JAXBElement<String>(_SoapReclamacionAnteriorReserva_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaPclave }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaPclave")
    public JAXBElement<BuscarOfertaPclave> createBuscarOfertaPclave(BuscarOfertaPclave value) {
        return new JAXBElement<BuscarOfertaPclave>(_BuscarOfertaPclave_QNAME, BuscarOfertaPclave.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaPclaveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaPclaveResponse")
    public JAXBElement<BuscarOfertaPclaveResponse> createBuscarOfertaPclaveResponse(BuscarOfertaPclaveResponse value) {
        return new JAXBElement<BuscarOfertaPclaveResponse>(_BuscarOfertaPclaveResponse_QNAME, BuscarOfertaPclaveResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AnnadirOferta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "annadirOferta")
    public JAXBElement<AnnadirOferta> createAnnadirOferta(AnnadirOferta value) {
        return new JAXBElement<AnnadirOferta>(_AnnadirOferta_QNAME, AnnadirOferta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrarOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "borrarOfertaResponse")
    public JAXBElement<BorrarOfertaResponse> createBorrarOfertaResponse(BorrarOfertaResponse value) {
        return new JAXBElement<BorrarOfertaResponse>(_BorrarOfertaResponse_QNAME, BorrarOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapOfertaYaReservadaException")
    public JAXBElement<String> createSoapOfertaYaReservadaException(String value) {
        return new JAXBElement<String>(_SoapOfertaYaReservadaException_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservaUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservaUsuarioResponse")
    public JAXBElement<BuscarReservaUsuarioResponse> createBuscarReservaUsuarioResponse(BuscarReservaUsuarioResponse value) {
        return new JAXBElement<BuscarReservaUsuarioResponse>(_BuscarReservaUsuarioResponse_QNAME, BuscarReservaUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapPrecioInvalidoException")
    public JAXBElement<String> createSoapPrecioInvalidoException(String value) {
        return new JAXBElement<String>(_SoapPrecioInvalidoException_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "SoapYaReclamadaException")
    public JAXBElement<String> createSoapYaReclamadaException(String value) {
        return new JAXBElement<String>(_SoapYaReclamadaException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertaID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertaID")
    public JAXBElement<BuscarOfertaID> createBuscarOfertaID(BuscarOfertaID value) {
        return new JAXBElement<BuscarOfertaID>(_BuscarOfertaID_QNAME, BuscarOfertaID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservaUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservaUsuario")
    public JAXBElement<BuscarReservaUsuario> createBuscarReservaUsuario(BuscarReservaUsuario value) {
        return new JAXBElement<BuscarReservaUsuario>(_BuscarReservaUsuario_QNAME, BuscarReservaUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AnnadirOfertaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "annadirOfertaResponse")
    public JAXBElement<AnnadirOfertaResponse> createAnnadirOfertaResponse(AnnadirOfertaResponse value) {
        return new JAXBElement<AnnadirOfertaResponse>(_AnnadirOfertaResponse_QNAME, AnnadirOfertaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarReservaIDOf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarReservaIDOf")
    public JAXBElement<BuscarReservaIDOf> createBuscarReservaIDOf(BuscarReservaIDOf value) {
        return new JAXBElement<BuscarReservaIDOf>(_BuscarReservaIDOf_QNAME, BuscarReservaIDOf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarOfertasUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "buscarOfertasUsuario")
    public JAXBElement<BuscarOfertasUsuario> createBuscarOfertasUsuario(BuscarOfertasUsuario value) {
        return new JAXBElement<BuscarOfertasUsuario>(_BuscarOfertasUsuario_QNAME, BuscarOfertasUsuario.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ReclamarReserva }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "reclamarReserva")
    public JAXBElement<ReclamarReserva> createReclamarReserva(ReclamarReserva value) {
        return new JAXBElement<ReclamarReserva>(_ReclamarReserva_QNAME, ReclamarReserva.class, null, value);
    }

}
