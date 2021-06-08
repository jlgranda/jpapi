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
 *
 * @author jlgranda
 */
public enum TaxType {
    NONE,
    IVA,
    ICE,
    IRBPNR;
    
    public static TaxType encode(String code){
        TaxType t = NONE;
        if ("2".equalsIgnoreCase(code)){
            t = IVA;
        } else if ("3".equalsIgnoreCase(code)){
            t = ICE;
        } else if ("5".equalsIgnoreCase(code)){
            t = IRBPNR;
        } 
        return t;
    }
    
    public static String decode(TaxType t){
        String s = "";
        if (null != t)switch (t) {
            case NONE:
                s = "";
                break;
            case IVA:
                s = "2";
                break;
            case ICE:
                s = "3"; 
                break;
            case IRBPNR:
                s = "5";
                break;
            default:
                break;
        }
        return s;
    }
    
    public static String translate(TaxType t){
        String s = "";
        if (null != t)switch (t) {
            case NONE:
                s = "";
                break;
            case IVA:
                s = "IVA 12%";
                break;
            case ICE:
                s = "ICE"; 
                break;
            case IRBPNR:
                s = "IRBPNR";
                break;
            default:
                break;
        }
        return s;
    }
}
