package es.udc.ws.app.soapservice;

public class SoapOfertaInvalidaExceptionInfo {

	private Long ofertaId;

    public SoapOfertaInvalidaExceptionInfo() {
    }
    
    public SoapOfertaInvalidaExceptionInfo(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }
}
