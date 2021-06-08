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

public abstract class Assert
{

   public static void isTrue(final boolean expression)
   {
      isTrue(expression, "Expected [true], but was [false]");
   }

   public static void isTrue(final boolean test, final String message)
   {
      if (!test)
      {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notNull(final Object o, final String message)
   {
      if (o == null)
      {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notNull(final Object o)
   {
      notNull(o, "Expected [not null], but was [null]");
   }
}
