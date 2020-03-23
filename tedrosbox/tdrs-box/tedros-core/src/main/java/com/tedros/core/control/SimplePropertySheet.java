package com.tedros.core.control;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Referenced classes of package ensemble.controls:
//            SimpleHSBColorPicker

public class SimplePropertySheet extends GridPane
{
    public static final class PropDesc
    {

        private String name;
        private Double min;
        private Double max;
        private Object initialValue;
        @SuppressWarnings("rawtypes")
		private ObservableValue valueModel;

        @SuppressWarnings("rawtypes")
		public PropDesc(String name, ObservableValue valueModel)
        {
            this.name = name;
            this.valueModel = valueModel;
            initialValue = SimplePropertySheet.get(valueModel);
        }

        public PropDesc(String name, DoubleProperty valueModel, Double min, Double max)
        {
            this.name = name;
            this.valueModel = valueModel;
            initialValue = Double.valueOf(valueModel.get());
            this.min = min;
            this.max = max;
        }
    }


    public SimplePropertySheet(PropDesc properties[])
    {
        getStyleClass().add("simple-property-sheet");
        setVgap(10D);
        setHgap(10D);
        int row = 0;
        PropDesc arr$[] = properties;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            PropDesc property = arr$[i$];
            final PropDesc prop = property;
            Label propName = new Label((new StringBuilder()).append(prop.name).append(":").toString());
            propName.getStyleClass().add("sample-control-grid-prop-label");
            GridPane.setConstraints(propName, 0, row);
            getChildren().add(propName);
            if(prop.valueModel instanceof DoubleProperty)
            {
                final Label valueLabel = new Label(twoDp.format(prop.initialValue));
                GridPane.setConstraints(valueLabel, 2, row);
                final Slider slider = new Slider();
                slider.setMin(prop.min.doubleValue());
                slider.setMax(prop.max.doubleValue());
                slider.setValue(((Number)prop.initialValue).doubleValue());
                GridPane.setConstraints(slider, 1, row);
                slider.setMaxWidth(1.7976931348623157E+308D);
                slider.valueProperty().addListener(new InvalidationListener() {

                    public void invalidated(Observable ov)
                    {
                        SimplePropertySheet.set(prop.valueModel, Double.valueOf(slider.getValue()));
                        valueLabel.setText(SimplePropertySheet.twoDp.format(slider.getValue()));
                    }            
                });
                
                getChildren().addAll(new Node[] {
                    slider, valueLabel
                });
                
            } else
            {
                final Rectangle colorRect = new Rectangle(20D, 20D, (Color)prop.initialValue);
                colorRect.setStroke(Color.GRAY);
                final Label valueLabel = new Label(formatWebColor((Color)prop.initialValue));
                valueLabel.setGraphic(colorRect);
                valueLabel.setContentDisplay(ContentDisplay.LEFT);
                GridPane.setConstraints(valueLabel, 2, row);
                final SimpleHSBColorPicker colorPicker = new SimpleHSBColorPicker();
                GridPane.setConstraints(colorPicker, 1, row);
                colorPicker.getColor().addListener(new InvalidationListener() {

                    public void invalidated(Observable valueModel)
                    {
                        Color c = (Color)colorPicker.getColor().get();
                        SimplePropertySheet.set(prop.valueModel, c);
                        valueLabel.setText(SimplePropertySheet.formatWebColor(c));
                        colorRect.setFill(c);
                    }            
                });
                getChildren().addAll(new Node[] {
                    colorPicker, valueLabel
                });
            }
            row++;
        }

    }

    private static String formatWebColor(Color c)
    {
        String r = Integer.toHexString((int)(c.getRed() * 255D));
        if(r.length() == 1)
            r = (new StringBuilder()).append("0").append(r).toString();
        String g = Integer.toHexString((int)(c.getGreen() * 255D));
        if(g.length() == 1)
            g = (new StringBuilder()).append("0").append(g).toString();
        String b = Integer.toHexString((int)(c.getBlue() * 255D));
        if(b.length() == 1)
            b = (new StringBuilder()).append("0").append(b).toString();
        return (new StringBuilder()).append("#").append(r).append(g).append(b).toString();
    }

    @SuppressWarnings("rawtypes")
	public static Object get(ObservableValue valueModel)
    {
        if(valueModel instanceof DoubleProperty)
            return Double.valueOf(((DoubleProperty)valueModel).get());
        if(valueModel instanceof ObjectProperty)
            return ((ObjectProperty)valueModel).get();
        else
            return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void set(ObservableValue valueModel, Object value)
    {
        if(valueModel instanceof DoubleProperty)
            ((DoubleProperty)valueModel).set(((Double)value).doubleValue());
        else
        if(valueModel instanceof ObjectProperty)
            ((ObjectProperty)valueModel).set(value);
    }

    private static final NumberFormat twoDp = new DecimalFormat("0.##");



}
