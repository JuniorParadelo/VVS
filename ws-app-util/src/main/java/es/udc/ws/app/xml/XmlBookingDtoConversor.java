package es.udc.ws.app.xml;

import es.udc.ws.app.dto.BookingDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlBookingDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/sales/xml");

    public static Document toResponse(BookingDto booking)
            throws IOException {

        Element bookingElement = toXml(booking);

        return new Document(bookingElement);
    }

    public static BookingDto toBooking(InputStream bookingXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(bookingXml);
            Element rootElement = document.getRootElement();

            return toBooking(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toXml(BookingDto booking) {

        Element bookingElement = new Element("booking", XML_NS);

        if (booking.getBookingId() != null) {
            Element bookingIdElement = new Element("bookingId", XML_NS);
            bookingIdElement.setText(booking.getBookingId().toString());
            bookingElement.addContent(bookingIdElement);
        }

        if (booking.getOfferId() != null) {
            Element bookingIdElement = new Element("offerId", XML_NS);
            bookingIdElement.setText(booking.getUserId().toString());
            bookingElement.addContent(bookingIdElement);
        }
        
        if (booking.getUserId() != null) {
            Element bookingIdElement = new Element("userId", XML_NS);
            bookingIdElement.setText(booking.getOfferId().toString());
            bookingElement.addContent(bookingIdElement);
        }
        
        if (booking.getCreditCardNumber() != null) {
            Element bookingIdElement = new Element("creditCardNumber", XML_NS);
            bookingIdElement.setText(booking.getCreditCardNumber().toString());
            bookingElement.addContent(bookingIdElement);
        }

        if (booking.getBookingDate() != null) {
            Element expirationDateElement = getExpirationDate(booking
                    .getBookingDate());
            bookingElement.addContent(expirationDateElement);
        }
        
        if (booking.getEnjoymentDate() != null) {
            Element expirationDateElement = getExpirationDate(booking
                    .getEnjoymentDate());
            bookingElement.addContent(expirationDateElement);
        }

        return bookingElement;
    }

    private static BookingDto toBooking(Element bookingElement)
            throws ParsingException, DataConversionException,
            NumberFormatException {
        if (!"booking".equals(bookingElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + bookingElement.getName() + "' ('booking' expected)");
        }
        Element identifierElement = bookingElement.getChild("bookingId", XML_NS);
        Long bookingId = null;
        
        if(identifierElement != null){
        	bookingId = Long.valueOf(identifierElement.getTextTrim());
        }
        
        Element identifierElement2 = bookingElement.getChild("offerId", XML_NS);
        Long offerId = null;
        
        if(identifierElement2 != null){
        	offerId = Long.valueOf(identifierElement2.getTextTrim());
        }
        
        String userId =bookingElement.getChildTextNormalize("userId", XML_NS);
        
        String creditCardNumber =bookingElement.getChildTextNormalize("creditCardNumber", XML_NS);
        
        Calendar bookingDate = getBookingDate(bookingElement.getChild(
                "bookingDate", XML_NS));
        
        Calendar enjoymentDate = getEnjoymentDate(bookingElement.getChild(
                "enjoymentDate", XML_NS));

        return new BookingDto(bookingId, offerId,userId,creditCardNumber,bookingDate, enjoymentDate);
    }
    
    

    private static Calendar getBookingDate(Element bookingDateElement)
            throws DataConversionException {

        if (bookingDateElement == null) {
            return null;
        }
        int day = bookingDateElement.getAttribute("day").getIntValue();
        int month = bookingDateElement.getAttribute("month").getIntValue();
        int year = bookingDateElement.getAttribute("year").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);

        return releaseDate;
    }
    
    private static Calendar getEnjoymentDate(Element enjoymentDateElement)
            throws DataConversionException {

        if (enjoymentDateElement == null) {
            return null;
        }
        int day = enjoymentDateElement.getAttribute("day").getIntValue();
        int month = enjoymentDateElement.getAttribute("month").getIntValue();
        int year = enjoymentDateElement.getAttribute("year").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);

        return releaseDate;
    }

    private static Element getExpirationDate(Calendar expirationDate) {

        Element releaseDateElement = new Element("expirationDate", XML_NS);
        int day = expirationDate.get(Calendar.DAY_OF_MONTH);
        int month = expirationDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = expirationDate.get(Calendar.YEAR);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));

        return releaseDateElement;
    }
}
