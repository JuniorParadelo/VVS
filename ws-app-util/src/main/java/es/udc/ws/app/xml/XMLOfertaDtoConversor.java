package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.dto.OfertaDto;



public class XMLOfertaDtoConversor {

	public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/ofertas/xml");
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	
	public static Document toXML(OfertaDto oDto) throws IOException{
		
		Element ofertaElement = toJDOMElement(oDto);
		
		return new Document(ofertaElement);
	}
	
	public static Document toXML(List<OfertaDto> ofertas) throws IOException {
		
		Element ofertasElement = new Element("ofertas",XML_NS);
		
		for (OfertaDto oDto : ofertas) {
			
			Element ofertaElement = toJDOMElement(oDto);
			ofertasElement.addContent(ofertaElement);
			
		}
		
		return new Document(ofertasElement);
		
	}
	
	
	public static Element toJDOMElement(OfertaDto oDto) {
		
		Element ofertaElement = new Element("oferta",XML_NS);
		
		
		if (oDto.getIdOferta()!=null ) {
			
			Element idElement = new Element("idOferta",XML_NS);
			idElement.setText(oDto.getIdOferta().toString() );
			ofertaElement.addContent(idElement);	
		}
		
		Element nombreElement = new Element("nombreO",XML_NS);
		nombreElement.setText(oDto.getNombre());
		ofertaElement.addContent(nombreElement);
		
		Element descripcionElement = new Element("descripcionO",XML_NS);
		descripcionElement.setText(oDto.getDescripcion());
		ofertaElement.addContent(descripcionElement);
		
		Element fechaLimReservaElement = new Element("fechaLimReserva",XML_NS);
		fechaLimReservaElement.setText( formato.format( oDto.getFechaLimReserva().getTime()) );
		ofertaElement.addContent(fechaLimReservaElement);
		
		Element fechaLimReclamacionElement = new Element("fechaLimReclamacion",XML_NS);
		fechaLimReclamacionElement.setText( formato.format( oDto.getFechaLimReclamacion().getTime()) );
		ofertaElement.addContent(fechaLimReclamacionElement);
		
		Element precioRealElement = new Element("precioRealO",XML_NS);
		precioRealElement.setText( Float.toString(oDto.getPrecioReal() ) );
		ofertaElement.addContent(precioRealElement);
		
		Element precioDescontadoElement = new Element("precioDescontadoO",XML_NS);
		precioDescontadoElement.setText( Float.toString(oDto.getPrecioDescontado()) );
		ofertaElement.addContent(precioDescontadoElement);
		
		
		Element invalida = new Element("invalidaO",XML_NS);
		invalida.setText( Boolean.toString(oDto.isInvalida() ));
		ofertaElement.addContent(invalida);
		
		return ofertaElement;
		
	}
	
	
	public static OfertaDto toOferta(Element ofertaElement) throws ParsingException {
		
		
		if (!"oferta".equals(ofertaElement.getName() ) ) {
			System.out.println("paso por aqui 5");
			throw new ParsingException("Elemento no reconocido "+ofertaElement.getName()+" (se esperaba 'oferta')");
		}
		
		Element idElement = ofertaElement.getChild("idOferta",XML_NS);
		Long id = null;
		
		if (idElement != null) {
			id = Long.valueOf(idElement.getTextTrim() );
		}
		
		String nombre = ofertaElement.getChildTextNormalize("nombreO",XML_NS);
		String descripcion = ofertaElement.getChildTextNormalize("descripcionO",XML_NS);
		
		String f = ofertaElement.getChildTextNormalize("fechaLimReserva",XML_NS);
		Calendar fechaLimReserva = Calendar.getInstance();
		try {
			fechaLimReserva.setTime(formato.parse(f));
		} catch (ParseException e) {
			System.out.println("Paso por aqui 2");
			throw new ParsingException(e);
		}
		
		
		f = ofertaElement.getChildTextNormalize("fechaLimReclamacion",XML_NS);
		Calendar fechaLimReclamacion = Calendar.getInstance();
		try {
			fechaLimReclamacion.setTime(formato.parse(f));
		} catch (ParseException e) {
			System.out.println("Paso por aqui 3");
			throw new ParsingException(e);
		}
		
		
		
		String precio = ofertaElement.getChildTextNormalize("precioRealO", XML_NS);
		float precioReal = Float.parseFloat(precio);
		
		
		String precioDesc = ofertaElement.getChildTextNormalize("precioDescontadoO",XML_NS);
		float precioDescontado = Float.parseFloat(precioDesc);
		
		String inv = ofertaElement.getChildTextNormalize("invalidaO",XML_NS);
		boolean invalida = Boolean.getBoolean(inv);
		
		
		return new OfertaDto(id,nombre,descripcion,fechaLimReserva,fechaLimReclamacion,
				precioReal,precioDescontado,invalida);
		
	}
	
	
	
	public static List<OfertaDto> toOfertas(InputStream ofertasXML) throws ParsingException {
	
		try {
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(ofertasXML);
			Element rootElement = documento.getRootElement();
			
			if (!"ofertas".equalsIgnoreCase(rootElement.getName() ) ) {
				throw new ParsingException("Elemento no reconocido "+rootElement.getName()+" (se esperaba 'ofertas')");
			}
			
			@SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
			List<OfertaDto> ofertasDto = new ArrayList<>(children.size());
			
			for (Element e : children) {
				ofertasDto.add(toOferta(e) );
			}
			
			return ofertasDto;
			
		} catch (ParsingException e) {
			throw e;
		} catch (Exception e) {
			throw new ParsingException(e);			
		}
	}
	
	public static OfertaDto toOferta(InputStream ofertaXML) throws ParsingException {
		
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(ofertaXML);
			
			Element rootElement = documento.getRootElement();
			
			return toOferta(rootElement);			
			
		}
		catch (ParsingException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}

	}
	
}

