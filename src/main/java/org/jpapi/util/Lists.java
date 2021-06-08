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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jlgranda
 */
public class Lists {
    
    /**
     * Convierte una lista de objetos en una cadena separada por comas, con los
     * elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param iter
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @return una cadena separada por comas, con los elementos de la lista.
     */
    public static String toString(Iterable iter) {
        List list = new ArrayList();
        for (Object obj : iter){
            list.add(obj);
        }
        return Lists.toString(list, ',', (char) Character.SPACE_SEPARATOR);
    }
    
    /**
     * Convierte una lista de objetos en una cadena separada por comas, con los
     * elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param list
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @return una cadena separada por comas, con los elementos de la lista.
     */
    public static String toString(List list) {
        return Lists.toString(list, ',', (char) Character.SPACE_SEPARATOR);
    }

    /**
     * Convierte una lista de objetos en una cadena separada por comas, con los
     * elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param list
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @return una cadena separada por comas, con los elementos de la lista.
     */
    public static String toString(String[] list) {
        if (list == null) {
            return "-";
        }
        StringBuilder buffer = new StringBuilder();
        int length = list.length;
        for (int i = 0; i < length; i++) {
            buffer.append(list[i]);
            if (i > 0) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

    /**
     * Convierte una lista de objetos en una cadena separada por separador, con
     * los elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param list
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @param separador
     * @return una cadena separada por separador, con los elementos de la lista.
     */
    public static String toString(List list, char separador) {
        return Lists.toString(list, separador, '\0');
    }
   
    /**
     * Convierte una lista de objetos en una cadena separada por separador, con
     * los elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param list
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @param separador
     * @param delimitador
     * @return una cadena separada por separador, con los elementos de la lista.
     */
    public static String toString(List list, char separador, char delimitador) {
        if (list.isEmpty()) {
            return "-";
        }
        StringBuilder buffer = new StringBuilder();
        int index = 0;
        Object value = null;
        String str;
        for (Iterator _iterator = list.iterator(); _iterator.hasNext(); ) {
            value = _iterator.next();
            str = (value == null ? "" : value.toString());
            str = str.replaceAll("\'", "\\\'");
            buffer.append(delimitador)
            .append(str)
            .append(delimitador);
           
            if (index < (list.size() - 1)) {
                buffer.append(separador);
                buffer.append(' ');
            }
            index++;
        }
        return buffer.toString();
    }

    /**
     * Convierte una lista de objetos en una cadena separada por separador, con
     * los elementos de la lista. Los objetos de la lista deben implementar el
     * método toString
     *
     * @param list
     *            la lista sobre la cual se construye la cadena de elementos
     *            seperadas por comas
     * @param separador
     * @return una cadena separada por separador, con los elementos de la lista.
     */
    public static String toString(List list, String separador) {
        if (list.isEmpty()) {
            return "-";
        }
        StringBuilder buffer = new StringBuilder();
        int index = 0;
        for (Iterator _iterator = list.iterator(); _iterator.hasNext(); buffer
                .append(_iterator.next().toString())) {
            if (index > 0) {
                buffer.append(separador);
            }
            index++;
        }
        return buffer.toString();
    }

    public static List<String> toList(String s) {
        if (s == null) {
            return new ArrayList<>();
        }
        if (s.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(s.split(","));
    }

    public static String findDefaultValue(String values) {
        return findDefaultValue(values, "*");
    }

    public static String findDefaultValue(String values, String mnemotecnico) {
        List<String> options = Lists.toList(values);
        String defaultValue = null;
        for (String s : options) {
            if (s.contains(mnemotecnico)) {
                defaultValue = s.substring(0, s.length() - 1);
            }
        }
        return defaultValue;
    }
   
    public static void main(String args[]){
        String scape = "ORD-CHICAGO-O'HARE INTERNATIONAL AIRPORT".replace("\'", "\\\'");
        List<String> tests = new ArrayList<>();
        tests.add(scape);
        tests.add(scape);
        tests.add(scape);
        tests.add(scape);
        tests.add(scape);
        tests.add(scape);
       
       
        System.out.print(Lists.toString(tests, ',', '\''));
    }
}