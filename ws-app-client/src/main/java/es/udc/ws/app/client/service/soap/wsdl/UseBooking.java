
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para useBooking complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="useBooking">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "useBooking", propOrder = {
    "bookingId"
})
public class UseBooking {

    protected Long bookingId;

    /**
     * Obtiene el valor de la propiedad bookingId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBookingId() {
        return bookingId;
    }

    /**
     * Define el valor de la propiedad bookingId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBookingId(Long value) {
        this.bookingId = value;
    }

}
