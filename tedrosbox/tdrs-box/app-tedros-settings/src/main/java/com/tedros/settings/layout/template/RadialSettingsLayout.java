package com.tedros.settings.layout.template;

import java.util.Arrays;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioButtonBuilder;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBoxBuilder;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.style.TStyleResourceValue;
import com.tedros.settings.start.TConstant;

/**
 * Radial Settings layout class to configure the layout.
 * @author Sai.Dandem
 *
 */
public class RadialSettingsLayout extends AbstractSettingsLayout implements SyntaxConstants{
	
	// Variables to which the values are binded to. And are used to build the gradient.
	protected SimpleBooleanProperty isFocusAngle = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty focusAngle = new SimpleIntegerProperty();
	protected SimpleBooleanProperty isFocusDistance = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty focusDistance = new SimpleIntegerProperty();
	protected SimpleBooleanProperty isCenter = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty centerX = new SimpleIntegerProperty();
	protected SimpleIntegerProperty centerY = new SimpleIntegerProperty();
	protected SimpleBooleanProperty isRadiusPixel = new SimpleBooleanProperty();
	protected SimpleIntegerProperty radiusPixel = new SimpleIntegerProperty();
	protected SimpleIntegerProperty radiusPercent = new SimpleIntegerProperty();
	
	/**
	 * Constructor to configure the layout.
	 * @param app - GradientBuilderApp
	 */
	public RadialSettingsLayout(GradientBuilderApp app){
		super();
		this.app = app;
		this.grid = new GridPane();
		this.grid.setVgap(4);
		
		// Calling the method to add the listener to all the observable properties.
		addListeners();
		
		// Calling the method to configure the layout.
		configure();
	}
	
	public SimpleStringProperty getGradientSyntax(){
		return gradientSyntax;
	}

	/**
	 * Method to configure the common listener to all the observable properties.
	 * Any change in one value will fire the listener and builds the gradient and apply the styles to the shapes.
	 */
	private void addListeners() {
		focusAngle.addListener(changeListener);
		focusDistance.addListener(changeListener);
		centerX.addListener(changeListener);
		centerY.addListener(changeListener);
		isRadiusPixel.addListener(changeListener);
		radiusPixel.addListener(changeListener);
		radiusPercent.addListener(changeListener);
		repeatReflect.addListener(changeListener);
		
		isFocusAngle.addListener(changeListener);
		isFocusDistance.addListener(changeListener);
		isCenter.addListener(changeListener);
		isRepeat.addListener(changeListener);
	}

	/**
	 * Configures the settings layout.
	 */
	private void configure() {
		
		String customFocusAngle = TStyleResourceValue.FOCUS_ANGLE.customStyle();
		String customFocusDistance = TStyleResourceValue.FOCUS_DISTANCE.customStyle();
		String customCenter = TStyleResourceValue.CENTER.customStyle();
		String customRadius = TStyleResourceValue.RADIUS.customStyle();
		String customReflect = TStyleResourceValue.REFLECT.customStyle();
		String customRepeat = TStyleResourceValue.REPEAT.customStyle();
		String customColorStop = TStyleResourceValue.COLOR_STOP.customStyle();
		
		String[] customCenterXY = null;
		String[] colorStopArr = null;
		
		boolean radiusPercentage = true;
		
		if(customFocusAngle!=null)
			customFocusAngle = customFocusAngle.trim().replaceAll("deg", "");
		
		if(customFocusDistance!=null)
			customFocusDistance= customFocusDistance.trim().replaceAll("%", "");
		
		if(customCenter!=null){
			customCenter = customCenter.trim().replaceAll("%", "");
			customCenterXY = customCenter.trim().split(" ");
		}
		
		if(customRadius!=null){
			radiusPercentage = customRadius.contains("%");
			customRadius= (radiusPercentage) ? customRadius.trim().replaceAll("%", "") : customRadius.trim().replaceAll("px", "");
		}
		
		if(customColorStop!=null){
			colorStopArr = customColorStop.trim().split(","); 
		}
		
		layout.getChildren().addAll(this.grid);
		
		TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(TConstant.UUI);
		
		int rowIndex =0;
		/* Focus Angle*/
		CheckBox focusAngleCB = new CheckBox();
		focusAngleCB.selectedProperty().bindBidirectional(isFocusAngle);
		SliderTextField focusAngleField = new SliderTextField(0, 360, (null!=customFocusAngle) ? Integer.valueOf(customFocusAngle) : 0, iEngine.getString("#{label.degrees}"));
		focusAngleField.sliderDisableProperty().bind(focusAngleCB.selectedProperty().not());
		focusAngleField.getStyleClass().add("t-text-color");
		focusAngle.bindBidirectional(focusAngleField.valueProperty());
		if(null==customFocusAngle) focusAngleCB.setSelected(false);
		
		this.grid.add(focusAngleCB, 0, rowIndex);
		this.grid.add(LabelBuilder.create().text(iEngine.getString("#{label.focusangle}")).id("t-label").build(), 1, rowIndex);
		this.grid.add(focusAngleField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Focus Distance*/
		CheckBox focusDistCB = new CheckBox();
		focusDistCB.selectedProperty().bindBidirectional(isFocusDistance);
		SliderTextField focusDistField = new SliderTextField(-120, 120, (null!=customFocusDistance) ? Integer.valueOf(customFocusDistance) : 0, "%");
		focusDistField.sliderDisableProperty().bind(focusDistCB.selectedProperty().not());
		focusDistance.bindBidirectional(focusDistField.valueProperty());
		if(null==customFocusDistance) focusDistCB.setSelected(false);
		
		this.grid.add(focusDistCB, 0, rowIndex);
		this.grid.add(LabelBuilder.create().text(iEngine.getString("#{label.focusdistance}")).id("t-label").build(), 1, rowIndex);
		this.grid.add(focusDistField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Center */
		CheckBox centerCB = new CheckBox();
		centerCB.selectedProperty().bindBidirectional(isCenter);
		SliderTextField centerXField = new SliderTextField(-120, 120, (null!=customCenterXY) ? Integer.valueOf(customCenterXY[0]) : 50, "%");
		centerXField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerX.bindBidirectional(centerXField.valueProperty());
		if(null==customCenterXY) centerCB.setSelected(false);
		
		this.grid.add(centerCB, 0, rowIndex);
		this.grid.add(LabelBuilder.create().text(iEngine.getString("#{label.center}")).id("t-label").build(), 1, rowIndex);
		this.grid.add(LabelBuilder.create().text("X : ").id("t-label").build(), 2, rowIndex);
		this.grid.add(centerXField, 3, rowIndex);
		rowIndex++;
		
		SliderTextField centerYField = new SliderTextField(-120, 120, (null!=customCenterXY) ? Integer.valueOf(customCenterXY[1]) : 50, "%");
		centerYField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerY.bindBidirectional(centerYField.valueProperty());
		
		this.grid.add(LabelBuilder.create().text("Y : ").id("t-label").build(), 2, rowIndex);
		this.grid.add(centerYField, 3, rowIndex);
		rowIndex++;
		
		/* Radius */
		//final SliderTextField radiusPercentField = new SliderTextField(0, 120, (null!=customRadius) ? Integer.valueOf(customRadius) : 0, "%");
		/* Radius */
		final SliderTextField radiusPercentField = new SliderTextField(0, 120, (null!=customRadius) 
				? (radiusPercentage) 
						? Integer.valueOf(customRadius) 
								: Integer.valueOf(customRadius) > 120 
								? 120 : Integer.valueOf(customRadius) 
				: 0
				, "%");
		radiusPercent.bindBidirectional(radiusPercentField.valueProperty());
		
		final SliderTextField radiusPixelField = new SliderTextField(0, 300, (null!=customRadius) ? Integer.valueOf(customRadius) : 0, "px");
		radiusPixel.bindBidirectional(radiusPixelField.valueProperty());
		
		final StackPane radiusContainer = StackPaneBuilder.create().alignment(Pos.TOP_LEFT).build();
		
		ToggleGroup grp = new ToggleGroup();
		RadioButton percentBtn = RadioButtonBuilder.create().id("per").text(iEngine.getString("#{label.percentage}")).styleClass(Arrays.asList("t-text-color")).toggleGroup(grp).build();
		RadioButton pixelBtn = RadioButtonBuilder.create().id("pix").text("Pixel").styleClass(Arrays.asList("t-text-color")).toggleGroup(grp).build();
		radiusPercentField.disableProperty().bind(pixelBtn.selectedProperty());
		radiusPixelField.disableProperty().bind(percentBtn.selectedProperty());
		grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> arg0,	Toggle arg1, Toggle arg2) {
				RadioButton btn = (RadioButton)arg2;
				radiusContainer.getChildren().clear();
				if(btn.getId().equals("per")){
					isRadiusPixel.set(false);
					radiusContainer.getChildren().add(radiusPercentField);
				}else{
					isRadiusPixel.set(true);
					radiusContainer.getChildren().add(radiusPixelField);
				}
			}
		});
		grp.selectToggle(percentBtn);
		
		if(radiusPercentage)
			percentBtn.setSelected(true);
		else
			pixelBtn.setSelected(true);
		
		this.grid.add(LabelBuilder.create().text("Radius : ").id("t-label").build(), 1, rowIndex);
		this.grid.add(HBoxBuilder.create().alignment(Pos.CENTER_LEFT).spacing(10).children(percentBtn,pixelBtn).build(), 2, rowIndex, 2, 1);
		rowIndex++;
		
		this.grid.add(radiusContainer, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Repeat Or Reflect*/
		CheckBox repeatCB = new CheckBox();
		repeatCB.selectedProperty().bindBidirectional(isRepeat);
		ChoiceBox<RepeatOrReflect> repeatChoice = new ChoiceBox<RepeatOrReflect>();
		repeatChoice.disableProperty().bind(repeatCB.selectedProperty().not());
		repeatChoice.setItems(RepeatOrReflect.getList());
		repeatReflect.bind(repeatChoice.getSelectionModel().selectedItemProperty());
		repeatChoice.getSelectionModel().select(1);
		if(null!=customRepeat)
			repeatChoice.getSelectionModel().select(2);
		if(null!=customReflect)
			repeatChoice.getSelectionModel().select(3);
		
		if(null==customReflect && null==customRepeat) 
			repeatCB.setSelected(false);
		
		this.grid.add(repeatCB, 0, rowIndex);
		this.grid.add(LabelBuilder.create().text(iEngine.getString("#{label.repeatorreflect}")).id("t-label").build(), 1, rowIndex);
		this.grid.add(repeatChoice, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Color Stops */
		colorStopsVB = VBoxBuilder.create().spacing(15).build();
		if(colorStopArr==null)
			colorStopArr = new String[]{"#001a80 11%", "#54b0ee 100%"};
			
		for(String stop : colorStopArr){
			String[] arr = stop.trim().split(" ");
			if(arr.length==0)
				continue;
			String color = (!arr[0].contains("#")) ? "#"+arr[0].trim() : arr[0].trim();
			int pos = (arr.length==2) ? Integer.valueOf(arr[1].replaceAll("%", "")) : 0;
			colorStopsVB.getChildren().add(getColorStopTemplate(0, 100, pos, -1, color));
		}
		
		this.grid.add(StackPaneBuilder.create().alignment(Pos.TOP_LEFT).padding(new Insets(5,0,0,0)).children(LabelBuilder.create().text("Color Stops : ").id("t-label").build()).build(), 1, rowIndex);
		this.grid.add(colorStopsVB, 2, rowIndex, 2, 1);
		rowIndex++;
		
		checkForDeleteBtn();
		
		this.grid.getColumnConstraints().addAll(ColumnConstraintsBuilder.create().minWidth(20).build(),
				ColumnConstraintsBuilder.create().minWidth(110).build(),
				ColumnConstraintsBuilder.create().minWidth(20).build(),
				ColumnConstraintsBuilder.create().minWidth(110).build()
                );
	}
	
	
	/**
	 * Method to build the final gradient string from the observable properties.,
	 * and apply it on the shapes.
	 */
	public void buildGradient() {
		StringBuilder sytx = new StringBuilder(bgRadial);
		
		// Focus Angle
		if(isFocusAngle.get()){
			sytx.append(focusAngleStart).append(focusAngle.get()).append(focusAngleUnit);
			sytx.append(separator);
		}
		
		// Focus Distant
		if(isFocusDistance.get()){
			sytx.append(focusDistStart).append(focusDistance.get()).append(focusDistUnit);
			sytx.append(separator);
		}
		
		// Center
		if(isCenter.get()){
			sytx.append(centerStart).append(centerX.get()).append(centerUnit);
			sytx.append(centerY.get()).append(centerUnit);
			sytx.append(separator);
		}
		
		// Radius
		if(isRadiusPixel.get()){
			sytx.append(radiusStart).append(radiusPixel.get()).append(radiusPixelUnit);
			sytx.append(separator);
		}else{
			sytx.append(radiusStart).append(radiusPercent.get()).append(radiusPercentUnit);
			sytx.append(separator);
		}
		
		// Repeat or Reflect
		if(isRepeat.get() && repeatReflect.getValue()!=null && !repeatReflect.getValue().equals(RepeatOrReflect.NONE)){
			sytx.append(repeatReflect.getValue().toString());
			sytx.append(separator);
		}
		
		// Color Stops
		ColorStopDTO dto;
		for (int i=0 ; i<colorStops.size(); i++) {
			dto =colorStops.get(i);
			if(dto.getColorCode()!=null && !dto.getColorCode().equals("")){
				if(dto.getColorCode().indexOf("#") == 0){
					sytx.append(dto.getColorCode());
				}else{
					sytx.append("#"+dto.getColorCode());
				}
				
				if(dto.getPercent()>0){
					sytx.append(spacer).append(dto.getPercent()).append(colorStopUnit);
				}
				
				if(i<(colorStops.size()-1)){
					sytx.append(separator);
				}
			}
		}
		
		sytx.append(bgGradEnd);
		String str = sytx.toString();
		//str = (str.contains(", );")) ? str.replaceAll(", \\);", " \\);") : str;
		
		gradientSyntax.set(str);
		System.out.println(str);
		// Setting the result style to nodes.
		app.applyStyles(gradientSyntax.get());
	}
}
