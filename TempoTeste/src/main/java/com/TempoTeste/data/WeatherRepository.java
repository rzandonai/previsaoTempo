package com.TempoTeste.data;


import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.TempoTeste.model.Weather;

@ApplicationScoped
public class WeatherRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;


    public Weather findById(Long id) {
    	EntityManager em = entityManagerFactory.createEntityManager();
        return em.find(Weather.class, id);
    }

    /**
     * metodo que fara a pesquisa dos resultados do banco atravez de sua data 
     * @param data = a data na qual deve ser consultado para retorno da condicão do tempo
     */
    public Weather findByData(Date data) {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	 return  findByData(data, em);
    
    }
    
    /*Tratativa do quartz 
     * ele não consegue  usar a persistencia quando esta sendo rodado pelo quartz então eu chamo ela fora do job 
     */
    public Weather findByData(Date data, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weather> criteria = cb.createQuery(Weather.class);
        Root<Weather> tempo = criteria.from(Weather.class);
        criteria.select(tempo).where(cb.equal(tempo.get("data"), data));
        return em.createQuery(criteria).getSingleResult();
    }

    /**
     * metodo que fara a pesquisa dos resultados do banco e retornara todas as condições do tempo 
     * ordenadas por data
     */
    
    public List<Weather> findAllOrderedByData() {
    	EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weather> criteria = cb.createQuery(Weather.class);
        Root<Weather> tempo = criteria.from(Weather.class);
        criteria.select(tempo).orderBy(cb.asc(tempo.get("data")));
        return em.createQuery(criteria).getResultList();
    }


}
