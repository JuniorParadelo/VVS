package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlOfertaDao extends AbstractSqlOfertaDao{
		
	@Override
	public Oferta crear(Connection c,Oferta o) {
		
		String peticion = "INSERT INTO Oferta"+
						" (nombre,descripcion,fechaLimReserva,fechaLimReclamacion,precioReal,precioDescontado,comisionVenta,invalida) "+
						" VALUES(?,?,?,?,?,?,?,?) ";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion, Statement.RETURN_GENERATED_KEYS)) {
			
			int i =1;
			preparada.setString(i++,o.getNombre());
			preparada.setString(i++,o.getDescripcion());
			Timestamp fechaLimReserva = o.getFechaLimReserva() != null ? new Timestamp(
						o.getFechaLimReserva().getTimeInMillis()) : null;
			
			Timestamp fechaLimReclamacion = o.getFechaLimReclamacion() != null ? new Timestamp(
						o.getFechaLimReclamacion().getTimeInMillis()) : null;
			
			preparada.setTimestamp(i++, fechaLimReserva);
			preparada.setTimestamp(i++,fechaLimReclamacion);
			preparada.setFloat(i++, o.getPrecioReal());
			preparada.setFloat(i++, o.getPrecioDescontado());
			preparada.setFloat(i++, o.getComisionVenta());
			preparada.setBoolean(i++, o.isInvalida());
			
			preparada.executeUpdate();
			
			ResultSet resultado = preparada.getGeneratedKeys();
			
			if(!resultado.next()) {
				throw new SQLException("JDBC driver did not return generated key.");
			}
			Long idOferta = resultado.getLong(1);
			
			return new Oferta(idOferta,o.getNombre(),o.getDescripcion(),o.getFechaLimReserva(),o.getFechaLimReclamacion(),
						o.getPrecioReal(),o.getPrecioDescontado(),o.getComisionVenta(),o.isInvalida());
			
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
