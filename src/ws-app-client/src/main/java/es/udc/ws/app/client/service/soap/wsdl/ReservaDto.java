
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
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://soap.ws.udc.es/}estadoReserva" minOccurs="0"/>
 *         &lt;element name="fechaDeReserva" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ofertaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="precio" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="reservaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "fechaDeReserva",
    "ofertaId",
    "precio",
    "reservaId",
    "tarjeta"
})
public class ReservaDto {

    protected int codigo;
    protected String email;
    @XmlSchemaType(name = "string")
    protected EstadoReserva estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaDeReserva;
    protected Long ofertaId;
    protected float precio;
    protected Long reservaId;
    protected String tarjeta;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     */
    public void setCodigo(int value) {
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
     * Obtiene el valor de la propiedad fechaDeReserva.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaDeReserva() {
        return fechaDeReserva;
    }

    /**
     * Define el valor de la propiedad fechaDeReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaDeReserva(XMLGregorianCalendar value) {
        this.fechaDeReserva = value;
    }

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

    /**
     * Obtiene el valor de la propiedad precio.
     * 
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Define el valor de la propiedad precio.
     * 
     */
    public void setPrecio(float value) {
        this.precio = value;
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
