package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import javax.ejb.Local;
import com.ssl.jv.gip.jpa.pojo.AgenteAduana;

@Local
public interface AgenteAduanaDAOLocal {

	public List<AgenteAduana> consultarAgenteAduanaPorFiltro(AgenteAduana pFiltro);
	
	/**
	 * Metodo que consulta todos los agentes de aduana activos
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public List<AgenteAduana> getAllActive();	
}
