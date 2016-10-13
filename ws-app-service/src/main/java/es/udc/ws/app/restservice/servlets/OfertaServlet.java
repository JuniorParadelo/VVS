package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.HayReservasException;
import es.udc.ws.app.exceptions.OfertaYaInvalidadaException;
import es.udc.ws.app.exceptions.PrecioInvalidoException;
import es.udc.ws.app.exceptions.ReclamacionAnteriorReservaException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XMLExceptionConversor;
import es.udc.ws.app.xml.XMLOfertaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;





@SuppressWarnings("serial")
public class OfertaServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta) throws IOException {
		  
		
		/* Recogemos el path de la peticion */
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );
		
		if (path==null || path.length()==0 ) { 
			
			/* Recogemos los parametros de la URL*/
			
			String palabrasClave = peticion.getParameter("keywords");
			
			Calendar hoy = Calendar.getInstance();
			
			List<Oferta> ofertas = OfertaServiceFactory.getService().buscarOferta(palabrasClave, hoy, true);
			
			List<OfertaDto> ofertasDto = OfertaToOfertaDtoConversor.toOfertasDto(ofertas);

			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_OK, XMLOfertaDtoConversor.toXML(ofertasDto), null);
			
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
										new InputValidationException("ID Oferta invalido ["+id+"] ") ), null);
				
				return;
			}
			
			Oferta o;
			try { 
				o = OfertaServiceFactory.getService().buscarOferta(idOferta); //llamamos al metodo y parseamos sus posibles excepciones
			
			}
			catch (InstanceNotFoundException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_FOUND,
								XMLExceptionConversor.toInstanceNotFoundExceptionXML(e), null);
				
				return;
				
			}
			catch (InputValidationException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
								XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
				
			}
				
			OfertaDto oDto = OfertaToOfertaDtoConversor.toOfertaDto(o);
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_OK, XMLOfertaDtoConversor.toXML(oDto), null);
			
		}
		
	}
	
	
	
	
	
	@Override
	protected void doPut(HttpServletRequest peticion,HttpServletResponse respuesta) throws IOException {
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );

		if (path == null | path.length()==0 ) {
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_IMPLEMENTED,
				null, null);
			
			return;
		}
		
		String id = path.substring(1);
		Long idOferta;
		
		try {
			idOferta = Long.parseLong(id);
		}
		catch (NumberFormatException e) {
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
				XMLExceptionConversor.toInputValidationExceptionXML( new InputValidationException("ID Oferta invalido ["+id+"]") ), null);
			
			return;
		}
		
		OfertaDto oDto;
		
		try {
			oDto = XMLOfertaDtoConversor.toOferta(peticion.getInputStream());
			 
		} catch (ParsingException e) {
				
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
				XMLExceptionConversor.toInputValidationExceptionXML( new InputValidationException(e.getMessage() ) ) , null);

			return;
		}
		
		oDto.setIdOferta(idOferta);
		
		Oferta o = OfertaToOfertaDtoConversor.toOferta(oDto);
		
		try {
			OfertaServiceFactory.getService().actualizarOferta(o);
			
		} 
		catch (InputValidationException e) {

			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
					XMLExceptionConversor.toInputValidationExceptionXML(e), null);
			
			return;
		} 
		catch (InstanceNotFoundException e) {
			
			ServletUtils.writeServiceResponse(respuesta,HttpServletResponse.SC_NOT_FOUND,
					XMLExceptionConversor.toInstanceNotFoundExceptionXML(e),null);
			
			return;
		}
		catch (FechaInvalidaException e) {
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
					XMLExceptionConversor.toFechaInvalidaException(e), null);
			
			return;
		}
		catch (ReclamacionAnteriorReservaException e) {
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
					XMLExceptionConversor.toReclamacionAnteriorReservaException(e), null);
			
			return;
		}
		catch (PrecioInvalidoException e) {
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
					XMLExceptionConversor.toPrecioInvalidoException(e), null);
			
			return;
		}	
		
		ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NO_CONTENT, null, null);
		
	}
	
	
	@Override 
	protected void doDelete(HttpServletRequest peticion, HttpServletResponse respuesta) 
			throws IOException{
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo());
		
		if (path==null || path.length()==0){
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_IMPLEMENTED,
						null,null );
			return;
			
		}
		
		String idStr = path.substring(1);
		Long idOferta;
		
		try {
			idOferta=Long.valueOf(idStr);
			
		}
		catch (NumberFormatException e){
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST, 
					XMLExceptionConversor.toInputValidationExceptionXML(
							new InputValidationException("Peticion incorrecta, el id no es valido "+idStr)), null);
			
			return;
		}
		
		
		try{
			OfertaServiceFactory.getService().borrarOferta(idOferta);
			
		}
		catch(InstanceNotFoundException e){
			
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_FOUND,
						XMLExceptionConversor.toInstanceNotFoundExceptionXML(e), null);
				
			return;
		}
		catch(InputValidationException e){
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST, 
					XMLExceptionConversor.toInputValidationExceptionXML(e), null);
			
			return;
		}
		catch(HayReservasException e){
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_GONE,
					XMLExceptionConversor.toHayReservasException(e), null);
			
			return;
		}
		

		ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NO_CONTENT, null, null);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
				throws ServletException, IOException{
		
		String path = ServletUtils.normalizePath(peticion.getPathInfo() );
		
		if (path == null || path.length() == 0) {
			
			OfertaDto xmloferta;
			
			try{
				xmloferta = XMLOfertaDtoConversor.toOferta(peticion.getInputStream());
				
			} catch(ParsingException e){
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST
						, XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException(e.getMessage())), null);
				return;
			}
			
			Oferta o = OfertaToOfertaDtoConversor.toOferta(xmloferta);
			
			try{
				o = OfertaServiceFactory.getService().crearOferta(o);
				
			} 
			catch (InputValidationException e){
				
				ServletUtils.writeServiceResponse(respuesta,
						HttpServletResponse.SC_BAD_REQUEST, 
						XMLExceptionConversor.toInputValidationExceptionXML(e), null);
				
				return;
			}
			catch (ReclamacionAnteriorReservaException e){
			
				ServletUtils.writeServiceResponse(respuesta,
						HttpServletResponse.SC_BAD_REQUEST, 
						XMLExceptionConversor.toReclamacionAnteriorReservaException(e), null);
				return;
			}
			
			OfertaDto oDto = OfertaToOfertaDtoConversor.toOfertaDto(o);
			
			String ofertaURL = ServletUtils.normalizePath(peticion.getRequestURL().toString()
					+"/"+oDto.getIdOferta());
			
			/* Mandamos en la cabecera la URL del nuevo recurso con el campo Location */
			
			Map<String, String> headers = new HashMap<>(1);
			headers.put("Location",ofertaURL);
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_CREATED,
					XMLOfertaDtoConversor.toXML(oDto), headers);
			
		}
		else {
			
			String[] despues = path.substring(1).split("/");
			
			if (despues.length != 2) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML( 
								new InputValidationException("Numero de parametros incorrecto")), null);				
				
				return;
			}
			
			String frase = despues[1];
			
			if ( !frase.equals("invalidar") ){
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException("El parametro 'invalidar' es necesario")), null);
				
				return;
			}
			
			String id = despues[0];
			Long idOferta;
			
			try {
				idOferta = Long.parseLong(id);
			}
			catch (NumberFormatException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(
								new InputValidationException(e.getMessage()) ), null);
				
				return;				
			}
			
			try {
				OfertaServiceFactory.getService().invalidarOferta(idOferta);
				
			} catch (InputValidationException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_BAD_REQUEST,
						XMLExceptionConversor.toInputValidationExceptionXML(e) , null);
				
				return;
				
			} catch (InstanceNotFoundException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NOT_FOUND,
						XMLExceptionConversor.toInstanceNotFoundExceptionXML(e), null);
				
				return;				
				
			} catch (OfertaYaInvalidadaException e) {
				
				ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_GONE,
						XMLExceptionConversor.toOfertaYaInvalidadaException(e) , null);

				return;
			}
			
			ServletUtils.writeServiceResponse(respuesta, HttpServletResponse.SC_NO_CONTENT,
					null, null);
			
		}
			
	}
	
}

