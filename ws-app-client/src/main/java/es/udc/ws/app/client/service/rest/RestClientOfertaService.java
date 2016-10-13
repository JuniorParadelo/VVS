package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientOfertaService;
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
import es.udc.ws.app.reserva.EstadoReserva;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XMLExceptionConversor;
import es.udc.ws.app.xml.XMLMixtoDtoConversor;
import es.udc.ws.app.xml.XMLOfertaDtoConversor;
import es.udc.ws.app.xml.XMLReservaDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;



public class RestClientOfertaService implements ClientOfertaService{

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientOfertaService.endpointAddress";
	private final static SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	private final static Namespace XML_NS = XMLOfertaDtoConversor.XML_NS;
	private String endpointAddress;
	
	
								/*	Funciones Auxiliares */
	
	
	private synchronized String getEndpointAddress() {
		
		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);
			
		}
		
		return endpointAddress;
	}
	
	
	private InputStream toInputStream(OfertaDto oDto) {
		
		/*	Funcion auxiliar para parsear un OfertaDto a un InputStream */
		
		try {
			
			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat() );
			
			outputter.output(XMLOfertaDtoConversor.toXML(oDto), xmlOutputStream );
			
			return new ByteArrayInputStream(xmlOutputStream.toByteArray() );
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private InputStream toInputStream(ReservaDto rDto){
		
		/*	Funcion auxiliar para parsear un ReservaDto a un InputStream */
		
		try{
			
			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			
			outputter.output(XMLReservaDtoConversor.toXML(rDto), xmlOutputStream);
			
			return new ByteArrayInputStream(xmlOutputStream.toByteArray());
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}
		
	}
	
	
	private void specialInputValidationException(Element rootElement) throws InputValidationException {
		
		/* Parseamos la excepcion InputValidationException desde su tag raiz */
		
		String mensaje = rootElement.getChildText("mensaje",XML_NS);
		
		throw new InputValidationException(mensaje);
		
	}
	
	
	private void specialReclamacionAnteriorReservaException(Element rootElement ) throws ReclamacionAnteriorReservaException, ParseException {
		
		/* Parseamos la excepcion ReclamacionAnteriorReservaException desde su tag raiz */
		
		String fechaRec = rootElement.getChildText("fechaLimReclamacion",XML_NS);
		String fechaRes = rootElement.getChildText("fechaLimReserva",XML_NS);
		
		Calendar fechaLimReclamacion = Calendar.getInstance();
		Calendar fechaLimReserva = Calendar.getInstance();
		
		fechaLimReclamacion.setTime( formato.parse(fechaRec) );
		fechaLimReserva.setTime( formato.parse(fechaRes) );
		
		throw new ReclamacionAnteriorReservaException(fechaLimReclamacion, fechaLimReserva);
		
	}
	
	
	private void specialFechaInvalidaException(Element rootElement) throws FechaInvalidaException, ParseException {
		
		/* Parseamos la excepcion FechaInvalidaException desde su tag raiz */
		
		String fechaInvalida = rootElement.getChildText("fechaInv",XML_NS);
		
		Calendar fechaInv = Calendar.getInstance();
		fechaInv.setTime( formato.parse(fechaInvalida) );
		
		throw new FechaInvalidaException(fechaInv);
		
	}
	
	
	private void specialPrecioInvalidoException(Element rootElement) throws PrecioInvalidoException {
		
		/* Parseamos la excepcion PrecioInvalidoException desde su tag raiz */
		
		String precio = rootElement.getChildText("precioInvalido",XML_NS);
		
		float precioInvalido = Float.parseFloat(precio);
		
		throw new PrecioInvalidoException(precioInvalido);
		
	}
	
	
	private void comprobarBadRequest(InputStream entrante) throws InputValidationException,ReclamacionAnteriorReservaException,
				FechaInvalidaException,PrecioInvalidoException, ParsingException{
		
		/*	Comprobamos que excepcion es de las que englobamos en BAD_REQUEST a partir del nombre del tag raiz*/
		
		
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document documento = builder.build(entrante);
			
			Element rootElement = documento.getRootElement();
			
			String cabecera = rootElement.getName();
			
			switch (cabecera) {
			
			case "InputValidationException" :
				specialInputValidationException(rootElement);
				
			case "ReclamacionAnteriorReservaException" : 
				specialReclamacionAnteriorReservaException(rootElement);
				
			case "FechaInvalidaException" : 
				specialFechaInvalidaException(rootElement);
				
			case "PrecioInvalidoException": 
				specialPrecioInvalidoException(rootElement);
				
			}
			
		} catch (InputValidationException | ReclamacionAnteriorReservaException | FechaInvalidaException | PrecioInvalidoException e) {
			throw e;			
			
		} catch ( JDOMException | IOException e) {
			throw new ParsingException(e);
			
		} catch (Exception e) {
			throw new ParsingException(e);
		}
		
		
		
	}
	
	
	private void validarEstado(HttpResponse respuesta, int recibido, int esperado) throws InputValidationException , InstanceNotFoundException , ReclamacionAnteriorReservaException,
		FechaInvalidaException , PrecioInvalidoException {
		
		/*	Comprobamos si el codigo es el esperado. En caso contrario, parseamos la excepcion correspondiente */
		
		try {
			if (recibido!=esperado) {
				
				switch (recibido) {
				case HttpStatus.SC_BAD_REQUEST:
					comprobarBadRequest(respuesta.getEntity().getContent());
					
				case HttpStatus.SC_NOT_FOUND:
					throw XMLExceptionConversor.fromInstanceNotFoundException(respuesta.getEntity().getContent() );
				
				case HttpStatus.SC_NOT_IMPLEMENTED:
					throw new RuntimeException("Operacion no soportada por el servidor");

				default :
					
					/*	En caso de que no corresponda con ningun codigo de los que el servidor lanza*/
					
					throw new RuntimeException("Error HTTP : Codigo ["+recibido+"]");
				}
				
			}
			
		}
		catch (InputValidationException | InstanceNotFoundException | ReclamacionAnteriorReservaException |
				FechaInvalidaException | PrecioInvalidoException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	/*
	 * 									Modelizacion de las peticiones
	 * 
	 * 									-------- OFERTAS ----------
	 * 
	 * ANNADIR OFERTA 			 		post 		/ws-app-service/ofertas									201
	 * ACTUALIZAR OFERTA 				put 		/ws-app-service/ofertas/id 								204
	 * INVALIDAR 				 		post 		/ws-app-service/ofertas/id/invalidar					204
	 * BORRAR OFERTA 			 		delete 		/ws-app-service/ofertas/id								204
	 * BUSCAR OFERTA (ID) 				get			/ws-app-service/ofertas/id								200
	 * BUSCAR OFERTA (PC) 		 		get 		/ws-app-service/ofertas?keywords=palabrasClave			200
	 * 
	 *  								-------- RESERVAS ----------
	 * 
	 * RESERVAR OFERTA			 		post	/ws-app-service/reservas									201
	 * BUSCAR RESERVA (ID) 		 		get 	/ws-app-service/reservas/idOferta							200
	 * BUSCAR RESERVA (USUARIO)	 		get 	/ws-app-service/reservas?usuario=c&soloValidas=true			200
	 * RECLAMAR RESERVA 		 		put 	/ws-app-service/reservas/codigo								204
	 * 	 	
	 * 
	 * 									--------- MIXTOS ------------
	 * 
	 * BUSCAR OFERTA (USUARIO) 	 		get /ws-app-service/mixtos?usuario=c								200
	 * 
	 */
	

									/*	Funciones principales	*/
	
	@Override
	public OfertaDto annadirOferta(OfertaDto ofertaDto) throws InputValidationException,
			ReclamacionAnteriorReservaException {
		
		try{
			
			/*	Mandamos la peticion correspondiente */
			
			HttpResponse respuesta =
					Request.Post(getEndpointAddress()+ "ofertas").
					bodyStream(toInputStream(ofertaDto), ContentType.create("application/xml")).
					execute().returnResponse();
			
			/*	Obtenemos el codigo HTTP de la respuesta */
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			/*	Comprobamos si el codigo es el esperado y miramos que error es*/
			
			validarEstado(respuesta, estado, HttpStatus.SC_CREATED);
			
			
			/*	Devolvemos al usuario el cuerpo de la respuesta */
			
			return XMLOfertaDtoConversor.toOferta(respuesta.getEntity().getContent());
			
		}
		catch(InputValidationException | ReclamacionAnteriorReservaException e){
			throw e;
			
		}catch(Exception e){
			throw new RuntimeException(e);
			
		}		
		
	}
	
	
	
	@Override
	public void actualizarOferta(OfertaDto ofertaDto)
			throws InputValidationException, InstanceNotFoundException, FechaInvalidaException, PrecioInvalidoException,
			ReclamacionAnteriorReservaException {
		
		try {
			
			HttpResponse respuesta = Request
							.Put(getEndpointAddress()+"ofertas/"+ofertaDto.getIdOferta())
							.bodyStream(toInputStream(ofertaDto),  ContentType.create("application/xml") )
							.execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta,estado,HttpStatus.SC_NO_CONTENT);
			
		}
		catch (InputValidationException | InstanceNotFoundException | FechaInvalidaException | PrecioInvalidoException | ReclamacionAnteriorReservaException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	@Override
	public void invalidarOferta(Long idOferta) throws InputValidationException,
			InstanceNotFoundException, OfertaYaInvalidadaException {
		
		
		try{
			
			HttpResponse respuesta = 
					Request.Post(getEndpointAddress()+"ofertas/"+idOferta.toString()+"/invalidar").
					execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
				
			/* Miramos si el codigo de la respuesta es uno de los especiales de la operacion */
			
			if (estado == HttpStatus.SC_GONE ){
				throw XMLExceptionConversor.fromOfertaYaInvalidadaException(respuesta.getEntity().getContent());
			}
			
			validarEstado(respuesta, estado, HttpStatus.SC_NO_CONTENT);
			
		}
		catch (OfertaYaInvalidadaException | InstanceNotFoundException | InputValidationException e){
			throw e;
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		
		
	}
	
	@Override
	public void borrarOferta(Long idOferta) throws InstanceNotFoundException,
			InputValidationException, HayReservasException {
		
		try {
			
			HttpResponse respuesta = Request.Delete(getEndpointAddress()+"ofertas/"+idOferta.toString()) 
					.execute().returnResponse();
			
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			if (estado==HttpStatus.SC_GONE) {
				throw XMLExceptionConversor.fromHayReservasException(respuesta.getEntity().getContent() );
			}
			
			validarEstado(respuesta, estado, HttpStatus.SC_NO_CONTENT);
			
			
		}
		catch (InstanceNotFoundException | InputValidationException | HayReservasException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public OfertaDto buscarOfertaID(Long idOferta)
			throws InputValidationException, InstanceNotFoundException {
		
		try{
			HttpResponse respuesta= 
					Request.Get(getEndpointAddress()+"ofertas/"+idOferta.toString()).execute().returnResponse();

			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta, estado, HttpStatus.SC_OK);
			
			return XMLOfertaDtoConversor.toOferta(respuesta.getEntity().getContent());
			
		}
		catch(InputValidationException | InstanceNotFoundException e){
			throw e;
		
		}
		catch (Exception e){
			throw new RuntimeException(e);
		
		}
	}
	
	
	
	@Override
	public List<OfertaDto> buscarOfertaPalabrasClave(String palabrasClave)
			throws DatatypeConfigurationException {
		
		try {
			HttpResponse respuesta = Request.Get(getEndpointAddress()+"ofertas?keywords="+URLEncoder.encode(palabrasClave,"UTF-8"))
					.execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta,estado,HttpStatus.SC_OK);
			
			return XMLOfertaDtoConversor.toOfertas(respuesta.getEntity().getContent());
			
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	

	@Override
	public long reservarOferta(String usuario, String tarjeta, Long idOferta)
			throws InputValidationException, InstanceNotFoundException,
			OfertaYaReservadaException, FechaReservaExpiradaException {
		
		
		try {

			/* Creamos un reserva para mandarle los datos necesarios al servidor, aunque no necesite todos*/
			
			
			ReservaDto reserva = new ReservaDto(usuario, tarjeta, idOferta, EstadoReserva.PENDIENTE,
					Calendar.getInstance(), -1);
			
			
			HttpResponse respuesta =
					Request.Post(getEndpointAddress()+"reservas").bodyStream(toInputStream(reserva), ContentType.create("application/xml")).
							execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			switch (estado) {

			case HttpStatus.SC_GONE:   
				throw XMLExceptionConversor.fromOfertaYaReservadaException(respuesta.getEntity().getContent() );
			
			case HttpStatus.SC_CONFLICT:
				
				/*	Debido a que se puede modificar la fecha limite de Reserva, entendemos esta excepcion
				 * 	como no permanente */
				
				throw XMLExceptionConversor.fromFechaReservaExpiradaException(respuesta.getEntity().getContent());
				
			}
			
			validarEstado(respuesta, estado, HttpStatus.SC_CREATED);
			
			/*	Recogemos la respuesta (un Long en este caso) */
			try {
				
				SAXBuilder builder = new SAXBuilder();
				Document documento = builder.build(respuesta.getEntity().getContent());
				Element rootElement = documento.getRootElement();
				
				String codigo = rootElement.getText();
				return Long.parseLong(codigo);
				
			}
			catch (JDOMException | IOException e) {
				throw new ParsingException(e);
			}
			catch (Exception e) {
				throw new ParsingException(e);
			}
						
		}
		catch(InputValidationException | InstanceNotFoundException | OfertaYaReservadaException | FechaReservaExpiradaException e){
			throw e;
			
		}
		catch(Exception e){
			throw new RuntimeException(e);
			
		}
		
	}
	
	
	
	@Override
	public List<ReservaDto> buscarReservaIDOferta(Long idOferta) throws InputValidationException {
		
		
		try{
			
			HttpResponse respuesta= 
					Request.Get(getEndpointAddress()+"reservas/"+idOferta.toString()).
					execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta, estado, HttpStatus.SC_OK);
			
			return XMLReservaDtoConversor.toReservas(respuesta.getEntity().getContent());
			
		}
		catch (InputValidationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		

	}
	
	
	
	
	@Override
	public List<ReservaDto> buscarReservaUsuario(String usuario,
			boolean soloValidas) throws InputValidationException,
			DatatypeConfigurationException {
		

		String validasSolo = Boolean.toString(soloValidas);
		
		try {
			HttpResponse respuesta = Request
					.Get(getEndpointAddress()+"reservas?usuario="+URLEncoder.encode(usuario, "UTF-8")
							+"&soloValidas="+validasSolo)
					.execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta, estado, HttpStatus.SC_OK);
			
			return XMLReservaDtoConversor.toReservas(respuesta.getEntity().getContent());
			
		} 
		catch (InputValidationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	@Override
	public List<MixtoDto> buscarOfertasUsuario(String usuario)
			throws InputValidationException, InstanceNotFoundException,
			DatatypeConfigurationException {
		
		try {
			
			HttpResponse respuesta=
					Request.Get(getEndpointAddress()+"mixtos?usuario="+URLEncoder.encode(usuario, "UTF-8"))
					.execute().returnResponse();
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			validarEstado(respuesta, estado, HttpStatus.SC_OK);
			
			return XMLMixtoDtoConversor.toMixtos(respuesta.getEntity().getContent());
			
		}
		catch(InputValidationException | InstanceNotFoundException e){
			throw e;
			
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public void reclamarReserva(Long codigo) throws InputValidationException,
			InstanceNotFoundException, ReservaYaReclamadaException {
		
		try {
			HttpResponse respuesta = Request.Put(getEndpointAddress() + "reservas/"+codigo.toString())
					.execute().returnResponse();
			
			
			int estado = respuesta.getStatusLine().getStatusCode();
			
			if (estado==HttpStatus.SC_GONE) {
				throw XMLExceptionConversor.fromReservaYaReclamadaException(respuesta.getEntity().getContent());
			}
			
			validarEstado(respuesta, estado, HttpStatus.SC_NO_CONTENT);
			
		}
		catch (InputValidationException | InstanceNotFoundException | ReservaYaReclamadaException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
}



