
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para soapPrecioDescontadoExceptionInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="soapPrecioDescontadoExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="precioDescontado" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soapPrecioDescontadoExceptionInfo", propOrder = {
    "precioDescontado"
})
public class SoapPrecioDescontadoExceptionInfo {

    protected float precioDescontado;

    /**
     * Obtiene el valor de la propiedad precioDescontado.
     * 
     */
    public float getPrecioDescontado() {
        return precioDescontado;
    }

    /**
     * Define el valor de la propiedad precioDescontado.
     * 
     */
    public void setPrecioDescontado(float value) {
        this.precioDescontado = value;
    }

}
