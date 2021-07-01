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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @adapter <a href="mailto:jlgranda81@gmail.com">José Luis Granda</a>
 *
 */
@Entity
@Table(name = "Setting")
@NamedQueries({ @NamedQuery(name = "Setting.findByName", query = "select s FROM Setting s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name = "Setting.findByNameAndOwnerIsNull", query = "select s FROM Setting s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name = "Setting.findByNameAndOwner", query = "select s FROM Setting s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
    @NamedQuery(name = "Setting.findByCodeType", query = "select s FROM Setting s WHERE s.codeType = ?1 ORDER BY 1"),
    @NamedQuery(name = "Setting.findByCodeTypeAndCategory", query = "select s FROM Setting s WHERE s.codeType = ?1 and s.category = ?2 ORDER BY 1")})
public class Setting extends PersistentObject<Setting> implements Comparable<Setting>, Serializable {

    private static final long serialVersionUID = -7485883311296510018L;
    
    private String category;
    
    private String label;
    
    private String value;
    
    @Column(nullable = true)
    private boolean overwritable = true; 

    public Setting() {
    }
    
     public Setting(String label, String name, String value) {
        this(name, value);
       this.label = label;
    }

    public Setting(String name, String value) {
        this.setName(name);
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public boolean isOverwritable() {
        return overwritable;
    }

    public void setOverwritable(boolean overwritable) {
        this.overwritable = overwritable;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31); // two randomly chosen prime numbers
        // if deriving: appendSuper(super.hashCode()).

        hcb.append(getName()).
                append(getCategory());

        return hcb.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Setting other = (Setting) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getName(), other.getName()).
                append(getCategory(), other.getCategory());

        return eb.isEquals();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("")
        .append(getId())
        .append(": ")
        .append(getCategory())
        .append(", ")
        .append(" ")
        .append(getName());
        return str.toString();
    }
    @Override
    public int compareTo(Setting other) {
        if (other.getLabel() == null || this.getLabel() == null){
            return -1;
        }
        return this.getLabel().compareTo(other.getLabel());
    }
}
