package es.udc.ws.app.model.ofertaservice;

import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.reserva.Reserva;

public interface OfertaService {
	
    public Oferta añadirOferta(Oferta oferta) throws InputValidationException;

    public void actualizarOferta(Oferta oferta) throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException;

    public void eliminarOferta(Long ofertaId) throws InstanceNotFoundException, OfertaReservedException;

    public Oferta buscarOferta(Long ofertaId) throws InstanceNotFoundException;

    public List<Oferta> buscarOfertas (String keywords, Boolean estado, Calendar fecha);

    public Reserva reservarOferta(Long ofertaId, String email, String tarjeta) throws InstanceNotFoundException, InputValidationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException;

    public Reserva buscarReserva(Long reservaId) throws InstanceNotFoundException;
    
    public void invalidarOferta (Long ofertaId) throws InstanceNotFoundException, InputValidationException, FechaInvalidaException, PrecioDescontadoException, OfertaInvalidaException;
    
    public List<Reserva> buscarReservasDeUnaOferta (Long ofertaId) throws InstanceNotFoundException;
    
    public List<Reserva> buscarReservasDeUnUsuario (String email, boolean validas);
    
    public void reclamarReserva (int codigo) throws InstanceNotFoundException, ReservaNoValidaException, ReservaExpirationException;
	
}
