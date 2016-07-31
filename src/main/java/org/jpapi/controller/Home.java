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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.jpapi.controller.Expressions.ValueExpression;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.BussinesEntity_;
import org.jpapi.model.Group;
import org.jpapi.model.Membership;
import org.jpapi.model.Membership_;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.QueryData;
import org.jpapi.util.QuerySortOrder;

/**
 * Seam 2 Home Class Clone
 *
 * @author jlgranda
 *
 */
public abstract class Home<T, E> extends MutableController<T> implements Serializable {

    private Object id;
    protected E instance;
    private Class<E> entityClass;
    protected Expressions.ValueExpression newInstance;
    private String deletedMessage = "Successfully deleted";
    private String createdMessage = "Successfully created";
    private String updatedMessage = "Successfully updated";

    protected void updatedMessage() {
        debug("updated entity #0 #1", getEntityClass().getName(), getId());
        //getFacesMessages().addFromResourceBundleOrDefault( SEVERITY_INFO, getUpdatedMessageKey(), getUpdatedMessage() );
    }

    protected void deletedMessage() {
        debug("deleted entity #0 #1", getEntityClass().getName(), getId());
        //getFacesMessages().addFromResourceBundleOrDefault( SEVERITY_INFO, getDeletedMessageKey(), getDeletedMessage() );
    }

    protected void createdMessage() {
        debug("created entity #0 #1", getEntityClass().getName(), getId());
        //getFacesMessages().addFromResourceBundleOrDefault( SEVERITY_INFO, getCreatedMessageKey(), getCreatedMessage() );
    }

    public void create() {
        if (getEntityClass() == null) {
            throw new IllegalStateException("entityClass is null");
        }
    }

    protected E getInstance() {
        if (instance == null) {
            initInstance();
        }
        return instance;
    }

    protected void clearInstance() {
        setInstance(null);
        setId(null);
    }

    protected void initInstance() {
        if (isIdDefined()) {
            if (true /*!isTransactionMarkedRollback()*/) {
                //we cache the instance so that it does not "disappear"
                //after remove() is called on the instance
                //is this really a Good Idea??
                setInstance(find());
            }
        } else {
            setInstance(createInstance());
        }
    }

    protected E find() {
        return findById(getEntityClass(), (Long) getId());
    }

    protected E handleNotFound() {
        //throw new EntityNotFoundException( getId(), getEntityClass() );
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public E createInstance() {
        if (newInstance != null) {
            return (E) newInstance.getValue();
        } else if (getEntityClass() != null) {
            try {
                return getEntityClass().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    protected Class<E> getEntityClass() {
        if (entityClass == null) {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) type;
                entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
            } else {
                throw new IllegalArgumentException("Could not guess entity class by reflection");
            }
        }
        return entityClass;
    }

    public void setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected Object getId() {
        return id;
    }

    protected void setId(Object id) {
        if (setDirty(this.id, id)) {
            setInstance(null);
        }
        this.id = id;
    }

    protected void assignId(Object id) {
        setDirty(this.id, id);
        this.id = id;
    }

    protected boolean isIdDefined() {
        return getId() != null && !"".equals(getId());
    }

    protected void setInstance(E instance) {
        setDirty(this.instance, instance);
        this.instance = instance;
    }

    public Expressions.ValueExpression getNewInstance() {
        return newInstance;
    }

    public void setNewInstance(ValueExpression newInstance) {
        this.newInstance = newInstance;
    }

    public String getCreatedMessage() {
        return createdMessage;
    }

    public void setCreatedMessage(String createdMessage) {
        this.createdMessage = createdMessage;
    }

    public String getDeletedMessage() {
        return deletedMessage;
    }

    public void setDeletedMessage(String deletedMessage) {
        this.deletedMessage = deletedMessage;
    }

    public String getUpdatedMessage() {
        return updatedMessage;
    }

    public void setUpdatedMessage(String updatedMessage) {
        this.updatedMessage = updatedMessage;
    }

    protected String getMessageKeyPrefix() {
        String className = getEntityClass().getName();
        return className.substring(className.lastIndexOf('.') + 1) + '_';
    }

    protected String getCreatedMessageKey() {
        return getMessageKeyPrefix() + "created";
    }

    protected String getUpdatedMessageKey() {
        return getMessageKeyPrefix() + "updated";
    }

    protected String getDeletedMessageKey() {
        return getMessageKeyPrefix() + "deleted";
    }

    protected String getSimpleEntityName() {
        String name = getEntityName();
        return name.lastIndexOf(".") > 0 && name.lastIndexOf(".") < name.length() ? name.substring(name.lastIndexOf(".") + 1, name.length()) : name;
    }

    protected abstract String getEntityName();
    private static final long serialVersionUID = 7520839069908084915L;

    /**
     * ****************************************************
     * Adapted from PersistenceUtil
     *****************************************************
     */
    protected <E> long count(final Class<E> type) {
        CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(type)));
        return getEntityManager().createQuery(cq).getSingleResult();
    }

    protected <E> long countByNamedQuery(final String namedQueryName, final Object... params) {

        Query query = getEntityManager().createNamedQuery(namedQueryName);
        int i = 1;
        for (Object p : params) {
            query.setParameter(i++, p);
        }

        try {
            return ((Number) query.getSingleResult()).intValue();
        } catch (javax.persistence.NoResultException nre) {
            return -1L;
        }
    }

    protected <E> void create(final E entity) {
        getEntityManager().persist(entity);
    }

    protected <E> void update(final E entity) {
        if (getEntityManager() == null) {
            throw new IllegalStateException("Must initialize EntityManager before using Services!");
        }
        getEntityManager().merge(entity);
    }

    protected <E> void refresh(final E entity) {
        getEntityManager().refresh(entity);
    }

    protected <E> void delete(final E entity) throws NoResultException {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    protected <E> E deleteById(final Class<E> type, final Long id) throws NoResultException {
        E object = findById(type, id);
        delete(object);
        return object;
    }

    @SuppressWarnings("unchecked")
    protected <E> E findById(final Class<E> type, final Long id) throws NoResultException {
        Class<?> clazz = getObjectClass(type);
        E result = (E) getEntityManager().find(clazz, id);
        if (result == null) {
            throw new NoResultException("No object of type: " + type + " with ID: " + id);
        }
        return result;
    }

    protected Class<?> getObjectClass(final Object type) throws IllegalArgumentException {
        Class<?> clazz = null;
        if (type == null) {
            throw new IllegalArgumentException("Null has no type. You must pass an Object");
        } else if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        } else {
            clazz = type.getClass();
        }
        return clazz;
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(final String namedQueryName) {
        return findByNamedQuery(namedQueryName, 0);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(final String namedQueryName, int limit) {
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQuery(final String namedQueryName, final Object... params) {
        return findByNamedQueryWithLimit(namedQueryName, 0, params);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> findByNamedQueryWithLimit(final String namedQueryName, final int limit, final Object... params) {
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        int i = 1;
        for (Object p : params) {
            query.setParameter(i++, p);
        }
        query.setMaxResults(limit);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> findObjectsByNamedQueryWithLimit(final String namedQueryName, final int limit, final Object... params) {
        Query query = getEntityManager().createNamedQuery(namedQueryName, Object[].class);
        int i = 1;
        for (Object p : params) {
            query.setParameter(i++, p);
        }
        query.setMaxResults(limit);
        return query.getResultList();
    }

    protected <E> List<E> findAll(final Class<E> type) {
        CriteriaQuery<E> query = getEntityManager().getCriteriaBuilder().createQuery(type);
        query.from(type);
        return getEntityManager().createQuery(query).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <E> E findUniqueByNamedQuery(final String namedQueryName) throws NoResultException {
        return (E) getEntityManager().createNamedQuery(namedQueryName).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public <E> E findUniqueByNamedQuery(final String namedQueryName, final Object... params)
            throws NoResultException {
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        int i = 1;
        for (Object p : params) {
            query.setParameter(i++, p);
        }

        try {
            return (E) query.getSingleResult();
        } catch (javax.persistence.NoResultException nre) {
            return null;
        }
    }

    public <E> E getSingleResult(final CriteriaQuery<E> query) {
        return this.<E>getTypedSingleResult(query);
    }

    public <E> E getTypedSingleResult(final CriteriaQuery<E> query) {
        try {
            return getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <E> List<E> getResultList(final CriteriaQuery<E> query) {
        return getEntityManager().createQuery(query).getResultList();
    }

    public <E> List<E> getResultList(final CriteriaQuery<E> query,
            int maxresults, int firstresult) {
        return getEntityManager().createQuery(query).setMaxResults(maxresults)
                .setFirstResult(firstresult).getResultList();
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    public TypedQuery<?> createQuery(CriteriaQuery<?> criteriaQuery) {
        return getEntityManager().createQuery(criteriaQuery);
    }

    public E find(final long id) {
        return getEntityManager().find(entityClass, id);
    }

    public QueryData<E> find(int start, int end, String sortField,
            QuerySortOrder order, Map<String, Object> filters) {

        QueryData<E> queryData = new QueryData<>();

        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<E> c = cb.createQuery(entityClass);
        Root<E> root = c.from(entityClass);
        c.select(root);

        CriteriaQuery<Long> countQ = cb.createQuery(Long.class);
        Root<E> rootCount = countQ.from(entityClass);
        countQ.select(cb.count(rootCount));

        List<Predicate> criteria = new ArrayList<>();
        List<Predicate> predicates = null;
        if (filters != null) {
            for (String filterProperty : filters.keySet()) {
                Object filterValue = filters.get(filterProperty);
                if ("tag".equalsIgnoreCase(filterProperty)) {
                    Root<BussinesEntity> bussinesEntity = (Root<BussinesEntity>) root;

                    SetJoin<BussinesEntity, Membership> joinBussinesEntity = bussinesEntity.join(BussinesEntity_.memberships, JoinType.LEFT);
                    Join<Membership, Group> joinMembershipGroup = joinBussinesEntity.join(Membership_.group, JoinType.LEFT);

                    //Agregar relación a rootCount
                    SetJoin<BussinesEntity, Membership> joinCountBussinesEntity = ((Root<BussinesEntity>) rootCount).join(BussinesEntity_.memberships, JoinType.LEFT);
                    joinCountBussinesEntity.join(Membership_.group, JoinType.LEFT);

                    Path<String> groupPath = joinMembershipGroup.get(BussinesEntity_.code); // mind these Path objects
                    ParameterExpression<String> pexpGroup = cb.parameter(String.class,
                            filterProperty);
                    Predicate predicate = cb.equal(groupPath, pexpGroup);
                    criteria.add(predicate);
                } else if ("keyword".equalsIgnoreCase(filterProperty)) {
                    Root<BussinesEntity> bussinesEntity = (Root<BussinesEntity>) root;

                    Join<BussinesEntity, Subject> joinBussinesEntity = bussinesEntity.join(BussinesEntity_.author, JoinType.LEFT);

                    //Agregar relación a rootCount
                    ((Root<BussinesEntity>) rootCount).join(BussinesEntity_.author, JoinType.LEFT);

                    Path<String> authorPath = joinBussinesEntity.get(BussinesEntity_.name); // mind these Path objects
                    Path<String> codePath = bussinesEntity.get(BussinesEntity_.code); // mind these Path objects
                    ParameterExpression<String> pexpAuthor = cb.parameter(String.class,
                            "author");
                    ParameterExpression<String> pexpCode = cb.parameter(String.class,
                            "code");
                    Predicate predicate = cb.or(cb.like(cb.lower(authorPath), pexpAuthor), cb.like(cb.lower(codePath), pexpCode));
                    criteria.add(predicate);
                } else if (filterValue instanceof Map) { //has multiples values
                    predicates = new ArrayList<>();
                    for (Object key : ((Map) filterValue).keySet()) {
                        Object value = ((Map) filterValue).get((String) key);
                        //Verify data content for build
                        if (value instanceof Date) {
                            Path<Date> filterPropertyPath = root.<Date>get(filterProperty);
                            ParameterExpression<Date> pexpStart = cb.parameter(Date.class,
                                    "start");
                            ParameterExpression<Date> pexpEnd = cb.parameter(Date.class,
                                    "end");
                            Predicate predicate = cb.between(filterPropertyPath, pexpStart, pexpEnd);
                            criteria.add(predicate);
                            break;
                        } else if (value instanceof String) { //busqueda de palabra clave en varias columnas
                            Path<String> filterPropertyPath = root.<String>get((String) key);
                            ParameterExpression<String> pexp = cb.parameter(String.class,
                                    (String) key);
                            Predicate predicate = cb.like(cb.lower(filterPropertyPath), pexp);
                            predicates.add(predicate);
                        }
                    }
                    //Si se han definido 
                    if (!predicates.isEmpty()) {
                        Predicate[] array = new Predicate[predicates.size()];
                        criteria.add(cb.or(predicates.toArray(array)));
                    }
                } else if (filterValue instanceof List) { //has multiples values for a column
                    Path<String> filterPropertyPath = root.<String>get((String) filterProperty);
                    ParameterExpression<List> pexp = cb.parameter(List.class,
                            filterProperty);
                    Predicate predicate = filterPropertyPath.in(pexp);
                    criteria.add(predicate);

                } else if (filterValue instanceof String) {
                    ParameterExpression<String> pexp = cb.parameter(String.class,
                            filterProperty);
                    Predicate predicate = cb.like(cb.lower(root.<String>get(filterProperty)), pexp);
                    criteria.add(predicate);
                } else {
                    ParameterExpression<?> pexp = cb.parameter(filterValue != null ? filterValue.getClass() : Object.class,
                            filterProperty);
                    Predicate predicate = null;
                    if (filterValue == null) {
                        predicate = cb.isNull(root.get(filterProperty));
                    } else {
                        predicate = cb.equal(root.get(filterProperty), pexp);
                    }

                    criteria.add(predicate);
                }
            }
        }

        if (criteria.size() == 1) {
            c.where(criteria.get(0));
            countQ.where(criteria.get(0));
        } else if (criteria.size() > 1) {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
            countQ.where(cb.and(criteria.toArray(new Predicate[0])));
        }

        if (sortField != null) {
            List<Order> orders = new ArrayList<>();
            Path<String> path = null;
            if (!sortField.contains(",")) {
                path = root.get(sortField);
                if (order == QuerySortOrder.ASC) {
                    orders.add(cb.asc(path));
                } else {
                    orders.add(cb.desc(path));
                }
                
            } else {
                for (String field : sortField.split(",")) {
                    path = root.get(field.trim());
                    if (order == QuerySortOrder.ASC) {
                        orders.add(cb.asc(path));
                    } else {
                        orders.add(cb.desc(path));
                    }
                }
            }
            
            c.orderBy(orders);
            
        }

        TypedQuery<E> q = (TypedQuery<E>) createQuery(c);
        q.setHint("org.hibernate.cacheable", true);
        TypedQuery<Long> countquery = (TypedQuery<Long>) createQuery(countQ);
        countquery.setHint("org.hibernate.cacheable", true);

        if (filters != null) {
            for (String filterProperty : filters.keySet()) {
                Object filterValue = filters.get(filterProperty);
                //System.err.println("---------------> filterProperty: " + filterProperty + ", filterValue: " + filterValue);
                if ("tag".equalsIgnoreCase(filterProperty)) {
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                } else if ("keyword".equalsIgnoreCase(filterProperty)) {
                    filterValue = "%" + filterValue.toString().toLowerCase() + "%";
                    q.setParameter("author", filterValue);
                    q.setParameter("code", filterValue);
                    countquery.setParameter("author", filterValue);
                    countquery.setParameter("code", filterValue);
                } else if (filterValue instanceof Map) {
                    for (Object key : ((Map) filterValue).keySet()) {
                        Object value = ((Map) filterValue).get((String) key);
                        //Verify data content for build
                        if (value instanceof Date) {
                            q.setParameter(q.getParameter((String) key, Date.class), (Date) value, TemporalType.TIMESTAMP);
                            countquery.setParameter(q.getParameter((String) key, Date.class), (Date) value, TemporalType.TIMESTAMP);
                        } else {
                            String _filterValue = "%" + (String) value + "%";
                            q.setParameter(q.getParameter((String) key, String.class), _filterValue);
                            countquery.setParameter(q.getParameter((String) key, String.class), _filterValue);
                        }
                    }
                } else if (filterValue instanceof List) { //has multiples values for a column
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                } else if (filterValue instanceof String) {
                    filterValue = "%" + filterValue + "%";
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                } else if (filterValue != null) {
                    //System.err.println("--------------->Setted filterProperty: " + filterProperty + ", filterValue: " + filterValue);
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                }
            }
        }

        if (start != -1 && end != -1) {
            q.setMaxResults(end - start);
            q.setFirstResult(start);
        }

        queryData.setResult(q.getResultList());
        Long totalResultCount = countquery.getSingleResult();
        queryData.setTotalResultCount(totalResultCount);

        return queryData;
    }
}
