package com.solidarity.module.acao.behavior;

import java.io.File;

import com.solidarity.model.Mailing;
import com.solidarity.module.acao.model.MailingModelView;
import com.solidarity.module.acao.util.MailingUtil;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.util.TFileUtil;
import com.tedros.util.TedrosFolderEnum;

public class MailingAction extends TPresenterAction<TDynaPresenter<MailingModelView>> {

	@Override
	public boolean runBefore(TDynaPresenter<MailingModelView> presenter) {
		final Mailing mailing = (Mailing) presenter.getBehavior().getModelView().getModel();
		String str = MailingUtil.buildConteudo(mailing);
		String path = TFileUtil.getTedrosFolderPath()+TedrosFolderEnum.CONF_FOLDER.getFolder()+"/email_template.html";
		File file = new File(path);
		String html = TFileUtil.readFile(file);
		html = html.replace("#CONTENT#", str);
		mailing.setHtml(html);
		return true;
	}

	@Override
	public void runAfter(TDynaPresenter<MailingModelView> presenter) {
		// TODO Auto-generated method stub
		
	}

}
