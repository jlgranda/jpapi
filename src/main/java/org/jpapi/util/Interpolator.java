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

/**
 *
 * @author jlgranda
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Populates an interpolated string using the given template and parameters:
 * <p/>
 * <b>For example:</b><br>
 * Template: <code>"This is a {0} template with {1} parameters. Just {1}."</code><br>
 * Parameters: <code>"simple", 2</code><br>
 * Result: <code>"This is a simple template with 2 parameters. Just 2"</code>
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class Interpolator {
    private static final String templateRegex = "\\{(\\d+)\\}";
    private static final Pattern templatePattern = Pattern.compile(templateRegex);

    /**
     * Populate a template with the corresponding parameters.
     */
    public static String interpolate(final String template, final Object... params) {
        StringBuffer result = new StringBuffer();
        if ((template != null) && (params != null)) {
            Matcher matcher = templatePattern.matcher(template);
            while (matcher.find()) {
                int index = Integer.valueOf(matcher.group(1));
                Object value = matcher.group();

                if (params.length > index) {
                    if (params[index] != null) {
                        value = params[index];
                    }
                }
                matcher.appendReplacement(result, value.toString());
            }
            matcher.appendTail(result);
        } else if (template != null) {
            result = new StringBuffer(template);
        }
        return result.toString();
    }
}
