/**
 * 
 */
package org.tedros.core.ai.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TAiCreateImage;
import org.tedros.core.ai.model.TAiImage;
import org.tedros.core.ai.model.TResponseFormat;
import org.tedros.core.ai.model.completion.TCompletionRequest;
import org.tedros.core.ai.model.completion.TCompletionResult;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;

import sun.misc.BASE64Decoder;

/**
 * @author Davis Gordon
 *
 */
public final class TAiModelUtil {

	private TAiModelUtil() {
	}
	

	public static TCreateImageRequest convert(TAiCreateImage e) {
		TCreateImageRequest req = new TCreateImageRequest();
		req.setPrompt(e.getPrompt());
		req.setN(e.getQuantity());
		req.setSize(e.getSize());
		req.setResponseFormat(e.getFormat());
		req.setUser(e.getUser());
		return req;
	}
	

	public static void parse(TImageResult r, TAiCreateImage e) {
		
		if(!r.isSuccess()) {
			e.addEvent("Fail: "+r.getLog());
		}else {
			
			r.getData().forEach(c->{
				if(c.getFormat().equals(TResponseFormat.BASE64))
					e.addImage(convert(c.getValue()));
				else
					e.addImage(c.getValue());
			});
			
			e.addEvent("Success");
		}
	}
	
	public static TCompletionRequest convert(TAiCompletion e) {
		TCompletionRequest req = new TCompletionRequest();
		req.setModel(e.getModel());
		req.setMaxTokens(e.getMaxTokens());
		req.setPrompt(e.getPrompt());
		req.setTemperature(e.getTemperature());
		req.setUser(e.getUser());
		
		return req;
	}
	
	public static void parse(TCompletionResult r, TAiCompletion e) {
		
		if(!r.isSuccess()) {
			e.setResponse("Fail see events for more details.");
			e.addEvent("Fail: "+r.getLog());
		}else {
			StringBuilder sb = new StringBuilder();
			r.getChoices().forEach(c->{
				sb.append(c.getText());
			});
			e.setResponse(sb.toString());
			e.addEvent("Success");
		}
	}
	

	public static TFileEntity convert(String base64) {
		
		try {
			byte[] imageByte;

			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(base64);
			
			TFileEntity e = new TFileEntity();
			e.setFileName("ai-image-created-"+System.currentTimeMillis()+".png");
			e.setFileExtension("png");
			TByteEntity b = new TByteEntity();
			b.setBytes(imageByte);
			e.setByte(b);
			return e;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
