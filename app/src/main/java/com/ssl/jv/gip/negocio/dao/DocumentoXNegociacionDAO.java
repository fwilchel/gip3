package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;

@Stateless
@LocalBean
public class DocumentoXNegociacionDAO extends GenericDAO<DocumentoXNegociacion> implements DocumentoXNegociacionDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(DocumentoXNegociacionDAO.class);

  public DocumentoXNegociacionDAO() {
    this.persistentClass = DocumentoXNegociacion.class;
  }

  public List<DocumentoXNegociacion> consultarDocumentoXNegociacionPorIdDocumento(Long idDocumento) {
    Query query = em
        .createNamedQuery("DocumentoXNegociacion.findByIdDocumento");
    query.setParameter("idDocumento", idDocumento);
    return query.getResultList();
  }

}
