package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the categorias_costos_logisticos database table.
 * 
 */
@Entity
@Table(name="categorias_costos_logisticos")
@NamedQuery(name="CategoriaCostoLogistico.findAll", query="SELECT c FROM CategoriaCostoLogistico c")
public class CategoriaCostoLogistico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CATEGORIAS_COSTOS_LOGISTICOS_ID_GENERATOR", sequenceName="CATEGORIAS_COSTOS_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORIAS_COSTOS_LOGISTICOS_ID_GENERATOR")
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to ItemCostoLogistico
	@OneToMany(mappedBy="categoriaCostoLogistico")
	private List<ItemCostoLogistico> itemsCostosLogisticos;
	
	@Column(name="campo_acumula")
	private String campoAcumula; 

	public CategoriaCostoLogistico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ItemCostoLogistico> getItemsCostosLogisticos() {
		return this.itemsCostosLogisticos;
	}

	public void setItemsCostosLogisticos(List<ItemCostoLogistico> itemsCostosLogisticos) {
		this.itemsCostosLogisticos = itemsCostosLogisticos;
	}

	public ItemCostoLogistico addItemCostoLogistico(ItemCostoLogistico itemCostoLogistico) {
		getItemsCostosLogisticos().add(itemCostoLogistico);
		itemCostoLogistico.setCategoriaCostoLogistico(this);

		return itemCostoLogistico;
	}

	public ItemCostoLogistico removeItemCostoLogistico(ItemCostoLogistico itemCostoLogistico) {
		getItemsCostosLogisticos().remove(itemCostoLogistico);
		itemCostoLogistico.setCategoriaCostoLogistico(null);

		return itemCostoLogistico;
	}

	public String getCampoAcumula() {
		return campoAcumula;
	}

	public void setCampoAcumula(String campoAcumula) {
		this.campoAcumula = campoAcumula;
	}

}