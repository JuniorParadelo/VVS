package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import es.udc.ws.app.dto.MixtoDto;

public class MixtoDtoToSoapMixtoDtoConversor {

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
	
	
	public static es.udc.ws.app.client.service.soap.wsdl.MixtoDto 
		toSoapMixtoDto (MixtoDto mDto) throws DatatypeConfigurationException {
		
		es.udc.ws.app.client.service.soap.wsdl.MixtoDto soapMixtoDto = new es.udc.ws.app.client.service.soap.wsdl.MixtoDto();
		
		soapMixtoDto.setDescripcion(mDto.getDescripcion());
		soapMixtoDto.setPrecioDescontado(mDto.getPrecioDescontado());
		
		XMLGregorianCalendar fecha = toXML(mDto.getFechaReserva());
		
		soapMixtoDto.setFechaReserva(fecha);
		
		return soapMixtoDto;
		
		
		
	}
	
	
	public static MixtoDto toMixtoDto ( es.udc.ws.app.client.service.soap.wsdl.MixtoDto soapMixtoDto) 
		throws DatatypeConfigurationException {
		
		String descripcion = soapMixtoDto.getDescripcion();		
		float precioDescontado = soapMixtoDto.getPrecioDescontado();		
		
		Calendar fecha = fromXML(soapMixtoDto.getFechaReserva());
		
		return new MixtoDto(descripcion, precioDescontado, fecha);
		
	}
	
	
	
	
	public static List<MixtoDto> toMixtoDtos(List<es.udc.ws.app.client.service.soap.wsdl.MixtoDto> soapMixtoDtos) 
		throws DatatypeConfigurationException {
		
		
		List<MixtoDto> mixtos = new ArrayList<>();
		
		for (es.udc.ws.app.client.service.soap.wsdl.MixtoDto m : soapMixtoDtos ) {
		
			MixtoDto mi = toMixtoDto(m);
			mixtos.add(mi);
			
		}
		
		
		return mixtos;
		
		
		
	}
	
}
