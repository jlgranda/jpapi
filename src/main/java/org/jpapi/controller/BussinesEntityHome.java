/*
 * Copyright 2015 jlgranda.
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
package org.jpapi.controller;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.jpapi.model.BussinesEntity;


/**
 *
 * @author jlgranda
 * @param <E>
 */
public abstract class BussinesEntityHome<E> extends Home<EntityManager, E> implements Serializable {

    private static final long serialVersionUID = -8910921676468441272L;


    private boolean editionEnabled = true;
    /**
     * Bandera para detectar cambios
     */
    private boolean modified;

    protected BussinesEntity bussinesEntity;


    public boolean isEditionEnabled() {
        return editionEnabled;
    }

    public void setEditionEnabled(boolean editionEnabled) {
        this.editionEnabled = editionEnabled;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }


    @Override
    public void create() {
        super.create();
        if (getEntityManager() == null) {
            throw new IllegalStateException("entityManager is null");
        }
    }

    public boolean isManaged() {
        return getInstance() != null
                && getEntityManager().contains(getInstance());
    }

    public String update() {
        super.update(getInstance());
        getEntityManager().flush();
        updatedMessage();
        return "updated";
    }

    public String persist() {
        super.create(getInstance());
        getEntityManager().flush();
        createdMessage();
        return "persisted";
    }

    public String remove() {
        super.delete(getInstance());
        getEntityManager().flush();
        deletedMessage();
        return "removed";
    }

    @Override
    public E find() {
        if (getEntityManager().isOpen()) {
            E result = loadInstance();
            if (result == null) {
                result = handleNotFound();
            }
            return result;
        } else {
            return null;
        }
    }

    protected E loadInstance() {
        return getEntityManager().find(getEntityClass(), getId());
    }

    @Override
    protected String getPersistenceContextName() {
        return "entityManager";
    }

    @Override
    protected String getEntityName() {
        //return PersistenceProvider.instance().getName(getInstance(), getEntityManager());
        return "empty";
    }

    public BussinesEntity getBussinesEntity() {
        return bussinesEntity;
    }

    public void setBussinesEntity(BussinesEntity bussinesEntity) {
        this.bussinesEntity = bussinesEntity;
    }
    
    public E save(E _instance){
        this.setInstance(_instance);
        if (isIdDefined()){
            this.update();
        } else {
            this.persist();
        }
        
        return this.getInstance();
    }
    
    public E remove(E _instance){
        this.setInstance(_instance);
        if (isIdDefined()){
            this.remove();
        } 
        return this.getInstance();
    }
    
    //@Transactional
    public E save(Object id, E _instance){
        this.setId(id);
        this.setInstance(_instance);
        if (isIdDefined()){
            this.update();
        } else {
            this.persist();
        }
        return this.getInstance();
    }
    
    public E remove(Object id, E _instance){
        this.setId(id);
        this.setInstance(_instance);
        if (isIdDefined()){
            this.remove();
        } 
        return this.getInstance();
    }
    
    public long count(String namedQuery, Object... params) {
        return super.countByNamedQuery(namedQuery, params); 
    }
}
