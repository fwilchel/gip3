package com.ssl.jv.gip.web.mb.maestros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.exportarExcel;

@ManagedBean(name="maestroInventarioMB")
@SessionScoped
public class ListadoMaestroInventarioMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductosMB.class);

	private ProductosInventario filtro;
	private LazyDataModel<ProductosInventario> modelo;
	private String campoOrden;
	private SortOrder orden;
	private List<SelectItem> categorias;
	private ProductosInventario seleccionado;
	private List<Unidad> unidades;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@PostConstruct
	public void init(){
		filtro = new ProductosInventario();
		filtro.setPais(new Pais());
		filtro.setCategoriasInventario(new CategoriasInventario());
		filtro.setDesactivado(true);
		List<CategoriasInventario> categorias = maestrosEjb.consultarCategoriasInventario();
		this.categorias = new ArrayList<SelectItem>();
		for (CategoriasInventario ci:categorias){
			SelectItemGroup sig=new SelectItemGroup(ci.getNombre());
			this.categorias.add(sig);
			SelectItem hijos[]=new SelectItem[ci.getCategoriasInventarios().size()];
			for (int i=0; i<hijos.length; i++){
				hijos[i]=new SelectItem(ci.getCategoriasInventarios().get(i).getId(), ci.getCategoriasInventarios().get(i).getNombre());
			}
			sig.setSelectItems(hijos);
		}
		unidades= maestrosEjb.consultarUnidades();
	}
	
	public void loadProductos(){
		modelo = new LazyProductsDataModel();
	}

	public ProductosInventario getFiltro() {
		return filtro;
	}

	public void setFiltro(ProductosInventario filtro) {
		this.filtro = filtro;
	}
	public LazyDataModel<ProductosInventario> getModelo() {
		return modelo;
	}
	
	public void setModelo(LazyDataModel<ProductosInventario> modelo) {
		this.modelo = modelo;
	}
	
	public String getCampoOrden() {
		return campoOrden;
	}
	
	public void setCampoOrden(String campoOrden) {
		this.campoOrden = campoOrden;
	}
	
	public SortOrder getOrden() {
		return orden;
	}
	
	public void setOrden(SortOrder orden) {
		this.orden = orden;
	}
	
	public List<SelectItem> getCategorias() {
		return categorias;
	}
	
	public void setCategorias(List<SelectItem> categorias) {
		this.categorias = categorias;
	}
	
	public ProductosInventario getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ProductosInventario selccionado) {
		this.seleccionado = selccionado;
	}



	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}
	
	public void generateExcel(){
		try {
			this.hacerExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void hacerExcel() throws FileNotFoundException {

		List<ProductosInventario> listaProductos = ((LazyProductsDataModel)modelo).getDatos();
		short rownum;
		try {
			// create a new file
			// FileOutputStream out = new
			// FileOutputStream("reporteProveedores.xls");
			// create a new workbook
			HSSFWorkbook wb = new HSSFWorkbook();
			// creaar una nueva hoja con sus propiedades
			HSSFSheet s = wb.createSheet();
			s.setHorizontallyCenter(true);
			s.setMargin((short) 0, 0.1);
			s.setMargin((short) 1, 0.1);
			s.setMargin((short) 2, 1);
			s.setMargin((short) 3, 1);

			// declare a row object reference
			HSSFRow r = null;
			HSSFRow r1 = null, r2 = null, r3 = null, r4 = null, r5 = null, r6 = null, r7 = null;
			// declare a cell object reference
			HSSFCell c = null;
			HSSFCell c0 = null, c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null, c9 = null,c10 = null;
			HSSFCell c11 = null, c12 = null, c13 = null, c14 = null, c15 = null, c16 = null, c17 = null, c18 = null;
			
			HSSFCell c19 = null, c20 = null, c21 = null, c22 = null, c23 = null, c24= null, c25 = null, c26 = null;
			HSSFCell c27 = null, c28 = null, c29 = null, c30 = null, c31 = null, c32= null, c33 = null, c34 = null;
			HSSFCell c35 = null, c36 = null, c37 = null, c38 = null, c39 = null, c40= null, c41 = null, c42 = null;
			HSSFCell c43 = null, c44 = null, c45 = null, c46 = null, c47 = null, c48= null, c49 = null, c50 = null;
			HSSFCell c51 = null ,c52 = null, c53 = null;
			
			// create 3 cell styles
			HSSFCellStyle cs = wb.createCellStyle();
			HSSFCellStyle cs2 = wb.createCellStyle();
			HSSFCellStyle cs3 = wb.createCellStyle();
			HSSFCellStyle cs4 = wb.createCellStyle();

			HSSFDataFormat df = wb.createDataFormat();

			// Definicion de variables para el response
			HttpServletResponse response = null;
			FacesContext context = null;
			HttpServletRequest request = null;

			context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			response = (HttpServletResponse) external.getResponse();
			request = (HttpServletRequest) external.getRequest();
			// fin definicion de variables para el response

			// ========DEFINICION DE FUENTES=============
			// create 2 fonts objects
			HSSFFont f = wb.createFont(); // Tipo de letra para encabezado de
											// columnas
			HSSFFont f2 = wb.createFont();// Tipo deletra para contenido
			HSSFFont f3 = wb.createFont();// Tipo deletra para nombre reporte
			HSSFFont f4 = wb.createFont();// Tipo deletra para informacion
											// adicional

			// set font 1 to 12 point type
			f.setFontHeightInPoints((short) 11);
			// make it cafe
			f.setColor(HSSFFont.COLOR_NORMAL);
			// make it bold
			// arial is the default font
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// set font 2 to 10 point type
			f2.setFontHeightInPoints((short) 10);
			// make it negro
			f2.setColor(HSSFFont.COLOR_NORMAL);
			// make it normal
			f2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			// set font 2 to 10 point type
			f3.setFontHeightInPoints((short) 15);
			// make it negro
			f3.setColor((short) 0x10);
			// make it normal
			f3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// set font 2 to 10 point type
			f4.setFontHeightInPoints((short) 11);
			// make it negro
			f4.setColor((short) 0x10);
			// make it normal
			f4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// ==========DEFINICION DE ESTILOS PARA ARCHIVO XLS================
			// set cell stlye
			cs.setFont(f);
			// set the cell format
			cs.setDataFormat(df.getFormat("#,##0.0"));
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cs.setBorderBottom(cs2.BORDER_THIN);
			cs.setBorderLeft(cs2.BORDER_THIN);
			cs.setBorderRight(cs2.BORDER_THIN);
			cs.setBorderTop(cs2.BORDER_THIN);

			// set a thin border
			cs2.setBorderBottom(cs2.BORDER_THIN);
			cs2.setBorderLeft(cs2.BORDER_THIN);
			cs2.setBorderRight(cs2.BORDER_THIN);
			cs2.setBorderTop(cs2.BORDER_THIN);
			// set the cell format to text see HSSFDataFormat for a full list
			cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			// set the font
			cs2.setFont(f2);
			cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			cs3.setFont(f3);
			cs3.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

			cs4.setFont(f4);
			cs4.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

			// set the sheet name in Unicode
			wb.setSheetName(0, "maestro Inventario");

			// Fila 0
			r1 = s.createRow((short) 0);
			c11 = r1.createCell((short) 0);
			c11.setCellValue("MAESTRO INVENTARIO");
			//MergeCellsRecord merge = new MergeCellsRecord();
			//merge.addArea(0, (short) 0, 0, (short) 8);
			c11.setCellStyle(cs3);

			// Fila 1
			r2 = s.createRow((short) 1);

			// Fila 2
			r3 = s.createRow((short) 2);
			c12 = r3.createCell((short) 0);
			c12.setCellValue("CATEGORIA INVENTARIO: "
					+ filtro.getCategoriasInventario().getNombre());
			//MergeCellsRecord merge2 = new MergeCellsRecord();
			//merge2.addArea(2, (short) 0, 2, (short) 5);
			c12.setCellStyle(cs4);

			c15 = r3.createCell((short) 3);
			c15.setCellValue("PAIS: " + filtro.getPais().getNombre());
			//MergeCellsRecord merge5 = new MergeCellsRecord();
			//merge5.addArea(2, (short) 0, 2, (short) 5);
			c15.setCellStyle(cs4);

			// Fila 3
			r4 = s.createRow((short) 3);

			// Fila 4
			r5 = s.createRow((short) 4);
			c13 = r5.createCell((short) 0);
			c13.setCellValue("SKU : " + filtro.getSku());
			//MergeCellsRecord merge3 = new MergeCellsRecord();
			//merge3.addArea(4, (short) 0, 4, (short) 5);
			c13.setCellStyle(cs4);

			c14 = r5.createCell((short) 3);
			c14.setCellValue("NOMBRE PRODUCTO: " + filtro.getNombre());
			//MergeCellsRecord merge4 = new MergeCellsRecord();
			//merge4.addArea(4, (short) 7, 4, (short) 12);
			c14.setCellStyle(cs4);

			// Fila 5
			r6 = s.createRow((short) 5);

			// Fila 6
			r = s.createRow((short) 6);
			
			c0 = r.createCell((short) 0);
			c0.setCellValue("CATEGORIA");
			
			c1 = r.createCell((short) 1);
			c1.setCellValue("SUB - CATEGORIA");
			c2 = r.createCell((short) 2);
			c2.setCellValue("SKU");
			c3 = r.createCell((short) 3);
			c3.setCellValue("NOMBRE PRODUCTO");
			c4 = r.createCell((short) 4);
			c4.setCellValue("CODIGO POS");
			c5 = r.createCell((short) 5);
			c5.setCellValue("PAIS");
			c6 = r.createCell((short) 6);
			c6.setCellValue("ACTIVO");
			
			c7 = r.createCell((short) 7);
			c7.setCellValue("EAN");
			
			c8 = r.createCell((short) 8);
			c8.setCellValue("UNIDAD RECETA");
			
			c9 = r.createCell((short) 9);
			c9.setCellValue("CONVERSOR RECETA");
			
			c10 = r.createCell((short) 10);
			c10.setCellValue("CODIGO SAP");
			
			c11 = r.createCell((short) 11);
			c11.setCellValue("EAN 14");
			
			c12 = r.createCell((short) 12);
			c12.setCellValue("POSICION ARANCELARIA");
			
			c13 = r.createCell((short) 13);
			c13.setCellValue("IMPORTADO");
			
			c14 = r.createCell((short) 14);
			c14.setCellValue("NOMBRE PROD PROVEEDOR");
			
			c15 = r.createCell((short) 15);
			c15.setCellValue("DESCRIPCION");
			
			c16 = r.createCell((short) 16);
			c16.setCellValue("LARGO");
			
			c17 = r.createCell((short) 17);
			c17.setCellValue("ALTO");
			
			c18 = r.createCell((short) 18);
			c18.setCellValue("ANCHO");
			
			c19 = r.createCell((short) 19);
			c19.setCellValue("VOLUMEN");
			
			c20 = r.createCell((short) 20);
			c20.setCellValue("PESO NETO");
			
			c21 = r.createCell((short) 21);
			c21.setCellValue("PESO BRUTO");
			
			c22 = r.createCell((short) 22);
			c22.setCellValue("TIEMPO UTIL");

			c23 = r.createCell((short) 23);
			c23.setCellValue("MARCACION FECHA VENCIMIENTO");

			c24 = r.createCell((short) 24);
			c24.setCellValue("TIPO LOTE OIC");
			
			c25 = r.createCell((short) 25);
			c25.setCellValue("CANTIDAD POR EMBALAJE");
			
			c26 = r.createCell((short) 26);
			c26.setCellValue("LARGO EMBALAJE");
			
			c27 = r.createCell((short) 27);
			c27.setCellValue("ALTO EMBALAJE");

			c28 = r.createCell((short) 28);
			c28.setCellValue("ANCHO EMBALAJE");
			
			c29 = r.createCell((short) 29);
			c29.setCellValue("VOLUMEN EMBALAJE");
			
			c30 = r.createCell((short) 30);
			c30.setCellValue("PESO NETO EMBALAJE");
			
			c31 = r.createCell((short) 31);
			c31.setCellValue("PESO BRUTO EMBALAJE");
			
			c32 = r.createCell((short) 32);
			c32.setCellValue("CLAVE");

			c33 = r.createCell((short) 33);
			c33.setCellValue("CANT.CAJAS POR TENDIDO");
			
			c34 = r.createCell((short) 34);
			c34.setCellValue("CANT.MAX.TEND.POR PALLET");

			c35 = r.createCell((short) 35);
			c35.setCellValue("TOTAL CAJAS POR PALLET");

			c36 = r.createCell((short) 36);
			c36.setCellValue("PESO BRUTO MAX.PALLET");
			
			c37 = r.createCell((short) 37);
			c37.setCellValue("ALTURA MAX.PALLET");

			c38 = r.createCell((short) 38);
			c38.setCellValue("VOLUMEN PALLET");
			
			c39 = r.createCell((short) 39);
			c39.setCellValue("UNIDAD MIN.DESPACHO POR TENDIDO");
			
			c40 = r.createCell((short) 40);
			c40.setCellValue("UNIDAD EMBALAJE");
			
			c41 = r.createCell((short) 41);
			c41.setCellValue("RUTA FOTO");
			
			c42 = r.createCell((short) 42);
			c42.setCellValue("RUTA REG.SANITARIO");
			
			c43 = r.createCell((short) 43);
			c43.setCellValue("RUTA INFO.ETIQUETADO");
			
			c44 = r.createCell((short) 44);
			c44.setCellValue("RUTA_FICHA_TECNICA");

			c45 = r.createCell((short) 45);
			c45.setCellValue("RUTA MARCACION LOTE");
			
			c46 = r.createCell((short) 46);
			c46.setCellValue("RUTA FORMULA CUALI.CUANTI");
			
			c47 = r.createCell((short) 47);
			c47.setCellValue("RUTA CERTIFICACIONES");
			
			c48 = r.createCell((short) 48);
			c48.setCellValue("RUTA EXAMENES LAB.");
			
			
			c49 = r.createCell((short) 49);
			c49.setCellValue("UNIDAD VENTA");
			
			
			c50 = r.createCell((short) 50);
			c50.setCellValue("UNIDAD DESPACHO");
			
			
	
			c0.setCellStyle(cs);
			c1.setCellStyle(cs);
			c2.setCellStyle(cs);
			c3.setCellStyle(cs);
			c4.setCellStyle(cs);
			c5.setCellStyle(cs);
			c6.setCellStyle(cs);

			c7.setCellStyle(cs);
			c8.setCellStyle(cs);
			c9.setCellStyle(cs);
			
			c10.setCellStyle(cs);
			c11.setCellStyle(cs);
			c12.setCellStyle(cs);
			c13.setCellStyle(cs);
			c14.setCellStyle(cs);
			c15.setCellStyle(cs);
			c16.setCellStyle(cs);

			
			c17.setCellStyle(cs);
			c18.setCellStyle(cs);
			c19.setCellStyle(cs);
			c20.setCellStyle(cs);
			c21.setCellStyle(cs);
			c22.setCellStyle(cs);
			c23.setCellStyle(cs);
			c24.setCellStyle(cs);
			c25.setCellStyle(cs);
			c26.setCellStyle(cs);
			c27.setCellStyle(cs);
			c28.setCellStyle(cs);
			c29.setCellStyle(cs);
			c30.setCellStyle(cs);
			c31.setCellStyle(cs);
			c32.setCellStyle(cs);
			c33.setCellStyle(cs);
			c34.setCellStyle(cs);
			c35.setCellStyle(cs);
			c36.setCellStyle(cs);
			c37.setCellStyle(cs);
			c38.setCellStyle(cs);
			c39.setCellStyle(cs);
			c40.setCellStyle(cs);
			c41.setCellStyle(cs);
			c42.setCellStyle(cs);
			c43.setCellStyle(cs);
			c44.setCellStyle(cs);
			c45.setCellStyle(cs);
			c46.setCellStyle(cs);
			c47.setCellStyle(cs);
			c48.setCellStyle(cs);
			c49.setCellStyle(cs);
			c50.setCellStyle(cs);
		
			
			
			
			
			// Ultima fila
			r7 = s.createRow((short) listaProductos.size() + 9);
			c15 = r7.createCell((short) 0);
			Calendar fecha = new GregorianCalendar();
			String strFecha = fecha.get(Calendar.YEAR) + "-"
					+ ((fecha.get(Calendar.MONTH)) + 1) + "-"
					+ fecha.get(Calendar.DAY_OF_MONTH);
			c15.setCellValue("FECHA: " + strFecha);
			//MergeCellsRecord merge6 = new MergeCellsRecord();
			//merge6.addArea((listaProductos.size() + 9), (short) 1,
				//	(listaProductos.size() + 9), (short) 10);
			c15.setCellStyle(cs4);

			ProductosInventario objProducto = new ProductosInventario();
			
			int cont = 0;
			for (Integer row = new Integer(7); row < listaProductos.size() + 7; row++) {

				r = s.createRow(row.shortValue());

				objProducto = (ProductosInventario) listaProductos.get(cont);

				c = r.createCell((short) 0);
				c.setCellValue(objProducto.getCategoriasInventario()
						.getCategoriasInventario().getNombre());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 1);
				c.setCellValue(objProducto.getCategoriasInventario().getNombre());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 2);
				c.setCellValue(objProducto.getSku());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 3);
				c.setCellValue(objProducto.getNombre());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 4);
				c.setCellValue(objProducto.getCodigoPos());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 5);
				c.setCellValue(objProducto.getPais().getId());
				c.setCellStyle(cs2);
				
				c = r.createCell((short) 6);
				String activo = "";
				if (objProducto.getDesactivado()) {
					activo = "SI";
				} else {
					activo = "NO";
				}
				c.setCellValue(activo);
				c.setCellStyle(cs2);

				
				c = r.createCell((short) 7);
				c.setCellValue(objProducto.getCodigoBarrasUd());
				c.setCellStyle(cs2);
			
				// TODO : fredy.wilches :: validar cual de las  unidades es la que se muestra en esta celda
				c = r.createCell((short) 8);
				c.setCellValue(objProducto.getUnidadDespacho().getNombre());
				c.setCellStyle(cs2);
				
				
				c = r.createCell((short) 9);
				c.setCellValue(objProducto.getConversorR());
				c.setCellStyle(cs2);
				
				if(objProducto.getProductosInventarioComext() != null){
					c = r.createCell((short) 10);
					c.setCellValue(objProducto.getProductosInventarioComext().getCodigoSap());
					c.setCellStyle(cs2);
					
					
					c = r.createCell((short) 11);
					c.setCellValue(objProducto.getProductosInventarioComext().getEan14());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 12);
					c.setCellValue(objProducto.getProductosInventarioComext().getPosicionArancelaria());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 13);
					String activo2 = "";
					if (objProducto.getProductosInventarioComext().getImportado()) {
						activo2 = "SI";
					} else {
						activo2 = "NO";
					}
					c.setCellValue(activo2);
					c.setCellStyle(cs2);
					
					
					
					c = r.createCell((short) 14);
					c.setCellValue(objProducto.getProductosInventarioComext().getNombrePrdProveedor());
					c.setCellStyle(cs2);
			
					c = r.createCell((short) 15);
					c.setCellValue(objProducto.getProductosInventarioComext().getDescripcion());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 16);
					c.setCellValue(objProducto.getProductosInventarioComext().getLargo().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 17);
					c.setCellValue(objProducto.getProductosInventarioComext().getAlto().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 18);
					c.setCellValue(objProducto.getProductosInventarioComext().getAncho().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 19);
					c.setCellValue(objProducto.getProductosInventarioComext().getVolumen().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 20);
					c.setCellValue(objProducto.getProductosInventarioComext().getPesoNeto().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 21);
					c.setCellValue(objProducto.getProductosInventarioComext().getPesoBruto().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 22);
					c.setCellValue(objProducto.getProductosInventarioComext().getTiempoUtil().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 23);
					c.setCellValue(objProducto.getProductosInventarioComext().getMarcacionFechaVencimiento());
					c.setCellStyle(cs2);
					
					
					c = r.createCell((short) 24);
					c.setCellValue(objProducto.getProductosInventarioComext().getTipoLoteoic().getId());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 25);
					c.setCellValue(objProducto.getProductosInventarioComext().getCantidadXEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					
					c = r.createCell((short) 26);
					c.setCellValue(objProducto.getProductosInventarioComext().getLargoEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 27);
					c.setCellValue(objProducto.getProductosInventarioComext().getAltoEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 28);
					c.setCellValue(objProducto.getProductosInventarioComext().getAnchoEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 29);
					c.setCellValue(objProducto.getProductosInventarioComext().getVolumenEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 30);
					c.setCellValue(objProducto.getProductosInventarioComext().getPesoNetoEmbalaje().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 31);
					c.setCellValue(objProducto.getProductosInventarioComext().getPesoBrutoEmbalaje().doubleValue());
					c.setCellStyle(cs2);				
					
					c = r.createCell((short) 32);
					c.setCellValue(objProducto.getProductosInventarioComext().getClave());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 33);
					c.setCellValue(objProducto.getProductosInventarioComext().getCantCajasXTendido().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 34);
					c.setCellValue(objProducto.getProductosInventarioComext().getCantMaxTendXPallet().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 35);
					c.setCellValue(objProducto.getProductosInventarioComext().getTotalCajasXPallet().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 36);
					c.setCellValue(objProducto.getProductosInventarioComext().getPesoBrutoMaxPallet().doubleValue());
					c.setCellStyle(cs2);
				
					c = r.createCell((short) 37);
					c.setCellValue(objProducto.getProductosInventarioComext().getAlturaMaxPallet().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 38);
					c.setCellValue(objProducto.getProductosInventarioComext().getVolumenPallet().doubleValue());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 39);
					c.setCellValue(objProducto.getProductosInventarioComext().getUnidadMinDespachoXTendido() != null ? 
							objProducto.getProductosInventarioComext().getUnidadMinDespachoXTendido().doubleValue() : 0);
					c.setCellStyle(cs2);
					
					
					//se debe cambiar por unidad
					c = r.createCell((short) 40);
					c.setCellValue(objProducto.getProductosInventarioComext().getUnidadEmbalaje() != null ?
							objProducto.getProductosInventarioComext().getUnidadEmbalaje().getId() : 0);
					c.setCellStyle(cs2);
					
					
					c = r.createCell((short) 41);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaFoto());
					c.setCellStyle(cs2);
					
					
					c = r.createCell((short) 42);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaRegSanitario());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 43);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaInfoEtiquetado());
					c.setCellStyle(cs2);
				
					
					c = r.createCell((short) 44);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaFichaTecnica());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 45);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaMarcacionLote());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 46);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaFormulaCualiCuanti());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 47);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaCertificaciones());
					c.setCellStyle(cs2);
					
					c = r.createCell((short) 48);
					c.setCellValue(objProducto.getProductosInventarioComext().getRutaExamenesLab());
					c.setCellStyle(cs2);
					
					// TODO : fredy.wilches :: validar si esta es la unidad que va en este caso
					c = r.createCell((short) 49);
					c.setCellValue(objProducto.getProductosInventarioComext().getUnidadEmbalaje() != null ?
							objProducto.getProductosInventarioComext().getUnidadEmbalaje().getNombre() : null);
					c.setCellStyle(cs2);
					
					// TODO : fredy.wilches :: validar si esta es la unidad que va en este caso
					c = r.createCell((short) 50);
					c.setCellValue(objProducto.getProductosInventarioComext().getUnidadMedida());
				}
				c.setCellStyle(cs2);
				
			
				
				cont += 1;

				// make this column a bit wider
				s.setColumnWidth((short) (0),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (1),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (2),
						(short) ((20 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (3),
						(short) ((50 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (4),
						(short) ((40 * 8) / ((double) 1 / 20)));
				s.setColumnWidth((short) (5),
						(short) ((20 * 8) / ((double) 1 / 20)));
				s.setColumnWidth((short) (6),
						(short) ((20 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (7),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (8),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (9),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (10),
						(short) ((40 * 8) / ((double) 1 / 20)));
				

				s.setColumnWidth((short) (11),
						(short) ((40 * 8) / ((double) 1 / 20)));

				s.setColumnWidth((short) (12),
						(short) ((50 * 8) / ((double) 1 / 20)));

				s.setColumnWidth((short) (13),
						(short) ((30 * 8) / ((double) 1 / 20)));

				s.setColumnWidth((short) (14),
						(short) ((55 * 8) / ((double) 1 / 20)));

				s.setColumnWidth((short) (15),
						(short) ((55 * 8) / ((double) 1 / 20)));

				
				s.setColumnWidth((short) (16),
						(short) ((40 * 8) / ((double) 1 / 20)));

				
				
				s.setColumnWidth((short) (17),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (18),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (19),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (20),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (21),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (22),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (23),
						(short) ((60 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (24),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (25),
						(short) ((50 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (26),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (27),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (28),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (29),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (30),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (31),
						(short) ((50 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (32),
						(short) ((30 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (33),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (34),
						(short) ((60 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (35),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (36),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (37),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (38),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (39),
						(short) ((65 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (40),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (41),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (42),
						(short) ((50 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (43),
						(short) ((50 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (44),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (45),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (46),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (47),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (48),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (49),
						(short) ((55 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (50),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				
				s.setColumnWidth((short) (51),
						(short) ((40 * 8) / ((double) 1 / 20)));
				
				s.setColumnWidth((short) (52),
						(short) ((40 * 8) / ((double) 1 / 20)));
			}

			// end draw thick black border
			// demonstrate adding/naming and deleting a sheet
			// create a sheet, set its title then delete it
			s = wb.createSheet();
			wb.setSheetName(1, "DeletedSheet");
			wb.removeSheetAt(1);
			// end deleted sheet

			// Envia por doGet el workbook y lo abre.
			exportarExcel pe = new exportarExcel();
			try {
				pe.doGet(request, response, wb, "maestroInventario");
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			context.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class LazyProductsDataModel extends LazyDataModel<ProductosInventario>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 283497341126330045L;
		private List<ProductosInventario> datos = null;
		
		@Override
	    public Object getRowKey(ProductosInventario pi) {
	        return pi.getId();
	    }
		
		@Override
	    public ProductosInventario getRowData(String rowKey) {
	        for(ProductosInventario pi : datos) {
	            if(pi.getId().toString().equals(rowKey))
	                return pi;
	        }
	        return null;
	    }
		
		public List<ProductosInventario> getDatos(){
			return datos;
		}

		
		@Override
	    public List<ProductosInventario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
			campoOrden = sortField;
			orden=sortOrder;
			Object rta[]=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, true);
			this.setRowCount(((Long)rta[0]).intValue());
			rta=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, false);
			datos=(List<ProductosInventario>)rta[1];
			return datos;
		}

	}
}
