package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pga_reports database table.
 * 
 */
@Entity
@Table(name="pga_reports")
@NamedQuery(name="PgaReport.findAll", query="SELECT p FROM PgaReport p")
public class PgaReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String reportname;

	private String reportbody;

	private String reportoptions;

	private String reportprocs;

	private String reportsource;

	public PgaReport() {
	}

	public String getReportname() {
		return this.reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getReportbody() {
		return this.reportbody;
	}

	public void setReportbody(String reportbody) {
		this.reportbody = reportbody;
	}

	public String getReportoptions() {
		return this.reportoptions;
	}

	public void setReportoptions(String reportoptions) {
		this.reportoptions = reportoptions;
	}

	public String getReportprocs() {
		return this.reportprocs;
	}

	public void setReportprocs(String reportprocs) {
		this.reportprocs = reportprocs;
	}

	public String getReportsource() {
		return this.reportsource;
	}

	public void setReportsource(String reportsource) {
		this.reportsource = reportsource;
	}

}