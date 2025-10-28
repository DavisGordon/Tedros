package org.tedros.fx.annotation.parser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.parser.engine.TAnnotationParser;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class TPaneParser extends TAnnotationParser<TPane, Pane> {
	
	public static final List<String> EXTRAS = Arrays.asList(TPane.REGION, TPane.HORIZONTAL_SEPARATOR, TPane.VERTICAL_SEPARATOR);

	@Override
	public void parse(TPane annotation, final Pane object, String... byPass) throws Exception {

		for(String field : annotation.children()){
			if(StringUtils.isBlank(field))
				continue;
			addNode(object, field);
		}
	}

	private void addNode(final Pane object, String field) {
		ITFieldDescriptor fd = getComponentDescriptor().getFieldDescriptor();
		if(fd.hasParent())
			return;
		Node node = null;
		if(fd.getFieldName().equals(field)) {
			node = fd.getComponent();
		}else if(isExtra(field)) {
			node = createExtras(field);
			node.setId(field);
		}else {
			fd = getComponentDescriptor().getFieldDescriptor(field);
			if(fd==null)
				throw new RuntimeException("The field "+field+" was not found in "
			+ getComponentDescriptor().getModelView().getClass().getSimpleName());
			
			fd.setHasParent(true);
			node = fd.hasLayout() 
					 ? fd.getLayout()
							 : fd.getComponent();
		}
		
		if(node!=null)
			object.getChildren().add(node);
		else
			throw new RuntimeException("The field "+field+" hasn't configured with a componet to be used."
					+ getComponentDescriptor().getModelView().getClass().getSimpleName());
	}

	public static boolean isExtra(String field) {
		return EXTRAS.stream().filter(v->field.contains(v)).findFirst().isPresent();
	}
	
	public static Node getExtra(Pane pane, String name) {
		Optional<Node> op = pane.getChildren().stream().filter(n->n.getId().equals(name)).findFirst();
		return (op.isPresent()) ? op.get() : null;
		
	}
	
	public static Node createExtras(String name) {
		
		String comp = EXTRAS.stream().filter(v->name.contains(v)).findFirst().get();
		
		switch (comp) {
		case TPane.REGION: {
			return new Region();			
		}
		case TPane.HORIZONTAL_SEPARATOR: {
			return new Separator(Orientation.HORIZONTAL);			
		}
		case TPane.VERTICAL_SEPARATOR: {
			return new Separator(Orientation.VERTICAL);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
	}
	
}
