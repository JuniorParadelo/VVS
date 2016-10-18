
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para buscarOferta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="buscarOferta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ofertaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarOferta", propOrder = {
    "ofertaId"
})
public class BuscarOferta {

    protected Long ofertaId;

    /**
     * Obtiene el valor de la propiedad ofertaId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOfertaId() {
        return ofertaId;
    }

    /**
     * Define el valor de la propiedad ofertaId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOfertaId(Long value) {
        this.ofertaId = value;
    }

}
