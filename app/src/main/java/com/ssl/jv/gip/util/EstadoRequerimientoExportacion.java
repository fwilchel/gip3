package com.ssl.jv.gip.util;

public enum EstadoRequerimientoExportacion {
	
	Nodiligenciado(0, "No diligenciado"), EsperandoAprobacion(1, "Esperando Aprobacion"), Aprobado(2,"Aprobado");

		  private Integer codigo;
		  private String nombre;

		  private EstadoRequerimientoExportacion(Integer codigo) {
		    this.codigo = codigo;
		  }

		  private EstadoRequerimientoExportacion(Integer codigo, String nombre) {
		    this.codigo = codigo;
		    this.nombre = nombre;
		  }

		  public Integer getCodigo() {
		    return codigo;
		  }

		  public String getNombre() {
		    return nombre;
		  }


}
