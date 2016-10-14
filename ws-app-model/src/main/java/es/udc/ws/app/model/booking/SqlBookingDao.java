package es.udc.ws.app.model.booking;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.app.exceptions.BookingIsNotValidException;
import es.udc.ws.app.model.booking.Booking.State;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlBookingDao {
	
	//Creamos una reserva
	public Booking create(Connection connection, Booking booking);
	
	//Actualizamos una reserva
	public void update(Connection connection, Booking booking)
			throws BookingIsNotValidException;
	
	//Borramos una reserva
	public void remove(Connection connection, Long bookingId)
			throws InstanceNotFoundException;
	
	//Buscamos las reservas de un usuario
	public List<Booking> findBookingByUser(Connection connection, String userId);
	
	//Buscamos las reservas de un codigo
    public Booking findBookingByCode (Connection connection, Long code) 
    		throws InstanceNotFoundException;

	
	//Buscamos reservas con unas palabras clave
	public List<Booking> findBookingByKeywords(Connection connection, String userId, State state)
			throws InstanceNotFoundException;
	
	//Buscamos las reservas de una oferta
	public List<Booking> findBookingByOfferId(Connection connection, Long offerId)
			throws InstanceNotFoundException;
	
	//Comprobamos si una offerta tiene reservas 
		public boolean hasTheOfferBookings(Connection connection, Long offerId)
				throws InstanceNotFoundException;
	
    public void annulBooking(Connection connection, Long offerId) throws BookingIsNotValidException;
	
	//Buscamos una reserva por su Id
	public Booking findBookingByBookingId(Connection connection, Long BookingId)
			throws InstanceNotFoundException;
	
	
	/*Esto está en los ejemplos pero a nosotros no nos hace falta
	 * public Booking findBookingById(Connection connection, Long bookingId)
	 * 		throws InstanceNotFoundException;
	 * */
	
}
