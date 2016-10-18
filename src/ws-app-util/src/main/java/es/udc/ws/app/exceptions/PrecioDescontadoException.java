package es.udc.ws.app.exceptions;

@SuppressWarnings("serial")
public class PrecioDescontadoException extends Exception {

    private float precioDescontado;

    public PrecioDescontadoException(float precioDescontado) {
        super("No se permite aumentar el precio descontado.");
        this.precioDescontado = precioDescontado;
    }

    public float getPrecioDescontado() {
    	return this.precioDescontado;
    }
    
    public void setPrecioDescontado(float precioDescontado) {
    	this.precioDescontado = precioDescontado;
    }
}