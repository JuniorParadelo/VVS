package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.OfertaInvalidaException;
import es.udc.ws.app.exceptions.OfertaReservedByUserException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.exceptions.ReservaNoValidaException;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ReservasServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Caso de uso RESERVAR OFERTA
		String ofertaIdParameter = req.getParameter("ofertaId");
		if (ofertaIdParameter == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'ofertaId' is mandatory")),
							null);
			return;
		}

		Long ofertaId;
		try {
			ofertaId = Long.valueOf(ofertaIdParameter);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'ofertaId' is invalid '"
													+ ofertaIdParameter + "'")),
							null);

			return;
		}

		String email = req.getParameter("email");
		if (email == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'email' is mandatory")),
							null);
			return;
		}

		String tarjeta = req.getParameter("tarjeta");
		if (tarjeta == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'tarjeta' is mandatory")),
							null);

			return;
		}

		Reserva reserva;
		try {
			reserva = OfertaServiceFactory.getService().reservarOferta(
					ofertaId, email, tarjeta);
		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		} catch (OfertaExpirationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toOfertaExpirationExceptionXml(ex),
					null);
			return;
		} catch (OfertaInvalidaException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_PRECONDITION_FAILED,
					XmlExceptionConversor.toOfertaInvalidaExceptionXml(ex),
					null);
			return;
		} catch (OfertaReservedByUserException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_FORBIDDEN, XmlExceptionConversor
							.toOfertaReservedByUserExceptionXml(ex), null);
			return;
		}

		ReservaDto reservaDto = ReservaToReservaDtoConversor.toReservaDto(reserva);
		String reservaURL = ServletUtils.normalizePath(req.getRequestURL()
				.toString()) + "/" + reserva.getReservaId().toString();

		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", reservaURL);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
				XmlReservaDtoConversor.toResponse(reservaDto), headers);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = ServletUtils.normalizePath(req.getPathInfo());
		
		// Caso de uso RECLAMAR RESERVA
		String reservaCodeAsString = path.substring(1);
		int reservaCode;
		try {
			reservaCode = Integer.valueOf(reservaCodeAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp, HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: " + "invalid reserva code'" + reservaCodeAsString + "'")),
							null);

			return;
		}

		try {
			OfertaServiceFactory.getService().reclamarReserva(reservaCode);

		} catch (InstanceNotFoundException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NOT_FOUND,
					XmlExceptionConversor.toInstanceNotFoundExceptionXml(ex),
					null);
			return;
		} catch (ReservaNoValidaException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_UNAUTHORIZED,
					XmlExceptionConversor.toReservaNoValidaExceptionXml(ex),
					null);
			return;
		} catch (ReservaExpirationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_GATEWAY,
					XmlExceptionConversor.toReservaExpirationExceptionXml(ex),
					null);
			return;
		}
		
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = ServletUtils.normalizePath(req.getPathInfo());
		
		if (path == null || path.length() == 0) {

			String ofertaIdAsString = req.getParameter("offer");
			String email = req.getParameter("email");
			String valid = req.getParameter("valid");
			
			if (ofertaIdAsString != null) {
				// Caso de uso BUSCAR RESERVAS DE UNA OFERTA
				Long ofertaId;
				try {
					ofertaId = Long.valueOf(ofertaIdAsString);
				} catch (NumberFormatException ex) {
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
						XmlExceptionConversor.toInputValidationExceptionXml(new InputValidationException(
							"Invalid Request: " + "invalid oferta id'" + ofertaIdAsString + "'")), null);
					return;
				}
				
				try {
					List<Reserva> reservas = OfertaServiceFactory.getService().buscarReservasDeUnaOferta(ofertaId);
					List<ReservaDto> reservaDtos = ReservaToReservaDtoConversor.toReservaDtos(reservas);
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
							XmlReservaDtoConversor.toXml(reservaDtos), null);
				} catch (InstanceNotFoundException e) {
					ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor.toInstanceNotFoundExceptionXml(e), null);
				}
			} else if (email != null && valid != null) {
				// Caso de uso BUSCAR RESERVAS DE UN USUARIO
				boolean reservasValidas = Boolean.valueOf(valid);
				
				List<Reserva> reservas = OfertaServiceFactory.getService().buscarReservasDeUnUsuario(email, reservasValidas);
				List<ReservaDto> reservaDtos = ReservaToReservaDtoConversor.toReservaDtos(reservas);
				ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
						XmlReservaDtoConversor.toXml(reservaDtos), null);
			}
		}
	}

}
