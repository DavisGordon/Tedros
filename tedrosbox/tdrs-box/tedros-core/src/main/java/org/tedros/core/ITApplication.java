package org.tedros.core;

/**
 * Define an application contract to be loaded.
 * 
 * @author Davis Gordon
 * */
public interface ITApplication {

	/**
	 * Run after login phase.
	 * */
	public void start();
	
	/**
	 * Run before exit/close Tedros Box
	 * */
	public void stop();
	
}
