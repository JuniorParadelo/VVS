package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlReservaDao {

    public Reserva create(Connection connection, Reserva reserva);

    public Reserva find(Connection connection, Long reservaId) throws InstanceNotFoundException;

    public void update(Connection connection, Reserva reserva) throws InstanceNotFoundException;

    public void remove(Connection connection, Long reservaId) throws InstanceNotFoundException;
    
	public List<Reserva> findByOfertaId (Connection connection, Long ofertaId);
	
	public List<Reserva> findByUser (Connection connection, String email, boolean validas);
	
	public Reserva findByCode (Connection connection, int codigo)  throws InstanceNotFoundException;
	
	public boolean isReservedByUser(Connection connection, Long ofertaId, String email) throws InstanceNotFoundException;

	
}
