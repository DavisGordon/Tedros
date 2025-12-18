package org.tedros.server.controller;

import java.util.List;
import java.util.Map;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

/**
 * Controller interface for secure EJB operations on entities that implement {@link ITEntity}.
 * <p>
 * All methods require a valid {@link TAccessToken} for authentication and authorization.
 * Operations are divided into:
 * <ul>
 *   <li>Unrestricted (admin/system level)</li>
 *   <li>User-filtered (by userId – typically the creator/owner of the entity)</li>
 * </ul>
 * Results are wrapped in {@link TResult} to provide uniform success/error handling and additional metadata.
 * </p>
 *
 * @param <E> the entity type extending {@link ITEntity}
 */
public interface ITSecureEjbController<E extends ITEntity> extends ITBaseController {

    /**
     * Searches for entities using the provided selection criteria.
     *
     * @param token the access token for authentication/authorization
     * @param sel   the selection criteria
     * @return result containing the list of matching entities
     */
    TResult<List<E>> search(TAccessToken token, TSelect<E> sel);

    /**
     * Searches for entities using the provided selection criteria with pagination.
     * Returns a map typically containing the list of results and total count.
     *
     * @param token       the access token
     * @param sel         the selection criteria
     * @param firstResult the index of the first result to retrieve
     * @param maxResult   the maximum number of results to retrieve
     * @return result containing a map with paginated data
     */
    TResult<Map<String, Object>> search(TAccessToken token, TSelect<E> sel, int firstResult, int maxResult);

    /**
     * Retrieves an entity by its ID.
     *
     * @param token   the access token
     * @param entity  the entity containing the ID to search for
     * @return result containing the found entity or error information
     * @throws Exception if an unexpected error occurs
     */
    TResult<E> findById(TAccessToken token, E entity) throws Exception;

    /**
     * Retrieves the first entity that matches the non-null attributes of the provided entity.
     *
     * @param token  the access token
     * @param entity the entity with filled attributes to match
     * @return result containing the first matching entity or null if none found
     */
    TResult<E> find(TAccessToken token, E entity);

    /**
     * Searches for all entities that match the non-null attributes of the provided entity.
     *
     * @param token  the access token
     * @param entity the entity with filled attributes to match
     * @return result containing the list of matching entities
     */
    TResult<List<E>> findAll(TAccessToken token, E entity);

    /**
     * Saves (persists or merges) an entity.
     *
     * @param token  the access token
     * @param entity the entity to save
     * @return result containing the saved entity
     */
    TResult<E> save(TAccessToken token, E entity);

    /**
     * Removes an entity from persistence.
     *
     * @param token  the access token
     * @param entity the entity to remove
     * @return result containing the removed entity or success information
     */
    TResult<E> remove(TAccessToken token, E entity);

    /**
     * Retrieves all persisted entities of the given type.
     *
     * @param token        the access token
     * @param entityClass  the class of the entity to list
     * @return result containing the list of all entities
     */
    TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entityClass);

    /**
     * Retrieves a paginated list of all entities, ordered as specified.
     * Returns a map typically containing the list of results and total count.
     *
     * @param token       the access token
     * @param entity      the entity example (may be used for default ordering)
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @param orderByAsc  true for ascending order, false for descending
     * @return result containing a map with paginated data
     */
    TResult<Map<String, Object>> pageAll(TAccessToken token, E entity, int firstResult, int maxResult,
                                        boolean orderByAsc);

    /**
     * Retrieves a paginated list of entities that match the non-null attributes of the provided entity.
     * Returns a map typically containing the list of results and total count.
     *
     * @param token              the access token
     * @param entity             the entity with attributes to match
     * @param firstResult        the index of the first result
     * @param maxResult          the maximum number of results
     * @param orderByAsc         true for ascending order, false for descending
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return result containing a map with paginated data
     * @throws Exception if an unexpected error occurs
     */
    TResult<Map<String, Object>> findAll(TAccessToken token, E entity, int firstResult, int maxResult,
                                        boolean orderByAsc, boolean containsAnyKeyWords) throws Exception;

    /**
     * Searches for entities using the provided selection criteria, filtered by user ID.
     *
     * @param token  the access token
     * @param userId the ID of the user (usually the creator)
     * @param sel    the selection criteria
     * @return result containing the list of matching entities belonging to the user
     */
    TResult<List<E>> search(TAccessToken token, Long userId, TSelect<E> sel);

    /**
     * Searches for entities using the provided selection criteria with pagination, filtered by user ID.
     *
     * @param token       the access token
     * @param userId      the ID of the user
     * @param sel         the selection criteria
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @return result containing a map with paginated data for the user
     */
    TResult<Map<String, Object>> search(TAccessToken token, Long userId, TSelect<E> sel, int firstResult,
                                       int maxResult);

    /**
     * Retrieves an entity by its ID, filtered by user ID (ownership check).
     *
     * @param token  the access token
     * @param userId the ID of the user
     * @param entity the entity containing the ID to search for
     * @return result containing the found entity or error if not owned by the user
     */
    TResult<E> findById(TAccessToken token, Long userId, E entity);

    /**
     * Retrieves the first entity that matches the non-null attributes, filtered by user ID.
     *
     * @param token  the access token
     * @param userId the ID of the user
     * @param entity the entity with filled attributes to match
     * @return result containing the first matching entity owned by the user
     */
    TResult<E> find(TAccessToken token, Long userId, E entity);

    /**
     * Retrieves all persisted entities of the given type that belong to the specified user.
     *
     * @param token       the access token
     * @param userId      the ID of the user
     * @param entityClass the class of the entity to list
     * @return result containing the list of entities owned by the user
     */
    TResult<List<E>> listAll(TAccessToken token, Long userId, Class<? extends ITEntity> entity);

    /**
     * Retrieves a paginated list of all entities belonging to the user, ordered as specified.
     *
     * @param token       the access token
     * @param userId      the ID of the user
     * @param entity      the entity example
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @param orderByAsc  true for ascending order, false for descending
     * @return result containing a map with paginated data for the user
     */
    TResult<Map<String, Object>> pageAll(TAccessToken token, Long userId, E entity, int firstResult, int maxResult,
                                        boolean orderByAsc);

    /**
     * Retrieves a paginated list of entities that match the non-null attributes, filtered by user ID.
     *
     * @param token              the access token
     * @param userId             the ID of the user
     * @param entity             the entity with attributes to match
     * @param firstResult        the index of the first result
     * @param maxResult          the maximum number of results
     * @param orderByAsc         true for ascending order, false for descending
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return result containing a map with paginated data for the user
     */
    TResult<Map<String, Object>> findAll(TAccessToken token, Long userId, E entity, int firstResult, int maxResult,
                                        boolean orderByAsc, boolean containsAnyKeyWords);

    /**
     * Searches for all entities that match the non-null attributes of the provided entity,
     * filtered by user ID (ownership check).
     *
     * @param token  the access token for authentication/authorization
     * @param userId the ID of the user (usually the creator/owner)
     * @param entity the entity with filled attributes to match
     * @return result containing the list of matching entities owned by the user
     */
    TResult<List<E>> findAll(TAccessToken token, Long userId, E entity);
}