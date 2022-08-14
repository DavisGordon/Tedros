package org.tedros.fx.annotation.presenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.ITBehavior;
import org.tedros.fx.presenter.entity.behavior.TMasterCrudViewBehavior;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TImportModelView;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.util.TEntityListViewCallback;
import org.tedros.server.entity.ITEntity;

import javafx.scene.control.ListView;
import javafx.util.Callback;

@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.ANNOTATION_TYPE)
public @interface TBehavior {
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITBehavior> type() default TMasterCrudViewBehavior.class;
	
	/**
	 *<pre>
	 *Specifies a {@link Callback} to {@link ListView} inside a {@link TEntityModelView}.  
	 *</pre>  
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends Callback> listViewCallBack() default TEntityListViewCallback.class;

	/**
	 * <pre>
	 * Process the save action for all changed entity's if false only the current 
	 * entity in edition is processed.
	 * 
	 * Default: true
	 * </pre>
	 * */
	public boolean saveAllModels() default true;
	
	/**
	 * <pre>
	 * Process the save action only if the model is changed.
	 * 
	 * Default: true
	 * </pre>
	 * */
	public boolean saveOnlyChangedModels() default true;

	/**
	 * <pre>
	 * Process the new action after save action.
	 * 
	 * Default: false
	 * </pre>
	 * */
	public boolean runNewActionAfterSave() default false;


	/**
	 * <pre>
	 * Specifies actions to specific events identified by TActionType;
	 * </pre>
	 * */
	public Class<? extends TPresenterAction>[] action() default {TPresenterAction.class};
	
	/**
	 * <pre>
	 * The import model view class, must be configured to build the import view properly
	 * with the correct ejb service (@TEjbService) decorator of type TImportFileModalDecorator.class
	 * and behavior of type TImportFileModalBehavior.class. The ejb service must implement ITEjbImportController
	 * 
	 * This import model view must set the properties importedEntityClass and importedModelViewClass 
	 * in your TBehavior
	 * 
	 * Example:
	 * <code>
	 * @TEjbService(serviceName = "IProdutoImportControllerRemote", model=ProdutoImport.class)
	 * @TPresenter(decorator = @TDecorator(type=TImportFileModalDecorator.class, viewTitle="Importar produtos"),
	 *		behavior = @TBehavior(type=TImportFileModalBehavior.class, 
	 *		importedEntityClass=Produto.class, importedModelViewClass=ProdutoModelView.class))
	 * public class ProdutoImportModelView extends TImportModelView&ltProdutoImport&gt {
	 * </code>
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TImportModelView> importModelViewClass() default TImportModelView.class;
	
	/**
	 * The entity object type returned by the import process. 
	 * This must be set in a TImportModelView type.
	 * */
	public Class<? extends ITEntity> importedEntityClass() default ITEntity.class;
	
	/**
	 * The model view type to be built with the result of the import process. 
	 * This must be set in a TImportModelView type.
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> importedModelViewClass() default TModelView.class;

	
	
}
