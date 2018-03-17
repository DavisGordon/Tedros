package com.tedros.settings.layout.template;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
/**
 * Color Picker component to selected the color in the text field.
 * Reference: https://forums.oracle.com/forums/thread.jspa?threadID=2316310
 */
public class ColorPicker extends HBox{
	private SimpleStringProperty colorCode = new SimpleStringProperty();
	private ColorChooser colorChooser;
	private ColorTextField textField;
	private Popup popup;
	
	public ColorPicker(){
		super();
		configure();
	}
	
	private void configure(){
		super.setMaxHeight(24);
		super.setMinWidth(85);
		super.setSpacing(3);
		
		textField = new ColorTextField();
		colorCode.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,	String arg1, String arg2) {
				textField.setText(arg2);
			}
		});
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,	String arg1, String newTxt) {
				// If the new text length is exceeded.
				if((newTxt.indexOf("#") == 0) && (newTxt.length()==7) ){
					colorCode.set(newTxt);
				}else if((newTxt.indexOf("#") == -1) && (newTxt.length()==6)){
					colorCode.set(newTxt);
				}
			}
		});
		
		ImageView palette = new ImageView(new Image(ColorPicker.class.getResource("/images/palette.png").toExternalForm()));
		palette.setCursor(Cursor.HAND);
		palette.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent paramT) {
				showPopup();
			}
		});
		
		this.colorChooser = new ColorChooser();
		this.popup = new Popup();
		this.popup.setAutoHide(true);
		this.popup.getContent().add(this.colorChooser);
		
		getChildren().addAll(textField,palette);
	}

	public SimpleStringProperty colorCodeProperty() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode.set(colorCode);
	}
	
	public void getColorCode() {
		this.colorCode.get();
	}
	
	protected void showPopup() {
		Parent parent = textField.getParent();
		Bounds childBounds = textField.getBoundsInParent();
		Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
		double layoutX = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX()
				+ parent.getScene().getWindow().getX();
		double layoutY = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY()
				+ parent.getScene().getWindow().getY();
		popup.show(textField, layoutX, layoutY);
	}
	
	/**
	 * Color Text field class. Allows only the color code values.
	 * @author Sai.Dandem
	 *
	 */
	class ColorTextField extends TextField{
		public ColorTextField(){
			super();
			configure();
		}
		
		public ColorTextField(String code){
			super(code);
			configure();
		}
		
		private void configure(){
			super.setMaxWidth(60);
		}
		
		@Override 
		public void replaceText(int start, int end, String text) {
		   if (accept(text)) {
		        super.replaceText(start, end, text.trim());
		   }
		}
		 
	    @Override 
	    public void replaceSelection(String text) {
	        if (accept(text)) {
	            super.replaceSelection(text);
	        }
	    }
	    
	    private boolean accept(String text){
	    	text = text.trim();
	    	if (text.length() == 0) return true;
	    	String newTxt;
	    	if(getText()!=null){
				newTxt = (getText()!=null ? getText() : "") +text;
    			// If the new text does not satisfy the color code characters.
				if (!newTxt.matches("^[#]?[a-fA-F0-9]*")){
    				return false;
    			}
    			
				// If the new text length is exceeded.
				if((newTxt.indexOf("#") == 0) && (newTxt.length()>7) ){
					return false;
				}else if((newTxt.indexOf("#") == -1) && (newTxt.length()>6)){
					return false;
				}
			}
	    	return true;
	    }
	}
	
	class ColorChooser extends VBox {
		  private final int N_COLUMNS = 14;
		  private final double TILE_SIZE = 18;
			 
		  /** The color the user has selected or the default initial color (the first color in the palette) */
		  private final ReadOnlyObjectWrapper<Color> chosenColor = new ReadOnlyObjectWrapper<Color>();
		  public Color getChosenColor() {
		    return chosenColor.get();
		  }
		  public ReadOnlyObjectProperty<Color> chosenColorProperty() {
		    return chosenColor.getReadOnlyProperty();
		  }
		 
		  /** Friendly name for the chosen color */
		  private final ReadOnlyObjectWrapper<String> chosenColorName = new ReadOnlyObjectWrapper<String>();
		  public String getChosenColorName() {
		    return chosenColorName.get();
		  }
		  
		  /** A palette of colors from http://docs.oracle.com/javafx/2.0/api/javafx/scene/doc-files/cssref.html#typecolor */
		  private final String[][] webPalette = {
		    {"aliceblue", "#f0f8ff"},{"antiquewhite", "#faebd7"},{"aqua", "#00ffff"},{"aquamarine", "#7fffd4"},
		    {"azure", "#f0ffff"},{"beige", "#f5f5dc"},{"bisque", "#ffe4c4"},{"black", "#000000"},
		    {"blanchedalmond", "#ffebcd"},{"blue", "#0000ff"},{"blueviolet", "#8a2be2"},{"brown", "#a52a2a"},
		    {"burlywood", "#deb887"},{"cadetblue", "#5f9ea0"},{"chartreuse", "#7fff00"},{"chocolate", "#d2691e"},
		    {"coral", "#ff7f50"},{"cornflowerblue", "#6495ed"},{"cornsilk", "#fff8dc"},{"crimson", "#dc143c"},
		    {"cyan", "#00ffff"},{"darkblue", "#00008b"},{"darkcyan", "#008b8b"},{"darkgoldenrod", "#b8860b"},
		    {"darkgray", "#a9a9a9"},{"darkgreen", "#006400"},{"darkgrey", "#a9a9a9"},{"darkkhaki", "#bdb76b"},
		    {"darkmagenta", "#8b008b"},{"darkolivegreen", "#556b2f"},{"darkorange", "#ff8c00"},{"darkorchid", "#9932cc"},
		    {"darkred", "#8b0000"},{"darksalmon", "#e9967a"},{"darkseagreen", "#8fbc8f"},{"darkslateblue", "#483d8b"},
		    {"darkslategray", "#2f4f4f"},{"darkslategrey", "#2f4f4f"},{"darkturquoise", "#00ced1"},{"darkviolet", "#9400d3"},
		    {"deeppink", "#ff1493"},{"deepskyblue", "#00bfff"},{"dimgray", "#696969"},{"dimgrey", "#696969"},
		    {"dodgerblue", "#1e90ff"},{"firebrick", "#b22222"},{"floralwhite", "#fffaf0"},{"forestgreen", "#228b22"},
		    {"fuchsia", "#ff00ff"},{"gainsboro", "#dcdcdc"},{"ghostwhite", "#f8f8ff"},{"gold", "#ffd700"},
		    {"goldenrod", "#daa520"},{"gray", "#808080"},{"green", "#008000"},{"greenyellow", "#adff2f"},
		    {"grey", "#808080"},{"honeydew", "#f0fff0"},{"hotpink", "#ff69b4"},{"indianred", "#cd5c5c"},
		    {"indigo", "#4b0082"},{"ivory", "#fffff0"},{"khaki", "#f0e68c"},{"lavender", "#e6e6fa"},
		    {"lavenderblush", "#fff0f5"},{"lawngreen", "#7cfc00"},{"lemonchiffon", "#fffacd"},{"lightblue", "#add8e6"},
		    {"lightcoral", "#f08080"},{"lightcyan", "#e0ffff"},{"lightgoldenrodyellow", "#fafad2"},{"lightgray", "#d3d3d3"},
		    {"lightgreen", "#90ee90"},{"lightgrey", "#d3d3d3"},{"lightpink", "#ffb6c1"},{"lightsalmon", "#ffa07a"},
		    {"lightseagreen", "#20b2aa"},{"lightskyblue", "#87cefa"},{"lightslategray", "#778899"},{"lightslategrey", "#778899"},
		    {"lightsteelblue", "#b0c4de"},{"lightyellow", "#ffffe0"},{"lime", "#00ff00"},{"limegreen", "#32cd32"},
		    {"linen", "#faf0e6"},{"magenta", "#ff00ff"},{"maroon", "#800000"},{"mediumaquamarine", "#66cdaa"},
		    {"mediumblue", "#0000cd"},{"mediumorchid", "#ba55d3"},{"mediumpurple", "#9370db"},{"mediumseagreen", "#3cb371"},
		    {"mediumslateblue", "#7b68ee"},{"mediumspringgreen", "#00fa9a"},{"mediumturquoise", "#48d1cc"},{"mediumvioletred", "#c71585"},
		    {"midnightblue", "#191970"},{"mintcream", "#f5fffa"},{"mistyrose", "#ffe4e1"},{"moccasin", "#ffe4b5"},
		    {"navajowhite", "#ffdead"},{"navy", "#000080"},{"oldlace", "#fdf5e6"},{"olive", "#808000"},
		    {"olivedrab", "#6b8e23"},{"orange", "#ffa500"},{"orangered", "#ff4500"},{"orchid", "#da70d6"},
		    {"palegoldenrod", "#eee8aa"},{"palegreen", "#98fb98"},{"paleturquoise", "#afeeee"},{"palevioletred", "#db7093"},
		    {"papayawhip", "#ffefd5"},{"peachpuff", "#ffdab9"},{"peru", "#cd853f"},{"pink", "#ffc0cb"},
		    {"plum", "#dda0dd"},{"powderblue", "#b0e0e6"},{"purple", "#800080"},{"red", "#ff0000"},
		    {"rosybrown", "#bc8f8f"},{"royalblue", "#4169e1"},{"saddlebrown", "#8b4513"},{"salmon", "#fa8072"},
		    {"sandybrown", "#f4a460"},{"seagreen", "#2e8b57"},{"seashell", "#fff5ee"},{"sienna", "#a0522d"},
		    {"silver", "#c0c0c0"},{"skyblue", "#87ceeb"},{"slateblue", "#6a5acd"},{"slategray", "#708090"},
		    {"slategrey", "#708090"},{"snow", "#fffafa"},{"springgreen", "#00ff7f"},{"steelblue", "#4682b4"},
		    {"tan", "#d2b48c"},{"teal", "#008080"},{"thistle", "#d8bfd8"},{"tomato", "#ff6347"},
		    {"turquoise", "#40e0d0"},{"violet", "#ee82ee"},{"wheat", "#f5deb3"},{"white", "#ffffff"},
		    {"whitesmoke", "#f5f5f5"},{"yellow", "#ffff00"},{"yellowgreen", "#9acd32"}
		  };
		 
		  public ColorChooser() {
			  super();

			  // create a pane for showing info on the chosen color.
			  final HBox colorInfo = new HBox();
			  colorInfo.getStyleClass().add("colorInfo-box");
			  final Label selectedColorName = new Label();
			  HBox.setMargin(selectedColorName, new Insets(2, 0, 2, 10));
			  colorInfo.getChildren().addAll(selectedColorName);
			  chosenColorName.addListener(new ChangeListener<String>() {
				  @Override public void changed(ObservableValue<? extends String> observableValue, String oldName, String newName) {
					  if (newName != null) {
						  colorInfo.setStyle("-fx-background-color: " + newName + ";");
						  selectedColorName.setText(newName);
						  chosenColor.set(Color.web(newName));
					  }
				  }
			  });

			  // create a color swatch.
			  final GridPane swatch = new GridPane();
			  swatch.setHgap(2);
			  swatch.setVgap(2);
			  swatch.setPadding(new Insets(2));

			 // create a bunch of button controls for color selection.
			  int i = 0;
			  for (String[] namedColor : webPalette) {
				  final String colorName = namedColor[0];
				  final String colorHex = namedColor[1];

				  // create a button for choosing a color.
				  final Button colorChoice = new Button();
				  colorChoice.setUserData(namedColor);
				  colorChoice.setCursor(Cursor.HAND);

				  // position the button in the grid.
				  GridPane.setRowIndex(colorChoice, i / N_COLUMNS);
				  GridPane.setColumnIndex(colorChoice, i % N_COLUMNS);
				  colorChoice.setMinSize(TILE_SIZE, TILE_SIZE);
				  colorChoice.setMaxSize(TILE_SIZE, TILE_SIZE);

				  // add a mouseover tooltip to display more info on the colour being examined.
				  // todo it would be nice to be able to have the tooltip appear immediately on mouseover, but there is no easy way to do this, (file jira feature request?)
				  final Tooltip tooltip = new Tooltip(colorName);
				  tooltip.setStyle("-fx-font-size: 14");
				  tooltip.setContentDisplay(ContentDisplay.BOTTOM);
				  final Rectangle graphic = new Rectangle(40, 40, Color.web(colorHex));
				  tooltip.setGraphic(graphic);
				  colorChoice.setTooltip(tooltip);

				  // color the button appropriately and change it's hover functionality (doing some of this in a css sheet would be better).
				  final String backgroundStyle = "-fx-background-color: " + colorHex + "; -fx-background-insets: 0; -fx-background-radius: 0;";
				  colorChoice.setStyle(backgroundStyle);
				  colorChoice.setOnMouseEntered(new EventHandler<MouseEvent>() {
					  @Override public void handle(MouseEvent mouseEvent) {
						  final String borderStyle = "-fx-border-color: ladder(" + colorHex + ", whitesmoke 49%, darkslategrey 50%); -fx-border-width: 2;";
						  colorChoice.setStyle(backgroundStyle + borderStyle);
					  }
				  });
				  colorChoice.setOnMouseExited(new EventHandler<MouseEvent>() {
					  @Override public void handle(MouseEvent mouseEvent) {
						  final String borderStyle = "-fx-border-width: 0; -fx-border-insets: 2;";
						  colorChoice.setStyle(backgroundStyle + borderStyle);
					  }
				  });

				  // choose the color when the button is clicked.
				  colorChoice.setOnAction(new EventHandler<ActionEvent>() {
					  @Override public void handle(ActionEvent actionEvent) {
						  String[] c = (String[]) colorChoice.getUserData();
						  chosenColorName.set(c[0]); // Color Name
						  colorCode.set(c[1]); // Color Code
						}
				  });

				  // add the color choice to the swatch selection.
				  swatch.getChildren().add(colorChoice);

				  i++;
			  }

			  // select the first color in the chooser.
			  ((Button) swatch.getChildren().get(0)).fire();

			  // layout the color picker.
			  getChildren().addAll(swatch, colorInfo);
			  VBox.setVgrow(swatch, Priority.ALWAYS);
			  getStyleClass().add("color-palatte-popup");
		  }
	}
}
