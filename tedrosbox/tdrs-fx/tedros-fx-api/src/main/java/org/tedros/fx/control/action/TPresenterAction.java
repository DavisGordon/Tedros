package org.tedros.fx.control.action;

import java.util.Arrays;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.fx.presenter.behavior.TActionType;


/**
 * <pre>
 * Specifies an action to run before, after or replace an action call.
 * 
 * To replace the entire call the method runBefore just need to return false.
 * </pre>
 * @author davis.dun
 * */
@SuppressWarnings("rawtypes")
public abstract class TPresenterAction implements Comparable<TPresenterAction>{
	
	private String name;
	private ITPresenter presenter;
	private Integer priority;
	private TActionType[] types;
	
	/**
	 * @param types
	 */
	public TPresenterAction(TActionType... tActionType) {
		this.name = getClass().getSimpleName();
		this.priority = 0;
		this.types = tActionType==null ? TActionType.values() : tActionType;
	}

	/**
	 * Run before execution of the specified action if false is returned the flow is interrupted.  
	 * */
	public abstract boolean runBefore();
	
	/**
	 * Run after execution of the specified action.  
	 * */
	public abstract void runAfter();
	
	@Override
	public int compareTo(TPresenterAction o) {
		return Integer.compare(getPriority(), o.getPriority());
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the types
	 */
	public TActionType[] getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(TActionType[] types) {
		this.types = types;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the presenter
	 */
	@SuppressWarnings("unchecked")
	public <T extends ITPresenter> T getPresenter() {
		return (T) presenter;
	}

	/**
	 * @param presenter the presenter to set
	 */
	public void setPresenter(ITPresenter presenter) {
		this.presenter = presenter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + Arrays.hashCode(types);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TPresenterAction))
			return false;
		TPresenterAction other = (TPresenterAction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (!Arrays.equals(types, other.types))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TPresenterAction [" + (name != null ? "name=" + name + ", " : "")
				+ (priority != null ? "priority=" + priority + ", " : "")
				+ (types != null ? "types=" + Arrays.toString(types) : "") + "]";
	}
}
