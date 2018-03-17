package com.tedros.global.brasil.module.pessoa.process;

import java.util.List;
import java.util.Map;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TOptionsProcess;

public class TUFOptionsProcess extends TOptionsProcess {

	public TUFOptionsProcess(Class<? extends ITEntity> entityType)	throws TProcessException {
		super(entityType);
		
	}

	@Override
	public List<TResult<Object>> doFilter(Map<String, Object> objects) {
		// TODO Auto-generated method stub
		return null;
	}

}
