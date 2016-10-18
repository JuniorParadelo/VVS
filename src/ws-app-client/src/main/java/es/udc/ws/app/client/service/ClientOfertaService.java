package es.udc.ws.app.client.service;

import java.util.List;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaReservadaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientOfertaService {
	
	public Long añadirOferta(OfertaDto oferta) throws InputValidationException;

    public void actualizarOferta(OfertaDto oferta) throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioDescontadoException;

    public void eliminarOferta(Long ofertaId) throws InstanceNotFoundException, OfertaReservedException;

    public OfertaDto buscarOferta(Long ofertaId) throws InstanceNotFoundException;

    public List<OfertaDto> buscarOfertas (String keywords);

    public Long reservarOferta(Long ofertaId, String email, String tarjeta) throws InstanceNotFoundException, InputValidationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException;

    //public ReservaDto buscarReserva(Long reservaId) throws InstanceNotFoundException;
    
    public void invalidarOferta (Long ofertaId) throws InstanceNotFoundException, InputValidationException, FechaInvalidaException, PrecioDescontadoException, OfertaInvalidaException;
    
    public List<ReservaDto> buscarReservasDeUnaOferta (Long ofertaId) throws InstanceNotFoundException;
    
    public List<ReservaDto> buscarReservasDeUnUsuario (String email, boolean validas);
    
	public List<OfertaReservadaDto> buscarOfertasReservadasDeUnUsuario(String email);
    
    public void reclamarReserva (int codigo) throws InstanceNotFoundException, ReservaNoValidaException, ReservaExpirationException;
}
