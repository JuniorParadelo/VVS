package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.MixtoDto;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.xml.XMLExceptionConversor;
import es.udc.ws.app.xml.XMLMixtoDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;



@SuppressWarnings("serial")
public class MixtoServlet extends HttpServlet{

	@Override
	protected void doGet (HttpServletRequest peticion , HttpServletResponse respuesta) throws IOException {
		
		/*	Recogemos los parametros */
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );
		
		if (path==null || path.length()== 0) {
			
			/* Procedemos a realizar la operacion pedida sobre el recurso coleccion */
			
			String usuario = peticion.getParameter("usuario");
			
			if (usuario == null) {
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'usuario' es obligatorio")), null);
				
				return;
			}
			
			List<Reserva> reservas;
			
			try {
				 reservas = OfertaServiceFactory.getService().buscarReserva(usuario, true);
			}
			catch (InputValidationException e) {
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
			}
			
			
			List<MixtoDto> mixtos = new ArrayList<>();
			
			for (Reserva r : reservas) {
				
				Oferta o;
				try {
					o = OfertaServiceFactory.getService().buscarOferta(r.getIdOferta() );
					
				} 
				catch (InputValidationException e) {
					ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
							XMLExceptionConversor.toInputValidationExceptionXML(e), null);
					
					return;
					
				} 
				catch (InstanceNotFoundException e) {
					ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_FOUND,
							XMLExceptionConversor.toInstanceNotFoundExceptionXML(e), null);
					
					return;
				}
				
				mixtos.add( new MixtoDto(o.getDescripcion()	, o.getPrecioDescontado() , r.getFechaReserva() ));
				
			}
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_OK,
					XMLMixtoDtoConversor.toXML(mixtos), null);
			
		}
		else {
			/*En caso de que no sea sobre el recurso coleccion, mandamos un NOT_IMPLEMENTED*/
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_IMPLEMENTED,
					null, null);
			
			return;
		}
		
		
		
	}
	
	
	
}
