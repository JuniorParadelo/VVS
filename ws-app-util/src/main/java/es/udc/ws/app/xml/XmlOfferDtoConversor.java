package es.udc.ws.app.xml;

import es.udc.ws.app.dto.OfferDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlOfferDtoConversor {

    public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/movies/xml");

    public static Document toXml(OfferDto offer)
            throws IOException {

        Element offerElement = toJDOMElement(offer);

        return new Document(offerElement);
    }

    public static Document toXml(List<OfferDto> offer)
            throws IOException {

        Element offersElement = new Element("offers", XML_NS);
        for (int i = 0; i < offer.size(); i++) {
            OfferDto xmlOfferDto = offer.get(i);
            Element offerElement = toJDOMElement(xmlOfferDto);
            offersElement.addContent(offerElement);
        }

        return new Document(offersElement);
    }

    public static OfferDto toOffer(InputStream offerXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(offerXml);
            Element rootElement = document.getRootElement();

            return toOffer(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<OfferDto> toOffers(InputStream offerXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(offerXml);
            Element rootElement = document.getRootElement();

            if(!"offers".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('offers' expected)");
            }
            @SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
            List<OfferDto> offerDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                offerDtos.add(toOffer(element));
            }

            return offerDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfferDto offer) {

    	Element offerElement = new Element("offer", XML_NS);

        if (offer.getOfferId() != null) {
            Element identifierElement = new Element("offerId", XML_NS);
            identifierElement.setText(offer.getOfferId().toString());
            offerElement.addContent(identifierElement);
        }

        Element priceElement = new Element("price", XML_NS);
        priceElement.setText(Double.toString(offer.getRealPrice()));
        priceElement.setText(Double.toString(offer.getDiscountPrice()));
        offerElement.addContent(priceElement);

        Element titleElement = new Element("name", XML_NS);
        titleElement.setText(offer.getName());
        offerElement.addContent(titleElement);
        
        Element bookingDateElement = new Element("bookingDate", XML_NS);
        bookingDateElement.setText(offer.getBookingDate().toString());
        Element enjoymentDateElement = new Element("enjoymentDate", XML_NS);
        enjoymentDateElement.setText(offer.getEnjoymentDate().toString());
        offerElement.addContent(enjoymentDateElement);

        return offerElement;
    }

    private static OfferDto toOffer(Element offerElement)
            throws ParsingException, DataConversionException {
        if (!"offer".equals(
        		offerElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + offerElement.getName() + "' ('offer' expected)");
        }
        Element identifierElement = offerElement.getChild("offerId", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String description = offerElement
                .getChildTextNormalize("description", XML_NS);

        String name = offerElement.getChildTextNormalize("name", XML_NS);

        float realPrice = Float.valueOf(
        		offerElement.getChildTextTrim("realPrice", XML_NS));

        float discountPrice = Float.valueOf(
        		offerElement.getChildTextTrim("discountPrice", XML_NS));

        Calendar calendar = Calendar.getInstance();
        //Calendar enjoymentDate = calendar.setTime(offerElement("enjoymentDate", XML_NS));
        //no soy capaz de parsear esto.
        return new OfferDto(identifier, name, description, realPrice, discountPrice,calendar, calendar);
    }

}
