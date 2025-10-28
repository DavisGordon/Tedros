package org.tedros.fx.annotation.parser;

import org.tedros.fx.annotation.parser.engine.TAnnotationParser;
import org.tedros.fx.annotation.scene.TNode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;

public class TNodeParser extends TAnnotationParser<TNode, Node>{
	
		@Override
		public void parse(TNode ann, Node node, String... byPass) throws Exception {

			super.parse(ann, node, "requestFocus");
			
			if(ann.requestFocus()) {
				node.sceneProperty().addListener(new ChangeListener<Scene>(){
					@Override
					public void changed(ObservableValue<? extends Scene> arg0, Scene arg1, Scene ne) {
						if(ne!=null) {
							node.sceneProperty().removeListener(this);
							node.requestFocus();
						}
					}
				});
			}
		}
}
