package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Empresa;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Parametro;
import com.ssl.jv.gip.jpa.pojo.Proveedor;
import com.ssl.jv.gip.jpa.pojo.Region;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

/**
 * The Interface ComunEJBLocal.
 */
@Local
public interface ComunEJBLocal {

  /**
   * Consultar empresas.
   *
   * @return the list
   */
  public List<Empresa> consultarEmpresas();

  /**
   * Consultar regiones.
   *
   * @return the list
   */
  public List<Region> consultarRegiones(String pais);

  /**
   * Consultar paises.
   *
   * @return the list
   */
  public List<Pais> consultarPaises();

  /**
   * Consultar bodegas abastecedoras.
   *
   * @return the list
   */
  public List<Ubicacion> consultarBodegasAbastecedoras();

  public List<Estado> consultarEstados();

  public List<TipoDocumento> consultarTiposDocumentos();

  public List<Proveedor> consultarProveedores();

  public List<Pais> consultarPaisesTodos();

  public List<Unidad> consultarUnidades();

  public List<Moneda> consultarMonedas();

  public Parametro consultarParametroPorNombre(String nombre);

  public List<Parametro> consultarParametroPorNombres(String... nombres);

  /**
   * Metodo que consulta los documentos por uno o varios de los campos del filtro.
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param filtro
   * @return
   */
  List<Documento> consultarDocumentos(FiltroDocumentoDTO filtro);

  /**
   * Metodo que consulta un cliente por su id.
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @param nquery
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param id
   * @return
   */
  Cliente consultarCliente(Long id, String nquery);
}
