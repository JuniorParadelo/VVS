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

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.enums.EstadoOferta;

public class XmlOfertaDtoConversor {

    public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/ofertas/xml");

    public static Document toXml(OfertaDto oferta)
            throws IOException {

        Element ofertaElement = toJDOMElement(oferta);

        return new Document(ofertaElement);
    }

    public static Document toXml(List<OfertaDto> oferta)
            throws IOException {

        Element ofertasElement = new Element("ofertas", XML_NS);
        for (int i = 0; i < oferta.size(); i++) {
            OfertaDto xmlOfertaDto = oferta.get(i);
            Element ofertaElement = toJDOMElement(xmlOfertaDto);
            ofertasElement.addContent(ofertaElement);
        }

        return new Document(ofertasElement);
    }

    public static OfertaDto toOferta(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            return toOferta(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<OfertaDto> toOfertas(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            if(!"ofertas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('ofertas' expected)");
            }
            List<Element> children = rootElement.getChildren();
            List<OfertaDto> ofertaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                ofertaDtos.add(toOferta(element));
            }

            return ofertaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfertaDto oferta) {

    	Element ofertaElement = new Element("oferta", XML_NS);

        if (oferta.getOfertaId() != null) {
            Element identifierElement = new Element("ofertaId", XML_NS);
            identifierElement.setText(oferta.getOfertaId().toString());
            ofertaElement.addContent(identifierElement);
        }

        Element nombre = new Element("nombre", XML_NS);
        nombre.setText(oferta.getNombre());
        ofertaElement.addContent(nombre);
        
        Element descripcion = new Element("descripcion", XML_NS);
        descripcion.setText(oferta.getDescripcion());
        ofertaElement.addContent(descripcion);
        
        Element fechaReservar = getExpirationDate(oferta.getFechaReservar(), "fechaReservar");
        ofertaElement.addContent(fechaReservar);
        
        Element fechaReclamar = getExpirationDate(oferta.getFechaReclamar(), "fechaReclamar");
        ofertaElement.addContent(fechaReclamar);
        
        Element precioReal = new Element("precioReal", XML_NS);
        precioReal.setText(Float.toString(oferta.getPrecioReal()));
        ofertaElement.addContent(precioReal);
        
        Element precioDescontado = new Element("precioDescontado", XML_NS);
        precioDescontado.setText(Float.toString(oferta.getPrecioDescontado()));
        ofertaElement.addContent(precioDescontado);
        
        Element estado = new Element("estado", XML_NS);
        estado.setText(oferta.getEstado().toString());
        ofertaElement.addContent(estado);

        return ofertaElement;
    }

    private static OfertaDto toOferta(Element ofertaElement)
            throws ParsingException, DataConversionException {

        if (!"oferta".equals(
                ofertaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + ofertaElement.getName() + "' ('oferta' expected)");
        }
        Element identifierElement = ofertaElement.getChild("ofertaId", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String nombre = ofertaElement
                .getChildTextNormalize("nombre", XML_NS);
        
        String descripcion = ofertaElement
                .getChildTextNormalize("descripcion", XML_NS);
        
        Calendar fechaReservar = getExpirationDate(ofertaElement.getChild(
                "fechaReservar", XML_NS));
        
        Calendar fechaReclamar = getExpirationDate(ofertaElement.getChild(
                "fechaReclamar", XML_NS));

        float precioReal = Float.valueOf(
                ofertaElement.getChildTextTrim("precioReal", XML_NS));
        
        float precioDescontado = Float.valueOf(
                ofertaElement.getChildTextTrim("precioDescontado", XML_NS));
        
        String estadoString = String.valueOf(
        		ofertaElement.getChildTextNormalize("estado", XML_NS));
        
        EstadoOferta estado;
		
		if (estadoString.equals(EstadoOferta.INVALIDA.toString())) {
			estado = EstadoOferta.INVALIDA;
		}
		else{
			if (estadoString.equals(EstadoOferta.VALIDA.toString())) {
				estado = EstadoOferta.VALIDA;
			}
			else {
				estado = EstadoOferta.ERRONEO;
			}
		}

        return new OfertaDto(identifier, nombre, descripcion, fechaReservar, fechaReclamar, precioReal, precioDescontado, estado);
    }
    
    private static Element getExpirationDate(Calendar expirationDate, String name) {

        Element releaseDateElement = new Element(name, XML_NS);
        int day = expirationDate.get(Calendar.DAY_OF_MONTH);
        int month = expirationDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = expirationDate.get(Calendar.YEAR);
        
        int hour = expirationDate.get(Calendar.HOUR_OF_DAY);
        int min = expirationDate.get(Calendar.MINUTE);
        int sec = expirationDate.get(Calendar.SECOND);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("min", Integer.toString(min));
        releaseDateElement.setAttribute("sec", Integer.toString(sec));

        return releaseDateElement;

    }
    
    private static Calendar getExpirationDate(Element expirationDateElement)
            throws DataConversionException {

        if (expirationDateElement == null) {
            return null;
        }
        
        int day = expirationDateElement.getAttribute("day").getIntValue();
        int month = expirationDateElement.getAttribute("month").getIntValue();
        int year = expirationDateElement.getAttribute("year").getIntValue();
        
        int hour = expirationDateElement.getAttribute("hour").getIntValue();
        int min = expirationDateElement.getAttribute("min").getIntValue();
        int sec = expirationDateElement.getAttribute("sec").getIntValue();
        
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);
        
        releaseDate.set(Calendar.HOUR_OF_DAY, hour);
        releaseDate.set(Calendar.MINUTE, min);
        releaseDate.set(Calendar.SECOND, sec);

        return releaseDate;

    }

}
