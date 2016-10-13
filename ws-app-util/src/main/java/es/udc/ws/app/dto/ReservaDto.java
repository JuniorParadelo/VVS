package es.udc.ws.app.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.app.reserva.EstadoReserva;



public class ReservaDto {
	
	private Long codigo;
	private String email;
	private String tarjeta;
	private Long idOferta;
	private Calendar fechaReserva;
	private EstadoReserva estado;
	private float precioReserva;
	
	
	public ReservaDto (String email,String tarjeta,Long idOferta,
			EstadoReserva estado,Calendar fechaReserva,float precioReserva) {
		this.email=email;
		this.tarjeta=tarjeta;
		this.idOferta=idOferta;
		this.estado=estado;
		this.fechaReserva=fechaReserva;
		if (fechaReserva != null) {
			this.fechaReserva.set(Calendar.MILLISECOND,0);
		}
		this.precioReserva = precioReserva;
	}
	
	public ReservaDto (Long codigo,String email,String tarjeta,Long idOferta,
			EstadoReserva estado,Calendar fechaReserva,float precioReserva) {
		this(email,tarjeta,idOferta,estado,fechaReserva,precioReserva);
		this.codigo=codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public Long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Long idOferta) {
		this.idOferta = idOferta;
	}

	public Calendar getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Calendar fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public float getPrecioReserva() {
		return precioReserva;
	}

	public void setPrecioReserva(float precioReserva) {
		this.precioReserva = precioReserva;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((fechaReserva == null) ? 0 : fechaReserva.hashCode());
		result = prime * result
				+ ((idOferta == null) ? 0 : idOferta.hashCode());
		result = prime * result + Float.floatToIntBits(precioReserva);
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
		ReservaDto other = (ReservaDto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaReserva == null) {
			if (other.fechaReserva != null)
				return false;
		} else if (!fechaReserva.equals(other.fechaReserva))
			return false;
		if (idOferta == null) {
			if (other.idOferta != null)
				return false;
		} else if (!idOferta.equals(other.idOferta))
			return false;
		if (Float.floatToIntBits(precioReserva) != Float
				.floatToIntBits(other.precioReserva))
			return false;
		if (tarjeta == null) {
			if (other.tarjeta != null)
				return false;
		} else if (!tarjeta.equals(other.tarjeta))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		
		System.out.println("-------------- ReservaDto ------------------");
		System.out.println("Codigo : "+codigo);
		System.out.println("Id Oferta : "+idOferta);
		System.out.println("Usuario : "+email);
		System.out.println("Tarjeta : "+tarjeta);
		System.out.println("Fecha de la Reserva : "+formato.format(fechaReserva.getTime()) );
		System.out.println("Estado de la Reserva : "+estado);
		System.out.println("Precio : "+precioReserva);
		System.out.println("---------------------------------------------");
		
		
		return "D:<";
	}
	
	
}


