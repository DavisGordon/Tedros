package org.tedros.fx.control;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.tedros.core.TLanguage;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TText.TTextStyle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class TAutoCompleteTextField extends TTextField {
	/** The existing autocomplete entries. */
	private final SortedSet<String> entries;
	/** The popup used to select an entry. */
	private ContextMenu entriesPopup;

	/** Construct a new AutoCompleteTextField. */
	public TAutoCompleteTextField() {

		this.setTooltip(new Tooltip(TLanguage.getInstance().getString(TFxKey.TOOLTIP_AUTOCOMPLETE)));
		entries = new TreeSet<>();
		entriesPopup = new ContextMenu();
		textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
				if (getText().length() == 0) {
					entriesPopup.hide();
				} else {
					LinkedList<String> searchResult = new LinkedList<>();
					searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
					if (entries.size() > 0) {
						populatePopup(searchResult);
						if (!entriesPopup.isShowing()) {
							entriesPopup.show(TAutoCompleteTextField.this, Side.BOTTOM, 0, 0);
						}
					} else {
						entriesPopup.hide();
					}
				}
			}
		});

		focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean aBoolean2) {
				entriesPopup.hide();
			}
		});

	}

	/**
	 * Get the existing set of autocomplete entries.
	 * 
	 * @return The existing autocomplete entries.
	 */
	public SortedSet<String> getEntries() {
		return entries;
	}

	/**
	 * Populate the entry set with the given search results. Display is limited to
	 * 10 entries, for performance.
	 * 
	 * @param searchResult The set of matching strings.
	 */
	private void populatePopup(List<String> searchResult) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		// If you'd like more entries, modify this line.
		int maxEntries = 10;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String result = searchResult.get(i);
			TextFlow entry = this.buildTextFlow(result, getText());
			CustomMenuItem item = new CustomMenuItem(entry, true);
			item.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					setText(result);
					entriesPopup.hide();
				}
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
		Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length())); // instead of "filter"
																								// to keep all "case
																								// sensitive"
		textBefore.settTextStyle(TTextStyle.MEDIUM);
		textAfter.settTextStyle(TTextStyle.MEDIUM);
		// textFilter.settTextStyle(TTextStyle.MEDIUM);
		textFilter.setFill(Color.ORANGE);
		textFilter.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		return new TextFlow(textBefore, textFilter, textAfter);
	}
}