package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the export_fx database table.
 * 
 */
@Entity
@Table(name="export_fx")
@NamedQuery(name="ExportFx.findAll", query="SELECT e FROM ExportFx e")
public class ExportFx implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ExportFxPK pk;

	public ExportFxPK getPk() {
		return pk;
	}

	public void setPk(ExportFxPK pk) {
		this.pk = pk;
	}


}