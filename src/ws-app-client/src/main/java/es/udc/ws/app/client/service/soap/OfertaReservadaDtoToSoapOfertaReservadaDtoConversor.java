package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import es.udc.ws.app.dto.OfertaReservadaDto;

public class OfertaReservadaDtoToSoapOfertaReservadaDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto toSoapOfertaReservadaDto(OfertaReservadaDto oferta) {
	
		es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto ofertaSalida = new es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto();
		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar aux = null;
		
		ofertaSalida.setDescripcion(oferta.getDescripcion());
		ofertaSalida.setPrecioDescontado(oferta.getPrecioDescontado());
		
		cal.setTimeInMillis(oferta.getFechaDeReserva().getTimeInMillis());
		try {
			aux = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			ofertaSalida.setFechaDeReserva(aux);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al convertir OfertaReservadaDTO a SoapOfertaReservadaDTO");
		}

		return ofertaSalida;
	}

	public static OfertaReservadaDto toOfertaReservadaDto(es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto oferta) {
		
		OfertaReservadaDto ofertaSalida = new OfertaReservadaDto();
		
		ofertaSalida.setDescripcion(oferta.getDescripcion());
		ofertaSalida.setPrecioDescontado(oferta.getPrecioDescontado());
		ofertaSalida.setFechaDeReserva(oferta.getFechaDeReserva().toGregorianCalendar());
		
		return ofertaSalida;
	}
	
	public static List<OfertaReservadaDto> toOfertaDtos(List<es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto> ofertasEntrada) {
		
		List<OfertaReservadaDto> ofertasSalida = new ArrayList<OfertaReservadaDto>();
		for (es.udc.ws.app.client.service.soap.wsdl.OfertaReservadaDto o: ofertasEntrada) {
			ofertasSalida.add(toOfertaReservadaDto(o));
		}
		return ofertasSalida;
	}
}
