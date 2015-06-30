package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;

import com.ssl.jv.gip.util.EstadoRequerimientoExportacion;











































import java.util.List;
import java.util.Date;

/**
 * The persistent class for the comext_requerimientoexportacion database table.
 * 
 */
@Entity
@Table(name="comext_requerimientoexportacion")
//@NamedQuery(name="ComextRequerimientoexportacion.findAll", query="SELECT c FROM ComextRequerimientoexportacion c")


@NamedQueries({
	  
	  @NamedQuery(name="ComextRequerimientoexportacion.findAll", query="SELECT c FROM ComextRequerimientoexportacion c JOIN FETCH c.modalidadembarque "),
	 // @NamedQuery(name="ComextRequerimientoexportacion.findAll", query="SELECT c FROM ComextRequerimientoexportacion c"),
	  @NamedQuery(name="ComextRequerimientoexportacion.BUSCAR_DOCUMENTO_POR_CONSECUTIVO", query = "SELECT c FROM ComextRequerimientoexportacion c WHERE c.id = :id")})
	   



//select * from comext_requerimientoexportacion where TO_CHAR(id, '9999')  LIKE '%%'
//UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) 

public class ComextRequerimientoexportacion implements Serializable {
		
	
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5796988097469664301L;
	
	public static final String FIND_ALL = "ComextRequerimientoexportacion.findAll";
	public static final String BUSCAR_DOCUMENTO_POR_CONSECUTIVO = "ComextRequerimientoexportacion.BUSCAR_DOCUMENTO_POR_CONSECUTIVO";
	  
	@Id
	@SequenceGenerator(name="COMEXT_REQUERIMIENTOEXPORTACION_ID_GENERATOR", sequenceName="comext_requerimientoexportacion_id_seq" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMEXT_REQUERIMIENTOEXPORTACION_ID_GENERATOR")
	private Long id;

	
	
	
	
	
	
	private String cciudadpais;

	
	private String cdireccion;
	
	private String cnit;
	 @Transient
	private String codigo;
	 
	private String contactobi;
	 @Transient
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
	 @Transient
	private Boolean enviaraestadireccion;

	@Column(name="estado")
	private Integer estado;

	@Column(name="fecha")
	private Date fecha;

	@Column(name="fechasolicitud")
	private Date fechasolicitud;
	
	private String puertosalida;
	
	//private int modalidadembarque;
	
	private String tipocontenedores;
	
	private String terminoincoterm;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "modalidadembarque")
	private ModalidadEmbarque modalidadembarque;
	
    
	
	
	 
	private String flete;
	 @Transient
	private String fomapago;

	//@Column(name="id_cliente")
	//private Long idCliente;
	 @Transient
	private Boolean marcacionespecial;
	 
	
	 
	private String nciudadpais;
	 
	private String ndireccion;
	 
	private String nnit;
	 
	private String nrazonsocial;
	 
	private String ntelefono;
	 @Transient
	private String observacioneaadicionales;
	 @Transient
	private String otropago;
	 
	private String puertollegada;


	
	 
	private String servicecontract;
	 
	private String telefonoemailbi;
	
	
	
	 @Transient
	private String tipoprecio;
	 
	private String zipcodebi;

	//bi-directional many-to-one association to Reqxproducto
	//@OneToMany(mappedBy="comextRequerimientoexportacion")
	//private List<Reqxproducto> reqxproductos;
	
	
	 
	// bi-directional many-to-one association to AgenteAduana
	//  @ManyToOne(fetch = FetchType.EAGER)
	//  @JoinColumn(name = "puertosalida")
	//  private AgenteAduana agenteAduana;

	  
	// bi-directional many-to-one association to Clientes
		  @ManyToOne(fetch = FetchType.EAGER)
		  @JoinColumn(name = "id_cliente")
		  private Cliente idCliente;
	 
	

	public ComextRequerimientoexportacion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	/*public Long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}*/

	public Boolean getMarcacionespecial() {
		return this.marcacionespecial;
	}

	public void setMarcacionespecial(Boolean marcacionespecial) {
		this.marcacionespecial = marcacionespecial;
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

	/*public Long getPuertosalida() {
		return this.puertosalida;
	}

	public void setPuertosalida(Long puertosalida) {
		this.puertosalida = puertosalida;
	}*/

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

	/*public List<RequerimientosXDocumento> getRequerimientosXDocumentos() {
		return this.requerimientosXDocumentos;
	}

	public void setRequerimientosXDocumentos(List<RequerimientosXDocumento> requerimientosXDocumentos) {
		this.requerimientosXDocumentos = requerimientosXDocumentos;
	}*/


	/*public List<Reqxproducto> getReqxproductos() {
		return this.reqxproductos;
	}

	public void setReqxproductos(List<Reqxproducto> reqxproductos) {
		this.reqxproductos = reqxproductos;
	}*/
	
	
	
	 @Transient
	  public String getEstadoNombre() {
	    for (EstadoRequerimientoExportacion i : EstadoRequerimientoExportacion.values()) {
	      if (i.getCodigo().equals(this.estado)) {
	        return i.getNombre();
	      }
	    }
	    return null;
	  }
	 

	/*public AgenteAduana getAgenteAduana() {
		return agenteAduana;
	}

	
	public void setAgenteAduana(AgenteAduana agenteAduana) {
		this.agenteAduana = agenteAduana;
	}*/

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the fechasolicitud
	 */
	public Date getFechasolicitud() {
		return fechasolicitud;
	}

	/**
	 * @param fechasolicitud the fechasolicitud to set
	 */
	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}
/*
	
	public Long getPuertosalida() {
		return puertosalida;
	}

	
	public void setPuertosalida(Long puertosalida) {
		this.puertosalida = puertosalida;
	}
*/

	/**
	 * @return the idCliente
	 */
	public Cliente getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the puertosalida
	 */
	public String getPuertosalida() {
		return puertosalida;
	}

	/**
	 * @param puertosalida the puertosalida to set
	 */
	public void setPuertosalida(String puertosalida) {
		this.puertosalida = puertosalida;
	}

	/**
	 * @return the modalidadembarque
	 */
	public ModalidadEmbarque getModalidadembarque() {
		return modalidadembarque;
	}

	/**
	 * @param modalidadembarque the modalidadembarque to set
	 */
	public void setModalidadembarque(ModalidadEmbarque modalidadembarque) {
		this.modalidadembarque = modalidadembarque;
	}

/*	public int getModalidadembarque() {
		return modalidadembarque;
	}

	public void setModalidadembarque(int modalidadembarque) {
		this.modalidadembarque = modalidadembarque;
	}*/

	
	

}