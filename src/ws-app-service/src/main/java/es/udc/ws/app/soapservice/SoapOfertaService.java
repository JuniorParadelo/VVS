package es.udc.ws.app.soapservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

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
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

@WebService(
		name="OfertsProvider",
		serviceName="OfertsProviderService",
		targetNamespace="http://soap.ws.udc.es/"
)
public class SoapOfertaService {

	@WebMethod(operationName = "anadirOferta")
	public Long anadirOferta(@WebParam(name = "ofertaDto") OfertaDto ofertaDto)
			throws SoapInputValidationException {
		Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		oferta.setEstado(EstadoOferta.VALIDA);
		
		try {
			return OfertaServiceFactory.getService().añadirOferta(oferta)
					.getOfertaId();
		} catch (InputValidationException ex) {
			throw new SoapInputValidationException(ex.getMessage());
		}
	}

	@WebMethod(operationName = "actualizarOferta")
	public void actualizarOferta(
			@WebParam(name = "ofertaDto") OfertaDto ofertaDto)
			throws SoapInputValidationException, SoapInstanceNotFoundException,
			SoapPrecioDescontadoException, SoapFechaInvalidaException {
		Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		
		try {
			
			/*
			*	Como las OfertasDto que nos llegan son una abstracción no tienen
			*	algunos campos que nosotros sí tenemos guardados, y no deben ser sobreescritos
			*/
			Oferta stored = OfertaServiceFactory.getService().buscarOferta(oferta.getOfertaId());
			oferta.setEstado(stored.getEstado());
			oferta.setComision(stored.getComision());
			
			OfertaServiceFactory.getService().actualizarOferta(oferta);
		} catch (InputValidationException ex) {
			throw new SoapInputValidationException(ex.getMessage());
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		} catch (PrecioDescontadoException ex) {
			throw new SoapPrecioDescontadoException(
					new SoapPrecioDescontadoExceptionInfo(
							ex.getPrecioDescontado()));
		} catch (FechaInvalidaException ex) {
			throw new SoapFechaInvalidaException();
		}
	}

	@WebMethod(operationName = "eliminarOferta")
	public void eliminarOferta(@WebParam(name = "ofertaId") Long ofertaId)
			throws SoapInstanceNotFoundException, SoapOfertaReservedException {
		try {
			OfertaServiceFactory.getService().eliminarOferta(ofertaId);
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		} catch (OfertaReservedException ex) {
			throw new SoapOfertaReservedException(
					new SoapOfertaReservedExceptionInfo(ex.getOfertaId()));
		}
	}

	@WebMethod(operationName = "buscarOferta")
	public OfertaDto buscarOferta(@WebParam(name = "ofertaId") Long ofertaId)
			throws SoapInstanceNotFoundException {

		try {
			Oferta oferta = OfertaServiceFactory.getService().buscarOferta(
					ofertaId);
			return OfertaToOfertaDtoConversor.toOfertaDto(oferta);
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		}
	}

	@WebMethod(operationName = "buscarOfertas")
	public List<OfertaDto> buscarOfertas(
			@WebParam(name = "keywords") String keywords) {
		// Se devolverán solamente ofertas con el período de reserva abierto.
		List<Oferta> ofertas = OfertaServiceFactory.getService().buscarOfertas(
				keywords, true, Calendar.getInstance());
		return OfertaToOfertaDtoConversor.toOfertaDtos(ofertas);
	}

	@WebMethod(operationName = "reservarOferta")
	public Long reservarOferta(@WebParam(name = "ofertaId") Long ofertaId,
			@WebParam(name = "email") String email,
			@WebParam(name = "tarjeta") String tarjeta)
			throws SoapInstanceNotFoundException, SoapInputValidationException,
			SoapOfertaInvalidaException, SoapOfertaExpirationException, SoapOfertaReservedByUserException {
		try {
			Reserva reserva = OfertaServiceFactory.getService().reservarOferta(ofertaId, email, tarjeta);
			return (long) reserva.getCodigo();
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		} catch (InputValidationException ex) {
			throw new SoapInputValidationException(ex.getMessage());
		} catch (OfertaInvalidaException ex) {
			throw new SoapOfertaInvalidaException(
					new SoapOfertaInvalidaExceptionInfo(ex.getOfertaId()));
		} catch (OfertaExpirationException ex) {
			throw new SoapOfertaExpirationException(
					new SoapOfertaExpirationExceptionInfo(ex.getOfertaId(),
							ex.getFechaReservar()));
		} catch (OfertaReservedByUserException ex) {
			throw new SoapOfertaReservedByUserException(
				new SoapOfertaReservedByUserExceptionInfo(ex.getOfertaId(), email));
		}
	}

	@WebMethod(operationName = "buscarReserva")
	public ReservaDto buscarReserva(@WebParam(name = "reservaId") Long reservaId)
			throws SoapInstanceNotFoundException {

		try {
			Reserva reserva = OfertaServiceFactory.getService().buscarReserva(
					reservaId);
			return ReservaToReservaDtoConversor.toReservaDto(reserva);
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		}
	}

	@WebMethod(operationName = "invalidarOferta")
	public void invalidarOferta(@WebParam(name = "ofertaId") Long ofertaId)
			throws SoapInstanceNotFoundException,
			SoapPrecioDescontadoException, SoapFechaInvalidaException,
			SoapInputValidationException, SoapOfertaInvalidaException {

		try {
			OfertaServiceFactory.getService().invalidarOferta(ofertaId);
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		} catch (PrecioDescontadoException ex) {
			throw new SoapPrecioDescontadoException(
					new SoapPrecioDescontadoExceptionInfo(
							ex.getPrecioDescontado()));
		} catch (FechaInvalidaException ex) {
			throw new SoapFechaInvalidaException();
		} catch (InputValidationException ex) {
			throw new SoapInputValidationException(ex.getMessage());
		} catch (OfertaInvalidaException ex) {
			throw new SoapOfertaInvalidaException(
					new SoapOfertaInvalidaExceptionInfo(ex.getOfertaId()));
		}
	}

	@WebMethod(operationName = "buscarReservasDeUnaOferta")
	public List<ReservaDto> buscarReservasDeUnaOferta(
			@WebParam(name = "ofertaId") Long ofertaId)
			throws SoapInstanceNotFoundException {
		try {
			List<Reserva> reservas = OfertaServiceFactory.getService()
					.buscarReservasDeUnaOferta(ofertaId);
			return ReservaToReservaDtoConversor.toReservaDtos(reservas);
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		}
	}

	@WebMethod(operationName = "buscarReservasDeUnUsuario")
	public List<ReservaDto> buscarReservasDeUnUsuario(
			@WebParam(name = "email") String email,
			@WebParam(name = "validas") Boolean validas) {
		List<Reserva> reservas = OfertaServiceFactory.getService()
				.buscarReservasDeUnUsuario(email, validas);
		return ReservaToReservaDtoConversor.toReservaDtos(reservas);
	}

	@WebMethod(operationName = "buscarOfertasReservadasDeUnUsuario")
	public List<OfertaReservadaDto> buscarOfertasReservadasDeUnUsuario(
			@WebParam(name = "email") String email) {
		// Para cada oferta que el usuario ha reservado, se devolverá la
		// descripción de la oferta, su precio descontado, y la fecha/hora en
		// que hizo la reserva.
		List<Reserva> reservas = OfertaServiceFactory.getService()
				.buscarReservasDeUnUsuario(email, false);

		List<OfertaReservadaDto> ofertasReservadas = new ArrayList<>();
		try {
			OfertaReservadaDto or = null;
			OfertaDto o = null;
			for (Reserva i : reservas) {
				o = this.buscarOferta(i.getOfertaId());
				or = new OfertaReservadaDto(o.getDescripcion(),
						o.getPrecioDescontado(), i.getFechaDeReserva());
				ofertasReservadas.add(or);
			}
		} catch (SoapInstanceNotFoundException e) {
			// Estado de la base de datos inconsistente: hay reservas cuya oferta no existe
			e.printStackTrace();
		}
		return ofertasReservadas;
	}

	@WebMethod(operationName = "reclamarReserva")
	public void reclamarReserva(@WebParam(name = "codigo") int codigo)
			throws SoapReservaExpirationException,
			SoapReservaNoValidaException, SoapInstanceNotFoundException {
		try {
			OfertaServiceFactory.getService().reclamarReserva(codigo);
		} catch (ReservaExpirationException ex) {
			throw new SoapReservaExpirationException(
					new SoapReservaExpirationExceptionInfo(ex.getReservaId(),
							ex.getFechaLimite()));
		} catch (ReservaNoValidaException ex) {
			throw new SoapReservaNoValidaException(
					new SoapReservaNoValidaExceptionInfo(ex.getReservaId()));
		} catch (InstanceNotFoundException ex) {
			throw new SoapInstanceNotFoundException(
					new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
							ex.getInstanceType()));
		}
	}

}
