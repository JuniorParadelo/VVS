
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para ofertaDto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ofertaDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaLimReclamacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaLimReserva" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idOferta" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="invalida" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="precioDescontado" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="precioReal" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ofertaDto", propOrder = {
    "descripcion",
    "fechaLimReclamacion",
    "fechaLimReserva",
    "idOferta",
    "invalida",
    "nombre",
    "precioDescontado",
    "precioReal"
})
public class OfertaDto {

    protected String descripcion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimReclamacion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaLimReserva;
    protected Long idOferta;
    protected boolean invalida;
    protected String nombre;
    protected float precioDescontado;
    protected float precioReal;

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaLimReclamacion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimReclamacion() {
        return fechaLimReclamacion;
    }

    /**
     * Define el valor de la propiedad fechaLimReclamacion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimReclamacion(XMLGregorianCalendar value) {
        this.fechaLimReclamacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaLimReserva.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaLimReserva() {
        return fechaLimReserva;
    }

    /**
     * Define el valor de la propiedad fechaLimReserva.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaLimReserva(XMLGregorianCalendar value) {
        this.fechaLimReserva = value;
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
     * Obtiene el valor de la propiedad invalida.
     * 
     */
    public boolean isInvalida() {
        return invalida;
    }

    /**
     * Define el valor de la propiedad invalida.
     * 
     */
    public void setInvalida(boolean value) {
        this.invalida = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

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

    /**
     * Obtiene el valor de la propiedad precioReal.
     * 
     */
    public float getPrecioReal() {
        return precioReal;
    }

    /**
     * Define el valor de la propiedad precioReal.
     * 
     */
    public void setPrecioReal(float value) {
        this.precioReal = value;
    }

}
