package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.offer.Offer;

public class BookingToBookingDtoConversor {
    
    public static List<BookingDto> toBookingDtos(List<Booking> bookings){
    	List<BookingDto> bookingDtos = new ArrayList<>(bookings.size());
    	for(int i = 0; i<bookings.size(); i++){
    		Booking booking = bookings.get(i);
    		bookingDtos.add(toBookingDto(booking));
    	}
    	return bookingDtos;
    }

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getBookingId() ,booking.getOfferId(),booking.getUserId(),
        		booking.getCreditCardNumber(),booking.getBookingDate(),booking.getEnjoymentDate());
    }

    public static Booking toBooking(BookingDto booking) {
        return new Booking(booking.getBookingId() ,booking.getOfferId(),booking.getUserId(),
        		booking.getCreditCardNumber(),booking.getBookingDate(),booking.getEnjoymentDate());
    }    
    
    
}
