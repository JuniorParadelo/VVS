package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.soap.wsdl.*;
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
import es.udc.ws.app.client.service.soap.wsdl.OfertsProvider;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

public class SoapClientOfertaService implements ClientOfertaService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
        "SoapClientOfertaService.endpointAddress";

    private String endpointAddress;

    private OfertsProvider ofertsProvider;

    public SoapClientOfertaService() {
        init(getEndpointAddress());
    }

    private void init(String ofertasProviderURL) {
        OfertsProviderService moviesProviderService = new OfertsProviderService();
        ofertsProvider = moviesProviderService.getOfertsProviderPort();
        ((BindingProvider) ofertsProvider).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ofertasProviderURL);
    }

    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

	@Override
	public Long añadirOferta(OfertaDto oferta) throws InputValidationException {
		
        try {
            return ofertsProvider.anadirOferta(OfertaDtoToSoapOfertaDtoConversor
                    .toSoapOfertaDto(oferta));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
	}

	@Override
	public void actualizarOferta(OfertaDto oferta) throws InputValidationException, InstanceNotFoundException,
			FechaInvalidaException, PrecioDescontadoException {
		
		try {
			ofertsProvider.actualizarOferta(OfertaDtoToSoapOfertaDtoConversor.toSoapOfertaDto(oferta));
		} catch (SoapFechaInvalidaException | SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapPrecioDescontadoException e) {
			throw new PrecioDescontadoException(oferta.getPrecioDescontado());
		}		
	}

	@Override
	public void eliminarOferta(Long ofertaId) throws InstanceNotFoundException, OfertaReservedException {
		
		try {
			ofertsProvider.eliminarOferta(ofertaId);
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapOfertaReservedException e) {
			throw new OfertaReservedException(ofertaId);
		}
	}

	@Override
	public OfertaDto buscarOferta(Long ofertaId) throws InstanceNotFoundException {
		
		try {
			return OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(ofertsProvider.buscarOferta(ofertaId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public List<OfertaDto> buscarOfertas(String keywords) {
		return OfertaDtoToSoapOfertaDtoConversor.toOfertaDtos(ofertsProvider.buscarOfertas(keywords));
	}

	@Override
	public Long reservarOferta(Long ofertaId, String email, String tarjeta)
			throws InstanceNotFoundException, InputValidationException, OfertaExpirationException, OfertaInvalidaException, OfertaReservedByUserException {
		
		try {
			return ofertsProvider.reservarOferta(ofertaId, email, tarjeta);
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapOfertaExpirationException e) {
			throw new OfertaExpirationException(ofertaId, Calendar.getInstance());//TODO qué fecha devolvemos?
		} catch (SoapOfertaInvalidaException e) {
			throw new OfertaInvalidaException(ofertaId);
		} catch (SoapOfertaReservedByUserException e) {
			throw new OfertaReservedByUserException(ofertaId, email);
		}
	}

	@Override
	public void invalidarOferta(Long ofertaId)
			throws InstanceNotFoundException, InputValidationException, FechaInvalidaException, PrecioDescontadoException, OfertaInvalidaException {
		
		try {
			ofertsProvider.invalidarOferta(ofertaId);
		} catch (SoapFechaInvalidaException e) {
			throw new FechaInvalidaException();
		} catch (SoapInputValidationException e) {
			throw new InputValidationException(e.getMessage());
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapPrecioDescontadoException e) {
			throw new PrecioDescontadoException(0);//TODO qué precio devolvemos?
		} catch (SoapOfertaInvalidaException e) {
			throw new OfertaInvalidaException(ofertaId);
		}
	}

	@Override
	public List<ReservaDto> buscarReservasDeUnaOferta(Long ofertaId) throws InstanceNotFoundException {
		
		try {
			return ReservaDtoToSoapReservaDtoConversor.toReservaDtos(ofertsProvider.buscarReservasDeUnaOferta(ofertaId));
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		}
	}

	@Override
	public List<ReservaDto> buscarReservasDeUnUsuario(String email, boolean validas) {
		
		return ReservaDtoToSoapReservaDtoConversor.toReservaDtos(ofertsProvider.buscarReservasDeUnUsuario(email, validas));
	}

	@Override
	public List<OfertaReservadaDto> buscarOfertasReservadasDeUnUsuario(String email) {
		return OfertaReservadaDtoToSoapOfertaReservadaDtoConversor.toOfertaDtos(ofertsProvider.buscarOfertasReservadasDeUnUsuario(email));
	}

	@Override
	public void reclamarReserva(int codigo) throws InstanceNotFoundException, ReservaNoValidaException, ReservaExpirationException {
		try {
			ofertsProvider.reclamarReserva(codigo);
		} catch (SoapInstanceNotFoundException e) {
			throw new InstanceNotFoundException(e.getFaultInfo().getInstanceId(), e.getFaultInfo().getInstanceType());
		} catch (SoapReservaExpirationException e) {
			throw new ReservaExpirationException((long) codigo, Calendar.getInstance());//TODO qué fecha y código devolvemos?
		} catch (SoapReservaNoValidaException e) {
			throw new ReservaNoValidaException((long) codigo);
		}
	}

}
