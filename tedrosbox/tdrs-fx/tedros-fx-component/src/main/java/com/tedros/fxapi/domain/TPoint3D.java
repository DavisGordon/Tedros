package com.tedros.fxapi.domain;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;

/**
 * javafx.geometry.Point3D Enum representation.
 * */
public enum TPoint3D {

	X_AXIS (Rotate.X_AXIS),
	Y_AXIS (Rotate.Y_AXIS),
	Z_AXIS (Rotate.Z_AXIS);
	
	
	private Point3D value;
	
	private TPoint3D(Point3D value) {
		this.value = value;
	}
	
	public Point3D getValue() {
		return value;
	}
}
