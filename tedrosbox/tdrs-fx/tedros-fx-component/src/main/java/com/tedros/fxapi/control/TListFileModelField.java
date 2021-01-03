package com.tedros.fxapi.control;

import java.io.ByteArrayInputStream;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

import com.tedros.app.component.ITComponent;
import com.tedros.fxapi.property.TSimpleFileModelProperty;

public class TListFileModelField extends Pane implements ITField, ITComponent{

	
	private final ObservableList<TSimpleFileModelProperty<?>> t_simpleFileModelObservableList;
	
	private SimpleObjectProperty<Orientation> t_orientationProperty;
	private SimpleObjectProperty<Pos> t_aligmentProperty;
	private String t_componentId;
	
	
	/*public static void main(String[] args) {
		
		System.out.println(System.getenv("APPDATA"));
		
		File f1 = new File("C:\\Users\\Davis Gordon\\Pictures\\mini logo 1.jpg");
		
		Magic magic = new Magic();
		try {
			MagicMatch match = magic.getMagicMatch(FileUtils.readFileToByteArray(f1));
			System.out.println(match.getMimeType());
			
		} catch (MagicParseException | MagicMatchNotFoundException
				| MagicException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	
	public TListFileModelField(final ObservableList<TSimpleFileModelProperty<?>> tSimpleFileModelObservableList, final Orientation orientation) {
		initialize();
		buildListener();
		this.t_simpleFileModelObservableList = tSimpleFileModelObservableList;
		this.t_orientationProperty.setValue(orientation);
		build();
	}
	
	@Override
	public Observable tValueProperty() {
		return t_simpleFileModelObservableList;
	}
	
	/**
	 * Initialize propertys
	 * */
	private void initialize(){
		this.t_orientationProperty = new SimpleObjectProperty<>();
		this.t_aligmentProperty = new SimpleObjectProperty<>();
	}
	
	private void buildListener() {
		this.t_orientationProperty.addListener(new ChangeListener<Orientation>() {
			@Override
			public void changed(ObservableValue<? extends Orientation> arg0, Orientation arg1, Orientation new_Value) {
				getChildren().clear();
				getChildren().add((new_Value==null || new_Value.equals(Orientation.VERTICAL)) ?
						VBoxBuilder.create().build() 
						: HBoxBuilder.create().build());
			}
		});
		
		this.t_aligmentProperty.addListener(new ChangeListener<Pos>() {
			@Override
			public void changed(ObservableValue<? extends Pos> arg0, Pos arg1, Pos new_Value) {
				if(isPaneBuilded())
					if(((Pane)getChildren().get(0)) instanceof VBox)
						((VBox)((Pane)getChildren().get(0))).setAlignment(new_Value);
					else
						((HBox)((Pane)getChildren().get(0))).setAlignment(new_Value);
			}
		});
	}

	
	
	private void build(){
		
		
		if(!isPaneBuilded())
			getChildren().add((this.t_orientationProperty.getValue()==null || this.t_orientationProperty.equals(Orientation.VERTICAL)) ?
					VBoxBuilder.create().build() 
					: HBoxBuilder.create().build());
		
		if(t_aligmentProperty!=null)
				
		if(t_simpleFileModelObservableList!=null)
			for (TSimpleFileModelProperty<?> fileProperty : t_simpleFileModelObservableList)
				buildImageViewIcon(fileProperty);
	}

	private boolean isPaneBuilded() {
		return ((Pane)getChildren().get(0))!=null;
	}
	
	private void buildImageViewIcon(TSimpleFileModelProperty<?> fileProperty) {
		final ImageView imgView = new ImageView(new Image(new ByteArrayInputStream(fileProperty.getBytes())));
		imgView.maxHeight(150);
		imgView.maxWidth(200);
		imgView.setCursor(Cursor.HAND);
		imgView.autosize();
		((Pane)getChildren().get(0)).getChildren().add(0,imgView);
	}
	
	/* PROPERTYS */
	
	public final ObservableList<TSimpleFileModelProperty<?>> tSimpleFileModelObservableList() {
		return t_simpleFileModelObservableList;
	}
	
	public final ReadOnlyObjectProperty<Orientation> tOrientationProperty() {
		return t_orientationProperty;
	}
	
	public final ReadOnlyObjectProperty<Pos> tAligmentProperty() {
		return t_aligmentProperty;
	}
	
	
	/* GETTERS AND SETTERS */

	public final Orientation gettOrientation() {
		return t_orientationProperty.getValue();
	}

	public final void settOrientation(Orientation t_orientation) {
		this.t_orientationProperty.setValue(t_orientation);;
	}

	public final Pos gettAligment() {
		return t_aligmentProperty.getValue();
	}

	public final void settAligment(Pos t_aligment) {
		this.t_aligmentProperty.setValue(t_aligment);
	}


	@Override
	public String gettComponentId() {
		return t_componentId;
	}


	@Override
	public void settComponentId(String id) {
		this.t_componentId = id;
	}


	@Override
	public void settFieldStyle(String style) {
		setStyle(style);
	}
	
	
	
}
