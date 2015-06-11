package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the comext_requerimientoexportacion database table.
 * 
 */
@Entity
@Table(name="comext_requerimientoexportacion")
@NamedQuery(name="ComextRequerimientoexportacion.findAll", query="SELECT c FROM ComextRequerimientoexportacion c")
public class ComextRequerimientoexportacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String cciudadpais;

	private String cdireccion;

	private String cnit;

	private String codigo;

	private String contactobi;

	private String cotradireccion;

	private String crazonsocial;

	private String ctelefono;

	private String dacontacto;

	private String dancual;

	private String danobscuales;

	private Boolean danobservaciones;

	private String danopciones;

	private String darazonsocial;

	private String datelefonoemail;

	private String direccionentregabi;

	private String emisionbi;

	private Boolean enviaraestadireccion;

	private Integer estado;

	private Timestamp fecha;

	private Timestamp fechasolicitud;

	private String flete;

	private String fomapago;

	@Column(name="id_cliente")
	private Long idCliente;

	private Boolean marcacionespecial;

	private String modalidadembarque;

	private String nciudadpais;

	private String ndireccion;

	private String nnit;

	private String nrazonsocial;

	private String ntelefono;

	private String observacioneaadicionales;

	private String otropago;

	private String puertollegada;

	private Long puertosalida;

	private String servicecontract;

	private String telefonoemailbi;

	private String terminoincoterm;

	private String tipocontenedores;

	private String tipoprecio;

	private String zipcodebi;

	//bi-directional many-to-one association to RequerimientosXDocumento
	@OneToMany(mappedBy="comextRequerimientoexportacion")
	private List<RequerimientosXDocumento> requerimientosXDocumentos;

	//bi-directional many-to-one association to Reqxproducto
	@OneToMany(mappedBy="comextRequerimientoexportacion")
	private List<Reqxproducto> reqxproductos;

	public ComextRequerimientoexportacion() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCciudadpais() {
		return this.cciudadpais;
	}

	public void setCciudadpais(String cciudadpais) {
		this.cciudadpais = cciudadpais;
	}

	public String getCdireccion() {
		return this.cdireccion;
	}

	public void setCdireccion(String cdireccion) {
		this.cdireccion = cdireccion;
	}

	public String getCnit() {
		return this.cnit;
	}

	public void setCnit(String cnit) {
		this.cnit = cnit;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContactobi() {
		return this.contactobi;
	}

	public void setContactobi(String contactobi) {
		this.contactobi = contactobi;
	}

	public String getCotradireccion() {
		return this.cotradireccion;
	}

	public void setCotradireccion(String cotradireccion) {
		this.cotradireccion = cotradireccion;
	}

	public String getCrazonsocial() {
		return this.crazonsocial;
	}

	public void setCrazonsocial(String crazonsocial) {
		this.crazonsocial = crazonsocial;
	}

	public String getCtelefono() {
		return this.ctelefono;
	}

	public void setCtelefono(String ctelefono) {
		this.ctelefono = ctelefono;
	}

	public String getDacontacto() {
		return this.dacontacto;
	}

	public void setDacontacto(String dacontacto) {
		this.dacontacto = dacontacto;
	}

	public String getDancual() {
		return this.dancual;
	}

	public void setDancual(String dancual) {
		this.dancual = dancual;
	}

	public String getDanobscuales() {
		return this.danobscuales;
	}

	public void setDanobscuales(String danobscuales) {
		this.danobscuales = danobscuales;
	}

	public Boolean getDanobservaciones() {
		return this.danobservaciones;
	}

	public void setDanobservaciones(Boolean danobservaciones) {
		this.danobservaciones = danobservaciones;
	}

	public String getDanopciones() {
		return this.danopciones;
	}

	public void setDanopciones(String danopciones) {
		this.danopciones = danopciones;
	}

	public String getDarazonsocial() {
		return this.darazonsocial;
	}

	public void setDarazonsocial(String darazonsocial) {
		this.darazonsocial = darazonsocial;
	}

	public String getDatelefonoemail() {
		return this.datelefonoemail;
	}

	public void setDatelefonoemail(String datelefonoemail) {
		this.datelefonoemail = datelefonoemail;
	}

	public String getDireccionentregabi() {
		return this.direccionentregabi;
	}

	public void setDireccionentregabi(String direccionentregabi) {
		this.direccionentregabi = direccionentregabi;
	}

	public String getEmisionbi() {
		return this.emisionbi;
	}

	public void setEmisionbi(String emisionbi) {
		this.emisionbi = emisionbi;
	}

	public Boolean getEnviaraestadireccion() {
		return this.enviaraestadireccion;
	}

	public void setEnviaraestadireccion(Boolean enviaraestadireccion) {
		this.enviaraestadireccion = enviaraestadireccion;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechasolicitud() {
		return this.fechasolicitud;
	}

	public void setFechasolicitud(Timestamp fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}

	public String getFlete() {
		return this.flete;
	}

	public void setFlete(String flete) {
		this.flete = flete;
	}

	public String getFomapago() {
		return this.fomapago;
	}

	public void setFomapago(String fomapago) {
		this.fomapago = fomapago;
	}

	public Long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Boolean getMarcacionespecial() {
		return this.marcacionespecial;
	}

	public void setMarcacionespecial(Boolean marcacionespecial) {
		this.marcacionespecial = marcacionespecial;
	}

	public String getModalidadembarque() {
		return this.modalidadembarque;
	}

	public void setModalidadembarque(String modalidadembarque) {
		this.modalidadembarque = modalidadembarque;
	}

	public String getNciudadpais() {
		return this.nciudadpais;
	}

	public void setNciudadpais(String nciudadpais) {
		this.nciudadpais = nciudadpais;
	}

	public String getNdireccion() {
		return this.ndireccion;
	}

	public void setNdireccion(String ndireccion) {
		this.ndireccion = ndireccion;
	}

	public String getNnit() {
		return this.nnit;
	}

	public void setNnit(String nnit) {
		this.nnit = nnit;
	}

	public String getNrazonsocial() {
		return this.nrazonsocial;
	}

	public void setNrazonsocial(String nrazonsocial) {
		this.nrazonsocial = nrazonsocial;
	}

	public String getNtelefono() {
		return this.ntelefono;
	}

	public void setNtelefono(String ntelefono) {
		this.ntelefono = ntelefono;
	}

	public String getObservacioneaadicionales() {
		return this.observacioneaadicionales;
	}

	public void setObservacioneaadicionales(String observacioneaadicionales) {
		this.observacioneaadicionales = observacioneaadicionales;
	}

	public String getOtropago() {
		return this.otropago;
	}

	public void setOtropago(String otropago) {
		this.otropago = otropago;
	}

	public String getPuertollegada() {
		return this.puertollegada;
	}

	public void setPuertollegada(String puertollegada) {
		this.puertollegada = puertollegada;
	}

	public Long getPuertosalida() {
		return this.puertosalida;
	}

	public void setPuertosalida(Long puertosalida) {
		this.puertosalida = puertosalida;
	}

	public String getServicecontract() {
		return this.servicecontract;
	}

	public void setServicecontract(String servicecontract) {
		this.servicecontract = servicecontract;
	}

	public String getTelefonoemailbi() {
		return this.telefonoemailbi;
	}

	public void setTelefonoemailbi(String telefonoemailbi) {
		this.telefonoemailbi = telefonoemailbi;
	}

	public String getTerminoincoterm() {
		return this.terminoincoterm;
	}

	public void setTerminoincoterm(String terminoincoterm) {
		this.terminoincoterm = terminoincoterm;
	}

	public String getTipocontenedores() {
		return this.tipocontenedores;
	}

	public void setTipocontenedores(String tipocontenedores) {
		this.tipocontenedores = tipocontenedores;
	}

	public String getTipoprecio() {
		return this.tipoprecio;
	}

	public void setTipoprecio(String tipoprecio) {
		this.tipoprecio = tipoprecio;
	}

	public String getZipcodebi() {
		return this.zipcodebi;
	}

	public void setZipcodebi(String zipcodebi) {
		this.zipcodebi = zipcodebi;
	}

	public List<RequerimientosXDocumento> getRequerimientosXDocumentos() {
		return this.requerimientosXDocumentos;
	}

	public void setRequerimientosXDocumentos(List<RequerimientosXDocumento> requerimientosXDocumentos) {
		this.requerimientosXDocumentos = requerimientosXDocumentos;
	}

	public RequerimientosXDocumento addRequerimientosXDocumento(RequerimientosXDocumento requerimientosXDocumento) {
		getRequerimientosXDocumentos().add(requerimientosXDocumento);
		requerimientosXDocumento.setComextRequerimientoexportacion(this);

		return requerimientosXDocumento;
	}

	public RequerimientosXDocumento removeRequerimientosXDocumento(RequerimientosXDocumento requerimientosXDocumento) {
		getRequerimientosXDocumentos().remove(requerimientosXDocumento);
		requerimientosXDocumento.setComextRequerimientoexportacion(null);

		return requerimientosXDocumento;
	}

	public List<Reqxproducto> getReqxproductos() {
		return this.reqxproductos;
	}

	public void setReqxproductos(List<Reqxproducto> reqxproductos) {
		this.reqxproductos = reqxproductos;
	}

	public Reqxproducto addReqxproducto(Reqxproducto reqxproducto) {
		getReqxproductos().add(reqxproducto);
		reqxproducto.setComextRequerimientoexportacion(this);

		return reqxproducto;
	}

	public Reqxproducto removeReqxproducto(Reqxproducto reqxproducto) {
		getReqxproductos().remove(reqxproducto);
		reqxproducto.setComextRequerimientoexportacion(null);

		return reqxproducto;
	}

}