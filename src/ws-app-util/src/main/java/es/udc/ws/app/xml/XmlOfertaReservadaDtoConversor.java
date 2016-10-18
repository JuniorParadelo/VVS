package es.udc.ws.app.xml;

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

import es.udc.ws.app.dto.OfertaReservadaDto;

public class XmlOfertaReservadaDtoConversor {

    public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/ofertasReservadas/xml");

    public static Document toXml(OfertaReservadaDto oferta) throws IOException {

        Element ofertaElement = toJDOMElement(oferta);

        return new Document(ofertaElement);
    }

    public static Document toXml(List<OfertaReservadaDto> oferta) throws IOException {

        Element ofertasElement = new Element("ofertasReservadas", XML_NS);
        for (int i = 0; i < oferta.size(); i++) {
            OfertaReservadaDto xmlOfertaDto = oferta.get(i);
            Element ofertaElement = toJDOMElement(xmlOfertaDto);
            ofertasElement.addContent(ofertaElement);
        }

        return new Document(ofertasElement);
    }

    public static OfertaReservadaDto toOfertaReservada(InputStream ofertaXml) throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            return toOfertaReservada(rootElement);
            
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<OfertaReservadaDto> toOfertasReservadas(InputStream ofertaXml) throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            if(!"ofertasReservadas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('ofertasReservadas' expected)");
            }
            List<Element> children = rootElement.getChildren();
            List<OfertaReservadaDto> ofertaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                ofertaDtos.add(toOfertaReservada(element));
            }

            return ofertaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfertaReservadaDto oferta) {

    	Element ofertaElement = new Element("ofertaReservada", XML_NS);

        Element descripcion = new Element("descripcion", XML_NS);
        descripcion.setText(oferta.getDescripcion());
        ofertaElement.addContent(descripcion);
        
        Element precioDescontado = new Element("precioDescontado", XML_NS);
        precioDescontado.setText(Float.toString(oferta.getPrecioDescontado()));
        ofertaElement.addContent(precioDescontado);
        
        Element fechaDeReserva = calendarToXml(oferta.getFechaDeReserva(), "fechaDeReserva");
        ofertaElement.addContent(fechaDeReserva);

        return ofertaElement;
    }

    private static OfertaReservadaDto toOfertaReservada(Element ofertaElement) throws ParsingException, DataConversionException {

        if (!"ofertaReservada".equals(ofertaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + ofertaElement.getName() + "' ('ofertaReservada' expected)");
        }
        
        String descripcion = ofertaElement.getChildTextNormalize("descripcion", XML_NS);
        
        Calendar fechaDeReserva = xmlToCalendar(ofertaElement.getChild(
                "fechaDeReserva", XML_NS));

        float precioDescontado = Float.valueOf(
                ofertaElement.getChildTextTrim("precioDescontado", XML_NS));
        
        return new OfertaReservadaDto(descripcion, precioDescontado, fechaDeReserva);
    }
    
    private static Element calendarToXml(Calendar calendar, String name) {

        Element element = new Element(name, XML_NS);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = calendar.get(Calendar.YEAR);
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        element.setAttribute("day", Integer.toString(day));
        element.setAttribute("month", Integer.toString(month));
        element.setAttribute("year", Integer.toString(year));
        
        element.setAttribute("hour", Integer.toString(hour));
        element.setAttribute("min", Integer.toString(min));
        element.setAttribute("sec", Integer.toString(sec));

        return element;

    }
    
    private static Calendar xmlToCalendar(Element element) throws DataConversionException {

        if (element == null) {
            return null;
        }
        
        int day = element.getAttribute("day").getIntValue();
        int month = element.getAttribute("month").getIntValue();
        int year = element.getAttribute("year").getIntValue();
        
        int hour = element.getAttribute("hour").getIntValue();
        int min = element.getAttribute("min").getIntValue();
        int sec = element.getAttribute("sec").getIntValue();
        
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        calendar.set(Calendar.YEAR, year);
        
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);

        return calendar;

    }

}
