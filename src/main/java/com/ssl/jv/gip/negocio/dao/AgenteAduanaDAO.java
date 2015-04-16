package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;

@Stateless
@LocalBean
public class AgenteAduanaDAO extends GenericDAO<AgenteAduana> implements AgenteAduanaDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(AgenteAduanaDAO.class);

  public AgenteAduanaDAO() {
    this.persistentClass = AgenteAduana.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AgenteAduana> consultarAgenteAduanaPorFiltro(AgenteAduana pFiltro) {
    List<AgenteAduana> listado = new ArrayList<AgenteAduana>();
    String query = null;
    try {
      if (pFiltro.getActivo()) {
        query = "select a from AgenteAduana a WHERE a.activo = true AND a.nombre like :nombre order by a.nombre";
      } else {
        query = "select a from AgenteAduana a WHERE a.nombre like :nombre order by a.nombre";
      }
      listado = em.createQuery(query).
          setParameter("nombre", PORCENTAJE_LIKE + pFiltro.getNombre() == null ? "" : pFiltro.getNombre() + PORCENTAJE_LIKE).
          getResultList();
    } catch (Exception e) {
      LOGGER.error(e + "********Error consultando agentes de aduana");
      return null;
    }
    return listado;
  }

  /**
   * @see com.ssl.jv.gip.negocio.dao.AgenteAduanaDAOLocal#getAllActive()
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   */
  @Override
  public List<AgenteAduana> getAllActive() {
    List<AgenteAduana> listado = new ArrayList<AgenteAduana>();
    try {
      listado = em.createNamedQuery("AgenteAduana.findAllActive").
          getResultList();
    } catch (Exception e) {
      LOGGER.error(e + "********Error consultando agentes de aduana");
      return null;
    }
    return listado;
  }

}
