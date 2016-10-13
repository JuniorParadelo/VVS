package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.reserva.EstadoReserva;
import es.udc.ws.util.exceptions.InstanceNotFoundException;


public abstract class AbstractSqlReservaDao implements SqlReservaDao {

	@Override
	public void eliminar(Connection c,Long codigo) throws InstanceNotFoundException{
		
		String peticion = "DELETE FROM Reserva WHERE codigo= ?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i = 1;
			preparada.setLong(i++, codigo);
			
			int filas_afectadas;
			filas_afectadas = preparada.executeUpdate();
			
			if (filas_afectadas==0) {
				throw new InstanceNotFoundException(codigo,Reserva.class.getName());
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	@Override
	public void actualizar(Connection c,Reserva r) throws InstanceNotFoundException{
		
		int [] noNulos = new int [6];
		
		for (int z=0;z<6;z++) {
			noNulos[z]=0;
		}
		
		String peticion = "UPDATE Reserva";
		
		boolean yaEmpezo = false;
			
		if (r.getEmail()!=null) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET email = ?";
			}
			else {
				peticion+=", email = ?";
			}
			noNulos[0]=1;
		}
		
		if (r.getEstado()!=null) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET estado = ?";
			}
			else {
				peticion+=", estado = ?";
			}
			noNulos[1]=1;
		}
		
		if (r.getIdOferta()!=null) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET idOferta = ?";
			}
			else {
				peticion+=", idOferta = ?";
			}
			noNulos[2]=1;
		}
		
		if (r.getTarjeta()!=null) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET tarjeta = ?";
			}
			else {
				peticion+=", tarjeta = ?";
			}
			noNulos[3]=1;
		}
		
		if (r.getFechaReserva()!=null) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET fechaReserva = ?";
			}
			else {
				peticion+=", fechaReserva = ?";
			}
			noNulos[4]=1;
		}
		
		if (r.getPrecioReserva()!=-1) {
			if (!yaEmpezo) {
				yaEmpezo=true;
				peticion+=" SET precioReserva = ?";
			}
			else {
				peticion+=", precioReserva = ?";
			}
			noNulos[5]=1;
		}
		
		peticion+=" WHERE codigo = ?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i = 1;
			if (noNulos[0]!=0) {
				preparada.setString(i++,r.getEmail());
			}
			
			if (noNulos[1]!=0) {
				preparada.setString(i++, r.getEstado().toString());
			}
			
			if (noNulos[2]!=0) {
				preparada.setLong(i++, r.getIdOferta());
			}
			
			if (noNulos[3]!=0) {
				preparada.setString(i++,r.getTarjeta());
			}
			
			if (noNulos[4]!=0) {
				Timestamp fechaReserva = r.getFechaReserva() != null ? new Timestamp(
						r.getFechaReserva().getTimeInMillis()) : null;
				preparada.setTimestamp(i++, fechaReserva);
			}
			
			if (noNulos[5]!=0) {
				preparada.setFloat(i++, r.getPrecioReserva());
			}
			
			preparada.setLong(i++, r.getCodigo());
			
			int filas_afectadas = preparada.executeUpdate();
			
			if (filas_afectadas==0) {
				throw new InstanceNotFoundException(r.getCodigo(), Reserva.class.getName());
			}
			
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<Reserva> buscar(Connection c,Long idOferta) {
		
		String peticion = "SELECT codigo,email,tarjeta,idOferta,fechaReserva,estado,precioReserva"+
							" FROM Reserva"+
							" WHERE idOferta = ?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i = 1;
			preparada.setLong(i++, idOferta);
			
			ResultSet resultado = preparada.executeQuery();
			
			List<Reserva> lista = new ArrayList<>();
			
			while (resultado.next()) {
				i = 1;
				Long cod = resultado.getLong(i++);
				String email = resultado.getString(i++);
				String tarjeta = resultado.getString(i++);
				Long idOf = resultado.getLong(i++);
				
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultado.getTimestamp(i++));
				
				EstadoReserva estado = EstadoReserva.toEstado(resultado.getString(i++));
				float precioReserva = resultado.getFloat(i++);
			
				lista.add(new Reserva(cod,email,tarjeta,idOf,estado,fecha,precioReserva));
			}
			
			return lista;
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	public List<Reserva> buscar (Connection c,String usuario,boolean soloValidas) {
		

		String peticion = "SELECT codigo,email,tarjeta,idOferta,fechaReserva,estado,precioReserva "+
							"FROM Reserva "+
							"WHERE (email) LIKE ?";
		
		if (soloValidas) {
			peticion+=" AND (estado) LIKE ?";
		}
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i = 1;
			preparada.setString(i++, usuario);
			
			if (soloValidas) {
				preparada.setString(i++, EstadoReserva.PENDIENTE.toString());
			}
			
			ResultSet resultado;
			resultado = preparada.executeQuery();
			
			List<Reserva> reservas = new ArrayList<>();
			
			while (resultado.next()) {
				i = 1;
				Long codigo = resultado.getLong(i++);
				String email = resultado.getString(i++);
				String tarjeta = resultado.getString(i++);
				Long idOferta = resultado.getLong(i++);
				
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultado.getTimestamp(i++));
				
				EstadoReserva estado = EstadoReserva.toEstado(resultado.getString(i++));
				float precioReserva = resultado.getFloat(i++);
				
				Reserva r = new Reserva(codigo,email,tarjeta,idOferta,estado,fecha,precioReserva);
				
				reservas.add(r);
			}
			
			return reservas;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public  Reserva buscarID (Connection c,Long codigo) throws InstanceNotFoundException {
		
		String peticion = "SELECT codigo,email,tarjeta,idOferta,fechaReserva,estado,precioReserva"+
						  " FROM Reserva"+
						  " WHERE codigo = ?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion) ) {
			
			int i = 1;
			preparada.setLong(i++, codigo);
			
			ResultSet resultado = preparada.executeQuery();
			
			if (resultado.next()) {
				i=1;
				Long cod = resultado.getLong(i++);
				String usuario = resultado.getString(i++);
				String tarjeta = resultado.getString(i++);
				Long id = resultado.getLong(i++);
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultado.getTimestamp(i++));
				EstadoReserva es = EstadoReserva.toEstado(resultado.getString(i++));
				float precioReserva = resultado.getFloat(i++);
				
				return new Reserva(cod,usuario,tarjeta,id,es,fecha,precioReserva);
			}
			else {
				throw new InstanceNotFoundException(codigo, "Reserva");
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}


	public void actualizar(Connection c,EstadoReserva estado,Long idOferta) throws InstanceNotFoundException {
		
		String peticion = "UPDATE Reserva SET estado = ? WHERE idOferta = ? AND estado NOT LIKE ?";
		
		
		try (PreparedStatement preparada = c.prepareStatement(peticion) ) {
			
			int i = 1;
			
			preparada.setString(i++, estado.toString());
			preparada.setLong(i++, idOferta);
			preparada.setString(i++, EstadoReserva.RECLAMADA.toString());
			
			int filas_afectadas = preparada.executeUpdate();
			
			if (filas_afectadas == 0) {
				throw new InstanceNotFoundException(idOferta,"ID DE LA OFERTA");
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
