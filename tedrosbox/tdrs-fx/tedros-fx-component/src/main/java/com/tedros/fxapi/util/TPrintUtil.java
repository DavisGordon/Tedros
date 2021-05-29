/**
 * 
 */
package com.tedros.fxapi.util;

import com.tedros.core.context.TedrosContext;

import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

/**
 * @author Davis Gordon
 *
 */
public class TPrintUtil {

	/**
	 * 
	 */
	public TPrintUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void print(Node node) {
		 PrinterJob job = PrinterJob.createPrinterJob();
		 if (job != null && job.showPrintDialog(TedrosContext.getStage())) {
		  PageLayout pageLayout = job.getJobSettings().getPageLayout();
		  double scaleX = 1.0;
		  if (pageLayout.getPrintableWidth() < node.getBoundsInParent().getWidth()) {
		   scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
		  }
		  double scaleY = 1.0;
		  if (pageLayout.getPrintableHeight() < node.getBoundsInParent().getHeight()) {
		   scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
		  }
		  double scaleXY = Double.min(scaleX, scaleY);
		  Scale scale = new Scale(scaleXY, scaleXY);
		  node.getTransforms().add(scale);
		  boolean success = job.printPage(node);
		  node.getTransforms().remove(scale);
		  if (success) {
		   job.endJob();
		  }
		 }
	}
	
}
