/**
 * 
 */
package org.tedros.fx.control;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.tedros.core.TLanguage;
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
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.geometry.Pos;
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
public class TBarcode extends StackPane {

	private ObjectProperty<InputStream> tValueProperty;
	private TRepository repo;
	private BorderPane bp;
	
	/**
	 * 
	 */
	public TBarcode(StringProperty tValueProperty) {
		super();
		this.tValueProperty = new SimpleObjectProperty<>();
		this.repo = new TRepository();
		
		TLanguage iEn = TLanguage.getInstance();

		TLabel sizeLbl= new TLabel(iEn.getString(TUsualKey.SIZE));
		TText txtSize = new TText();
		ChangeListener<Number> sizeChl = (a,o,n)->{
			txtSize.setText(n+"x"+n);
		};
		repo.add("sizeChl", sizeChl);
		TIntegerField size = new TIntegerField();
		size.valueProperty().addListener(new WeakChangeListener<>(sizeChl));
		size.setValue(200);
		TToolBar sizeBar = new TToolBar();
		sizeBar.getItems().addAll(sizeLbl, size, txtSize);
		
		TLabel colLbl= new TLabel(iEn.getString(TFxKey.COLUMNS));
		TIntegerField columns = new TIntegerField();
		columns.setValue(10);

		TText txtRes = new TText();
		ChangeListener<Number> resChl = (a,o,n)->{
			txtRes.setText(n.toString());
		};
		repo.add("resChl", resChl);
		
		TToolBar complBar = new TToolBar();
		complBar.getItems().addAll(colLbl, columns);

		HBox hb = new HBox(5);
		hb.getChildren().addAll(complBar, sizeBar);

		TLabel resLbl= new TLabel(iEn.getString(TFxKey.RESOLUTION_DPI));
		THorizontalRadioGroup resolution = new THorizontalRadioGroup();
		for(TBarcodeResolution t : TBarcodeResolution.values()){
			RadioButton rb = new RadioButton(String.valueOf(t.getValue()));
			rb.setUserData(t);
			resolution.addRadioButton(rb);
		}
		
		TLabel orLbl= new TLabel(iEn.getString(TFxKey.ORIENTATION_DEGREES));
		THorizontalRadioGroup orientation = new THorizontalRadioGroup();
		for(TBarcodeOrientation t : TBarcodeOrientation.values()){
			RadioButton rb = new RadioButton(String.valueOf(t.getValue()));
			rb.setUserData(t);
			orientation.addRadioButton(rb);
		}
		
		THorizontalRadioGroup types = new THorizontalRadioGroup();
		types.setRequired(true);
		for(TBarcodeType t : TBarcodeType.values()){
			RadioButton rb = new RadioButton(t.name());
			rb.setUserData(t);
			types.addRadioButton(rb);
		}

		ChangeListener<Toggle> typeChl = (a,o,n)->{

			TBarcodeType tp = (TBarcodeType) n.getUserData();
			switch(tp) {
			case CODE128:
			case EAN13:
			case UPCA:
				columns.setDisable(true);
				sizeBar.setDisable(true);
				orientation.setDisable(false);
				resolution.setDisable(false);
				break;
			case PDF417:
				columns.setDisable(false);
				sizeBar.setDisable(true);
				orientation.setDisable(false);
				resolution.setDisable(false);
				break;
			case QRCODE:
				columns.setDisable(true);
				sizeBar.setDisable(false);
				orientation.setDisable(true);
				resolution.setDisable(true);
				break;
			default:
				columns.setDisable(true);
				sizeBar.setDisable(true);
				orientation.setDisable(true);
				resolution.setDisable(true);
				break;
			
			}
		};
		repo.add("typeChl", typeChl);
		types.selectedToggleProperty().addListener(new WeakChangeListener<>(typeChl));

		TTextAreaField textarea = new TTextAreaField();
		textarea.setPrefRowCount(2);
		textarea.setWrapText(true);
		textarea.textProperty().bindBidirectional(tValueProperty);
		
		TButton genBtn = new TButton(iEn.getString(TFxKey.BUTTON_GENERATE));
		TButton clearBtn = new TButton(iEn.getString(TFxKey.BUTTON_CLEAN));
		TButton printBtn = new TButton(iEn.getString(TFxKey.BUTTON_PRINT));
		
		EventHandler<ActionEvent> genEvh = ev ->{
			TBarcodeType t = (TBarcodeType) types.getSelectedToggle().getUserData();
			String txt = textarea.getText();
			int cols = columns.getValue();
			int sz = size.getValue();
			TBarcodeResolution res = (TBarcodeResolution) resolution.getSelectedToggle().getUserData();
			TBarcodeOrientation ori = (TBarcodeOrientation) orientation.getSelectedToggle().getUserData();
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
				if(bf!=null) {
					InputStream is = createInputStream(bf);
					this.tValueProperty.setValue(is);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				TLabel l = new TLabel(e.getMessage());
				PopOver warn = new PopOver(l);
				warn.setArrowLocation(ArrowLocation.TOP_CENTER);
				warn.setAutoHide(true);
				warn.show(textarea);
			}
		};
		repo.add("genEvh", genEvh);
		genBtn.setOnAction(new WeakEventHandler<>(genEvh));
		genBtn.disableProperty().bind(
		BooleanBinding.booleanExpression(textarea.textProperty().isEmpty())
		.and(types.requirementAccomplishedProperty().not()));
		
		EventHandler<ActionEvent> clearEvh = ev ->{
			textarea.clear();
			this.generateDefaultImage();
		};
		repo.add("clearEvh", clearEvh);
		clearBtn.setOnAction(new WeakEventHandler<>(clearEvh));
		clearBtn.disableProperty().bind(textarea.textProperty().isEmpty());
				
		
		TToolBar btnBar = new TToolBar();
		btnBar.getItems().addAll(genBtn, clearBtn, printBtn);
		
		VBox vb = new VBox(5);
		vb.getChildren().addAll(types, resLbl, resolution, orLbl,  orientation, hb, textarea, btnBar);
		
		bp = new BorderPane();
		super.getChildren().add(bp);
		bp.setLeft(vb);
		
		columns.setDisable(true);
		sizeBar.setDisable(true);
		orientation.setDisable(true);
		resolution.setDisable(true);
		//genBtn.setDisable(true);
		//clearBtn.setDisable(true);
		//printBtn.setDisable(true);
		

		ChangeListener<InputStream> isChl = (a,o,n)->{
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
			}
		};
		repo.add("isChl", isChl);
		this.tValueProperty.addListener(new WeakChangeListener<>(isChl));
		
		if(tValueProperty.getValue()==null) {
			Platform.runLater(()->{
				generateDefaultImage();
			});
		}
	}

	private InputStream createInputStream(BufferedImage bf)  {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bf, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void generateDefaultImage() {
		File f = new File(TedrosFolder.IMAGES_FOLDER.getFullPath()+"default-image.jpg");
		try {
			InputStream is = FileUtils.openInputStream(f);
			this.tValueProperty.setValue(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
