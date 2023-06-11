/**
 * 
 */
package org.tedros.fx.presenter.assistant;

import java.util.Arrays;
import java.util.Date;

import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.message.TMessageType;
import org.tedros.core.model.ITModelView;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TFxKey;
import org.tedros.fx.control.TButton;
import org.tedros.fx.control.TTextAreaField;
import org.tedros.fx.control.TVerticalRadioGroup;
import org.tedros.fx.exception.TException;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.modal.TMessageBox;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.model.TModelViewBuilder;
import org.tedros.fx.util.JsonHelper;
import org.tedros.fx.util.TChatUtil;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITModel;
import org.tedros.server.model.TJsonModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TAiAssistant<M extends TModelView> extends BorderPane {
	
	private Class<? extends TJsonModel> jsonModelType;
	
	private RadioButton analyseRadio;
	private RadioButton changeRadio;
	private RadioButton createRadio;
	private TVerticalRadioGroup tActions;
	private TButton tSendButton;
	private TButton tClearButton;
	private TTextAreaField tPrompt;
	private TToolBar toolbar;
	private ITView tView;
	

	private SimpleObjectProperty<M> tOutModel;
	private SimpleObjectProperty<M> tTargetModel;
	private ObservableList<M> tModels;
	private Class<M> tModelViewType;
	
	private TRepository repo;

	/**
	 * @param jsonModelType
	 */
	@SuppressWarnings("unchecked")
	public TAiAssistant(Class<? extends TModelView> modelViewType, Class<? extends TJsonModel> jsonModelType) {
		this.jsonModelType = jsonModelType;
		this.tModelViewType = (Class<M>) modelViewType;
		this.repo = new TRepository();
		this.tOutModel = new SimpleObjectProperty<>();
		TLanguage iEng = TLanguage.getInstance();
		analyseRadio = new RadioButton(iEng.getString(TFxKey.ANALYSE));
		analyseRadio.setTooltip(new Tooltip(iEng.getString(TFxKey.TOOLTIP_AI_ANALYSE)));
		changeRadio = new RadioButton(iEng.getString(TFxKey.CHANGE));
		changeRadio.setTooltip(new Tooltip(iEng.getString(TFxKey.TOOLTIP_AI_CHANGE)));
		createRadio = new RadioButton(iEng.getString(TFxKey.CREATE));
		createRadio.setTooltip(new Tooltip(iEng.getString(TFxKey.TOOLTIP_AI_CREATE)));
		this.tActions = new TVerticalRadioGroup();
		this.tSendButton = new TButton(iEng.getString(TFxKey.BUTTON_SEND));
		this.tClearButton = new TButton(iEng.getString(TFxKey.BUTTON_CLEAN));
		this.tPrompt = new TTextAreaField();
		this.tPrompt.setWrapText(true);
		this.tPrompt.setRequired(true);
		this.toolbar = new TToolBar();
		this.toolbar.getItems().addAll(this.tSendButton, this.tClearButton);
		VBox pane = new VBox(10);
		pane.getChildren().addAll(this.tActions, this.tPrompt, this.toolbar);
		this.setCenter(pane);
		this.buildListeners();
	}
	
	private void buildListeners() {
		this.toolbar.disableProperty().bind(tPrompt.requirementAccomplishedProperty().not());
		
		EventHandler<ActionEvent> clearEv = ev ->{
			this.tPrompt.clear();
		};
		this.repo.add("clearEv", clearEv);
		this.tClearButton.setOnAction(new WeakEventHandler<>(clearEv));
		
		EventHandler<ActionEvent> sendEv = ev ->{
			callAssistant();
		};
		this.repo.add("sendEv", sendEv);
		this.tSendButton.setOnAction(new WeakEventHandler<>(sendEv));
	}
	
	private void callAssistant() {

		Toggle selected = this.tActions.getSelectedToggle();
		if(selected.equals(this.createRadio)) 
			processCreate();
		else 
		if(selected.equals(this.analyseRadio)) 
			processAnalyse();
		else
		if(selected.equals(this.changeRadio))
			processChange();
		
	}

	@SuppressWarnings("unchecked")
	private void processChange() {
		try {
			TJsonModel m = this.jsonModelType.newInstance();
			m.setModel("insert");
			TAiAssistantProcess prc = new TAiAssistantProcess();
			tView.gettProgressIndicator().bind(prc.runningProperty());
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<TChatResult> res = prc.getValue();
					if(res.getState().equals(TState.SUCCESS)) {
						TChatResult r = res.getValue();
						if(r.getChoices()!=null) {
							r.getChoices().forEach(c->{
								//System.out.println(c.getMessage().getContent());
								//System.out.println("-----");
								try {
									TJsonModel t = JsonHelper.read(c.getMessage().getContent(), this.jsonModelType);
									if(t.getData()!=null) {
										if(t.getData().size()==1) {
											try {
												M mv = this.tTargetModel.getValue();
												mv.reload((ITModel) t.getData().get(0));
											} catch (Exception e1) {
												e1.printStackTrace();
												showErrorMessage(e1);
											}
										}
									}
									if(t.getAssistantMessage()!=null) {
										Pane v = (Pane) tView;
										double w = v.getBoundsInLocal().getWidth()-200;
										double h = v.getBoundsInLocal().getHeight()-200;
										StackPane msg = TChatUtil
												.buildTextPane("Teros", t.getAssistantMessage(),w-20, new Date());
										VBox pane = new VBox();
										pane.getChildren().add(msg);
										pane.setMinSize(w,h);
										pane.setMaxSize(w,h);
										pane.setPrefSize(w,h);
										showModal(pane);
									}
								} catch (Exception e) {
									e.printStackTrace();
									showErrorMessage(e);
								}
							});
						}else {
							if(r.getLog()!=null) {
								String msg = r.getLog();
								if(r.getErrorCode()!=null)
									msg += "\n"+r.getErrorCode();
								showErrorMessage(msg);
							}
						}
					}else {
						showErrorMessage(res.getMessage());
					}
				}
			});
			prc.askForChange(m, this.tPrompt.getText(), this.tTargetModel.getValue().getModel());
			prc.startProcess();
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			showErrorMessage(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void processCreate() {
		try {
			TJsonModel m = this.jsonModelType.newInstance();
			m.setModel("insert");
			TAiAssistantProcess prc = new TAiAssistantProcess();
			tView.gettProgressIndicator().bind(prc.runningProperty());
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<TChatResult> res = prc.getValue();
					if(res.getState().equals(TState.SUCCESS)) {
						TChatResult r = res.getValue();
						if(r.getChoices()!=null) {
							r.getChoices().forEach(c->{
								//System.out.println(c.getMessage().getContent());
								//System.out.println("-----");
								try {
									TJsonModel t = JsonHelper.read(c.getMessage().getContent(), this.jsonModelType);
									if(t.getData()!=null) {
										if(t.getData().size()==1) {
											try {
												M mv = buildModelView(t.getData().get(0));
												this.tOutModel.setValue(mv);
												this.tOutModel.setValue(null);
											} catch (TException e1) {
												e1.printStackTrace();
												showErrorMessage(e1);
											}
										}else
											t.getData().forEach(e->{
												try {
													M mv = buildModelView(e);
													tModels.add(mv);
												} catch (TException e1) {
													e1.printStackTrace();
													showErrorMessage(e1);
												}
											});
									}
									if(t.getAssistantMessage()!=null) {
										Pane v = (Pane) tView;
										double w = v.getBoundsInLocal().getWidth()-200;
										double h = v.getBoundsInLocal().getHeight()-200;
										StackPane msg = TChatUtil
												.buildTextPane("Teros", t.getAssistantMessage(),w-20, new Date());
										VBox pane = new VBox();
										pane.getChildren().add(msg);
										pane.setMinSize(w,h);
										pane.setMaxSize(w,h);
										pane.setPrefSize(w,h);
										showModal(pane);
									}
								} catch (Exception e) {
									e.printStackTrace();
									showErrorMessage(e);
								}
							});
						}else {
							if(r.getLog()!=null) {
								String msg = r.getLog();
								if(r.getErrorCode()!=null)
									msg += "\n"+r.getErrorCode();
								showErrorMessage(msg);
							}
						}
					}else {
						showErrorMessage(res.getMessage());
					}
				}
			});
			prc.askForCreate(m, this.tPrompt.getText());
			prc.startProcess();
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			showErrorMessage(e);
		}
	}

	private void processAnalyse() {
		try {
			TJsonModel m = this.jsonModelType.newInstance();
			m.setModel("analyse");
			TAiAssistantProcess prc = new TAiAssistantProcess();
			tView.gettProgressIndicator().bind(prc.runningProperty());
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<TChatResult> res = prc.getValue();
					if(res.getState().equals(TState.SUCCESS)) {
						TChatResult r = res.getValue();
						if(r.getChoices()!=null) {
							r.getChoices().forEach(c->{
								//System.out.println(c.getMessage().getContent());
								//System.out.println("-----");
								try {
									TJsonModel t = JsonHelper.read(c.getMessage().getContent(), this.jsonModelType);
									
									if(t.getAssistantMessage()!=null) {
										Pane v = (Pane) tView;
										double w = v.getBoundsInLocal().getWidth()-200;
										double h = v.getBoundsInLocal().getHeight()-200;
										StackPane msg = TChatUtil
												.buildTextPane("Teros", t.getAssistantMessage(),w-20, new Date());
										VBox pane = new VBox();
										pane.getChildren().add(msg);
										pane.setMinSize(w,h);
										pane.setMaxSize(w,h);
										pane.setPrefSize(w,h);
										showModal(pane);
									}
								} catch (Exception e) {
									e.printStackTrace();
									showErrorMessage(e);
								}
							});
						}else {
							if(r.getLog()!=null) {
								String msg = r.getLog();
								if(r.getErrorCode()!=null)
									msg += "\n"+r.getErrorCode();
								showErrorMessage(msg);
							}
						}
					}else {
						showErrorMessage(res.getMessage());
					}
				}
			});
			prc.askForAnalyse(m, this.tPrompt.getText(), 
					Arrays.asList(this.tTargetModel.getValue().getModel()));
			prc.startProcess();
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			showErrorMessage(e);
		}
	}

	@SuppressWarnings("unchecked")
	private M buildModelView(Object e) throws TException {
		M mv = (M) TModelViewBuilder.create()
			.modelViewClass((Class<? extends ITModelView<?>>) tModelViewType)
			.build((ITEntity) e);
		return mv;
	}
	
	private void showErrorMessage(Throwable ex) {
		final TMessageBox tMessageBox = new TMessageBox(ex);
		showModal(tMessageBox);
	}

	private void showErrorMessage(String message) {
		final TMessageBox tMessageBox = new TMessageBox(message, TMessageType.ERROR);
		showModal(tMessageBox);
	}

	private void showModal(final Pane pane) {
		tView.tShowModal(pane, true);
	}

	private void buildModelListener() {
		if(this.tTargetModel!=null) {
			ChangeListener<M> chl = (a,o,n)->{
				process(n);
			};
			this.repo.add("chl", chl);
			this.tTargetModel.addListener(new WeakChangeListener<>(chl));
		}
	}

	private void process(M n) {
		this.analyseRadio.setSelected(false);
		this.changeRadio.setSelected(false);
		this.createRadio.setSelected(false);
		this.tActions.getTogleeGroup().getBox().getChildren().clear();
		this.tActions.getTogleeGroup().getToggles().clear();
		if(n!=null) {
			this.tActions.addRadioButton(this.analyseRadio);
			this.tActions.addRadioButton(changeRadio);
		}else if(this.tModels!=null) {
			this.tActions.addRadioButton(this.createRadio);
			this.createRadio.setSelected(true);
		}
		
	}

	/**
	 * @return the tSendButton
	 */
	public TButton gettSendButton() {
		return tSendButton;
	}

	/**
	 * @return the tClearButton
	 */
	public TButton gettClearButton() {
		return tClearButton;
	}

	/**
	 * @return the tPrompt
	 */
	public TTextAreaField gettPrompt() {
		return tPrompt;
	}

	/**
	 * @param tTargetModel the tTargetModel to set
	 */
	public void settTargetModel(SimpleObjectProperty<M> tTargetModel) {
		this.tTargetModel = tTargetModel;
		this.buildModelListener();
	}

	/**
	 * @param tModels the tModels to set
	 */
	public void settModels(ObservableList<M> tModels) {
		this.tModels = tModels;
		this.process(null);
	}
	
	public void tInvalidate() {
		this.repo.clear();
		this.tTargetModel = null;
		this.tOutModel = null;
		this.tModels = null;
		this.tView = null;
		this.tModelViewType = null;
	}

	/**
	 * @param tView the tView to set
	 */
	public void settView(ITView tView) {
		this.tView = tView;
	}

	/**
	 * @return the tOutModel
	 */
	public M gettOutModel() {
		return tOutModel.getValue();
	}
	
	/**
	 * @return the tOutModel property
	 */
	public ReadOnlyObjectProperty<M> tOutModelProperty() {
		return tOutModel;
	}

}
