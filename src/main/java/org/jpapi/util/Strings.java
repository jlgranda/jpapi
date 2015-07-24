/**
 * This file is part of Glue: Adhesive BRMS
 *
 * Copyright (c)2012 José Luis Granda <jlgranda@eqaula.org> (Eqaula Tecnologías Cia Ltda)
 * Copyright (c)2012 Eqaula Tecnologías Cia Ltda (http://eqaula.org)
 *
 * If you are developing and distributing open source applications under
 * the GNU General Public License (GPL), then you are free to re-distribute Glue
 * under the terms of the GPL, as follows:
 *
 * GLue is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Glue is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Glue. If not, see <http://www.gnu.org/licenses/>.
 *
 * For individuals or entities who wish to use Glue privately, or
 * internally, the following terms do not apply:
 *
 * For OEMs, ISVs, and VARs who wish to distribute Glue with their
 * products, or host their product online, Eqaula provides flexible
 * OEM commercial licenses.
 *
 * Optionally, Customers may choose a Commercial License. For additional
 * details, contact an Eqaula representative (sales@eqaula.org)
 */
package org.jpapi.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Strings {
	/**
	 * Replace all instances of camel case naming standard with the underscore
	 * equivalent. E.g. "someString" becomes "some_string". This method does not
	 * perform any validation and will not throw any exceptions.
	 * 
	 * @param input
	 *            unformatted string
	 * @return modified result
	 */
	public static String camelToUnderscore(final String input) {
		String result = input;
		if (input instanceof String) {
			result = input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
		}
		return result;
	}

	/**
	 * Join all strings, using the delim character as the delimeter. E.g.: a
	 * delim of "-", and strings of "foo", "bar" would produce "foo-bar"
	 * 
	 * @param buf
	 * @param strings
	 * @return
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
	 * @param buf
	 * @param strings
	 * @return
	 */
	public static String join(final String delim, final List<String> strings) {
		String result = "";
		for (String string : strings) {
			result += delim + string;
		}

		if (delim != null) {
			result = result.substring(delim.length());
		}
		return result;
	}

	public static String canonicalize(final String name) {
		String result = null;
		if (name != null) {
			result = name.toLowerCase().replace(' ', '-')
					.replaceAll("[^a-z0-9-]*", "").replaceAll("-+", "-");
		}
		return result;
	}

	/**
	 * 
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is non-null; {@code ""} if it is null
	 */
	public static String nullToEmpty(String string) {
		return (string == null) ? "" : string;
	}

	/**
	 * Returns the given string if it is nonempty; {@code null} otherwise.
	 * 
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is nonempty; {@code null} if it is
	 *         empty or null
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
	 * @param string
	 *            a string reference to check
	 * @return {@code true} if the string is null or is the empty string
	 */
	public static boolean isNullOrEmpty(String string) {
	  return string == null || string.length() ==  0; //string.isEmpty() in Java 6
	}

	public static String toString(Object object) {
		return object != null ? object.toString() :  "";
	}
	
	public static Integer toInteger(Object object) {
		if (object == null) return null; 
		if ("".equals(object.toString())) return null;
		return Integer.valueOf(Strings.toString(object));
	}
	
	public static Long toLong(Object object) {
		if (object == null) return null;
		if ("".equals(object.toString())) return null;
		return Long.valueOf(Strings.toString(object));
	}
	
	public static BigDecimal toDecimal(Object object) {
		if (object == null) return null; 
		BigDecimal decimal = new BigDecimal(Double.valueOf(Strings.toString(object)));
		decimal = decimal.setScale(4, BigDecimal.ROUND_HALF_UP);
		return decimal;
	}

	public static int toInt(Object object) {
		Integer _i = Strings.toInteger(object);
		return _i == null ? 0 : _i.intValue();
	}

	public static Boolean toBoolean(Object object) {
		if (object == null) return null; 
		return (Boolean) object;
	}
	
	public static <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
        
        
    public static String toUpperCase(String str) {
        if (str == null) {
            return str;
        }
        return str.toUpperCase();
    }
    
    private static final int MINIMUM_SUPPORTED_LENGTH = 4;

    /**
     * Truncate text on a whitespace boundary (near a specified length). The
     * length of the resultant string will be in the range:<br>
     * <code> (requested-length * .25) ~ (requested-length * 1.5) </code>
     *
     * @param text
     *            Text to truncate
     * @param length
     *            Target length
     * @return Truncated text
     */
    public static String truncateAtWhitespace(String text, int length) {
        int desired, lowerBound, upperBound;
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
         * No whitespace - look beyond the desired maximum
         */
        for (int i = (length); i < upperBound; i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                return text.substring(0, i);
            }
        }
        /*
         * No whitespace, just truncate the text at the requested length
         */
        return text.substring(0, length);
    }
    
    public static List<String> splitAtWhitepace(String text, int skip){
        List<String> parts = new ArrayList<>();
        int index = text.indexOf(' ');
        skip = skip > 2 ? 2: skip;
        for (int i = 1; i < skip; i++){
            index = text.indexOf(' ', index + 1);
        }
        if (index != -1){
            parts.add(text.substring(0, index));
            parts.add(text.substring(index+1, text.length()));
        } else {
            parts.add(text);
        }
        return parts;
    }
    
    public static List<String> splitNamesAt(String text, String seperator){
        int count = StringUtils.countMatches(text, seperator);
        System.out.println("count --> " + count);
        return splitAtWhitepace(text, count);
    }
    
    public static List<String> splitNamesAt(String text){
        return splitNamesAt(text, " ");
    }
    
    public static void main(String args[]){
        System.out.println(Strings.splitNamesAt("José Luis Granda Sivisapa"));
        System.out.println(Strings.splitNamesAt("José Luis Granda"));
        System.out.println(Strings.splitNamesAt("José Granda"));
        System.out.println(Strings.splitNamesAt("José"));
        System.out.println(Strings.canonicalize("josé luis GRANDA Sivisapa"));
    }
}
