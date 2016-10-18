package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlOfertaDao extends AbstractSqlOfertaDao {

	@Override
	public Oferta create(Connection connection, Oferta oferta) {

		/* Create "queryString". */
		String queryString = "INSERT INTO Oferta"
				+ " (nombre, descripcion, fechaReservar, fechaReclamar, precioReal, precioDescontado, comision, estado)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString, Statement.RETURN_GENERATED_KEYS)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, oferta.getNombre());
			preparedStatement.setString(i++, oferta.getDescripcion());

			Timestamp fechaReservar = oferta.getFechaReservar() != null ? new Timestamp(
					oferta.getFechaReservar().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaReservar);
			
			Timestamp fechaReclamar = oferta.getFechaReclamar() != null ? new Timestamp(
					oferta.getFechaReclamar().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaReclamar);

			preparedStatement.setFloat(i++, oferta.getPrecioReal());
			preparedStatement.setFloat(i++, oferta.getPrecioDescontado());

			preparedStatement.setFloat(i++, oferta.getComision());
			preparedStatement.setString(i++, oferta.getEstado().toString());

			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long ofertaId = resultSet.getLong(1);

			/* Return movie. */
			return new Oferta(ofertaId, oferta.getNombre(),
					oferta.getDescripcion(), oferta.getFechaReservar(), oferta.getFechaReclamar(), 
					oferta.getPrecioReal(), oferta.getPrecioDescontado(),
					oferta.getComision(), oferta.getEstado());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
