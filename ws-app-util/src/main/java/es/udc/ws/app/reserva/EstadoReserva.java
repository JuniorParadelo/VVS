package es.udc.ws.app.reserva;

public enum EstadoReserva {
	PENDIENTE,ANULADA,RECLAMADA;
	
	public static EstadoReserva toEstado(String arg) {
		if (arg.equals("PENDIENTE")) {
			return PENDIENTE;
		}
		
		if (arg.equals("ANULADA")) {
			return ANULADA;
		}
		
		if (arg.equals("RECLAMADA")) {
			return RECLAMADA;
		}
		
		else return null;
	}
}
