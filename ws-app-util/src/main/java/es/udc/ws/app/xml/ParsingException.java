package es.udc.ws.app.xml;



@SuppressWarnings("serial")
public class ParsingException extends Exception{

	public ParsingException(String mensaje, Throwable causa) {
		super(mensaje,causa);
	}
	
	public ParsingException(Throwable causa) {
		super(causa);
	}
	
	public ParsingException(String mensaje) {
		super(mensaje);
	}
	
	public ParsingException() {
		
	}
	
}


