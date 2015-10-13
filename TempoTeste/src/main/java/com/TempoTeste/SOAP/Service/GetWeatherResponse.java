
package com.TempoTeste.SOAP.Service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetWeatherResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getWeatherResult"
})
@XmlRootElement(name = "GetWeatherResponse")
public class GetWeatherResponse {

    @XmlElement(name = "GetWeatherResult")
    protected String getWeatherResult;

    /**
     * Obt�m o valor da propriedade getWeatherResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetWeatherResult() {
        return getWeatherResult;
    }

    /**
     * Define o valor da propriedade getWeatherResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetWeatherResult(String value) {
        this.getWeatherResult = value;
    }

}
