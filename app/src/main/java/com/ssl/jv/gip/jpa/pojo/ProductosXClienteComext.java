package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the productos_x_cliente_comext database table.
 *
 */
@Entity
@Table(name = "productos_x_cliente_comext")
@NamedQueries(value = {
  @NamedQuery(name = ProductosXClienteComext.PRODUCTOS_X_CLIENTE_COM_EXT_FIND_ALL, query = "SELECT p FROM ProductosXClienteComext p"),
  @NamedQuery(name = ProductosXClienteComext.PRODUCTOS_X_CLIENTE_SKU, query = "SELECT p FROM ProductosXClienteComext p WHERE p.pk.idCliente= :idCliente AND p.productosInventario.sku= :sku"),
  @NamedQuery(name = ProductosXClienteComext.BUSCAR_POR_PRODUCTO_Y_CLIENTE, query = "SELECT p FROM ProductosXClienteComext p WHERE p.pk.idCliente= :idCliente AND p.pk.idProducto = :idProducto")
})
public class ProductosXClienteComext implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -2058658769141993022L;
  public static final String PRODUCTOS_X_CLIENTE_COM_EXT_FIND_ALL = "ProductosXClienteComext.findAll";
  public static final String PRODUCTOS_X_CLIENTE_SKU = "ProductosXClienteComext.findByClienteSku";
  public static final String BUSCAR_POR_PRODUCTO_Y_CLIENTE = "buscarPorProductoYCliente";

  @EmbeddedId
  private ProductosXClienteComextPK pk;

  private Boolean activo;

  private BigDecimal descuentoxproducto;

  @Column(name = "id")
  private Long id;

  @Column(name = "id_moneda")
  private String idMoneda;

  private BigDecimal iva;

  @Column(name = "otros_descuentos")
  private BigDecimal otrosDescuentos;

  private BigDecimal precio;

  @Column(name = "reg_sanitario")
  private String regSanitario;

  // bi-directional many-to-one association to Cliente
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
  private Cliente cliente;

  // bi-directional many-to-one association to ProductosInventario
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
  private ProductosInventario productosInventario;

  public ProductosXClienteComext() {
  }

  public ProductosXClienteComextPK getPk() {
    return this.pk;
  }

  public void setPk(ProductosXClienteComextPK pk) {
    this.pk = pk;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public BigDecimal getDescuentoxproducto() {
    return this.descuentoxproducto;
  }

  public void setDescuentoxproducto(BigDecimal descuentoxproducto) {
    this.descuentoxproducto = descuentoxproducto;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdMoneda() {
    return this.idMoneda;
  }

  public void setIdMoneda(String idMoneda) {
    this.idMoneda = idMoneda;
  }

  public BigDecimal getIva() {
    return this.iva;
  }

  public void setIva(BigDecimal iva) {
    this.iva = iva;
  }

  public BigDecimal getOtrosDescuentos() {
    return this.otrosDescuentos;
  }

  public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
    this.otrosDescuentos = otrosDescuentos;
  }

  public BigDecimal getPrecio() {
    return this.precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

  public String getRegSanitario() {
    return this.regSanitario;
  }

  public void setRegSanitario(String regSanitario) {
    this.regSanitario = regSanitario;
  }

  public Cliente getCliente() {
    return this.cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public ProductosInventario getProductosInventario() {
    return this.productosInventario;
  }

  public void setProductosInventario(ProductosInventario productosInventario) {
    this.productosInventario = productosInventario;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pk == null) ? 0 : pk.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ProductosXClienteComext other = (ProductosXClienteComext) obj;
    if (pk == null) {
      if (other.pk != null) {
        return false;
      }
    } else if (!pk.equals(other.pk)) {
      return false;
    }
    return true;
  }

}
