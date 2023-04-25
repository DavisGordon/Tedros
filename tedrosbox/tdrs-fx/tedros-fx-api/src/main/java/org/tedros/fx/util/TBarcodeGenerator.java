/**
 * 
 */
package org.tedros.fx.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import net.glxn.qrgen.javase.QRCode;

public class TBarcodeGenerator {

    public static BufferedImage generateUPCABarcodeImage(String barcodeText, 
    		TBarcodeResolution resolution, TBarcodeOrientation orientation) {
        UPCABean barcodeGenerator = new UPCABean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(resolution.getValue(), BufferedImage.TYPE_BYTE_BINARY, false, orientation.getValue());

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText, 
    		TBarcodeResolution resolution, TBarcodeOrientation orientation) {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(resolution.getValue(), BufferedImage.TYPE_BYTE_BINARY, false, orientation.getValue());

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generateCode128BarcodeImage(String barcodeText, 
    		TBarcodeResolution resolution, TBarcodeOrientation orientation) {
        Code128Bean barcodeGenerator = new Code128Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(resolution.getValue(), BufferedImage.TYPE_BYTE_BINARY, false, orientation.getValue());

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    public static BufferedImage generatePDF417BarcodeImage(String barcodeText, int cols, 
    		TBarcodeResolution resolution, TBarcodeOrientation orientation) {
        PDF417Bean barcodeGenerator = new PDF417Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(resolution.getValue(), BufferedImage.TYPE_BYTE_BINARY, false, orientation.getValue());
        barcodeGenerator.setColumns(cols);

        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }
    
    public static BufferedImage generateQRCodeImage(String barcodeText, int width, int height) throws IOException {
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(width, height)
                .stream();
        try(ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray())){
        	stream.close();
        	return ImageIO.read(bis);
        }
    }

}
