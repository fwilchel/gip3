package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import org.apache.log4j.Logger;

public class ProductoClienteDAO extends GenericDAO<ProductosXCliente> implements ProductoClienteDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(ProductoClienteDAO.class);

	public ProductoClienteDAO () {
		this.persistentClass = ProductosXCliente.class;
	}
	
	
	

}
