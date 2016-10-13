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

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.reserva.EstadoReserva;



public class XMLReservaDtoConversor {

		public final static Namespace XML_NS = Namespace.getNamespace("http://ws.udc.es/reservas/xml");
		private static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		public static Document toXML(ReservaDto rDto) throws IOException{
			Element reservaElement = toJDOMElement(rDto);
			return new Document(reservaElement);
			
		}
		
		public static Document toXML(List<ReservaDto> reservas) throws IOException{
			Element reservasElement = new Element("reservas",XML_NS);
			
			for(ReservaDto rDto : reservas){
				
				Element reservaElement = toJDOMElement(rDto);
				reservasElement.addContent(reservaElement);
			}
			return new Document(reservasElement);
			
		}
	
		
		public static Element toJDOMElement(ReservaDto rDto){
			
			Element reservaElement = new Element("reserva",XML_NS);
			
			if (rDto.getCodigo()!=null){
				Element idElement = new Element("codigo",XML_NS);
				idElement.setText(rDto.getCodigo().toString());
				reservaElement.addContent(idElement);
			}
			Element usuario = new Element("email",XML_NS);
			usuario.setText(rDto.getEmail());
			reservaElement.addContent(usuario);
			
			Element estado = new Element("estado",XML_NS);
			estado.setText(rDto.getEstado().toString());
			reservaElement.addContent(estado);
			
			Element fechaReserva = new Element("fechaReserva",XML_NS);
			fechaReserva.setText(formato.format(rDto.getFechaReserva().getTime() ) );
			reservaElement.addContent(fechaReserva);
			
			Element idOferta = new Element("idOferta",XML_NS);
			idOferta.setText(rDto.getIdOferta().toString());
			reservaElement.addContent(idOferta);
			
			Element precioReserva = new Element("precioReserva",XML_NS);
			precioReserva.setText(Float.toString(rDto.getPrecioReserva()));
			reservaElement.addContent(precioReserva);
			
			Element tarjeta = new Element("tarjeta",XML_NS);
			tarjeta.setText(rDto.getTarjeta());
			reservaElement.addContent(tarjeta);
			
			return reservaElement;
		}
		
		public static ReservaDto toReserva(Element reservaElement) 
				throws ParsingException{
			if (!"reserva".equals(reservaElement.getName())){
				throw new ParsingException("Elemento no reconocido "+reservaElement.getName()+" (esperaba'reserva')");
			}
			
			Element idElement = reservaElement.getChild("codigo", XML_NS);
			Long id = null;
			
			if (idElement!=null){
				id = Long.valueOf(idElement.getTextTrim());
			}
			
			String usuario = reservaElement.getChildTextNormalize("email", XML_NS);
			String estadoString = reservaElement.getChildTextNormalize("estado", XML_NS);
			EstadoReserva estado = EstadoReserva.toEstado(estadoString);
			
			String f = reservaElement.getChildTextNormalize("fechaReserva", XML_NS);
			Calendar fechaReserva = Calendar.getInstance();
			try{
				fechaReserva.setTime(formato.parse(f));
			}catch(ParseException e){
				throw new ParsingException(e);
			}
		
			Long idOferta = Long.valueOf(reservaElement.getChildTextNormalize("idOferta", XML_NS));
			String precio = reservaElement.getChildTextNormalize("precioReserva", XML_NS);
			float precioReserva = Float.parseFloat(precio);
			String tarjeta = reservaElement.getChildTextNormalize("tarjeta", XML_NS);
			
			return new ReservaDto(id, usuario, tarjeta, idOferta, estado, fechaReserva, precioReserva);
			
		}
	
		
		
		
		public static List<ReservaDto> toReservas(InputStream reservasXML) throws ParsingException{
			
			try{
				SAXBuilder builder = new SAXBuilder();
				Document documento = builder.build(reservasXML);
				Element rootElement = documento.getRootElement();
				
				if(!"reservas".equalsIgnoreCase(rootElement.getName())){
					throw new ParsingException("Elemento no reconocido "+rootElement.getName()+" (se esperaba 'reservas')");
				}
				
				@SuppressWarnings("unchecked")
				List<Element> children = rootElement.getChildren();
				List<ReservaDto> reservasDto = new ArrayList<>(children.size());
				
				for(Element e : children){
					reservasDto.add(toReserva(e));
				}
				return reservasDto;
			}catch (ParsingException e){
				throw new ParsingException(e);
				
			}catch (Exception e){
				throw new ParsingException(e);
				
			}
		}
		
		public static ReservaDto toReserva(InputStream reservaXML) throws ParsingException{
			
			try {
				SAXBuilder builder = new SAXBuilder();
				Document documento = builder.build(reservaXML);
				Element rootElement = documento.getRootElement();
				return toReserva(rootElement);
				
			}catch (ParsingException e){
				throw e;
			}catch (Exception e ){
				throw new ParsingException(e);
			}
		}
		
		
}


