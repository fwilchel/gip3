package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comext_directorio_x_cliente database table.
 * 
 */
@Entity
@Table(name="comext_directorio_x_cliente")
@NamedQuery(name="ComextDirectorioXCliente.findAll", query="SELECT c FROM ComextDirectorioXCliente c")
public class ComextDirectorioXCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComextDirectorioXClientePK llave;
	
	public ComextDirectorioXCliente() {
	}

	public ComextDirectorioXClientePK getLlave() {
		return llave;
	}

	public void setLlave(ComextDirectorioXClientePK llave) {
		this.llave = llave;
	}


}