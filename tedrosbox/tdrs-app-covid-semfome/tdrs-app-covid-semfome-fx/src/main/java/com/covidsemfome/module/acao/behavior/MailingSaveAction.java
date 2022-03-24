package com.covidsemfome.module.acao.behavior;

import java.io.File;

import com.covidsemfome.model.Mailing;
import com.covidsemfome.module.acao.model.MailingModelView;
import com.covidsemfome.module.acao.util.MailingUtil;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolder;

public class MailingSaveAction extends TPresenterAction {

	public MailingSaveAction() {
		super(TActionType.SAVE);
	}

	@Override
	public boolean runBefore() {
		TDynaPresenter<MailingModelView> presenter = getPresenter();
		final Mailing mailing = (Mailing) presenter.getBehavior().getModelView().getModel();
		String str = MailingUtil.buildConteudo(mailing);
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolder.CONF_FOLDER.getFolder()+"/email_template.html";
		File file = new File(path);
		String html = TFileUtil.readFile(file);
		html = html.replace("#CONTENT#", str);
		mailing.setHtml(html);
		return true;
	}

	@Override
	public void runAfter() {
	}

}
