package org.tedros.fx.control;

import java.util.LinkedList;
import java.util.List;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.server.entity.TEntity;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * This class is a TextField which implements an "autocomplete" functionality,
 * based on a supplied list of entries.
 * 
 * @author Caleb Brinkman
 */
public class TAutoCompleteEntity extends TTextField {
	private int startLength;
	private int totalItemsList;
	private Side side;
	private SimpleObjectProperty<TEntity> tSelectedItemProperty;
	private String serviceName;
	private Class<? extends TEntity> eClass;
	private String[] fieldsName;
	/** The existing autocomplete entries. */
	private final ObservableList<TEntity> entries;
	/** The popup used to select an entry. */
	private ContextMenu entriesPopup;
	
	private ListChangeListener<TEntity> lchl;
	private ChangeListener<String> chl;
	private ChangeListener<Boolean> fchl;
	private ChangeListener<TEntity> echl;
	
	
	/** Construct a new AutoCompleteTextField. */
	
	public TAutoCompleteEntity( Class<? extends TEntity> eClass, 
			int startLength, int totalItemsList,
			String serviceName, String... fieldsName) {
		this.setTooltip(new Tooltip(TLanguage.getInstance()
				.getString(TFxKey.TOOLTIP_AUTOCOMPLETE)));
		this.serviceName = serviceName;
		this.eClass = eClass;
		this.fieldsName = fieldsName;
		this.side = Side.BOTTOM;
		this.startLength = startLength>0 ? startLength : 3;
		this.totalItemsList = totalItemsList>0 ? totalItemsList : 6;
		this.tSelectedItemProperty = new SimpleObjectProperty<>();
		entriesPopup = new ContextMenu();
		entries = FXCollections.observableArrayList();
		buildListeners();
		entries.addListener(new WeakListChangeListener<>(lchl));
		textProperty().addListener(chl);
		focusedProperty().addListener(new WeakChangeListener<>(fchl));
		this.tSelectedItemProperty.addListener(new WeakChangeListener<>(echl));
	}

	/**
	 * @return the tSelectedItemProperty
	 */
	public SimpleObjectProperty<TEntity> tSelectedItemProperty() {
		return tSelectedItemProperty;
	}
	
	@Override
	@SuppressWarnings({ "unchecked"})
	public <T extends Observable> T tValueProperty() {
		return (T) tSelectedItemProperty();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildListeners() {
		echl = (a,o,n)->{
			if(n!=null) {
				textProperty().removeListener(chl);
				setText(n.toString());
				entries.clear();
				textProperty().addListener(chl);
			}
		};
		
		lchl = ch->{
			if(ch.next()) {
				if(ch.wasAdded()) {
					populatePopup(ch.getAddedSubList());
					if (!entriesPopup.isShowing()) 
						entriesPopup.show(TAutoCompleteEntity.this, side, 0, 0);
				}else if(ch.wasRemoved())
					entriesPopup.hide();
			}
		};
		
		chl = (a,o,n) -> {
			if (n==null || n.length() < startLength) {
				entries.clear();
				this.tSelectedItemProperty.setValue(null);
			} else if(n.length()>=startLength){
				try {
					TSelect sel = new TSelect(eClass);
					for(String f : fieldsName)
						sel.addOrCondition(f, TCompareOp.LIKE, "%"+n+"%");
					TEntityProcess p = new TEntityProcess(eClass, serviceName) {};
					p.stateProperty().addListener((a1, o1, n1)->{
						if(n1.equals(State.SUCCEEDED)) {
							List<TResult<List>> l = (List<TResult<List>>) p.getValue();
							if(l.size()>0) {
								TResult<List> res = l.get(0);
								if(res.getState().equals(TState.SUCCESS)) {
									List l1 = res.getValue();
									if(l1.size()>0)
										entries.addAll(l1);
									else
										entries.clear();
								}else 
									entries.clear();
							}else
								entries.clear();
						}
					});
					p.search(sel);
					p.startProcess();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		fchl = (a,o,n) -> entries.clear();
	}


	/**
	 * Populate the entry set with the given search results. Display is limited to
	 * 10 entries, for performance.
	 * 
	 * @param list The set of matching strings.
	 */
	private void populatePopup(List<? extends TEntity> list) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		// If you'd like more entries, modify this line.
		int count = Math.min(list.size(), this.totalItemsList);
		for (int i = 0; i < count; i++) {
			final TEntity result = list.get(i);
			TextFlow text = this.buildTextFlow(result.toString(), getText());
			CustomMenuItem item = new CustomMenuItem(text, true);
			item.setOnAction(ev ->  {
				tSelectedItemProperty.setValue(result);
			});
			menuItems.add(item);
		}
		entriesPopup.getItems().clear();
		entriesPopup.getItems().addAll(menuItems);

	}
	
	private TextFlow buildTextFlow(String text, String filter) {        
	    int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
	    TText textBefore = new TText(text.substring(0, filterIndex));
	    TText textAfter = new TText(text.substring(filterIndex + filter.length()));
	    Text textFilter = new 
	    		Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
	    textBefore.settTextStyle(TTextStyle.MEDIUM);
	    textAfter.settTextStyle(TTextStyle.MEDIUM);
	    textFilter.setFill(Color.ORANGE);
	    textFilter.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));  
	    return new TextFlow(textBefore, textFilter, textAfter);
	}
}