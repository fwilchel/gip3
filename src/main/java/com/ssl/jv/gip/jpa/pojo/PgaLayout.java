package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pga_layout database table.
 * 
 */
@Entity
@Table(name="pga_layout")
@NamedQuery(name="PgaLayout.findAll", query="SELECT p FROM PgaLayout p")
public class PgaLayout implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String tablename;

	private String colnames;

	private String colwidth;

	private Integer nrcols;

	public PgaLayout() {
	}

	public String getTablename() {
		return this.tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getColnames() {
		return this.colnames;
	}

	public void setColnames(String colnames) {
		this.colnames = colnames;
	}

	public String getColwidth() {
		return this.colwidth;
	}

	public void setColwidth(String colwidth) {
		this.colwidth = colwidth;
	}

	public Integer getNrcols() {
		return this.nrcols;
	}

	public void setNrcols(Integer nrcols) {
		this.nrcols = nrcols;
	}

}