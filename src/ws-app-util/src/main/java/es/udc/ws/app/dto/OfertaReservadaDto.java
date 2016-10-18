package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OfertaReservadaDto {

	private String descripcion;
	private float precioDescontado;
	private Calendar fechaDeReserva;

	public OfertaReservadaDto() {
	}
	
	public OfertaReservadaDto(String descripcion, float precioDescontado,
			Calendar fechaDeReserva) {
		super();
		this.descripcion = descripcion;
		this.precioDescontado = precioDescontado;
		this.fechaDeReserva = fechaDeReserva;
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

	public Calendar getFechaDeReserva() {
		return fechaDeReserva;
	}

	public void setFechaDeReserva(Calendar fechaDeReserva) {
		this.fechaDeReserva = fechaDeReserva;
	}

	@Override
	public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	
		return "OfertaReservadaDto [descripcion=" + descripcion
				+ ", precioDescontado=" + precioDescontado
				+ ", fechaDeReserva=" + sdf.format(fechaDeReserva.getTime()) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((fechaDeReserva == null) ? 0 : fechaDeReserva.hashCode());
		result = prime * result + Float.floatToIntBits(precioDescontado);
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
		OfertaReservadaDto other = (OfertaReservadaDto) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaDeReserva == null) {
			if (other.fechaDeReserva != null)
				return false;
		} else if (!fechaDeReserva.equals(other.fechaDeReserva))
			return false;
		if (Float.floatToIntBits(precioDescontado) != Float
				.floatToIntBits(other.precioDescontado))
			return false;
		return true;
	}

}
