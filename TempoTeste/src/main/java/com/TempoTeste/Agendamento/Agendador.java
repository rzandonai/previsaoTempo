package com.TempoTeste.Agendamento;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@Startup
@Singleton
public class Agendador {
	
@PersistenceUnit
private EntityManagerFactory entityManagerFactory;


/* Neste ponto e feito o agendamento para a atualização dos dados sobre a previsão do tempo
 * o mesmo ira retornar esse job a cada 1 hora
 */
	@PostConstruct
	void init() {

		try {
			Scheduler sched = new StdSchedulerFactory().getScheduler();
			JobDetail atualizar = JobBuilder.newJob(AtualizarPrevisao.class).withIdentity("1", "1").build();
			 /*Tratativa do quartz 
		     * ele não consegue  usar a persistencia quando esta sendo rodado pelo quartz então eu chamo ela fora do job 
		     */
			atualizar.getJobDataMap().put("entityManagerFactory", entityManagerFactory);
			
			SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("1", "1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever()).build();
			sched.scheduleJob(atualizar, trigger);
			sched.start();
		} catch (Throwable t) {
			//like a ninja
		}
	}

}
