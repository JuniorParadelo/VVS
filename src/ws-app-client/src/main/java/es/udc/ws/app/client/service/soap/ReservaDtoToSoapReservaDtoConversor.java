package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import es.udc.ws.app.dto.ReservaDto;;

public class ReservaDtoToSoapReservaDtoConversor {

	public static es.udc.ws.app.client.service.soap.wsdl.ReservaDto toSoapReservaDto(ReservaDto reserva) {
	
		es.udc.ws.app.client.service.soap.wsdl.ReservaDto reservaSalida = new es.udc.ws.app.client.service.soap.wsdl.ReservaDto();
		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar aux = null;
		
		reservaSalida.setReservaId(reserva.getReservaId());
		reservaSalida.setOfertaId(reserva.getOfertaId());
		reservaSalida.setCodigo(reserva.getCodigo());
		reservaSalida.setEmail(reserva.getEmail());
		reservaSalida.setPrecio(reserva.getPrecio());
		reservaSalida.setTarjeta(reserva.getTarjeta());
		
		cal.setTimeInMillis(reserva.getFechaDeReserva().getTimeInMillis());
		try {
			aux = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			reservaSalida.setFechaDeReserva(aux);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al convertir ReservaDTO a SoapReservaDTO");
		}

		return reservaSalida;
	}

	public static ReservaDto toReservaDto(es.udc.ws.app.client.service.soap.wsdl.ReservaDto reserva) {
		
		ReservaDto reservaSalida = new ReservaDto();

		reservaSalida.setReservaId(reserva.getReservaId());
		reservaSalida.setOfertaId(reserva.getOfertaId());
		reservaSalida.setCodigo(reserva.getCodigo());
		reservaSalida.setEmail(reserva.getEmail());
		reservaSalida.setPrecio(reserva.getPrecio());
		reservaSalida.setTarjeta(reserva.getTarjeta());
		reservaSalida.setFechaDeReserva(reserva.getFechaDeReserva().toGregorianCalendar());
		
		return reservaSalida;
	}
	
	public static List<ReservaDto> toReservaDtos(List<es.udc.ws.app.client.service.soap.wsdl.ReservaDto> reservasEntrada) {
		
		List<ReservaDto> reservasSalida = new ArrayList<ReservaDto>();
		for (es.udc.ws.app.client.service.soap.wsdl.ReservaDto o: reservasEntrada) {
			reservasSalida.add(toReservaDto(o));
		}
		return reservasSalida;
	}
}
