
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anadirOferta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="anadirOferta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ofertaDto" type="{http://soap.ws.udc.es/}ofertaDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "anadirOferta", propOrder = {
    "ofertaDto"
})
public class AnadirOferta {

    protected OfertaDto ofertaDto;

    /**
     * Obtiene el valor de la propiedad ofertaDto.
     * 
     * @return
     *     possible object is
     *     {@link OfertaDto }
     *     
     */
    public OfertaDto getOfertaDto() {
        return ofertaDto;
    }

    /**
     * Define el valor de la propiedad ofertaDto.
     * 
     * @param value
     *     allowed object is
     *     {@link OfertaDto }
     *     
     */
    public void setOfertaDto(OfertaDto value) {
        this.ofertaDto = value;
    }

}
