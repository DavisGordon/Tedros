/**
 * 
 */
package org.tedros.fx.control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.core.repository.TRepository;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.layout.TToolBar;
import org.tedros.fx.util.TBarcodeGenerator;
import org.tedros.fx.util.TBarcodeOrientation;
import org.tedros.fx.util.TBarcodeResolution;
import org.tedros.fx.util.TBarcodeType;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.fx.util.TPrintUtil;
import org.tedros.server.model.ITBarcode;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.model.TFileModel;
import org.tedros.util.TedrosFolder;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Davis Gordon
 *
 */
public class TBarcode extends TRequiredStackedComponent {
	
	private Class<? extends ITBarcode> tBarcodeModelType;
	private ObjectProperty<ITBarcode> tValueProperty;
	private BooleanProperty tAlreadyProperty;
	private TRepository repo;
	private BorderPane bp;
	
	private TText txtSize;
	private TIntegerField size;
	private TIntegerField columns;
	private THorizontalRadioGroup resolution;
	private THorizontalRadioGroup orientation;
	private THorizontalRadioGroup types;
	private TToolBar sizeBar;
	private TTextAreaField textarea;
	private TButton genBtn;
	private TButton clearBtn;
	private TButton printBtn;
	/**
	 * 
	 */
	public TBarcode(Class<? extends ITBarcode> tBarcodeModelType) {
		super();
		this.tBarcodeModelType = tBarcodeModelType;
		init();
		buildListeners();
	}
	private void init() {
		this.tAlreadyProperty = new SimpleBooleanProperty(false);
		this.tValueProperty = new SimpleObjectProperty<>();
		this.repo = new TRepository();
		
		TLanguage iEn = TLanguage.getInstance();

		TLabel sizeLbl= new TLabel(iEn.getString(TUsualKey.SIZE));
		txtSize = new TText();
		size = new TIntegerField();
		size.setValue(200);
		
		sizeBar = new TToolBar();
		sizeBar.getItems().addAll(sizeLbl, size, txtSize);
		
		TLabel colLbl= new TLabel(iEn.getString(TFxKey.COLUMNS));
		columns = new TIntegerField();
		columns.setValue(10);
		
		TToolBar complBar = new TToolBar();
		complBar.getItems().addAll(colLbl, columns);

		HBox hb = new HBox(5);
		hb.getChildren().addAll(complBar, sizeBar);

		TLabel resLbl= new TLabel(iEn.getString(TFxKey.RESOLUTION_DPI));
		resolution = new THorizontalRadioGroup();
		for(TBarcodeResolution t : TBarcodeResolution.values()){
			RadioButton rb = new RadioButton(String.valueOf(t.getValue()));
			rb.setUserData(t);
			resolution.addRadioButton(rb);
		}
		
		TLabel orLbl= new TLabel(iEn.getString(TFxKey.ORIENTATION_DEGREES));
		orientation = new THorizontalRadioGroup();
		for(TBarcodeOrientation t : TBarcodeOrientation.values()){
			RadioButton rb = new RadioButton(String.valueOf(t.getValue()));
			rb.setUserData(t);
			orientation.addRadioButton(rb);
		}
		
		types = new THorizontalRadioGroup();
		types.setRequired(true);
		for(TBarcodeType t : TBarcodeType.values()){
			RadioButton rb = new RadioButton(t.name());
			rb.setUserData(t);
			types.addRadioButton(rb);
		}

		textarea = new TTextAreaField();
		textarea.setPrefRowCount(2);
		textarea.setWrapText(true);
		//textarea.textProperty().bindBidirectional(tInputStreamProperty);
		
		genBtn = new TButton(iEn.getString(TFxKey.BUTTON_GENERATE));
		clearBtn = new TButton(iEn.getString(TFxKey.BUTTON_CLEAN));
		printBtn = new TButton(iEn.getString(TFxKey.BUTTON_PRINT));
		
		TToolBar btnBar = new TToolBar();
		btnBar.getItems().addAll(genBtn, clearBtn, printBtn);
		
		VBox vb = new VBox(5);
		vb.getChildren().addAll(types, resLbl, resolution, orLbl,  orientation, hb, textarea, btnBar);
		
		bp = new BorderPane();
		super.getChildren().add(bp);
		StackPane.setMargin(bp, new Insets(10));
		bp.setLeft(vb);
		
		columns.setDisable(true);
		sizeBar.setDisable(true);
		orientation.setDisable(true);
		resolution.setDisable(true);
		
	}

	private void buildListeners() {

		ChangeListener<ITBarcode> barChl = (a,o,n)->{
			this.readModel();
		};
		repo.add("barChl", barChl);
		this.tValueProperty.addListener(new WeakChangeListener<>(barChl));
		
		ChangeListener<String> conChl = (a,o,n)->{
			ITBarcode model = getModel();
			model.setContent(n);
		};
		repo.add("conChl", conChl);
		textarea.textProperty().addListener(new WeakChangeListener<>(conChl));
		
		ChangeListener<Number> colChl = (a,o,n)->{
			ITBarcode model = getModel();
			model.setColumns((Integer) n);
		};
		repo.add("colChl", colChl);
		columns.valueProperty().addListener(new WeakChangeListener<>(colChl));
		
		ChangeListener<Number> sizeChl = (a,o,n)->{
			txtSize.setText(n+"x"+n);
			ITBarcode model = getModel();
			model.setSize((Integer) n);
		};
		repo.add("sizeChl", sizeChl);
		size.valueProperty().addListener(new WeakChangeListener<>(sizeChl));
		
		ChangeListener<Toggle> resChl = (a,o,n)->{
			TBarcodeResolution e = (TBarcodeResolution) n.getUserData();
			ITBarcode model = getModel();
			model.setResolution(e.getValue());
		};
		repo.add("resChl", resChl);
		this.resolution.selectedToggleProperty().addListener(new WeakChangeListener<>(resChl));

		ChangeListener<Toggle> oriChl = (a,o,n)->{
			TBarcodeOrientation e = (TBarcodeOrientation) n.getUserData();
			ITBarcode model = getModel();
			model.setOrientation(e.getValue());
		};
		repo.add("oriChl", oriChl);
		this.orientation.selectedToggleProperty().addListener(new WeakChangeListener<>(oriChl));

		ChangeListener<Toggle> typeChl = (a,o,n)->{

			TBarcodeType tp = (TBarcodeType) n.getUserData();
			ITBarcode model = getModel();
			model.setType(tp.name());
			switch(tp) {
			case CODE128:
			case EAN13:
			case UPCA:
				columns.setDisable(true);
				sizeBar.setDisable(true);
				orientation.setDisable(false);
				resolution.setDisable(false);
				model.setColumns(null);
				model.setSize(null);
				model.setResolution(resolution.getSelectedToggle()!=null
						? ((TBarcodeResolution)resolution.getSelectedToggle().getUserData()).getValue()
								: null);
				model.setOrientation(orientation.getSelectedToggle()!=null
						? ((TBarcodeOrientation)orientation.getSelectedToggle().getUserData()).getValue()
								: null);
				break;
			case PDF417:
				columns.setDisable(false);
				sizeBar.setDisable(true);
				orientation.setDisable(false);
				resolution.setDisable(false);
				model.setColumns(columns.getValue());
				model.setSize(null);
				model.setResolution(resolution.getSelectedToggle()!=null
						? ((TBarcodeResolution)resolution.getSelectedToggle().getUserData()).getValue()
								: null);
				model.setOrientation(orientation.getSelectedToggle()!=null
						? ((TBarcodeOrientation)orientation.getSelectedToggle().getUserData()).getValue()
								: null);
				break;
			case QRCODE:
				columns.setDisable(true);
				sizeBar.setDisable(false);
				orientation.setDisable(true);
				resolution.setDisable(true);
				model.setColumns(null);
				model.setSize(size.getValue());
				model.setResolution(null);
				model.setOrientation(null);
				break;
			default:
				columns.setDisable(true);
				sizeBar.setDisable(true);
				orientation.setDisable(true);
				resolution.setDisable(true);
				model.setColumns(null);
				model.setSize(null);
				model.setResolution(null);
				model.setOrientation(null);
				break;
			}
		};
		repo.add("typeChl", typeChl);
		types.selectedToggleProperty().addListener(new WeakChangeListener<>(typeChl));
		
		EventHandler<ActionEvent> genEvh = ev ->{
			this.tAlreadyProperty.setValue(false);
			ITBarcode model = getModel();
			TBarcodeType t = TBarcodeType.valueOf(TBarcodeType.class, model.getType());
			String txt = model.getContent();
			Integer cols = model.getColumns();
			Integer sz = model.getSize();
			TBarcodeResolution res = model.getResolution()!=null 
					? TBarcodeResolution.valueOf(model.getResolution())
							: TBarcodeResolution._100;
			TBarcodeOrientation ori =  model.getOrientation()!=null 
					? TBarcodeOrientation.valueOf(model.getOrientation())
							: TBarcodeOrientation._0;
			BufferedImage bf = null;
			try {
				switch(t) {
				case CODE128:
					bf = TBarcodeGenerator.generateCode128BarcodeImage(txt, res, ori);
					break;
				case EAN13:
					bf = TBarcodeGenerator.generateEAN13BarcodeImage(txt, res, ori);
					break;
				case PDF417:
					bf = TBarcodeGenerator.generatePDF417BarcodeImage(txt, cols, res, ori);
					break;
				case QRCODE:
					try {
						bf = TBarcodeGenerator.generateQRCodeImage(txt, sz, sz);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case UPCA:
					bf = TBarcodeGenerator.generateUPCABarcodeImage(txt, res, ori);
					break;
				}
				if(bf!=null) 
					readBufferedImage(bf);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				TLabel l = new TLabel(e.getMessage());
				PopOver warn = new PopOver(l);
				warn.setArrowLocation(ArrowLocation.TOP_CENTER);
				warn.setAutoHide(true);
				warn.show(textarea);
				this.generateDefaultImage();
			}
		};
		repo.add("genEvh", genEvh);
		genBtn.setOnAction(new WeakEventHandler<>(genEvh));
		genBtn.disableProperty().bind(
		BooleanBinding.booleanExpression(textarea.textProperty().isEmpty())
		.or(types.requirementAccomplishedProperty().not()));
		
		EventHandler<ActionEvent> clearEvh = ev ->{
			textarea.clear();
			this.generateDefaultImage();
		};
		repo.add("clearEvh", clearEvh);
		clearBtn.setOnAction(new WeakEventHandler<>(clearEvh));
		clearBtn.disableProperty().bind(textarea.textProperty().isEmpty());
		

		EventHandler<ActionEvent> printEvh = ev ->{
			TPrintUtil.print(bp.getCenter());
		};
		repo.add("printEvh", printEvh);
		printBtn.setOnAction(new WeakEventHandler<>(printEvh));
		printBtn.disableProperty().bind(this.tAlreadyProperty.not());
		
		
	}
	/*private void setModel(ITBarcode model) {
		this.tValueProperty.setValue(null);
		this.tValueProperty.setValue(model);
	}*/
	private ITBarcode getModel() {
		ITBarcode model = this.tValueProperty.getValue();
		if(model==null) {
			try {
				model = this.tBarcodeModelType.newInstance();
				this.tValueProperty.setValue(model);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(model.getImage()==null) {
			ITFileBaseModel fm = model.createFileModel();
			model.setImage(fm);
		}
		return model;
	}

	private ImageView buildImageView(InputStream n) {
		if(n!=null) {
			Image img = new Image(n);
			try {
				n.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageView iv = new ImageView();
			iv.setImage(img);
			bp.setCenter(iv);
			BorderPane.setAlignment(iv, Pos.CENTER);
			return iv;
		}
		return null;
	}
	private void readBufferedImage(BufferedImage bf)  {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bf, "png", os);
			ITBarcode model = getModel();
			model.getImage().getByte().setBytes(os.toByteArray());
			model.getImage().setFileExtension("png");
			String name = model.getContent().length()>10 ? model.getContent().substring(0, 9) : model.getContent();
			model.getImage().setFileName(name+".png");
			os.close();
			generateBarcodeImage();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void generateBarcodeImage() {
		ITBarcode model = getModel();
		if(model.getImage()!=null && model.getImage().getByte()!=null 
				&& model.getImage().getByte().getBytes()!=null) {
			InputStream is = new ByteArrayInputStream(model.getImage().getByte().getBytes());
			ImageView iv = buildImageView(is);
			iv.setCursor(Cursor.HAND);
			iv.setOnMouseClicked(ev->{
				if(ev.getClickCount()==2) {
					try {
						String path = TedrosFolder.EXPORT_FOLDER.getFullPath()+"barcode.png";
						File file = new File(path);
						FileUtils.writeByteArrayToFile(file, model.getImage().getByte().getBytes());
						TedrosContext.openDocument(path);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			this.tAlreadyProperty.setValue(true);
			
		}else
			generateDefaultImage();
	}
	
	private void generateDefaultImage() {
		File f = new File(TedrosFolder.IMAGES_FOLDER.getFullPath()+"default-image.jpg");
		try {
			InputStream is = FileUtils.openInputStream(f);
			buildImageView(is);
			this.tAlreadyProperty.setValue(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readModel() {
		ITBarcode model = getModel();
		String txt = model.getContent();
		Integer cols = model.getColumns();
		Integer sz = model.getSize();
		TBarcodeType type = model.getType()!=null
				? TBarcodeType.valueOf(TBarcodeType.class, model.getType())
						: TBarcodeType.CODE128;
		TBarcodeResolution res = model.getResolution()!=null 
				? TBarcodeResolution.valueOf(model.getResolution())
						: TBarcodeResolution._100;
		TBarcodeOrientation ori = model.getOrientation()!=null 
				? TBarcodeOrientation.valueOf(model.getOrientation())
						: TBarcodeOrientation._0;
		textarea.setText(txt);
		columns.setValue(cols==null?10:cols);
		size.setValue(sz==null?200:sz);
		orientation.getTogleeGroup().getToggles().forEach(g->{
			TBarcodeOrientation e = (TBarcodeOrientation) g.getUserData();
			if(e.equals(ori))
				g.setSelected(true);
		});
		resolution.getTogleeGroup().getToggles().forEach(g->{
			TBarcodeResolution e = (TBarcodeResolution) g.getUserData();
			if(e.equals(res))
				g.setSelected(true);
		});
		types.getTogleeGroup().getToggles().forEach(g->{
			TBarcodeType e = (TBarcodeType) g.getUserData();
			if(e.equals(type))
				g.setSelected(true);
		});
		this.generateBarcodeImage();
	}
	/**
	 * @return the tValueProperty
	 */
	@SuppressWarnings("unchecked")
	public ObjectProperty<ITBarcode> tValueProperty() {
		return tValueProperty;
	}
	
	@Override
	public void settFieldStyle(String style) {
		this.bp.setStyle(style);
		
	}
}
