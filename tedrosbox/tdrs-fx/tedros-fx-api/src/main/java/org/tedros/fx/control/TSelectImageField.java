package org.tedros.fx.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TFileEntityController;
import org.tedros.core.repository.TRepository;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.control.action.TEventHandler;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.domain.TImageExtension;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.process.TCustomProcess;
import org.tedros.fx.property.TBytesLoader;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.TFileModel;
import org.tedros.server.result.TResult;
import org.tedros.util.TedrosFolder;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class TSelectImageField extends TRequiredSelectImage{

	
	private static final String SOURCE = "source";
	private static final String TARGET = "target";
	private static final String DEFIMAGE = "defimage";
	@SuppressWarnings("rawtypes")
	private ObservableList<TEventHandler> tEventHandlerList;
	private final ObservableList<ITFileBaseModel> tFileList;
	private ObservableList<ITFileBaseModel> tSelectedFileList;
	private SimpleObjectProperty<ITFileBaseModel> tModelProperty;
	private SimpleDoubleProperty tFitWidthProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty tFitHeightProperty = new SimpleDoubleProperty();
	private SimpleDoubleProperty tMaxFileSizeProperty = new SimpleDoubleProperty();
	private SimpleBooleanProperty tPreLoadBytes = new SimpleBooleanProperty();
	private SimpleObjectProperty<TImageExtension> tImageExtensionProperty = new SimpleObjectProperty<>();
	
	private TDirectoryField directoryField;
	
	private TEnvironment tFileSource;
	private TEnvironment tFileTarget;
	private String[] remoteFileOwner;
	
	private TText text;
	private ImageView defImgView;
	private FlowPane sourcePane;
	private FlowPane targetPane;
	private BorderPane mainPane;
	private ScrollPane scrollPane;
	private HBox hbox;
	private ToolBar envToolbar;
	private ToolBar actToolbar;
	private TButton backBtn;
	private TButton viewBtn;
	private TButton remBtn;
	private TButton loadBtn;
	
	private TRepository repo = new TRepository();
	private TLanguage iEngine;
	
	public TSelectImageField(SimpleObjectProperty<ITFileBaseModel> property, TEnvironment source, TEnvironment target, 
			TImageExtension imgExtension, Boolean preLoadFileBytes, String...remoteFileOwner) {
		this.tModelProperty = property;
		this.tSelectedFileList = FXCollections.observableArrayList();
		this.tFileSource = source != null ? source : TEnvironment.LOCAL;
		this.tFileTarget = target != null ? target : TEnvironment.LOCAL;
		this.tImageExtensionProperty.setValue(imgExtension);
		this.remoteFileOwner = remoteFileOwner==null 
				|| (remoteFileOwner.length==1 && remoteFileOwner[0].equals("")) 
				? null 
						: remoteFileOwner;
		this.tFileList = FXCollections.observableArrayList();
		this.tEventHandlerList =  FXCollections.observableArrayList();
		initialize();
		buildListener();
		callBuild();
		this.tPreLoadBytes.setValue(preLoadFileBytes);
		super.tRequiredNodeProperty().setValue(gettComponent());
	}
	
	public TSelectImageField(ObservableList<ITFileBaseModel> property, TEnvironment source, TEnvironment target, 
			TImageExtension imgExtension, Boolean preLoadFileBytes, String...remoteFileOwner) {
		this.tModelProperty = null;
		this.tSelectedFileList = property;
		this.tFileSource = source != null ? source : TEnvironment.LOCAL;
		this.tFileTarget = target != null ? target : TEnvironment.LOCAL;
		this.tImageExtensionProperty.setValue(imgExtension);
		this.remoteFileOwner = remoteFileOwner==null 
				|| (remoteFileOwner.length==1 && remoteFileOwner[0].equals("")) 
				? null 
						: remoteFileOwner;
		this.tFileList = FXCollections.observableArrayList();
		this.tEventHandlerList =  FXCollections.observableArrayList();
		initialize();
		buildListener();
		callBuild();
		this.tPreLoadBytes.setValue(preLoadFileBytes);
		super.tRequiredNodeProperty().setValue(gettComponent());
	}
	
	private void callBuild() {
		if(tFileSource.equals(TEnvironment.LOCAL))
			if(!tSelectedFileList.isEmpty()) 
				buildTarget();
			else
				buildSource();
		else
			callRemoteService();
	}
	
	/**
	 * Initialize propertys
	 * */
	private void initialize(){
		iEngine = TLanguage.getInstance(null);
		
		envToolbar = new ToolBar();
		envToolbar.setId("t-view-toolbar");
		actToolbar = new ToolBar();
		actToolbar.setId("t-view-toolbar");
		
		text = new TText();
		text.settTextStyle(TTextStyle.LARGE);
		
		hbox = new HBox();
		hbox.setId("t-group-header-box");
		
		viewBtn = new TButton();
		viewBtn.setText(iEngine.getString("#{tedros.fxapi.button.view.selected}"));
		
		backBtn = new TButton();
		backBtn.setText(iEngine.getString("#{tedros.fxapi.button.back}"));
		
		remBtn = new TButton();
		remBtn.setId("t-last-button");
		remBtn.setText(iEngine.getString("#{tedros.fxapi.button.removeAll}"));
		
		actToolbar.getItems().addAll(viewBtn, backBtn, remBtn);
		
		scrollPane = new ScrollPane();
		scrollPane.setId("t-form-scroll");
		scrollPane.setFitToWidth(true);
		
		
		scrollPane.maxWidth(Double.MAX_VALUE);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setStyle("-fx-background-color: transparent;");
	
		mainPane = new BorderPane();
		
		if(tFileSource.equals(TEnvironment.LOCAL)) {
			directoryField = new TDirectoryField(TedrosContext.getStage());
			directoryField.settUserHomeAsInitialDirectory();
			ChangeListener<File> dfChl = (a,b,n)->{
				if(!tFileList.isEmpty())
					tFileList.clear();
				if(n!=null) {
					List<ITFileBaseModel> addLst = new ArrayList<>();
					for(File f : n.listFiles()) {
						if(f.isFile()) {
							String ext = FilenameUtils.getExtension(f.getName());
							String[] exts = this.tImageExtensionProperty.getValue().getExtension();
							if(ArrayUtils.contains(exts, "*."+ext.toLowerCase())) {
								try {
									TFileModel fm = new TFileModel(f);
									addLst.add(fm);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					tFileList.addAll(addLst);
				}
			};
			repo.add("dfChl", dfChl);
			directoryField.tFileProperty().addListener(new WeakChangeListener<>(dfChl));
			envToolbar.getItems().add(directoryField);
		}else{
			loadBtn = new TButton();
			loadBtn.setId("t-last-button");
			loadBtn.setText(iEngine.getString("#{tedros.fxapi.button.load}"));
			envToolbar.getItems().add(loadBtn);
		}
		
		Region space0 = new Region();
		Region space1 = new Region();
		HBox.setHgrow(space0, Priority.ALWAYS);
		HBox.setHgrow(space1, Priority.ALWAYS);
		HBox.setHgrow(text, Priority.ALWAYS);
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(envToolbar, space0, text, space1, actToolbar);
		mainPane.setTop(hbox);
		mainPane.setCenter(scrollPane);
		BorderPane.setMargin(scrollPane, new Insets(10,0,0,10));
		super.getChildren().add(mainPane);
		
	}

	/**
	 * 
	 */
	public void settHeight(double height) {
		scrollPane.setMaxHeight(height);
	}
	
	private String getScrollContentType() {
		return this.getScrollContentType(scrollPane.getContent());
	}
	
	private String getScrollContentType(Node n) {
		if(n!=null && n instanceof ImageView) 
			return DEFIMAGE;
		else if(n!=null && n instanceof FlowPane)
			return n.getId();
		else return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildListener() {
		ChangeListener<Node> scrlChl = (a,b,n)->{
			if(n!=null) {
				String c = this.getScrollContentType(n);
				if(c.equals(DEFIMAGE)) {
					this.text.setText(iEngine.getString("#{tedros.fxapi.label.select.file}"));
					this.remBtn.setDisable(true);
					this.backBtn.setDisable(true);
					viewBtn.setDisable(tSelectedFileList.isEmpty());
					if(tFileList.isEmpty())
						sourcePane = null;
					if(tSelectedFileList.isEmpty()) {
						targetPane = null;
					}
				}else if(c.equals(SOURCE)) {
					this.text.setText(iEngine.getString("#{tedros.fxapi.label.select.file}"));
					this.remBtn.setDisable(true);
					this.backBtn.setDisable(true);
					viewBtn.setDisable(tSelectedFileList.isEmpty());
					if(tSelectedFileList.isEmpty()) {
						targetPane = null;
					}
				}else if(c.equals(TARGET)) {
					this.text.setText(iEngine.getString("#{tedros.fxapi.label.selected}"));
					this.remBtn.setDisable(false);
					this.backBtn.setDisable(false);
					viewBtn.setDisable(true);
				}
			}
		};
		repo.add("scrlChl", scrlChl);
		scrollPane.contentProperty().addListener(new WeakChangeListener<>(scrlChl));
		
		// buttos acttion
		remBtn.setOnAction(e->{
			this.tSelectedFileList.clear();
		});
		viewBtn.setOnAction(e->{
			if(targetPane!=null)
				this.scrollPane.setContent(targetPane);
			else
				buildTarget();
		});
		backBtn.setOnAction(e->{
			if(sourcePane!=null) {
				this.scrollPane.setContent(sourcePane);
			}else
				buildSource();
		});
		if(loadBtn!=null)
			loadBtn.setOnAction(e->{
				this.callRemoteService();
			});
		// selected list
		ListChangeListener<ITFileBaseModel> selChl = c -> {
			if(tModelProperty!=null) {
				tModelProperty.removeListener((ChangeListener<ITFileBaseModel>)repo.get("modChl"));
				ObservableList<? extends ITFileBaseModel> l = c.getList();
				tModelProperty.setValue(l.isEmpty()? null : l.get(0));
				tModelProperty.addListener((ChangeListener<ITFileBaseModel>)repo.get("modChl"));
			}
			if(!c.getList().isEmpty())
				buildTarget();
			else {
				buildSource();
			}
		};
		repo.add("selChl", selChl);
		this.tSelectedFileList.addListener(new WeakListChangeListener<>(selChl));
		
		// source image view event handler
		ListChangeListener<TEventHandler> chl0 = c -> {
			while(c.next()) {
				if(c.wasAdded()) 
					for(TEventHandler ev : c.getAddedSubList()) 
						for(Node i : sourcePane.getChildren()) 
							if(((TFieldBox) i).gettControl().getUserData()!=null) {
								ImageView iv = (ImageView) ((TFieldBox) i).gettControl();
								iv.addEventHandler(ev.getEventType(), ev);
							}
				if(c.wasRemoved()) 
					for(TEventHandler ev : c.getRemoved()) 
						for(Node i : sourcePane.getChildren()) 
							if(((TFieldBox) i).gettControl().getUserData()!=null) {
								ImageView iv = (ImageView) ((TFieldBox) i).gettControl();
								iv.removeEventHandler(ev.getEventType(), ev);
							}
			}
		};
		this.repo.add("chl0", chl0);
		this.tEventHandlerList.addListener(new WeakListChangeListener<>(chl0));
		// source list
		ListChangeListener<ITFileBaseModel> chl = c -> {
			buildSource();
		};
		this.repo.add("chl", chl);
		this.tFileList.addListener(new WeakListChangeListener<>(chl));
		
		// max file size
		ChangeListener<Double> chl1 = (a,b,n)->{
			if(getScrollContentType().equals(TARGET))
				buildTarget();
			else
				buildSource();
		};
		this.repo.add("chl1", chl1);
		this.tMaxFileSizeProperty.addListener(new WeakChangeListener(chl1));
		
		// model property only one file can be selected
		if(tModelProperty!=null) {
			ChangeListener<ITFileBaseModel> modChl = (a,b,n)->{
				if(n!=null) {
					addTarget(n);
				}else 
					tSelectedFileList.clear();
			};
			repo.add("modChl", modChl);
			tModelProperty.addListener(modChl);
			
			if(tModelProperty.getValue()!=null && tSelectedFileList.isEmpty()) {
				addTarget(tModelProperty.getValue());
			}
		}
		
	}

	private void buildSource(){
		if(!tFileList.isEmpty()) {
			buildSourcePane();
			scrollPane.setContent(sourcePane);
			for (ITFileBaseModel m : tFileList)
				buildImageViewIcon(m);
		}else 
			showDefImageView();
			
	}

	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildSourcePane() {
		if(sourcePane!=null && !sourcePane.getChildren().isEmpty()) {
			sourcePane.getChildren().forEach(n->{
				TFieldBox fb = (TFieldBox) n;
				ImageView iv = (ImageView) fb.gettControl();
				iv.fitHeightProperty().unbindBidirectional(tFitHeightProperty);
				iv.fitWidthProperty().unbindBidirectional(tFitWidthProperty);
				if(!this.tEventHandlerList.isEmpty())
					for(TEventHandler ev : tEventHandlerList)
						iv.removeEventHandler(ev.getEventType(), ev);
			});
		}
		
		sourcePane =  new FlowPane();
		sourcePane.setHgap(15);
		sourcePane.setVgap(15);
		sourcePane.setAlignment(Pos.CENTER);
		sourcePane.setId(SOURCE);
	}
	
	private void buildTarget() {
		if(!tSelectedFileList.isEmpty()) {
			buildTargetPane();
			this.scrollPane.setContent(targetPane);
			for(ITFileBaseModel m : tSelectedFileList) {
				try {
					ToolBar tb = new ToolBar();
					tb.setId("t-view-toolbar");
					VBox vbox = new VBox();
					
					ImageView iv = new ImageView();
					iv.setUserData(m);
					setting(iv);
					settingImage(m, iv);
					vbox.getChildren().add(iv);
					if(m instanceof ITFileEntity) {
						TTextField tif = new TTextField();
						tif.setPromptText(iEngine.getString("#{tedros.fxapi.label.description}"));
						tif.setText(((ITFileEntity)m).getDescription());
						tif.textProperty().addListener((a,b,n)->{
							((ITFileEntity)m).setDescription(n);
						});
						vbox.getChildren().add(tif);
						VBox.setMargin(tif, new Insets(5,0,5,0));
						if(!((ITFileEntity) m).isNew()) {
							TButton dBtn = new TButton();
							dBtn.setText(iEngine.getString("#{tedros.fxapi.button.download}"));
							dBtn.setOnAction(e->{
								TButton b = (TButton) e.getSource();
								DirectoryChooser dc = new DirectoryChooser();
								dc.setTitle(iEngine.getString("#{tedros.fxapi.button.select}"));
								dc.setInitialDirectory(new File(System.getProperty("user.home")));
								final File file = dc.showDialog(TedrosContext.getStage());
					            if (file != null) {
					            	ITFileBaseModel x = (ITFileBaseModel) b.getUserData();
									File df = new File(file.getPath()+File.separator+x.getFileName());
									try {
										FileUtils.copyInputStreamToFile(new ByteArrayInputStream(x.getByte().getBytes()), df);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
					            }
					            	
							});
							dBtn.setUserData(m);
							tb.getItems().add(dBtn);
						}
						TButton rBtn = new TButton();
						rBtn.setId("t-last-button");
						rBtn.setText(iEngine.getString("#{tedros.fxapi.button.remove}"));
						rBtn.setOnAction(e->{
							TButton b = (TButton) e.getSource();
							ITFileBaseModel x = (ITFileBaseModel) b.getUserData();
							b.setUserData(null);
							tSelectedFileList.remove(x);
						});
						rBtn.setUserData(m);
						tb.getItems().add(rBtn);
						vbox.getChildren().add(tb);
					}else {
						TButton rBtn = new TButton();
						rBtn.setId("t-last-button");
						rBtn.setText(iEngine.getString("#{tedros.fxapi.button.remove}"));
						rBtn.setOnAction(e->{
							tSelectedFileList.clear();
						});
						tb.getItems().addAll(rBtn);
						vbox.getChildren().add(tb);
						VBox.setMargin(tb, new Insets(5,0,0,0));
					}
					
					this.targetPane.getChildren().add(vbox);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			if(targetPane!=null)
				this.scrollPane.setContent(targetPane);
			else
				showDefImageView();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildTargetPane() {
		if(targetPane!=null && !targetPane.getChildren().isEmpty()) {
			targetPane.getChildren().forEach(n->{
				VBox h = (VBox) n;
				ImageView iv = (ImageView) h.getChildren().get(0);
				iv.fitHeightProperty().unbindBidirectional(tFitHeightProperty);
				iv.fitWidthProperty().unbindBidirectional(tFitWidthProperty);
				if(!this.tEventHandlerList.isEmpty())
					for(TEventHandler ev : tEventHandlerList)
						iv.removeEventHandler(ev.getEventType(), ev);
			});
		}
		targetPane =  new FlowPane();
		targetPane.setHgap(15);
		targetPane.setVgap(15);
		targetPane.setAlignment(Pos.CENTER);
		targetPane.setId(TARGET);
	}
	
	private void addTarget(ITFileBaseModel... base) {
		List<ITFileBaseModel> l = new ArrayList<>();
		if(tFileTarget.equals(TEnvironment.LOCAL)) {
			for(ITFileBaseModel value : base) {
				if(value instanceof ITFileEntity) {
					TFileModel m = TFileBaseUtil.convert((ITFileEntity)value);
					l.add(m);
				}else 
					l.add(value);
			}
		}else {
			for(ITFileBaseModel value : base) {
				if(value instanceof TFileModel) {
					ITFileEntity m = TFileBaseUtil.convert((TFileModel)value);
					if(this.remoteFileOwner!=null && this.remoteFileOwner.length>0)
						m.setOwner(this.remoteFileOwner[0]);
					l.add(m);
				}else {
					if(((TFileEntity) value).getOwner()==null && this.remoteFileOwner!=null && this.remoteFileOwner.length>0)
						((TFileEntity) value).setOwner(this.remoteFileOwner[0]);
					l.add(value);
				}
			}
		}
		if(tModelProperty==null) 
			tSelectedFileList.addAll(l);
		else {
			if(!tSelectedFileList.isEmpty())
				this.tSelectedFileList.set(0, l.get(0));
			else
				this.tSelectedFileList.add(l.get(0));
		}
			
	}
	
	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void showDefImageView() {
		if(defImgView==null) {
			try {
				Image img = buildDefImg();
				defImgView = new ImageView(img);
				defImgView.setUserData(null);
				setting(defImgView);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		scrollPane.setContent(defImgView);
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private Image buildDefImg() throws FileNotFoundException, IOException {
		String path = TedrosFolder.IMAGES_FOLDER.getFullPath()+"default-image.jpg";
		File f =  new File(path);
		InputStream is = new FileInputStream(f);
		Image img = new Image(is);
		is.close();
		return img;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildImageViewIcon(ITFileBaseModel model) {
		try {
			Double max = tMaxFileSizeProperty.getValue();
			if(max!=null && max>0 && model.getFileSize()!=null
					&& model.getFileSize()>max) {
				return;
			}
			
			final ImageView imgView = new ImageView();
			setting(imgView);
			settingImage(model, imgView);
			imgView.setCursor(Cursor.HAND);
			imgView.setUserData(model);
			imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if(e.getClickCount()==2) {
					ImageView x = (ImageView) e.getSource();
					addTarget((ITFileBaseModel) x.getUserData());
				}
			});
			for(TEventHandler ev : tEventHandlerList)
				imgView.addEventHandler(ev.getEventType(), ev);
			
			showImageView(imgView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param imgView
	 */
	private void showImageView(final ImageView imgView) {
		TLabel l = new TLabel("");
		if(imgView.getUserData()!=null) {
			ITFileBaseModel m = (ITFileBaseModel) imgView.getUserData();
			String s = (m instanceof ITFileEntity && ((ITFileEntity)m).getDescription()!=null) 
					? ((ITFileEntity)m).getDescription()
							: m.getFileName();
			if(m.getFileSize()!=null)
				s += " ("+m.getFileSize().toString()+" bytes)";
			l.setText(s);
		}
		TFieldBox fb = new TFieldBox(null, l, imgView, TLabelPosition.BOTTOM);
		sourcePane.getChildren().add(fb);
	}

	/**
	 * @param imgView
	 * @param fe
	 */
	private void loadImage(final ImageView imgView, ITFileEntity fe) {
		SimpleObjectProperty<byte[]> bp = new SimpleObjectProperty<>();
		bp.addListener(new ChangeListener<byte[]>() {
			@Override
			public void changed(ObservableValue<? extends byte[]> arg0, byte[] arg1,
					byte[] n) {
				try {
					fe.getByteEntity().setBytes(n);
					setImage(imgView, n);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bp.removeListener(this);
			}
		});
		loadBytes(bp, fe);
	}

	/**
	 * @param imgView
	 * @param bytes
	 * @throws IOException
	 */
	private void setImage(final ImageView imgView, byte[] bytes) throws IOException {
		InputStream is = new ByteArrayInputStream(bytes);
		Image img = new Image(is);
		is.close();
		imgView.setImage(img);
	}

	
	/**
	 * @param model
	 * @param imgView
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void settingImage(ITFileBaseModel model, final ImageView imgView)
			throws FileNotFoundException, IOException {
		if(model instanceof ITFileEntity) {
			ITFileEntity fe = (ITFileEntity) model;
			byte[] bytes = fe.getByte().getBytes();
			if(bytes==null) { 
				if(tPreLoadBytes.getValue()) {
					loadImage(imgView, fe);
				}else {
					imgView.setImage(buildDefImg());
					final EventHandler<MouseEvent> evh = new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							ImageView x = (ImageView) e.getSource();
							x.removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
							ITFileEntity m = (ITFileEntity) x.getUserData();
							loadImage(x, m);
						}
					};
					imgView.addEventHandler(MouseEvent.MOUSE_ENTERED, evh);
				}
			}else {
				setImage(imgView, bytes);
			}
		}else{
			setImage(imgView, model.getByte().getBytes());
		}
	}

	/**
	 * @param imgView
	 */
	private void setting(final ImageView imgView) {
		imgView.setPreserveRatio(true);
		imgView.autosize();
		imgView.fitHeightProperty().bindBidirectional(tFitHeightProperty);
		imgView.fitWidthProperty().bindBidirectional(tFitWidthProperty);
	}

	/**
	 * 
	 */
	private void callRemoteService() {
		TRemoteFileProcess p = new TRemoteFileProcess();
		p.stateProperty().addListener((a,b,n)->{
			if(n.equals(State.SUCCEEDED)) {
				TResult<List<TFileEntity>> r = p.getValue();
				if(r.getValue()!=null && !r.getValue().isEmpty()) {
					this.tFileList.clear();
					this.tFileList.addAll(r.getValue());
				}
			}
		});
		List<String> owner = this.remoteFileOwner!=null && this.remoteFileOwner.length>0 
				? Arrays.asList(this.remoteFileOwner) 
						: null;
		List<String> exts = this.tImageExtensionProperty.getValue()!=null 
				? Arrays.asList(this.tImageExtensionProperty.getValue().getExtensionName())
						: null;
		Long maxSize = this.tMaxFileSizeProperty.getValue() != null && this.tMaxFileSizeProperty.getValue()>0
				? this.tMaxFileSizeProperty.getValue().longValue()
						: null;
		Boolean load = this.tPreLoadBytes.getValue()!=null
				? this.tPreLoadBytes.getValue()
						: false;
		
		Map<String, Object> in = new HashMap<>();
		in.put("o", owner);
		in.put("e", exts);
		in.put("s", maxSize);
		in.put("l", load);
		p.setObjectToProcess(in);
		p.startProcess();
	}

	public final ObservableList<ITFileBaseModel> gettItems() {
		return tFileList;
	}

	/**
	 * @return the tSelectedFileList
	 */
	public ObservableList<ITFileBaseModel> gettSelectedFileList() {
		return tSelectedFileList;
	}

	@Override
	public Node gettComponent() {
		return sourcePane;
	}
	
	public void settFitWidth(double w) {
		this.tFitWidthProperty.setValue(w);
	}
	
	public void settFitHeight(double h) {
		this.tFitHeightProperty.setValue(h);
	}
	
	public void settPreLoadBytes(boolean b) {
		this.tPreLoadBytes.setValue(b);
	}
	
	public void settMaxFileSize(double max) {
		this.tMaxFileSizeProperty.setValue(max);
	}
	
	public void settScroll(boolean scroll) {
		if(scroll) {
			this.scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			this.scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		}else {

			this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
			this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		}
	}
	
	public void settLocalFolder(String path) {
		if(this.directoryField!=null) {
			File f = new File(path);
			if(f.isDirectory()) {
				this.directoryField.settFile(f);
			}
		}
	}
	
	public <T extends Event> void addTEventHandler(TEventHandler<T> tEventHandler) {
		this.tEventHandlerList.add(tEventHandler);
	}
	
	public <T extends Event> void removeTEventHandler(TEventHandler<T> tEventHandler) {
		this.tEventHandlerList.remove(tEventHandler);
	}

	/**
	 * @param bp
	 * @param m
	 */
	private void loadBytes(SimpleObjectProperty<byte[]> bp, ITFileEntity m) {
		try {
			TBytesLoader.loadBytesFromTFileEntity(m.getByteEntity().getId(), bp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private class TRemoteFileProcess extends TCustomProcess<Map<String, Object>, TResult<List<TFileEntity>>>{
		
		private final static String SERV = "TFileEntityControllerRemote";
		
		@SuppressWarnings("unchecked")
		@Override
		public TResult<List<TFileEntity>> process(Map<String, Object> m) {
			if(m==null)
				return null;
			List<String> owner = (List<String>) m.get("o");
			List<String> exts = (List<String>) m.get("e");
			Long maxSize = (Long) m.get("s");
			Boolean load = (Boolean) m.get("l");
			ServiceLocator loc = ServiceLocator.getInstance();
    		try {
        		TUser user = TedrosContext.getLoggedUser(); 
        		TFileEntityController serv =  loc.lookup(SERV);
        		TResult<List<TFileEntity>> r = serv.find(user.getAccessToken(), owner, exts, maxSize, load);
        		return r;
    		}catch(Exception e) {
    			e.printStackTrace();
    			return null;
    		}
		}
	};

}
