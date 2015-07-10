package com.ssl.jv.gip.negocio.dao;

import com.ssl.jv.gip.jpa.pojo.Reqxproducto;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;



@Stateless
@LocalBean
public class ReqxproductoDAO extends GenericDAO<Reqxproducto> implements  ReqxproductoDAOLocal {
	
	public ReqxproductoDAO () {
	    this.persistentClass = Reqxproducto.class;
	  }

}
