
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoReserva.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoReserva">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALIDA"/>
 *     &lt;enumeration value="VALIDA"/>
 *     &lt;enumeration value="RECLAMADA"/>
 *     &lt;enumeration value="ERRONEO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "estadoReserva")
@XmlEnum
public enum EstadoReserva {

    INVALIDA,
    VALIDA,
    RECLAMADA,
    ERRONEO;

    public String value() {
        return name();
    }

    public static EstadoReserva fromValue(String v) {
        return valueOf(v);
    }

}
