package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.app.enums.EstadoReserva;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlOfertaDao implements SqlOfertaDao {

	protected AbstractSqlOfertaDao() {
	}
	
	@Override
	public boolean isReserved(Connection connection, Long ofertaId)
			throws InstanceNotFoundException {
			
		/* Create "queryString". */
		String queryString = " SELECT CASE WHEN ((select count(*) from Reserva where ofertaId = ?) > 0) THEN 1  ELSE 0 END AS MY_BOOLEAN_COLUMN";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId.longValue());

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(ofertaId,
						Oferta.class.getName());
			}

			/* Get results. */
			i = 1;
			Boolean result = resultSet.getBoolean(i++);
			return result;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Oferta find(Connection connection, Long ofertaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT nombre, descripcion, "
				+ "fechaReservar, fechaReclamar, precioReal, precioDescontado, comision, estado FROM Oferta WHERE ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId.longValue());

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(ofertaId,
						Oferta.class.getName());
			}

			/* Get results. */
			i = 1;
			String nombre = resultSet.getString(i++);
			String descripcion = resultSet.getString(i++);

			Calendar fechaReservar = Calendar.getInstance();
			fechaReservar.setTime(resultSet.getTimestamp(i++));
			
			Calendar fechaReclamar = Calendar.getInstance();
			fechaReclamar.setTime(resultSet.getTimestamp(i++));

			float precioReal = resultSet.getFloat(i++);
			float precioDescontado = resultSet.getFloat(i++);
			float comision = resultSet.getFloat(i++);
			
			String estadoString = resultSet.getString(i++);
			EstadoOferta estado;
			
			if (estadoString.equals(EstadoOferta.INVALIDA.toString())) {
				estado = EstadoOferta.INVALIDA;
			}
			else{
				if (estadoString.equals(EstadoOferta.VALIDA.toString())) {
					estado = EstadoOferta.VALIDA;
				}
				else estado = EstadoOferta.ERRONEO;
			}

			/* Return movie. */
			return new Oferta(ofertaId, nombre, descripcion, fechaReservar, fechaReclamar,
					precioReal, precioDescontado, comision, estado);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Oferta> findByKeywords(Connection connection, String keywords, EstadoOferta mEstado, Calendar fecha) {

		/* Create "queryString". */
		String[] words = keywords != null ? keywords.split(" ") : null;
		String queryString = "SELECT ofertaId, nombre, "
				+ "descripcion, fechaReservar, fechaReclamar, precioReal, precioDescontado, comision, estado FROM Oferta";
		//Contiene algún parámetro
		if (((words != null) && (words.length > 0)) || (mEstado != null) || (fecha != null)) {
			queryString += " WHERE";
			
			//Contiene keywords
			if (words != null) {
				for (int i = 0; i < words.length; i++) {
					if (i > 0) {
						queryString += " OR";
					}
					queryString += " ((LOWER(nombre) LIKE LOWER(?)) OR (LOWER(descripcion) LIKE LOWER(?))) ";
				}
			}
			//Contiene estado
			if (mEstado != null) {
				if (words != null) {
					queryString += " AND";
				}
				queryString += " estado = (?)";
			}
			//Contiene fecha
			if (fecha != null) {	
				if ((words != null) || (mEstado != null)) {
					queryString += " AND";
				}
				queryString += " fechaReservar > ?";	
			}
		}
		queryString += " ORDER BY descripcion";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			int aux = 0;
			
			if (words != null) {
				/* Fill "preparedStatement". */
				for (int i = 0; i < words.length; i++) {
					preparedStatement.setString(i*2+1, "%" + words[i] + "%");
					aux++;
					preparedStatement.setString(i*2+2, "%" + words[i] + "%");
					aux++;
				}
			}

			if (mEstado != null) {
				aux++;
				preparedStatement.setString(aux, mEstado.toString());
			}
			
			if (fecha != null) {
				aux++;
				Timestamp fechaReservar = fecha != null ? new Timestamp(fecha.getTime().getTime()) : null;
				preparedStatement.setTimestamp(aux, fechaReservar);
			}
			
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read movies. */
			List<Oferta> ofertas = new ArrayList<Oferta>();

			while (resultSet.next()) {

				int i = 1;
				Long ofertaId = new Long(resultSet.getLong(i++));
				String nombre = resultSet.getString(i++);
				String descripcion = resultSet.getString(i++);

				Calendar fechaReservar2 = Calendar.getInstance();
				fechaReservar2.setTime(resultSet.getTimestamp(i++));
				
				Calendar fechaReclamar = Calendar.getInstance();
				fechaReclamar.setTime(resultSet.getTimestamp(i++));

				float precioReal = resultSet.getFloat(i++);
				float precioDescontado = resultSet.getFloat(i++);

				float comision = resultSet.getFloat(i++);
				
				String estadoString = resultSet.getString(i++);
				EstadoOferta estado;
								
				if (estadoString.equals(EstadoOferta.INVALIDA.toString())) {
					estado = EstadoOferta.INVALIDA;
				}
				else{
					if (estadoString.equals(EstadoOferta.VALIDA.toString())) {
						estado = EstadoOferta.VALIDA;
					}
					else {
						estado = EstadoOferta.ERRONEO;
					}
				}

				ofertas.add(new Oferta(ofertaId, nombre, descripcion,
						fechaReservar2, fechaReclamar, precioReal, precioDescontado, comision,
						estado));
				

			}

			return ofertas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(Connection connection, Oferta oferta)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "UPDATE Oferta"
				+ " SET nombre = ?, descripcion = ?, "
				+ "fechaReservar = ?, fechaReclamar = ?, precioReal = ?, precioDescontado = ?, comision = ?, estado = ? WHERE ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, oferta.getNombre());
			preparedStatement.setString(i++, oferta.getDescripcion());
			
			Timestamp fechaReservar = oferta.getFechaReservar() != null ? new Timestamp(oferta.getFechaReservar().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaReservar);
			
			Timestamp fechaReclamar = oferta.getFechaReclamar() != null ? new Timestamp(oferta.getFechaReclamar().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaReclamar);
			
			preparedStatement.setFloat(i++, oferta.getPrecioReal());
			preparedStatement.setFloat(i++, oferta.getPrecioDescontado());
			preparedStatement.setFloat(i++, oferta.getComision());
			preparedStatement.setString(i++, oferta.getEstado().toString());
            preparedStatement.setLong(i++, oferta.getOfertaId());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(oferta.getOfertaId(),
						Oferta.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public int cancelReservations(Connection connection, Long ofertaId) {
		/* Create "queryString". */
		String queryString = "UPDATE Reserva"
				+ " SET estado = ? WHERE ofertaId = ? AND estado = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, EstadoReserva.INVALIDA.toString());
            preparedStatement.setLong(i++, ofertaId);
            preparedStatement.setString(i++, EstadoReserva.VALIDA.toString());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();
			return updatedRows;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, Long ofertaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Oferta WHERE" + " ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(ofertaId,
						Oferta.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
