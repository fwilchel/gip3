package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the comext_user_role database table.
 * 
 */
@Embeddable
public class ComextUserRolePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="role_id")
	private Long roleId;

	@Column(name="user_id")
	private Long userId;

	public ComextUserRolePK() {
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComextUserRolePK other = (ComextUserRolePK) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	

}