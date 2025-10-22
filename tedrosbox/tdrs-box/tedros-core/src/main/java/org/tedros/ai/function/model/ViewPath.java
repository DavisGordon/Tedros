/**
 * 
 */
package org.tedros.ai.function.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("the path to call a view")
public class ViewPath implements Comparable<ViewPath> {
	
	@JsonPropertyDescription("The view path use it to open a view in the system. Don't change it!")
	private String viewPath;
	
	/**
	 * @param viewPath
	 */
	public ViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	/**
	 * 
	 */
	public ViewPath() {
		super();
	}
	/**
	 * @return the viewPath
	 */
	public String getViewPath() {
		return viewPath;
	}

	/**
	 * @param viewPath the viewPath to set
	 */
	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(viewPath);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ViewPath)) {
			return false;
		}
		ViewPath other = (ViewPath) obj;
		return Objects.equals(viewPath, other.viewPath);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ViewPath other) {
		if (this.viewPath == null && other.viewPath == null) {
			return 0;
		}
		if (this.viewPath == null) {
			return -1;
		}
		if (other.viewPath == null) {
			return 1;
		}
		return this.viewPath.compareTo(other.viewPath);
	}
}