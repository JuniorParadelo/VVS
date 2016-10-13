package es.udc.ws.app.soapservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import es.udc.ws.app.dto.MixtoDto;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;

@WebService(
	    name="OfertaProvider",
	    serviceName="OfertaProviderService",
	    targetNamespace="http://soap.ws.udc.es/"
	)
public class SoapOfertaService {
	
	@WebMethod(
	        operationName="annadirOferta"
	    )
	public OfertaDto annadirOferta(@WebParam(name="ofertaDto")OfertaDto ofertaDto)
			throws SoapInputValidationException,SoapReclamacionAnteriorReservaException{
		
		Oferta o = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		
		try{
			Oferta o1= OfertaServiceFactory.getService().crearOferta(o);
			return OfertaToOfertaDtoConversor.toOfertaDto(o1);
			
		}catch(InputValidationException ex){
			throw new SoapInputValidationException(ex.getMessage());
		} catch (ReclamacionAnteriorReservaException e) {
			throw new SoapReclamacionAnteriorReservaException(o.getFechaLimReclamacion(), o.getFechaLimReserva());
		}	
	}
	
	@WebMethod(
			operationName="actualizarOferta"
			)
	public void actualizarOferta(@WebParam(name="ofertaDto")OfertaDto ofertaDto)
	throws SoapInputValidationException,SoapInstanceNotFoundException,SoapFechaInvalidaException,SoapPrecioInvalidoException, SoapReclamacionAnteriorReservaException{
		
		Oferta o = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		
		try{
			OfertaServiceFactory.getService().actualizarOferta(o);
			
		}catch (InputValidationException e1){
			throw new SoapInputValidationException(e1.getMessage());
			
		}catch (InstanceNotFoundException e2){
			throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(
                    ofertaDto.getIdOferta(), "ID OFERTA") );
			
		}catch (FechaInvalidaException e3){
			throw new SoapFechaInvalidaException(o.getFechaLimReclamacion());
			
		}catch (PrecioInvalidoException e4){
			throw new SoapPrecioInvalidoException(o.getPrecioDescontado());
			
		}catch (ReclamacionAnteriorReservaException e) {
			throw new SoapReclamacionAnteriorReservaException(ofertaDto.getFechaLimReclamacion(), ofertaDto.getFechaLimReserva());
		}
	}
	
	@WebMethod(
			operationName="invalidarOferta"
			)
	public void invalidarOferta(@WebParam(name="idOferta")Long idOferta)
	throws SoapInputValidationException,SoapInstanceNotFoundException, SoapOfertaYaInvalidadaException{

		try{
			OfertaServiceFactory.getService().invalidarOferta(idOferta);
			
		}catch(InputValidationException e1){
			throw new SoapInputValidationException(e1.getMessage());
			
		}catch(InstanceNotFoundException e2){
			throw new SoapInstanceNotFoundException(new SoapInstanceNotFoundExceptionInfo(
					idOferta,"ID OFERTA") );
		} catch (OfertaYaInvalidadaException e) {
			throw new SoapOfertaYaInvalidadaException(idOferta);
		}
		
	}
	
	
	@WebMethod(
			operationName="borrarOferta"
			)
	public void borrarOferta( @WebParam(name="idOferta")Long idOferta ) throws SoapInputValidationException,
	SoapInstanceNotFoundException, SoapHayReservasException {
		
			try {
				OfertaServiceFactory.getService().borrarOferta(idOferta);
				
			} catch ( InstanceNotFoundException e) {
				throw new SoapInstanceNotFoundException( new SoapInstanceNotFoundExceptionInfo(
						idOferta, "ID OFERTA") );
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
				
			}catch (HayReservasException e){
				throw new SoapHayReservasException(idOferta);
			}
	}

	@WebMethod(
			operationName="buscarOfertaID"
			)
	public OfertaDto buscarOfertaID( @WebParam(name="idOferta")Long idOferta ) throws SoapInputValidationException,
	SoapInstanceNotFoundException {
		
			try {
				Oferta o = OfertaServiceFactory.getService().buscarOferta(idOferta);
				
				return OfertaToOfertaDtoConversor.toOfertaDto(o);
				
			} catch ( InstanceNotFoundException e) {
				throw new SoapInstanceNotFoundException( new SoapInstanceNotFoundExceptionInfo(
						idOferta, "ID OFERTA") );
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
			}
	}
	
	
	@WebMethod(
			operationName="buscarOfertaPclave"
			)
	public List<OfertaDto> buscarOfertaPalabrasClave( @WebParam(name="palabrasClave")String palabrasClave) {
		
		Calendar fecha = Calendar.getInstance();
		
		List<Oferta> ofertas = OfertaServiceFactory.getService().buscarOferta(palabrasClave,fecha,true);
				
		return OfertaToOfertaDtoConversor.toOfertasDto(ofertas);	
	}
	
	@WebMethod(
			operationName="reservarOferta"
			)
	public long reservarOferta( @WebParam(name="usuario")String usuario,
												@WebParam(name="tarjeta")String tarjeta,
												@WebParam(name="idOferta")Long idOferta)
		throws SoapInputValidationException,SoapInstanceNotFoundException,SoapOfertaYaReservadaException, SoapFechaReservaExpiradaException{
		
			try {
				return OfertaServiceFactory.getService().reservarOferta(usuario, tarjeta, idOferta);
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
				
			} catch (InstanceNotFoundException e) {
				throw new SoapInstanceNotFoundException( new SoapInstanceNotFoundExceptionInfo(
						idOferta, "ID OFERTA") );
				
			} catch (OfertaYaReservadaException e) {
				throw new SoapOfertaYaReservadaException(idOferta);
				
			}catch (FechaReservaExpiradaException e){
				throw new SoapFechaReservaExpiradaException(Calendar.getInstance());
			}
	}
	
	
	
	@WebMethod(
			operationName="reclamarReserva"
			)
	public void reclamarReserva( @WebParam(name="codigo") Long codigo)
		throws SoapInputValidationException,SoapInstanceNotFoundException,SoapYaReclamadaException{
		
			try {
				OfertaServiceFactory.getService().reclamarReserva(codigo);
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
				
			} catch (InstanceNotFoundException e) {
				throw new SoapInstanceNotFoundException( new SoapInstanceNotFoundExceptionInfo(
						codigo, "CODIGO") );
				
			} catch (ReservaYaReclamadaException e) {
				throw new SoapYaReclamadaException(codigo);
				
			}
	}
	
	
	@WebMethod(
			operationName="buscarReservaIDOf"
			)
	public List<ReservaDto> buscarReservaIDOferta( @WebParam(name="idOferta") Long idOferta)
		throws SoapInputValidationException {
		
			try {
				List<Reserva> reservas = OfertaServiceFactory.getService().buscarReserva(idOferta);
				
				return ReservaToReservaDtoConversor.toReservasDto(reservas);
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
				
			} 
	}
	
	@WebMethod(
			operationName="buscarReservaUsuario"
			)
	public List<ReservaDto> buscarReservaUsuario( @WebParam(name="usuario") String usuario,
												  @WebParam(name="soloValidas")boolean soloValidas)
		throws SoapInputValidationException {
		
			try {
				List<Reserva> reservas = OfertaServiceFactory.getService().buscarReserva(usuario,soloValidas);
				
				return ReservaToReservaDtoConversor.toReservasDto(reservas);
				
			} catch (InputValidationException e) {
				throw new SoapInputValidationException(e.getMessage());
				
			} 
	}
	
	
	@WebMethod(
			operationName="buscarOfertasUsuario"
			)
	public List<MixtoDto> buscarOfertasUsuario( @WebParam(name="usuario") String usuario)
		throws SoapInputValidationException,SoapInstanceNotFoundException {
		
		try{
			PropertyValidator.validateMandatoryString("usuario", usuario);
		
				
			List<Reserva> reservas = OfertaServiceFactory.getService().buscarReserva(usuario, true);
			List<MixtoDto> mixtos = new ArrayList<>();
			
			for (Reserva r : reservas) {
				Oferta o = OfertaServiceFactory.getService().buscarOferta(r.getIdOferta() );
				mixtos.add( new MixtoDto(o.getDescripcion() , o.getPrecioDescontado(), r.getFechaReserva()) );
			}
		
			return mixtos;
		
		} catch (InputValidationException e ) {
			throw new SoapInputValidationException(e.getMessage());
		} catch (InstanceNotFoundException e) {
			throw new SoapInstanceNotFoundException( new SoapInstanceNotFoundExceptionInfo( e.getInstanceId(), e.getInstanceType()));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
