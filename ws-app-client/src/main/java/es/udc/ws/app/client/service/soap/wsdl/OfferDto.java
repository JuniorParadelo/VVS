
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para offerDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="offerDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="discountPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="enjoymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="offerId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="realPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offerDto", propOrder = {
    "bookingDate",
    "description",
    "discountPrice",
    "enjoymentDate",
    "name",
    "offerId",
    "realPrice"
})
public class OfferDto {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bookingDate;
    protected String description;
    protected float discountPrice;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enjoymentDate;
    protected String name;
    protected Long offerId;
    protected float realPrice;

    /**
     * Obtiene el valor de la propiedad bookingDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBookingDate() {
        return bookingDate;
    }

    /**
     * Define el valor de la propiedad bookingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBookingDate(XMLGregorianCalendar value) {
        this.bookingDate = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad discountPrice.
     * 
     */
    public float getDiscountPrice() {
        return discountPrice;
    }

    /**
     * Define el valor de la propiedad discountPrice.
     * 
     */
    public void setDiscountPrice(float value) {
        this.discountPrice = value;
    }

    /**
     * Obtiene el valor de la propiedad enjoymentDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnjoymentDate() {
        return enjoymentDate;
    }

    /**
     * Define el valor de la propiedad enjoymentDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnjoymentDate(XMLGregorianCalendar value) {
        this.enjoymentDate = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad offerId.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * Define el valor de la propiedad offerId.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOfferId(Long value) {
        this.offerId = value;
    }

    /**
     * Obtiene el valor de la propiedad realPrice.
     * 
     */
    public float getRealPrice() {
        return realPrice;
    }

    /**
     * Define el valor de la propiedad realPrice.
     * 
     */
    public void setRealPrice(float value) {
        this.realPrice = value;
    }

}
