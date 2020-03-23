package com.tedros.settings.layout.template;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.tedros.settings.layout.template.SyntaxConstants.RepeatOrReflect;
import com.tedros.util.TColorUtil;

/**
 * Abstract class which contains the common fields for both "Linear Settings layout" and "Radial Settings Layout".
 * @author Sai.Dandem
 *
 */
public abstract class AbstractSettingsLayout extends StackPane{
	
	protected GradientBuilderApp app;
	public SimpleStringProperty gradientSyntax = new SimpleStringProperty("");
	
	protected SimpleBooleanProperty isRepeat = new SimpleBooleanProperty(true);
	protected SimpleObjectProperty<RepeatOrReflect> repeatReflect = new SimpleObjectProperty<RepeatOrReflect>();
	protected ObservableList<ColorStopDTO> colorStops = FXCollections.observableArrayList();
	
	protected VBox layout;
	protected VBox colorStopsVB;
	protected GridPane grid;
	
	// Listener to build the gradient on change of value.
	protected ChangeListener<Object> changeListener = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> arg0,	Object arg1, Object arg2) {
			buildGradient();
		}
	};
	
	/**
	 * Constructor to initialize the layout.
	 */
	public AbstractSettingsLayout() {
		super();
		setAlignment(Pos.TOP_LEFT);
		layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(10));
		getChildren().add(layout);
	}

	protected abstract void buildGradient();
	
	/**
	 * Returns the color stop row template.
	 * @param startValue - Start value of the slider.
	 * @param endValue - End value of the slider.
	 * @param pos - Default value of the slider
	 * @param finalPos - Position to which the color stop need to be added.
	 * @param color - Color code to be set in the text field.
	 * @return HBox
	 */
	/*protected HBox getColorStopTemplate(int startValue, int endValue, int pos, int finalPos, String color){
		HBox hb = getColorStopTemplate(startValue, endValue, pos, finalPos, color);
		((javafx.scene.control.ColorPicker)hb.getChildren().get(0)).setValue(Color.web(color));
		return hb;
	}*/
	
	/**
	 * Returns the color stop row template.
	 * @param startValue - Start value of the slider.
	 * @param endValue - End value of the slider.
	 * @param pos - Default value of the slider
	 * @param finalPos - Position to which the color stop need to be added.
	 * @return HBox
	 */
	protected HBox getColorStopTemplate(int startValue, int endValue, int pos, int finalPos, String color){
		final ColorStopDTO dto = new ColorStopDTO();
		dto.colorCodeProperty().addListener(changeListener);
		dto.percentProperty().addListener(changeListener);
		
		if(finalPos==-1){
			colorStops.add(dto);
		}else{
			colorStops.add(finalPos, dto);
		}
		
		SliderTextField sliderTF = new SliderTextField(startValue, endValue, pos);
		dto.percentProperty().bindBidirectional(sliderTF.valueProperty());
		
		
		final StringProperty valueProperty = new SimpleStringProperty();
		
		//ColorPicker colorPicker = new ColorPicker();
		final javafx.scene.control.ColorPicker colorPicker = new javafx.scene.control.ColorPicker();
		if(null!=color)
			colorPicker.setValue(Color.web(color));
		
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	String hex = TColorUtil.toHexadecimal(colorPicker.getValue());
            	valueProperty.setValue(hex);
            }
        });
		
		colorPicker.valueProperty().addListener(new ChangeListener<Color>() {

			@Override
			public void changed(ObservableValue<? extends Color> arg0,
					Color arg1, Color arg2) {
				String hex = TColorUtil.toHexadecimal(arg2);
            	valueProperty.setValue(hex);
			}
		});
		
		dto.colorCodeProperty().bindBidirectional(valueProperty);
		if(null!=color)
			valueProperty.set(color);
		
		Button add = ButtonBuilder.create()
								  .styleClass("transparentButton")
								  .graphic(new ImageView(new Image(ColorPicker.class.getResource("/images/add.png").toExternalForm())))
								  .build();
		Button delete = ButtonBuilder.create()
								  .styleClass("transparentButton")
								  .graphic(new ImageView(new Image(ColorPicker.class.getResource("/images/delete.png").toExternalForm())))
								  .build();

		final HBox hb = HBoxBuilder.create()
							 .maxHeight(30)
							 .spacing(20)
							 .alignment(Pos.CENTER_LEFT)
							 .children(colorPicker, sliderTF, StackPaneBuilder.create().alignment(Pos.CENTER_LEFT).padding(new Insets(0,0,0,20)).children(add).build(),delete)
							 .build();
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				addNewColorStop(hb);
			}
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				deleteColorStop(hb);
			}
		});
		return hb;
	}
	
	/**
	 * Method to add a new ColorStop row in the list and the layout. Called on click of "+" button.
	 * @param current - Current color template row to determine the position.
	 */
	private void addNewColorStop(HBox current){
		int finalPos = getColorStopPosition(current);
		finalPos = (finalPos == colorStopsVB.getChildren().size()-1 || finalPos == -1) ? -1 : (finalPos+1);
		if(finalPos ==-1){
			colorStopsVB.getChildren().add(getColorStopTemplate(0, 100, 0, finalPos, null));
		}else{
			colorStopsVB.getChildren().add(finalPos, getColorStopTemplate(0, 100, 0, finalPos, null));
		}
		
		// After adding the row calling the method to build the gradient and apply the styles to the shapes.
		buildGradient();
		
		// Calling the method to enable delete buttons based on the row count.
		checkForDeleteBtn();
	}
	
	/**
	 * Method to remove the color stop row from the list and the layout. Called on click of "X" button.
	 * @param current - Current color template row to determine the position.
	 */
	private void deleteColorStop(HBox current){
		int finalPos = getColorStopPosition(current);
		
		colorStops.get(finalPos).colorCodeProperty().removeListener(changeListener);
		colorStops.get(finalPos).percentProperty().removeListener(changeListener);
		colorStops.remove(finalPos);
		
		colorStopsVB.getChildren().remove(current);
		// After adding the row calling the method to build the gradient and apply the styles to the shapes.
		buildGradient();
		
		// Calling the method to enable delete buttons based on the row count.
		checkForDeleteBtn();
	}
	
	/**
	 * Utility method to get the color stop position from the list.
	 * @param current - Current color template row to determine the position.
	 * @return position.
	 */
	protected int getColorStopPosition(HBox current){
		int finalPos = -1;
		for (int i=0 ; i<colorStopsVB.getChildren().size(); i++) {
			if(colorStopsVB.getChildren().get(i) == current){
				finalPos = i;
				break;
			}
		}
		return finalPos;
	}
	
	/**
	 * Utility method to check for no of color-stops and enable/disable the delete buttons.
	 */
	protected void checkForDeleteBtn(){
		boolean flag = (colorStopsVB.getChildren().size()>2) ? true : false;
		for (Node node : colorStopsVB.getChildren()) {
			if(flag){
				((HBox)node).getChildren().get(3).setDisable(false);
			}else{
				((HBox)node).getChildren().get(3).setDisable(true);
			}
		}
	}
	
}
