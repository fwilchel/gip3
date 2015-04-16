package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the pga_forms database table.
 *
 */
@Entity
@Table(name = "pga_forms")
@NamedQuery(name = "PgaForm.findAll", query = "SELECT p FROM PgaForm p")
public class PgaForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String formname;

  private String formsource;

  public PgaForm() {
  }

  public String getFormname() {
    return this.formname;
  }

  public void setFormname(String formname) {
    this.formname = formname;
  }

  public String getFormsource() {
    return this.formsource;
  }

  public void setFormsource(String formsource) {
    this.formsource = formsource;
  }

}
