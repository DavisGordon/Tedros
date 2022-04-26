package org.somos.module.mailing.behavior;

import java.io.File;
import java.util.Properties;

import org.somos.model.Mailing;
import org.somos.module.acao.util.MailingUtil;
import org.somos.module.mailing.model.MailingModelView;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TFileUtil;
import com.tedros.util.TResourceUtil;
import com.tedros.util.TedrosFolder;

public class MailingSaveAction extends TPresenterAction {

	private String siteurl;
	
	public MailingSaveAction() {
		super(TActionType.SAVE);
		Properties prop = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		siteurl = prop.getProperty("siteurl");
	}

	@Override
	public boolean runBefore() {
		TDynaPresenter<MailingModelView> presenter = getPresenter();
		final Mailing mailing = (Mailing) presenter.getBehavior().getModelView().getModel();
		String str = MailingUtil.buildConteudo(mailing);
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"/email_template.html";
		File file = new File(path);
		String html = TFileUtil.readFile(file);

		html = html.replace("#HOST#", siteurl);
		html = html.replace("#CONTENT#", str);
		mailing.setHtml(html);
		return true;
	}

	@Override
	public void runAfter() {
	}

}
