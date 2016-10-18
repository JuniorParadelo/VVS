package es.udc.ws.app.soapservice;

import java.util.Calendar;

public class SoapOfertaExpirationExceptionInfo {

	private Long ofertaId;
	private Calendar fechaReservar;

	public SoapOfertaExpirationExceptionInfo() {
	}

	public SoapOfertaExpirationExceptionInfo(Long ofertaId, Calendar fechaReservar) {
		this.ofertaId = ofertaId;
		this.fechaReservar = fechaReservar;
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public Calendar getFechaReservar() {
		return fechaReservar;
	}
}
