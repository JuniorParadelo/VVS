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

import es.udc.ws.app.dto.MixtoDto;



public class XMLMixtoDtoConversor {

	public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/mixtos/xml");
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	
	public static Document toXML(MixtoDto mDto) throws IOException {
		
		Element mixtoElement = toJDOMElement(mDto);
		
		return new Document(mixtoElement);
		
	}
	
	
	
	public static Document toXML(List<MixtoDto> mixtos) throws IOException {
		
		Element mixtosElement = new Element("mixtos",XML_NS);
		
		for (MixtoDto m : mixtos) {
			
			Element mixtoElement = toJDOMElement(m);
			mixtosElement.addContent(mixtoElement);
			
		}
		
		return new Document(mixtosElement);
		
	}
	
	
	
	
	public static Element toJDOMElement(MixtoDto mDto) {
		
		Element mixtoDtoElement = new Element("mixto",XML_NS);
		
		Element precioReservaElement = new Element("precioDescontado",XML_NS);
		precioReservaElement.setText( Float.toString((mDto.getPrecioDescontado()) ) );
		mixtoDtoElement.addContent(precioReservaElement);
		
		Element descripcionElement = new Element("descripcion",XML_NS);
		descripcionElement.setText(mDto.getDescripcion());
		mixtoDtoElement.addContent(descripcionElement);
		
		Element fechaReservaElement = new Element("fechaReserva",XML_NS);
		String f = formato.format( mDto.getFechaReserva().getTime() );
		fechaReservaElement.setText(f);
		mixtoDtoElement.addContent(fechaReservaElement);
		
		return mixtoDtoElement;
		
		
	}
	
	
	
	
	
	public static MixtoDto toMixto(Element mixtoElement) throws ParsingException{
		
		try { 
			
			/*Comprobamos que el dato que nos viene es el tipo de dato que esperamos */
			
			if (!"mixto".equals(mixtoElement.getName() ) ) {
				throw new ParsingException("Elemento no reconocido "+mixtoElement.getName()+" (se esperaba 'mixto'");
				
			}
			
			/*Recogemos sus parametros y creamos el nuevo Dto */
			
			String descripcion = mixtoElement.getChildTextNormalize("descripcion", XML_NS);
			
			String p = mixtoElement.getChildTextNormalize("precioDescontado", XML_NS);
			float precioDescontado = Float.parseFloat(p);
			
			String f = mixtoElement.getChildTextNormalize("fechaReserva", XML_NS);
			Calendar fechaReserva = Calendar.getInstance();
			
			fechaReserva.setTime( formato.parse(f) );
			
			return new MixtoDto(descripcion, precioDescontado, fechaReserva);
			
		} catch (ParseException e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	public static List<MixtoDto> toMixtos(InputStream entrante) throws ParsingException {
		
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			Element rootElement = documento.getRootElement();
			
			if (!"mixtos".equals(rootElement.getName() ) ) {
				throw new ParsingException("Elemento no reconocido "+rootElement.getName()+" (se esperaba 'mixtos')");
			}
			
			@SuppressWarnings("unchecked")
			List<Element> elementos = rootElement.getChildren();
			List<MixtoDto> mixtos = new ArrayList<>();
			
			for (Element e : elementos) {
				mixtos.add( toMixto(e) );
			}
			
			return mixtos;			
		
		}
		catch (ParsingException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	
	public static MixtoDto toMixto(InputStream entrante) throws ParsingException {
		
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			return toMixto(rootElement);			
		}
		catch (ParsingException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ParsingException(e);
		}
		
	}
	
	
	
	
	
	
}


