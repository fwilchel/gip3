package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;

@Local
public interface ComextRequerimientoExportacionDAOLocal extends
		IGenericDAO<ComextRequerimientoexportacion> {

	public List<ComextRequerimientoexportacionDTO> consultarMarcacionEspecial(
			Long id);

	public List<ComextRequerimientoexportacionDTO> crearMarcacionEspecial(
			String id);

}
