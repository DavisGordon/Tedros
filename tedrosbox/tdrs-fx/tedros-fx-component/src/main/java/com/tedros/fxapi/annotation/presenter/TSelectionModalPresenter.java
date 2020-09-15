package com.tedros.fxapi.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.modal.behavior.TSelectionModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;

/**
 * <pre>
 * The selection modal view settings
 * 
 * </pre>
 * @author Davis Gordon
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TSelectionModalPresenter {
	
	/**
	 * The list view max width
	 * */
	public double listViewMaxWidth() default 250;
	/**
	 * The list view min width
	 * */
	public double listViewMinWidth() default 250;
	/**
	 * If true allows the user to select more than one item, 
	 * but this must be set according the field type which 
	 * will received the selected items. If the field type is a 
	 * SimpleObjectProperty than this must be false because 
	 * the field can hold just one value but if the field type 
	 * is an ITObservableList this can be true.
	 * */
	public boolean allowsMultipleSelections();
	/**
	 * The table view build settings to show the search result.
	 * */
	public TTableView tableView();
	/**<pre>
	 *  The paginator settings, the following property is required:
	 *  
	 *  entityClass - the entity of type ITEntity to search
	 *  
	 *  modelViewClass - the model view class with the fields of the entityClass
	 *  which was configured for the tableView this fields is the columns values.
	 *  
	 *  serviceName - the jndi name for the ejb service
	 *  </pre>
	 * */
	public TPaginator paginator();
	
	/**
	 * The modal view build settings 
	 * */
	public TPresenter presenter() default @TPresenter(	behavior = @TBehavior(type = TSelectionModalBehavior.class), 
														decorator = @TDecorator(type = TSelectionModalDecorator.class, 
														viewTitle="#{tedros.fxapi.view.modal.selection.title}"), 
														type = TDynaPresenter.class);
}
