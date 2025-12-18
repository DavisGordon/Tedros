package org.tedros.server.cdi.bo;

import java.util.List;

import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;

/**
 * Abstract base implementation of {@link ITGenericBO}.
 * Delegates most operations to the underlying {@link ITGenericEAO} and adds
 * user-based filtering (by createdByUserId) for methods that accept a userId parameter.
 *
 * @param <E> the entity type extending {@link ITEntity}
 */
public abstract class TGenericBO<E extends ITEntity> implements ITGenericBO<E> {

    @Override
    public List<E> search(TSelect<E> sel) {
        return getEao().search(sel);
    }

    @Override
    public List<E> search(Long userId, TSelect<E> sel) {
        sel.addAndCondition(sel.getAlias(), ITGenericEAO.CREATED_BY_USER_ID, TCompareOp.EQUAL, userId);
        return getEao().search(sel);
    }

    @Override
    public List<E> search(TSelect<E> sel, int firstResult, int maxResult) {
        return getEao().search(sel, firstResult, maxResult);
    }

    @Override
    public List<E> search(Long userId, TSelect<E> sel, int firstResult, int maxResult) {
        sel.addAndCondition(sel.getAlias(), ITGenericEAO.CREATED_BY_USER_ID, TCompareOp.EQUAL, userId);
        return getEao().search(sel, firstResult, maxResult);
    }

    @Override
    public Long countSearch(TSelect<E> sel) {
        return getEao().countSearch(sel);
    }

    @Override
    public Long countSearch(Long userId, TSelect<E> sel) {
        sel.addAndCondition(sel.getAlias(), ITGenericEAO.CREATED_BY_USER_ID, TCompareOp.EQUAL, userId);
        return getEao().countSearch(sel);
    }

    @Override
    public E findById(E entity) throws Exception {
        return getEao().findById(entity);
    }

    @Override
    public E findById(Long userId, E entity) throws Exception {
        E e = getEao().findById(entity);
        return (e != null && e.getCreatedByUserId().equals(userId)) ? e : null;
    }

    @Override
    public E find(E entity) throws Exception {
        return getEao().find(entity);
    }

    @Override
    public E find(Long userId, E entity) throws Exception {
        entity.setCreatedByUserId(userId);
        return getEao().find(entity);
    }

    @Override
    public List<E> findAll(E entity) throws Exception {
        return getEao().findAll(entity);
    }

    @Override
    public List<E> findAll(Long userId, E entity) throws Exception {
        entity.setCreatedByUserId(userId);
        return getEao().findAll(entity);
    }

    @Override
    public E save(E entity) throws Exception {
        if (entity.isNew()) {
            getEao().persist(entity);
            return entity;
        } else {
            return getEao().merge(entity);
        }
    }

    @Override
    public void remove(E entity) throws Exception {
        getEao().remove(entity);
    }

    @Override
    public List<E> listAll(Class<? extends ITEntity> entity) throws Exception {
        return getEao().listAll(entity);
    }

    @Override
    public List<E> listAll(Long userId, Class<? extends ITEntity> entity) throws Exception {
        return getEao().listAll(userId, entity);
    }

    @Override
    public List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
        return getEao().pageAll(entity, firstResult, maxResult, orderByAsc);
    }

    @Override
    public List<E> pageAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
        entity.setCreatedByUserId(userId);
        return getEao().pageAll(entity, firstResult, maxResult, orderByAsc);
    }

    @Override
    public Long countAll(Class<? extends ITEntity> entity) throws Exception {
        return getEao().countAll(entity);
    }

    @Override
    public Long countAll(Long userId, Class<? extends ITEntity> entity) throws Exception {
        return getEao().countAll(userId, entity);
    }

    @Override
    public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc,
            boolean containsAnyKeyWords) throws Exception {
        return getEao().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
    }

    @Override
    public List<E> findAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc,
            boolean containsAnyKeyWords) throws Exception {
        entity.setCreatedByUserId(userId);
        return getEao().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
    }

    @Override
    public Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception {
        return getEao().countFindAll(entity, containsAnyKeyWords);
    }

    @Override
    public Integer countFindAll(Long userId, E entity, boolean containsAnyKeyWords) throws Exception {
        entity.setCreatedByUserId(userId);
        return getEao().countFindAll(entity, containsAnyKeyWords);
    }
}