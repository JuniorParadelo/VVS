package es.udc.ws.app.soapservice;

public class SoapReservaNoValidaExceptionInfo {
	private Long reservaId;

	public SoapReservaNoValidaExceptionInfo(Long reservaId) {

		this.reservaId = reservaId;
	}

	public Long getReservaId() {
		return reservaId;
	}
}
