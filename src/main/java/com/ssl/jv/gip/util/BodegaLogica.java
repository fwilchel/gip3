package com.ssl.jv.gip.util;

public enum BodegaLogica {

  DEFAULT(0L), DEVOLUCION(1L), DANANO(6L);

  private Long codigo;

  private BodegaLogica(Long codigo) {
    this.codigo = codigo;
  }

  public Long getCodigo() {
    return codigo;
  }

}
