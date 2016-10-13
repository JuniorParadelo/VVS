package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.reserva.EstadoReserva;

public class ReservaDtoToSoapReservaDtoConversor {

	public static Calendar fromXML(XMLGregorianCalendar xc)
			  throws DatatypeConfigurationException {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(xc.toGregorianCalendar().getTimeInMillis());
		return c;
	}
	
	
	public static XMLGregorianCalendar toXML(Calendar c)
			  throws DatatypeConfigurationException {
	
		 GregorianCalendar gc = new GregorianCalendar();
		 gc.setTimeInMillis(c.getTimeInMillis());
		 XMLGregorianCalendar xc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		 return xc;

	 }
	
	
	public static es.udc.ws.app.client.service.soap.wsdl.ReservaDto
		toSoapReservaDto(ReservaDto rDto) throws DatatypeConfigurationException {
		
		es.udc.ws.app.client.service.soap.wsdl.ReservaDto soapReservaDto;
		
		soapReservaDto = new es.udc.ws.app.client.service.soap.wsdl.ReservaDto();
		soapReservaDto.setCodigo(rDto.getCodigo());
		soapReservaDto.setEmail(rDto.getEmail());
		
		switch (rDto.getEstado() ) {
		
			case ANULADA :
				soapReservaDto.setEstado( es.udc.ws.app.client.service.soap.wsdl.EstadoReserva.ANULADA );
				break;
				
			case RECLAMADA :
				soapReservaDto.setEstado( es.udc.ws.app.client.service.soap.wsdl.EstadoReserva.RECLAMADA );
				break;
				
			case PENDIENTE :
				soapReservaDto.setEstado( es.udc.ws.app.client.service.soap.wsdl.EstadoReserva.PENDIENTE );
				break;
		
		}
		
		soapReservaDto.setIdOferta(rDto.getIdOferta());
		soapReservaDto.setPrecioReserva(rDto.getPrecioReserva());
		soapReservaDto.setTarjeta(rDto.getTarjeta());
		
		XMLGregorianCalendar gregoriano = toXML(rDto.getFechaReserva() );
		
		soapReservaDto.setFechaReserva( gregoriano );
		
		return soapReservaDto;
		
	}
	
	
	public static ReservaDto toReservaDto (es.udc.ws.app.client.service.soap.wsdl.ReservaDto soapReservaDto) 
		throws DatatypeConfigurationException {
		
		Long codigo = soapReservaDto.getCodigo();
		Long idOferta = soapReservaDto.getIdOferta();
		String email = soapReservaDto.getEmail();
		float precioReserva = soapReservaDto.getPrecioReserva();
		String tarjeta = soapReservaDto.getTarjeta();
		EstadoReserva estado;
		
		switch (soapReservaDto.getEstado() ) {
		
			case ANULADA :
				estado=EstadoReserva.ANULADA;
				break;
				
			case RECLAMADA :
				estado=EstadoReserva.RECLAMADA;
				break;
				
			case PENDIENTE :
				estado=EstadoReserva.PENDIENTE;
				break;
				
			default:
				throw new RuntimeException("Nos encontramos ante un estado inesperado");
		}
		
		Calendar fecha = fromXML(soapReservaDto.getFechaReserva() );
		
		return new ReservaDto(codigo,email,tarjeta,idOferta,estado,fecha,precioReserva);
		
		
	}
	
	
	public static List<ReservaDto> toReservasDtos(List<es.udc.ws.app.client.service.soap.wsdl.ReservaDto> soapReservasDto)
			throws DatatypeConfigurationException{
		
		
		List<ReservaDto> reservas = new ArrayList<>();
		
		for (es.udc.ws.app.client.service.soap.wsdl.ReservaDto r : soapReservasDto) {
			ReservaDto rDto = toReservaDto(r);
			
			reservas.add(rDto);
		}
		
		return reservas;
		
	}
	
	
}
