package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pga_graphs database table.
 * 
 */
@Entity
@Table(name="pga_graphs")
@NamedQuery(name="PgaGraph.findAll", query="SELECT p FROM PgaGraph p")
public class PgaGraph implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String graphname;

	private String graphcode;

	private String graphsource;

	public PgaGraph() {
	}

	public String getGraphname() {
		return this.graphname;
	}

	public void setGraphname(String graphname) {
		this.graphname = graphname;
	}

	public String getGraphcode() {
		return this.graphcode;
	}

	public void setGraphcode(String graphcode) {
		this.graphcode = graphcode;
	}

	public String getGraphsource() {
		return this.graphsource;
	}

	public void setGraphsource(String graphsource) {
		this.graphsource = graphsource;
	}

}