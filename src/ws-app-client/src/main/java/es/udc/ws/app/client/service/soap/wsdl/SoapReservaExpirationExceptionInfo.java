
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para soapReservaExpirationExceptionInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="soapReservaExpirationExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaLimite" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="reservaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soapReservaExpirationExceptionInfo", propOrder = {
    "fechaLimite",
    "reservaId"
})
public class SoapReservaExpirationExceptionInfo {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimite;
    protected Long reservaId;

    /**
     * Obtiene el valor de la propiedad fechaLimite.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimite() {
        return fechaLimite;
    }

    /**
     * Define el valor de la propiedad fechaLimite.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimite(XMLGregorianCalendar value) {
        this.fechaLimite = value;
    }

    /**
     * Obtiene el valor de la propiedad reservaId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReservaId() {
        return reservaId;
    }

    /**
     * Define el valor de la propiedad reservaId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReservaId(Long value) {
        this.reservaId = value;
    }

}
