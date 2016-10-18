package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.app.enums.EstadoOferta;


public class OfertaDto {

	private Long ofertaId;
	private String nombre;
	private String descripcion;
	private Calendar fechaReservar;
	private Calendar fechaReclamar;
	private float precioReal;
	private float precioDescontado;
	private EstadoOferta estado;

    public OfertaDto() {
    }    
    
    public OfertaDto(Long ofertaId, String nombre, String descripcion,
			Calendar fechaReservar, Calendar fechaReclamar, float precioReal,
			float precioDescontado, EstadoOferta estado) {
		super();
		this.ofertaId = ofertaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaReservar = fechaReservar;
		this.fechaReclamar = fechaReclamar;
		this.precioReal = precioReal;
		this.precioDescontado = precioDescontado;
		this.estado = estado;
	}
    
	public EstadoOferta getEstado() {
		return estado;
	}

	public void setEstado(EstadoOferta estado) {
		this.estado = estado;
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
	}

	public Calendar getFechaReclamar() {
		return fechaReclamar;
	}

	public void setFechaReclamar(Calendar fechaReclamar) {
		this.fechaReclamar = fechaReclamar;
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

	@Override
	public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	
		return "OfertaDto [ofertaId=" + ofertaId + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", fechaReservar="
				+ sdf.format(fechaReservar.getTime()) + ", fechaReclamar=" + sdf.format(fechaReclamar.getTime())
				+ ", precioReal=" + precioReal + ", precioDescontado="
				+ precioDescontado + ", estado=" + estado + "]";
	}




}
