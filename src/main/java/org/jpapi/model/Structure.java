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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author jlgranda
 */
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "Structure.findForId", query = "SELECT s FROM Structure s WHERE s.id = :id")
})
public class Structure extends DeletableObject<Structure> implements Serializable {

    private static final long serialVersionUID = -1939876087269734534L;
    @ManyToOne
    private BussinesEntityType bussinesEntityType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "structure", fetch = FetchType.LAZY)
    @OrderBy(value = "sequence")
    private List<Property> properties;

    public BussinesEntityType getBussinesEntityType() {
        return bussinesEntityType;
    }

    public void setBussinesEntityType(BussinesEntityType bussinesEntityType) {
        this.bussinesEntityType = bussinesEntityType;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public boolean addProperty(Property property) {
        property.setStructure(this);
        return this.properties.add(property);
    }

    public void setProperties(List<Property> properties) {
        for (Property p : properties) {
            p.setStructure(this);
        }
        this.properties = properties;
    }

    public boolean removeProperty(Property property) {       
        
        return this.properties.remove(property);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Structure)) {
            return false;
        }
        Structure other = (Structure) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.eqaula.glue.model.Structure[ id=" + this.getId() + " ]";
    }
}
