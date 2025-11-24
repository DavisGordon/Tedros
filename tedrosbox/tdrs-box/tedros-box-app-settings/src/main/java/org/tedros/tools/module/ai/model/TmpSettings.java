package org.tedros.tools.module.ai.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.form.TSetting;
import org.tedros.tools.module.ai.function.HtmlContent;
import org.tedros.tools.module.ai.function.ShowHtmlAiResponse;

import javafx.scene.Node;
import javafx.scene.web.WebView;

public class TmpSettings extends TSetting {

	public TmpSettings(ITComponentDescriptor descriptor) {
		super(descriptor);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		
		((Node) super.getForm()).sceneProperty().addListener((a,o,n)->{
			if(n!=null) {
				
				WebView wv = super.getControl("webContent");
				
				wv.getEngine().getLoadWorker().stateProperty().addListener((sa,so,sn)->{
					if(sn==javafx.concurrent.Worker.State.SUCCEEDED) {
						String content = null;
						try {
							content = FileUtils.readFileToString(new File("C:\\others\\tmp.txt"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						ShowHtmlAiResponse func = new ShowHtmlAiResponse();
						func.getCallback().apply(new HtmlContent(content));
					}
				});
				
			}
		});
	
		

	}
	
	

}
