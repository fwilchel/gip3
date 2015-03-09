package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

/**
 * The Class UbicacionRecibirDevolucionDTO.
 */
public class UbicacionRecibirDevolucionDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6826470281110432159L;
	
	/** The id geografia. */
	private String idGeografia;
	
	/** The id usuario. */
	private String idUsuario;
	
	/** The tipo geografia. */
	private String tipoGeografia;
	
	/** The id region. */
	private String idRegion;
	
	/** The id pais. */
	private String idPais;
	
	/** The nombre pais. */
	private String nombrePais;
	
	/** The id empresa. */
	private String idEmpresa;
	
	/** The nombre empresa. */
	private String nombreEmpresa;
	
	/** The bodega abastecedora. */
	private String bodegaAbastecedora;
	
	private String nombre;
	
	/**
	 * Gets the id geografia.
	 *
	 * @return the id geografia
	 */
	public String getIdGeografia() {
		return idGeografia;
	}
	
	/**
	 * Sets the id geografia.
	 *
	 * @param idGeografia the new id geografia
	 */
	public void setIdGeografia(String idGeografia) {
		this.idGeografia = idGeografia;
	}
	
	/**
	 * Gets the id usuario.
	 *
	 * @return the id usuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Sets the id usuario.
	 *
	 * @param idUsuario the new id usuario
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Gets the tipo geografia.
	 *
	 * @return the tipo geografia
	 */
	public String getTipoGeografia() {
		return tipoGeografia;
	}
	
	/**
	 * Sets the tipo geografia.
	 *
	 * @param tipoGeografia the new tipo geografia
	 */
	public void setTipoGeografia(String tipoGeografia) {
		this.tipoGeografia = tipoGeografia;
	}
	
	/**
	 * Gets the id region.
	 *
	 * @return the id region
	 */
	public String getIdRegion() {
		return idRegion;
	}
	
	/**
	 * Sets the id region.
	 *
	 * @param idRegion the new id region
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}
	
	/**
	 * Gets the id pais.
	 *
	 * @return the id pais
	 */
	public String getIdPais() {
		return idPais;
	}
	
	/**
	 * Sets the id pais.
	 *
	 * @param idPais the new id pais
	 */
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	
	/**
	 * Gets the nombre pais.
	 *
	 * @return the nombre pais
	 */
	public String getNombrePais() {
		return nombrePais;
	}
	
	/**
	 * Sets the nombre pais.
	 *
	 * @param nombrePais the new nombre pais
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	/**
	 * Gets the id empresa.
	 *
	 * @return the id empresa
	 */
	public String getIdEmpresa() {
		return idEmpresa;
	}
	
	/**
	 * Sets the id empresa.
	 *
	 * @param idEmpresa the new id empresa
	 */
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	/**
	 * Gets the nombre empresa.
	 *
	 * @return the nombre empresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	
	/**
	 * Sets the nombre empresa.
	 *
	 * @param nombreEmpresa the new nombre empresa
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	/**
	 * Gets the bodega abastecedora.
	 *
	 * @return the bodega abastecedora
	 */
	public String getBodegaAbastecedora() {
		return bodegaAbastecedora;
	}

	/**
	 * Sets the bodega abastecedora.
	 *
	 * @param bodegaAbastecedora the new bodega abastecedora
	 */
	public void setBodegaAbastecedora(String bodegaAbastecedora) {
		this.bodegaAbastecedora = bodegaAbastecedora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
