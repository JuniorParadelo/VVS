
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoOferta.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoOferta">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="INVALIDA"/>
 *     &lt;enumeration value="VALIDA"/>
 *     &lt;enumeration value="ERRONEO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "estadoOferta")
@XmlEnum
public enum EstadoOferta {

    INVALIDA,
    VALIDA,
    ERRONEO;

    public String value() {
        return name();
    }

    public static EstadoOferta fromValue(String v) {
        return valueOf(v);
    }

}
