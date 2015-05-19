package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the facts_currency_conversion database table.
 *
 */
@Entity
@Table(name = "facts_currency_conversion")
@NamedQueries({
  @NamedQuery(name = "FactsCurrencyConversion.findAll", query = "SELECT f FROM FactsCurrencyConversion f"),
  @NamedQuery(name = "FactsCurrencyConversion.findByFecha", query = "SELECT f FROM FactsCurrencyConversion f WHERE f.type=2 AND f.pgCatalog<= :fecha ORDER BY f.pgCatalog DESC")
})

public class FactsCurrencyConversion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @Column(name = "destination_source_exchange_rate")
  private float destinationSourceExchangeRate;

  @Column(name = "id_destination_currency")
  private String idDestinationCurrency;

  @Column(name = "id_source_currency")
  private String idSourceCurrency;

  @Column(name = "pg_catalog")
  private Timestamp pgCatalog;

  @Column(name = "source_destination_exchange_rate")
  private float sourceDestinationExchangeRate;

  private Integer type;

  public FactsCurrencyConversion() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public float getDestinationSourceExchangeRate() {
    return this.destinationSourceExchangeRate;
  }

  public void setDestinationSourceExchangeRate(float destinationSourceExchangeRate) {
    this.destinationSourceExchangeRate = destinationSourceExchangeRate;
  }

  public String getIdDestinationCurrency() {
    return this.idDestinationCurrency;
  }

  public void setIdDestinationCurrency(String idDestinationCurrency) {
    this.idDestinationCurrency = idDestinationCurrency;
  }

  public String getIdSourceCurrency() {
    return this.idSourceCurrency;
  }

  public void setIdSourceCurrency(String idSourceCurrency) {
    this.idSourceCurrency = idSourceCurrency;
  }

  public Timestamp getPgCatalog() {
    return this.pgCatalog;
  }

  public void setPgCatalog(Timestamp pgCatalog) {
    this.pgCatalog = pgCatalog;
  }

  public float getSourceDestinationExchangeRate() {
    return this.sourceDestinationExchangeRate;
  }

  public void setSourceDestinationExchangeRate(float sourceDestinationExchangeRate) {
    this.sourceDestinationExchangeRate = sourceDestinationExchangeRate;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

}
