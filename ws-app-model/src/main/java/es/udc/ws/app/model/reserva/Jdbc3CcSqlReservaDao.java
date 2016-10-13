package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao{

	@Override
	public Reserva crear(Connection c,Reserva r) {
		
		String peticion = "INSERT INTO Reserva"+
				" (email,tarjeta,idOferta,fechaReserva,estado,precioReserva) "+
				"VALUES(?,?,?,?,?,?) ";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion, Statement.RETURN_GENERATED_KEYS)) {
			
			int i = 1;
			preparada.setString(i++, r.getEmail());
			preparada.setString(i++, r.getTarjeta());
			preparada.setLong(i++, r.getIdOferta());
			
			Timestamp fecha = r.getFechaReserva() != null ? new Timestamp(
					r.getFechaReserva().getTimeInMillis()) : null;
		
			preparada.setTimestamp(i++, fecha);
			
			preparada.setString(i++,r.getEstado().toString());
			preparada.setFloat(i++, r.getPrecioReserva());
			
			preparada.executeUpdate();
			
			ResultSet resultado = preparada.getGeneratedKeys();
			
			if (!resultado.next()) {
				throw new SQLException("JDBC driver did not return generated key.");
			}
			
			Long codigo = resultado.getLong(1);
			
			return new Reserva(codigo,r.getEmail(),r.getTarjeta(),r.getIdOferta(),r.getEstado(),r.getFechaReserva(),r.getPrecioReserva());
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
