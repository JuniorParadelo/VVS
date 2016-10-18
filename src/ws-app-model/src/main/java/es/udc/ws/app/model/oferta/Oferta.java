package es.udc.ws.app.model.oferta;

import java.util.Calendar;

import es.udc.ws.app.enums.EstadoOferta;


public class Oferta {

	public static final int OFERTA_INVALIDA = 0;
	public static final int OFERTA_VALIDA = 1;
	
	private Long ofertaId;
	private String nombre;
	private String descripcion;
	private Calendar fechaReservar;
	private Calendar fechaReclamar;
	private float precioReal;
	private float precioDescontado;
	private float comision;
	private EstadoOferta estado;

	public Oferta(String nombre, String descripcion, Calendar fechaReservar, Calendar fechaReclamar, float precioReal, float precioDescontado, float comision, EstadoOferta estado) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaReservar = fechaReservar;
		
        if (fechaReservar != null) {
            this.fechaReservar.set(Calendar.MILLISECOND, 0);
        }
        
		this.fechaReclamar = fechaReclamar;
		
        if (fechaReclamar != null) {
            this.fechaReclamar.set(Calendar.MILLISECOND, 0);
        }
		
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.comision = comision;
		this.estado = estado;
	}

	public Oferta(Long ofertaId, String nombre, String descripcion,
			Calendar fechaReservar, Calendar fechaReclamar, float precioReal, float precioDescontado,
			float comision, EstadoOferta estado) {
		super();
		this.ofertaId = ofertaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaReservar = fechaReservar;
		
        if (fechaReservar != null) {
            this.fechaReservar.set(Calendar.MILLISECOND, 0);
        }
        
		this.fechaReclamar = fechaReclamar;
		
        if (fechaReclamar != null) {
            this.fechaReclamar.set(Calendar.MILLISECOND, 0);
        }
		
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.comision = comision;
		this.estado = estado;
	}
	
	public Oferta(Long ofertaId, String nombre, String descripcion,
			Calendar fechaReservar, Calendar fechaReclamar, float precioReal, float precioDescontado, EstadoOferta estado) {
		super();
		this.ofertaId = ofertaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaReservar = fechaReservar;
		
        if (fechaReservar != null) {
            this.fechaReservar.set(Calendar.MILLISECOND, 0);
        }
        
		this.fechaReclamar = fechaReclamar;
		
        if (fechaReclamar != null) {
            this.fechaReclamar.set(Calendar.MILLISECOND, 0);
        }
		
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.comision = 0;
		this.estado = estado;
	}

	public Calendar getFechaReclamar() {
		return fechaReclamar;
	}

	public void setFechaReclamar(Calendar fechaReclamar) {
		this.fechaReclamar = fechaReclamar;
		
        if (fechaReclamar != null) {
            this.fechaReclamar.set(Calendar.MILLISECOND, 0);
        }
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
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
		this.descripcion = descripcion;
	}

	public Calendar getFechaReservar() {
		return fechaReservar;
	}

	public void setFechaReservar(Calendar fechaReservar) {
		this.fechaReservar = fechaReservar;
        if (fechaReservar != null) {
            this.fechaReservar.set(Calendar.MILLISECOND, 0);
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

	public float getComision() {
		return comision;
	}

	public void setComision(float comision) {
		this.comision = comision;
	}

	public EstadoOferta getEstado() {
		return estado;
	}

	public void setEstado(EstadoOferta estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(comision);
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((fechaReclamar == null) ? 0 : fechaReclamar.hashCode());
		result = prime * result
				+ ((fechaReservar == null) ? 0 : fechaReservar.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((ofertaId == null) ? 0 : ofertaId.hashCode());
		result = prime * result + Float.floatToIntBits(precioDescontado);
		result = prime * result + Float.floatToIntBits(precioReal);
		return result;
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
		if (Float.floatToIntBits(comision) != Float
				.floatToIntBits(other.comision))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estado != other.estado){ 
			return false;
		}
		if (fechaReclamar == null) {
			if (other.fechaReclamar != null)
				return false;
		} else if (!fechaReclamar.equals(other.fechaReclamar)) {
			return false;
		}
		if (fechaReservar == null) {
			if (other.fechaReservar != null)
				return false;
		} else if (!fechaReservar.equals(other.fechaReservar))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (ofertaId == null) {
			if (other.ofertaId != null)
				return false;
		} else if (!ofertaId.equals(other.ofertaId))
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
