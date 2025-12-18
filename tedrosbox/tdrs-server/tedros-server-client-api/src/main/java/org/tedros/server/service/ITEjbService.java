package org.tedros.server.service;

import java.util.List;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

/**
 * Service interface for common CRUD and search operations on entities that implement {@link ITEntity}.
 * Provides both unrestricted operations and user-filtered operations (based on createdByUserId).
 *
 * @param <E> the entity type extending {@link ITEntity}
 */
public interface ITEjbService<E extends ITEntity> {

    /**
     * Searches for entities using the provided selection criteria.
     *
     * @param sel the selection criteria
     * @return list of matching entities
     */
    List<E> search(TSelect<E> sel);

    /**
     * Searches for entities using the provided selection criteria with pagination.
     *
     * @param sel         the selection criteria
     * @param firstResult the index of the first result to retrieve
     * @param maxResult   the maximum number of results to retrieve
     * @return paginated list of matching entities
     */
    List<E> search(TSelect<E> sel, int firstResult, int maxResult);

    /**
     * Counts the number of entities that match the provided selection criteria.
     *
     * @param sel the selection criteria
     * @return the total count of matching entities
     */
    Long countSearch(TSelect<E> sel);

    /**
     * Retrieves an entity by its ID.
     *
     * @param entity the entity containing the ID to search for
     * @return the found entity or null if not found
     * @throws Exception if an error occurs during retrieval
     */
    E findById(E entity) throws Exception;

    /**
     * Retrieves the first entity that matches the non-null attributes of the provided entity.
     *
     * @param entity the entity with filled attributes to match
     * @return the first matching entity or null if none found
     * @throws Exception if an error occurs during the search
     */
    E find(E entity) throws Exception;

    /**
     * Searches for all entities that match the non-null attributes of the provided entity.
     *
     * @param entity the entity with filled attributes to match
     * @return list of matching entities
     * @throws Exception if an error occurs during the search
     */
    List<E> findAll(E entity) throws Exception;

    /**
     * Saves (persists or merges) an entity.
     *
     * @param entity the entity to save
     * @return the saved entity (usually with updated ID if new)
     * @throws Exception if an error occurs during persistence
     */
    E save(E entity) throws Exception;

    /**
     * Removes an entity from persistence.
     *
     * @param entity the entity to remove
     * @throws Exception if an error occurs during removal
     */
    void remove(E entity) throws Exception;

    /**
     * Retrieves all persisted entities of the given type.
     *
     * @param entityClass the class of the entity to list
     * @return list of all entities
     * @throws Exception if an error occurs during retrieval
     */
    List<E> listAll(Class<? extends ITEntity> entityClass) throws Exception;

    /**
     * Retrieves a paginated list of all entities, ordered according to the specified direction.
     *
     * @param entity      the entity example (may be used for default ordering)
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @param orderByAsc  true for ascending order, false for descending
     * @return paginated list of entities
     * @throws Exception if an error occurs during retrieval
     */
    List<E> pageAll(E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception;

    /**
     * Retrieves a paginated list of entities that match the non-null attributes of the provided entity.
     *
     * @param entity             the entity with attributes to match
     * @param firstResult        the index of the first result
     * @param maxResult          the maximum number of results
     * @param orderByAsc         true for ascending order, false for descending
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return paginated list of matching entities
     * @throws Exception if an error occurs during the search
     */
    List<E> findAll(E entity, int firstResult, int maxResult, boolean orderByAsc,
                    boolean containsAnyKeyWords) throws Exception;

    /**
     * Counts the number of entities that match the non-null attributes of the provided entity.
     *
     * @param entity             the entity with attributes to match
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return the count of matching entities
     * @throws Exception if an error occurs during counting
     */
    Integer countFindAll(E entity, boolean containsAnyKeyWords) throws Exception;

    /**
     * Counts all persisted entities of the given type.
     *
     * @param entityClass the class of the entity to count
     * @return the total number of persisted entities
     * @throws Exception if an error occurs during counting
     */
    Long countAll(Class<? extends ITEntity> entity) throws Exception;

    /**
     * Searches for entities using the provided selection criteria, filtered by user ID.
     *
     * @param userId the ID of the user (usually the creator)
     * @param sel    the selection criteria
     * @return list of matching entities belonging to the user
     */
    List<E> search(Long userId, TSelect<E> sel);

    /**
     * Searches for entities using the provided selection criteria with pagination, filtered by user ID.
     *
     * @param userId      the ID of the user
     * @param sel         the selection criteria
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @return paginated list of matching entities belonging to the user
     */
    List<E> search(Long userId, TSelect<E> sel, int firstResult, int maxResult);

    /**
     * Counts entities that match the selection criteria, filtered by user ID.
     *
     * @param userId the ID of the user
     * @param sel    the selection criteria
     * @return the count of matching entities belonging to the user
     */
    Long countSearch(Long userId, TSelect<E> sel);

    /**
     * Retrieves an entity by its ID, filtered by user ID (ownership check).
     *
     * @param userId the ID of the user
     * @param entity the entity containing the ID to search for
     * @return the found entity or null if not found or not owned by the user
     * @throws Exception if an error occurs during retrieval
     */
    E findById(Long userId, E entity) throws Exception;

    /**
     * Retrieves the first entity that matches the non-null attributes, filtered by user ID.
     *
     * @param userId the ID of the user
     * @param entity the entity with filled attributes to match
     * @return the first matching entity owned by the user or null
     * @throws Exception if an error occurs during the search
     */
    E find(Long userId, E entity) throws Exception;

    /**
     * Searches for all entities that match the non-null attributes, filtered by user ID.
     *
     * @param userId the ID of the user
     * @param entity the entity with filled attributes to match
     * @return list of matching entities owned by the user
     * @throws Exception if an error occurs during the search
     */
    List<E> findAll(Long userId, E entity) throws Exception;

    /**
     * Retrieves all persisted entities of the given type that belong to the specified user.
     *
     * @param userId      the ID of the user
     * @param entityClass the class of the entity to list
     * @return list of all entities owned by the user
     * @throws Exception if an error occurs during retrieval
     */
    List<E> listAll(Long userId, Class<? extends ITEntity> entity) throws Exception;

    /**
     * Retrieves a paginated list of all entities belonging to the user, ordered as specified.
     *
     * @param userId      the ID of the user
     * @param entity      the entity example
     * @param firstResult the index of the first result
     * @param maxResult   the maximum number of results
     * @param orderByAsc  true for ascending order, false for descending
     * @return paginated list of entities owned by the user
     * @throws Exception if an error occurs during retrieval
     */
    List<E> pageAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc) throws Exception;

    /**
     * Counts all persisted entities of the given type that belong to the specified user.
     *
     * @param userId      the ID of the user
     * @param entityClass the class of the entity to count
     * @return the total number of entities owned by the user
     * @throws Exception if an error occurs during counting
     */
    Long countAll(Long userId, Class<? extends ITEntity> entity) throws Exception;

    /**
     * Retrieves a paginated list of entities that match the non-null attributes, filtered by user ID.
     *
     * @param userId             the ID of the user
     * @param entity             the entity with attributes to match
     * @param firstResult        the index of the first result
     * @param maxResult          the maximum number of results
     * @param orderByAsc         true for ascending order, false for descending
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return paginated list of matching entities owned by the user
     * @throws Exception if an error occurs during the search
     */
    List<E> findAll(Long userId, E entity, int firstResult, int maxResult, boolean orderByAsc,
                    boolean containsAnyKeyWords) throws Exception;

    /**
     * Counts the number of entities that match the non-null attributes, filtered by user ID.
     *
     * @param userId             the ID of the user
     * @param entity             the entity with attributes to match
     * @param containsAnyKeyWords true if keyword search should match any word, false for all words
     * @return the count of matching entities owned by the user
     * @throws Exception if an error occurs during counting
     */
    Integer countFindAll(Long userId, E entity, boolean containsAnyKeyWords) throws Exception;
}