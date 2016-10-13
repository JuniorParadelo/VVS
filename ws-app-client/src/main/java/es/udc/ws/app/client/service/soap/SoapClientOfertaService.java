package es.udc.ws.app.client.service.soap;

import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.BindingProvider;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.dto.MixtoDto;
import es.udc.ws.app.client.service.soap.wsdl.OfertaProvider;
import es.udc.ws.app.client.service.soap.wsdl.OfertaProviderService;
import es.udc.ws.app.client.service.soap.wsdl.SoapFechaInvalidaException;
import es.udc.ws.app.client.service.soap.wsdl.SoapFechaReservaExpiradaException;
import es.udc.ws.app.client.service.soap.wsdl.SoapHayReservasException;
import es.udc.ws.app.client.service.soap.wsdl.SoapInputValidationException;
import es.udc.ws.app.client.service.soap.wsdl.SoapInstanceNotFoundException;
import es.udc.ws.app.client.service.soap.wsdl.SoapOfertaYaInvalidadaException;
import es.udc.ws.app.client.service.soap.wsdl.SoapOfertaYaReservadaException;
import es.udc.ws.app.client.service.soap.wsdl.SoapPrecioInvalidoException;
import es.udc.ws.app.client.service.soap.wsdl.SoapReclamacionAnteriorReservaException;
import es.udc.ws.app.client.service.soap.wsdl.SoapYaReclamadaException;
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
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class SoapClientOfertaService implements ClientOfertaService{

	private final static String ENDPOINT_ADDRESS_PARAMETER ="SoapClientOfertaService.endpointAddress";
    private String endpointAddress;
    private OfertaProvider ofertaProvider;

    
    public SoapClientOfertaService() {
        init(getEndpointAddress());
        
    }
    
    private void init(String ofertaProviderURL) {
    	
        OfertaProviderService ofertaProviderService = new OfertaProviderService();
        ofertaProvider = ofertaProviderService.getOfertaProviderPort();
        
        ((BindingProvider) ofertaProvider).getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
               													ofertaProviderURL);
    }
    
    
    
    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }
    
    
	@Override
	public OfertaDto annadirOferta(OfertaDto ofertaDto)
			throws InputValidationException, DatatypeConfigurationException, ReclamacionAnteriorReservaException {
	
		try{
			/*Llamamos al metodo del servidor y devolvemos la respuesta al usuario*/
			
			return OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(ofertaProvider.
					annadirOferta(OfertaDtoToSoapOfertaDtoConversor.toSoapOfertaDto(ofertaDto) ) );
			
		}catch (SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
			
		}catch (SoapReclamacionAnteriorReservaException e) {
			throw new ReclamacionAnteriorReservaException(ofertaDto.getFechaLimReclamacion(),
					ofertaDto.getFechaLimReserva());
		}
		
	}

	@Override
	public void actualizarOferta(OfertaDto ofertaDto)
			throws InputValidationException, InstanceNotFoundException,
			FechaInvalidaException, PrecioInvalidoException, DatatypeConfigurationException,
			ReclamacionAnteriorReservaException {
		
		
		try{
			ofertaProvider.actualizarOferta(OfertaDtoToSoapOfertaDtoConversor.toSoapOfertaDto(ofertaDto));
		
		}
		catch(SoapFechaInvalidaException e){
			throw new FechaInvalidaException(ofertaDto.getFechaLimReclamacion());
			
		}
		catch(SoapPrecioInvalidoException e){
			throw new PrecioInvalidoException(ofertaDto.getPrecioDescontado());
			
		}
		catch(SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
			
		}
		catch(SoapInstanceNotFoundException e){
			throw new InstanceNotFoundException(ofertaDto.getIdOferta(), "IdOferta");
			
		}
		catch(DatatypeConfigurationException e){
			throw new DatatypeConfigurationException();
			
		}
		catch (SoapReclamacionAnteriorReservaException e) {
			throw new ReclamacionAnteriorReservaException(ofertaDto.getFechaLimReclamacion(),
					ofertaDto.getFechaLimReserva());
		}
	}

	@Override
	public void invalidarOferta(Long idOferta)
			throws InputValidationException, InstanceNotFoundException, OfertaYaInvalidadaException {
		
		try{
			ofertaProvider.invalidarOferta(idOferta);
			
		}
		catch(SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
			
		}
		catch(SoapInstanceNotFoundException e){
			throw new InstanceNotFoundException(idOferta, "IdOferta");
			
		}
		catch (SoapOfertaYaInvalidadaException e) {
			throw new OfertaYaInvalidadaException(idOferta);
		}
		
		
		
	}
	
	@Override
	public void borrarOferta(Long idOferta)
			throws InstanceNotFoundException, InputValidationException, HayReservasException{
		
		try{
			ofertaProvider.borrarOferta(idOferta);
		
		}
		catch(SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
		}
		catch(SoapInstanceNotFoundException e){
			throw new InstanceNotFoundException(idOferta, "IdOferta");
		}
		catch(SoapHayReservasException e){
			throw new HayReservasException(idOferta);
		}	
	
	}

	@Override
	public OfertaDto buscarOfertaID(Long idOferta)
			throws InputValidationException, InstanceNotFoundException, DatatypeConfigurationException {
		
		try{
			return OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(ofertaProvider.buscarOfertaID(idOferta));
		
		}
		catch(SoapInputValidationException e){
			throw new InputValidationException(e.getMessage());
		}
		catch(SoapInstanceNotFoundException e){
			throw new InstanceNotFoundException(idOferta, "IdOferta");
		}
		
	}

	@Override
	public List<OfertaDto> buscarOfertaPalabrasClave(String palabrasClave) throws DatatypeConfigurationException {
		
		return OfertaDtoToSoapOfertaDtoConversor.toOfertaDtos(ofertaProvider.buscarOfertaPclave(palabrasClave));
		
	}

	@Override
	public long reservarOferta(String usuario,String tarjeta,Long idOferta) 
			throws InputValidationException, InstanceNotFoundException, OfertaYaReservadaException,
			FechaReservaExpiradaException{
		
		try {
			return ofertaProvider.reservarOferta(usuario, tarjeta, idOferta);
			
		}
		catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
			
		}
		catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(idOferta, "idOferta");
			
		}
		catch (SoapOfertaYaReservadaException e) {
			throw new OfertaYaReservadaException(idOferta);
			
		}
		catch (SoapFechaReservaExpiradaException e){
			throw new FechaReservaExpiradaException(Calendar.getInstance());
		}
		
	}

	@Override
	public List<ReservaDto> buscarReservaIDOferta(Long idOferta)
			throws InputValidationException, DatatypeConfigurationException {
		
			try {
				return ReservaDtoToSoapReservaDtoConversor.toReservasDtos(
						ofertaProvider.buscarReservaIDOf(idOferta));
				
			}
			catch (SoapInputValidationException e) {
				throw new InputValidationException(e.getMessage());
			}
		
	}

	@Override
	public List<ReservaDto> buscarReservaUsuario(String usuario,
			boolean soloValidas) throws InputValidationException, DatatypeConfigurationException {
		
		try {
			return ReservaDtoToSoapReservaDtoConversor.toReservasDtos(
					ofertaProvider.buscarReservaUsuario(usuario, soloValidas));
			
		}
		catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());	
		}
		
	}
	@Override
	public List<MixtoDto> buscarOfertasUsuario(String usuario)
			throws InputValidationException,InstanceNotFoundException, DatatypeConfigurationException{
		
		try {
			return MixtoDtoToSoapMixtoDtoConversor.toMixtoDtos(ofertaProvider.buscarOfertasUsuario(usuario));
			
		}
		catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		}
		catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(usuario, "user");
		}	
		
	}

	@Override
	public void reclamarReserva(Long codigo)
			throws InputValidationException, InstanceNotFoundException,ReservaYaReclamadaException {
		
		try {
			ofertaProvider.reclamarReserva(codigo);
			
		} 
		catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
			
		} 
		catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(codigo, "codigo");
				
		} 
		catch (SoapYaReclamadaException e) {
				throw new ReservaYaReclamadaException(codigo);
				
		}
	}
		
		
		
		
}
