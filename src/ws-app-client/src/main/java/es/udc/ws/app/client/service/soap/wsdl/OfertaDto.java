
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
 *         &lt;element name="estado" type="{http://soap.ws.udc.es/}estadoOferta" minOccurs="0"/>
 *         &lt;element name="fechaReclamar" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaReservar" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ofertaId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "estado",
    "fechaReclamar",
    "fechaReservar",
    "nombre",
    "ofertaId",
    "precioDescontado",
    "precioReal"
})
public class OfertaDto {

    protected String descripcion;
    @XmlSchemaType(name = "string")
    protected EstadoOferta estado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReclamar;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaReservar;
    protected String nombre;
    protected Long ofertaId;
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
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoOferta }
     *     
     */
    public EstadoOferta getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoOferta }
     *     
     */
    public void setEstado(EstadoOferta value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReclamar.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaReclamar() {
        return fechaReclamar;
    }

    /**
     * Define el valor de la propiedad fechaReclamar.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaReclamar(XMLGregorianCalendar value) {
        this.fechaReclamar = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaReservar.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaReservar() {
        return fechaReservar;
    }

    /**
     * Define el valor de la propiedad fechaReservar.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaReservar(XMLGregorianCalendar value) {
        this.fechaReservar = value;
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
