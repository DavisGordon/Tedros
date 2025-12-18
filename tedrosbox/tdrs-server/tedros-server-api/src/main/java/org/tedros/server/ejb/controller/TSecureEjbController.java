package org.tedros.server.ejb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJBException;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.OptimisticLockException;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.exception.TBusinessException;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.service.ITEjbService;

/**
 * Abstract base implementation of {@link ITSecureEjbController}.
 * <p>
 * Provides secure access to EJB services with:
 * <ul>
 *   <li>Authentication via {@link TAccessToken}</li>
 *   <li>Method-level security using {@link TMethodSecurity}</li>
 *   <li>Uniform exception handling returning {@link TResult}</li>
 *   <li>Post-processing hooks for single entities and lists</li>
 *   <li>Pagination support returning a map with "list" and "total" keys</li>
 * </ul>
 * Most read operations run without an explicit transaction ({@link TransactionAttributeType#NOT_SUPPORTED}),
 * while write operations (save/remove) inherit REQUIRED from the service layer.
 * </p>
 *
 * @param <E> the entity type extending {@link ITEntity}
 */
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TSecureEjbController<E extends ITEntity> implements ITSecureEjbController<E> {

    private static final String LIST_KEY = "list";
    private static final String TOTAL_KEY = "total";

    /**
     * Returns the EJB service that performs the actual persistence operations.
     *
     * @return the service instance for the entity type
     */
    protected abstract ITEjbService<E> getService();

    /**
     * Hook method called after successfully retrieving or saving a single entity.
     * Subclasses can override to apply additional processing (e.g., sensitive data masking).
     *
     * @param token  the access token
     * @param entity the processed entity (may be null)
     */
    protected void processEntity(TAccessToken token, E entity) {
    }

    /**
     * Hook method called after successfully retrieving a list of entities.
     * Subclasses can override to apply batch processing.
     *
     * @param token    the access token
     * @param entities the list of entities (may be empty)
     */
    protected void processEntityList(TAccessToken token, List<E> entities) {
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<List<E>> search(TAccessToken token, TSelect<E> sel) {
        try {
            List<E> list = getService().search(sel);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<List<E>> search(TAccessToken token, Long userId, TSelect<E> sel) {
        try {
            List<E> list = getService().search(userId, sel);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<Map<String, Object>> search(TAccessToken token, TSelect<E> sel, int firstResult, int maxResult) {
        try {
            Long count = getService().countSearch(sel);
            List<E> list = getService().search(sel, firstResult, maxResult);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count);
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<Map<String, Object>> search(TAccessToken token, Long userId, TSelect<E> sel, int firstResult,
                                              int maxResult) {
        try {
            Long count = getService().countSearch(userId, sel);
            List<E> list = getService().search(userId, sel, firstResult, maxResult);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count);
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ})})
    public TResult<E> findById(TAccessToken token, E entity) {
        try {
            entity = getService().findById(entity);
            processEntity(token, entity);
            return new TResult<>(TState.SUCCESS, entity);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ})})
    public TResult<E> findById(TAccessToken token, Long userId, E entity) {
        try {
            entity = getService().findById(userId, entity);
            processEntity(token, entity);
            return new TResult<>(TState.SUCCESS, entity);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<E> find(TAccessToken token, E entity) {
        try {
            entity = getService().find(entity);
            processEntity(token, entity);
            return new TResult<>(TState.SUCCESS, entity);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<E> find(TAccessToken token, Long userId, E entity) {
        try {
            entity = getService().find(userId, entity);
            processEntity(token, entity);
            return new TResult<>(TState.SUCCESS, entity);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<List<E>> findAll(TAccessToken token, E entity) {
        try {
            List<E> list = getService().findAll(entity);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})
    })
    public TResult<List<E>> findAll(TAccessToken token, Long userId, E entity) {
        try {
            List<E> list = getService().findAll(userId, entity);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE, TActionPolicie.NEW})})
    public TResult<E> save(TAccessToken token, E entity) {
        try {
            E e = getService().save(entity);
            processEntity(token, e);
            return new TResult<>(TState.SUCCESS, e);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.DELETE}, id = "")})
    public TResult<E> remove(TAccessToken token, E entity) {
        try {
            getService().remove(entity);
            return new TResult<>(TState.SUCCESS);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entity) {
        try {
            List<E> list = getService().listAll(entity);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<List<E>> listAll(TAccessToken token, Long userId, Class<? extends ITEntity> entity) {
        try {
            List<E> list = getService().listAll(userId, entity);
            processEntityList(token, list);
            return new TResult<>(TState.SUCCESS, list);
        } catch (Exception e) {
            return processException(token, null, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<Map<String, Object>> pageAll(TAccessToken token, E entity, int firstResult, int maxResult,
                                               boolean orderByAsc) {
        try {
            Long count = getService().countAll(entity.getClass());
            List<E> list = getService().pageAll(entity, firstResult, maxResult, orderByAsc);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count);
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<Map<String, Object>> pageAll(TAccessToken token, Long userId, E entity, int firstResult,
                                               int maxResult, boolean orderByAsc) {
        try {
            Long count = getService().countAll(userId, entity.getClass());
            List<E> list = getService().pageAll(userId, entity, firstResult, maxResult, orderByAsc);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count);
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<Map<String, Object>> findAll(TAccessToken token, E entity, int firstResult, int maxResult,
                                               boolean orderByAsc, boolean containsAnyKeyWords) {
        try {
            Number count = getService().countFindAll(entity, containsAnyKeyWords);
            List<E> list = getService().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count.longValue());
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    @Override
    @TMethodSecurity({
        @TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")
    })
    public TResult<Map<String, Object>> findAll(TAccessToken token, Long userId, E entity, int firstResult,
                                               int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
        try {
            Number count = getService().countFindAll(userId, entity, containsAnyKeyWords);
            List<E> list = getService().findAll(userId, entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
            processEntityList(token, list);
            Map<String, Object> map = new HashMap<>();
            map.put(TOTAL_KEY, count.longValue());
            map.put(LIST_KEY, list);
            return new TResult<>(TState.SUCCESS, map);
        } catch (Exception e) {
            return processException(token, entity, e);
        }
    }

    /**
     * Centralised exception processing that converts various exception types into appropriate {@link TResult} states.
     * Handles optimistic locking, constraint violations, business exceptions, and generic errors.
     *
     * @param <T>    the result value type
     * @param token  the access token
     * @param entity the entity involved (may be null)
     * @param e      the thrown exception
     * @return a TResult reflecting the error state
     */
    @SuppressWarnings("unchecked")
    protected <T> T processException(TAccessToken token, E entity, Throwable e) {
        e.printStackTrace();
        if (e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException) {
            TResult<E> result = find(token, entity);
            String message = (result.getValue() == null) ? "REMOVED" : "OUTDATED";
            return (T) new TResult<>(TState.OUTDATED, message, result.getValue());
        } else if (e instanceof EJBTransactionRolledbackException) {
            if (this.isTheCause(e, JdbcSQLIntegrityConstraintViolationException.class))
                return (T) new TResult<>(TState.ERROR, true, "This operation cant be done to preserve data integrity!");
            else
                return (T) new TResult<>(TState.ERROR, true, e.getCause().getMessage());
        } else if (e instanceof EJBException) {
            if (e.getCause() instanceof EJBException)
                return this.processException(token, entity, e.getCause());
            else if (e.getCause() instanceof TBusinessException bex) {
                return (T) new TResult<>(bex.isWarning() ? TState.WARNING : TState.ERROR, true, bex.getMessage());
            } else
                return (T) new TResult<>(TState.ERROR, true, e.getCause().getMessage());
        } else {
            return (T) new TResult<>(TState.ERROR, e.getMessage());
        }
    }

    /**
     * Utility method to check if a specific exception type is present in the cause chain.
     *
     * @param e    the throwable to inspect
     * @param type the exception class to look for
     * @return true if the type (by simple name) is found in the cause chain
     */
    protected boolean isTheCause(Throwable e, Class<? extends Throwable> type) {
        Throwable c = e;
        do {
            if (c.getClass().getSimpleName().equals(type.getSimpleName()))
                return true;
            c = c.getCause();
        } while (c != null);
        return false;
    }
}