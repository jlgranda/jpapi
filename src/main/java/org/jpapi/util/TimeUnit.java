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
public enum TimeUnit {

    CENTURY(3155692597470L, "Century"),
    DAY(1000L * 60L * 60L * 24L, "Day"),
    DECADE(315569259747L, "Decade"),
    HOUR(1000L * 60L * 60L, "Hour"),
    JUSTNOW(1000L * 60L * 5L, "JustNow"),
    MILLENNIUM(31556926000000L, "Millenium"),
    MILLISECOND(1, "Millisecond"),
    MINUTE(1000L * 60L, "Minute"),
    MONTH(2629743830L, "Month"),
    SECOND(1000L, "Second"),
    WEEK(1000L * 60L * 60L * 24L * 7L, "Week"),
    YEAR(2629743830L * 12L, "Year");
    private long millisPerUnit;
    private String resourceKeyPrefix;

    private TimeUnit(long millisPerUnit, String resourceKeyPrefix) {
        this.millisPerUnit = millisPerUnit;
        this.resourceKeyPrefix = resourceKeyPrefix;
    }

    public String getResourceKeyPrefix() {
        return resourceKeyPrefix;
    }
    
    public long getMillisPerUnit(){
        return millisPerUnit;
    }
}
