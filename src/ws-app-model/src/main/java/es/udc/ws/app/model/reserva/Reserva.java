package es.udc.ws.app.model.reserva;

import java.util.Calendar;

import es.udc.ws.app.enums.EstadoReserva;



public class Reserva {

	public static final int RESERVA_ANULADA = 0;
	public static final int RESERVA_VALIDA = 1;
	public static final int RESERVA_RECLAMADA = 2;

	private Long reservaId;
	private Long ofertaId;
	private String email;
	private String tarjeta;
	private Calendar fechaDeReserva;
	private EstadoReserva estado; /* 3 estados: 0 -> invalida, 1 -> valida, 2 -> reclamada */
	private int codigo;
	private float precio;

	public Reserva(Long ofertaId, String email, String tarjeta,
			Calendar fechaDeReserva, EstadoReserva estado, int codigo, float precio) {
		super();
		this.ofertaId = ofertaId;
		this.email = email;
		this.tarjeta = tarjeta;

		this.fechaDeReserva = fechaDeReserva;

		if (fechaDeReserva != null) {
			this.fechaDeReserva.set(Calendar.MILLISECOND, 0);
		}

		this.estado = estado;
		this.codigo = codigo;
		this.precio = precio;

	}

	public Reserva(Long reservaId, Long ofertaId, String email, String tarjeta,
			Calendar fechaDeReserva, EstadoReserva estado, int codigo, float precio) {
		super();
		this.reservaId = reservaId;
		this.ofertaId = ofertaId;
		this.email = email;
		this.tarjeta = tarjeta;

		this.fechaDeReserva = fechaDeReserva;

		if (fechaDeReserva != null) {
			this.fechaDeReserva.set(Calendar.MILLISECOND, 0);
		}

		this.estado = estado;
		this.codigo = codigo;
		this.precio = precio;

	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Calendar getFechaDeReserva() {
		return fechaDeReserva;
	}

	public void setFechaDeReserva(Calendar fechaDeReserva) {
		this.fechaDeReserva = fechaDeReserva;

		if (fechaDeReserva != null) {
			this.fechaDeReserva.set(Calendar.MILLISECOND, 0);
		}
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

	public void setReservaId(Long reservaID) {
		this.reservaId = reservaID;
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaID) {
		this.ofertaId = ofertaID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((fechaDeReserva == null) ? 0 : fechaDeReserva.hashCode());
		result = prime * result
				+ ((ofertaId == null) ? 0 : ofertaId.hashCode());
		result = prime * result + Float.floatToIntBits(precio);
		result = prime * result
				+ ((reservaId == null) ? 0 : reservaId.hashCode());
		result = prime * result + ((tarjeta == null) ? 0 : tarjeta.hashCode());
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
		Reserva other = (Reserva) obj;
		if (codigo != other.codigo)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaDeReserva == null) {
			if (other.fechaDeReserva != null)
				return false;
		} else if (!fechaDeReserva.equals(other.fechaDeReserva))
			return false;
		if (ofertaId == null) {
			if (other.ofertaId != null)
				return false;
		} else if (!ofertaId.equals(other.ofertaId))
			return false;
		if (Float.floatToIntBits(precio) != Float.floatToIntBits(other.precio))
			return false;
		if (reservaId == null) {
			if (other.reservaId != null)
				return false;
		} else if (!reservaId.equals(other.reservaId))
			return false;
		if (tarjeta == null) {
			if (other.tarjeta != null)
				return false;
		} else if (!tarjeta.equals(other.tarjeta))
			return false;
		return true;
	}

	
}
