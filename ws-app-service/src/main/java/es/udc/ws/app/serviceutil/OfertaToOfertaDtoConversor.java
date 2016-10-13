package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.model.oferta.Oferta;
import static es.udc.ws.app.model.util.ConstantesDelModelo.COMISION_FIJA;

public class OfertaToOfertaDtoConversor {
	
	
	
	public static OfertaDto toOfertaDto(Oferta o){
		
		OfertaDto oDto = new OfertaDto(o.getIdOferta(),o.getNombre(),o.getDescripcion(),
				o.getFechaLimReserva(),o.getFechaLimReclamacion(),o.getPrecioReal(),o.getPrecioDescontado(),o.isInvalida());
		return oDto;
	}
	
	
	public static Oferta toOferta(OfertaDto o){

		/* Establecemos una comision fija del 30%*/
		
		Oferta of = new Oferta(o.getIdOferta(),o.getNombre(),o.getDescripcion(),o.getFechaLimReserva(),o.getFechaLimReclamacion()
				,o.getPrecioReal(),o.getPrecioDescontado(), COMISION_FIJA ,o.isInvalida() );		
		
		return of;
	}	
	
	
	public static List<OfertaDto> toOfertasDto(List<Oferta> ofertas){
		
		List<OfertaDto> ofertasDto = new ArrayList<>();
		
		for (Oferta of : ofertas) {
			OfertaDto oDto = new OfertaDto(of.getIdOferta(),of.getNombre(),of.getDescripcion(),
					of.getFechaLimReserva(),of.getFechaLimReclamacion(),of.getPrecioReal(),of.getPrecioDescontado(),of.isInvalida());
			
			ofertasDto.add(oDto);
		}
		
		return ofertasDto;
	}
	
	public static List<Oferta> toOfertas(List<OfertaDto> ofertasDto){
		
		List<Oferta> ofertas = new ArrayList<>();
		
		for (OfertaDto oDto : ofertasDto) {
			
			Oferta of = new Oferta(oDto.getIdOferta(),oDto.getNombre(),oDto.getDescripcion(),oDto.getFechaLimReserva(),oDto.getFechaLimReclamacion()
					,oDto.getPrecioReal(),oDto.getPrecioDescontado(), COMISION_FIJA ,oDto.isInvalida() );		
			
			ofertas.add(of);
		}
		
		return ofertas;
	}
	
}
