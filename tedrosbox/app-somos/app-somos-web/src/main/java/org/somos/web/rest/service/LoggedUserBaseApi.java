/**
 * 
 */
package org.somos.web.rest.service;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.somos.web.bean.CovidUserBean;

/**
 * @author Davis Gordon
 *
 */
public class LoggedUserBaseApi extends BaseApi {


	@Inject @Any
	protected CovidUserBean covidUserBean;
}
