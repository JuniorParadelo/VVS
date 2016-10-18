package es.udc.ws.app.serviceutil;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.model.oferta.Oferta;

import java.util.ArrayList;
import java.util.List;

public class OfertaToOfertaDtoConversor {

    public static List<OfertaDto> toOfertaDtos(List<Oferta> ofertas) {
        List<OfertaDto> ofertaDtos = new ArrayList<>(ofertas.size());
        for (int i = 0; i < ofertas.size(); i++) {
            Oferta oferta = ofertas.get(i);
            ofertaDtos.add(toOfertaDto(oferta));
        }
        return ofertaDtos;
    }

    public static OfertaDto toOfertaDto(Oferta oferta) {
        return new OfertaDto(oferta.getOfertaId(), oferta.getNombre(), oferta.getDescripcion(), oferta.getFechaReservar(),
        		oferta.getFechaReclamar(), oferta.getPrecioReal(), oferta.getPrecioDescontado(), oferta.getEstado());
    }

    public static Oferta toOferta(OfertaDto oferta) {
        return new Oferta(oferta.getOfertaId(), oferta.getNombre(), oferta.getDescripcion(), oferta.getFechaReservar(),
        		oferta.getFechaReclamar(), oferta.getPrecioReal(), oferta.getPrecioDescontado(), oferta.getEstado());
    }    
    
}
