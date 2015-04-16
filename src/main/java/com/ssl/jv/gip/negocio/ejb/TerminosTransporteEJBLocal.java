package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.InstruccionesEmbarqueDTO;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: EJB local para las transacciones sobre la informacion de terminos de transporte GIP</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
@Local
public interface TerminosTransporteEJBLocal {

  /**
   * Metodo para consultar el listado de terminos de transporte disponibles
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  List<ShipmentConditions> consultarListadoTerminosTransporte();

  /**
   * Metodo que obtiene una instruccion de embarque
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @param idTermTrans
   * @return
   */
  InstruccionesEmbarqueDTO consultarTerminosTransportePorId(String idTermTrans);

  /**
   * Metodo que consulta todos los agentes de aduana activos
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  List<AgenteAduana> consultarAgentesAduanaActivos();

  /**
   * Metodoq ue consulta las modalidades de embarque
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  List<ModalidadEmbarque> consultarModalidadesEmbarque();

  /**
   * Metodo que retorna el listado de incoterms de despacho
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  List<TerminoIncoterm> consultarIncoterms();

  /**
   * Metodo que actualiza una instruccion de embarque seleccionada
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  TerminosTransporte actualizarInstruccionEmbarque(TerminosTransporte tt);

  /**
   * Metodo que actualiza un documento
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @param documento
   */
  Documento actualizarDocumento(Documento documento);
}
