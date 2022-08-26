package org.tedros.fx.layout;

import java.util.ArrayList;
import java.util.List;

import org.tedros.api.presenter.view.ITGroupViewItem;
import org.tedros.api.presenter.view.ITView;
import org.tedros.fx.presenter.view.group.TGroupViewControl;
import org.tedros.fx.presenter.view.group.TViewItem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * TBreadcrumbView
 */
public class TBreadcrumbView extends HBox {
    
    private List<Button> buttons = new ArrayList<Button>();
    private List<TViewItem> itens;
	private StackPane contentPane;
    private TGroupViewControl tGroupControl;
    
    public TBreadcrumbView(StackPane contentPane) {
    	super(0);
    	this.contentPane = contentPane;
        initialize();
    }
    
    public TBreadcrumbView(StackPane contentPane, TGroupViewControl tGroupControl) {
    	super(0);
    	this.contentPane = contentPane;
    	this.tGroupControl = tGroupControl;
        initialize();
    }

	private void initialize() {
		getStyleClass().setAll("t-breadcrumb-bar");
        setFillHeight(true);
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.CENTER_LEFT);
	}

    public List<TViewItem> getGroupItens() {
        return itens;
    }

    public void setGroupItens(List<TViewItem> itens) {
    	this.itens = itens;
    	int i = 0;
        for (final ITGroupViewItem item : this.itens) {
        	if (i<this.itens.size()) {
                Button button = null;
                if (i<buttons.size()) {
                    // alread have a button
                    button = buttons.get(i);
                } else {
                    button = new Button(item.getButtonTitle());
                    button.setMaxHeight(Double.MAX_VALUE);
                    buttons.add(button);
                    getChildren().add(button);
                }
                button.setVisible(true) ;
                button.setText(item.getButtonTitle());
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	buildView(item);
                    }					
                });
                if (i == this.itens.size()-1) {
                    if(i==0) {
                        button.getStyleClass().setAll("button","only-button");
                    } else {
                        button.getStyleClass().setAll("button","last-button");
                    }
                } else if (i==0) {
                    button.getStyleClass().setAll("button","first-button");
                } else {
                    button.getStyleClass().setAll("button","middle-button");
                }
	            if(i==0)
	            	buildView(item);
	            i++;
        	}else
        		 buttons.get(i).setVisible(false);
        }
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        getChildren().add(spacer);
    }
    
    private void buildView(final ITGroupViewItem item) {
		try {
			final ITView<?> view = item.getViewInstance(item.getModule());
			if(tGroupControl!=null)
				tGroupControl.initializeView(item, itens);
			contentPane.getChildren().clear();
			contentPane.getChildren().add((Node)view);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
	}

	public final StackPane getContentPane() {
		return contentPane;
	}

	public final void setContentPane(StackPane contentPane) {
		this.contentPane = contentPane;
	}

	public final TGroupViewControl gettGroupControl() {
		return tGroupControl;
	}

	public final void settGroupControl(TGroupViewControl tGroupControl) {
		this.tGroupControl = tGroupControl;
	}

	public final List<Button> getButtons() {
		return buttons;
	}

	public final List<TViewItem> getItens() {
		return itens;
	}
}
