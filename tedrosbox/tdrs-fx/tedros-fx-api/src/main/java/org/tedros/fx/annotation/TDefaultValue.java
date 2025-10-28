package org.tedros.fx.annotation;

import org.tedros.fx.TFxKey;
import org.tedros.fx.form.TDefaultForm;
import org.tedros.fx.process.TProcess;
import org.tedros.server.model.ITModel;

public interface TDefaultValue {

	final static String TVIEW_newButtonText = TFxKey.BUTTON_NEW;
	final static String TVIEW_saveButtonText = TFxKey.BUTTON_SAVE;
	final static String TVIEW_editButtonText = TFxKey.BUTTON_EDIT;
	final static String TVIEW_cancelButtonText = TFxKey.BUTTON_CANCEL;
	final static String TVIEW_deleteButtonText = TFxKey.BUTTON_DELETE;
	final static String TVIEW_searchButtonText =TFxKey.BUTTON_SEARCH;
	static final String TVIEW_cleanButtonText = TFxKey.BUTTON_CLEAN;
	static final String TVIEW_selectButtonText = TFxKey.BUTTON_SELECT;
	static final String TVIEW_selectAllButtonText = TFxKey.BUTTON_SELECTALL;
	static final String TVIEW_closeButtonText = TFxKey.BUTTON_CLOSE;
	final static String TVIEW_viewTitle = TFxKey.VIEW_TITLE;
	final static String TVIEW_listTitle = TFxKey.VIEW_LIST_TITLE;
	final static String TVIEW_masterTitle = "Primary data";
	final static String TVIEW_detailTitle = "Secundary data";
	final static String TVIEW_colapse = " <|> ";
	
	final static Class<?> TFORM_modelClass = ITModel.class;
	final static Class<?> TFORM_formClass = TDefaultForm.class;
	final static Class<?> TFORM_processClass =TProcess.class;
	final static String TFORM_formTitle = "";
	final static String TCHART_title = "";
	
	final static double DEFAULT_DOUBLE_VALUE_IDENTIFICATION = -99229922;
	final static float DEFAULT_FLOAT_VALUE_IDENTIFICATION = -99229922;
	final static int DEFAULT_INT_VALUE_IDENTIFICATION = -99229922;
	static final String TVIEW_editModeTitle = TFxKey.RADIO_BUTTON_EDIT;
	static final String TVIEW_readerModeTitle = TFxKey.RADIO_BUTTON_READ;
	static final String TVIEW_excelButtonText = "Excel";
	static final String TVIEW_wordButtonText = "Word";
	static final String TVIEW_pdfButtonText = "PDF";
	static final String TVIEW_selected = TFxKey.SELECTED;
	static final String TVIEW_importButtonText = TFxKey.BUTTON_IMPORT;
	static final String TVIEW_openExportFolderButtonText = TFxKey.BUTTON_OPEN_FOLDER_EXPORT;
	static final String TVIEW_addButtonText = TFxKey.BUTTON_ADD;
	static final String TVIEW_removeButtonText = TFxKey.BUTTON_REMOVE;
	static final String TVIEW_printButtonText = TFxKey.BUTTON_PRINT;
	
			
	
}
