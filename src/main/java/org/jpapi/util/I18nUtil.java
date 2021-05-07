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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author jlgranda
 */
public class I18nUtil {
    public static String getMessages(String key) {
         FacesContext fc = FacesContext.getCurrentInstance();
         Locale myLocale = fc.getExternalContext().getRequestLocale();
         ResourceBundle myResources = ResourceBundle.getBundle("MessageResources", myLocale);

         return myResources.containsKey(key) ? myResources.getString(key) : key;
    }
    
    public static String getFormat(String key, String... args) {
        MessageFormat mf = new MessageFormat(I18nUtil.getMessages(key));
        return  mf.format(args);
    }
    
    public static String getMessages(String key, String... args) {
         return I18nUtil.getFormat(key, args);
    }
}
