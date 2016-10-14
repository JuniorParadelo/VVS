package es.udc.ws.app.model.offerservice;

import es.udc.ws.app.exceptions.*;

import java.util.List;
import java.util.Calendar;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;



public interface OfferService {

	//Añadimos una oferta 
	public Offer addOffer(Offer offer) throws InputValidationException, OfferAlreadyCreatedException, 
	commissionUpperTheAverageException;
	
	//Actualizamos una oferta
	public void updateOffer(Offer offer) throws InstanceNotFoundException, InputValidationException,
	UserAlreadyReservedException, commissionUpperTheAverageException;
	
	//Borramos una oferta
	public void removeOffer(Long offerId) throws UserAlreadyReservedException, InstanceNotFoundException;
	
	//Buscamos una oferta por el offerId
	public Offer findOfferByOfferId(Long offerId) throws InstanceNotFoundException;
	
	//Buscamos las reservas de un usuario
	public List<Booking> findBookingByUser(String userId) throws InstanceNotFoundException;
	
	//Buscamos las reservas de una oferta
	public List<Booking> findBookingsOfOffer(Long offerId)throws InstanceNotFoundException;
	
	//Buscamos una oferta por diferentes campos
	public List<Offer> findOfferByKeywords (String description, boolean ValidOffer, Calendar bookingDate);
	
	//Invalidamos una oferta
	public void invalidateOffer(Long offerId) throws OfferIsAlreadyInvalid, InstanceNotFoundException;
	
	//Hacemos una reserva
	public Long addBooking (Long offerId, String userId, String creditCardNumber) throws InstanceNotFoundException, 
	InputValidationException;
	
	//Buscamos una reserva por el bookingId
	public Booking findBookingByBookingId (Long bookingId) throws InstanceNotFoundException;

	//Buscamos una reserva por el code
	public Booking findBookingByCode(Long code) throws InstanceNotFoundException;
	
	public void claimBooking (Long bookingId)throws InstanceNotFoundException,BookingIsNotValidException;
}
