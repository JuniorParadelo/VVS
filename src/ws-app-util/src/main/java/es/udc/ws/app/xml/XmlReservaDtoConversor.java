package es.udc.ws.app.xml;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.enums.EstadoReserva;

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

public class XmlReservaDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/reservas/xml");

    public static Document toResponse(ReservaDto reserva)
            throws IOException {

        Element reservaElement = toXml(reserva);

        return new Document(reservaElement);
    }

    public static ReservaDto toReserva(InputStream reservaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();

            return toReserva(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Document toXml(List<ReservaDto> reserva) throws IOException {

        Element reservasElement = new Element("reservas", XML_NS);
        for (int i = 0; i < reserva.size(); i++) {
            ReservaDto xmlReservaDto = reserva.get(i);
            Element reservaElement = toXml(xmlReservaDto);
            reservasElement.addContent(reservaElement);
        }

        return new Document(reservasElement);
    }

    public static Element toXml(ReservaDto reserva) {

        Element reservaElement = new Element("reserva", XML_NS);

        if (reserva.getReservaId() != null) {
            Element reservaIdElement = new Element("reservaId", XML_NS);
            reservaIdElement.setText(reserva.getReservaId().toString());
            reservaElement.addContent(reservaIdElement);
        }

        if (reserva.getOfertaId() != null) {
            Element ofertaIdElement = new Element("ofertaId", XML_NS);
            ofertaIdElement.setText(reserva.getOfertaId().toString());
            reservaElement.addContent(ofertaIdElement);
        }
        
        if (reserva.getEmail() != null) {
            Element emailElement = new Element("email", XML_NS);
            emailElement.setText(reserva.getEmail());
            reservaElement.addContent(emailElement);
        }
        
        if (reserva.getTarjeta() != null) {
            Element tarjetaElement = new Element("tarjeta", XML_NS);
            tarjetaElement.setText(reserva.getTarjeta());
            reservaElement.addContent(tarjetaElement);
        }

        if (reserva.getFechaDeReserva() != null) {
            Element fechaDeReservaElement = getFechaDeReserva(reserva.getFechaDeReserva());
            reservaElement.addContent(fechaDeReservaElement);
        }
        
        Element codigoElement = new Element("codigo", XML_NS);
        codigoElement.setText(Integer.toString(reserva.getCodigo()));
        reservaElement.addContent(codigoElement);
        
        Element precioElement = new Element("precio", XML_NS);
        precioElement.setText(Float.toString(reserva.getPrecio()));
        reservaElement.addContent(precioElement);
        
        Element estadoElement = new Element("estado", XML_NS);
        estadoElement.setText(reserva.getEstado().toString());
        reservaElement.addContent(estadoElement);

        return reservaElement;
    }

    private static ReservaDto toReserva(Element reservaElement)
            throws ParsingException, DataConversionException,
            NumberFormatException {
        if (!"reserva".equals(reservaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + reservaElement.getName() + "' ('reserva' expected)");
        }
        Element reservaIdElement = reservaElement.getChild("reservaId", XML_NS);
        Long reservaId = null;
        if (reservaIdElement != null) {
            reservaId = Long.valueOf(reservaIdElement.getTextTrim());
        }

        Element ofertaIdElement = reservaElement.getChild("ofertaId", XML_NS);
        Long ofertaId = null;
        if (ofertaIdElement != null) {
            ofertaId = Long.valueOf(ofertaIdElement.getTextTrim());
        }
     
        String email = reservaElement.getChildTextTrim("email", XML_NS);
        
        String tarjeta = reservaElement.getChildTextTrim("tarjeta", XML_NS);

        Calendar fechaDeReserva = getFechaDeReserva(reservaElement.getChild(
                "fechaDeReserva", XML_NS));
        
        int codigo = Integer.valueOf(reservaElement.getChildTextTrim("codigo", XML_NS));
        
        float precio = Float.valueOf(reservaElement.getChildTextTrim("precio", XML_NS));
        
        String estadoString = reservaElement.getChildTextTrim("estado", XML_NS);
        EstadoReserva estado;
		
		if (estadoString.equals(EstadoReserva.INVALIDA.toString())) {
			estado = EstadoReserva.INVALIDA;
		}
		else{
			if (estadoString.equals(EstadoReserva.VALIDA.toString())) {
				estado = EstadoReserva.VALIDA;
			}
			else {
				if (estadoString.equals(EstadoReserva.RECLAMADA.toString())) {
					estado = EstadoReserva.RECLAMADA;
				}
				else estado = EstadoReserva.ERRONEO;
			}
			
		}

        return new ReservaDto(reservaId, ofertaId, email, tarjeta, fechaDeReserva, codigo, precio, estado);
    }

    public static List<ReservaDto> toReservas(InputStream reservaXml) throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();

            if(!"reservas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('reservas' expected)");
            }
            List<Element> children = rootElement.getChildren();
            List<ReservaDto> reservaaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                reservaaDtos.add(toReserva(element));
            }

            return reservaaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static Calendar getFechaDeReserva(Element fechaDeReservaElement)
            throws DataConversionException {

        if (fechaDeReservaElement == null) {
            return null;
        }
        
        Calendar releaseDate = Calendar.getInstance();
        
        int day = fechaDeReservaElement.getAttribute("day").getIntValue();
        int month = fechaDeReservaElement.getAttribute("month").getIntValue();
        int year = fechaDeReservaElement.getAttribute("year").getIntValue();
        int hour = fechaDeReservaElement.getAttribute("hour").getIntValue();
        int min = fechaDeReservaElement.getAttribute("min").getIntValue();
        int sec = fechaDeReservaElement.getAttribute("sec").getIntValue();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);
        releaseDate.set(Calendar.HOUR_OF_DAY, hour);
        releaseDate.set(Calendar.MINUTE, min);
        releaseDate.set(Calendar.SECOND, sec);

        return releaseDate;

    }

    private static Element getFechaDeReserva(Calendar fechaDeReserva) {

        Element releaseDateElement = new Element("fechaDeReserva", XML_NS);
        
        int day = fechaDeReserva.get(Calendar.DAY_OF_MONTH);
        int month = fechaDeReserva.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = fechaDeReserva.get(Calendar.YEAR);
        int hour = fechaDeReserva.get(Calendar.HOUR_OF_DAY);
        int min = fechaDeReserva.get(Calendar.MINUTE);
        int sec = fechaDeReserva.get(Calendar.SECOND);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("min", Integer.toString(min));
        releaseDateElement.setAttribute("sec", Integer.toString(sec));

        return releaseDateElement;

    }

}
