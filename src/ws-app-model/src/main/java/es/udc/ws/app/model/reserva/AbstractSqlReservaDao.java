package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.enums.EstadoReserva;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlReservaDao implements SqlReservaDao {

	protected AbstractSqlReservaDao() {
	}

	@Override
	public Reserva find(Connection connection, Long reservaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio FROM Reserva WHERE reservaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, reservaId.longValue());

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(reservaId,
						Reserva.class.getName());
			}

			/* Get results. */
			i = 1;
			Long ofertaId = resultSet.getLong(i++);
			String email = resultSet.getString(i++);
			String tarjeta = resultSet.getString(i++);
			
			Calendar fechaDeReserva = Calendar.getInstance();
			fechaDeReserva.setTime(resultSet.getTimestamp(i++));
			
			String estadoString = resultSet.getString(i++);
			EstadoReserva estado;
			
			if (estadoString.equals(EstadoReserva.INVALIDA.toString())) {
				estado = EstadoReserva.INVALIDA;
			}
			else{
				if (estadoString.equals(EstadoReserva.VALIDA.toString())) {
					estado = EstadoReserva.VALIDA;
				}
				else {
					if (estadoString.equals(EstadoReserva.RECLAMADA.toString())) {
						estado = EstadoReserva.RECLAMADA;
					}
					else estado = EstadoReserva.ERRONEO;
				}
				
			}
			
			int codigo = resultSet.getInt(i++);
			float precio = resultSet.getFloat(i++);

			/* Return sale. */
			return new Reserva(reservaId, ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

    
    @Override
	public List<Reserva> findByOfertaId (Connection connection, Long ofertaId) {

        String queryString = "SELECT reservaId, email, tarjeta, fechaDeReserva, estado, codigo, precio FROM Reserva WHERE ofertaId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, ofertaId);

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read movies. */
			List<Reserva> reservas = new ArrayList<Reserva>();

			while (resultSet.next()) {

				i = 1;
	            Long reservaId = resultSet.getLong(i++);
	            String email = resultSet.getString(i++);
	            String tarjeta = resultSet.getString(i++);
	            
	            Calendar fechaDeReserva = Calendar.getInstance();
				fechaDeReserva.setTime(resultSet.getTimestamp(i++));
	         
				
				String estadoString = resultSet.getString(i++);
				EstadoReserva estado;
				
				if (estadoString.equals(EstadoReserva.INVALIDA.toString())) {
					estado = EstadoReserva.INVALIDA;
				}
				else{
					if (estadoString.equals(EstadoReserva.VALIDA.toString())) {
						estado = EstadoReserva.VALIDA;
					}
					else {
						if (estadoString.equals(EstadoReserva.RECLAMADA.toString())) {
							estado = EstadoReserva.RECLAMADA;
						}
						else estado = EstadoReserva.ERRONEO;
					}
					
				}
				
				int codigo = resultSet.getInt(i++);
				
				float precio = resultSet.getFloat(i++);

				reservas.add(new Reserva(reservaId, ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio));

			}

			/* Return movies. */
			return reservas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
    
    //Validas = 1   -> Solo las validas
    //Validas = 0   -> Todas
    
    @Override
	public List<Reserva> findByUser (Connection connection, String email, boolean validas) {

        String queryString = "SELECT reservaId, ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio FROM Reserva WHERE email = ?";
        
        if (validas) {
        	queryString += " AND estado = 'VALIDA'";
        }
        
			try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
				
	            /* Fill "preparedStatement". */
	            int i = 1;
	            preparedStatement.setString(i++, email);
	
				/* Execute query. */
				ResultSet resultSet = preparedStatement.executeQuery();
	
				/* Read movies. */
				List<Reserva> reservas = new ArrayList<Reserva>();
	
				while (resultSet.next()) {
	
					i = 1;
		            Long reservaId = resultSet.getLong(i++);
		            Long ofertaId = resultSet.getLong(i++);
		            String email2 = resultSet.getString(i++);
		            String tarjeta = resultSet.getString(i++);
		            
		            Calendar fechaDeReserva = Calendar.getInstance();
					fechaDeReserva.setTime(resultSet.getTimestamp(i++));
					
					String estadoString = resultSet.getString(i++);
					EstadoReserva estado;
					
					if (estadoString.equals(EstadoReserva.INVALIDA.toString())) {
						estado = EstadoReserva.INVALIDA;
					}
					else{
						if (estadoString.equals(EstadoReserva.VALIDA.toString())) {
							estado = EstadoReserva.VALIDA;
						}
						else {
							if (estadoString.equals(EstadoReserva.RECLAMADA.toString())) {
								estado = EstadoReserva.RECLAMADA;
							}
							else estado = EstadoReserva.ERRONEO;
						}
						
					}
					
					int codigo = resultSet.getInt(i++);
					float precio = resultSet.getFloat(i++);
	
					reservas.add(new Reserva(reservaId, ofertaId, email2, tarjeta, fechaDeReserva, estado, codigo, precio));
	
				}
	
				/* Return movies. */
				return reservas;
	
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
        

	}
    
    
    @Override
	public Reserva findByCode(Connection connection, int codigo) throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT reservaId, ofertaId, email, tarjeta, fechaDeReserva, estado, codigo, precio FROM Reserva WHERE codigo = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setInt(i++, codigo);

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(codigo, Reserva.class.getName());
			}

			/* Get results. */
			i = 1;
			Long reservaId = resultSet.getLong(i++);
			Long ofertaId = resultSet.getLong(i++);
			String email = resultSet.getString(i++);
			String tarjeta = resultSet.getString(i++);
			
			Calendar fechaDeReserva = Calendar.getInstance();
			fechaDeReserva.setTime(resultSet.getTimestamp(i++));
			
			String estadoString = resultSet.getString(i++);
			EstadoReserva estado;
			
			if (estadoString.equals(EstadoReserva.INVALIDA.toString())) {
				estado = EstadoReserva.INVALIDA;
			}
			else{
				if (estadoString.equals(EstadoReserva.VALIDA.toString())) {
					estado = EstadoReserva.VALIDA;
				}
				else {
					if (estadoString.equals(EstadoReserva.RECLAMADA.toString())) {
						estado = EstadoReserva.RECLAMADA;
					}
					else estado = EstadoReserva.ERRONEO;
				}
				
			}
			
			int codigo2 = resultSet.getInt(i++);
			
			float precio = resultSet.getFloat(i++);

			/* Return sale. */
			return new Reserva(reservaId, ofertaId, email, tarjeta, fechaDeReserva, estado, codigo2, precio);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
    
    
    

	@Override
	public void update(Connection connection, Reserva reserva) throws InstanceNotFoundException {


		/* Create "queryString". */
		String queryString = "UPDATE Reserva"
				+ " SET ofertaId = ?, email = ?, tarjeta = ?, fechaDeReserva = ?, estado = ?, codigo = ?, precio = ? WHERE reservaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, reserva.getOfertaId());
			preparedStatement.setString(i++, reserva.getEmail());
			preparedStatement.setString(i++, reserva.getTarjeta());
			
			Timestamp fechaDeReserva = reserva.getFechaDeReserva() != null ? new Timestamp(reserva.getFechaDeReserva().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, fechaDeReserva);
			
			preparedStatement.setString(i++, reserva.getEstado().toString());
			preparedStatement.setInt(i++, reserva.getCodigo());
			preparedStatement.setFloat(i++, reserva.getPrecio());
			preparedStatement.setLong(i++, reserva.getReservaId());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(reserva.getOfertaId(),
						Reserva.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, Long reservaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Reserva WHERE" + " reservaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, reservaId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(reservaId,
						Reserva.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public boolean isReservedByUser(Connection connection, Long ofertaId, String email) throws InstanceNotFoundException {
			
		/* Create "queryString". */
		String queryString = " SELECT CASE WHEN ((select count(*) from Reserva where ofertaId = ? AND email = ?) > 0) THEN 1  ELSE 0 END AS MY_BOOLEAN_COLUMN";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId.longValue());
			preparedStatement.setString(i++, email);

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
	
	

}
