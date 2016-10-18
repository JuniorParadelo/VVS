package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao {

	@Override
	public Reserva create(Connection connection, Reserva reserva) {

		/* Create "queryString". */
		String queryString = "INSERT INTO Reserva"
				+ " (ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString, Statement.RETURN_GENERATED_KEYS)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, reserva.getOfertaId());
			preparedStatement.setString(i++, reserva.getEmail());
			preparedStatement.setString(i++, reserva.getTarjeta());
			
			Timestamp fechaDeReserva = reserva.getFechaDeReserva() != null ? new Timestamp(
					reserva.getFechaDeReserva().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaDeReserva);
			
			preparedStatement.setString(i++, reserva.getEstado().toString());
			
			preparedStatement.setInt(i++, reserva.getCodigo());
			
			preparedStatement.setFloat(i++, reserva.getPrecio());


			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long reservaID = resultSet.getLong(1);

			/* Return sale. */
			return new Reserva(reservaID, reserva.getOfertaId(),
					reserva.getEmail(), reserva.getTarjeta(), reserva.getFechaDeReserva(), reserva.getEstado(), reserva.getCodigo(), reserva.getPrecio());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
