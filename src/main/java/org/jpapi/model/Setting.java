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

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @adapter <a href="mailto:jlgranda81@gmail.com">José Luis Granda</a>
 *
 */
@Entity
@Table(name = "Setting")
@NamedQueries({ @NamedQuery(name = "Setting.findByName", query = "select s FROM Setting s WHERE s.name =?1 ORDER BY 1")
    /*@NamedQuery(name = "BussinesEntity.findBussinesEntityByParentIdAndType", query = "select m FROM Group JOIN g.members m WHERE g.id=:id and m.type=:type ORDER BY g.name")*/})
public class Setting extends PersistentObject<Setting> {

    private static final long serialVersionUID = -7485883311296510018L;
    private String value;

    public Setting() {
    }

    public Setting(String name, String value) {
        this.setName(name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
