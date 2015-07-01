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

import javax.persistence.Column;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author jlgranda
 * @param <E>
 */
@MappedSuperclass
public abstract class PersistentObject<E extends PersistentObject<E>> extends BaseObject<E> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

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

    
    @Override
    public String toString() {
        return String.valueOf(getId());
    }

}
