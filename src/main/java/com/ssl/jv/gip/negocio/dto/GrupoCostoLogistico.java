package com.ssl.jv.gip.negocio.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ssl.jv.gip.util.ECampoAcumula;

public class GrupoCostoLogistico implements Comparable{

	private String categoria;
	private List<CostoLogisticoDTO> costos=new ArrayList<CostoLogisticoDTO>();
	
	public GrupoCostoLogistico(String categoria) {
		super();
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<CostoLogisticoDTO> getCostos() {
		return costos;
	}

	public void setCostos(List<CostoLogisticoDTO> costos) {
		this.costos = costos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((costos == null) ? 0 : costos.hashCode());
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
		GrupoCostoLogistico other = (GrupoCostoLogistico) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		return true;
	}
	
	public void addCosto(CostoLogisticoDTO cl){
		this.costos.add(cl);
	}

	@Override
	public int compareTo(Object o) {
		GrupoCostoLogistico g=(GrupoCostoLogistico)o;
		return this.categoria.compareTo(g.getCategoria());
	}
	
	public BigDecimal getTotal(){
		BigDecimal v=new BigDecimal(0);
		for (CostoLogisticoDTO c:this.costos){
			if (c.isSeleccionado()){
				v=v.add(new BigDecimal(c.getId().getValor()));
			}
		}
		return v;
	}

	public BigDecimal getTotalFOB(){
		BigDecimal v=new BigDecimal(0);
		for (CostoLogisticoDTO c:this.costos){
			if (c.isSeleccionado() && c.getId().getCampoAcumula().equals(ECampoAcumula.FOB.name())){
				v=v.add(new BigDecimal(c.getId().getValor()));
			}
		}
		return v;
	}

	public BigDecimal getTotalFletes(){
		BigDecimal v=new BigDecimal(0);
		for (CostoLogisticoDTO c:this.costos){
			if (c.isSeleccionado() && c.getId().getCampoAcumula().equals(ECampoAcumula.FLETES.name())){
				v=v.add(new BigDecimal(c.getId().getValor()));
			}
		}
		return v;
	}
	
	public BigDecimal getTotalSeguros(){
		BigDecimal v=new BigDecimal(0);
		for (CostoLogisticoDTO c:this.costos){
			if (c.isSeleccionado() && c.getId().getCampoAcumula().equals(ECampoAcumula.SEGURO.name())){
				v=v.add(new BigDecimal(c.getId().getValor()));
			}
		}
		return v;
	}
	
}
