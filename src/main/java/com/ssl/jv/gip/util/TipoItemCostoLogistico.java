package com.ssl.jv.gip.util;

public enum TipoItemCostoLogistico {
	
	NO_APLICA(0,"No aplica"), MULTIPLICA_CONTENEDORES_20(1, "Multiplica por contenedores de 20"), MULTIPLICA_CONTENEDORES_40(2, "Multiplica por contenedores de 40"), 
	MULTIPLICA_PALLET(3, "Multiplica por Pallets"), MULTIPLICA_CATEGORIA(4,"Multiplica por categoría"), MULTIPLICA_SAES(5,"Multiplica por Número SAE"), MULTIPLICA_FOB(6, "Multiplica por el FOB"),
	POR_RANGOS(7,"Según rangos de peso"), MULTIPLICA_PESO(8,"Multiplica por peso"), MULTIPLICA_CONTENEDOR(9,"Multiplica por contenedores"), 
	A_PUERTO_NAL(10,"A puerto nacional"), A_PUERTO_INTERNAL(11,"Puerto Nal a Puerto Internacional");
	
	private Integer id;
	private String descripcion;
	
	private TipoItemCostoLogistico(Integer id, String descripcion){
		this.id=id;
		this.descripcion=descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
