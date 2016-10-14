package es.udc.ws.app.soapservice;

import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.exceptions.BookingIsNotValidException;
import es.udc.ws.app.exceptions.OfferAlreadyCreatedException;
import es.udc.ws.app.exceptions.UserAlreadyReservedException;
import es.udc.ws.app.exceptions.commissionUpperTheAverageException;
import es.udc.ws.app.model.booking.Booking;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.serviceutil.BookingToBookingDtoConversor;
import es.udc.ws.app.serviceutil.OfferToOfferDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
    name="OfferProvider",
    serviceName="OfferProviderService",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapOfferService {

    @WebMethod(
        operationName="addOffer"
    )
    public Long addOffer(@WebParam(name="offerDto") OfferDto offerDto)
            throws SoapInputValidationException, OfferAlreadyCreatedException {
        Offer offer = OfferToOfferDtoConversor.toOffer(offerDto);
        try {
            return OfferServiceFactory.getService().addOffer(offer).getOfferId();
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (commissionUpperTheAverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    @WebMethod(
        operationName="updateOffer"
    )
    public void updateOffer(@WebParam(name="offerDto") OfferDto offerDto)
            throws SoapInputValidationException, SoapInstanceNotFoundException, UserAlreadyReservedException {
    	Offer offer = OfferToOfferDtoConversor.toOffer(offerDto);
        try {
        	OfferServiceFactory.getService().updateOffer(offer);
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        } catch (commissionUpperTheAverageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @WebMethod(
        operationName="removeOffer"
    )
    public void removeOffer(@WebParam(name="offerId") Long offerId)
            throws SoapInstanceNotFoundException, UserAlreadyReservedException {
        try {
        	OfferServiceFactory.getService().removeOffer(offerId);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(
                    ex.getInstanceId(), ex.getInstanceType()));
        }
    }

    @WebMethod(
        operationName="findOffer"
    )
    public List<OfferDto> findOffer(
            @WebParam(name="keywords") String keywords) {
        List<Offer> offer =
        		OfferServiceFactory.getService().findOfferByKeywords(keywords, false, null);
        return OfferToOfferDtoConversor.toOfferDtos(offer);
    }

    @WebMethod(
        operationName="addBooking"
    )
    public Long buyOffer(@WebParam(name="offerId")  Long offerId,
                         @WebParam(name="userId")   String userId,
                         @WebParam(name="creditCardNumber") String creditCardNumber)
            throws SoapInstanceNotFoundException, SoapInputValidationException {
        try {
            return OfferServiceFactory.getService()
                    .addBooking(offerId, userId, creditCardNumber);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }
    }

    @WebMethod(
        operationName="findBooking"
    )
    public BookingDto findBooking(@WebParam(name="bookingId") Long bookingId)
            throws SoapInstanceNotFoundException {

        try {
            Booking booking = OfferServiceFactory.getService().findBookingByBookingId(bookingId);
            return BookingToBookingDtoConversor.toBookingDto(booking);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        }
    }
    
    @WebMethod(
    		operationName="useBooking"
    )
    public void UseBooking(@WebParam(name="bookingId") Long bookingId){
        try {
			OfferServiceFactory.getService().claimBooking(bookingId);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BookingIsNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
