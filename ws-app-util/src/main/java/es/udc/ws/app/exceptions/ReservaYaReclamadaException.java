package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class ReservaYaReclamadaException extends Exception{

	private Long codigo;

    public ReservaYaReclamadaException(Long codigo) {
        super("Reserva con codigo=\"" + codigo + 
              "\" ya ha sido reclamada");
        this.codigo=codigo;
    }

    public Long getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
