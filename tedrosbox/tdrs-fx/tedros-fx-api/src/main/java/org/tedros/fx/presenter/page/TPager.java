/**
 * 
 */
package org.tedros.fx.presenter.page;


import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TFxKey;
import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TComboBoxField;
import org.tedros.fx.control.THRadioGroup;
import org.tedros.fx.control.TLabel;
import org.tedros.fx.control.TMaskField;
import org.tedros.fx.control.TOption;
import org.tedros.fx.control.TSlider;
import org.tedros.fx.form.TConverter;
import org.tedros.server.query.TCompareOp;
import org.tedros.util.TDateUtil;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * @author Davis Gordon
 *
 */
public class TPager extends BorderPane {

	
	private double totalItens = 25.0;
	private double maxItens = 100.0;
	private int lastEnd;
	private String lastButton;
	
	private TSlider slider;
	private TButton searchButton;
	private TButton clearButton;
	private PopOver searchPopover;
	private TMaskField search = null;
	private TComboBoxField<TOption<String>> searches;
	private TComboBoxField<TOption<String>> orderBy;
	private THRadioGroup orderByType;
	private ToolBar toolbar;
	private TLabel label;
	
	private SimpleObjectProperty<TPagination> paginationProperty;
	
	private TRepository repo;
	
	public TPager(boolean showSearch, boolean showOrderBy) {
		
		setId("t-form");
		paginationProperty = new SimpleObjectProperty<>();
		
		TLanguage iEngine = TLanguage.getInstance(null);
		
		repo = new TRepository();
		label = new TLabel();
		label.setId("t-title-label");
		lastButton = null;
		
		slider = new TSlider();
		slider.setMax(maxItens);
		slider.setMin(totalItens);
		slider.setValue(totalItens);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(totalItens);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		
		ChangeListener<Number> ehs = (a0, a1, a2) ->{
			if(!slider.isValueChanging() 
					|| (slider.isValueChanging() && (a2.doubleValue()==totalItens || a2.doubleValue()==maxItens))) {
				lastButton = null;
				paginationProperty.setValue(buildPagination(0));
			}
		};
		repo.add("slider", ehs);
		slider.valueProperty().addListener(new WeakChangeListener<>(ehs));
		TLabel title = new TLabel(iEngine.getString(TFxKey.CURRENT_PAGE) + ":") ;
		title.setId("t-title-label");
		title.setFont(Font.font(16));
		HBox pane = new HBox();
		pane.setId("t-view-title-box");
		HBox.setHgrow(label, Priority.ALWAYS);
		HBox.setMargin(label, new Insets(0, 5, 0, 15));
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.getChildren().addAll(title, label);
		
		VBox box = new VBox();
		box.getChildren().add(pane);
		
		if(showSearch) {
			searchButton = new TButton();
			searchButton.setText(iEngine.getString(TFxKey.BUTTON_SEARCH));
			searchButton.setId("t-button");
			EventHandler<ActionEvent> eh = e ->{
				if(validateSearch(search.getText())) {
					lastButton = null;
					paginationProperty.setValue(buildPagination(0));
				}
			};
			repo.add("searchbtn", eh);
			searchButton.setOnAction(new WeakEventHandler<>(eh));
			
			clearButton = new TButton();
			clearButton.setText(iEngine.getString(TFxKey.BUTTON_CLEAN));
			clearButton.setId("t-last-button");
			EventHandler<ActionEvent> eh1 = e ->{
				lastButton = null;
				search.setText("");
				paginationProperty.setValue(buildPagination(0));
			};
			repo.add("clearhbtn", eh1);
			clearButton.setOnAction(new WeakEventHandler<>(eh1));

			searches = new TComboBoxField<>();
			ChangeListener<TOption<String>> chl0 = (a0, o, n) ->{
				TSearch s = (TSearch) n.getUserData();
				if(s.mask!=null) {
					search.setMask(s.mask);
					search.setPromptText(s.mask);
				}else if(s.temporal!=null && !s.temporal.equals(TTemporal.NONE)) {
					String m = TLanguage.getLocale().equals(new Locale("pt")) 
							? "99/99/9999"
								: "99-99-9999";
					if(s.temporal.equals(TTemporal.DATETIME))
						m+=" 99:99";
					search.setMask(m);
					search.setPromptText(m);
				}else {
					search.setMask(null);
					search.setPromptText(null);
				}
			};
			repo.add("searches", chl0);
			searches.valueProperty().addListener(new WeakChangeListener<>(chl0));
			
			search = new TMaskField();
			search.setMaxHeight(searchButton.getHeight());
			ChangeListener<String> schl = (a,o,n) -> {
				validateSearch(n);
			};
			repo.add("schl", schl);
			search.textProperty().addListener(new WeakChangeListener<>(schl));
			search.setStyle("-fx-background-radius: 10 0 0 10; -fx-border-radius: 10 0 0 10;");
			HBox h0 = new HBox(0);
			HBox.setHgrow(search, Priority.ALWAYS);
			HBox.setHgrow(searches, Priority.ALWAYS);
			h0.getChildren().addAll(search, searches);
			
			ToolBar h1 = new ToolBar();
			h1.setId("t-view-toolbar");
			h1.getItems().addAll(searchButton, clearButton);
			box.getChildren().addAll(h0, h1);
		}
		
		if(showOrderBy) {

			orderBy = new TComboBoxField<>();
			orderBy.setPromptText(iEngine.getString(TFxKey.SORT_BY));
			ChangeListener<TOption<String>> eh = (a0, a1, a2) ->{
				lastButton = null;
				paginationProperty.setValue(buildPagination(0));
			};
			repo.add("orderBy", eh);
			orderBy.valueProperty().addListener(new WeakChangeListener<>(eh));
			
			HBox h1 = new HBox();
			h1.setId("t-view-toolbar");
			h1.setAlignment(Pos.BOTTOM_LEFT);
			h1.setPadding(new Insets(10, 0, 0, 0));
			HBox.setHgrow(orderBy, Priority.ALWAYS);
			orderBy.setMinWidth(200);
			h1.getChildren().addAll(orderBy);
			
			orderByType = new THRadioGroup();
			ChangeListener<Toggle> eh1 = (a0, a1, a2) ->{
				lastButton = null;
				paginationProperty.setValue(buildPagination(0));
			};
			repo.add("orderByType", eh1);
			orderByType.selectedToggleProperty().addListener(new WeakChangeListener<>(eh1));
			
			RadioButton ascRadioBtn = new RadioButton(iEngine.getString(TFxKey.SORT_BY_ASC)); 
			RadioButton descRadioBtn = new RadioButton(iEngine.getString(TFxKey.SORT_BY_DESC)); 
			ascRadioBtn.setSelected(true);
			ascRadioBtn.setUserData(true);
			descRadioBtn.setUserData(false);
			orderByType.addRadioButton(ascRadioBtn);
			orderByType.addRadioButton(descRadioBtn);
			THRadioGroup.setMargin(ascRadioBtn, new Insets(0, 5, 0, 0));
			
			HBox h2 = new HBox();
			h2.setId("t-view-toolbar");
			h2.setAlignment(Pos.BOTTOM_LEFT);
			h2.setPadding(new Insets(10, 0, 0, 0));
			HBox.setHgrow(orderByType, Priority.ALWAYS);
			h2.getChildren().addAll(orderByType);
			
			box.getChildren().addAll(h1, h2);
		}
		
		BorderPane.setMargin(box, new Insets(10, 8, 10, 8) );
		BorderPane.setMargin(slider, new Insets(0, 15, 0, 15) );
		setTop(box);
		setCenter(slider);
		
		toolbar = new ToolBar();
		toolbar.setId("t-view-toolbar");
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setContent(toolbar);
		sp.setStyle("-fx-background-color: transparent;");
		setBottom(sp);
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean validateSearch(String n) {
		if(searchPopover!=null && searchPopover.isShowing())
			searchPopover.hide();
		if(StringUtils.isNotBlank(n)) {
			TSearch s = this.searches.getValue()!=null
					? (TSearch) this.searches.getValue().getUserData()
							: null;
			if(s!=null) {
				if(s.converter!=TConverter.class) {
					try {
						TConverter converter = s.converter.newInstance();
						converter.setIn(n);
						converter.getOut();
					} catch (Exception e) {
						showSearchMsg(e.getMessage());
						return false;
					}
				}else
				if(s.temporal!=null && !s.temporal.equals(TTemporal.NONE)) {
					String m = TLanguage.getLocale().equals(new Locale("pt")) 
							? "dd/MM/yyyy"
								: "MM-dd-yyyy";
					if(s.temporal.equals(TTemporal.DATETIME))
						m+=" HH:mm";
					if(m.length()==n.length()) {
						try {
							TDateUtil.getDate(n, m);
						} catch (ParseException e1) {
							showSearchMsg(TLanguage.getInstance().getString(TFxKey.VALIDATOR_INCORRECT_DATA));
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void showSearchMsg(String msg) {
		TLabel l = new TLabel(msg);
		searchPopover = new PopOver(l);
		searchPopover.setAutoHide(true);
		searchPopover.setArrowLocation(ArrowLocation.TOP_CENTER);
		searchPopover.show(search);
	}
	
	@SuppressWarnings("rawtypes")
	public void tAddSearchOption(String text, String field, String alias, String mask, String prompt, 
			TCompareOp operator, TTemporal temporal, Class<? extends TConverter> converter) {
		searches.getItems().add(new TOption<>(TLanguage
				.getInstance(null)
				.getString(text), field, new TSearch(field, alias, text, mask, prompt, 
						operator, temporal, converter)));
		if(searches.getSelectionModel().isEmpty())
			searches.getSelectionModel().selectFirst();
	}
	
	public void tAddOrderByOption(String text, String field, String alias) {
		orderBy.getItems().add(new TOption<>(TLanguage
				.getInstance(null)
				.getString(text), field, alias));
		if(orderBy.getSelectionModel().isEmpty())
			orderBy.getSelectionModel().selectFirst();
	}
	
	public void tReload(long totalRows) {
		removeBtnEvent();
		toolbar.getItems().clear();
		
		double pag = slider.getValue();
		double total = (totalRows > pag) ?
				totalRows / pag
				: 1;
		
		lastEnd = (int) (pag - 1);
		
		for(int i = 0; i<total; i++) {
			TButton p = buildItem(i, totalRows);
			toolbar.getItems().add(p);
		}
		Node firstNode = toolbar.getItems().get(0);
		
		label.setText(lastButton==null 
					? ((TButton)firstNode).getText()
							: lastButton
				);
		
		Node lastNode = toolbar.getItems().get(toolbar.getItems().size()-1);
		lastNode.setId("t-last-button");;
	}
	
	private TButton buildItem(int startAt, long totalRows) {
		
		final double pag = slider.getValue();
		final int i = (int) ((startAt == 0) 
					? startAt
						: startAt * pag);
		
		int begin = i==0 ? i : lastEnd+1; 
		int end = (begin+((int)pag-1));
		lastEnd = end;

		String l =   (begin==0 ? "1" : begin) + "-" + (end>totalRows ? totalRows : end);
		String eId = UUID.randomUUID().toString();
		final TButton p1 = new TButton() ;
		p1.setId("t-button");
		p1.setText(l);
		p1.setUserData(eId);
		EventHandler<ActionEvent> eh = e ->{
			lastButton = p1.getText();
			label.setText(lastButton);
			paginationProperty.setValue(buildPagination(i));
		};
		repo.add(eId, eh);
		p1.setOnAction(eh);
		
		return p1;
	}
	
	private void removeBtnEvent() {
		
		for(Node node : toolbar.getItems()) {
			TButton b = (TButton) node;
			EventHandler<ActionEvent> e =  repo.remove((String) b.getUserData());
			b.removeEventHandler(ActionEvent.ACTION, e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TPagination buildPagination(int start){
		String orderBy = gettOrderBy();
		String orderAlias = gettOrderByAlias();
		boolean orderAsc = gettOrderAsc();
		String prompt = search!=null ? search.getText() : null;
		Object value = prompt;
		TSearch s = this.searches!=null && this.searches.getValue()!=null
				? (TSearch) this.searches.getValue().getUserData()
						: null;
		if(s!=null) {
			if(s.converter!=TConverter.class) {
				try {
					TConverter converter = s.converter.newInstance();
					converter.setIn(prompt);
					value = converter.getOut();
				} catch (Exception e) {
					showSearchMsg(e.getMessage());
				}
			}else if(s.temporal!=null && !s.temporal.equals(TTemporal.NONE)) {
				String m = TLanguage.getLocale().equals(new Locale("pt")) 
						? "dd/MM/yyyy"
							: "MM-dd-yyyy";
				if(s.temporal.equals(TTemporal.DATETIME))
					m+=" HH:mm";
				if(m.length()==prompt.length()) {
					try {
						value = TDateUtil.getDate(prompt, m);
					} catch (ParseException e1) {
						showSearchMsg(TLanguage.getInstance().getString(TFxKey.VALIDATOR_INCORRECT_DATA));
					}
				}
			}
		}
		return new TPagination(value, s,
				orderBy, orderAlias, orderAsc, 
				start, gettTotalRows());
	}

	/**
	 * @return
	 */
	public int gettTotalRows() {
		return (int) slider.getValue();
	}

	/**
	 * @return
	 */
	public boolean gettOrderAsc() {
		return this.orderByType!=null 
				? (boolean) this.orderByType.getSelectedToggle().getUserData() 
						: true;
	}

	/**
	 * @return
	 */
	public String gettOrderByAlias() {
		return this.orderBy!=null && this.orderBy.getValue()!=null 
				? (String) this.orderBy.getValue().getUserData() 
						: null;
	}
	/**
	 * @return
	 */
	public String gettOrderBy() {
		return this.orderBy!=null && this.orderBy.getValue()!=null 
				? this.orderBy.getValue().getValue() 
						: null;
	}
	
	public void settOrderBy(int index) {
		if(index>=0 && this.orderBy!=null)
			this.orderBy.getSelectionModel().select(index);
	}
	
	public void settOrderBy(String fieldName) {
		if(StringUtils.isNotBlank(fieldName) && this.orderBy!=null) {
			Optional<TOption<String>> op = this.orderBy.getItems().stream()
			.filter(e->{
				return e.getValue().equals(fieldName);
			}).findFirst();
			if(op.isPresent()) 
				this.orderBy.getSelectionModel().select(op.get());
		}
	}
	
	public TPagination gettPagination(){
		return buildPagination(0);
	}

	/**
	 * @return the paginationProperty
	 */
	public ReadOnlyObjectProperty<TPagination> tPaginationProperty() {
		return paginationProperty;
	}
	
	public void tDispose() {
		repo.clear();
	}

}
