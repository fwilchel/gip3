package com.ssl.jv.gip.web.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>
 * Title: GIP
 * </p>
 *
 * <p>
 * Description: GIP
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Fredy Giovanny Wilches L�pez
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class Utilidad {
  // private static List listaUbicaciones = new ArrayList();

  public final static String stringFormat(String sql, String[] params) {

    // String resultado = sql;
    String patron = "";

    String temporal = "";

    for (int i = 0; i < params.length; i++) {

      // se construye el patron
      patron = "\\{";
      patron += Integer.toString(i);
      patron += "}";

      // compilamos el patron
      Pattern expresion = Pattern.compile(patron);
      // creamos el Matcher a partir del patron, la cadena como parametro
      Matcher encaja = expresion.matcher(sql);
      // invocamos el metodo replaceAll
      temporal = encaja.replaceAll(params[i]);

      sql = temporal;

    }

    return temporal;
  }

  /*
   * Metodo que Convierte una un string con formato de fecha, a un Calendar
   * 
   * @return calendar con la fecha @pre String tiene formato de fecha
   * yyyy-mm-dd
   */
  public static Calendar convertirStringToCalendar(String fechaS) {
    Calendar calendarEsperada = Calendar.getInstance();
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      sdf.applyPattern("yyyy-MM-dd");
      Date dateEsperada = sdf.parse(fechaS);
      calendarEsperada.setTime(dateEsperada);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return calendarEsperada;
  }

  /*
   * Metodo que Convierte una un string con formato de fecha, a un timestamp
   * 
   * @return Timestmp con la fecha @pre String tiene formato de fecha
   * yyyy-mm-dd
   */
  public static Timestamp convertirStringToTimestamp(String fechaS) {
    Timestamp timestampEsperada = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      sdf.applyPattern("yyyy-MM-dd");
      Date dateEsperada = sdf.parse(fechaS);
      timestampEsperada = new Timestamp(dateEsperada.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return timestampEsperada;
  }

  /*
   * Metodo que Convierte una un Date a un timestamp @return Timestmp con la
   * fecha @pre Date instanciado
   */
  public static Timestamp convertirDateTotimestamp(Date fechaActual) {
    Timestamp timestampGeneracion = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      sdf.applyPattern("yyyy-MM-dd");
      String mientras = sdf.format(fechaActual);
      Date dateGeneracion = sdf.parse(mientras);
      timestampGeneracion = new Timestamp(dateGeneracion.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return timestampGeneracion;
  }

  /*
   * Metodo que Convierte una un timestamp a un string @return String con
   * fotmato yyyy-mm-dd @pre Timestamp diferente de null
   */
  public static String convertirTimestampToString(Timestamp ts) {
    String fechaStringGenerado = "";

    if (ts == null) {

      System.out.println("llega nulo");
    }
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    fechaStringGenerado = ft.format(ts);
    return fechaStringGenerado;
  }

  /*
   * Metodo que Convierte una un timestamp a un string @return String con
   * fotmato yyyy-mm-dd @pre Timestamp diferente de null
   */
  public static Timestamp convertirDateTotimestampCompleto(Date fechaActual) {
    Timestamp timestampGeneracion = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(
              "yyyy-MM-dd hh:mm:ss aaa");
      sdf.applyPattern("yyyy-MM-dd hh:mm:ss aaa");
      String mientras = sdf.format(fechaActual);
      Date dateGeneracion = sdf.parse(mientras);
      timestampGeneracion = new Timestamp(dateGeneracion.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return timestampGeneracion;
  }

  /*
   * metodo que configura el tipo de respuesta del servlet cuando se genera el
   * pdf
   */
  public static HttpServletResponse configureResponse(
          HttpServletResponse response, String fileName) {

    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control",
            "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    response.setContentType("application/pdf");
    response.addHeader("Content-disposition", "attachment; filename=\""
            // + fileName + ".pdf\"");
            + fileName);

    // response.getWriter()
    return response;
  }

  public static HttpServletResponse configureResponse2(
          HttpServletResponse response, String fileName) {

    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control",
            "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    response.setContentType("application/txt");
    response.addHeader("Content-disposition", "attachment; filename=\""
            + fileName + ".txt\"");

    // response.getWriter()
    return response;
  }

  /*
   * metodo que configura el tipo de respuesta del servlet cuando se genera el
   * pdf
   */
  public static HttpServletResponse configureResponse3(
          HttpServletResponse response, String fileName) {

    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control",
            "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    response.setContentType("application/pdf");
    response.addHeader("Content-disposition", "attachment; filename=\""
            + fileName + ".pdf\"");
		// + fileName);

    // response.getWriter()
    return response;
  }

  /* metodo que configura el documento pdf */
  /*
   * public static Document configureDocument(float width, float height, float
   * margin) {
   * 
   * Document document = new Document();
   * 
   * float widhtPage = (width / 2.54f) * 72f; float heightPage = (height /
   * 2.54f) * 72f; document.setPageSize(new Rectangle(widhtPage, heightPage));
   * float marginPage = (margin / 2.54f) * 72f;
   * document.setMargins(marginPage, marginPage, marginPage, marginPage);
   * 
   * return document; }
   */

  /* metodo que devuelve una celda pdf cuando el documento es una tabla */

  /*
   * public static PdfPCell makeCell(String text, int hAlignment, Font font,
   * Rectangle border) {
   * 
   * PdfPCell celda = new PdfPCell(new Paragraph(text, font));
   * celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
   * celda.setHorizontalAlignment(hAlignment);
   * celda.cloneNonPositionParameters(border); celda.setNoWrap(true);
   * celda.setPaddingTop(4f); celda.setPaddingBottom(4f);
   * celda.setPaddingLeft(2f); celda.setPaddingRight(2f);
   * 
   * return celda; }
   */
  /**
   * Esta funcion construye de manera recursiva la lista de categor�as y subcategor�as a partir de arrCategorias
   */
  /*
   * public static ArrayList getArrCategorias(int intPadre, String strNivel,
   * ArrayList arrCategorias) { int i; int intId = 0; ArrayList arrL = new
   * ArrayList(); CategoriaInventario CategoriaAuxiliar = null;
   * 
   * for (i = 0; i < arrCategorias.size(); i++) { if (((CategoriaInventario)
   * arrCategorias.get(i)) .getObjCategoriaInventario().getIntId() ==
   * intPadre) { CategoriaAuxiliar = new CategoriaInventario(); intId =
   * ((CategoriaInventario) arrCategorias.get(i)).getIntId();
   * CategoriaAuxiliar.setIntId(intId);
   * CategoriaAuxiliar.setStrNombre(strNivel + ((CategoriaInventario)
   * arrCategorias.get(i)) .getStrNombre()); arrL.add(CategoriaAuxiliar);
   * arrL.addAll(getArrCategorias(intId, strNivel + ".  ", arrCategorias)); }
   * } return arrL; }
   */
  /**
   * Este m�todo formatea en una lista de selecci�n las categor�as de inventario de manera tal que aparezcan indentadas de acuerdo al nivel de jerarqu�a en el que se encuentren
   *
   * @param arrCategorias Arrego que contiene las categor�as de inventario
   * @param blnTodos Indica si se debe incluir como �tem de la lista de selecci�n el valor TODOS
   * @return Lista de selecci�n
   */
  /*
   * public static SelectItem[] getListaCategoria(ArrayList arrCategorias,
   * boolean blnTodos) {
   * 
   * ArrayList arrL = getArrCategorias(0, "", arrCategorias); int i, j; int
   * intSize;
   * 
   * if (blnTodos) { intSize = arrL.size() + 1; j = 1; } else { intSize =
   * arrL.size(); j = 0; }
   * 
   * SelectItem[] listaCategoria = new SelectItem[intSize]; if (blnTodos)
   * listaCategoria[0] = new SelectItem(new Integer(0), new String( "TODOS"));
   * 
   * for (i = 0; i < arrL.size(); i++) { listaCategoria[j] = new
   * SelectItem(new Integer( ((CategoriaInventario) arrL.get(i)).getIntId()),
   * new String(((CategoriaInventario) arrL.get(i)) .getStrNombre())); j++; }
   * return listaCategoria; }
   */
  /*
   * public static ArrayList getListaUbicaciones() {
   * 
   * final PostgresUsuarioPorGeografiaDAO PostUsuariosPorGeografias; final
   * PostgresUbicacionDAO PostUbicacionDao; final PostgresDAOFactory
   * fabricaPostgres = new PostgresDAOFactory(); ; PostUsuariosPorGeografias =
   * fabricaPostgres.getUsuarioPorGeografiaDAO(); PostUbicacionDao =
   * fabricaPostgres.getUbicacionDAO();
   * 
   * try { listaUbicaciones.clear(); ExternalContext context =
   * FacesContext.getCurrentInstance() .getExternalContext(); HttpSession
   * session = (HttpSession) context.getSession(true); Usuario objUsuario =
   * new Usuario(); objUsuario = (Usuario)
   * session.getAttribute("usuarioLogin");
   * 
   * Object parametros[] = new Object[2]; parametros[0] =
   * "buscar_usuario_pais"; parametros[1] = objUsuario.getSrtId(); // id
   * usuario
   * 
   * ArrayList arrUsuariosXgeografias = new ArrayList(); Ubicacion ubicacion =
   * new Ubicacion(); UsuarioPorGeografia usuarioXgeografia = new
   * UsuarioPorGeografia(); arrUsuariosXgeografias = PostUsuariosPorGeografias
   * .consultarLista(parametros);
   * 
   * for (int i = 0; i < arrUsuariosXgeografias.size(); i++) {
   * 
   * usuarioXgeografia = (UsuarioPorGeografia) arrUsuariosXgeografias .get(i);
   * ubicacion = usuarioXgeografia.getObjUbicacion();
   * 
   * ubicacion = PostUbicacionDao.consultar(ubicacion);
   * 
   * listaUbicaciones.add(ubicacion); }
   * 
   * } catch (DataBaseException e) { e.printStackTrace(); } catch
   * (SQLException e) { e.printStackTrace(); } catch (InstantiationException
   * e) { e.printStackTrace(); } catch (ClassNotFoundException e) {
   * e.printStackTrace(); } catch (IllegalAccessException e) {
   * e.printStackTrace(); } return listaUbicaciones; }
   */

  /*
   * public static double convertirMonedas(String strIdMonedaOrigen, String
   * strIdMonedaDestino, Timestamp dtmFecha) { double dblFactor = 0.0; final
   * PostgresConversionDAO PostConversionDao; final PostgresDAOFactory
   * fabricaPostgres = new PostgresDAOFactory();
   * 
   * PostConversionDao = fabricaPostgres.getConversionDAO(); Conversion
   * objConversion = new Conversion(); // moneda de origen
   * objConversion.setStrIdSourceCurrency(strIdMonedaOrigen); // moneda de
   * destino objConversion.setStrIdDestinationCurrency(strIdMonedaDestino); //
   * Fecha a la cual se quiere hacer la conversi�n
   * objConversion.setTmcPgCatalog(dtmFecha); try { objConversion =
   * PostConversionDao.consultar(objConversion); dblFactor =
   * objConversion.getDblSourceDestinationExchangeRate(); } catch
   * (DataBaseException e) { e.printStackTrace(); } catch (SQLException e) {
   * e.printStackTrace(); } catch (InstantiationException e) {
   * e.printStackTrace(); } catch (ClassNotFoundException e) {
   * e.printStackTrace(); } catch (IllegalAccessException e) {
   * e.printStackTrace(); } return dblFactor; }
   */
  /**
   * Encripta un String utilizando el algoritmo Triple DES
   *
   * @param clearText Texto en ckaro a encriptar
   * @return texto encriptado en base 64
   */
  public static String cifrar(String clearText) {
    // Tres claves de 64 bits tipo DES
    // Por lo tanto este algoritmo es compatible con DES

    String cipherTextB64 = "";
    try {
      byte[] secret = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
        0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x01, 0x02,
        0x03, 0x04, 0x05, 0x06, 0x07, 0x08};

      Key key = null;
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
      key = skf.generateSecret(new DESedeKeySpec(secret));

      // Necesitamos un cifrador
      Cipher cipher = Cipher.getInstance("DESede");
      // Ciframos el texto en claro
      cipher.init(Cipher.ENCRYPT_MODE, key);

      byte cipherText[] = cipher.doFinal(clearText.getBytes());
      // Codificamos el texto cifrado en base 64

      BASE64Encoder base64encoder = new BASE64Encoder();
      cipherTextB64 = base64encoder.encode(cipherText);
    } catch (NoSuchAlgorithmException nsae) {
      nsae.printStackTrace();
    } catch (InvalidKeyException ike) {
      ike.printStackTrace();
    } catch (NoSuchPaddingException nspe) {
      nspe.printStackTrace();
    } catch (IllegalBlockSizeException ibse) {
      ibse.printStackTrace();
    } catch (BadPaddingException bpe) {
      bpe.printStackTrace();
    } catch (InvalidKeySpecException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Retornamos el texto cifrado en BASE64
    return cipherTextB64;

  }

  private static byte[] transformByte(char[] hexa) {
    int length = hexa.length / 2;
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; i++) {
      // CHECKSTYLE:OFF
      int alto = Character.digit(hexa[i * 2], 16);
      int bajo = Character.digit(hexa[i * 2 + 1], 16);
      int valor = (alto << 4) | bajo;
      if (valor > 127) {
        valor -= 256;
      }
      // CHECKSTYLE:ON
      bytes[i] = (byte) valor;
    }
    return bytes;
  }

  public static String descifrar(String textIni) {
		// Tres claves de 64 bits tipo DES
    // Por lo tanto este algoritmo es compatible con DES

    // byte[] encriptada = transformByte(text.toCharArray());
    String cipherTextB64 = "";
    try {
      byte[] secret = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
        0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x01, 0x02,
        0x03, 0x04, 0x05, 0x06, 0x07, 0x08};

      BASE64Decoder base64decoder = new BASE64Decoder();
      byte encriptada[] = base64decoder.decodeBuffer(textIni);

      Key key = null;
      SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
      key = skf.generateSecret(new DESedeKeySpec(secret));

      // Necesitamos un cifrador
      Cipher cipher = Cipher.getInstance("DESede");
      // Ciframos el texto en claro
      cipher.init(Cipher.DECRYPT_MODE, key);

      byte cipherText[] = cipher.doFinal(encriptada);
      // Codificamos el texto cifrado en base 64
      cipherTextB64 = new String(cipherText);

    } catch (NoSuchAlgorithmException nsae) {
      nsae.printStackTrace();
    } catch (InvalidKeyException ike) {
      ike.printStackTrace();
    } catch (IllegalBlockSizeException ibse) {
      ibse.printStackTrace();
    } catch (BadPaddingException bpe) {
      bpe.printStackTrace();
    } catch (NoSuchPaddingException nspe) {
      nspe.printStackTrace();
    } catch (IOException ibse) {
      ibse.printStackTrace();
    } catch (InvalidKeySpecException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Retornamos el texto cifrado en BASE64
    return cipherTextB64;

  }

  /**
   * public static void main(String args[]){ String constrase�aSinEncriptar=args[0]; String contrase�a=Utilidad.encriptar(constrase�aSinEncriptar); System.out.println(">"+contrase�a+"<"); }
   */

  /*
   * 
   * public static ArrayList consultarListaAprobacion() { ArrayList lista =
   * new ArrayList(); final PostgresAprobacionDAO PostAprobacionDao; final
   * PostgresDAOFactory fabricaPostgres = new PostgresDAOFactory();
   * 
   * PostAprobacionDao = fabricaPostgres.getAprobacionDAO();
   * 
   * try { lista = PostAprobacionDao.consultarLista(null); } catch
   * (DataBaseException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } catch (SQLException e) { // TODO Auto-generated
   * catch block e.printStackTrace(); } catch (InstantiationException e) { //
   * TODO Auto-generated catch block e.printStackTrace(); } catch
   * (ClassNotFoundException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
   * Auto-generated catch block e.printStackTrace(); } return lista; }
   */

  /*
   * public static Aprobacion obtenerCargoAprobacion(int id) { final
   * PostgresAprobacionDAO PostAprobacionDao; final PostgresDAOFactory
   * fabricaPostgres = new PostgresDAOFactory(); Aprobacion aprobar = new
   * Aprobacion(); PostAprobacionDao = fabricaPostgres.getAprobacionDAO();
   * aprobar.setIntId(id); try { aprobar =
   * PostAprobacionDao.consultar(aprobar); } catch (DataBaseException e) { //
   * TODO Auto-generated catch block e.printStackTrace(); } catch
   * (SQLException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } catch (InstantiationException e) { // TODO
   * Auto-generated catch block e.printStackTrace(); } catch
   * (ClassNotFoundException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
   * Auto-generated catch block e.printStackTrace(); } return aprobar; }
   */
  public static String convertirCalendartoString(Calendar actualday) {

    int year = actualday.get(Calendar.YEAR);
    int month = actualday.get(Calendar.MONTH) + 1;
    int day = actualday.get(Calendar.DAY_OF_MONTH);
    String month1 = "", day1 = "";
    if (month < 10) {
      month1 = "0" + month;
    } else {
      month1 = "" + month;
    }
    if (day < 10) {
      day1 = "0" + day;
    } else {
      day1 = "" + day;
    }
    String fechaventa = year + "-" + month1 + "-" + day1;
    return fechaventa;
  }

  public static String quitaEspacios(String texto) {
    java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
    StringBuilder buff = new StringBuilder();
    while (tokens.hasMoreTokens()) {
      buff.append(" ").append(tokens.nextToken());
    }
    return buff.toString().trim();
  }

  /*
   * Metodo que Convierte una un Date a un timestamp @return Timestmp con la
   * fecha @pre Date instanciado
   */
  public static Timestamp convertirDateTotimestamp2(Date fechaActual) {
    Timestamp timestampGeneracion = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
      sdf.applyPattern("dd-MMM-yyyy");
      String mientras = sdf.format(fechaActual);
      Date dateGeneracion = sdf.parse(mientras);
      timestampGeneracion = new Timestamp(dateGeneracion.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return timestampGeneracion;
  }

  /*
   * Metodo que Convierte una un timestamp a un string @return String con
   * fotmato yyyy-mm-dd @pre Timestamp diferente de null
   */
  public static String convertirTimestampToString2(Timestamp ts) {
    String fechaStringGenerado = "";

    if (ts == null) {

      System.out.println("llega nulo");
    }
    SimpleDateFormat ft = new SimpleDateFormat("dd-MMM-yyyy");
    fechaStringGenerado = ft.format(ts);
    return fechaStringGenerado;
  }

  public static Timestamp adicionarDiaToTimestamp(Timestamp ts, int NoDia) {
    Timestamp timestampGeneracion = null;
    if (ts == null) {

      System.out.println("llega nulo");
    }

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, NoDia); // Adding No de day to current date
    String newdate = dateformat.format(cal.getTime());
    System.out.println(newdate);
    return timestampGeneracion;
  }

  /*
   * Metodo que Convierte una un Date a un String @return string con la fecha
   * 
   * @pre Date instanciado
   */
  public static String convertirDateToString(Date fecha) {

    String fechaStringGenerado = "";
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

      sdf.applyPattern("yyyy.MM.dd");

      fechaStringGenerado = sdf.format(fecha);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return fechaStringGenerado;
  }

  public static String convertirDateToString2(Date fecha) {

    String fechaStringGenerado = "";
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

      sdf.applyPattern("HH:mm:ss");

      fechaStringGenerado = sdf.format(fecha);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return fechaStringGenerado;
  }

  /*
   * Metodo que Convierte una un timestamp a un string @return String con
   * fotmato yyyy-mm-dd @pre Timestamp diferente de null
   */
  public static String convertirTimestampToString3(Timestamp ts) {
    String fechaStringGenerado = "";

    if (ts == null) {

      System.out.println("llega nulo");
    }
    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
    fechaStringGenerado = ft.format(ts);
    return fechaStringGenerado;
  }

  public static String convertirTimestampToString4(Timestamp ts) {
    String strFechaGeneracion = null;

    try {
      SimpleDateFormat sdf = new SimpleDateFormat(
              "EEE, d MMM yyyy hh:mm:ss aaa");
      sdf.applyPattern("EEE, d MMM yyyy hh:mm:ss aaa");
      strFechaGeneracion = sdf.format(ts);

    } catch (Exception e) {
      e.printStackTrace();
    }

    // strFechaGeneracion = TempFecha;
    // System.out.println("fecha generacion 6:"+this.strFechaGeneracion);
    return strFechaGeneracion;

  }

  private static final String[] tensNames = {"", " ten", " twenty",
    " thirty", " forty", " fifty", " sixty", " seventy", " eighty",
    " ninety"};

  private static final String[] numNames = {"", " one", " two", " three",
    " four", " five", " six", " seven", " eight", " nine", " ten",
    " eleven", " twelve", " thirteen", " fourteen", " fifteen",
    " sixteen", " seventeen", " eighteen", " nineteen"};

  private static String convertLessThanOneThousand(int number) {
    String soFar;

    if (number % 100 < 20) {
      soFar = numNames[number % 100];
      number /= 100;
    } else {
      soFar = numNames[number % 10];
      number /= 10;

      soFar = tensNames[number % 10] + soFar;
      number /= 10;
    }
    if (number == 0) {
      return soFar;
    }
    return numNames[number] + " hundred" + soFar;
  }

  public static String convertNumberToWords(Double number) {

    String numero_original, numero_original2;
    String parte_decimal;

    // 0 to 999 999 999 999
    if (number == 0) {
      return "zero";
    }

    String snumber = String.valueOf(number);

    numero_original = snumber.replace(".", ",");
    numero_original2 = snumber.replace(".", ";");

    String strTmp = numero_original2;
    String[] arrayCadena = strTmp.split(";");
    int intTmp = Integer.parseInt(arrayCadena[0]);

    // pad with "0"
    String mask = "000000000000";
    DecimalFormat df = new DecimalFormat(mask);
    // snumber = df.format(number);
    snumber = df.format(intTmp);

    // XXXnnnnnnnnn
    int billions = Integer.parseInt(snumber.substring(0, 3));
    // nnnXXXnnnnnn
    int millions = Integer.parseInt(snumber.substring(3, 6));
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
    // nnnnnnnnnXXX

    int thousands = Integer.parseInt(snumber.substring(9, 12));

    String tradBillions;
    switch (billions) {
      case 0:
        tradBillions = "";
        break;
      case 1:
        tradBillions = convertLessThanOneThousand(billions) + " billion ";
        break;
      default:
        tradBillions = convertLessThanOneThousand(billions) + " billion ";
    }
    String result = tradBillions;

    String tradMillions;
    switch (millions) {
      case 0:
        tradMillions = "";
        break;
      case 1:
        tradMillions = convertLessThanOneThousand(millions) + " million ";
        break;
      default:
        tradMillions = convertLessThanOneThousand(millions) + " million ";
    }
    result = result + tradMillions;

    String tradHundredThousands;
    switch (hundredThousands) {
      case 0:
        tradHundredThousands = "";
        break;
      case 1:
        tradHundredThousands = "one thousand ";
        break;
      default:
        tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                + " thousand ";
    }
    result = result + tradHundredThousands;

    String tradThousand;

    tradThousand = convertLessThanOneThousand(thousands);
    result = result + tradThousand;

    String Num[] = numero_original.split(",");

    if (Num[1].equals("0") || Num[1].equals("00")) // Parte decimal
    {
      // de da formato al numero decimal
      parte_decimal = "DOLLARS";

    } else {
      // de da formato al numero decimal
      parte_decimal = "AND " + Num[1] + "/100 DOLLARS";
    }

    // remove extra spaces!
    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ")
            .toUpperCase()
            + " " + parte_decimal;
  }

  public static void main(String args[]) {

    String c = Utilidad.cifrar("Fwilches750930"); // vA8JBDmgfXLHPPumFEqPAA==
    System.out.println("1 " + c);
    String d = Utilidad.descifrar(c);
    System.out.println("2 " + d);
  }

  /**
   *
   * @param value
   * @return
   */
  public static BigDecimal round(BigDecimal value) {
    return value.setScale(1, BigDecimal.ROUND_HALF_UP);
  }

  /**
   *
   * @param value
   * @param scale
   * @return
   */
  public static BigDecimal round(BigDecimal value, int scale) {
    return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
  }

}
