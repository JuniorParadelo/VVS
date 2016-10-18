package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import es.udc.ws.app.dto.OfertaDto;

public class OfertaDtoToSoapOfertaDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.OfertaDto toSoapOfertaDto(OfertaDto oferta) {
	
		es.udc.ws.app.client.service.soap.wsdl.OfertaDto ofertaSalida = new es.udc.ws.app.client.service.soap.wsdl.OfertaDto();
		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar aux = null;
		
		ofertaSalida.setOfertaId(oferta.getOfertaId());
		ofertaSalida.setNombre(oferta.getNombre());
		ofertaSalida.setDescripcion(oferta.getDescripcion());
		ofertaSalida.setPrecioDescontado(oferta.getPrecioDescontado());
		ofertaSalida.setPrecioReal(oferta.getPrecioReal());
		
		try {
			cal.setTimeInMillis(oferta.getFechaReclamar().getTimeInMillis());
			aux = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			ofertaSalida.setFechaReclamar(aux);

			cal.setTimeInMillis(oferta.getFechaReservar().getTimeInMillis());
			aux = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			ofertaSalida.setFechaReservar(aux);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al convertir OfertaDTO a SoapOfertaDTO");
		}

		return ofertaSalida;
	}

	public static OfertaDto toOfertaDto(es.udc.ws.app.client.service.soap.wsdl.OfertaDto oferta) {
		
		OfertaDto ofertaSalida = new OfertaDto();
		
		ofertaSalida.setOfertaId(oferta.getOfertaId());
		ofertaSalida.setNombre(oferta.getNombre());
		ofertaSalida.setDescripcion(oferta.getDescripcion());
		ofertaSalida.setFechaReclamar(oferta.getFechaReclamar().toGregorianCalendar());
		ofertaSalida.setFechaReservar(oferta.getFechaReservar().toGregorianCalendar());
		ofertaSalida.setPrecioReal(oferta.getPrecioReal());
		ofertaSalida.setPrecioDescontado(oferta.getPrecioDescontado());
		
		return ofertaSalida;
	}
	
	public static List<OfertaDto> toOfertaDtos(List<es.udc.ws.app.client.service.soap.wsdl.OfertaDto> ofertasEntrada) {
		
		List<OfertaDto> ofertasSalida = new ArrayList<OfertaDto>();
		for (es.udc.ws.app.client.service.soap.wsdl.OfertaDto o: ofertasEntrada) {
			ofertasSalida.add(toOfertaDto(o));
		}
		return ofertasSalida;
	}
}
