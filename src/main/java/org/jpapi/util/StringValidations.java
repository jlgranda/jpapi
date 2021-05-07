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

public class StringValidations {

    public static final String EMAIL_REGEX = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e"
            + "-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]"
            + "*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4]"
            + "[0-9]|[01]?[0-9][0-9]?|[A-Za-z0-9-]*[A-Za-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b"
            + "\\x0c\\x0e-\\x7f])+)\\])";

    public static boolean isAlphabetic(final String text) {
        if (text != null) {
            return text.matches("^[a-zA-Z]*$");
        }

        return false;
    }

    public static boolean isAlphanumeric(final String textWithDigits) {
        if (textWithDigits != null) {
            return textWithDigits.matches("^[a-zA-Z\\d]*$");
        }

        return false;
    }

    public static boolean isAlphanumericDash(String text) {
        return text.matches("^[a-zA-Z\\d-]*$");
    }

    public static boolean isAlphanumericSpaceUnderscore(final String textWithDigits) {
        if (textWithDigits != null) {
            return textWithDigits.matches("^[a-zA-Z\\d_ ]*$");
        }

        return false;
    }

    public static boolean isEmptyString(final String text) {
        if (text != null) {
            return text.matches("");
        }

        return false;
    }

    public static boolean isPunctuatedTextUTF8(final String text) {
        if (text != null) {
            return text.matches("^[\\p{L}+a-zA-Z/\\d\\s._?!,;':\"~`$%&()+=\\[\\]-]*$");
        }

        return false;
    }

    public static boolean isPunctuatedText(final String text) {
        if (text != null) {
            return text.matches("^[a-zA-Z/\\d\\s._?!,;':\"~`$%&()+=\\[\\]-]*$");
        }

        return false;
    }

    public static boolean isPunctuatedTextWithoutSpace(final String text) {
        if (text != null) {
            return text.matches("^[a-zA-Z/\\d._?!,;':\"~`$%&()+=\\[\\]-]*$");
        }

        return false;
    }

    public static boolean isDecimal(final String number) {
        if (number != null) {
            return number.matches("([0-9]+(\\.[0-9]+)?)|([0-9]*\\.[0-9]+)");
        }
        return false;
    }

    public static boolean isWholeNumber(final String number) {
        if (number != null) {
            return number.matches("[0-9]+");
        }
        return false;
    }

    public static boolean isDigit(final String digit) {
        if (digit != null) {
            return digit.matches("^\\d$");
        }

        return false;
    }

    public static boolean isEmailAddress(final String email) {
        if (email != null) {
            return email.matches("^" + EMAIL_REGEX + "$");
        }

        return false;
    }

    public static boolean isState(final String state) {
        if (state != null) {
            return state.toUpperCase().matches(
                    "^(AL|AK|AS|AZ|AR|CA|CO|CT|DE|DC|FM|FL|FA|GU|HI|ID|IL|IN|IA|KS|KY|LA|ME|MH|MD|"
                    + "MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|MP|OH|OK|OR|PW|PA|PR|RI|SC|SD|TN|TX|"
                    + "UT|VT|VI|VA|WA|WV|WI|WY)$");
        }

        return false;
    }

    public static boolean isZipCode(final String zipCode) {
        if (zipCode != null) {
            return zipCode.matches("\\d{5}((-| +)\\d{4})?$");
        }

        return false;
    }

    public static boolean isPhoneNumber(final String phoneNumber) {
        if (phoneNumber != null) {
            return phoneNumber.matches("^(?:\\([2-9]\\d{2}\\)\\ ?|[2-9]\\d{2}(?:\\-?|\\ ?))[2-9]\\d{2}[- ]?\\d{4}$");
        }

        return false;
    }

    public static boolean isPassword(final String password) {
        return isPassword(password, 5);
    }
    
    public static boolean isPassword(final String password, final int length) {
        if (password != null) {
            return password.matches("^[a-zA-Z0-9!@#$%^&*\\s\\(\\)_\\+=-]{" + length + ",}$");
        }

        return false;
    }

    public static boolean isStrictPassword(final String password) {
        if (password != null) {
            return password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d!@#$%^&*\\s\\(\\)_\\+=-]{8,}$");
        }

        return false;
    }

    public static boolean hasData(final String value) {
        return (value != null) && (value.length() > 0) && value.matches(".*\\S.*");
    }

    public static boolean length(final int length, final String value) {
        return (value != null) && (value.length() == length);
    }

    public static boolean minLength(final int length, final String value) {
        return (value != null) && (value.length() >= length);
    }

    public static boolean maxLength(final int length, final String value) {
        return (value != null) && (value.length() <= length);
    }

    public static void main(String args[]) {
        String text = "jlg%";
        if (!StringValidations.isPassword(text)) {

            System.out.print("Este texto " + text + " no anda bien, evite usar caracteres extraños...");

        } else {
                        System.out.print("Este texto " + text + " esta bién ;)...");
        }
    }
}
