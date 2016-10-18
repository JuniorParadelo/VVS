package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.OfertaReservadaDto;
import es.udc.ws.app.exceptions.FechaInvalidaException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedException;
import es.udc.ws.app.exceptions.PrecioDescontadoException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlOfertaReservadaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class OfertasServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		OfertaDto xmlOferta;
		try {
			xmlOferta = XmlOfertaDtoConversor.toOferta(req.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);

			return;

		}
		Oferta oferta = OfertaToOfertaDtoConversor.toOferta(xmlOferta);
		try {
			oferta = OfertaServiceFactory.getService().añadirOferta(oferta);
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		}
		OfertaDto OfertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);

		String OfertaURL = ServletUtils.normalizePath(req.getRequestURL()
				.toString()) + "/" + oferta.getOfertaId();
		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", OfertaURL);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
				XmlOfertaDtoConversor.toXml(OfertaDto), headers);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String path = ServletUtils.normalizePath(req.getPathInfo());

		// Validar número de la oferta "/ofertas/'NUM'"
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid Oferta id")),
							null);
			return;
		}
		
		String ofertaIdAsString = path.substring(1);
		Long ofertaId;
		try {
			ofertaId = Long.valueOf(ofertaIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid oferta id '"
													+ ofertaIdAsString + "'")),
							null);
			return;
		}

		if (req.getInputStream().available() == 0) {
			// Caso de uso INVALIDAR OFERTA
			try {
				OfertaServiceFactory.getService().invalidarOferta(ofertaId);
				
				ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK, null, null);
				
			} catch (InstanceNotFoundException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_NOT_FOUND,
						XmlExceptionConversor.toInstanceNotFoundExceptionXml(e), null);
				return;
			} catch (InputValidationException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInputValidationExceptionXml(e), null);
				return;
			} catch (FechaInvalidaException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_CONFLICT,
						XmlExceptionConversor.toFechaInvalidaExceptionXml(e), null);
				return;
			} catch (PrecioDescontadoException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_GATEWAY_TIMEOUT,
						XmlExceptionConversor.toPrecioDescontadoExceptionXml(e), null);
				return;
			} catch (OfertaInvalidaException e) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_PRECONDITION_FAILED,
						XmlExceptionConversor.toOfertaInvalidaExceptionXml(e), null);
				return;
			}
			return;
		}

		// Caso de uso ACTUALIZAR OFERTA
		OfertaDto ofertaDto;
		try {
			ofertaDto = XmlOfertaDtoConversor.toOferta(req.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_BAD_REQUEST, 
						XmlExceptionConversor.toInputValidationExceptionXml(
								new InputValidationException(ex.getMessage())), null);
			return;

		}
		
		if (!ofertaId.equals(ofertaDto.getOfertaId())) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid OfertaId")),
							null);
			return;
		}
		Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
		try {
			OfertaServiceFactory.getService().actualizarOferta(oferta);
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
		} catch (FechaInvalidaException e) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_CONFLICT,
					XmlExceptionConversor.toFechaInvalidaExceptionXml(e), null);
			return;
		} catch (PrecioDescontadoException e) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GATEWAY_TIMEOUT,
					XmlExceptionConversor.toPrecioDescontadoExceptionXml(e),
					null);
			return;
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid Oferta id")),
							null);
			return;
		}
		String ofertaIdAsString = path.substring(1);
		Long ofertaId;
		try {
			ofertaId = Long.valueOf(ofertaIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid Oferta id '"
													+ ofertaIdAsString + "'")),
							null);

			return;
		}
		try {
			OfertaServiceFactory.getService().eliminarOferta(ofertaId);
		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
		} catch (OfertaReservedException e) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_EXPECTATION_FAILED,
							XmlExceptionConversor
									.toOfertaReservedExceptionXml(e), null);
			return;
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		
		if (path == null || path.length() == 0) {
			String keyWords = req.getParameter("keywords");
			String email = req.getParameter("email");
			
			if (keyWords != null) {

				// Caso de uso BUSCAR OFERTAS POR PALABRAS CLAVE
				List<Oferta> Ofertas = OfertaServiceFactory.getService().buscarOfertas(keyWords, true, Calendar.getInstance());
				List<OfertaDto> OfertaDtos = OfertaToOfertaDtoConversor.toOfertaDtos(Ofertas);
				ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
						XmlOfertaDtoConversor.toXml(OfertaDtos), null);
				
			} else if (email != null) {

				// Caso de uso BUSCAR OFERTAS RESERVADAS DE UN USUARIO
				List<Reserva> reservas = OfertaServiceFactory.getService().buscarReservasDeUnUsuario(email, false);

				List<OfertaReservadaDto> ofertasReservadas = new ArrayList<>();
				try {
					OfertaReservadaDto or = null;
					Oferta o = null;
					for (Reserva i : reservas) {
						o = OfertaServiceFactory.getService().buscarOferta(i.getOfertaId());
						or = new OfertaReservadaDto(o.getDescripcion(),
								o.getPrecioDescontado(), i.getFechaDeReserva());
						ofertasReservadas.add(or);
					}
					
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
							XmlOfertaReservadaDtoConversor.toXml(ofertasReservadas), null);
				} catch (InstanceNotFoundException e) {
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor.toInstanceNotFoundExceptionXml(e), null);
				}
				
			}
			
		} else {
			// Caso de uso BUSCAR OFERTA POR ID
			String ofertaIdAsString = path.substring(1);
			Long ofertaId;
			try {
				ofertaId = Long.valueOf(ofertaIdAsString);
			} catch (NumberFormatException ex) {
				ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
										"Invalid Request: " + "invalid oferta id'" + ofertaIdAsString + "'")), null);

				return;
			}
			Oferta oferta;
			try {
				oferta = OfertaServiceFactory.getService().buscarOferta(ofertaId);
			} catch (InstanceNotFoundException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_NOT_FOUND, XmlExceptionConversor
								.toInstanceNotFoundExceptionXml(ex), null);
				return;
			}
			OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlOfertaDtoConversor.toXml(ofertaDto), null);
		}
	}

}
