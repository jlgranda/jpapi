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
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author jlgranda
 * @param <E>
 */
@MappedSuperclass
public abstract class PersistentObject<E extends PersistentObject<E>> implements JPAPIPersistable, Serializable {

    private static final long serialVersionUID = -1272280183658745494L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;
    @Column(nullable = true)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CodeType codeType;
    @Column(nullable = true)
    private String name;
    @Column(nullable = true, length = 2048)
    private String description;
    @Version
    @Column(name = "version", nullable = false)
    private int version = 0;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdate", nullable = false)
    private Date lastUpdate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn", updatable = false, nullable = false)
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date activationTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date expirationTime;
    @Column(nullable = true)
    private Integer priority;
    @Column(nullable = true)
    private Boolean active;
    @Column(nullable = true)
    private String status;

    @PrePersist
    void prePersist() {
        createdOn = new Date();
        active = true;
    }

    @PreUpdate
    void preUpdate() {
        lastUpdate = new Date();
    }

    protected static boolean getBooleanValue(final Boolean value) {
        return Boolean.valueOf(String.valueOf(value));
    }

    public Long getId() {
        return id;
    }

    public boolean isPersistent() {
        return getId() != null;
    }

    @SuppressWarnings("unchecked")
    public E setId(final Long id) {
        if (this.id != null) {
            throw new IllegalStateException("Cannot alter immutable ID of persistent object with id: " + id);
        }
        this.id = id;
        return (E) this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    @SuppressWarnings("unchecked")
    public E setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return (E) this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    @SuppressWarnings("unchecked")
    public E setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
        return (E) this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Transient
    public boolean isExpired() {
        Calendar now = Calendar.getInstance();
        return now.after(getExpirationTime());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
