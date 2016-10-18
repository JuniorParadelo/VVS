package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.enums.EstadoOferta;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlOfertaDao {
	
    public Oferta create(Connection connection, Oferta oferta);

    public Oferta find(Connection connection, Long ofertaId) throws InstanceNotFoundException;

    public List<Oferta> findByKeywords(Connection connection, String keywords, EstadoOferta estado, Calendar fecha);

    public void update(Connection connection, Oferta oferta) throws InstanceNotFoundException;

    public int cancelReservations(Connection connection, Long ofertaId);
    
    public void remove(Connection connection, Long ofertaId) throws InstanceNotFoundException;
    
    public boolean isReserved(Connection connection, Long ofertaId) throws InstanceNotFoundException;
    
}
