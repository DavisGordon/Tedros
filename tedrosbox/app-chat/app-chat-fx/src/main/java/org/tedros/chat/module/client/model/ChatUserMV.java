package org.tedros.chat.module.client.model;

import org.tedros.chat.ejb.controller.IChatUserController;
import org.tedros.chat.entity.ChatUser;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;

@TSelectionModalPresenter(allowsMultipleSelections = true, 
paginator = @TPaginator(entityClass = ChatUser.class, serviceName = IChatUserController.JNDI_NAME), 
tableView = @TTableView(columns = { @TTableColumn(text = TUsualKey.NAME, cellValue="name" ) }))
public class ChatUserMV extends TEntityModelView<ChatUser> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField
	private SimpleStringProperty name;
	
	private SimpleStringProperty profiles;
	
	public ChatUserMV(ChatUser entity) {
		super(entity);
		super.formatToString("%s", name);
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	/**
	 * @return the profiles
	 */
	public SimpleStringProperty getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(SimpleStringProperty profiles) {
		this.profiles = profiles;
	}

}
