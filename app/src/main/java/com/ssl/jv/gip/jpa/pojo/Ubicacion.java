package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the ubicaciones database table.
 *
 */
@Entity
@Table(name = "ubicaciones")
@NamedQueries({
  @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u LEFT JOIN FETCH u.regione r LEFT JOIN FETCH r.pais p LEFT JOIN FETCH u.empresa e LEFT JOIN FETCH u.tipoCanal tc order by u.nombre"),
  @NamedQuery(name = "Ubicacion.findByIDs", query = "SELECT u FROM Ubicacion u LEFT JOIN FETCH u.regione r LEFT JOIN FETCH r.pais p WHERE u.id IN :ids")
})
public class Ubicacion implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String BUSCAR_UBICACIONES_QUE_SON_TIENDA_POR_USUARIO = "select u.id uid, u.nombre unombre, b.id bid, b.nombre bnombre from ubicaciones u inner join usuariosXgeografias uxg on u.id=uxg.id_geografia inner join regiones r on u.id_region=r.id inner join empresa e on u.id_empresa=e.id inner join paises p on r.id_pais=p.id inner join ubicaciones b on u.id_bodega_abastecedora=b.id where uxg.id_usuario=:usuario and u.es_tienda=true ORDER BY UPPER (u.nombre) ASC";

  public static final String BUSCAR_UBICACIONES_RECIBIR_DEVOLUCIONES_TIENDA = "SELECT  distinct 'usuariosXgeografias.id_geografia' uno,'usuariosXgeografias.id_usuario' dos,'usuariosXgeografias.tipo_geografia' tres,'ubicaciones.id_region' cuatro, "
      + "'paises.id' cinco,'paises.nombre ' seis,'empresa.id' AS ID_EMPRESA, 'empresa.nombre' AS NOMBRE_EMPRESA, ubicaciones.id_bodega_abastecedora, ub.nombre "
      + "FROM usuariosXgeografias,ubicaciones,regiones,paises,empresa, ubicaciones ub  "
      + "WHERE ubicaciones.id=usuariosXgeografias.id_geografia AND ubicaciones.id_region=regiones.id AND regiones.id_pais=paises.id "
      + "AND usuariosXgeografias.id_usuario=':usuario' AND ubicaciones.id_empresa = empresa.id AND ubicaciones.id_bodega_abastecedora NOT IN (-1, -2) "
      + "AND ubicaciones.id not in (-1, -2) AND ub.id = ubicaciones.id_bodega_abastecedora ORDER BY ub.nombre ASC";

  @Id
  @SequenceGenerator(name = "ubicacion_id_seq", sequenceName = "ubicacion_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicacion_id_seq")
  private Long id;

  @Column(name = "cliente_icg")
  private Long clienteIcg;

  @Column(name = "despacho_domingo")
  private Boolean despachoDomingo;

  @Column(name = "despacho_jueves")
  private Boolean despachoJueves;

  @Column(name = "despacho_lunes")
  private Boolean despachoLunes;

  @Column(name = "despacho_martes")
  private Boolean despachoMartes;

  @Column(name = "despacho_miercoles")
  private Boolean despachoMiercoles;

  @Column(name = "despacho_sabado")
  private Boolean despachoSabado;

  @Column(name = "despacho_viernes")
  private Boolean despachoViernes;

  private String direccion;

  @Column(name = "es_franquicia")
  private Boolean esFranquicia;

  @Column(name = "es_tienda")
  private Boolean esTienda;

  @Column(name = "id_ciudad")
  private Long idCiudad;

  @Column(name = "id_ubicacion_padre")
  private Long idUbicacionPadre;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "objeto_co")
  private Long objetoCo;

  @Column(name = "telefono")
  private String telefono;

  // bi-directional many-to-one association to Documento
  @OneToMany(mappedBy = "ubicacionDestino", fetch = FetchType.LAZY)
  private List<Documento> documentos1;

  // bi-directional many-to-one association to Documento
  @OneToMany(mappedBy = "ubicacionOrigen", fetch = FetchType.LAZY)
  private List<Documento> documentos2;

  // bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "ubicacionDestino", fetch = FetchType.LAZY)
  private List<MovimientosInventario> movimientosInventarios1;

  // bi-directional many-to-one association to MovimientosInventario
  @OneToMany(mappedBy = "ubicacionOrigen", fetch = FetchType.LAZY)
  private List<MovimientosInventario> movimientosInventarios2;

  // bi-directional many-to-one association to Saldo
  @OneToMany(mappedBy = "ubicacione", fetch = FetchType.LAZY)
  private List<Saldo> saldos;

  // bi-directional many-to-one association to Empresa
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa")
  private Empresa empresa;

  // bi-directional many-to-one association to Region
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_region")
  private Region regione;

  // bi-directional many-to-one association to TipoCanal
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_tipo_canal")
  private TipoCanal tipoCanal;

  // bi-directional many-to-one association to Ubicacion
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_bodega_abastecedora")
  private Ubicacion ubicacione;

  // bi-directional many-to-one association to Ubicacion
  @OneToMany(mappedBy = "ubicacione", fetch = FetchType.LAZY)
  private List<Ubicacion> ubicaciones;

  public Ubicacion() {
  }

  public Ubicacion(Long id) {
    this.id = id;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getClienteIcg() {
    return this.clienteIcg;
  }

  public void setClienteIcg(Long clienteIcg) {
    this.clienteIcg = clienteIcg;
  }

  public Boolean getDespachoDomingo() {
    return this.despachoDomingo;
  }

  public void setDespachoDomingo(Boolean despachoDomingo) {
    this.despachoDomingo = despachoDomingo;
  }

  public Boolean getDespachoJueves() {
    return this.despachoJueves;
  }

  public void setDespachoJueves(Boolean despachoJueves) {
    this.despachoJueves = despachoJueves;
  }

  public Boolean getDespachoLunes() {
    return this.despachoLunes;
  }

  public void setDespachoLunes(Boolean despachoLunes) {
    this.despachoLunes = despachoLunes;
  }

  public Boolean getDespachoMartes() {
    return this.despachoMartes;
  }

  public void setDespachoMartes(Boolean despachoMartes) {
    this.despachoMartes = despachoMartes;
  }

  public Boolean getDespachoMiercoles() {
    return this.despachoMiercoles;
  }

  public void setDespachoMiercoles(Boolean despachoMiercoles) {
    this.despachoMiercoles = despachoMiercoles;
  }

  public Boolean getDespachoSabado() {
    return this.despachoSabado;
  }

  public void setDespachoSabado(Boolean despachoSabado) {
    this.despachoSabado = despachoSabado;
  }

  public Boolean getDespachoViernes() {
    return this.despachoViernes;
  }

  public void setDespachoViernes(Boolean despachoViernes) {
    this.despachoViernes = despachoViernes;
  }

  public String getDireccion() {
    return this.direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public Boolean getEsFranquicia() {
    return this.esFranquicia;
  }

  public void setEsFranquicia(Boolean esFranquicia) {
    this.esFranquicia = esFranquicia;
  }

  public Boolean getEsTienda() {
    return this.esTienda;
  }

  public void setEsTienda(Boolean esTienda) {
    this.esTienda = esTienda;
  }

  public Long getIdCiudad() {
    return this.idCiudad;
  }

  public void setIdCiudad(Long idCiudad) {
    this.idCiudad = idCiudad;
  }

  public Long getIdUbicacionPadre() {
    return this.idUbicacionPadre;
  }

  public void setIdUbicacionPadre(Long idUbicacionPadre) {
    this.idUbicacionPadre = idUbicacionPadre;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getObjetoCo() {
    return this.objetoCo;
  }

  public void setObjetoCo(Long objetoCo) {
    this.objetoCo = objetoCo;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public List<Documento> getDocumentos1() {
    return this.documentos1;
  }

  public void setDocumentos1(List<Documento> documentos1) {
    this.documentos1 = documentos1;
  }

  public Documento addDocumentos1(Documento documentos1) {
    getDocumentos1().add(documentos1);
    documentos1.setUbicacionDestino(this);

    return documentos1;
  }

  public Documento removeDocumentos1(Documento documentos1) {
    getDocumentos1().remove(documentos1);
    documentos1.setUbicacionDestino(null);

    return documentos1;
  }

  public List<Documento> getDocumentos2() {
    return this.documentos2;
  }

  public void setDocumentos2(List<Documento> documentos2) {
    this.documentos2 = documentos2;
  }

  public Documento addDocumentos2(Documento documentos2) {
    getDocumentos2().add(documentos2);
    documentos2.setUbicacionOrigen(this);

    return documentos2;
  }

  public Documento removeDocumentos2(Documento documentos2) {
    getDocumentos2().remove(documentos2);
    documentos2.setUbicacionOrigen(null);

    return documentos2;
  }

  public List<MovimientosInventario> getMovimientosInventarios1() {
    return this.movimientosInventarios1;
  }

  public void setMovimientosInventarios1(
      List<MovimientosInventario> movimientosInventarios1) {
    this.movimientosInventarios1 = movimientosInventarios1;
  }

  public MovimientosInventario addMovimientosInventarios1(
      MovimientosInventario movimientosInventarios1) {
    getMovimientosInventarios1().add(movimientosInventarios1);
    movimientosInventarios1.setUbicacionDestino(this);

    return movimientosInventarios1;
  }

  public MovimientosInventario removeMovimientosInventarios1(
      MovimientosInventario movimientosInventarios1) {
    getMovimientosInventarios1().remove(movimientosInventarios1);
    movimientosInventarios1.setUbicacionDestino(null);

    return movimientosInventarios1;
  }

  public List<MovimientosInventario> getMovimientosInventarios2() {
    return this.movimientosInventarios2;
  }

  public void setMovimientosInventarios2(
      List<MovimientosInventario> movimientosInventarios2) {
    this.movimientosInventarios2 = movimientosInventarios2;
  }

  public MovimientosInventario addMovimientosInventarios2(
      MovimientosInventario movimientosInventarios2) {
    getMovimientosInventarios2().add(movimientosInventarios2);
    movimientosInventarios2.setUbicacionOrigen(this);

    return movimientosInventarios2;
  }

  public MovimientosInventario removeMovimientosInventarios2(
      MovimientosInventario movimientosInventarios2) {
    getMovimientosInventarios2().remove(movimientosInventarios2);
    movimientosInventarios2.setUbicacionOrigen(null);

    return movimientosInventarios2;
  }

  public List<Saldo> getSaldos() {
    return this.saldos;
  }

  public void setSaldos(List<Saldo> saldos) {
    this.saldos = saldos;
  }

  public Saldo addSaldo(Saldo saldo) {
    getSaldos().add(saldo);
    saldo.setUbicacione(this);

    return saldo;
  }

  public Saldo removeSaldo(Saldo saldo) {
    getSaldos().remove(saldo);
    saldo.setUbicacione(null);

    return saldo;
  }

  public Empresa getEmpresa() {
    return this.empresa;
  }

  public void setEmpresa(Empresa empresa) {
    this.empresa = empresa;
  }

  public Region getRegione() {
    return this.regione;
  }

  public void setRegione(Region regione) {
    this.regione = regione;
  }

  public TipoCanal getTipoCanal() {
    return this.tipoCanal;
  }

  public void setTipoCanal(TipoCanal tipoCanal) {
    this.tipoCanal = tipoCanal;
  }

  public Ubicacion getUbicacione() {
    return this.ubicacione;
  }

  public void setUbicacione(Ubicacion ubicacione) {
    this.ubicacione = ubicacione;
  }

  public List<Ubicacion> getUbicaciones() {
    return this.ubicaciones;
  }

  public void setUbicaciones(List<Ubicacion> ubicaciones) {
    this.ubicaciones = ubicaciones;
  }

  public Ubicacion addUbicacione(Ubicacion ubicacione) {
    getUbicaciones().add(ubicacione);
    ubicacione.setUbicacione(this);

    return ubicacione;
  }

  public Ubicacion removeUbicacione(Ubicacion ubicacione) {
    getUbicaciones().remove(ubicacione);
    ubicacione.setUbicacione(null);

    return ubicacione;
  }
}
