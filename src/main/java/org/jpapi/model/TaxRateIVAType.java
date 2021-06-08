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
 * TaxRateIVA types for code on entity
 * @author jlgranda
 */
public enum TaxRateIVAType {
    NONE,
    ZERO,
    TWO,
    SIX,
    SEVEN;
    
    public static TaxRateIVAType encode(String code){
        TaxRateIVAType t = NONE;
        if ("0".equalsIgnoreCase(code)){
            t = ZERO;
        } else if ("2".equalsIgnoreCase(code)){
            t = TWO;
        } else if ("6".equalsIgnoreCase(code)){
            t = SIX;
        } else if ("7".equalsIgnoreCase(code)){
            t = SEVEN;
        } 
        return t;
    }
    
    public static String decode(TaxRateIVAType t){
        String s = "";
        if (NONE.equals(t)){
            s = "";
        } else if (ZERO.equals(t)){
            s = "0";
        } else if (TWO.equals(t)){
            s = "2";
        } else if (SIX.equals(t)){
            s = "6";
        } else if (SEVEN.equals(t)){
            s = "7";
        } 
        return s;
    }
    
    public static String translate(TaxRateIVAType t){
        String s = "";
        if (NONE.equals(t)){
            s = "";
        } else if (ZERO.equals(t)){
            s = "0%";
        } else if (TWO.equals(t)){
            s = "12%";
        } else if (SIX.equals(t)){
            s = "No objeto de impuesto";
        } else if (SEVEN.equals(t)){
            s = "Excento de IVA";
        } 
        return s;
    }
}
