package org.tedros.server.entity;

import org.tedros.server.security.TAccessToken;

public interface ITUser extends ITVersionableEntity{

	String getName();

	/**
	 * @return the accessToken
	 */
	TAccessToken getAccessToken();

	String getProfilesText();

}