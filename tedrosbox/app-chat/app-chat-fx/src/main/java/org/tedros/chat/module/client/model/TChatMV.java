/**
 * 
 */
package org.tedros.chat.module.client.model;

import org.tedros.chat.CHATKey;
import org.tedros.chat.domain.DomainApp;
import org.tedros.chat.module.client.behaviour.TChatBehaviour;
import org.tedros.chat.module.client.decorator.TChatDecorator;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TModelView;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(
		decorator=@TDecorator(type=TChatDecorator.class, 
		viewTitle=CHATKey.VIEW_CLIENT_MESSAGES),
		behavior=@TBehavior(type=TChatBehaviour.class))
@TSecurity(	id=DomainApp.CHAT_FORM_ID, appName=CHATKey.APP_CHAT, moduleName=CHATKey.MODULE_MESSAGES, 
			allowedAccesses= {TAuthorizationType.VIEW_ACCESS, TAuthorizationType.SEARCH})
public class TChatMV extends TModelView<TChatModel> {

	@TModelViewType(modelClass = TUser.class)
	private ITObservableList<TUser> users;
	
	public TChatMV(TChatModel entity) {
		super(entity);
	}

	/**
	 * @return the users
	 */
	public ITObservableList<TUser> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(ITObservableList<TUser> users) {
		this.users = users;
	}

}
