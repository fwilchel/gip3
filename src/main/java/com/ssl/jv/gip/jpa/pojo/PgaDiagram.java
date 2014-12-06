package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pga_diagrams database table.
 * 
 */
@Entity
@Table(name="pga_diagrams")
@NamedQuery(name="PgaDiagram.findAll", query="SELECT p FROM PgaDiagram p")
public class PgaDiagram implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String diagramname;

	private String diagramlinks;

	private String diagramtables;

	public PgaDiagram() {
	}

	public String getDiagramname() {
		return this.diagramname;
	}

	public void setDiagramname(String diagramname) {
		this.diagramname = diagramname;
	}

	public String getDiagramlinks() {
		return this.diagramlinks;
	}

	public void setDiagramlinks(String diagramlinks) {
		this.diagramlinks = diagramlinks;
	}

	public String getDiagramtables() {
		return this.diagramtables;
	}

	public void setDiagramtables(String diagramtables) {
		this.diagramtables = diagramtables;
	}

}