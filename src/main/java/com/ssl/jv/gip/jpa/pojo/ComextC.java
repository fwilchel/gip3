package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the comext_c database table.
 * 
 */
@Entity
@Table(name="comext_c")
@NamedQuery(name="ComextC.findAll", query="SELECT c FROM ComextC c")
public class ComextC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="expires_at")
	private Timestamp expiresAt;

	private Boolean locked;

	private BigDecimal total;

	@Column(name="total_items")
	private Integer totalItems;

	//bi-directional many-to-one association to ComextItemc
	@OneToMany(mappedBy="comextC")
	private List<ComextItemc> comextItemcs;

	public ComextC() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getExpiresAt() {
		return this.expiresAt;
	}

	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getTotalItems() {
		return this.totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	public List<ComextItemc> getComextItemcs() {
		return this.comextItemcs;
	}

	public void setComextItemcs(List<ComextItemc> comextItemcs) {
		this.comextItemcs = comextItemcs;
	}

	public ComextItemc addComextItemc(ComextItemc comextItemc) {
		getComextItemcs().add(comextItemc);
		comextItemc.setComextC(this);

		return comextItemc;
	}

	public ComextItemc removeComextItemc(ComextItemc comextItemc) {
		getComextItemcs().remove(comextItemc);
		comextItemc.setComextC(null);

		return comextItemc;
	}

}