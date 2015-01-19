package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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
	@OneToMany(mappedBy="categoriasCostosLogistico")
	private List<ItemCostoLogistico> itemCostoLogisticos;

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

	public List<ItemCostoLogistico> getItemCostoLogisticos() {
		return this.itemCostoLogisticos;
	}

	public void setItemCostoLogisticos(List<ItemCostoLogistico> itemCostoLogisticos) {
		this.itemCostoLogisticos = itemCostoLogisticos;
	}

	public ItemCostoLogistico addItemCostoLogistico(ItemCostoLogistico itemCostoLogistico) {
		getItemCostoLogisticos().add(itemCostoLogistico);
		itemCostoLogistico.setCategoriasCostosLogistico(this);

		return itemCostoLogistico;
	}

	public ItemCostoLogistico removeItemCostoLogistico(ItemCostoLogistico itemCostoLogistico) {
		getItemCostoLogisticos().remove(itemCostoLogistico);
		itemCostoLogistico.setCategoriasCostosLogistico(null);

		return itemCostoLogistico;
	}

}