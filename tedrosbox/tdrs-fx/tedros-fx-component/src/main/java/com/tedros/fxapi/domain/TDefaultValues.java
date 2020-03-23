/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 02/04/2014
 */
package com.tedros.fxapi.domain;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TDefaultValues {

	public static final int   LABEL_WIDTH = 250;
	public static final TextAlignment LABEL_TEXT_ALIGNMENT = TextAlignment.LEFT; 
	public static final Pos LABEL_ALIGNMENT = Pos.CENTER_LEFT;
	public static final OverrunStyle LABEL_TEXT_OVERRUN = OverrunStyle.ELLIPSIS;
	public static final String LABEL_ELLIPSISSTRING = "...";
	public static final boolean LABEL_UNDERLINE = false;
	public static final boolean LABEL_WRAPTEXT = true;
	public static final boolean LABEL_MNEMONICPARSING = false;
	
	public static final String FONT_FAMILY = "System";
	public static final FontWeight FONT_WEIGHT = FontWeight.NORMAL;
	public static final FontPosture FONT_POSTURE = FontPosture.REGULAR;
	public static final double FONT_SIZE = 12;
	
	public static final double READER_TEXT_X = 0;
	public static final double READER_TEXT_Y = 0;
	public static final double READER_TEXT_WRAPPINGWIDTH = 0;
	public static final boolean READER_TEXT_UNDERLINE = false;
	public static final boolean READER_TEXT_STRIKETHROUGH = false;
	public static final boolean READER_TEXT_VISIBLE = true;
	public static final VPos READER_TEXT_TEXTORIGIN = VPos.BASELINE;
	public static final TextBoundsType READER_TEXT_BOUNDSTYPE = TextBoundsType.LOGICAL;
	public static final TextAlignment READER_TEXT_TEXTALIGNMENT = TextAlignment.LEFT;
	public static final FontSmoothingType READER_TEXT_FONTSMOOTHINGTYPE = FontSmoothingType.GRAY;
	
	public static final String DISPLAY_PROPERTY_NEW_MODEL = "?";
	
}
