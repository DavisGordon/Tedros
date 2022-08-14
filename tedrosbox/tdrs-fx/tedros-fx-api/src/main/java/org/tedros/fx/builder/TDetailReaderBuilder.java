/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.annotation.reader.TDetailReader;
import org.tedros.fx.form.ITForm;
import org.tedros.fx.form.TConverter;
import org.tedros.fx.form.TReaderFormBuilder;
import org.tedros.fx.layout.TFlowPane;
import org.tedros.fx.util.TModelViewUtil;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.TEntity;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPaneBuilder;


/**
 * <pre>
 * The builder used by {@link TDetailReader} annotation.
 * </pre>
 * @see TDetailReader
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailReaderBuilder 
extends TBuilder
implements ITReaderBuilder<Node, Object> {

	
	@SuppressWarnings("unchecked")
	public Node build(final Annotation annotation, final Object attrProperty) throws Exception {
		TDetailReader tAnnotation = (TDetailReader) annotation;
		if(tAnnotation.converter().type()!=org.tedros.fx.form.TConverter.class){
			Class clazz = tAnnotation.converter().type();
			TConverter converter = (TConverter) clazz.getConstructor().newInstance();
			converter.setIn(attrProperty);
			Node node = (Node) converter.getOut();
			node.setId("t-reader");
			callParser(tAnnotation, node);
			return node;
		}
		
		if (attrProperty instanceof SimpleObjectProperty){
			final SimpleObjectProperty<?> property = (SimpleObjectProperty) attrProperty;
			Object object = property.getValue();
			
			if(object instanceof ITModelView){
				final Node node = buildAndAddDetailForm(tAnnotation, (ITModelView<?>) object);
				node.setId("t-reader");
				callParser(tAnnotation, node);
				return node; 
			}else if (object instanceof TEntity){
				final Node node = buildAndAddDetailForm(tAnnotation, new TModelViewUtil(tAnnotation.modelViewClass(), tAnnotation.entityClass(), (ITEntity) object).convertToModelView());
				node.setId("t-reader");
				callParser(tAnnotation, node);
				return node;
			}
		}
		
		if (attrProperty instanceof ObservableList) {
			//final ObservableList attrProperty = (ObservableList) attrProperty;
			TFlowPane flowPane = new TFlowPane(10, 10);
			flowPane.autosize();
			flowPane.setOrientation(Orientation.HORIZONTAL);
			//flowPane.setPrefWrapLength(300);
			//flowPane.set
			flowPane.setAlignment(Pos.TOP_LEFT);
			for (Object object : (ObservableList) attrProperty) {
				ITForm detailForm = tAnnotation.formClass()
						.getConstructor(ITModelView.class, Boolean.class)
						.newInstance((ITModelView<?>) object, true);
				detailForm.setId("t-reader-");
				flowPane.getChildren().add((Node) detailForm);
			}
			//flowPane.setId("t-reader");
			callParser(tAnnotation, flowPane);
			return StackPaneBuilder.create().children(flowPane).build();
		}
		
		return null;
	}
	
	private static final Node buildAndAddDetailForm(TDetailReader tDetailReader, ITModelView<?> modelView)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		ITForm detailForm = TReaderFormBuilder.create(modelView, tDetailReader.formClass()).build();
		detailForm.setId("t-reader");
		return (Node) detailForm;
	}
	
	
	
}
