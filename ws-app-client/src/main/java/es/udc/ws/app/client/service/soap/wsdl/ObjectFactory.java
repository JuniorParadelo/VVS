
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

    private final static QName _FindBookingResponse_QNAME = new QName("http://soap.ws.udc.es/", "findBookingResponse");
    private final static QName _AddOffer_QNAME = new QName("http://soap.ws.udc.es/", "addOffer");
    private final static QName _UserAlreadyReservedException_QNAME = new QName("http://soap.ws.udc.es/", "UserAlreadyReservedException");
    private final static QName _FindOffer_QNAME = new QName("http://soap.ws.udc.es/", "findOffer");
    private final static QName _UpdateOffer_QNAME = new QName("http://soap.ws.udc.es/", "updateOffer");
    private final static QName _UseBookingResponse_QNAME = new QName("http://soap.ws.udc.es/", "useBookingResponse");
    private final static QName _SoapInstanceNotFoundException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInstanceNotFoundException");
    private final static QName _AddBooking_QNAME = new QName("http://soap.ws.udc.es/", "addBooking");
    private final static QName _RemoveOffer_QNAME = new QName("http://soap.ws.udc.es/", "removeOffer");
    private final static QName _FindOfferResponse_QNAME = new QName("http://soap.ws.udc.es/", "findOfferResponse");
    private final static QName _OfferAlreadyCreatedException_QNAME = new QName("http://soap.ws.udc.es/", "OfferAlreadyCreatedException");
    private final static QName _AddBookingResponse_QNAME = new QName("http://soap.ws.udc.es/", "addBookingResponse");
    private final static QName _RemoveOfferResponse_QNAME = new QName("http://soap.ws.udc.es/", "removeOfferResponse");
    private final static QName _SoapInputValidationException_QNAME = new QName("http://soap.ws.udc.es/", "SoapInputValidationException");
    private final static QName _AddOfferResponse_QNAME = new QName("http://soap.ws.udc.es/", "addOfferResponse");
    private final static QName _UseBooking_QNAME = new QName("http://soap.ws.udc.es/", "useBooking");
    private final static QName _UpdateOfferResponse_QNAME = new QName("http://soap.ws.udc.es/", "updateOfferResponse");
    private final static QName _FindBooking_QNAME = new QName("http://soap.ws.udc.es/", "findBooking");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.ws.app.client.service.soap.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddBooking }
     * 
     */
    public AddBooking createAddBooking() {
        return new AddBooking();
    }

    /**
     * Create an instance of {@link RemoveOffer }
     * 
     */
    public RemoveOffer createRemoveOffer() {
        return new RemoveOffer();
    }

    /**
     * Create an instance of {@link SoapInstanceNotFoundExceptionInfo }
     * 
     */
    public SoapInstanceNotFoundExceptionInfo createSoapInstanceNotFoundExceptionInfo() {
        return new SoapInstanceNotFoundExceptionInfo();
    }

    /**
     * Create an instance of {@link FindOfferResponse }
     * 
     */
    public FindOfferResponse createFindOfferResponse() {
        return new FindOfferResponse();
    }

    /**
     * Create an instance of {@link OfferAlreadyCreatedException }
     * 
     */
    public OfferAlreadyCreatedException createOfferAlreadyCreatedException() {
        return new OfferAlreadyCreatedException();
    }

    /**
     * Create an instance of {@link AddBookingResponse }
     * 
     */
    public AddBookingResponse createAddBookingResponse() {
        return new AddBookingResponse();
    }

    /**
     * Create an instance of {@link RemoveOfferResponse }
     * 
     */
    public RemoveOfferResponse createRemoveOfferResponse() {
        return new RemoveOfferResponse();
    }

    /**
     * Create an instance of {@link UseBooking }
     * 
     */
    public UseBooking createUseBooking() {
        return new UseBooking();
    }

    /**
     * Create an instance of {@link AddOfferResponse }
     * 
     */
    public AddOfferResponse createAddOfferResponse() {
        return new AddOfferResponse();
    }

    /**
     * Create an instance of {@link UpdateOfferResponse }
     * 
     */
    public UpdateOfferResponse createUpdateOfferResponse() {
        return new UpdateOfferResponse();
    }

    /**
     * Create an instance of {@link FindBooking }
     * 
     */
    public FindBooking createFindBooking() {
        return new FindBooking();
    }

    /**
     * Create an instance of {@link FindBookingResponse }
     * 
     */
    public FindBookingResponse createFindBookingResponse() {
        return new FindBookingResponse();
    }

    /**
     * Create an instance of {@link AddOffer }
     * 
     */
    public AddOffer createAddOffer() {
        return new AddOffer();
    }

    /**
     * Create an instance of {@link UserAlreadyReservedException }
     * 
     */
    public UserAlreadyReservedException createUserAlreadyReservedException() {
        return new UserAlreadyReservedException();
    }

    /**
     * Create an instance of {@link FindOffer }
     * 
     */
    public FindOffer createFindOffer() {
        return new FindOffer();
    }

    /**
     * Create an instance of {@link UpdateOffer }
     * 
     */
    public UpdateOffer createUpdateOffer() {
        return new UpdateOffer();
    }

    /**
     * Create an instance of {@link UseBookingResponse }
     * 
     */
    public UseBookingResponse createUseBookingResponse() {
        return new UseBookingResponse();
    }

    /**
     * Create an instance of {@link OfferDto }
     * 
     */
    public OfferDto createOfferDto() {
        return new OfferDto();
    }

    /**
     * Create an instance of {@link BookingDto }
     * 
     */
    public BookingDto createBookingDto() {
        return new BookingDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBookingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findBookingResponse")
    public JAXBElement<FindBookingResponse> createFindBookingResponse(FindBookingResponse value) {
        return new JAXBElement<FindBookingResponse>(_FindBookingResponse_QNAME, FindBookingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addOffer")
    public JAXBElement<AddOffer> createAddOffer(AddOffer value) {
        return new JAXBElement<AddOffer>(_AddOffer_QNAME, AddOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAlreadyReservedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "UserAlreadyReservedException")
    public JAXBElement<UserAlreadyReservedException> createUserAlreadyReservedException(UserAlreadyReservedException value) {
        return new JAXBElement<UserAlreadyReservedException>(_UserAlreadyReservedException_QNAME, UserAlreadyReservedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findOffer")
    public JAXBElement<FindOffer> createFindOffer(FindOffer value) {
        return new JAXBElement<FindOffer>(_FindOffer_QNAME, FindOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateOffer")
    public JAXBElement<UpdateOffer> createUpdateOffer(UpdateOffer value) {
        return new JAXBElement<UpdateOffer>(_UpdateOffer_QNAME, UpdateOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UseBookingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "useBookingResponse")
    public JAXBElement<UseBookingResponse> createUseBookingResponse(UseBookingResponse value) {
        return new JAXBElement<UseBookingResponse>(_UseBookingResponse_QNAME, UseBookingResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBooking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addBooking")
    public JAXBElement<AddBooking> createAddBooking(AddBooking value) {
        return new JAXBElement<AddBooking>(_AddBooking_QNAME, AddBooking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOffer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeOffer")
    public JAXBElement<RemoveOffer> createRemoveOffer(RemoveOffer value) {
        return new JAXBElement<RemoveOffer>(_RemoveOffer_QNAME, RemoveOffer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findOfferResponse")
    public JAXBElement<FindOfferResponse> createFindOfferResponse(FindOfferResponse value) {
        return new JAXBElement<FindOfferResponse>(_FindOfferResponse_QNAME, FindOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfferAlreadyCreatedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "OfferAlreadyCreatedException")
    public JAXBElement<OfferAlreadyCreatedException> createOfferAlreadyCreatedException(OfferAlreadyCreatedException value) {
        return new JAXBElement<OfferAlreadyCreatedException>(_OfferAlreadyCreatedException_QNAME, OfferAlreadyCreatedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBookingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addBookingResponse")
    public JAXBElement<AddBookingResponse> createAddBookingResponse(AddBookingResponse value) {
        return new JAXBElement<AddBookingResponse>(_AddBookingResponse_QNAME, AddBookingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "removeOfferResponse")
    public JAXBElement<RemoveOfferResponse> createRemoveOfferResponse(RemoveOfferResponse value) {
        return new JAXBElement<RemoveOfferResponse>(_RemoveOfferResponse_QNAME, RemoveOfferResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "addOfferResponse")
    public JAXBElement<AddOfferResponse> createAddOfferResponse(AddOfferResponse value) {
        return new JAXBElement<AddOfferResponse>(_AddOfferResponse_QNAME, AddOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UseBooking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "useBooking")
    public JAXBElement<UseBooking> createUseBooking(UseBooking value) {
        return new JAXBElement<UseBooking>(_UseBooking_QNAME, UseBooking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOfferResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "updateOfferResponse")
    public JAXBElement<UpdateOfferResponse> createUpdateOfferResponse(UpdateOfferResponse value) {
        return new JAXBElement<UpdateOfferResponse>(_UpdateOfferResponse_QNAME, UpdateOfferResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindBooking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.ws.udc.es/", name = "findBooking")
    public JAXBElement<FindBooking> createFindBooking(FindBooking value) {
        return new JAXBElement<FindBooking>(_FindBooking_QNAME, FindBooking.class, null, value);
    }

}
