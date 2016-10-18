package es.udc.ws.app.enums;

public enum EstadoReserva {
	INVALIDA(0), VALIDA(1), RECLAMADA(2), ERRONEO(13);
	
	private final int value;
	
	EstadoReserva(int val) {
		this.value = val;
	}
	
	public int getValue() {
		return this.value;
	}
}
