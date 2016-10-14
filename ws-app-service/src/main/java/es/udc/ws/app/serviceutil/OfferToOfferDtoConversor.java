package es.udc.ws.app.serviceutil;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.model.offer.Offer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfferToOfferDtoConversor {

    public static List<OfferDto> toOfferDtos(List<Offer> offers) {
        List<OfferDto> offerDtos = new ArrayList<>(offers.size());
        for (int i = 0; i < offers.size(); i++) {
            Offer offer = offers.get(i);
            offerDtos.add(toOfferDto(offer));
        }
        return offerDtos;
    }

    public static OfferDto toOfferDto(Offer offer) {
        return new OfferDto(offer.getOfferId(), offer.getName(), offer.getDescription(),offer.getRealPrice(),
        		offer.getDiscountPrice(),offer.getBookingDate(), offer.getEnjoymentDate());
    }

    public static Offer toOffer(OfferDto offer) {
        return new Offer(offer.getName(), offer.getDescription(),offer.getRealPrice(),offer.getDiscountPrice(),
        		offer.getBookingDate(),offer.getEnjoymentDate());
    }    
    
}
