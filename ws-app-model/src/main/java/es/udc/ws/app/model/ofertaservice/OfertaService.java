package es.udc.ws.app.model.ofertaservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;





public interface  OfertaService {

	public abstract Oferta crearOferta(Oferta o) throws InputValidationException, ReclamacionAnteriorReservaException;
	public abstract void actualizarOferta(Oferta o) throws InputValidationException,InstanceNotFoundException,FechaInvalidaException,PrecioInvalidoException, ReclamacionAnteriorReservaException;
	public abstract void invalidarOferta(Long idOferta) throws InputValidationException, InstanceNotFoundException, OfertaYaInvalidadaException;
	public abstract void borrarOferta(Long idOferta) throws InputValidationException,InstanceNotFoundException, HayReservasException;
	public abstract Oferta buscarOferta(Long idOferta) throws InputValidationException,InstanceNotFoundException;
	public abstract List<Oferta> buscarOferta (String palabrasClave , Calendar fecha , boolean soloValidas ) ;
	public abstract long reservarOferta(String usuario,String tarjeta,Long idOferta) throws InputValidationException,InstanceNotFoundException,OfertaYaReservadaException, FechaReservaExpiradaException;
	public abstract void reclamarReserva(Long codigo) throws InputValidationException,InstanceNotFoundException,ReservaYaReclamadaException;
	public abstract List<Reserva> buscarReserva(Long idOferta) throws InputValidationException;
	public abstract List<Reserva> buscarReserva(String usuario, boolean soloValidas) throws InputValidationException;

	

	
}
