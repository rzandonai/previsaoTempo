package com.TempoTeste.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement(name="NewDataSet")
public class NewDataSet {
	@XmlElement(name="Table")
	private List<Table> table;
	private NewDataSet NewDataSet;

	public NewDataSet getNewDataSet() {
		return NewDataSet;
	}

	public void setNewDataSet(NewDataSet newDataSet) {
		this.NewDataSet = newDataSet;
	}

	public List<Table> getTable() {
		return table;
	}

}