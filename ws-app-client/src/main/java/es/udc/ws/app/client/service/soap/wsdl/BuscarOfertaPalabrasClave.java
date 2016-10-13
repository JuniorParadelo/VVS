
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para buscarOfertaPalabrasClave complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="buscarOfertaPalabrasClave">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="palabrasClave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarOfertaPalabrasClave", propOrder = {
    "palabrasClave"
})
public class BuscarOfertaPalabrasClave {

    protected String palabrasClave;

    /**
     * Obtiene el valor de la propiedad palabrasClave.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPalabrasClave() {
        return palabrasClave;
    }

    /**
     * Define el valor de la propiedad palabrasClave.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPalabrasClave(String value) {
        this.palabrasClave = value;
    }

}
