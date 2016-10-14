package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.dto.BookingDto;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class BookingDtoToSoapBookingDtoConversor {
	
	public static es.udc.ws.app.client.service.soap.wsdl.BookingDto toSoapBookingDto (BookingDto bookingDto){
		es.udc.ws.app.client.service.soap.wsdl.BookingDto soapBookingDto =new es.udc.ws.app.client.service.soap.wsdl.BookingDto();
			
			soapBookingDto.setOfferId(bookingDto.getOfferId());
			soapBookingDto.setUserId(bookingDto.getUserId());
			soapBookingDto.setCreditCardNumber(bookingDto.getCreditCardNumber());
			GregorianCalendar ModifDateGregorianCalendar = new GregorianCalendar(); 
			ModifDateGregorianCalendar.setTime(bookingDto.getBookingDate().getTime());
			try {
				soapBookingDto.setBookingDate(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(ModifDateGregorianCalendar));
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
			GregorianCalendar ModifDateGregorianCalendar2 = new GregorianCalendar(); 
			ModifDateGregorianCalendar2.setTime(bookingDto.getEnjoymentDate().getTime());
			try {
				soapBookingDto.setEnjoymentDate(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(ModifDateGregorianCalendar2));
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
			
			 return soapBookingDto;
	}
	
	public static BookingDto toBookingDto (es.udc.ws.app.client.service.soap.wsdl.BookingDto BookingDto){
		return new BookingDto(BookingDto.getOfferId(),BookingDto.getOfferId(),BookingDto.getUserId(),
				BookingDto.getCreditCardNumber(),BookingDto.getBookingDate().toGregorianCalendar(),
				BookingDto.getEnjoymentDate().toGregorianCalendar());
	}
	
	public static List<BookingDto> toBookindDto (List<es.udc.ws.app.client.service.soap.wsdl.BookingDto> Booking){
		List<BookingDto> BookingDto =new ArrayList<>(Booking.size());
		for (int i = 0; i<Booking.size(); i++){
			es.udc.ws.app.client.service.soap.wsdl.BookingDto Bookings= Booking.get(i);
			BookingDto.add(toBookingDto(Bookings));
		}
		return BookingDto;
	}
		
}
