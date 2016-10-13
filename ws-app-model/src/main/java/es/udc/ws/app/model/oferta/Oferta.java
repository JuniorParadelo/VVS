package es.udc.ws.app.model.oferta;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Oferta {

	private Long idOferta;
	private String nombre;
	private String descripcion;
	private Calendar fechaLimReserva;
	private Calendar fechaLimReclamacion;
	private float precioReal;
	private float precioDescontado;
	private float comisionVenta;
	private boolean invalida;
	
	
	public Oferta(String nombre,String descripcion,Calendar fechaLimRes,Calendar fechaLimRec,float precioReal,
			float precioDes ,float comis ,boolean invalida){
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.fechaLimReclamacion=fechaLimRec;
		if (fechaLimReclamacion!=null) {
			this.fechaLimReclamacion.set(Calendar.MILLISECOND,0);
		}
		this.fechaLimReserva=fechaLimRes;
		if (fechaLimReserva!=null) {
			this.fechaLimReserva.set(Calendar.MILLISECOND,0);
		}
		this.precioReal=precioReal;
		this.precioDescontado=precioDes;
		this.comisionVenta=comis;
		this.invalida=invalida;
	}

	public Oferta(Long idOferta,String nombre,String descripcion,Calendar fechaLimRes,Calendar fechaLimRec,float precioReal,
			float precioDes ,float comis ,boolean invalida){
		this(nombre,descripcion,fechaLimRes,fechaLimRec,precioReal,precioDes,comis,invalida);
		this.idOferta=idOferta;
	}

	public Long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Long idOferta) {
		this.idOferta = idOferta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion=descripcion;
	}

	public Calendar getFechaLimReserva() {
		return fechaLimReserva;
	}

	public void setFechaLimReserva(Calendar fechaLimReserva) {
		this.fechaLimReserva = fechaLimReserva;
		if (fechaLimReserva != null) {
			this.fechaLimReserva.set(Calendar.MILLISECOND, 0);
		}
	}

	public Calendar getFechaLimReclamacion() {
		return fechaLimReclamacion;
	}

	public void setFechaLimReclamacion(Calendar fechaLimReclamacion) {
		this.fechaLimReclamacion = fechaLimReclamacion;
		if (fechaLimReclamacion != null) {
			this.fechaLimReclamacion.set(Calendar.MILLISECOND,0);
		}
	}

	public float getPrecioReal() {
		return precioReal;
	}

	public void setPrecioReal(float precioReal) {
		this.precioReal = precioReal;
	}

	public float getPrecioDescontado() {
		return precioDescontado;
	}

	public void setPrecioDescontado(float precioDescontado) {
		this.precioDescontado = precioDescontado;
	}

	public float getComisionVenta() {
		return comisionVenta;
	}

	public void setComisionVenta(float comisionVenta) {
		this.comisionVenta = comisionVenta;
	}

	public boolean isInvalida() {
		return invalida;
	}

	public void setInvalida(boolean invalida) {
		this.invalida = invalida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(comisionVenta);
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime
				* result
				+ ((fechaLimReclamacion == null) ? 0 : fechaLimReclamacion
						.hashCode());
		result = prime * result
				+ ((fechaLimReserva == null) ? 0 : fechaLimReserva.hashCode());
		result = prime * result
				+ ((idOferta == null) ? 0 : idOferta.hashCode());
		result = prime * result + (invalida ? 1231 : 1237);
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + Float.floatToIntBits(precioDescontado);
		result = prime * result + Float.floatToIntBits(precioReal);
		return result;
	}
	

	
	public void imprimirOferta() {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd '/' HH:mm:ss:SS");
		
		System.out.println("ID : "+idOferta);
		System.out.println("NOMBRE : "+nombre);
		System.out.println("DESCR : "+descripcion);
		System.out.println("FECHA RESER : "+formato.format( fechaLimReserva.getTime() ));
		System.out.println("FECHA RECLAM : "+formato.format( fechaLimReclamacion.getTime() ));
		System.out.println("REAL : "+precioReal);
		System.out.println("DESCONTADO : "+precioDescontado);
		System.out.println("COMISION : "+comisionVenta);
		System.out.println("INVALIDA : "+invalida);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (Float.floatToIntBits(comisionVenta) != Float
				.floatToIntBits(other.comisionVenta))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaLimReclamacion == null) {
			if (other.fechaLimReclamacion != null)
				return false;
		} else if (!fechaLimReclamacion.equals(other.fechaLimReclamacion))
			return false;
		if (fechaLimReserva == null) {
			if (other.fechaLimReserva != null)
				return false;
		} else if (!fechaLimReserva.equals(other.fechaLimReserva))
			return false;
		if (idOferta == null) {
			if (other.idOferta != null)
				return false;
		} else if (!idOferta.equals(other.idOferta))
			return false;
		if (invalida != other.invalida)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Float.floatToIntBits(precioDescontado) != Float
				.floatToIntBits(other.precioDescontado))
			return false;
		if (Float.floatToIntBits(precioReal) != Float
				.floatToIntBits(other.precioReal))
			return false;
		return true;
	}
	
	
	
	
}
