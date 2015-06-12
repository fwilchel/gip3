package com.ssl.jv.gip.util;

public enum EstadoRequerimientoExportacion {
	
	Nodiligenciado(0, "No diligenciado"), EsperandoAprobacion(1, "Esperando Aprobacion"), Aprobado(2,"Aprobado");

		  private long codigo;
		  private String nombre;

		  private EstadoRequerimientoExportacion(long codigo) {
		    this.codigo = codigo;
		  }

		  private EstadoRequerimientoExportacion(long codigo, String nombre) {
		    this.codigo = codigo;
		    this.nombre = nombre;
		  }

		  public long getCodigo() {
		    return codigo;
		  }

		  public String getNombre() {
		    return nombre;
		  }


}
