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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author jlgranda
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "BussinesEntityAttribute.findByBussinesEntityIdAndTypes",
    query = "select a FROM BussinesEntityAttribute a JOIN a.property p "
    + "WHERE p.structure.bussinesEntityType.name = :bussinesEntitytypeName "
    + "AND p.type IN (:types) "
    + "ORDER BY p.id"),
    @NamedQuery(name = "BussinesEntityAttribute.countRequiredProperties",
    query = "select count(a)  FROM BussinesEntityAttribute a WHERE a.bussinesEntity.id = :bussinesEntityId AND a.property.structure.bussinesEntityType.name = :bussinesEntityTypeName AND a.property.required = true GROUP BY a.property.structure"),
    @NamedQuery(name = "BussinesEntityAttribute.countCompletedRequiredProperties",
    query = "select count(a)  FROM BussinesEntityAttribute a WHERE a.bussinesEntity.id = :bussinesEntityId AND a.property.structure.bussinesEntityType.name = :bussinesEntityTypeName AND a.property.required = true AND a.valueByteArray != a.property.valueByteArray GROUP BY a.property.structure")
})
@XmlRootElement
public class BussinesEntityAttribute implements Serializable, Comparable<BussinesEntityAttribute> {

    private static final long serialVersionUID = 7807041724651919898L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private BussinesEntity bussinesEntity;
    private String name;
    @Transient
    private Object value;
    @Basic(fetch = FetchType.LAZY)
    private byte[] valueByteArray;
    private String type;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    private String stringValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BussinesEntity getBussinesEntity() {
        return bussinesEntity;
    }

    public void setBussinesEntity(BussinesEntity bussinesEntity) {
        this.bussinesEntity = bussinesEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected byte[] getValueByteArray() {
        return valueByteArray;
    }

    protected void setValueByteArray(byte[] valueByteArray) {
        this.valueByteArray = valueByteArray;
    }

    public Object getValue() {
        return SerializationUtils.deserialize(getValueByteArray());
    }

    public void setValue(Object value) {
        setValueByteArray(SerializationUtils.serialize((Serializable) value));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
                append(getType()).
                append(getProperty()).
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
        BussinesEntityAttribute other = (BussinesEntityAttribute) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                append(getProperty(), other.getProperty()).
                isEquals();
    }

    @Override
    public String toString() {
        return "org.eqaula.glue.model.BussinesEntityAttribute[ "
                + "id=" + id + ","
                + "name=" + name + ","
                + "type=" + type + ","
                + " ]";
    }

    @Override
    public int compareTo(BussinesEntityAttribute o) {
        return (int)(this.getProperty().getSequence() - o.getProperty().getSequence());
    }
}
