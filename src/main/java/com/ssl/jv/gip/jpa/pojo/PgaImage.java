package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the pga_images database table.
 *
 */
@Entity
@Table(name = "pga_images")
@NamedQuery(name = "PgaImage.findAll", query = "SELECT p FROM PgaImage p")
public class PgaImage implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String imagename;

  private String imagesource;

  public PgaImage() {
  }

  public String getImagename() {
    return this.imagename;
  }

  public void setImagename(String imagename) {
    this.imagename = imagename;
  }

  public String getImagesource() {
    return this.imagesource;
  }

  public void setImagesource(String imagesource) {
    this.imagesource = imagesource;
  }

}
