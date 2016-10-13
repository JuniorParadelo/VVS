package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;



public class XMLExceptionConversor {

	private final static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private final static Namespace XML_NS = XMLOfertaDtoConversor.XML_NS; 
	
	
	public static InputValidationException fromInputValidationException(InputStream entrante) throws ParsingException{
		
		try {
			/* Parseamos el Stream entrante a un documento y recogemos su tag raiz*/
			
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			/*Recogemos los parametros de la raiz y creamos la excepcion a partir de ellos*/
			
			Element mensaje = rootElement.getChild("mensaje", XML_NS);
			
			return new InputValidationException(mensaje.getText());
			
		} catch ( JDOMException | IOException e) {
			throw new ParsingException(e);
			
		} catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	public static Document toInputValidationExceptionXML(InputValidationException e) throws IOException {
		
		/* Creamos un nuveo tag con el nombre de la excepcion y le introducimos los parametros*/
		Element excepcionElement = new Element("InputValidationException",XML_NS);
		
		Element messageElement = new Element("mensaje",XML_NS);
		messageElement.setText(e.getMessage() );
		excepcionElement.addContent(messageElement);
		
		/* Creamos el nuevo documento a partir del tag raiz*/
		return new Document(excepcionElement);
		
	}
	
								//-----------------InstanceNotFoundException------------------------
	
	
	public static InstanceNotFoundException fromInstanceNotFoundException(InputStream entrante) throws ParsingException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			Element rootElement = documento.getRootElement();
			
			Element instanceId = rootElement.getChild("instanceID",XML_NS);
			Element instanceType = rootElement.getChild("instanceType",XML_NS);
			
			return new InstanceNotFoundException(instanceId.getText(),instanceType.getText() );
			
		}
		catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	public static Document toInstanceNotFoundExceptionXML(InstanceNotFoundException ex) {
		
		Element excepcionElement = new Element("InstanceNotFoundException",XML_NS);
		
		if (ex.getInstanceId() != null ) {
			Element instanceIDElement = new Element("instanceID",XML_NS);
			instanceIDElement.setText(ex.getInstanceId().toString() );
			
			excepcionElement.addContent(instanceIDElement);			
		}
		
		if (ex.getInstanceType() != null ) {
			
			Element instanceTypeElement = new Element("instanceType",XML_NS);
			instanceTypeElement.setText(ex.getInstanceType() );
			
			excepcionElement.addContent(instanceTypeElement );
		}
		
		return new Document(excepcionElement);
		
	}
	
	
								//---------------------FechaInvalidaException---------------------------
	
	
	
	public static FechaInvalidaException fromFechaInvalidaException(InputStream entrante) throws ParsingException{
		
		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(entrante);
			Element raiz = doc.getRootElement();
			
			Element fecha = raiz.getChild("fechaInv", XML_NS);
			
			String texto = fecha.getText();
			
			Calendar fechaInvalida = Calendar.getInstance();
			fechaInvalida.setTime(formato.parse(texto));
			
			return new FechaInvalidaException(fechaInvalida);
			
		}catch (JDOMException | IOException e){
			throw new ParsingException(e);
			
		}catch (Exception e){
			throw new ParsingException(e);
			
		}
	}
	
	public static Document toFechaInvalidaException(FechaInvalidaException e){
		
		Element exception =new Element ("FechaInvalidaException",XML_NS);
		if (e.getFecha()!=null){
			String f = formato.format(e.getFecha().getTime());
			
			Element fecha = new Element("fechaInv", XML_NS);
			
			fecha.setText(f);
			
			exception.addContent(fecha);
		}
		return new Document(exception);
	}
	
	
							//----------------------FechaReservaExpiradaException----------------------------
	
	
	public static FechaReservaExpiradaException fromFechaReservaExpiradaException(InputStream entrante)
			throws ParsingException {
		
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			Element rootElement = documento.getRootElement();
			
			Element fecha = rootElement.getChild("fecha", XML_NS);
			String texto = fecha.getText();
			
			Calendar fechaReserva = Calendar.getInstance();
			fechaReserva.setTime( formato.parse(texto) );
			
			return new FechaReservaExpiradaException(fechaReserva);
			
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		
		} catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	public static Document toFechaReservaExpiradaException(FechaReservaExpiradaException ex) {
		
		Element excepcionElement = new Element("FechaReservaExpiradaException",XML_NS);
		
		if (ex.getFecha() != null) {
			
			String f = formato.format( ex.getFecha().getTime() );
			Element fechaElement = new Element("fecha",XML_NS);
			fechaElement.setText(f);
			
			excepcionElement.addContent(fechaElement);
		}
		
		return new Document(excepcionElement);		
		
	}
	
	
	
							//----------------------HayReservasException----------------------------
	
	
	
	public static HayReservasException fromHayReservasException(InputStream entrante)
		throws ParsingException{
		
		try{
			SAXBuilder  builder = new SAXBuilder();
			Document doc = builder.build(entrante);
			Element raiz = doc.getRootElement();
			Element idOf = raiz.getChild("idOferta", XML_NS);
			Long id = Long.parseLong(idOf.getText());
			return new HayReservasException(id);
			
		}catch (JDOMException | IOException e){
			throw new ParsingException(e);
			
		}catch (Exception e){
			throw new ParsingException(e);
			
		}
	}
	
	public static Document toHayReservasException(HayReservasException e){
		Element exception = new Element("HayReservasException", XML_NS);
		
		if(e.getIDOferta()!=null){
			
			Element idOf =  new Element("idOferta", XML_NS);
			idOf.setText(e.getIDOferta().toString());
			exception.addContent(idOf);
		}
		
		return new Document(exception);
	}
						//------------------------OfertaYaInvalidadaException--------------------------
	
	
	
	public static OfertaYaInvalidadaException fromOfertaYaInvalidadaException(InputStream entrante) 
			throws ParsingException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			Element idOfertaElement = rootElement.getChild("idOferta", XML_NS);
			
			Long idOferta = Long.parseLong(idOfertaElement.getText() ); 
			
			
			return new OfertaYaInvalidadaException(idOferta);
						
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		
		} catch (Exception e) {
			throw new ParsingException(e);
			
		}
	}
	
	
	public static Document toOfertaYaInvalidadaException(OfertaYaInvalidadaException ex) {
		
		Element excepcionElement = new Element("OfertaYaInvalidadaException",XML_NS);
		
		if (ex.getIdOferta() != null ) {
			
			Element idOfertaElement = new Element("idOferta",XML_NS);
			idOfertaElement.setText( ex.getIdOferta().toString() );
			
			excepcionElement.addContent(idOfertaElement);
			
		}
		
		return new Document(excepcionElement);
		
	}
	
							//-------------------OfertaYaReservadaException-----------------------------
	
	
	public static OfertaYaReservadaException fromOfertaYaReservadaException(InputStream entrante) 
			throws ParsingException {
		
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement  = documento.getRootElement();
			
			Element idOfertaElement = rootElement.getChild("idOferta", XML_NS);
			
			Long idOferta = Long.parseLong(idOfertaElement.getText());
			
			return new OfertaYaReservadaException(idOferta);		
		}
		catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}		
		
	}
	
	
	public static Document toOfertaYaReservadaException(OfertaYaReservadaException ex) {
		
		Element excepcionElement = new Element("OfertaYaReservadaException",XML_NS);
		
		if (ex.getIdOferta() != null) {
			
			String id = ex.getIdOferta().toString();
			Element idOfertaElement = new Element("idOferta",XML_NS);
			idOfertaElement.setText(id);
			
			excepcionElement.addContent(idOfertaElement);			
		}
		
		return new Document(excepcionElement);
		
	}
	
	
	
						//------------------------  PrecioInvalidoException ----------------
	
	
	public static PrecioInvalidoException fromPrecioInvalidoException(InputStream entrante) throws ParsingException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			Element precioInvElement = rootElement.getChild("precioInvalido", XML_NS);
			float precio = Float.parseFloat(precioInvElement.getText());
			
			return new PrecioInvalidoException(precio);
			
		}
		catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
	}
	
	public static Document toPrecioInvalidoException(PrecioInvalidoException ex) {
		
		Element excepcionElement = new Element("PrecioInvalidoException",XML_NS);
		
		Element precioElement = new Element("precioInvalido",XML_NS);
		precioElement.setText( Float.toString(ex.getPrecio() ) );
		
		excepcionElement.addContent(precioElement);
		
		return new Document(excepcionElement);
	}
	
	
	
	//------------------------------------------  ReclamacionAnteriorReserva -----------------------
	
	
	
	public static ReclamacionAnteriorReservaException fromReclamacionAnteriorReservaException(InputStream entrante)
		throws ParsingException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			Element fechaLimReservaElement = rootElement.getChild("fechaLimReserva", XML_NS);
			Element fechaLimReclamacionElement = rootElement.getChild("fechaLimReclamacion",XML_NS);
			
			Calendar fechaLimReserva = Calendar.getInstance();
			Calendar fechaLimReclamacion = Calendar.getInstance();
			
			fechaLimReserva.setTime( formato.parse(fechaLimReservaElement.getText() ) );
			fechaLimReclamacion.setTime( formato.parse(fechaLimReclamacionElement.getText() ) );
			
			return new ReclamacionAnteriorReservaException(fechaLimReclamacion, fechaLimReserva);
			
		}
		catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	
	public static Document toReclamacionAnteriorReservaException(ReclamacionAnteriorReservaException ex) {
		
		Element excepcionElement = new Element("ReclamacionAnteriorReservaException",XML_NS);
		
		if (ex.getFechaLimReserva() != null) {
			
			String f = formato.format( ex.getFechaLimReserva().getTime() );
			Element fechaLimReservaElement = new Element("fechaLimReserva",XML_NS);
			fechaLimReservaElement.setText(f);
			
			excepcionElement.addContent(fechaLimReservaElement);			
			
		}
		
		if (ex.getFechaLimReclamacion() != null) {
			
			String f = formato.format( ex.getFechaLimReserva().getTime() );
			Element fechaLimReclamacionElement = new Element("fechaLimReclamacion",XML_NS);
			fechaLimReclamacionElement.setText(f);
			
			excepcionElement.addContent(fechaLimReclamacionElement);
		}
		
		return new Document(excepcionElement);
		
	}
	
	
	// --------------------------- ReservaYaReclamadaException ----------------------
	
	public static ReservaYaReclamadaException fromReservaYaReclamadaException(InputStream entrante) throws ParsingException {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			Element codigoElement = rootElement.getChild("codigo", XML_NS);
			
			Long codigo = Long.parseLong( codigoElement.getText() );
			
			return new ReservaYaReclamadaException(codigo);
		}
		catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	
	public static Document toReservaYaReclamadaException(ReservaYaReclamadaException ex) {
		
		Element excepcionElement = new Element("ReservaYaReclamadaException",XML_NS);
		
		if (ex.getCodigo() != null) {
			
			Element codigoElement = new Element("codigo",XML_NS);
			codigoElement.setText( ex.getCodigo().toString() );
			
			excepcionElement.addContent(codigoElement);
			
		}
		
		return new Document(excepcionElement);
		
	}
	
}

