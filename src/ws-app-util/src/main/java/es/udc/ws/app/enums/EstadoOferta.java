package es.udc.ws.app.enums;

public enum EstadoOferta {
	INVALIDA(0), VALIDA(1), ERRONEO(13);
	
	private final int value;
	
	EstadoOferta(int val) {
		this.value = val;
	}
	
	public int getValue() {
		return this.value;
	}
}
