package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.client.service.soap.wsdl.*;
import es.udc.ws.app.dto.BookingDto;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.client.service.soap.OfferDtoToSoapOfferDtoConversor;

import java.util.List;

import javax.xml.ws.BindingProvider;

public class SoapClientOfferService implements ClientOfferService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
        "SoapClientOfferService.endpointAddress";

    private String endpointAddress;

    private OfferProvider OffersProvider;

    public SoapClientOfferService() {
        init(getEndpointAddress());
    }

    private void init(String OffersProviderURL) {
        OfferProviderService OffersProviderService =
                new OfferProviderService();
        OffersProvider = OffersProviderService
                .getOfferProviderPort();
        ((BindingProvider) OffersProvider).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                OffersProviderURL);
    }

    @Override
    public Long addOffer(OfferDto Offer)
            throws InputValidationException {
        try {
           return OffersProvider.addOffer(OfferDtoToSoapOfferDtoConversor.toSoapOfferDto(Offer));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateOffer(OfferDto Offer)
            throws InputValidationException, InstanceNotFoundException {
        try {
            OffersProvider.updateOffer(OfferDtoToSoapOfferDtoConversor
                    .toSoapOfferDto(Offer));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeOffer(Long OfferId)
            throws InstanceNotFoundException {
        try {
            OffersProvider.removeOffer(OfferId);
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch (UserAlreadyReservedException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public List<OfferDto> findOffers(String keywords) {
        return OfferDtoToSoapOfferDtoConversor.toOfferDtos(
                    OffersProvider.findOffer(keywords));
    }
    
    
    
    @Override
	public OfferDto findOffer(Long offerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDto> findOffersReservations(Long offerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDto> findUserReservations(String userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingDto> findUserOffers(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long reserveOffer(Long OfferId, String userId,
			String creditCardNumber) throws InstanceNotFoundException,
			InputValidationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidateOffer(Long offerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClaimBooking(Long BookingId) {
		// TODO Auto-generated method stub
		
	}
	
	
    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }
}