package org.tedros.server.ejb.service;

import java.util.List;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.service.ITEjbService;

/**
 * Abstract base implementation of {@link ITEjbService}.
 * Delegates all operations to a {@link ITGenericBO} obtained via {@link #getBussinesObject()}.
 * <p>
 * Most operations run in {@link TransactionAttributeType#NOT_SUPPORTED} mode (read-only, no transaction),
 * while {@link #save(ITEntity)} and {@link #remove(ITEntity)} are explicitly marked as
 * {@link TransactionAttributeType#REQUIRED} to ensure proper transactional behavior for write operations.
 * </p>
 *
 * @param <E> the entity type extending {@link ITEntity}
 */
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbService<E extends ITEntity> implements ITEjbService<E> {

    /**
     * Returns the business object responsible for performing the actual operations.
     *
     * @return the generic business object for the entity type
     */
    public abstract ITGenericBO<E> getBussinesObject();

    @Override
    public List<E> search(TSelect<E> sel) {
        return getBussinesObject().search(sel);
    }

    @Override
    public List<E> search(Long userId, TSelect<E> sel) {
        return getBussinesObject().search(userId, sel);
    }

    @Override
    public List<E> search(TSelect<E> sel, int firstResult, int maxResult) {
        return getBussinesObject().search(sel, firstResult, maxResult);
    }

    @Override
    public List<E> search(Long userId, TSelect<E> sel, int firstResult, int maxResult) {
        return getBussinesObject().search(userId, sel, firstResult, maxResult);
    }

    @Override
    public Long countSearch(TSelect<E> sel) {
        return getBussinesObject().countSearch(sel);
    }

    @Override
    public Long countSearch(Long userId, TSelect<E> sel) {
        return getBussinesObject().countSearch(userId, sel);
    }

    @Override
    public E findById(E entity) throws Exception {
        return getBussinesObject().findById(entity);
    }

    @Override
    public E findById(Long userId, E entity) throws Exception {
        return getBussinesObject().findById(userId, entity);
    }

    @Override
    public E find(E entity) throws Exception {
        return getBussinesObject().find(entity);
    }

    @Override
    public E find(Long userId, E entity) throws Exception {
        return getBussinesObject().find(userId, entity);
    }

    @Override
    public List<E> findAll(E entity) throws Exception {
        return getBussinesObject().findAll(entity);
    }

    @Override
    public List<E> findAll(Long userId, E entity) throws Exception {
        return getBussinesObject().findAll(userId, entity);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public E save(E entity) throws Exception {
        return getBussinesObject().save(entity);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void remove(E entity) throws Exception {
        getBussinesObject().remove(entity);
    }

    @Override
    public List<E> listAll(Class<? extends ITEntity> entity) throws Exception {
        return getBussinesObject().listAll(entity);
    }

    @Override
    public List<E> listAll(Long userId, Class<? extends ITEntity> entity) throws Exception {
        return getBussinesObject().listAll(userId, entity);
    }

    @Override
    public List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
        return getBussinesObject().pageAll(entity, firstResult, maxResult, orderByAsc);
    }

    @Override
    public List<E> pageAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception {
        return getBussinesObject().pageAll(userId, entity, firstResult, maxResult, orderByAsc);
    }

    @Override
    public Long countAll(Class<? extends ITEntity> entity) throws Exception {
        return getBussinesObject().countAll(entity);
    }

    @Override
    public Long countAll(Long userId, Class<? extends ITEntity> entity) throws Exception {
        return getBussinesObject().countAll(userId, entity);
    }

    @Override
    public List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc,
                           boolean containsAnyKeyWords) throws Exception {
        return getBussinesObject().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
    }

    @Override
    public List<E> findAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc,
                           boolean containsAnyKeyWords) throws Exception {
        return getBussinesObject().findAll(userId, entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
    }

    @Override
    public Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception {
        return getBussinesObject().countFindAll(entity, containsAnyKeyWords);
    }

    @Override
    public Integer countFindAll(Long userId, E entity, boolean containsAnyKeyWords) throws Exception {
        return getBussinesObject().countFindAll(userId, entity, containsAnyKeyWords);
    }
}