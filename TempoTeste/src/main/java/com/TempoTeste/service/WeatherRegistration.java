package com.TempoTeste.service;

import java.text.SimpleDateFormat;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.TempoTeste.model.Weather;

@Stateless
public class WeatherRegistration {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
    @Inject
    private Event<Weather> WeaterEventSrc;

    public void register(Weather tempo) throws Exception {
    	SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy - HH:mm");    
    	String result = out.format(tempo.getData()); 
        System.out.println("Registrando a previsão do tempo da data: " + result);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(tempo);
        WeaterEventSrc.fire(tempo);
    }
    
    
    public void update(Weather tempo) throws Exception {
    	SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy HH");    
    	String result = out.format(tempo.getData()); 
    	System.out.println("Registrando a previsão do tempo da data: " + result);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.merge(tempo);
        WeaterEventSrc.fire(tempo);
    }
}
