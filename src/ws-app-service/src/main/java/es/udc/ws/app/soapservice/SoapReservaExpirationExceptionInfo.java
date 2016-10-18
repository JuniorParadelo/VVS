package es.udc.ws.app.soapservice;

import java.util.Calendar;

public class SoapReservaExpirationExceptionInfo {

	private Long reservaId;
    private Calendar fecha_limite;

    public SoapReservaExpirationExceptionInfo(Long reservaId, Calendar fecha_limite) {
        this.reservaId = reservaId;
        this.fecha_limite = fecha_limite;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public Calendar getFechaLimite() {
        return fecha_limite;
    }

    public void setFechaLimite(Calendar fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }
}
