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
package org.jpapi.model.management;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "Owner")
@DiscriminatorValue(value = "OWN")
@PrimaryKeyJoinColumn(name = "id")
public class Owner extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 117029006983960496L;
    @ManyToOne
    private Organization organization;
//    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
//    private List<Theme> themes ;
//    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
//    private  List<Diagnostic> diagnostic;
    
    public Owner() {
        //themes = new ArrayList<Theme>();
    }
        
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    public List<Theme> getThemes() {
//        return themes;
//    }
//
//    public void setThemes(List<Theme> themes) {
//        this.themes = themes;
//    }
//
//    public List<Diagnostic> getDiagnostic() {
//        return diagnostic;
//    }
//
//    public void setDiagnostic(List<Diagnostic> diagnostic) {
//        this.diagnostic = diagnostic;
//    }
//
//    
//    public boolean addTheme(Theme theme){
//        theme.setOwner(this);
//        return getThemes().add(theme);
//    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
                append(getType()).
                toHashCode();
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
        Owner other = (Owner) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }
    
     @Override
    public String getCanonicalPath(){
        StringBuilder path = new StringBuilder();       
        path.append(getOrganization().getCanonicalPath());
        return path.toString();
    }

    @Override
    public String toString() {
        /*return "org.eqaula.glue.model.management.Owner[ "
                + "id=" + getId() + ","
                + "name=" + getName() + ","
                + "type=" + getType() + ","
                + " ]";*/
        return getName();
    }
}
