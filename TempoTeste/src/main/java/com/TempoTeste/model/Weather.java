package com.TempoTeste.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Modelo de dado , que sera usado pelo jpa
 * o mesmo ira criar a tabela com o nome weather_html5
 * foi inserido uma constante no campo data para que o mesmo não seja dupicado por engano
 */

@Entity
@XmlRootElement
@Table(name = "weather_html5", uniqueConstraints = @UniqueConstraint(columnNames = "data"))
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;
    /*<CurrentWeather>
    <Location>Florianopolis Aeroporto , Brazil (SBFL) 27-40S 048-33W 5M</Location>  - não preciso disso
    <Time>Sep 19, 2015 - 11:00 AM EDT / 2015.09.19 1500 UTC</Time>
    <Wind> from the SE (130 degrees) at 9 MPH (8 KT):0</Wind>
    <Visibility> greater than 7 mile(s):0</Visibility>
    <SkyConditions> partly cloudy</SkyConditions>
    <Temperature> 75 F (24 C)</Temperature>
    <DewPoint> 68 F (20 C)</DewPoint>
    <RelativeHumidity> 78%</RelativeHumidity>
    <Pressure> 29.97 in. Hg (1015 hPa)</Pressure>
    <Status>Success</Status>  - não preciso disso
  </CurrentWeather>*/
 
    @Id
    @GeneratedValue
    private Long id;

    private Date data;
    
	private String vento;
    
    private String visibilidade;
    
    private String condicaoDoTempo;
    
    private String temperatura;
    
    private String humidadeRelativa;
    
    private String pontoDeCondensacao;
    
	private String pressaoAtmosferica;
    
	 /* Getters e Setters*/
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getPontoDeCondensacao() {
		return pontoDeCondensacao;
	}

	public void setPontoDeCondensacao(String pontoDeCondensacao) {
		this.pontoDeCondensacao = pontoDeCondensacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getVento() {
		return vento;
	}

	public void setVento(String vento) {
		this.vento = vento;
	}

	public String getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}

	public String getCondicaoDoTempo() {
		return condicaoDoTempo;
	}

	public void setCondicaoDoTempo(String condicaoDoTempo) {
		this.condicaoDoTempo = condicaoDoTempo;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getHumidadeRelativa() {
		return humidadeRelativa;
	}

	public void setHumidadeRelativa(String humidadeRelativa) {
		this.humidadeRelativa = humidadeRelativa;
	}

	public String getPressaoAtmosferica() {
		return pressaoAtmosferica;
	}

	public void setPressaoAtmosferica(String pressaoAtmosferica) {
		this.pressaoAtmosferica = pressaoAtmosferica;
	}
}
