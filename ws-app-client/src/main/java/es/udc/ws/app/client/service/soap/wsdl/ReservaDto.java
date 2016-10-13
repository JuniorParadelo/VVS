
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para reservaDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="reservaDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://soap.ws.udc.es/}estadoReserva" minOccurs="0"/>
 *         &lt;element name="fechaReserva" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idOferta" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="precioReserva" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="tarjeta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservaDto", propOrder = {
    "codigo",
    "email",
    "estado",
    "fechaReserva",
    "idOferta",
    "precioReserva",
    "tarjeta"
})
public class ReservaDto {

    protected Long codigo;
    protected String email;
    @XmlSchemaType(name = "string")
    protected EstadoReserva estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReserva;
    protected Long idOferta;
    protected float precioReserva;
    protected String tarjeta;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodigo(Long value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoReserva }
     *     
     */
    public EstadoReserva getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoReserva }
     *     
     */
    public void setEstado(EstadoReserva value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReserva.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Define el valor de la propiedad fechaReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaReserva(XMLGregorianCalendar value) {
        this.fechaReserva = value;
    }

    /**
     * Obtiene el valor de la propiedad idOferta.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdOferta() {
        return idOferta;
    }

    /**
     * Define el valor de la propiedad idOferta.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdOferta(Long value) {
        this.idOferta = value;
    }

    /**
     * Obtiene el valor de la propiedad precioReserva.
     * 
     */
    public float getPrecioReserva() {
        return precioReserva;
    }

    /**
     * Define el valor de la propiedad precioReserva.
     * 
     */
    public void setPrecioReserva(float value) {
        this.precioReserva = value;
    }

    /**
     * Obtiene el valor de la propiedad tarjeta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarjeta() {
        return tarjeta;
    }

    /**
     * Define el valor de la propiedad tarjeta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarjeta(String value) {
        this.tarjeta = value;
    }

}
