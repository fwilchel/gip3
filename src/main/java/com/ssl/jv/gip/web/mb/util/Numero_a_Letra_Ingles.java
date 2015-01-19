package com.ssl.jv.gip.web.mb.util;

import java.text.DecimalFormat;

public class Numero_a_Letra_Ingles {

  private static final String[] tensNames = {
    "",
    " ten",
    " twenty",
    " thirty",
    " forty",
    " fifty",
    " sixty",
    " seventy",
    " eighty",
    " ninety"
  };

  private static final String[] numNames = {
    "",
    " one",
    " two",
    " three",
    " four",
    " five",
    " six",
    " seven",
    " eight",
    " nine",
    " ten",
    " eleven",
    " twelve",
    " thirteen",
    " fourteen",
    " fifteen",
    " sixteen",
    " seventeen",
    " eighteen",
    " nineteen"
  };

  private static String convertLessThanOneThousand(int number) {
    String soFar;

    if (number % 100 < 20){
      soFar = numNames[number % 100];
      number /= 100;
    }
    else {
      soFar = numNames[number % 10];
      number /= 10;

      soFar = tensNames[number % 10] + soFar;
      number /= 10;
    }
    if (number == 0) return soFar;
    return numNames[number] + " hundred" + soFar;
  }


  public static String convert(Double number) {
	  
	String numero_original, numero_original2;
	String parte_decimal; 
	  
    // 0 to 999 999 999 999
    if (number == 0) { return "zero"; }

    String snumber = String.valueOf(number);
    
    numero_original = snumber.replace(".", ",");
    numero_original2 = snumber.replace(".", ";");

    String strTmp = numero_original2;   
	String[] arrayCadena = strTmp.split(";");
	int intTmp = Integer.parseInt(arrayCadena[0]);

    // pad with "0"
    String mask = "000000000000";
    DecimalFormat df = new DecimalFormat(mask);
    //snumber = df.format(number);
    snumber = df.format(intTmp);
	
    // XXXnnnnnnnnn 
    int billions = Integer.parseInt(snumber.substring(0,3));
    // nnnXXXnnnnnn
    int millions  = Integer.parseInt(snumber.substring(3,6)); 
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(6,9)); 
    // nnnnnnnnnXXX
    
    int thousands = Integer.parseInt(snumber.substring(9,12));  
    
    String tradBillions;
    switch (billions) {
    case 0:
      tradBillions = "";
      break;
    case 1 :
      tradBillions = convertLessThanOneThousand(billions) 
      + " billion ";
      break;
    default :
      tradBillions = convertLessThanOneThousand(billions) 
      + " billion ";
    }
    String result =  tradBillions;

    String tradMillions;
    switch (millions) {
    case 0:
      tradMillions = "";
      break;
    case 1 :
      tradMillions = convertLessThanOneThousand(millions) 
      + " million ";
      break;
    default :
      tradMillions = convertLessThanOneThousand(millions) 
      + " million ";
    }
    result =  result + tradMillions;

    String tradHundredThousands;
    switch (hundredThousands) {
    case 0:
      tradHundredThousands = "";
      break;
    case 1 :
      tradHundredThousands = "one thousand ";
      break;
    default :
      tradHundredThousands = convertLessThanOneThousand(hundredThousands) 
      + " thousand ";
    }
    result =  result + tradHundredThousands;

    String tradThousand;
    
    tradThousand = convertLessThanOneThousand(thousands);
    result =  result + tradThousand;
    
    String Num[] = numero_original.split(","); 
    
    if (Num[1].equals("0") || Num[1].equals("00")) //Parte decimal
    {
        //de da formato al numero decimal
        parte_decimal = "DOLLARS";
    	
    }
    else
    {
        //de da formato al numero decimal
        parte_decimal = "and " + Num[1] + "/100 DOLLARS";
    }    

    // remove extra spaces!
    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ").toUpperCase() + " " + parte_decimal;
  }

}