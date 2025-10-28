package org.tedros.login.model;

import org.tedros.core.security.model.TProfile;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 */
public final class ProfileMV extends TEntityModelView<TProfile> {
	
	private SimpleStringProperty name;
	
	public ProfileMV(TProfile entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}
