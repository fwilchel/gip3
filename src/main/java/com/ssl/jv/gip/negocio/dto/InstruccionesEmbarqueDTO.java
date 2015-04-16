package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.List;

import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;

/**
 * <p>
 * Title: InstruccionesEmbarqueDTO</p>
 *
 * <p>
 * Description: DTO para almacenar la informacion de una instruccion de embarque</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
public class InstruccionesEmbarqueDTO implements Serializable {

  private static final long serialVersionUID = 5772395465483898864L;

  private String clientName;
  private String clientAddress;
  private String clientPhone;
  private String clientContact;
  private String city;
  private String country;
  private List<DocTerminosTransporteDTO> documentos;
  private TerminosTransporte terminosTransporte;

  public InstruccionesEmbarqueDTO() {
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public String getClientAddress() {
    return clientAddress;
  }

  public void setClientAddress(String clientAddress) {
    this.clientAddress = clientAddress;
  }

  public String getClientPhone() {
    return clientPhone;
  }

  public void setClientPhone(String clientPhone) {
    this.clientPhone = clientPhone;
  }

  public String getClientContact() {
    return clientContact;
  }

  public void setClientContact(String clientContact) {
    this.clientContact = clientContact;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public TerminosTransporte getTerminosTransporte() {
    return terminosTransporte;
  }

  public void setTerminosTransporte(TerminosTransporte terminosTransporte) {
    this.terminosTransporte = terminosTransporte;
  }

  public List<DocTerminosTransporteDTO> getDocumentos() {
    return documentos;
  }

  public void setDocumentos(List<DocTerminosTransporteDTO> documentos) {
    this.documentos = documentos;
  }

  @Override
  public String toString() {
    return "InstruccionesEmbarqueDTO [clientName=" + clientName
        + ", clientAddress=" + clientAddress + ", clientPhone="
        + clientPhone + ", clientContact=" + clientContact + ", city="
        + city + ", country=" + country + ", documentos=" + documentos
        + ", terminosTransporte=" + terminosTransporte + "]";
  }

}
