package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pga_scripts database table.
 * 
 */
@Entity
@Table(name="pga_scripts")
@NamedQuery(name="PgaScript.findAll", query="SELECT p FROM PgaScript p")
public class PgaScript implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String scriptname;

	private String scriptsource;

	public PgaScript() {
	}

	public String getScriptname() {
		return this.scriptname;
	}

	public void setScriptname(String scriptname) {
		this.scriptname = scriptname;
	}

	public String getScriptsource() {
		return this.scriptsource;
	}

	public void setScriptsource(String scriptsource) {
		this.scriptsource = scriptsource;
	}

}