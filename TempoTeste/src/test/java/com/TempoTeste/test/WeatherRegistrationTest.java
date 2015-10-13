/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.TempoTeste.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;

import com.TempoTeste.rest.WeatherService;
import com.TempoTeste.service.WeatherRegistration;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.TempoTeste.data.WeatherRepository;
import com.TempoTeste.model.Weather;



@RunWith(Arquillian.class)
public class WeatherRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(Weather.class, WeatherService.class, WeatherRepository.class, WeatherRegistration.class).addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    WeatherService TempoRegistration;
   
    String data = "Sep 19, 2015 - 11:00 AM EDT / 2015.09.19 1500 UTC";
    String vento ="from the SE (130 degrees) at 9 MPH (8 KT):0";
    String vento2 ="from the SE (130 degrees) at 9 MPH (8 KT):0";
    String visibilidade = "greater than 7 mile(s):0";
    String condicao = "partly cloudy";
    String temperatura = "75 F (24 C)";
    String pontoDeCondensacao = "68 F (20 C)";
    String umidade = "78%";
    String pressao = "29.97 in. Hg (1015 hPa)";
    
    
    @Test
    @InSequence(1)
    public void testRegister() throws Exception {
    	data = data.substring(0, data.indexOf("/")-1);
    	SimpleDateFormat formater =  new SimpleDateFormat("MMM dd, yyyy - kk:mm aa zzz",Locale.ENGLISH);
    	Date hora = formater.parse(data);
    	Weather tempo = createWeatherInstance(hora, vento, visibilidade, condicao, temperatura, umidade, pressao, pontoDeCondensacao);
        Response response = TempoRegistration.addOrUpdateTempo(tempo);

        assertEquals("Unexpected response status", 200, response.getStatus());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @InSequence(2)
    public void testInvalidRegister() throws Exception {

        Weather tempo = createWeatherInstance(null, vento, visibilidade, condicao, temperatura, umidade, pressao, pontoDeCondensacao);
        Response response = TempoRegistration.addOrUpdateTempo(tempo);

        assertEquals("Unexpected response status", 400, response.getStatus());
        assertNotNull("response.getEntity() should not null", response.getEntity());
        assertEquals("Unexpected response.getEntity(). It contains " + response.getEntity(), 1,
                ((Map<String, String>) response.getEntity()).size());
    }

    @SuppressWarnings("unchecked")
    @Test
    @InSequence(3)
    public void testUpdateRegistro() throws Exception {
    	data = data.substring(0, data.indexOf("/"));
    	SimpleDateFormat formater =  new SimpleDateFormat("MMM dd, yyyy - kk:mm a z",Locale.ENGLISH);
    	Date hora = formater.parse(data);
    	Weather tempo = createWeatherInstance(hora, vento, visibilidade, condicao, temperatura, umidade, pressao, pontoDeCondensacao);
        Response response = TempoRegistration.addOrUpdateTempo(tempo);

        // Register a different user with the same email
        Weather tempo2 = createWeatherInstance(hora, vento2, visibilidade, condicao, temperatura, umidade, pressao, pontoDeCondensacao);
        Response response2 = TempoRegistration.addOrUpdateTempo(tempo2);

        assertEquals("Unexpected response status", 200, response2.getStatus());
        assertNotNull("response.getEntity() should not null", response2.getEntity());
        assertEquals("Unexpected response.getEntity(). It contains" + response2.getEntity(), true,
                response.getEntity()!= null);
    }

    private Weather createWeatherInstance(Date data,String vento,String visibilidade,String condicaoDoTempo,String temperatura,String humidadeRelativa,String pressaoAtmosferica,String pontoDeCondensação) {
    	Weather tempo = new Weather();
    	tempo.setData(data);
    	tempo.setVento(vento);
    	tempo.setVisibilidade(visibilidade);
    	tempo.setCondicaoDoTempo(condicaoDoTempo);
    	tempo.setTemperatura(temperatura);
    	tempo.setHumidadeRelativa(humidadeRelativa);
    	tempo.setPressaoAtmosferica(pressaoAtmosferica);
    	tempo.setPontoDeCondensacao(pontoDeCondensação);
        return tempo;
    }
}
