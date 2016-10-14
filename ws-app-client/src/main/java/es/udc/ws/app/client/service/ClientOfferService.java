package es.udc.ws.app.client.service;


import es.udc.ws.app.dto.BookingDto;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface ClientOfferService {

    public Long addOffer(OfferDto Offer)
            throws InputValidationException;

    public void updateOffer(OfferDto Offer)
            throws InputValidationException, InstanceNotFoundException;

    public void removeOffer(Long OfferId) throws InstanceNotFoundException;

    public OfferDto findOffer(Long offerId);
    
    public List<OfferDto> findOffers(String keywords);
    
    public List<BookingDto> findOffersReservations (Long offerId);
    
    public List<BookingDto> findUserReservations (String userId, String token);
    
    public List<BookingDto> findUserOffers (String userId);


    public Long reserveOffer(Long OfferId, String userId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException;
    
    public void invalidateOffer(Long offerId);
    
    public void ClaimBooking(Long BookingId);
}
