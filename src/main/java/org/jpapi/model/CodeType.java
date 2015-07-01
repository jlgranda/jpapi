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
package org.jpapi.model;

/**
 * Code types for code on entity
 * @author jlgranda
 */
public enum CodeType {
    NONE,
    SYSTEM,
    CEDULA,
    RUC,
    PASSPORT,
    DEFAULT_CUSTOMER,
    DNI,
    BADGE,
    NUMERO_FACTURA,
    CLAVE_ACCESO,
    GROUP,
    TAG;
    
    public static CodeType encode(String tipoIdentificacionComprador){
        CodeType t = NONE;
        if ("04".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = RUC;
        } else if ("05".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = CEDULA;
        } else if ("06".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = PASSPORT;
        } else if ("07".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = DEFAULT_CUSTOMER;
        } else if ("08".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = DNI;
        } else if ("09".equalsIgnoreCase(tipoIdentificacionComprador)){
            t = BADGE;
        } 
        return t;
    }
    
    public static String decode(CodeType t){
        String s = "";
        if (RUC.equals(t)){
            s = "04";
        } else if (CEDULA.equals(t)){
            s = "05";
        } else if (PASSPORT.equals(t)){
            s = "06";
        } else if (DEFAULT_CUSTOMER.equals(t)){
            s = "07";
        } else if (DNI.equals(t)){
            s = "08";
        } else if (BADGE.equals(t)){
            s = "09";
        } 
        return s;
    }
}
