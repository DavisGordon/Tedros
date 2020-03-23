package com.tedros.settings.layout.template;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;

public class GradientBuilderApp  {

	// Root Node of the application.
	private BorderPane root;
	
	private SimpleStringProperty backgroundStyleProperty;
	
	// Shapes(Panes) to which the gradient is applied.
	private StackPane rectangle;
	
	// Instance Variables
	private RadialSettingsLayout radialSettingLayout;
	private StackPane settingsContainer;
	//private TedrosTemplate tedrosTemplate;
	
	//public Node build(final TedrosTemplate tedrosTemplate) throws Exception {
	public Node build(SimpleStringProperty backgroundStyleProperty) throws Exception {
		//this.tedrosTemplate = tedrosTemplate;
		this.backgroundStyleProperty = backgroundStyleProperty;
		root = new BorderPane();
		root.autosize();
		root.getStylesheets().add("styles/gradientbuilder.css");
		root.getStyleClass().add("t-content-background-color");
		configureCenter();
		configureToolBar();
		return root;
	}
	
	public String getRadialStyle(){
		if(radialSettingLayout!=null)
			return radialSettingLayout.getGradientSyntax().getValue();
		return null;
	}

	/**
	 * Configures the center part(body) of the application.
	 */
	private void configureCenter() {
		// Getting the left side top pane. (Rectangle's view)
		StackPane rectanglePane = configureRectanglePane();
		
		// Getting the right side settings pane.
		ScrollPane rightPane = configureGradientSettings();
		rightPane.setMinHeight(150);
		
		VBox vbox = new VBox(4);
		vbox.setPadding(new Insets(5));
		VBox.setVgrow(rightPane, Priority.ALWAYS);
		VBox.setVgrow(rectanglePane, Priority.ALWAYS);
		vbox.getChildren().addAll(rightPane, rectanglePane);
		
		root.setCenter(vbox);
	}
	
	/**
	 * Configures the tool bar of the application.
	 */
	private void configureToolBar() {
		
		settingsContainer.getChildren().add(radialSettingLayout);
		radialSettingLayout.buildGradient();
	}

	/**
	 * Configures the layout for the "Rectangle" shape.
	 * @return StackPane
	 */
	private StackPane configureRectanglePane(){
		// Initializing the "Rectangle".
		rectangle = new StackPane();
		rectangle.setMaxHeight(200);
		// Creating the width label and binding its value with the rectangle's "widthProperty".
		Label widthLbl = getValueLabel();
		widthLbl.textProperty().bind(new StringBinding() {
			{
				bind(rectangle.widthProperty());
			}
			@Override
			protected String computeValue() {
				return rectangle.getWidth()+"px";
			}
		});
		
		// Creating the height label and binding its value with the rectangle's "heightProperty".
		Label heightLbl = getValueLabel();
		heightLbl.textProperty().bind(new StringBinding() {
			{
				bind(rectangle.heightProperty());
			}
			@Override
			protected String computeValue() {
				return rectangle.getHeight()+"px";
			}
		});
		
		// Creating a HBox layout to place the Rectangle's width and height information.
		HBox hb = HBoxBuilder.create()
							 .alignment(Pos.CENTER_RIGHT)
							 .prefHeight(20).build();
		hb.getChildren().addAll(getBoldLabel("Width : "), widthLbl, getSpacer(), getBoldLabel("Height : "), heightLbl);
		
		// BorderPane to hold the Rectangle and Bounds information.
		BorderPane bp = new BorderPane();
		bp.setCenter(rectangle);
		bp.setBottom(hb);
		
		// StackPane to hold the BorderPane and to apply the padding to BorderPane.
		StackPane rectanglePane = StackPaneBuilder.create()
											.children(bp)
											.build();
		return rectanglePane;
	}
	
	/**
	 * Configures the Gradient setting pane.
	 * @return ScrollPane
	 */
	private ScrollPane configureGradientSettings(){
		// Initializing the RadialSettingsLayout.
		radialSettingLayout = new RadialSettingsLayout(this);
		radialSettingLayout.getGradientSyntax().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				backgroundStyleProperty.set("-fx-background-color: "+newValue);
				
			}
		});
		
		// Initializing the container to hold RadialSettingsLayout or LinearSettingsLayout.
		settingsContainer = StackPaneBuilder.create().alignment(Pos.TOP_LEFT).build();
		
		// Wrapping the container with the ScrollPane.
		ScrollPane scroll = ScrollPaneBuilder.create()
				                             .styleClass("builder-scroll-pane")
				                             .fitToHeight(true)
				                             .fitToWidth(true)
				                             .content(settingsContainer)
				                             .build();
		return scroll;
	}

	/**
	 * Utility method to return a StackPane with some width to act like a spacer.
	 * @return StackPane
	 */
	private StackPane getSpacer(){
		return StackPaneBuilder.create().prefWidth(20).build();
	}
	
	/**
	 * Utility method to return a Label with bold font style.
	 * @return Label
	 */
	private Label getBoldLabel(String str){
		return LabelBuilder.create()
						   .text(str)
						   .style("-fx-font-weight:bold;").build();
	}
	
	/**
	 * Utility method to return a Label with normal font style.
	 * @return Label
	 */
	private Label getValueLabel(){
		return LabelBuilder.create()
						   .style("-fx-font-family:verdana;").build();
	}
	
	/**
	 * Method to apply the styles to the shapes.
	 * @param bg - CSS gradient string.
	 */
	public void applyStyles(String bg){
		rectangle.setStyle("-fx-background-color:"+bg);
	}
	
}

