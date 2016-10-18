package es.udc.ws.app.soapservice;

public class SoapOfertaReservedExceptionInfo {

	private Long ofertaId;

	public SoapOfertaReservedExceptionInfo(){
		
	}
	
    public SoapOfertaReservedExceptionInfo(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }
}
