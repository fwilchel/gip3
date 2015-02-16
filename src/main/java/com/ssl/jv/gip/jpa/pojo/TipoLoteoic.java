package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;


/**
 * The persistent class for the tipo_loteoic database table.
 * 
 */
@Entity
@Table(name="tipo_loteoic")
@NamedQuery(name="TipoLoteoic.findAll", query="SELECT t FROM TipoLoteoic t")
public class TipoLoteoic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	@Column(name="descripcion_ingles")
	private String descripcionIngles;

	//bi-directional many-to-one association to DocumentoXLotesoic
	@OneToMany(mappedBy="tipoLoteoic",fetch=FetchType.LAZY)
	private List<DocumentoXLotesoic> documentoXLotesoics;

	//bi-directional many-to-one association to ProductosInventarioComext
	@OneToMany(mappedBy="tipoLoteoic",fetch=FetchType.LAZY)
	private List<ProductosInventarioComext> productosInventarioComexts;

	public TipoLoteoic() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionIngles() {
		return this.descripcionIngles;
	}

	public void setDescripcionIngles(String descripcionIngles) {
		this.descripcionIngles = descripcionIngles;
	}

	public List<DocumentoXLotesoic> getDocumentoXLotesoics() {
		return this.documentoXLotesoics;
	}

	public void setDocumentoXLotesoics(List<DocumentoXLotesoic> documentoXLotesoics) {
		this.documentoXLotesoics = documentoXLotesoics;
	}

	public DocumentoXLotesoic addDocumentoXLotesoic(DocumentoXLotesoic documentoXLotesoic) {
		getDocumentoXLotesoics().add(documentoXLotesoic);
		documentoXLotesoic.setTipoLoteoic(this);

		return documentoXLotesoic;
	}

	public DocumentoXLotesoic removeDocumentoXLotesoic(DocumentoXLotesoic documentoXLotesoic) {
		getDocumentoXLotesoics().remove(documentoXLotesoic);
		documentoXLotesoic.setTipoLoteoic(null);

		return documentoXLotesoic;
	}

	public List<ProductosInventarioComext> getProductosInventarioComexts() {
		return this.productosInventarioComexts;
	}

	public void setProductosInventarioComexts(List<ProductosInventarioComext> productosInventarioComexts) {
		this.productosInventarioComexts = productosInventarioComexts;
	}

	public ProductosInventarioComext addProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
		getProductosInventarioComexts().add(productosInventarioComext);
		productosInventarioComext.setTipoLoteoic(this);

		return productosInventarioComext;
	}

	public ProductosInventarioComext removeProductosInventarioComext(ProductosInventarioComext productosInventarioComext) {
		getProductosInventarioComexts().remove(productosInventarioComext);
		productosInventarioComext.setTipoLoteoic(null);

		return productosInventarioComext;
	}

}