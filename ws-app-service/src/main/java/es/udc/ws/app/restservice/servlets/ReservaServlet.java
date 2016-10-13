package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.FechaReservaExpiradaException;
import es.udc.ws.app.exceptions.OfertaYaReservadaException;
import es.udc.ws.app.exceptions.ReservaYaReclamadaException;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XMLExceptionConversor;
import es.udc.ws.app.xml.XMLReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;


@SuppressWarnings("serial")
public class ReservaServlet extends HttpServlet{

	
	
	@Override
	protected void doGet(HttpServletRequest peticion,HttpServletResponse respuesta) throws IOException {
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );
		
		if ( path ==null || path.length() ==0 ) {  
			
			String usuario = peticion.getParameter("usuario");
			
			if (usuario == null ) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML( 
								new InputValidationException("El parametro 'usuario' es obligatorio")), null);
				
				return;
			}
			
			String validas = peticion.getParameter("soloValidas");
			
			if (validas == null) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'soloValidas' es obligatorio")), null);
				
				return;
			}
			
			boolean soloValidas = Boolean.parseBoolean(validas);
			
			List<Reserva> reservas;
			
			try {
				reservas = OfertaServiceFactory.getService().buscarReserva(usuario, soloValidas);
			} catch (InputValidationException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
			}
			
			List<ReservaDto> reservasDto;
			
			reservasDto = ReservaToReservaDtoConversor.toReservasDto(reservas);
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_OK,
					XMLReservaDtoConversor.toXML(reservasDto), null);
				
		}
		else {	
			
			String id = path.substring(1);
			Long idOferta;
			
			try {
				idOferta = Long.parseLong(id);
			}
			catch (NumberFormatException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("ID Oferta invalido ["+id+"]") ), null);
				
				return;
			}
			
			List<Reserva> reservas;
			
			try {
				reservas = OfertaServiceFactory.getService().buscarReserva(idOferta);
			} catch (InputValidationException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
				
			}
			
			List<ReservaDto> reservasDto = ReservaToReservaDtoConversor.toReservasDto(reservas);
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_OK,
					XMLReservaDtoConversor.toXML(reservasDto)
					, null);
			
		}
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest peticion , HttpServletResponse respuesta) throws IOException {
		
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );
		ReservaDto reservaxml;
		
		if (path==null || path.length()==0 ) {
			
			try{
				reservaxml= XMLReservaDtoConversor.toReserva(peticion.getInputStream());
			
			}catch (ParsingException e){
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST, 
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException(e.getMessage())), null);
				return;
			}
			
			Reserva r = ReservaToReservaDtoConversor.toReserva(reservaxml);
			
			String usuario = r.getEmail();
			if (usuario == null) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'usuario' es obligatorio") ), null);
				
				return;
			}
			
			String tarjeta = r.getTarjeta();
			if (tarjeta == null) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'usuario' es obligatorio")), null);
				
				return;
			}
			
			String id = r.getIdOferta().toString();
			if (id==null) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'idOferta' es obligatorio")), null);
				
			}
			
			Long idOferta;
			try {
				idOferta = Long.parseLong(id);
				
			}
			catch (NumberFormatException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML( 
								new InputValidationException("ID Oferta invalido ["+id+"]")), null);
				
				return;
			}
			
			Long cod;
			try {
				cod = OfertaServiceFactory.getService().reservarOferta(usuario, tarjeta, idOferta);
				
			} 
			catch (InputValidationException e) {

				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
			} 
			catch (InstanceNotFoundException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_FOUND,
						XMLExceptionConversor.toInstanceNotFoundExceptionXML(e) , null);
				
				return;

			} 
			catch (OfertaYaReservadaException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_GONE,
						XMLExceptionConversor.toOfertaYaReservadaException(e), null);
				
				return;
				
			}
			catch (FechaReservaExpiradaException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_CONFLICT, 
						XMLExceptionConversor.toFechaReservaExpiradaException(e) , null);

				return;
			}
			
			/*	Creamos un nuevo Documento con el fin de poder mandarle la respuesta al cliente */
			
			Element env = new Element("codigo");
			env.setText(cod.toString());
			Document doc = new Document(env);
			
			String reservaURL = ServletUtils.normalizePath( peticion.getRequestURL().toString() )+"/"+
									cod.toString();
			
			Map<String, String> headers = new HashMap<>(1);
	        headers.put("Location", reservaURL);
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_CREATED,
					doc, headers);
						
			
		}
		else {
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_IMPLEMENTED,
					null,null);
			
			return;
		}
		
		
	}
	
	
	
	@Override
	protected void doPut(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
		
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo());
		
		
		if ( path==null || path.length()==0 ) { 
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_IMPLEMENTED,
							null, null);
			
			return;
		}
		else { 
			
			String cod = path.substring(1);
			Long codigo;
			
			try {
				codigo = Long.parseLong(cod);
			}
			catch (NumberFormatException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("Codigo invalido ["+cod+"]") ), null);
				
				return;
			}
			
			try {
				OfertaServiceFactory.getService().reclamarReserva(codigo);
				
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
			catch (ReservaYaReclamadaException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_GONE,
						XMLExceptionConversor.toReservaYaReclamadaException(e), null);
				
				return;
			}
			
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NO_CONTENT,
					null, null);
			
		}
		
	}
	
}

