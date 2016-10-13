package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OfertaDto {

	
	private Long idOferta;
	private String nombre;
	private String descripcion;
	private Calendar fechaLimReserva;
	private Calendar fechaLimReclamacion;
	private float precioReal;
	private float precioDescontado;
	private boolean invalida;
	
	public OfertaDto(){
					
	}
	
	public OfertaDto(String nombre,String descripcion,Calendar fechaLimRes, Calendar fechaLimRec, 
			float precioReal,float precioDescontado, boolean invalida){

		this.nombre=nombre;
		this.descripcion=descripcion;
		this.fechaLimReclamacion= fechaLimRec;
		if (fechaLimReclamacion!=null) {
			this.fechaLimReclamacion.set(Calendar.MILLISECOND,0);
		}
		this.fechaLimReserva=fechaLimRes;
		if (fechaLimReserva!=null) {
			this.fechaLimReserva.set(Calendar.MILLISECOND,0);
		}
		this.precioReal= precioReal;
		this.precioDescontado= precioDescontado;
		this.invalida= invalida;		
		
		
	}
	public OfertaDto(Long idOferta,String nombre,String descripcion,Calendar fechaLimRes, Calendar fechaLimRec, 
			float precioReal,float precioDescontado, boolean invalida){		
		this(nombre,descripcion,fechaLimRes,fechaLimRec,precioReal,precioDescontado,invalida);
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

	public boolean isInvalida() {
		return invalida;
	}

	public void setInvalida(boolean invalida) {
		this.invalida = invalida;
	}

	@Override
	public String toString() {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		System.out.println("-------------- OfertaDto ------------------");
		System.out.println("ID Oferta : "+idOferta);
		System.out.println("Nombre : "+nombre);
		System.out.println("Descripcion : "+descripcion);
		System.out.println("Fecha Limite Reclamacion : "+formato.format(fechaLimReclamacion.getTime()) );
		System.out.println("Fecha Limite Reserva : "+formato.format(fechaLimReserva.getTime()) );
		System.out.println("Precio Real : "+precioReal);
		System.out.println("Precio Descontado : "+precioDescontado);
		System.out.println("Invalida : "+invalida);
		System.out.println("------------------------------------------");
		
		return ">:D";
		
	}
	
		
}
