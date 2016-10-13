package es.udc.ws.app.client.service;

import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

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
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface ClientOfertaService {

	
	public OfertaDto annadirOferta(OfertaDto ofertaDto)
			throws InputValidationException, DatatypeConfigurationException, ReclamacionAnteriorReservaException;
	
	public void actualizarOferta(OfertaDto ofertaDto) 
			throws InputValidationException,InstanceNotFoundException,FechaInvalidaException,PrecioInvalidoException, DatatypeConfigurationException,ReclamacionAnteriorReservaException;

	public void invalidarOferta(Long idOferta)
			throws InputValidationException,InstanceNotFoundException, OfertaYaInvalidadaException;
	
	public void borrarOferta(Long idOferta)
	throws InstanceNotFoundException, InputValidationException,HayReservasException;

	public OfertaDto buscarOfertaID(Long idOferta ) 
			throws InputValidationException, InstanceNotFoundException, DatatypeConfigurationException;

	public List<OfertaDto> buscarOfertaPalabrasClave(String palabrasClave) 
			throws DatatypeConfigurationException;

	public long reservarOferta(String usuario,String tarjeta,Long idOferta)
			throws InputValidationException,InstanceNotFoundException, OfertaYaReservadaException, FechaReservaExpiradaException;
	
	public List<ReservaDto> buscarReservaIDOferta(Long idOferta)
			throws InputValidationException, DatatypeConfigurationException ;
	
	public List<ReservaDto> buscarReservaUsuario(String usuario,boolean soloValidas)
			throws InputValidationException, DatatypeConfigurationException;
	
	public List<MixtoDto> buscarOfertasUsuario(String usuario)
			throws InputValidationException,InstanceNotFoundException, DatatypeConfigurationException;
	
	public void reclamarReserva(Long codigo)
			throws InputValidationException,InstanceNotFoundException,ReservaYaReclamadaException;
	
}
