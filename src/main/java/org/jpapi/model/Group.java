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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.jpapi.util.Dates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author paul.coronel
 * @adaptedby jlgranda
 *
 */
@Entity
@Table(name = "GGROUP")
@DiscriminatorValue(value = "GRP")
@PrimaryKeyJoinColumn(name = "groupId")
/*
 * Group querynamed
 */
@NamedQueries({
    /*@NamedQuery(name = "Group.findGroupByBussinessEntityIdAndType", query = "select g FROM BussinesEntity "
     + "o JOIN o.groups g WHERE o.id=:id and g.type=:type"),
     @NamedQuery(name = "Group.findGroupByType", query = "select g FROM Group "
     + " g where g.type=:type"),
     @NamedQuery(name = "Group.findParentsByType", query = "select _group from Group _group join _group.members _member where _member=:member and _group.type = :type"),
     @NamedQuery(name = "Group.findGroupByNameAndParent", query = "select _member from Group _group join _group.members _member "
     + " where _group.id = :id and lower(_member.name) LIKE lower(:name)"),
     @NamedQuery(name = "Group.findGroupByNameAndParentAndType", query = "select _member from Group _group join _group.members _member "
     + " where _member.type=:type and _group.id = :id and lower(_member.name) LIKE lower(:name)"),
     @NamedQuery(name = "Group.findGroupById", query = "from Group g where g.id = :id"),
     @NamedQuery(name = "Group.findGroupsByType", query = "from Group g where g.type = :type"),
     @NamedQuery(name = "Group.findGroupByName", query = "from Group g where lower(g.name) = lower(:name) and g.type = :type"),
    @NamedQuery(name = "Group.findByBussinesEntityIdAndPropertyId", query = "Select m.group from BussinesEntity be JOIN be.memberships m where m.bussinesEntity.id = :bussinesEntityId and m.group.property.id = :propertyId"),*/
    @NamedQuery(name = "Group.findById", query = "select g FROM Group g WHERE g.id = ?1"),
    @NamedQuery(name = "Group.findByGroup", query = "select g.name, g.icon, g.type, g.createdOn FROM Group g WHERE g.createdOn>=?1 AND g.createdOn<=?2 ORDER BY 2 DESC"),
    @NamedQuery(name = "Group.findLastGroup", query = "select g FROM Group g ORDER BY g.id DESC")
    
})
public class Group extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 5665775223006691311L;
    private static Logger log = LoggerFactory.getLogger(Group.class);
    
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    public enum Type {
        GROUP,
        LABEL,
        PRODUCT,
        CATEGORY,
        WAREHOUSE,
        TRADEMARK,
        APPICATION;

        private Type() {
        }
    }
    /*
    Tipo de grupo
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)    
    private Group.Type groupType;

    /*
    HTML code color valid
     */
    @Column
    private String color;

    /*
    Bootstraps valid icon
     */
    @Column
    private String icon;

    @Column
    private String module;

    public Group() {
        super();
        setGroupType(Type.GROUP); //type by default
    }

    public Group(String code, String name) {
        super();
        setCode(code);
        setName(name);
        setGroupType(Type.GROUP);
    }
    
    public Type getGroupType() {
        return groupType;
    }    
    public void setGroupType(Type groupType) {
        this.groupType = groupType;
    }
    
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<BussinesEntity> getMembers() {
        List<BussinesEntity> members = new ArrayList<>();
        for (Membership m : getMemberships()) {
            members.add(m.getBussinesEntity());
        }

        return members;
    }

    public void setMembers(List<BussinesEntity> members) {
        // this.members = members;
    }

    public List<BussinesEntity> findOtherMembers(BussinesEntity me) {

        List<BussinesEntity> _buffer = new ArrayList<>();

        if (me == null) {
            return _buffer;
        }

        for (BussinesEntity e : getMembers()) {
            if (me.getId() != null && !me.getId().equals(e.getId())) {
                _buffer.add(e);
            }
        }
        return _buffer;
    }

    public void add(BussinesEntity be) {
        Date now = Calendar.getInstance().getTime();
        Membership membershipt = new Membership();
        membershipt.setCreatedOn(now);
        membershipt.setLastUpdate(now);
        membershipt.setActivationTime(now);
        membershipt.setExpirationTime(Dates.addDays(now, 364));
        membershipt.setGroup(this);
        membershipt.setBussinesEntity(be);

        if (!getMemberships().contains(membershipt)) {
            //log.infof("Add to membershipt {0}. Group {1}, BussinesEntity {2}", membershipt, this, be);
            getMemberships().add(membershipt);
        }
    }

    public void remove(BussinesEntity be) {
        System.out.print(">>> Intentando remover: " + be + " desge grupo " + this);
        Membership membershipt = new Membership();
        membershipt.setGroup(this);
        membershipt.setBussinesEntity(be);
        if (getMemberships().contains(membershipt)) {
//            log.infof("remove to membershipt {0}. Group {1}, BussinesEntity {2}", membershipt, this, be);
            getMemberships().remove(membershipt);
        }
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
                append(getGroupType()).
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
        Group other = (Group) obj;
        return new org.apache.commons.lang.builder.EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getGroupType(), other.getGroupType()).
                isEquals();
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
} 