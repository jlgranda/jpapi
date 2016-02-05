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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;



/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "BUSSINESENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ENTITY_TYPE", discriminatorType = DiscriminatorType.STRING, length = 7)
@NamedQueries({
    @NamedQuery(name = "BussinesEntity.findByCode", query = "select b FROM BussinesEntity b WHERE b.code = ?1 ORDER BY 1"),
    @NamedQuery(name = "BussinesEntity.findByIds", query = "select b FROM BussinesEntity b WHERE b.id in (?1)"),
    @NamedQuery(name = "BussinesEntity.findByCodes", query = "select b FROM BussinesEntity b WHERE b.code in (?1)"),
    @NamedQuery(name = "BussinesEntity.findByCodesAndOwner", query = "select b FROM BussinesEntity b WHERE b.code in (?1) and b.owner = ?2 ORDER BY 1"),
    @NamedQuery(name = "BussinesEntity.findByCodeAndCodeType", query = "select b FROM BussinesEntity b WHERE b.code =?1 and b.codeType = ?2 ORDER BY 1"),
    @NamedQuery(name = "BussinesEntity.findBussinesEntityByGroup", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1"),
    @NamedQuery(name = "BussinesEntity.countByOwner", query = "select count(b) FROM BussinesEntity b WHERE b.owner = ?1"),
    
})
@XmlRootElement
public class BussinesEntity extends DeletableObject<BussinesEntity> {

    public static final String SEPARATOR= " » ";
    
    private static final long serialVersionUID = -3282665873474370357L;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "author", nullable = true)
    private Subject author;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "owner", nullable = true)
    private Subject owner;

    //Best practice http://java.dzone.com/articles/deterring-%E2%80%9Ctomany%E2%80%9D?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3a+javalobby/frontpage+%28Javalobby+/+Java+Zone%29
    //Replace ManyToMany fro OneToMany and link entity
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bussinesEntity", orphanRemoval = false,  fetch = FetchType.LAZY)
    private List<Membership> memberships = new ArrayList<Membership>();
    @ManyToOne
    private BussinesEntityType type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bussinesEntity", fetch = FetchType.LAZY)
    private List<BussinesEntityAttribute> attributes = new ArrayList<BussinesEntityAttribute>();
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = true)
    private Property property;
    
    @XmlTransient
    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public void setGroups(List<Group> groups) {
        //TODO this.groups = groups;
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Membership m : getMemberships()) {
            groups.add(m.getGroup());
        }

        return groups;
    }

    public Group getGroup(String name) {
        for (Group g : getGroups()) {
            if (name.equals(g.getName())) {
                return g;
            }
        }

        return null;
    }

    public List<Group> findGroups(final String... names) {
        List<Group> _buffer = new ArrayList<Group>();
        Group g = null;
        for (String name : names) {
            g = getGroup(name);
            if (g != null) {
                _buffer.add(g);
            }
        }
        return _buffer;
    }

    public List<Group> findGroupsFor(final String names) {
        return findGroups(names.split(","));
    }

    public void add(Group g) {
        Membership membershipt = new Membership();
        membershipt.setGroup(g);
        membershipt.setBussinesEntity(this);
        membershipt.setCreatedOn(Dates.now());
        membershipt.setLastUpdate(Dates.now());
        membershipt.setActivationTime(Dates.now());
        membershipt.setExpirationTime(Dates.addDays(Dates.now(), 364));
        if (!getMemberships().contains(membershipt)) {
            getMemberships().add(membershipt);
        }
    }

    public void remove(Group g) {
        /*if (groups.contains(g)) {
         this.getGroups().remove(g);
         }
         */
    }

    public boolean containsGroup(String name) {
        for (Group g : getGroups()) {
            if (name.equalsIgnoreCase(g.getName())) {
                return true;
            }
        }
        return false;
    }

    public Subject getAuthor() {
        return author;
    }

    public void setAuthor(Subject author) {
        this.author = author;
    }

    public Subject getOwner() {
        return owner;
    }

    public void setOwner(Subject owner) {
        this.owner = owner;
    }

    public BussinesEntityType getType() {
        return type;
    }

    public void setType(BussinesEntityType type) {
        this.type = type;
    }

    @XmlTransient
    public List<BussinesEntityAttribute> getAttributes() {
        Collections.sort(attributes);
        return attributes;
    }

    public void setAttributes(List<BussinesEntityAttribute> attributes) {
        for (BussinesEntityAttribute attr : attributes) {
            attr.setBussinesEntity(this);
        }
        this.attributes.addAll(attributes);
    }

    public void addBussinesEntityAttribute(BussinesEntityAttribute attribute) {
        attribute.setBussinesEntity(this);
        this.attributes.add(attribute);
    }

    public boolean containsBussinesEntityAttribute(String name) {
        for (BussinesEntityAttribute attr : getAttributes()) {
            if (name.equalsIgnoreCase(attr.getName())) {
                return true;
            }
        }
        return false;
    }

    public BussinesEntityAttribute getBussinessEntityAttribute(String name) {
        for (BussinesEntityAttribute attr : getAttributes()) {
            if (name.equalsIgnoreCase(attr.getName())) {
                return attr;
            }
        }
        return null;
    }

    public String findAttributeValue(String name) {
        if ("code".equalsIgnoreCase(name)) {
            return getCode();
        } else if ("name".equalsIgnoreCase(name)) {
            return getName();
        } else {
            return (getBussinessEntityAttribute(name) != null && getBussinessEntityAttribute(name).getValue() != null ? getBussinessEntityAttribute(name).getValue().toString() : "undefined!");
        }
    }

    /*
     * Retrieve all attributes for strcuture name list
     */
    public List<BussinesEntityAttribute> getBussinessEntityAttributes(String... structureNames) {
        List<BussinesEntityAttribute> _buffer = new ArrayList<BussinesEntityAttribute>();
        for (BussinesEntityAttribute a : getAttributes()) {
            for (String sn : structureNames) {
                if (sn.equalsIgnoreCase(a.getProperty().getStructure().getBussinesEntityType().getName())) {
                    _buffer.add(a);
                }
            }
        }
        return _buffer;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    /**
     * Return for each BussinesEntity te canonical path ....
     * @return 
     */
    @Transient
    public String getCanonicalPath(){
        return getName();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
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
        BussinesEntity other = (BussinesEntity) obj;
        return new org.apache.commons.lang.builder.EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getCode(), other.getCode()).
                isEquals();
    }

    //-------------------------------------------------------------------------
    // Attributes management and build
    //-------------------------------------------------------------------------
//    public void buildAttributes(BussinesEntityService bes) {
//        if (getType() == null) {
//            return;
//        }
//        for (Structure s : getType().getStructures()) {
//            for (Property a : s.getProperties()) {
//                if (a.getType().equals(Group.class.getName())) {
//                    buildGroup(a, bes);
//                } else if (a.getType().equals(Structure.class.getName())) {
//                    loadStructure(a, bes);
//                } else {
//                    this.addBussinesEntityAttribute(buildAtribute(a));
//                }
//            }
//        }
//    }
//
//    public void buildAttributes(String name, BussinesEntityService bes) {
//        BussinesEntityType _type = bes.findBussinesEntityTypeByName(name);
//        if (_type == null) {
//            return;
//        }
//        for (Structure s : _type.getStructures()) {
//            for (Property a : s.getProperties()) {
//                if (a.getType().equals(Group.class.getName())) {
//                    //TODO support for groups into groups
//                } else if (a.getType().equals(Structure.class.getName())) {
//                    loadStructure(a, bes);
//                } else {
//                    this.addBussinesEntityAttribute(buildAtribute(a));
//                }
//            }
//        }
//    }
//
//    protected void loadStructure(Property a, BussinesEntityService bes) {
//        BussinesEntityType _type = bes.findBussinesEntityTypeByName(a.getName());
//        if (_type == null) {
//            return;
//        }
//        for (Structure s : _type.getStructures()) {
//            for (Property attr : s.getProperties()) {
//                this.addBussinesEntityAttribute(buildAtribute(attr));
//            }
//        }
//    }
//
//    protected Group buildGroup(Property attr, BussinesEntityService bes) {
//        Date now = Calendar.getInstance().getTime();
//        Group g = null;
//        if (!this.containsGroup(attr.getName())) {
//            g = new Group();
//            g.setName(attr.getName());
//            g.setCreatedOn(now);
//            g.setLastUpdate(now);
//            g.setActivationTime(now);
//            g.setExpirationTime(Dates.addDays(now, 364));
//            g.setAuthor(null); //TODO Establecer al usuario actual
//            g.setProperty(attr); //almacenar en memoria para dibujar GUI
//            g.buildAttributes(bes);
//            this.add(g);
//        }
//        return g;
//    }
//
//    protected BussinesEntityAttribute buildAtribute(Property p) {
//        BussinesEntityAttribute attribute = new BussinesEntityAttribute();
//        attribute.setName(p.getName());
//        attribute.setType(p.getType());
//        if ("java.lang.String[]".equals(p.getType())) {
//            attribute.setValue((Serializable) Lists.findDefaultValue(p.getValue().toString()));
//        } else {
//            attribute.setValue((Serializable) p.getValue());
//        }
//        attribute.setProperty(p); //Relaciona los objetos de forma directa
//        return attribute;
//    }
//
//    /**
//     * Return list of BussinesEntityAttribute for BussinesEntityType names lists
//     *
//     * @param names list of names for BussinessEntityType
//     * @return list of BussinesEntityAttribute for BussinesEntityType names
//     * lists
//     */
//    public List<BussinesEntityAttribute> findBussinesEntityAttribute(final String names) {
//        if (names == null) {
//            return new ArrayList<BussinesEntityAttribute>();
//        }
//        List<BussinesEntityAttribute> temp = getBussinessEntityAttributes(names.split(","));
//        return temp;
//    }
    
}
