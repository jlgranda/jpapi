/*
 * Copyright 2012 jlgranda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jpapi.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import javax.persistence.EmbeddedId;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "MEMBERSHIP")
public class Membership extends BaseObject<Membership> implements Serializable {
    private static final long serialVersionUID = -7034844401678558748L;
    
    @EmbeddedId
    private Id id = new Id();
    
    @ManyToOne
    @JoinColumn(name = "group_id", insertable=false, updatable=false, nullable=false)
    Group group;
    
    @ManyToOne
    @JoinColumn(name = "bussinesentity_id", insertable=false, updatable=false, nullable=false)
    BussinesEntity bussinesEntity;

    public Membership() {
        super();
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        this.getId().groupId = group.getId();
    }

    public BussinesEntity getBussinesEntity() {
        return bussinesEntity;
    }

    public void setBussinesEntity(BussinesEntity bussinesEntity) {
        this.bussinesEntity = bussinesEntity;
        this.getId().bussinesEntityId = bussinesEntity.getId();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                append(this.id).
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
        Membership other = (Membership) obj;
        return new EqualsBuilder().
                append(this.id, other.id).
                isEquals();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 6048903634744057702L;


        @Column(name = "group_id")
        private Long groupId;
        @Column(name = "bussinesentity_id")
        private Long bussinesEntityId;

        public Id() {
        }

        public Id(Long groupId, Long bussinesEntityId) {
            this.groupId = groupId;
            this.bussinesEntityId = bussinesEntityId;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                    // if deriving: appendSuper(super.hashCode()).
                    append(this.groupId).
                    append(this.bussinesEntityId).
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
            Id other = (Id) obj;
            return new EqualsBuilder().
                    // if deriving: appendSuper(super.equals(obj)).
                    append(this.bussinesEntityId, other.bussinesEntityId).
                    append(this.groupId, other.groupId).
                    isEquals();
        }
        
        @Override
        public String toString(){
            return ToStringBuilder.reflectionToString(this);
        }

    }
    
}
