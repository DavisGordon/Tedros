package com.tedros.fxapi.annotation;

import javafx.scene.Cursor;

public enum TCursor {
	
	DEFAULT (Cursor.DEFAULT),
	CROSSHAIR (Cursor.CROSSHAIR),
	TEXT (Cursor.TEXT),
	WAIT (Cursor.WAIT),
	SW_RESIZE (Cursor.SW_RESIZE),
	SE_RESIZE (Cursor.SE_RESIZE),
	NW_RESIZE (Cursor.NW_RESIZE),
	NE_RESIZE (Cursor.NE_RESIZE),
	N_RESIZE (Cursor.N_RESIZE),
	S_RESIZE (Cursor.S_RESIZE),
	W_RESIZE (Cursor.W_RESIZE),
	E_RESIZE (Cursor.E_RESIZE),
	OPEN_HAND (Cursor.OPEN_HAND),
	CLOSED_HAND (Cursor.CLOSED_HAND),
	HAND (Cursor.HAND),
	MOVE (Cursor.MOVE),
	DISAPPEAR (Cursor.DISAPPEAR),
	H_RESIZE (Cursor.H_RESIZE),
	V_RESIZE (Cursor.V_RESIZE),
	NONE (Cursor.NONE),
	NULL (null);
	
	private Cursor value;
	
	private TCursor(Cursor value) {
		this.value = value;
	}
	
	public Cursor getValue() {
		return value;
	}
	

}
