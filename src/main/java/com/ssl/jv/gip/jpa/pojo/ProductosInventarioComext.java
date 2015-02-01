package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the productos_inventario_comext database table.
 * 
 */
@Entity
@Table(name="productos_inventario_comext")
@NamedQueries({
	@NamedQuery(name="ProductosInventarioComext.findAll", query="SELECT p FROM ProductosInventarioComext p"),
	@NamedQuery(name="ProductosInventarioComext.findBySku", query="SELECT p FROM ProductosInventarioComext p WHERE p.idProducto = (SELECT pi.id FROM ProductosInventario pi WHERE pi.sku= :sku)")
})


public class ProductosInventarioComext implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_producto")
	private Long idProducto;

	private BigDecimal alto;

	@Column(name="alto_embalaje")
	private BigDecimal altoEmbalaje;

	@Column(name="altura_max_pallet")
	private BigDecimal alturaMaxPallet;

	private BigDecimal ancho;

	@Column(name="ancho_embalaje")
	private BigDecimal anchoEmbalaje;

	@Column(name="cant_cajas_x_tendido")
	private BigDecimal cantCajasXTendido;

	@Column(name="cant_max_tend_x_pallet")
	private BigDecimal cantMaxTendXPallet;

	@Column(name="cantidad_x_embalaje")
	private BigDecimal cantidadXEmbalaje;

	private String clave;

	@Column(name="codigo_sap")
	private String codigoSap;
	
	//@Column(name="controlstock")
	@Transient
	private Boolean controlStock;

	private String descripcion;

	@Column(name="ean_14")
	private Long ean14;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creado")
	private Date fechaCreado;

	private Boolean importado;

	private BigDecimal largo;

	@Column(name="largo_embalaje")
	private BigDecimal largoEmbalaje;

	@Column(name="marcacion_fecha_vencimiento")
	private String marcacionFechaVencimiento;

	@Column(name="nombre_prd_proveedor")
	private String nombrePrdProveedor;

	@Column(name="observaciones_unidad_embalaje")
	private String observacionesUnidadEmbalaje;

	@Column(name="peso_bruto")
	private BigDecimal pesoBruto;

	@Column(name="peso_bruto_embalaje")
	private BigDecimal pesoBrutoEmbalaje;

	@Column(name="peso_bruto_max_pallet")
	private BigDecimal pesoBrutoMaxPallet;

	@Column(name="peso_neto")
	private BigDecimal pesoNeto;

	@Column(name="peso_neto_embalaje")
	private BigDecimal pesoNetoEmbalaje;

	@Column(name="posicion_arancelaria")
	private String posicionArancelaria;

	@Column(name="ruta_certificaciones")
	private String rutaCertificaciones;

	@Column(name="ruta_examenes_lab")
	private String rutaExamenesLab;

	@Column(name="ruta_ficha_tecnica")
	private String rutaFichaTecnica;

	@Column(name="ruta_formula_cuali_cuanti")
	private String rutaFormulaCualiCuanti;

	@Column(name="ruta_foto")
	private String rutaFoto;

	@Column(name="ruta_foto1")
	private String rutaFoto1;

	@Column(name="ruta_foto2")
	private String rutaFoto2;

	@Column(name="ruta_foto3")
	private String rutaFoto3;

	@Column(name="ruta_info_etiquetado")
	private String rutaInfoEtiquetado;

	@Column(name="ruta_marcacion_lote")
	private String rutaMarcacionLote;

	@Column(name="ruta_reg_sanitario")
	private String rutaRegSanitario;

	@Column(name="tiempo_util")
	private BigDecimal tiempoUtil;

	@Column(name="total_cajas_x_pallet")
	private BigDecimal totalCajasXPallet;

	@Column(name="unidad_medida")
	private String unidadMedida;

	@Column(name="unidad_min_despacho_x_tendido")
	private BigDecimal unidadMinDespachoXTendido;

	@Column(name="unidad_peso")
	private String unidadPeso;

	private BigDecimal volumen;

	@Column(name="volumen_embalaje")
	private BigDecimal volumenEmbalaje;

	@Column(name="volumen_pallet")
	private BigDecimal volumenPallet;

	//bi-directional many-to-one association to ComextItemc
	@OneToMany(mappedBy="productosInventarioComext")
	private List<ComextItemc> comextItemcs;

	//bi-directional many-to-one association to CuentaContable
	@ManyToOne
	@JoinColumn(name="id_cuenta_contable_ce")
	private CuentaContable cuentaContable;

	//bi-directional one-to-one association to ProductosInventario
	@OneToOne
	@JoinColumn(name="id_producto")
	private ProductosInventario productosInventario;

	//bi-directional many-to-one association to TipoLoteoic
	@ManyToOne
	@JoinColumn(name="id_tipo_loteoic")
	private TipoLoteoic tipoLoteoic;

	//bi-directional many-to-one association to Unidad
	@ManyToOne
	@JoinColumn(name="id_unidad_embalaje")
	private Unidad unidadEmbalaje;

	public ProductosInventarioComext() {
	}

	public Long getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public BigDecimal getAlto() {
		return this.alto;
	}

	public void setAlto(BigDecimal alto) {
		this.alto = alto;
	}

	public BigDecimal getAltoEmbalaje() {
		return this.altoEmbalaje;
	}

	public void setAltoEmbalaje(BigDecimal altoEmbalaje) {
		this.altoEmbalaje = altoEmbalaje;
	}

	public BigDecimal getAlturaMaxPallet() {
		return this.alturaMaxPallet;
	}

	public void setAlturaMaxPallet(BigDecimal alturaMaxPallet) {
		this.alturaMaxPallet = alturaMaxPallet;
	}

	public BigDecimal getAncho() {
		return this.ancho;
	}

	public void setAncho(BigDecimal ancho) {
		this.ancho = ancho;
	}

	public BigDecimal getAnchoEmbalaje() {
		return this.anchoEmbalaje;
	}

	public void setAnchoEmbalaje(BigDecimal anchoEmbalaje) {
		this.anchoEmbalaje = anchoEmbalaje;
	}

	public BigDecimal getCantCajasXTendido() {
		return this.cantCajasXTendido;
	}

	public void setCantCajasXTendido(BigDecimal cantCajasXTendido) {
		this.cantCajasXTendido = cantCajasXTendido;
	}

	public BigDecimal getCantMaxTendXPallet() {
		return this.cantMaxTendXPallet;
	}

	public void setCantMaxTendXPallet(BigDecimal cantMaxTendXPallet) {
		this.cantMaxTendXPallet = cantMaxTendXPallet;
	}

	public BigDecimal getCantidadXEmbalaje() {
		return this.cantidadXEmbalaje;
	}

	public void setCantidadXEmbalaje(BigDecimal cantidadXEmbalaje) {
		this.cantidadXEmbalaje = cantidadXEmbalaje;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodigoSap() {
		return this.codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getEan14() {
		return this.ean14;
	}

	public void setEan14(Long ean14) {
		this.ean14 = ean14;
	}

	public Date getFechaCreado() {
		return this.fechaCreado;
	}

	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	public Boolean getImportado() {
		return this.importado;
	}

	public void setImportado(Boolean importado) {
		this.importado = importado;
	}

	public BigDecimal getLargo() {
		return this.largo;
	}

	public void setLargo(BigDecimal largo) {
		this.largo = largo;
	}

	public BigDecimal getLargoEmbalaje() {
		return this.largoEmbalaje;
	}

	public void setLargoEmbalaje(BigDecimal largoEmbalaje) {
		this.largoEmbalaje = largoEmbalaje;
	}

	public String getMarcacionFechaVencimiento() {
		return this.marcacionFechaVencimiento;
	}

	public void setMarcacionFechaVencimiento(String marcacionFechaVencimiento) {
		this.marcacionFechaVencimiento = marcacionFechaVencimiento;
	}

	public String getNombrePrdProveedor() {
		return this.nombrePrdProveedor;
	}

	public void setNombrePrdProveedor(String nombrePrdProveedor) {
		this.nombrePrdProveedor = nombrePrdProveedor;
	}

	public String getObservacionesUnidadEmbalaje() {
		return this.observacionesUnidadEmbalaje;
	}

	public void setObservacionesUnidadEmbalaje(String observacionesUnidadEmbalaje) {
		this.observacionesUnidadEmbalaje = observacionesUnidadEmbalaje;
	}

	public BigDecimal getPesoBruto() {
		return this.pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoBrutoEmbalaje() {
		return this.pesoBrutoEmbalaje;
	}

	public void setPesoBrutoEmbalaje(BigDecimal pesoBrutoEmbalaje) {
		this.pesoBrutoEmbalaje = pesoBrutoEmbalaje;
	}

	public BigDecimal getPesoBrutoMaxPallet() {
		return this.pesoBrutoMaxPallet;
	}

	public void setPesoBrutoMaxPallet(BigDecimal pesoBrutoMaxPallet) {
		this.pesoBrutoMaxPallet = pesoBrutoMaxPallet;
	}

	public BigDecimal getPesoNeto() {
		return this.pesoNeto;
	}

	public void setPesoNeto(BigDecimal pesoNeto) {
		this.pesoNeto = pesoNeto;
	}

	public BigDecimal getPesoNetoEmbalaje() {
		return this.pesoNetoEmbalaje;
	}

	public void setPesoNetoEmbalaje(BigDecimal pesoNetoEmbalaje) {
		this.pesoNetoEmbalaje = pesoNetoEmbalaje;
	}

	public String getPosicionArancelaria() {
		return this.posicionArancelaria;
	}

	public void setPosicionArancelaria(String posicionArancelaria) {
		this.posicionArancelaria = posicionArancelaria;
	}

	public String getRutaCertificaciones() {
		return this.rutaCertificaciones;
	}

	public void setRutaCertificaciones(String rutaCertificaciones) {
		this.rutaCertificaciones = rutaCertificaciones;
	}

	public String getRutaExamenesLab() {
		return this.rutaExamenesLab;
	}

	public void setRutaExamenesLab(String rutaExamenesLab) {
		this.rutaExamenesLab = rutaExamenesLab;
	}

	public String getRutaFichaTecnica() {
		return this.rutaFichaTecnica;
	}

	public void setRutaFichaTecnica(String rutaFichaTecnica) {
		this.rutaFichaTecnica = rutaFichaTecnica;
	}

	public String getRutaFormulaCualiCuanti() {
		return this.rutaFormulaCualiCuanti;
	}

	public void setRutaFormulaCualiCuanti(String rutaFormulaCualiCuanti) {
		this.rutaFormulaCualiCuanti = rutaFormulaCualiCuanti;
	}

	public String getRutaFoto() {
		return this.rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public String getRutaFoto1() {
		return this.rutaFoto1;
	}

	public void setRutaFoto1(String rutaFoto1) {
		this.rutaFoto1 = rutaFoto1;
	}

	public String getRutaFoto2() {
		return this.rutaFoto2;
	}

	public void setRutaFoto2(String rutaFoto2) {
		this.rutaFoto2 = rutaFoto2;
	}

	public String getRutaFoto3() {
		return this.rutaFoto3;
	}

	public void setRutaFoto3(String rutaFoto3) {
		this.rutaFoto3 = rutaFoto3;
	}

	public String getRutaInfoEtiquetado() {
		return this.rutaInfoEtiquetado;
	}

	public void setRutaInfoEtiquetado(String rutaInfoEtiquetado) {
		this.rutaInfoEtiquetado = rutaInfoEtiquetado;
	}

	public String getRutaMarcacionLote() {
		return this.rutaMarcacionLote;
	}

	public void setRutaMarcacionLote(String rutaMarcacionLote) {
		this.rutaMarcacionLote = rutaMarcacionLote;
	}

	public String getRutaRegSanitario() {
		return this.rutaRegSanitario;
	}

	public void setRutaRegSanitario(String rutaRegSanitario) {
		this.rutaRegSanitario = rutaRegSanitario;
	}

	public BigDecimal getTiempoUtil() {
		return this.tiempoUtil;
	}

	public void setTiempoUtil(BigDecimal tiempoUtil) {
		this.tiempoUtil = tiempoUtil;
	}

	public BigDecimal getTotalCajasXPallet() {
		return this.totalCajasXPallet;
	}

	public void setTotalCajasXPallet(BigDecimal totalCajasXPallet) {
		this.totalCajasXPallet = totalCajasXPallet;
	}

	public String getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public BigDecimal getUnidadMinDespachoXTendido() {
		return this.unidadMinDespachoXTendido;
	}

	public void setUnidadMinDespachoXTendido(BigDecimal unidadMinDespachoXTendido) {
		this.unidadMinDespachoXTendido = unidadMinDespachoXTendido;
	}

	public String getUnidadPeso() {
		return this.unidadPeso;
	}

	public void setUnidadPeso(String unidadPeso) {
		this.unidadPeso = unidadPeso;
	}

	public BigDecimal getVolumen() {
		return this.volumen;
	}

	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}

	public BigDecimal getVolumenEmbalaje() {
		return this.volumenEmbalaje;
	}

	public void setVolumenEmbalaje(BigDecimal volumenEmbalaje) {
		this.volumenEmbalaje = volumenEmbalaje;
	}

	public BigDecimal getVolumenPallet() {
		return this.volumenPallet;
	}

	public void setVolumenPallet(BigDecimal volumenPallet) {
		this.volumenPallet = volumenPallet;
	}

	public List<ComextItemc> getComextItemcs() {
		return this.comextItemcs;
	}

	public void setComextItemcs(List<ComextItemc> comextItemcs) {
		this.comextItemcs = comextItemcs;
	}

	public ComextItemc addComextItemc(ComextItemc comextItemc) {
		getComextItemcs().add(comextItemc);
		comextItemc.setProductosInventarioComext(this);

		return comextItemc;
	}

	public ComextItemc removeComextItemc(ComextItemc comextItemc) {
		getComextItemcs().remove(comextItemc);
		comextItemc.setProductosInventarioComext(null);

		return comextItemc;
	}

	public CuentaContable getCuentaContable() {
		return this.cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public ProductosInventario getProductosInventario() {
		return this.productosInventario;
	}

	public void setProductosInventario(ProductosInventario productosInventario) {
		this.productosInventario = productosInventario;
	}

	public TipoLoteoic getTipoLoteoic() {
		return this.tipoLoteoic;
	}

	public void setTipoLoteoic(TipoLoteoic tipoLoteoic) {
		this.tipoLoteoic = tipoLoteoic;
	}

	public Unidad getUnidadEmbalaje() {
		return this.unidadEmbalaje;
	}

	public void setUnidadEmbalaje(Unidad unidade) {
		this.unidadEmbalaje = unidade;
	}

	public Boolean getControlStock() {
		return controlStock;
	}

	public void setControlStock(Boolean controlStock) {
		this.controlStock = controlStock;
	}

}