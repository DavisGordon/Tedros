package org.tedros.api.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;

public interface ITFieldDescriptor {

	boolean isIgnorable();

	String getFieldName();

	Class<?> getFieldType();

	boolean isLoaded();

	ReadOnlyBooleanProperty loadedProperty();

	List<Annotation> getAnnotations();
	
	<T extends Annotation> T getAnnotation(Class<T> type);

	Field getField();

	int hashCode();

	Object[] getArrControl();

	Object[] getArrLayout();

	boolean hasControl();

	boolean hasLayout();

	int getOrder();

	void setOrder(int order);

	ReadOnlyBooleanProperty componentLoadedProperty();

	void setComponentLoaded(boolean loaded);

	boolean isComponentLoaded();

	ReadOnlyBooleanProperty layoutLoadedProperty();

	void setLayoutLoaded(boolean loaded);

	boolean isLayoutLoaded();

	Node getLayout();

	void setLayout(Node layout);

	Node getComponent();

	void setComponent(Node component);

	boolean hasParent();

	void setHasParent(boolean hasParent);

	Node getControl();

	void setControl(Node control);

}