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
	
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ})})
	public TResult<E> findById(TAccessToken token, E entidade) {
		try{
			entidade = getService().findById(entidade);
			return new TResult<E>(TState.SUCCESS, entidade);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<E> find(TAccessToken token, E entidade) {
		try{
			entidade = getService().find(entidade);
			return new TResult<E>(TState.SUCCESS, entidade);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public TResult<List<E>> findAll(TAccessToken token, E entity){
		try{
			List<E> list = getService().findAll(entity);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

	@Override
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE, TActionPolicie.NEW})})
	public TResult<E> save(TAccessToken token, E entidade) {
		try{
			E e = getService().save(entidade);
			return new TResult<E>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.DELETE}, id = "")})
	public TResult<E> remove(TAccessToken token, E entidade) {
		try{
			getService().remove(entidade);
			return new TResult<E>(TState.SUCCESS);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<List<E>> listAll(TAccessToken token, Class<? extends ITEntity> entidade) {
		
		try{
			List<E> list = getService().listAll(entidade);
			return new TResult<List<E>>(TState.SUCCESS, list);
			
		}catch(Exception e){
			return processException(token, null, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<Map<String, Object>> pageAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc) {
		try{
			Long count  = getService().countAll(entidade.getClass());
			
			List<E> list = getService().pageAll(entidade, firstResult, maxResult, orderByAsc);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count);
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}

	@Override
	@TMethodSecurity({
	@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH}, id = "")})
	public TResult<Map<String, Object>> findAll(TAccessToken token, E entidade, int firstResult, int maxResult, boolean orderByAsc, boolean containsAnyKeyWords) {
		try{
			Number count  =  getService().countFindAll(entidade, containsAnyKeyWords);
			
			List<E> list = getService().findAll(entidade, firstResult, maxResult, orderByAsc, containsAnyKeyWords);
			
			Map<String, Object> map = new HashMap<>();
			map.put("total", count.longValue());
			map.put("list", list);
			
			return new TResult<>(TState.SUCCESS, map);
			
		}catch(Exception e){
			return processException(token, entidade, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T processException(TAccessToken token, E entidade, Exception e) {
		e.printStackTrace();
		if(e instanceof OptimisticLockException || e.getCause() instanceof OptimisticLockException){
			TResult<E> result = find(token, entidade);
			
			String message = (result.getValue()==null) ? "REMOVED" : "OUTDATED";
			
			return (T) new TResult<>(TState.OUTDATED, message, result.getValue());
		}else if(e instanceof EJBTransactionRolledbackException) {
			if(this.isTheCause(e, JdbcSQLIntegrityConstraintViolationException.class))
				return (T) new TResult<>(TState.ERROR,true, "This operation cant be done to preserve data integrity!");
			else
				return (T) new TResult<>(TState.ERROR,true, e.getCause().getMessage());
		}else if(e instanceof EJBException) {
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
