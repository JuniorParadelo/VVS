package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.configuration.ConfigurationParametersManager;
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
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlOfertaReservadaDtoConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class RestClientOfertaService implements ClientOfertaService {

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientOfertaService.endpointAddress";
	private String endpointAddress;

	@Override
	public Long añadirOferta(OfertaDto oferta) throws InputValidationException {

		try {

			HttpResponse response = Request
					.Post(getEndpointAddress() + "ofertas")
					.bodyStream(toInputStream(oferta),
							ContentType.create("application/xml")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_CREATED, response);

			return XmlOfertaDtoConversor.toOferta(
					response.getEntity().getContent()).getOfertaId();

		} catch (InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void actualizarOferta(OfertaDto oferta)
			throws InputValidationException, InstanceNotFoundException,
			FechaInvalidaException, PrecioDescontadoException {

		try {

			HttpResponse response = Request
					.Put(getEndpointAddress() + "ofertas/"
							+ +oferta.getOfertaId())
					.bodyStream(toInputStream(oferta),
							ContentType.create("application/xml")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InputValidationException | InstanceNotFoundException
				| FechaInvalidaException | PrecioDescontadoException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void eliminarOferta(Long ofertaId) throws InstanceNotFoundException,
			OfertaReservedException {

		try {

			HttpResponse response = Request
					.Delete(getEndpointAddress() + "ofertas/" + ofertaId)
					.execute().returnResponse();

			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InstanceNotFoundException | OfertaReservedException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public OfertaDto buscarOferta(Long ofertaId)
			throws InstanceNotFoundException {

		try {

			HttpResponse response = Request
					.Get(getEndpointAddress() + "ofertas/"
							+ Long.toString(ofertaId)).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlOfertaDtoConversor.toOferta(response.getEntity()
					.getContent());

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<OfertaDto> buscarOfertas(String keywords) {

		try {

			HttpResponse response = Request
					.Get(getEndpointAddress() + "ofertas?keywords="
							+ URLEncoder.encode(keywords, "UTF-8")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlOfertaDtoConversor.toOfertas(response.getEntity()
					.getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Long reservarOferta(Long ofertaId, String email, String tarjeta)
			throws InstanceNotFoundException, InputValidationException,
			OfertaExpirationException, OfertaInvalidaException,
			OfertaReservedByUserException {

		try {

			HttpResponse response = Request
					.Post(getEndpointAddress() + "reservas")
					.bodyForm(
							Form.form()
									.add("ofertaId", Long.toString(ofertaId))
									.add("email", email)
									.add("tarjeta", tarjeta).build()).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_CREATED, response);

			return (long) XmlReservaDtoConversor.toReserva(
					response.getEntity().getContent()).getCodigo();

		} catch (InputValidationException | InstanceNotFoundException
				| OfertaExpirationException | OfertaInvalidaException
				| OfertaReservedByUserException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void invalidarOferta(Long ofertaId)
			throws InstanceNotFoundException, InputValidationException,
			FechaInvalidaException, PrecioDescontadoException,
			OfertaInvalidaException {


		try {

			HttpResponse response = Request.Put(getEndpointAddress() + "ofertas/"
							+ Long.toString(ofertaId)).execute().returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ReservaDto> buscarReservasDeUnaOferta(Long ofertaId) throws InstanceNotFoundException {

		try {

			HttpResponse response = Request
					.Get(getEndpointAddress() + "reservas?offer="
							+ URLEncoder.encode(String.valueOf(ofertaId), "UTF-8")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlReservaDtoConversor.toReservas(response.getEntity().getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ReservaDto> buscarReservasDeUnUsuario(String email, boolean validas) {

		try {

			HttpResponse response = Request
					.Get(getEndpointAddress() + "reservas?email="
							+ URLEncoder.encode(email, "UTF-8")
							+ "&valid="
							+ URLEncoder.encode(String.valueOf(validas), "UTF-8")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlReservaDtoConversor.toReservas(response.getEntity().getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OfertaReservadaDto> buscarOfertasReservadasDeUnUsuario(String email) {

		try {

			HttpResponse response = Request
					.Get(getEndpointAddress() + "ofertas?email="
							+ URLEncoder.encode(email, "UTF-8")).execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);

			return XmlOfertaReservadaDtoConversor.toOfertasReservadas(response.getEntity().getContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reclamarReserva(int codigo) throws InstanceNotFoundException,
			ReservaNoValidaException, ReservaExpirationException {

		try {

			HttpResponse response = Request
					.Put(getEndpointAddress() + "reservas/" + codigo)
					.execute()
					.returnResponse();

			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);

		} catch (InstanceNotFoundException
				| ReservaNoValidaException | ReservaExpirationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized String getEndpointAddress() {
		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}

	private InputStream toInputStream(OfertaDto oferta) {

		try {

			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

			outputter.output(XmlOfertaDtoConversor.toXml(oferta),
					xmlOutputStream);

			return new ByteArrayInputStream(xmlOutputStream.toByteArray());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void validateStatusCode(int successCode, HttpResponse response)
			throws InputValidationException, InstanceNotFoundException,
			FechaInvalidaException, PrecioDescontadoException,
			OfertaExpirationException, OfertaInvalidaException,
			OfertaReservedByUserException, ReservaNoValidaException,
			ReservaExpirationException, ParsingException,
			OfertaReservedException {

		try {

			int statusCode = response.getStatusLine().getStatusCode();

			/* Success? */
			if (statusCode == successCode) {
				return;
			}

			/* Handler error. */
			switch (statusCode) {

			case HttpStatus.SC_NOT_FOUND:
				throw XmlExceptionConversor
						.fromInstanceNotFoundExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_BAD_REQUEST:
				throw XmlExceptionConversor
						.fromInputValidationExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_CONFLICT:
				throw XmlExceptionConversor
						.fromFechaInvalidaExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_GONE:
				throw XmlExceptionConversor
						.fromOfertaExpirationExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_FORBIDDEN:
				throw XmlExceptionConversor
						.fromOfertaReservedByUserExceptionXml(response
								.getEntity().getContent());

			case HttpStatus.SC_EXPECTATION_FAILED:
				throw XmlExceptionConversor
						.fromOfertaReservedExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_GATEWAY_TIMEOUT:
				throw XmlExceptionConversor
						.fromPrecioDescontadoExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_BAD_GATEWAY:
				throw XmlExceptionConversor
						.fromReservaExpirationExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_UNAUTHORIZED:
				throw XmlExceptionConversor
						.fromReservaNoValidaExceptionXml(response.getEntity()
								.getContent());

			case HttpStatus.SC_PRECONDITION_FAILED:
				throw XmlExceptionConversor
						.fromOfertaInvalidaExceptionXml(response.getEntity()
								.getContent());

			default:
				throw new RuntimeException("HTTP error; status code = "
						+ statusCode);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
