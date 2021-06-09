/*
 * Copyright (C) 2015 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jpapi.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class Strings.
 */
public final class Strings {


    private Strings() {
    }

    /**
     * Replace all instances of camel case naming standard with the underscore
     * equivalent. E.g. "someString" becomes "some_string". This method does not
     * perform any validation and will not throw any exceptions.
     *
     * @param input unformatted string
     * @return modified result
     */
    public static String camelToUnderscore(final String input) {
        String result = input;
        if (input instanceof String) {
            result = input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase(Locale.getDefault());
        }
        return result;
    }
    
    /**
     * Join all strings, using the delim character as the delimeter. E.g.: a
     * delim of "-", and strings of "foo", "bar" would produce "foo-bar"
     *
     * @param delim the delim
     * @param strings the strings
     * @return the string
     */
    public static String join(final String delim, final String... strings) {
        String result = "";
        for (String string : strings) {
            result += delim + string;
        }

        if (delim != null) {
            result = result.substring(delim.length());
        }
        return result;
    }

    /**
     * Join all strings, using the delim character as the delimeter. E.g.: a
     * delim of "-", and strings of "foo", "bar" would produce "foo-bar"
     *
     * @param delim the delim
     * @param strings the strings
     * @return the string
     */
    public static String join(final String delim, final List<String> strings) {
        String result = "";
        result = strings.stream().map(string -> delim + string).reduce(result, String::concat);

        if (delim != null) {
            result = result.substring(delim.length());
        }
        return result;
    }

    /**
     * Canonicalize.
     *
     * @param name the name
     * @return the string
     */
    public static String canonicalize(final String name) {
        String result = null;
        if (name != null) {
            result = name.toLowerCase(Locale.getDefault()).replace(' ', '-')
                    .replaceAll("[^a-z0-9-]*", "").replaceAll("-+", "-");
        }
        return result;
    }
    
    /**
     * Canonicalize with '_'.
     *
     * @param name the name
     * @return the string
     */
    public static String canonicalizeToUnderscore(final String name) {
        String result = null;
        if (name != null) {
            result = name.toLowerCase(Locale.getDefault()).replace(' ', '_').replace('-', '_')
                    .replaceAll("[^a-z0-9-_]*", "").replaceAll("-+", "_");
        }
        return result;
    }

    /**
     * Null to empty.
     *
     * @param string the string to test and possibly return
     * @return {@code string} itself if it is non-null; {@code ""} if it is null
     */
    public static String nullToEmpty(String string) {
        return (string == null) ? "" : string;
    }

    /**
     * Returns the given string if it is nonempty; {@code null} otherwise.
     *
     * @param string the string to test and possibly return
     * @return {@code string} itself if it is nonempty; {@code null} if it is
     * empty or null
     */
    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    /**
     * Returns {@code true} if the given string is null or is the empty string.
     *
     * <p>
     * Consider normalizing your string references with {@link #nullToEmpty}. If
     * you do, you can use {@link String#isEmpty()} instead of this method, and
     * you won't need special null-safe forms of methods like
     * {@link String#toUpperCase} either. Or, if you'd like to normalize "in the
     * other direction," converting empty strings to {@code null}, you can use
     * {@link #emptyToNull}.
     *
     * @param string a string reference to check
     * @return {@code true} if the string is null or is the empty string
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0; //string.isEmpty() in Java 6
    }

    /**
     * To string.
     *
     * @param object the object
     * @return the string
     */
    public static String toString(Object object) {
        return object != null ? object.toString() : "";
    }
    
    /**
     * To string format
     *
     * @param value
     * @param pattern
     * @return the string
     */
    public static String format(double value, String pattern) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }
    
    /**
     * To string.
     *
     * @param map the map
     * @return the string
     */
    public static String toString(Map<String, String> map) {
        if (map == null) return "";
        StringBuilder sb = new StringBuilder();
        map.keySet().forEach((k) -> {
            sb.append(k).append(": ").append(map.get(k)).append("\n");
        });
        return sb.toString();
    }

    /**
     * To integer.
     *
     * @param object the object
     * @return the integer
     */
    public static Integer toInteger(Object object) {
        if (object == null) {
            return null;
        }
        if ("".equals(object.toString())) {
            return null;
        }
        return Integer.valueOf(Strings.toString(object));
    }

    /**
     * To long.
     *
     * @param object the object
     * @return the long
     */
    public static Long toLong(Object object) {
        if (object == null) {
            return null;
        }
        if ("".equals(object.toString())) {
            return null;
        }
        return Long.valueOf(Strings.toString(object));
    }

    /**
     * To decimal.
     *
     * @param object the object
     * @return the big decimal
     */
    public static BigDecimal toDecimal(Object object) {
        if (object == null) {
            return null;
        }
        BigDecimal decimal = new BigDecimal(Double.valueOf(Strings.toString(object)));
        decimal = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
        return decimal;
    }

    /**
     * To int.
     *
     * @param object the object
     * @return the int
     */
    public static int toInt(Object object) {
        Integer i = Strings.toInteger(object);
        return i == null ? 0 : i;
    }

    /**
     * To boolean.
     *
     * @param object the object
     * @return the boolean
     */
    public static Boolean toBoolean(Object object) {
        if (object == null) {
            return null;
        }
        return (Boolean) object;
    }

    /**
     * Append.
     *
     * @param <T> the generic type
     * @param arr the arr
     * @param element the element
     * @return the t[]
     */
    public static <T> T[] append(T[] arr, T element) {
        final int arregloN = arr.length;
        arr = Arrays.copyOf(arr, arregloN + 1);
        arr[arregloN] = element;
        return arr;
    }

    /**
     * To upper case.
     *
     * @param str the str
     * @return the string
     */
    public static String toUpperCase(String str) {
        if (str == null) {
            return str;
        }
        return str.toUpperCase(Locale.getDefault());
    }

    /**
     * To lower case.
     *
     * @param str the str
     * @return the string
     */
    public static String toLowerCase(String str) {
        if (str == null) {
            return str;
        }
        return str.toLowerCase(Locale.getDefault());
    }

    /**
     * The Constant MINIMUM_SUPPORTED_LENGTH.
     */
    private static final int MINIMUM_SUPPORTED_LENGTH = 4;

    /**
     * Truncate text on a whitespace boundary (near a specified length). The
     * length of the resultant string will be in the range:<br>
     * <code> (requested-length * .25) ~ (requested-length * 1.5) </code>
     *
     * @param text Text to truncate
     * @param length Target length
     * @return Truncated text
     */
    public static String truncateAtWhitespace(String text, int length) {
        int lowerBound;
        int upperBound;
        /*
         * Make sure we have a reasonable length to work with
         */
        if (length < MINIMUM_SUPPORTED_LENGTH) {
            throw new IllegalArgumentException(
                    "Requested length too short (must be "
                    + MINIMUM_SUPPORTED_LENGTH + " or greated)");
        }
        /*
         * No need to truncate - the original string "fits"
         */
        if (text.length() <= length) {
            return text;
        }
        /*
         * Try to find whitespace befor the requested maximum
         */
        lowerBound = length / 4;
        upperBound = length + (length / 2);
        for (int i = length - 1; i > lowerBound; i--) {
            if (Character.isWhitespace(text.charAt(i))) {
                return text.substring(0, i);
            }
        }
        /*
         * No whitespace - look beyond the desiredvar maximum
         */
        for (int i = length; i < upperBound; i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                return text.substring(0, i);
            }
        }
        /*
         * No whitespace, just truncate the text at the requested length
         */
        return text.substring(0, length);
    }

    /**
     * Split at whitepace.
     *
     * @param text the text
     * @param skip the skip
     * @return the list
     */
    public static List<String> splitAtWhitepace(String text, int skip) {
        List<String> parts = new ArrayList<>();
        int index = text.indexOf(' ');
        skip = skip > 2 ? 2 : skip;
        for (int i = 1; i < skip; i++) {
            index = text.indexOf(' ', index + 1);
        }
        if (index != -1) {
            parts.add(text.substring(0, index));
            parts.add(text.substring(index + 1, text.length()));
        } else {
            parts.add(text);
        }
        return parts;
    }

    /**
     * Split names at.
     *
     * @param text the text
     * @param seperator the seperator
     * @return the list
     */
    public static List<String> splitNamesAt(String text, String seperator) {
        int count = StringUtils.countMatches(text, seperator);
        return splitAtWhitepace(text, count);
    }

    /**
     * Split names at.
     *
     * @param text the text
     * @return the list
     */
    public static List<String> splitNamesAt(String text) {
        return splitNamesAt(text, " ");
    }

    /**
     * Checks if is url.
     *
     * @param url the url
     * @return true, if is url
     */
    public static boolean isUrl(String url) {
        String[] schemes = {"http", "https", "ftp"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }

    /**
     * Complete.
     *
     * @param longitud the longitud
     * @param secuencia the secuencia
     * @param caracter the caracter
     * @return the string
     */
    public static String complete(int longitud, Long secuencia, char caracter) {

        String valors;
        valors = secuencia.toString();
        String nuevaCadena = "";
        int j = 0;
        if (valors.length() < longitud) {
            String cadenaCeros = "";
            int faltan = longitud - valors.length();
            for (j = 0; j < faltan; j++) {
                cadenaCeros = cadenaCeros + caracter;
            }
            nuevaCadena = cadenaCeros + valors;
        } else {
            nuevaCadena = valors;
        }
        return nuevaCadena;
    }

    /**
     * To string.
     *
     * @param date the date
     * @param field the field
     * @return the string
     */
    public static String toString(Date date, int field) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Map<String, String> map = new HashMap<>();
        map.put("7_1", "Domingo");
        map.put("7_2", "Lunes");
        map.put("7_3", "Martes");
        map.put("7_4", "Miércoles");
        map.put("7_5", "Jueves");
        map.put("7_6", "Viernes");
        map.put("7_7", "Sábado");

        int value = calendar.get(field);
        return map.get(field + "_" + value);
    }

    /**
     * Separar codigo catastral.
     *
     * @param codigoCatastral the codigo catastral
     * @param separadores the separadores
     * @return the list
     */
    public static List<String> separarCodigoCatastral(String codigoCatastral, String separadores) {
        List<String> partes = new ArrayList<>();
        int indice = 0;
        int paso = 0;
        for (String s : separadores.split(",")) {
            paso = Integer.valueOf(s);
            partes.add(codigoCatastral.substring(indice, indice + paso));
            indice = indice + paso;
        }
        return partes;
    }
    
    /**
     * Extrae la subcadena antes de la primera ocurrencia del separator
     * @param str
     * @param separator
     * @return 
     */
    public static String extractBefore(String str, String separator){
        String token = "";
        if (!Strings.isNullOrEmpty(str)) {
            int index = str.indexOf(separator);
            if (index >= 0) {
                token = str.substring(0, index);
            }
        }

        return token;
    }
    public static String extractAfter(String str, String separator){
        String token = "";
        if (!Strings.isNullOrEmpty(str)) {
            int index = str.indexOf(separator);
            if (index >= 0) {
                token = str.substring(index + 1);
            }
        }

        return token;
    }
    
    public static String extractLast(String str, String separator){
        String token = "";
        if (!Strings.isNullOrEmpty(str)) {
            int index = str.lastIndexOf(separator);
            if (index >= 0) {
                token = str.substring(index + 1);
            }
        }

        return token;
    }
    
    public static String extractFirst(String str, String separator){
        String token = "";
        if (!Strings.isNullOrEmpty(str)) {
            int index = str.lastIndexOf(separator);
            if (index >= 0) {
                token = str.substring(0, index);
            }
        }

        return token;
    }
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean verifyNationalIdentityDocument(String nid) {
        String wced = nid.substring(0, 9);
        String verif = nid.substring(9, 10);
        double wd = 0;
        double wc = 0;
        double wa = 0;
        double wb = 0;

        for (int i = 0; i <= wced.length() - 1; i = i + 2) {
            System.out.println("wa " + i + ": " + wced.substring(i, i + 1));
            wa = Double.parseDouble(wced.substring(i, i + 1));
            wb = wa * 2;
            wc = wb;
            if (wb > 9) {
                wc = wb - 9;
            }
            wd = wd + wc;
        }

        for (int i = 1; i <= wced.length() - 1; i = i + 2) {
            System.out.println("wa " + i + ": " + wced.substring(i, i + 1));
            wa = Double.parseDouble(wced.substring(i, i + 1));
            wd = wd + wa;
        }
        double wn;
        wn = wd / 10;
        double wn2 = Math.ceil(wn);
        wn2 = wn2 * 10;
        double digit = wn2 - wd;

       return digit == Double.parseDouble(verif);
    }
    
    public static List<String> splitAt(String test, String separator) {
        int index1 = test.indexOf(separator);
        int index2 = index1 + separator.length();
        
        List<String> partes = new ArrayList<>();
        partes.add(test.substring(0, index1));
        partes.add(test.substring(index2, test.length()));
        
        return partes;
    }
    
    public static String toRegex(String key) {
        key = key.replaceAll("%",".*");
        //for (String s: key.split(""));
        return ".*" + key + ".*";
    }
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String args[]) {
//        String test = "PROY001/agregacion/tsssssssssssssss.jpg";
//        System.out.println(">>> " + Strings.toUpperCase(test));
//        System.out.println(">>> " + Strings.extractLast(test, "/"));
//        System.out.println(">>> " + Strings.extractFirst(test, "/"));
//        System.out.println(">>> " + Strings.extractAfter(test, "/"));
//        System.out.println(">>> " + Strings.extractBefore(test, "/"));
//        
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("SELECT audit.audit_table('").append("test").append(".").append("table").append("'");
//        buffer.append(");");
//        buffer.append("\n");
//        System.out.println(">>> " + buffer.toString());
//        
        //System.out.println(">>> " + Strings.verifyNationalIdentityDocument("1103826960"));
//        test = "prb_nombre_uno_dos_tres as fid";
//        System.out.println(">>> " + toUpperCase(test));
//        System.out.println(">>> " + Strings.extractLast(test, "as"));
//        System.out.println(">>> " + Strings.extractFirst(test, "as"));
//        System.out.println(">>> " + Strings.extractAfter(test, "as"));
//        
//        test = "PROYECTO-007 681064ae-2d29-4f51-b3cc-6446a22f6b2f";
//        System.out.println(">>> canonicalize: " + Strings.canonicalize(test));
//        System.out.println(">>> canonicalizeToU: " + Strings.canonicalizeToUnderscore(test));
//        
//        Map<String, String> valores = new HashMap<>();
//
//        //valores.put("cedula", "1716110059");
//        valores.put("cedula", "1103826960");
//        valores.put("codigoConadis", "1716110059");
//        valores.put("tipoDiscapacidadPredomina", "FÍSICA");
//        valores.put("gradoDiscapacidad", "MODERADO");
//        valores.put("porcentajeDiscapacidad", "46");
//        valores.put("mensaje", "");
//        
//        Map<String, String> obs = new LinkedHashMap<>();
//                obs.put("CÓDIGO CONADIS", valores.get("codigoConadis"));
//                obs.put("TIPO DISCAPACIDAD PREDOMINA", valores.get("tipoDiscapacidadPredomina"));
//                obs.put("GRADO DISCAPACIDAD", valores.get("gradoDiscapacidad"));
//                obs.put("PORCENTAJE DISCAPACIDAD", valores.get("porcentajeDiscapacidad"));
//                
//        System.out.println(Strings.toString(obs));
//        
//        List<String> columns = new ArrayList<>();
//        columns.add("POINT(1215899.53522678 10052434.3328883)");
//        columns.add("POINT(288651.627317715 9546439.03237465)");
//        System.out.println(StringUtils.substringBetween(columns.get(0), "(", " "));
//        System.out.println(StringUtils.substringBetween(columns.get(0), " ", ")"));
//        System.out.println(StringUtils.substringBetween(columns.get(1), "(", " "));
//         System.out.println(StringUtils.substringBetween(columns.get(1), " ", ")"));
//          System.out.println(">>>>>>>>>>>>><<: " + String.format("%.2f", 288651.627317715f).replace(",", "."));
//          System.out.println(">>>>>>>>>>>>><<: " + String.format("%.2f", 288651f).replace(",", "."));
//          System.out.println(">>>>>>>>>>>>><<: " + String.format("%.2f", 0f).replace(",", "."));


            String json = "    \"nombreEntidad\": \"Datos Demográficos (Registro Civil)\",\n" +
"    \"nui\": \"0910985993\",\n" +
"    \"condicionCedulado\": \"FALLECIDO\",\n" +
"    \"conyuge\": \"CARRIEL VERA JANETH MAGDALENA\",\n" +
"    \"estadoCivil\": \"CASADO\",\n" +
"    \"fechaDefuncion\": \"27/10/2016\",\n" +
"    \"fechaNacimiento\": \"24/03/1967\",\n" +
"    \"edad\": \"53\",\n" +
"    \"lugarNacimiento\": \"GUAYAS/GUAYAQUIL/CARBO (CONCEPCION)\",\n" +
"    \"nombre\": \"GARCIA MORENO FRANCISCO XAVIER\",\n" +
"    \"nacionalidad\": \"ECUATORIANA\",\n" +
"    \"nombreMadre\": \"MORENO B LUZMILA RAQUEL\",\n" +
"    \"nombrePadre\": \"GARCIA RAMOS JUAN GILBERTO\",\n" +
"    \"fechaInscripcionGenero\": \" \",\n" +
"    \"genero\": \" \",\n" +
"    \"lugarInscripcionGenero\": \" \",\n" +
"    \"sexo\": \"HOMBRE\",\n" +
"    \"instruccion\": \"SECUNDARIA\",\n" +
"    \"numeroCasa\": \"00000\",\n" +
"    \"calle\": \"HUANCAVILCA Y LA L5\",\n" +
"    \"codigoDomicilio\": \"93905355\",\n" +
"    \"domicilio\": \"GUAYAS/GUAYAQUIL/FEBRES CORDERO\",\n" +
"    \"origen\": \"Dinardap cod1\"\n" +
"}";
//                String key = "\"condicionCedulado\": \"FALLECIDO\"";
//                if (json.contains(key)){
//                    int index = json.indexOf(key);
//                    String valorNumeroRuc = json.substring(index + key.length() + 1, json.indexOf("\"", index + key.length() + 1));         
//                    System.out.println("valido: " + false);
//                }
//                
//                
//                String regexInicio = "\\[\\[\\[";
//                String regexFin = "\\]\\]\\]";
//                //Corregir [[[ por [[[[
//                String geometryText = "dasdasdasd{type:'', geometry:[[a,b]]};";
//                if (geometryText.contains("[[[")){
//                    geometryText = geometryText.replaceFirst(regexInicio, "[[[[");
//                    geometryText = geometryText.replaceFirst(regexFin, "]]]]");
//                }
//                System.out.println(">>" + geometryText);
//                
//                System.out.println(">>>" + Strings.format(-4888888.899999, "#.###"));
//                String regexp = "${PREFIX}";
//                String prefijo = "prefix_nombre_tabla";
//                String test = "\"parroquia_ubi_id_padre\"  = current_value('${PREFIX}6_canton_domicilio')";
//                List<String> partes = Strings.splitAt(test, regexp);
//                System.out.println(Strings.join(Strings.extractBefore(prefijo, "_") + "_", Strings.splitAt(test, regexp)));
                
    }
}
