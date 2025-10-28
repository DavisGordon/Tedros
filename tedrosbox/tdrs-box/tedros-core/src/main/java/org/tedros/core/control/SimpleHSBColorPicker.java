
package org.tedros.core.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class SimpleHSBColorPicker extends Region
{

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public SimpleHSBColorPicker()
    {
        hsbRect = new Rectangle(200D, 30D, buildHueBar());
        lightRect = new Rectangle(200D, 30D, new LinearGradient(0.0D, 0.0D, 0.0D, 1.0D, true, CycleMethod.NO_CYCLE, new Stop[] {
            new Stop(0.0D, Color.WHITE), new Stop(0.5D, Color.rgb(255, 255, 255, 0.0D)), new Stop(0.501D, Color.rgb(0, 0, 0, 0.0D)), new Stop(1.0D, Color.BLACK)
        }));
        getChildren().addAll(new Node[] {
            hsbRect, lightRect
        });
        lightRect.setStroke(Color.GRAY);
        lightRect.setStrokeType(StrokeType.OUTSIDE);
        EventHandler ml = new EventHandler() {
            
			public void handle(MouseEvent e)
            {
                double w = getWidth();
                double h = getHeight();
                double x = Math.min(w, Math.max(0.0D, e.getX()));
                double y = Math.min(h, Math.max(0.0D, e.getY()));
                double hue = (360D / w) * x;
                double vert = (1.0D / h) * y;
                double sat = 0.0D;
                double bright = 0.0D;
                if(vert < 0.5D)
                {
                    bright = 1.0D;
                    sat = vert * 2D;
                } else
                {
                    bright = sat = 1.0D - (vert - 0.5D) * 2D;
                }
                Color c = Color.hsb((int)hue, sat, bright);
                color.set(c);
                e.consume();
            }

            public void handle(Event x0)
            {
                handle((MouseEvent)x0);
            }
            
        }
;
        lightRect.setOnMouseDragged(ml);
        lightRect.setOnMouseClicked(ml);
    }

    protected double computeMinWidth(double height)
    {
        return 200D;
    }

    protected double computeMinHeight(double width)
    {
        return 30D;
    }

    protected double computePrefWidth(double height)
    {
        return 200D;
    }

    protected double computePrefHeight(double width)
    {
        return 30D;
    }

    protected double computeMaxWidth(double height)
    {
        return 1.7976931348623157E+308D;
    }

    protected double computeMaxHeight(double width)
    {
        return 1.7976931348623157E+308D;
    }

    protected void layoutChildren()
    {
        double w = getWidth();
        double h = getHeight();
        hsbRect.setX(1.0D);
        hsbRect.setY(1.0D);
        hsbRect.setWidth(w - 2D);
        hsbRect.setHeight(h - 2D);
        lightRect.setX(1.0D);
        lightRect.setY(1.0D);
        lightRect.setWidth(w - 2D);
        lightRect.setHeight(h - 2D);
    }

    @SuppressWarnings("rawtypes")
	public ObjectProperty getColor()
    {
        return color;
    }

    private LinearGradient buildHueBar()
    {
        Stop stops[] = new Stop[255];
        for(int y = 0; y < 255; y++)
        {
            double offset = 0.0039215686274509803D * (double)y;
            int h = (int)(((double)y / 255D) * 360D);
            stops[y] = new Stop(offset, Color.hsb(h, 1.0D, 1.0D));
        }

        return new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, stops);
    }

    @SuppressWarnings("rawtypes")
	private final ObjectProperty color = new SimpleObjectProperty();
    private Rectangle hsbRect;
    private Rectangle lightRect;

}
