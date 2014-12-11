package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ean_micros database table.
 * 
 */
@Entity
@Table(name="ean_micros")
@NamedQuery(name="EanMicro.findAll", query="SELECT e FROM EanMicro e")
public class EanMicro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long barcode;

	private Long micros;

	public EanMicro() {
	}

	public Long getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}

	public Long getMicros() {
		return this.micros;
	}

	public void setMicros(Long micros) {
		this.micros = micros;
	}

}