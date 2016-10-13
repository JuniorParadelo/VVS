package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.app.reserva.EstadoReserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlReservaDao {

	public abstract Reserva crear(Connection c,Reserva r);
	public abstract void eliminar(Connection c,Long codigo) throws InstanceNotFoundException,InputValidationException;
	public abstract void actualizar(Connection c,Reserva r) throws InstanceNotFoundException;
	public abstract void actualizar(Connection c,EstadoReserva e,Long idOferta) throws InstanceNotFoundException;
	public abstract List<Reserva> buscar (Connection c,Long idOferta) ;
	public abstract List<Reserva> buscar (Connection c,String usuario,boolean soloValidas);
	public abstract Reserva buscarID (Connection c,Long codigo) throws InputValidationException,InstanceNotFoundException;
	
	
}
