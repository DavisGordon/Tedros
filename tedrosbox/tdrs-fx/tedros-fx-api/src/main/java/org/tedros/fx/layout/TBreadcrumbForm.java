package org.tedros.fx.layout;

import java.util.ArrayList;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.core.context.TEntry;
import org.tedros.fx.form.ITForm;
import org.tedros.fx.form.group.TGroupFormControl;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * TBreadcrumbForm
 */
public class TBreadcrumbForm extends HBox {
	
	public static String TBUTTON_TITLE =  "BT" ;
	public static String TFORM = "F";
    
	private StackPane tFormSpace;
    private ObservableList<TEntry<Object>> tEntryList;
	private TGroupFormControl tGroupFormControl;
	private List<Button> tButtonList = new ArrayList<Button>();
	private ListChangeListener<TEntry<Object>> entryListChangeListener;
	private TLanguage iEngine = TLanguage.getInstance(null);
    
    public TBreadcrumbForm(StackPane formSpace) {
    	super(0);
    	this.tFormSpace = formSpace;
        initialize();
    }
    
    public TBreadcrumbForm(StackPane formSpace, TGroupFormControl tGroupFormControl) {
    	super(0);
    	this.tFormSpace = formSpace;
    	this.tGroupFormControl = tGroupFormControl;
        initialize();
    }

	private void initialize() {
		this.tEntryList = FXCollections.observableArrayList();
		getStyleClass().setAll("t-breadcrumb-bar");
        setFillHeight(true);
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.CENTER_LEFT);
        entryListChangeListener = new ListChangeListener<TEntry<Object>>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends TEntry<Object>> arg0) {
				tBuildButtons();
			}
    	};
    	tAddEntryListChangeListener();
	}

	public void tBuildButtons() {
		tBuildBreadcrumbar(true);
	}

	public final void tBuildBreadcrumbar(boolean showForm) {
		getChildren().clear();
    	tButtonList.clear();
		if(this.tEntryList==null)
    		return;
    		
    	int i = 0;
        for (final TEntry<Object> item : this.tEntryList) {
        	if (i<this.tEntryList.size()) {
                final Button button = buildButton(i, item);
                button.setVisible(true) ;
                button.setText(iEngine.getString((String) item.getEntry(TBUTTON_TITLE)));
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	showForm(item);
                    	tRemoveEntryListChangeListener();
                    	int index = tEntryList.indexOf(item);
                    	for (int i=tEntryList.size()-1; i>index; i--)
                    		tEntryList.remove(i);
                    	tAddEntryListChangeListener();
                    	tBuildBreadcrumbar(false);
                    }

									
                });
                if (i == this.tEntryList.size()-1) {
                    if(i==0) {
                        button.getStyleClass().setAll("button","only-button");
                        button.setDisable(true);
                    } else {
                        button.getStyleClass().setAll("button","last-button");
                    }
                } else if (i==0) {
                    button.getStyleClass().setAll("button","first-button");
                } else {
                    button.getStyleClass().setAll("button","middle-button");
                }
	            if(showForm && i==this.tEntryList.size()-1)
	            	showForm(item);
	            i++;
        	}else
        		 tButtonList.get(i).setVisible(false);
        }
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);
	}

	private Button buildButton(int i, final TEntry<Object> item) {
		Button button = null;
		if (i<tButtonList.size()) {
		    // alread have a button
		    button = tButtonList.get(i);
		} else {
		    button = new Button( (String) item.getEntry(TBUTTON_TITLE));
		    button.setMaxHeight(Double.MAX_VALUE);
		    tButtonList.add(button);
		    getChildren().add(button);
		}
		return button;
	}
    

	public StackPane gettFormSpace() {
		return tFormSpace;
	}

	public void settFormSpace(StackPane tFormSpace) {
		this.tFormSpace = tFormSpace;
	}

	public TGroupFormControl gettGroupFormControl() {
		return tGroupFormControl;
	}

	public void settGroupFormControl(TGroupFormControl tGroupFormControl) {
		this.tGroupFormControl = tGroupFormControl;
	}

	public List<Button> gettButtonList() {
		return tButtonList;
	}

	public void settButtonList(List<Button> tButtonList) {
		this.tButtonList = tButtonList;
	}
	
	private synchronized void showForm(final TEntry<Object> item) {
		try {
			if(tGroupFormControl!=null)
				tGroupFormControl.runBefore(item, tEntryList);
			
			final ITForm form = (ITForm) item.getEntry(TFORM);
			//form.tReloadForm();
			ScrollPane scroll = new ScrollPane();
			scroll.setId("t-form-scroll");
			scroll.setContent((Node)form);
			scroll.setFitToWidth(true);
			scroll.maxHeight(Double.MAX_VALUE);
			scroll.maxWidth(Double.MAX_VALUE);
			scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
			scroll.setStyle("-fx-background-color: transparent;");
					
			tFormSpace.getChildren().clear();
			tFormSpace.getChildren().add(scroll);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
	}

	public void tRemoveEntryListChangeListener() {
		if(entryListChangeListener!=null && tEntryList!=null)
			tEntryList.removeListener(entryListChangeListener);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void tAddEntryListChangeListener() {
		if(entryListChangeListener!=null && tEntryList!=null && !tEntryList.contains(entryListChangeListener))
			tEntryList.addListener(entryListChangeListener);
	}
	
	public SimpleListProperty<TEntry<Object>> tEntryListProperty() {
		return new SimpleListProperty<>(tEntryList);
	}

}
