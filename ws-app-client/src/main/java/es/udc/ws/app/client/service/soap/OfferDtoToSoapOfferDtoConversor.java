package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.dto.OfferDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class OfferDtoToSoapOfferDtoConversor {
    
    public static es.udc.ws.app.client.service.soap.wsdl.OfferDto toSoapOfferDto(OfferDto Offer) {
        es.udc.ws.app.client.service.soap.wsdl.OfferDto soapOfferDto = 
                new es.udc.ws.app.client.service.soap.wsdl.OfferDto();
        soapOfferDto.setOfferId(Offer.getOfferId());
        soapOfferDto.setName(Offer.getName());
        soapOfferDto.setDescription(Offer.getDescription());
        soapOfferDto.setRealPrice(Offer.getRealPrice());
        soapOfferDto.setDiscountPrice(Offer.getDiscountPrice());
        GregorianCalendar inicioCalendarioGregoriano= new GregorianCalendar();
        inicioCalendarioGregoriano.setTime(Offer.getBookingDate().getTime());
        try{
        	soapOfferDto.setBookingDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(inicioCalendarioGregoriano));
        }catch (DatatypeConfigurationException e){
        	throw new RuntimeException(e);
        }
       
        GregorianCalendar inicioCalendarioGregoriano2= new GregorianCalendar();
        inicioCalendarioGregoriano2.setTime(Offer.getBookingDate().getTime());
        try{
        	soapOfferDto.setEnjoymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(inicioCalendarioGregoriano2));
        }catch (DatatypeConfigurationException e){
        	throw new RuntimeException(e);
        }
       
        return soapOfferDto;
    }
    
    public static OfferDto toOfferDto(
            es.udc.ws.app.client.service.soap.wsdl.OfferDto Offer) {
        return new OfferDto(Offer.getOfferId(), Offer.getName(), 
                Offer.getDescription(), Offer.getRealPrice(),Offer.getDiscountPrice(),
                Offer.getBookingDate().toGregorianCalendar(), Offer.getEnjoymentDate().toGregorianCalendar());
    }     
    
    public static List<OfferDto> toOfferDtos(
            List<es.udc.ws.app.client.service.soap.wsdl.OfferDto> Offers) {
        List<OfferDto> OfferDtos = new ArrayList<>(Offers.size());
        for (int i = 0; i < Offers.size(); i++) {
            es.udc.ws.app.client.service.soap.wsdl.OfferDto Offer = 
                    Offers.get(i);
            OfferDtos.add(toOfferDto(Offer));
            
        }
        return OfferDtos;
    }    
    
}
