package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the reqxproducto database table.
 * 
 */
@Entity
@NamedQuery(name="Reqxproducto.findAll", query="SELECT r FROM Reqxproducto r")
public class Reqxproducto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5024941745608481367L;





	@Id
	@SequenceGenerator(name="REQXPRODUCTO_ID_GENERATOR", sequenceName="reqxproducto_id_seq" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REQXPRODUCTO_ID_GENERATOR")
	private Integer id;
	
	
	private Long documento;

	private Boolean mcajamaster;

	private Boolean mpallet;

	private Boolean mproducto;

	private String observaciones;

	private Long producto;

	private Boolean tienemarcacion;

	@Column(name="requerimiento")
	private Long idrequerimiento;
	
	
	
	
	//bi-directional many-to-one association to ComextRequerimientoexportacion
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="requerimiento")
	//private ComextRequerimientoexportacion comextRequerimientoexportacion;

	public Reqxproducto() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDocumento() {
		return this.documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public Boolean getMcajamaster() {
		return this.mcajamaster;
	}

	public void setMcajamaster(Boolean mcajamaster) {
		this.mcajamaster = mcajamaster;
	}

	public Boolean getMpallet() {
		return this.mpallet;
	}

	public void setMpallet(Boolean mpallet) {
		this.mpallet = mpallet;
	}

	public Boolean getMproducto() {
		return this.mproducto;
	}

	public void setMproducto(Boolean mproducto) {
		this.mproducto = mproducto;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Long getProducto() {
		return this.producto;
	}

	public void setProducto(Long producto) {
		this.producto = producto;
	}

	public Boolean getTienemarcacion() {
		return this.tienemarcacion;
	}

	public void setTienemarcacion(Boolean tienemarcacion) {
		this.tienemarcacion = tienemarcacion;
	}

	/*public ComextRequerimientoexportacion getComextRequerimientoexportacion() {
		return this.comextRequerimientoexportacion;
	}

	public void setComextRequerimientoexportacion(ComextRequerimientoexportacion comextRequerimientoexportacion) {
		this.comextRequerimientoexportacion = comextRequerimientoexportacion;
	}*/

	/**
	 * @return the idrequerimiento
	 */
	public Long getIdrequerimiento() {
		return idrequerimiento;
	}

	/**
	 * @param idrequerimiento the idrequerimiento to set
	 */
	public void setIdrequerimiento(Long idrequerimiento) {
		this.idrequerimiento = idrequerimiento;
	}

}