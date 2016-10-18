package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class XmlExceptionConversor {

    public final static String CONVERSION_PATTERN =
            "EEE, d MMM yyyy HH:mm:ss Z";

    public final static Namespace XML_NS = XmlOfertaDtoConversor.XML_NS;

    public static InputValidationException
            fromInputValidationExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new InputValidationException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static FechaInvalidaException
		    fromFechaInvalidaExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		    return new FechaInvalidaException();
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }

    public static InstanceNotFoundException
            fromInstanceNotFoundExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType =
                    rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(),
                    instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
    public static OfertaExpirationException
		    fromOfertaExpirationExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element ofertaId = rootElement.getChild("ofertaId", XML_NS);
		    Element fechaReservar = rootElement
                    .getChild("fechaReservar", XML_NS);

            Calendar calendar = null;
            if (fechaReservar != null) {
                SimpleDateFormat sdf =
                        new SimpleDateFormat(CONVERSION_PATTERN,
                        Locale.ENGLISH);
                calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(fechaReservar.getText()));
            }
		
		    return new OfertaExpirationException(Long.parseLong(ofertaId.getTextTrim()),
                    calendar);
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }
    
    public static OfertaInvalidaException
		    fromOfertaInvalidaExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element ofertaId = rootElement.getChild("ofertaId", XML_NS);
		
		    return new OfertaInvalidaException(Long.parseLong(ofertaId.getTextTrim()));
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }

    public static OfertaReservedByUserException
		    fromOfertaReservedByUserExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element ofertaId = rootElement.getChild("ofertaId", XML_NS);
		    Element emailElement =
		            rootElement.getChild("email", XML_NS);
		
		    return new OfertaReservedByUserException(Long.parseLong(ofertaId.getTextTrim()),
		            emailElement.getText());
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }
    
    public static OfertaReservedException
		    fromOfertaReservedExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element ofertaId = rootElement.getChild("ofertaId", XML_NS);
		    
		    return new OfertaReservedException(Long.parseLong(ofertaId.getTextTrim()));
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }

    public static PrecioDescontadoException
		    fromPrecioDescontadoExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element precioDescontado = rootElement.getChild("precioDescontado", XML_NS);
		    
		    return new PrecioDescontadoException(Float.parseFloat(precioDescontado.getTextTrim()));
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }
    
    public static ReservaExpirationException
		    fromReservaExpirationExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element reservaId = rootElement.getChild("reservaId", XML_NS);
		    Element fechaLimite = rootElement
		            .getChild("fechaLimite", XML_NS);
		
		    Calendar calendar = null;
		    if (fechaLimite != null) {
		        SimpleDateFormat sdf =
		                new SimpleDateFormat(CONVERSION_PATTERN,
		                Locale.ENGLISH);
		        calendar = Calendar.getInstance();
		        calendar.setTime(sdf.parse(fechaLimite.getText()));
		    }
		
		    return new ReservaExpirationException(Long.parseLong(reservaId.getTextTrim()),
		            calendar);
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }
    
    
    public static ReservaNoValidaException
		    fromReservaNoValidaExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element reservaId = rootElement.getChild("reservaId", XML_NS);
		
		    return new ReservaNoValidaException(Long.parseLong(reservaId.getTextTrim()));
		} catch (JDOMException | IOException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
    }

    public static Document toInputValidationExceptionXml(
                InputValidationException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InputValidationException", XML_NS);

        Element messageElement = new Element("message", XML_NS);
        messageElement.setText(ex.getMessage());
        exceptionElement.addContent(messageElement);

        return new Document(exceptionElement);
    }
    
    public static Document toFechaInvalidaExceptionXml(
            FechaInvalidaException ex)
        throws IOException {

	    Element exceptionElement =
	            new Element("FechaInvalidaException", XML_NS);
	
	    return new Document(exceptionElement);
	}

    public static Document toInstanceNotFoundExceptionXml (
                InstanceNotFoundException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InstanceNotFoundException", XML_NS);

        if(ex.getInstanceId() != null) {
            Element instanceIdElement = new Element("instanceId", XML_NS);
            instanceIdElement.setText(ex.getInstanceId().toString());

            exceptionElement.addContent(instanceIdElement);
        }

        if(ex.getInstanceType() != null) {
            Element instanceTypeElement = new Element("instanceType", XML_NS);
            instanceTypeElement.setText(ex.getInstanceType());

            exceptionElement.addContent(instanceTypeElement);
        }
        return new Document(exceptionElement);
    }
    
    public static Document toOfertaExpirationExceptionXml (
            OfertaExpirationException ex)
        throws IOException {

	    Element exceptionElement =
	            new Element("OfertaExpirationException", XML_NS);
	
	    if(ex.getOfertaId() != null) {
	        Element instanceIdElement = new Element("ofertaId", XML_NS);
	        instanceIdElement.setText(ex.getOfertaId().toString());
	
	        exceptionElement.addContent(instanceIdElement);
	    }
	
	    if(ex.getFechaReservar() != null) {
	        SimpleDateFormat dateFormatter =
	                new SimpleDateFormat(CONVERSION_PATTERN,
	                    Locale.ENGLISH);
	
	        Element fechaReservarElement = new
	                Element("fechaReservar", XML_NS);
	        fechaReservarElement.setText(dateFormatter.format(
	                ex.getFechaReservar().getTime()));
	
	        exceptionElement.addContent(fechaReservarElement);
	    }
	    
	    return new Document(exceptionElement);
    }
    
    public static Document toOfertaInvalidaExceptionXml (
            OfertaInvalidaException ex)
        throws IOException {

	    Element exceptionElement =
	            new Element("OfertaInvalidaException", XML_NS);
	
	    if(ex.getOfertaId() != null) {
	        Element instanceIdElement = new Element("ofertaId", XML_NS);
	        instanceIdElement.setText(ex.getOfertaId().toString());
	
	        exceptionElement.addContent(instanceIdElement);
	    }
	    
	    return new Document(exceptionElement);
    }
    
    
    public static Document toOfertaReservedByUserExceptionXml (
            OfertaReservedByUserException ex)
        throws IOException {

		    Element exceptionElement =
		            new Element("OfertaReservedByUserExceptionException", XML_NS);
		
		    if(ex.getOfertaId() != null) {
		        Element instanceIdElement = new Element("ofertaId", XML_NS);
		        instanceIdElement.setText(ex.getOfertaId().toString());
		
		        exceptionElement.addContent(instanceIdElement);
		    }
		
		    if(ex.getEmail() != null) {
		        Element emailElement = new Element("email", XML_NS);
		        emailElement.setText(ex.getEmail());
		
		        exceptionElement.addContent(emailElement);
		    }
		    return new Document(exceptionElement);
	}   

	public static Document toOfertaReservedExceptionXml (
	        OfertaReservedException ex)
	    throws IOException {
	
		    Element exceptionElement =
		            new Element("OfertaReservedException", XML_NS);
		
		    if(ex.getOfertaId() != null) {
		        Element instanceIdElement = new Element("ofertaId", XML_NS);
		        instanceIdElement.setText(ex.getOfertaId().toString());
		
		        exceptionElement.addContent(instanceIdElement);
		    }
		
		    return new Document(exceptionElement);
	} 
	
	
	public static Document toPrecioDescontadoExceptionXml (
	        PrecioDescontadoException ex)
	    throws IOException {
	
		    Element exceptionElement =
		            new Element("PrecioDescontadoException", XML_NS);
		
		    Element precioDescontadoElement = new Element("precioDescontado", XML_NS);
		    precioDescontadoElement.setText(Float.toString(ex.getPrecioDescontado()));
		
		    exceptionElement.addContent(precioDescontadoElement);
		
		    return new Document(exceptionElement);
	} 
	
    public static Document toReservaExpirationExceptionXml (
            ReservaExpirationException ex)
        throws IOException {

	    Element exceptionElement =
	            new Element("ReservaExpirationException", XML_NS);
	
	    if(ex.getReservaId() != null) {
	        Element reservaIdElement = new Element("reservaId", XML_NS);
	        reservaIdElement.setText(ex.getReservaId().toString());
	
	        exceptionElement.addContent(reservaIdElement);
	    }
	
	    if(ex.getFechaLimite() != null) {
	        SimpleDateFormat dateFormatter =
	                new SimpleDateFormat(CONVERSION_PATTERN,
	                    Locale.ENGLISH);
	
	        Element fechaLimiteElement = new
	                Element("fechaLimite", XML_NS);
	        fechaLimiteElement.setText(dateFormatter.format(
	                ex.getFechaLimite().getTime()));
	
	        exceptionElement.addContent(fechaLimiteElement);
	    }
	    
	    return new Document(exceptionElement);
    }
    
    public static Document toReservaNoValidaExceptionXml (
            ReservaNoValidaException ex)
        throws IOException {

	    Element exceptionElement =
	            new Element("ReservaNoValidaException", XML_NS);
	
	    if(ex.getReservaId() != null) {
	        Element reservaIdElement = new Element("reservaId", XML_NS);
	        reservaIdElement.setText(ex.getReservaId().toString());
	
	        exceptionElement.addContent(reservaIdElement);
	    }
	    
	    return new Document(exceptionElement);
    }

}


