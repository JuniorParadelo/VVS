package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.model.reserva.Reserva;

public class ReservaToReservaDtoConversor {
	
	
	public static ReservaDto toReservaDto(Reserva r) {
		
		return new ReservaDto(r.getCodigo(),r.getEmail(),r.getTarjeta(),r.getIdOferta(),r.getEstado(),r.getFechaReserva(),r.getPrecioReserva());
	}
	
	public static Reserva toReserva (ReservaDto r) {
		
		return new Reserva(r.getCodigo(),r.getEmail(),r.getTarjeta(),r.getIdOferta(),r.getEstado(),r.getFechaReserva(),r.getPrecioReserva());
	}
	
	
	public static List<ReservaDto> toReservasDto(List<Reserva> reservas){
			
		List<ReservaDto> reservasDto = new ArrayList<>();
		
		for (Reserva re : reservas) {
			ReservaDto rDto = new ReservaDto(re.getCodigo(),re.getEmail(),re.getTarjeta(),re.getIdOferta(),
					re.getEstado(),re.getFechaReserva(),re.getPrecioReserva());
			reservasDto.add(rDto);
		}
		
		return reservasDto;
	}
		
	
	public static List<Reserva> toReservas(List<ReservaDto> reservasDto){
		
		List<Reserva> reservas = new ArrayList<>();
		
		for (ReservaDto rDto : reservasDto) {
			Reserva re = new Reserva(rDto.getCodigo(),rDto.getEmail(),rDto.getTarjeta(),rDto.getIdOferta(),
					rDto.getEstado(),rDto.getFechaReserva(),rDto.getPrecioReserva());
			reservas.add(re);
		}
		
		return reservas;
	}
	
	
	
}




