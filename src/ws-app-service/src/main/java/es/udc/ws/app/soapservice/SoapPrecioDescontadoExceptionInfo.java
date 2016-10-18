package es.udc.ws.app.soapservice;

public class SoapPrecioDescontadoExceptionInfo {

	private float precioDescontado;

	public SoapPrecioDescontadoExceptionInfo(float precioDescontado) {
		this.precioDescontado = precioDescontado;
	}

	public float getPrecioDescontado() {
		return this.precioDescontado;
	}

	public void setPrecioDescontado(float precioDescontado) {
		this.precioDescontado = precioDescontado;
	}
}
