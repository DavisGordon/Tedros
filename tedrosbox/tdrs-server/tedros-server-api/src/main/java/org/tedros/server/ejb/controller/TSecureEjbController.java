package org.tedros.server.ejb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;

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

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TSecureEjbController<E extends ITEntity> implements ITSecureEjbController<E> {

	
	protected abstract ITEjbService<E> getService();
	
	protected void processEntity(TAccessToken token, E entity) {
		
	}
	
	protected void processEntityList(TAccessToken token, List<E> entities) {
		
	}
	
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<List<E>> search(TAccessToken token,TSelect<E> sel){
		try{
			List<E> list = getService().search(sel);
			processEntityList(token, list);
			return new TResult<List<E>>(TState.SUCCESS, list);
		}catch(Exception e){
			return processException(token, null, e);
		}
	}
	

	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<Map<String, Object>> search(TAccessToken token,TSelect<E> sel, int firstResult, int maxResult){
		try{
			Long count  = getService().countSearch(sel);
			
			List<E> list = getService().search(sel, firstResult, maxResult);
			processEntityList(token, list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, null, e);
		}
	}
	
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ})})
	public TResult<E> findById(TAccessToken token, E entity) {
		try{
			entity = getService().findById(entity);
			processEntity(token, entity);
			return new TResult<E>(TState.SUCCESS, entity);
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}
	
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<E> find(TAccessToken token, E entity) {
		try{
			entity = getService().find(entity);
			processEntity(token, entity);
			return new TResult<E>(TState.SUCCESS, entity);
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}
	
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<List<E>> findAll(TAccessToken token, E entity){
		try{
			List<E> list = getService().findAll(entity);
			processEntityList(token, list);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE, TActionPolicie.NEW})})
	public TResult<E> save(TAccessToken token, E entity) {
		try{
			E e = getService().save(entity);
			processEntity(token, e);
			return new TResult<E>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.DELETE}, id = "")})
	public TResult<E> remove(TAccessToken token, E entity) {
		try{
			getService().remove(entity);
			return new TResult<E>(TState.SUCCESS);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entity) {
		
		try{
			List<E> list = getService().listAll(entity);
			processEntityList(token, list);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(token, null, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<Map<String, Object>> pageAll(TAccessToken token, E entity, int firstResult, int maxResult, boolean orderByAsc) {
		try{
			Long count  = getService().countAll(entity.getClass());
			
			List<E> list = getService().pageAll(entity, firstResult, maxResult, orderByAsc);
			processEntityList(token, list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<Map<String, Object>> findAll(TAccessToken token, E entity, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		try{
			Number count  =  getService().countFindAll(entity, containsAnyKeyWords);
			
			List<E> list = getService().findAll(entity, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			processEntityList(token, list);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T processException(TAccessToken token, E entity, Throwable e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException){
			TResult<E> result = find(token, entity);
			
			String message = (result.getValue()==null) ? "REMOVED" : "OUTDATED";
			
			return (T) new TResult<>(TState.OUTDATED, message, result.getValue());
		}else if(e instanceof EJBTransactionRolledbackException) {
			if(this.isTheCause(e, JdbcSQLIntegrityConstraintViolationException.class))
				return (T) new TResult<>(TState.ERROR,true, "This operation cant be done to preserve data integrity!");
			else
				return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else if(e instanceof EJBException) {
			if(e.getCause() instanceof EJBException)
				return this.processException(token, entity, e.getCause());
			else if(e.getCause() instanceof TBusinessException) {
				TBusinessException bex = (TBusinessException) e.getCause();
				return (T) new TResult<>(bex.isWarning() ? TState.WARNING : TState.ERROR, true, bex.getMessage());
			}else
				return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else{
			return (T) new TResult<>(TState.ERROR, e.getMessage());
		}
	}
	
	protected boolean  isTheCause(Throwable e, Class<? extends Throwable> type) {
		Throwable  c = e;
		do {
			if(c.getClass().getSimpleName().equals(type.getSimpleName()))
				return true;
			c = c.getCause();
		}while(c!=null);
		
		return false;
	}
	
	

}
