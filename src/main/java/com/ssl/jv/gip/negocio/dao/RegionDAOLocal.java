package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Region;

@Local
public interface RegionDAOLocal extends IGenericDAO<Region>{

	public List<Region> findByRegional(String pais);
	
}
