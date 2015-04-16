package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the pga_queries database table.
 *
 */
@Entity
@Table(name = "pga_queries")
@NamedQuery(name = "PgaQuery.findAll", query = "SELECT p FROM PgaQuery p")
public class PgaQuery implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String queryname;

  private String querycommand;

  private String querycomments;

  private String querylinks;

  private String queryresults;

  private String querytables;

  private String querytype;

  public PgaQuery() {
  }

  public String getQueryname() {
    return this.queryname;
  }

  public void setQueryname(String queryname) {
    this.queryname = queryname;
  }

  public String getQuerycommand() {
    return this.querycommand;
  }

  public void setQuerycommand(String querycommand) {
    this.querycommand = querycommand;
  }

  public String getQuerycomments() {
    return this.querycomments;
  }

  public void setQuerycomments(String querycomments) {
    this.querycomments = querycomments;
  }

  public String getQuerylinks() {
    return this.querylinks;
  }

  public void setQuerylinks(String querylinks) {
    this.querylinks = querylinks;
  }

  public String getQueryresults() {
    return this.queryresults;
  }

  public void setQueryresults(String queryresults) {
    this.queryresults = queryresults;
  }

  public String getQuerytables() {
    return this.querytables;
  }

  public void setQuerytables(String querytables) {
    this.querytables = querytables;
  }

  public String getQuerytype() {
    return this.querytype;
  }

  public void setQuerytype(String querytype) {
    this.querytype = querytype;
  }

}
