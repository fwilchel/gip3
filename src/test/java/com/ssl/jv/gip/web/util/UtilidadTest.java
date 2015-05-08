package com.ssl.jv.gip.web.util;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author d5a9p6s7
 */
public class UtilidadTest {

  public UtilidadTest() {
  }

  /**
   * Test of stringFormat method, of class Utilidad.
   */
  @Test
  public void testStringFormat() {
    System.out.println("stringFormat");
    String sql = "";
    String[] params = null;
    String expResult = "";
    String result = Utilidad.stringFormat(sql, params);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirStringToCalendar method, of class Utilidad.
   */
  @Test
  public void testConvertirStringToCalendar() {
    System.out.println("convertirStringToCalendar");
    String fechaS = "";
    Calendar expResult = null;
    Calendar result = Utilidad.convertirStringToCalendar(fechaS);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirStringToTimestamp method, of class Utilidad.
   */
  @Test
  public void testConvertirStringToTimestamp() {
    System.out.println("convertirStringToTimestamp");
    String fechaS = "";
    Timestamp expResult = null;
    Timestamp result = Utilidad.convertirStringToTimestamp(fechaS);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirDateTotimestamp method, of class Utilidad.
   */
  @Test
  public void testConvertirDateTotimestamp() {
    System.out.println("convertirDateTotimestamp");
    Date fechaActual = null;
    Timestamp expResult = null;
    Timestamp result = Utilidad.convertirDateTotimestamp(fechaActual);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirTimestampToString method, of class Utilidad.
   */
  @Test
  public void testConvertirTimestampToString() {
    System.out.println("convertirTimestampToString");
    Timestamp ts = null;
    String expResult = "";
    String result = Utilidad.convertirTimestampToString(ts);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirDateTotimestampCompleto method, of class Utilidad.
   */
  @Test
  public void testConvertirDateTotimestampCompleto() {
    System.out.println("convertirDateTotimestampCompleto");
    Date fechaActual = null;
    Timestamp expResult = null;
    Timestamp result = Utilidad.convertirDateTotimestampCompleto(fechaActual);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of configureResponse method, of class Utilidad.
   */
  @Test
  public void testConfigureResponse() {
    System.out.println("configureResponse");
    HttpServletResponse response = null;
    String fileName = "";
    HttpServletResponse expResult = null;
    HttpServletResponse result = Utilidad.configureResponse(response, fileName);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of configureResponse2 method, of class Utilidad.
   */
  @Test
  public void testConfigureResponse2() {
    System.out.println("configureResponse2");
    HttpServletResponse response = null;
    String fileName = "";
    HttpServletResponse expResult = null;
    HttpServletResponse result = Utilidad.configureResponse2(response, fileName);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of configureResponse3 method, of class Utilidad.
   */
  @Test
  public void testConfigureResponse3() {
    System.out.println("configureResponse3");
    HttpServletResponse response = null;
    String fileName = "";
    HttpServletResponse expResult = null;
    HttpServletResponse result = Utilidad.configureResponse3(response, fileName);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of cifrar method, of class Utilidad.
   */
  @Test
  public void testCifrar() {
    System.out.println("cifrar");
    String clearText = "";
    String expResult = "";
    String result = Utilidad.cifrar(clearText);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of descifrar method, of class Utilidad.
   */
  @Test
  public void testDescifrar() {
    System.out.println("descifrar");
    String textIni = "";
    String expResult = "";
    String result = Utilidad.descifrar(textIni);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirCalendartoString method, of class Utilidad.
   */
  @Test
  public void testConvertirCalendartoString() {
    System.out.println("convertirCalendartoString");
    Calendar actualday = null;
    String expResult = "";
    String result = Utilidad.convertirCalendartoString(actualday);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of quitaEspacios method, of class Utilidad.
   */
  @Test
  public void testQuitaEspacios() {
    System.out.println("quitaEspacios");
    String texto = "";
    String expResult = "";
    String result = Utilidad.quitaEspacios(texto);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirDateTotimestamp2 method, of class Utilidad.
   */
  @Test
  public void testConvertirDateTotimestamp2() {
    System.out.println("convertirDateTotimestamp2");
    Date fechaActual = null;
    Timestamp expResult = null;
    Timestamp result = Utilidad.convertirDateTotimestamp2(fechaActual);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirTimestampToString2 method, of class Utilidad.
   */
  @Test
  public void testConvertirTimestampToString2() {
    System.out.println("convertirTimestampToString2");
    Timestamp ts = null;
    String expResult = "";
    String result = Utilidad.convertirTimestampToString2(ts);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of adicionarDiaToTimestamp method, of class Utilidad.
   */
  @Test
  public void testAdicionarDiaToTimestamp() {
    System.out.println("adicionarDiaToTimestamp");
    Timestamp ts = null;
    int NoDia = 0;
    Timestamp expResult = null;
    Timestamp result = Utilidad.adicionarDiaToTimestamp(ts, NoDia);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirDateToString method, of class Utilidad.
   */
  @Test
  public void testConvertirDateToString() {
    System.out.println("convertirDateToString");
    Date fecha = null;
    String expResult = "";
    String result = Utilidad.convertirDateToString(fecha);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirDateToString2 method, of class Utilidad.
   */
  @Test
  public void testConvertirDateToString2() {
    System.out.println("convertirDateToString2");
    Date fecha = null;
    String expResult = "";
    String result = Utilidad.convertirDateToString2(fecha);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirTimestampToString3 method, of class Utilidad.
   */
  @Test
  public void testConvertirTimestampToString3() {
    System.out.println("convertirTimestampToString3");
    Timestamp ts = null;
    String expResult = "";
    String result = Utilidad.convertirTimestampToString3(ts);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertirTimestampToString4 method, of class Utilidad.
   */
  @Test
  public void testConvertirTimestampToString4() {
    System.out.println("convertirTimestampToString4");
    Timestamp ts = null;
    String expResult = "";
    String result = Utilidad.convertirTimestampToString4(ts);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of convertNumberToWords method, of class Utilidad.
   */
  @Test
  public void testConvertNumberToWords() {
    System.out.println("convertNumberToWords");
    Double number = null;
    String expResult = "";
    String result = Utilidad.convertNumberToWords(number);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of main method, of class Utilidad.
   */
  @Test
  public void testMain() {
    System.out.println("main");
    String[] args = null;
    Utilidad.main(args);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of round method, of class Utilidad.
   */
  @Test
  public void testRound_BigDecimal() {
    System.out.println("round");
    BigDecimal value = null;
    BigDecimal expResult = null;
    BigDecimal result = Utilidad.round(value);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of round method, of class Utilidad.
   */
  @Test
  public void testRound_BigDecimal_int() {
    System.out.println("round");
    BigDecimal value = null;
    int scale = 0;
    BigDecimal expResult = null;
    BigDecimal result = Utilidad.round(value, scale);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of formatearFecha method, of class Utilidad.
   */
  @Test
  public void testFormatearFecha() {
    System.out.println("formatearFecha");
    Date fecha = null;
    String formato = "";
    String expResult = "";
    String result = Utilidad.formatearFecha(fecha, formato);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of inputStreamToFile method, of class Utilidad.
   */
  @Test
  public void testInputStreamToFile_InputStream() throws Exception {
    System.out.println("inputStreamToFile");
    InputStream is = null;
    File expResult = null;
    File result = Utilidad.inputStreamToFile(is);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of inputStreamToFile method, of class Utilidad.
   */
  @Test
  public void testInputStreamToFile_InputStream_String() throws Exception {
    System.out.println("inputStreamToFile");
    InputStream is = null;
    String path = "";
    File expResult = null;
    File result = Utilidad.inputStreamToFile(is, path);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of createFileFromString method, of class Utilidad.
   */
  @Test
  public void testCreateFileFromString() throws Exception {
    System.out.println("createFileFromString");
    String path = "";
    String name = "";
    String content = "";
    Utilidad.createFileFromString(path, name, content);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of inputStreamToBase64 method, of class Utilidad.
   */
  @Test
  public void testInputStreamToBase64() throws Exception {
    System.out.println("inputStreamToBase64");
    InputStream inputStream = null;
    String expResult = "";
    String result = Utilidad.inputStreamToBase64(inputStream);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of base64ToInputStream method, of class Utilidad.
   */
  @Test
  public void testBase64ToInputStream_String() throws Exception {
    System.out.println("base64ToInputStream");
    String base64 = "";
    InputStream expResult = null;
    InputStream result = Utilidad.base64ToInputStream(base64);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of base64ToInputStream method, of class Utilidad.
   */
  @Test
  public void testBase64ToInputStream_byteArr() throws Exception {
    System.out.println("base64ToInputStream");
    byte[] archivo = null;
    InputStream expResult = null;
    InputStream result = Utilidad.base64ToInputStream(archivo);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  @Test
  public void testBase64Decode() {
    String text = "procafecol2014*";
    String expResult = "cHJvY2FmZWNvbDIwMTQq";
    String result = Base64.encodeBase64String(text.getBytes());
    assertEquals(result, expResult);
  }

  @Test
  public void testBase64Encode() {
    String text = "cHJvY2FmZWNvbDIwMTQq";
    String expResult = "procafecol2014*";
    String result = new String(Base64.decodeBase64(text));
    assertEquals(result, expResult);
  }

}
