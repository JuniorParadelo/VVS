package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class PrecioInvalidoException extends Exception{

	private float precioDescontado;

    public PrecioInvalidoException(float precioDescontado) {
        super("No se puede aumentar el precio descontado [ "+precioDescontado+" ]");
        this.precioDescontado=precioDescontado;
    }

    public float getPrecio() {
        return precioDescontado;
    }
    
    public void setPrecio(float precioDescontado) {
        this.precioDescontado = precioDescontado;
    }
}
