package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.dto.OfertaDto;

public class OfertaDtoToSoapOfertaDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.OfertaDto toSoapOfertaDto( OfertaDto o)
			throws DatatypeConfigurationException {
		
		try {
		
			/*Generamos una OfertaDto del WSDL y le introducimos los datos para mandar*/
			
			es.udc.ws.app.client.service.soap.wsdl.OfertaDto soapOfertaDto = new es.udc.ws.app.client.service.soap.wsdl.OfertaDto();
			
			soapOfertaDto.setIdOferta(o.getIdOferta());
			soapOfertaDto.setNombre(o.getNombre());
			soapOfertaDto.setDescripcion(o.getDescripcion());
			soapOfertaDto.setFechaLimReserva(toXML(o.getFechaLimReserva()));
			soapOfertaDto.setFechaLimReclamacion(toXML(o
					.getFechaLimReclamacion()));
			soapOfertaDto.setPrecioReal(o.getPrecioReal());
			soapOfertaDto.setPrecioDescontado(o.getPrecioDescontado());
			soapOfertaDto.setInvalida(o.isInvalida());
			
			return soapOfertaDto;
		
		}
		catch (DatatypeConfigurationException e) {
			throw new DatatypeConfigurationException();
		
		}
	}

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
		XMLGregorianCalendar xc = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(gc);
		return xc;

	}

	
	public static OfertaDto toOfertaDto(es.udc.ws.app.client.service.soap.wsdl.OfertaDto oferta)
			throws DatatypeConfigurationException {
		
		try {
			
			return new OfertaDto(oferta.getIdOferta(), oferta.getNombre(),
					oferta.getDescripcion(),
					fromXML(oferta.getFechaLimReserva()),
					fromXML(oferta.getFechaLimReclamacion()),
					oferta.getPrecioReal(), oferta.getPrecioDescontado(),
					oferta.isInvalida());
			
		}
		catch (DatatypeConfigurationException e) {
			throw new DatatypeConfigurationException();
		}

	}

	public static List<OfertaDto> toOfertaDtos(List<es.udc.ws.app.client.service.soap.wsdl.OfertaDto> ofertas)
			throws DatatypeConfigurationException {
		
		List<OfertaDto> ofertaDtos = new ArrayList<>();

		for (int i = 0; i < ofertas.size(); i++) {
			es.udc.ws.app.client.service.soap.wsdl.OfertaDto oferta = ofertas.get(i);
			
			ofertaDtos.add(toOfertaDto(oferta));

		}
		
		return ofertaDtos;
	}

}
