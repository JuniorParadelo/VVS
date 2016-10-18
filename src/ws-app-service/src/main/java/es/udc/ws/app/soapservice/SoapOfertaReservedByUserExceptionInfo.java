package es.udc.ws.app.soapservice;

public class SoapOfertaReservedByUserExceptionInfo {

	private Long ofertaId;
	private String email;

    public SoapOfertaReservedByUserExceptionInfo() {
    }
    
    public SoapOfertaReservedByUserExceptionInfo(Long ofertaId, String email) {
        this.ofertaId = ofertaId;
        this.email = email;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public String getOfertaEmail() {
        return email;
    }
}
