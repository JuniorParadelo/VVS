package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlOfertaDao {

	public Oferta crear(Connection c,Oferta o);
	public void actualizar(Connection c,Oferta o) throws InstanceNotFoundException;
	public void eliminar(Connection c,Long idOferta) throws InstanceNotFoundException;
	public Oferta buscar(Connection c,Long idOferta) throws InstanceNotFoundException;
	public List<Oferta> buscar(Connection c,String palabras,Calendar fecha,boolean soloValidas);
	
}

