package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.app.enums.EstadoReserva;

public class ReservaDto {

	private Long reservaId;
	private Long ofertaId;
	private String email;
	private String tarjeta;
	private Calendar fechaDeReserva;
	private int codigo;
	private float precio;
	private EstadoReserva estado;

	public ReservaDto() {
	}
	
	public ReservaDto (Long reservaId, Long ofertaId, String email, String tarjeta, Calendar fechaDeReserva, int codigo, float precio, EstadoReserva estado) {
		super();
		this.reservaId = reservaId;
		this.ofertaId = ofertaId;
		this.email = email;
		this.tarjeta = tarjeta;
        
		this.fechaDeReserva = fechaDeReserva;
		
        if (fechaDeReserva != null) {
            this.fechaDeReserva.set(Calendar.MILLISECOND, 0);
        }
		
		this.codigo = codigo;
		this.precio = precio;
		this.estado = estado;

	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public Long getReservaId() {
		return reservaId;
	}

	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Calendar getFechaDeReserva() {
		return fechaDeReserva;
	}

	public void setFechaDeReserva(Calendar fechaDeReserva) {
		this.fechaDeReserva = fechaDeReserva;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	
		return "ReservaDto [reservaId=" + reservaId + ", ofertaId=" + ofertaId
				+ ", email=" + email + ", tarjeta=" + tarjeta
				+ ", fechaDeReserva=" + sdf.format(fechaDeReserva.getTime()) + ", codigo=" + codigo
				+ ", precio=" + precio + ", estado=" + estado + "]";
	}


}
