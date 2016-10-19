package es.udc.pojoapp.model.util.OptionState;

public enum Estado {
	Pendiente, Ganada, Perdida;
	
public static Estado toEstado(String Estado) {
		
		if (Estado.equals("Pendiente")) {
			return Pendiente;
		}
		
		if (Estado.equals("Ganada")) {
			return Ganada;
		}
		
		if (Estado.equals("Perdida")) {
			return Perdida;
		}
		
		else return null;
	}
}
