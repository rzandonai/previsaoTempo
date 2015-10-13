package com.TempoTeste.Agendamento;

import javax.ejb.Stateless;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.TempoTeste.SOAP.Service.GlobalWeather;
import com.TempoTeste.SOAP.Service.GlobalWeatherSoap;
import com.TempoTeste.data.WeatherRepository;
import com.TempoTeste.model.NewDataSet;
import com.TempoTeste.model.Table;
import com.TempoTeste.model.Weather;

@Stateless
public class AtualizarPrevisao implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("Quartz test job executed!");
	    /*Tratativa do quartz 
	     * ele não consegue  usar a persistencia quando esta sendo rodado pelo quartz então eu chamo ela fora do job 
	     */
		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context
				.getJobDetail().getJobDataMap().get("entityManagerFactory");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		buscaESalvaPrevisao("BRAZIL", "florianopolis", entityManager);
	}

	private static Document createDomDocument(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder;
		InputSource inputSource;
		Document doc;

		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		inputSource = new InputSource();
		inputSource.setCharacterStream(new StringReader(xml));
		doc = builder.parse(inputSource);

		return doc;
	}

	private static String getString(Document doc, String key) {
		String result = null;

		if (doc != null) {
			NodeList nodes = doc.getElementsByTagName(key);
			if (nodes != null && nodes.getLength() > 0) {
				Node node = nodes.item(0);
				if (node != null) {
					result = node.getTextContent();
				}
			}
		}

		return result;
	}

	public void buscaESalvaPrevisao(String pais, String cidade,
			EntityManager em) {

	
		String localidade = buscarCidade(pais, cidade);
		if (localidade == null) {
			return;
		}
		GlobalWeather service = new GlobalWeather();
		GlobalWeatherSoap soap = service.getGlobalWeatherSoap();
		String retorno = soap.getWeather(localidade, pais);
		Weather previsao = null;
		Document doc;
		try {
			doc = createDomDocument(retorno);
			previsao = new Weather();
			String data = getString(doc, "Time");
			data = data.substring(0, data.indexOf("/") - 1);
			SimpleDateFormat formater = new SimpleDateFormat(
					"MMM dd, yyyy - kk:mm aa zzz", Locale.ENGLISH);
			previsao.setData(formater.parse(data));
			previsao.setVento(getString(doc, "Wind"));
			previsao.setVisibilidade(getString(doc, "Visibility"));
			previsao.setCondicaoDoTempo(getString(doc, "SkyConditions"));
			previsao.setTemperatura(getString(doc, "Temperature"));
			previsao.setPontoDeCondensacao(getString(doc, "DewPoint"));
			previsao.setHumidadeRelativa(getString(doc, "RelativeHumidity"));
			previsao.setPressaoAtmosferica(getString(doc, "Pressure"));
			WeatherRepository wr = new WeatherRepository();
			try {
				previsao.setId(wr.findByData(previsao.getData(),em).getId());
			} catch (Exception e) {
				/* não teve resultado ou teve problema em buscar*/
			}
			em.getTransaction().begin();
			em.merge(previsao);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

	public static String buscarCidade(String pais, String nome) {
		GlobalWeather service = new GlobalWeather();
		GlobalWeatherSoap soap = service.getGlobalWeatherSoap();
		String retorno = soap.getCitiesByCountry(pais);

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(NewDataSet.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(retorno);
			NewDataSet dataSet = (NewDataSet) unmarshaller.unmarshal(reader);
			List<Table> tabela = dataSet.getTable();

			for (Table table : tabela) {
				if (table.getCity().toLowerCase().contains(nome)) {
					return table.getCity();
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
