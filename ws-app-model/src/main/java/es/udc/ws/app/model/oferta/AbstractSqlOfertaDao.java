package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlOfertaDao implements SqlOfertaDao{

	
	protected AbstractSqlOfertaDao(){
		
	}
	
	@Override
	public List<Oferta> buscar(Connection c,String palabras,Calendar fecha,boolean soloValidas) {
		
		String [] palabrasClave;
		
		if (palabras != null) {
			palabras=palabras.trim();
			palabras=palabras.replaceAll("  "," ");
			palabrasClave=palabras.split(" ");
		}
		else palabrasClave=null;
		
		
		String peticion="SELECT idOferta,nombre,descripcion,fechaLimReserva,fechaLimReclamacion,"+
					"precioReal,precioDescontado,comisionVenta,invalida "+
					"FROM Oferta";
		
		boolean yaHay = false;
		
		if (palabrasClave!=null && palabrasClave.length>0) {
			
			peticion=peticion+" WHERE ";
			
			yaHay = true;
			
			for (int z = 0; z<palabrasClave.length; z++) {
					if (z>0) {
						peticion=peticion+" AND ";
					}
					peticion=peticion+"LOWER(descripcion) LIKE LOWER(?)";
			}
		}
		
		if (fecha!=null) {
			
			if (!yaHay) {
				peticion=peticion+" WHERE fechaLimReserva >= ?";
				yaHay=true;
			}
			else {	
				peticion=peticion+" AND fechaLimReserva >= ?";
			}
		}
		
		if (soloValidas) {
			if (!yaHay) {
				peticion=peticion+" WHERE invalida=?";
				yaHay=true;
			}
			else {
				peticion=peticion+" AND invalida=?";
			}
		}
		
		peticion=peticion+" ORDER BY nombre";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i=0;
			
			if (palabrasClave!=null && palabrasClave.length>0) {
				for (i =0 ; i<palabrasClave.length ; i++) {
					preparada.setString(i+1,"%"+palabrasClave[i]+"%" );
				}
			}
			i++;
			
			if (fecha!=null) {
				Timestamp fechaB = new Timestamp(fecha.getTimeInMillis());
				preparada.setTimestamp(i++, fechaB);
			}
			
			if (soloValidas) {
				preparada.setBoolean(i++, !soloValidas);
			}
			
			ResultSet resultado = preparada.executeQuery();
			
			List<Oferta> ofertas = new ArrayList<>();
			
			while (resultado.next()) {
				i =1;
				Long idOferta = new Long(resultado.getLong(i++));
				String nombre = resultado.getString(i++);
				String descripcion = resultado.getString(i++);
				
				Calendar fechaLimReserva = Calendar.getInstance();
				Calendar fechaLimReclamacion = Calendar.getInstance();
				fechaLimReserva.setTime(resultado.getTimestamp(i++));
				fechaLimReclamacion.setTime(resultado.getTimestamp(i++));
				
				float precioReal = resultado.getFloat(i++);
				float precioDescontado = resultado.getFloat(i++);
				float comisionVenta = resultado.getFloat(i++);
				boolean invalida=resultado.getBoolean(i++);
				
				ofertas.add(new Oferta(idOferta,nombre,descripcion,fechaLimReserva,fechaLimReclamacion,precioReal,precioDescontado,comisionVenta,invalida));
			}
			
			return ofertas;
			
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	
	}
	
	
	@Override
	public Oferta buscar(Connection c,Long idOferta) throws InstanceNotFoundException {
		
		String peticion = "SELECT nombre,descripcion,fechaLimReserva,fechaLimReclamacion,"+
							"precioReal,precioDescontado,comisionVenta,invalida "+
							"FROM Oferta "+
							"WHERE idOferta=?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			int i=1;
			preparada.setLong(i++, idOferta);
			
			ResultSet resultado;
			resultado = preparada.executeQuery();
			
			if (resultado.next()) {
				i =1;
				String nombre = resultado.getString(i++);
				String descripcion = resultado.getString(i++);
				Calendar fechaLimReserva = Calendar.getInstance();
				Calendar fechaLimReclamacion = Calendar.getInstance();
				fechaLimReserva.setTime(resultado.getTimestamp(i++));
				fechaLimReclamacion.setTime(resultado.getTimestamp(i++));
				float precioReal = resultado.getFloat(i++);
				float precioDescontado = resultado.getFloat(i++);
				float comisionVenta = resultado.getFloat(i++);
				boolean invalida = resultado.getBoolean(i++);
			
				return new Oferta(idOferta,nombre,descripcion,fechaLimReserva,fechaLimReclamacion,precioReal,
					precioDescontado,comisionVenta,invalida);
			}
			else {
				throw new InstanceNotFoundException(idOferta, "Oferta no Encontrada");
				
			}
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	
	@Override
	public void eliminar(Connection c,Long idOferta) throws InstanceNotFoundException {
	
		/*Creamos el string de la peticion*/
		String peticion = "DELETE FROM Oferta WHERE"+" idOferta= ?";
		
		try (PreparedStatement preparada = c.prepareStatement(peticion)) {
			
			/*Llenamos la peticion*/
			int i=1;
			preparada.setLong(i++, idOferta);
			
			/*Ejecutamos la peticion*/
			int filas_afectadas;
			filas_afectadas = preparada.executeUpdate();
			
			if (filas_afectadas == 0) {
				throw new InstanceNotFoundException(idOferta, Oferta.class.getName());
			}
			
		}
		catch (SQLException e){
			throw new RuntimeException(e);
		}
		
	}
		
	
	
	@Override
	public void actualizar(Connection c,Oferta o) throws InstanceNotFoundException {
		
		String peticion = "UPDATE Oferta";
		
		int [] noNulos = new int [8];
		
		for (int i = 0;i<8;i++) {
			noNulos[i]=0;
		}
		
		boolean yaReclamo = false;
		
		if (o.getIdOferta()!=null) {
			noNulos[0]=1;
			if (!yaReclamo) {
				peticion+=" SET idOferta = ?";
				yaReclamo=true;
			}
			else {
				peticion+=", idOferta = ?";
			}
			
		}
		
		if (o.getNombre()!=null) {
			noNulos[1]=1;
			if (!yaReclamo) {
				peticion+=" SET nombre = ?";
				yaReclamo=true;
			}
			else {
				peticion+=", nombre = ?";
			}
			
		}
		
		if (o.getDescripcion()!=null) {
			noNulos[2]=1;
			if (!yaReclamo) {
				peticion+=" SET descripcion = ?";
				yaReclamo=true;
			}
			else {
				peticion+=", descripcion = ?";
			}
			
		}
		
		if (o.getFechaLimReserva()!=null) {
			noNulos[3]=1;
			if (!yaReclamo) {
				peticion+=" SET fechaLimReserva = ?";
				yaReclamo=true;
			}
			else {
				peticion+=", fechaLimReserva = ?";
			}
			
		}
		
		if (o.getFechaLimReclamacion()!=null) {
			noNulos[4]=1;
			if (!yaReclamo) {
				peticion+=" SET fechaLimReclamacion";
				yaReclamo=true;
			}
			else {
				peticion+=", fechaLimReclamacion = ?";
			}
			
		}
		
		if (o.getPrecioReal()>=0) {
			noNulos[5]=1;
			if (!yaReclamo) {
				peticion+=" SET precioReal = ?";
				yaReclamo=true;
			}
			else {
				peticion+=", precioReal = ?";
			}
			
		}
		
		if (o.getPrecioDescontado()>=0) {
			noNulos[6]=1;
			if (!yaReclamo) {
				peticion+=" SET precioDescontado = ?";
				yaReclamo=true;
			}
			peticion+=", precioDescontado = ?";
			
		}
		
		if (o.getComisionVenta()>=0) {
			noNulos[7]=1;
			if (!yaReclamo) {
				peticion+=" SET comisionVenta = ?";
				yaReclamo=true;
			}
			peticion+=", comisionVenta = ?";
		}
		
		if (!yaReclamo) {
			peticion+=" SET invalida = ?";
			yaReclamo=true;
		}
		else {
			peticion+=", invalida = ?";
		} 
		
		peticion += " WHERE idOferta = ?";

		
		try (PreparedStatement preparada = c.prepareStatement(peticion) ){
			
			int i = 1;
			if (noNulos[0]==1) {
				preparada.setLong(i++, o.getIdOferta());
			}
			
			if (noNulos[1]==1) {
				preparada.setString(i++,o.getNombre());
			}
			
			if (noNulos[2]==1) {
				preparada.setString(i++,o.getDescripcion());
			}
			
			if (noNulos[3]==1) {
				Timestamp fechaLimReserva= o.getFechaLimReserva() != null ? new Timestamp(
						o.getFechaLimReserva().getTimeInMillis()) : null;
				preparada.setTimestamp(i++, fechaLimReserva);
			}
			
			if (noNulos[4]==1) {
				Timestamp fechaLimReclamacion= o.getFechaLimReclamacion() != null ? new Timestamp(
						o.getFechaLimReclamacion().getTimeInMillis()) : null;
				preparada.setTimestamp(i++, fechaLimReclamacion);
			}
			
			if (noNulos[5]==1) {
				
				preparada.setFloat(i++, o.getPrecioReal());
			}
			if (noNulos[6]==1) {
				preparada.setFloat(i++, o.getPrecioDescontado());
			}
			
			if (noNulos[7]==1){
				preparada.setFloat(i++, o.getComisionVenta());
			}

			preparada.setBoolean(i++, o.isInvalida());
			preparada.setLong(i++, o.getIdOferta());

			int filas_afectadas = preparada.executeUpdate();
			
			if (filas_afectadas==0) {
				throw new InstanceNotFoundException(o.getIdOferta(), Oferta.class.getName());
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		
	}
	
}
