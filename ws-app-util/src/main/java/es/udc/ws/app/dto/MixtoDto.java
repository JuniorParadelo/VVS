package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MixtoDto {

	private String descripcion;
	private float precioDescontado;
	private Calendar fechaReserva;
	
	
	public MixtoDto(String descr,float precioD, Calendar fecha) {
		
		descripcion=descr;
		if (fecha!=null) {
			fecha.set(Calendar.MILLISECOND,0);
		}
		fechaReserva=fecha;
		precioDescontado=precioD;
		
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public float getPrecioDescontado() {
		return precioDescontado;
	}
	
	public void setPrecioDescontado(float precioDescontado) {
		this.precioDescontado = precioDescontado;
	}
	
	public Calendar getFechaReserva() {
		return fechaReserva;
	}
	
	public void setFechaReserva(Calendar fechaReserva) {
		
		if (fechaReserva!=null) {
			fechaReserva.set(Calendar.MILLISECOND,0);	
		}
		this.fechaReserva = fechaReserva;
	}
	
	public void imprimirMixto() {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd '/' HH:mm:ss:SS");
		
		System.out.println("--------------- Mixto -----------------");
		System.out.println("DESCRIPCION : "+ descripcion);
		System.out.println("PRECIO DESCONTADO : "+precioDescontado);
		System.out.println("FECHA DE RESERVA : "+formato.format(fechaReserva.getTime()));
		System.out.println("---------------------------------------");
	}
	
	
	
}
