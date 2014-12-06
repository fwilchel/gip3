package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the comext_user_role database table.
 * 
 */
@Entity
@Table(name="comext_user_role")
@NamedQuery(name="ComextUserRole.findAll", query="SELECT c FROM ComextUserRole c")
public class ComextUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComextUserRolePK llave;
	
	public ComextUserRole() {
	}

	public ComextUserRolePK getLlave() {
		return llave;
	}

	public void setLlave(ComextUserRolePK llave) {
		this.llave = llave;
	}
	
	

}