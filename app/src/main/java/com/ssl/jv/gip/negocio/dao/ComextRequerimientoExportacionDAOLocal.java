package com.ssl.jv.gip.negocio.dao;


import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;

@Local
public interface ComextRequerimientoExportacionDAOLocal extends IGenericDAO<ComextRequerimientoexportacion>{

	List<ComextRequerimientoexportacionDTO> consultarMarcacionEspecial(Long id);
	
	
public List<ComextRequerimientoexportacionDTO> crearMarcacionEspecial(String id);


public void actualizarEstadoRequerimientoExportacion(DocumentoRequerimientoExportacionDTO requerimiento);




	
}
