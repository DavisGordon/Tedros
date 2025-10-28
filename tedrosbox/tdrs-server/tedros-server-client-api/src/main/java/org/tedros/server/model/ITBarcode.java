package org.tedros.server.model;

public interface ITBarcode extends ITModel{

	/**
	 * Create the ITFileBaseModel object
	 * */
	public ITFileBaseModel createFileModel();
	
	/**
	 * @return the content
	 */
	String getContent();

	/**
	 * @param content the content to set
	 */
	void setContent(String content);

	/**
	 * @return the type
	 */
	String getType();

	/**
	 * @param type the type to set
	 */
	void setType(String type);

	/**
	 * @return the orientation
	 */
	Integer getOrientation();

	/**
	 * @param orientation the orientation to set
	 */
	void setOrientation(Integer orientation);

	/**
	 * @return the resolution
	 */
	Integer getResolution();

	/**
	 * @param resolution the resolution to set
	 */
	void setResolution(Integer resolution);

	/**
	 * @return the columns
	 */
	Integer getColumns();

	/**
	 * @param columns the columns to set
	 */
	void setColumns(Integer columns);

	/**
	 * @return the size
	 */
	Integer getSize();

	/**
	 * @param size the size to set
	 */
	void setSize(Integer size);

	/**
	 * @return the image
	 */
	ITFileBaseModel getImage();

	/**
	 * @param image the image to set
	 */
	void setImage(ITFileBaseModel image);

}