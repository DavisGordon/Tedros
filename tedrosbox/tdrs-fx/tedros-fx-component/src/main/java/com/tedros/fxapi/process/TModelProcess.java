package com.tedros.fxapi.process;

import java.util.List;

import com.tedros.ejb.base.result.TResult;


/**
 * A model process receive a pre defined object 
 * and return a List of TResult of the same object processed.
 * 
 * @author Davis Gordon
 * */
public abstract class TModelProcess<IN> extends TCustomProcess<IN, List<TResult<IN>>>  {
	
	
	
}
