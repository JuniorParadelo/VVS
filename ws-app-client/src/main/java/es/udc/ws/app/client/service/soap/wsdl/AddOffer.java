
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para addOffer complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="addOffer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="offerDto" type="{http://soap.ws.udc.es/}offerDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addOffer", propOrder = {
    "offerDto"
})
public class AddOffer {

    protected OfferDto offerDto;

    /**
     * Obtiene el valor de la propiedad offerDto.
     * 
     * @return
     *     possible object is
     *     {@link OfferDto }
     *     
     */
    public OfferDto getOfferDto() {
        return offerDto;
    }

    /**
     * Define el valor de la propiedad offerDto.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferDto }
     *     
     */
    public void setOfferDto(OfferDto value) {
        this.offerDto = value;
    }

}
