package org.tedros.tools.module.scheme.behaviour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.core.style.TThemeUtil;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.server.model.TFileModel;
import org.tedros.tools.module.scheme.model.TBackgroundImage;
import org.tedros.tools.module.scheme.model.TBackgroundImageMV;
import org.tedros.util.TFileUtil;
import org.tedros.util.TedrosFolder;

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
					StringBuffer sbf = new StringBuffer("#t-tedros-color { -fx-background-size: cover; -fx-background-image: url(\"../../IMAGES/BACKGROUND/"+imageName+"\"); -fx-background-repeat: "+repeat+"; }");
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
