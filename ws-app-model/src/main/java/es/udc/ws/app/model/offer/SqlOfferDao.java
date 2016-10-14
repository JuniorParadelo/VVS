package es.udc.ws.app.model.offer;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlOfferDao {
	//Creamos una oferta
	public Offer create(Connection connection, Offer offer);
	
	//Buscamos una oferta por el ID
	public Offer findOfferByOfferId(Connection connection, Long offerId)
			throws InstanceNotFoundException;
	
	//Buscamos una oferta por diferentes campos
	public List<Offer>findOfferByKeywords(Connection connection,String words, Calendar bookingDate,
			boolean validOffer);
	
	//Borramos uan oferta
	public void remove(Connection connection, Long offerId)
			throws InstanceNotFoundException;
	
	//Actualizamos una oferta
	public void update(Connection connection, Offer offer)
			throws InstanceNotFoundException;
	
}
