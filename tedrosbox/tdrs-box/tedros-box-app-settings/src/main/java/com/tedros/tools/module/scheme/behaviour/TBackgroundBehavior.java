package com.tedros.tools.module.scheme.behaviour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.tedros.core.TLanguage;
import com.tedros.core.context.TedrosContext;
import com.tedros.core.style.TThemeUtil;
import com.tedros.ejb.base.model.TFileModel;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.tools.module.scheme.model.TBackgroundImage;
import com.tedros.tools.module.scheme.model.TBackgroundImageMV;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

public class TBackgroundBehavior extends TDynaViewCrudBaseBehavior<TBackgroundImageMV, TBackgroundImage> {

	public void load(){
		super.load();
		super.configSaveButton();
		super.addAction(new TPresenterAction(TActionType.SAVE) {

			@Override
			public boolean runBefore() {
				
				TBackgroundImageMV mv = getModelView();
				TFileModel fm = mv.getModel().getFileModel();
				
				if(fm==null) {
					String msg = TLanguage.getInstance(null).getString("#{message.select.image}");
					addMessage(new TMessage(TMessageType.WARNING, msg));
					return false;
				}
				
				String bp = TedrosFolder.BACKGROUND_IMAGES_FOLDER.getFullPath();
				try {
					File f = fm.getFile();
					if(!fm.getFilePath().equals(bp)) {
						FileUtils.copyToDirectory(f, new File(bp));
					}
					
					String propFilePath = TThemeUtil.getBackgroundFilePath();
					File css = new File(TThemeUtil.getBackgroundCssFilePath());
					String imageName = fm.getFileName();
					String repeat =  "no-repeat";
					String ativar =  "true";
					FileOutputStream fos = new FileOutputStream(propFilePath);
					Properties prop = new Properties();
					prop.setProperty("image", imageName);
					prop.setProperty("repeat", repeat);
					prop.setProperty("ativar", ativar);
					prop.store(fos, "background styles");
					fos.close();
					StringBuffer sbf = new StringBuffer("#t-tedros-color { -fx-background-size: cover; -fx-background-image: url(\"../../IMAGES/FUNDO/"+imageName+"\"); -fx-background-repeat: "+repeat+"; }");
					if(css.exists()){
						css.delete();
						css.createNewFile();
					}
					TFileUtil.saveFile(sbf.toString(), css);
					TedrosContext.reloadStyle();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				return false;
			}

			@Override
			public void runAfter() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		super.setModelView(new TBackgroundImageMV(new TBackgroundImage()));
	}
	
	@Override
	public void colapseAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean processNewEntityBeforeBuildForm(TBackgroundImageMV model) {
		return true;

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
