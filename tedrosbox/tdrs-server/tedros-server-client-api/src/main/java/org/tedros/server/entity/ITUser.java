package org.tedros.server.entity;

import org.tedros.server.security.TAccessToken;

public interface ITUser extends ITEntity{

	String getName();

	/**
	 * @return the accessToken
	 */
	TAccessToken getAccessToken();

	String getProfilesText();

}