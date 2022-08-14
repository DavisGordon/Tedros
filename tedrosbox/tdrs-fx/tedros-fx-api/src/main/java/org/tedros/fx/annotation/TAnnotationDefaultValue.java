package org.tedros.fx.annotation;

import org.tedros.fx.form.TDefaultForm;
import org.tedros.fx.process.TProcess;
import org.tedros.server.model.ITModel;

public interface TAnnotationDefaultValue {

	public final static String TVIEW_newButtonText = "#{tedros.fxapi.button.new}";
	public final static String TVIEW_saveButtonText = "#{tedros.fxapi.button.save}";
	public final static String TVIEW_editButtonText = "#{tedros.fxapi.button.edit}";
	public final static String TVIEW_cancelButtonText = "#{tedros.fxapi.button.cancel}";
	public final static String TVIEW_deleteButtonText = "#{tedros.fxapi.button.delete}";
	public final static String TVIEW_searchButtonText = "#{tedros.fxapi.button.search}";
	public static final String TVIEW_cleanButtonText = "#{tedros.fxapi.button.clean}";
	public static final String TVIEW_selectButtonText = "#{tedros.fxapi.button.select}";
	public static final String TVIEW_selectAllButtonText = "#{tedros.fxapi.button.selectAll}";
	public static final String TVIEW_closeButtonText = "#{tedros.fxapi.button.close}";
	public final static String TVIEW_viewTitle = "#{tedros.fxapi.view.title}";
	public final static String TVIEW_listTitle = "#{tedros.fxapi.view.list.title}";
	public final static String TVIEW_masterTitle = "Primary data";
	public final static String TVIEW_detailTitle = "Secundary data";
	public final static String TVIEW_colapse = " <|> ";
	
	public final static Class<?> TFORM_modelClass = ITModel.class;
	public final static Class<?> TFORM_formClass = TDefaultForm.class;
	public final static Class<?> TFORM_processClass =TProcess.class;
	public final static String TFORM_formTitle = "";
	public final static String TCHART_title = "";
	
	public final static double DEFAULT_DOUBLE_VALUE_IDENTIFICATION = -99229922;
	public final static float DEFAULT_FLOAT_VALUE_IDENTIFICATION = -99229922;
	public final static int DEFAULT_INT_VALUE_IDENTIFICATION = -99229922;
	public static final String TVIEW_editModeTitle = "#{tedros.fxapi.radio-button.edit}";
	public static final String TVIEW_readerModeTitle = "#{tedros.fxapi.radio-button.read}";
	public static final String TVIEW_excelButtonText = "Excel";
	public static final String TVIEW_wordButtonText = "Word";
	public static final String TVIEW_pdfButtonText = "PDF";
	public static final String TVIEW_selected = "#{tedros.fxapi.label.selected}";
	public static final String TVIEW_importButtonText = "#{tedros.fxapi.button.import}";
	public static final String TVIEW_openExportFolderButtonText = "#{tedros.fxapi.button.open.folder.export}";
	public static final String TVIEW_addButtonText = "#{tedros.fxapi.button.add}";
	public static final String TVIEW_removeButtonText = "#{tedros.fxapi.button.remove}";
	public static final String TVIEW_printButtonText = "#{tedros.fxapi.button.print}";
	
			
	
}
