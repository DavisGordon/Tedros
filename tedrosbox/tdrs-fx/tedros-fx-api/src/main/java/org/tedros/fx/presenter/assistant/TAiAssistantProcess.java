/**
 * 
 */
package org.tedros.fx.presenter.assistant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiChatCompletionController;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.TFxKey;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;
import org.tedros.fx.util.JsonHelper;
import org.tedros.server.model.ITModel;
import org.tedros.server.model.TJsonModel;
import org.tedros.server.result.TResult;
import org.tedros.server.util.TModelInfoUtil;

/**
 * @author Davis Gordon
 *
 */
public class TAiAssistantProcess extends TProcess<TResult<TChatResult>> {
	
	private String sysMsg;
	private String userMsg;
	
	public TAiAssistantProcess() {
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askForChange(TJsonModel model, String prompt, ITModel target) {

		TLanguage iEng = TLanguage.getInstance();
		
		String intro =  iEng.getString(TFxKey.AI_ASSISTANT_INTRO);
		String detail = iEng.getString(TFxKey.AI_ASSISTANT_CHANGE_MESSAGE);
		String title =  iEng.getString(TFxKey.MODEL_ORIGINAL);
		String rule =  iEng.getString(TFxKey.AI_ASSISTANT_RESPONSE_RULE);
		String compl =  iEng.getString(TFxKey.AI_ASSISTANT_CHANGE_RESPONSE_RULE);
		String info = "";
		try {
			info = TModelInfoUtil.getFieldsInfo(model.getModelType());
			if(StringUtils.isNotBlank(info))
				info = iEng.getString(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json = JsonHelper.write(model);
		String jsonTarget = JsonHelper.write(target);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+title+jsonTarget+rule+compl;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askForAnalyse(TJsonModel model, String prompt, List<ITModel> models) {

		TLanguage iEng = TLanguage.getInstance();
		
		String intro =  iEng.getString(TFxKey.AI_ASSISTANT_INTRO);
		String detail = iEng.getString(TFxKey.AI_ASSISTANT_ANALYSE_MESSAGE);
		String rule =  iEng.getString(TFxKey.AI_ASSISTANT_RESPONSE_RULE);
		String info = "";
		try {
			info = TModelInfoUtil.getFieldsInfo(model.getModelType());
			if(StringUtils.isNotBlank(info))
				info = iEng.getString(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json = JsonHelper.write(model);
		String jsonTarget = JsonHelper.write(models);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+"\r\n"+jsonTarget+rule;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void askForCreate(TJsonModel model, String prompt) {

		TLanguage iEng = TLanguage.getInstance();
		
		String intro =  iEng.getString(TFxKey.AI_ASSISTANT_INTRO);
		String detail = iEng.getString(TFxKey.AI_ASSISTANT_CREATE_MESSAGE);
		String rule =  iEng.getString(TFxKey.AI_ASSISTANT_RESPONSE_RULE);
		String info = "";
		try {
			info = TModelInfoUtil.getFieldsInfo(model.getModelType());
			if(StringUtils.isNotBlank(info))
				info = iEng.getString(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json = JsonHelper.write(model);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+rule;
	}

	@Override
	protected TTaskImpl<TResult<TChatResult>> createTask() {

		return new TTaskImpl<TResult<TChatResult>>() {
			
			protected TResult<TChatResult> call() throws IOException, MalformedURLException {
	    		ServiceLocator loc = ServiceLocator.getInstance();
	    		TResult<TChatResult> res = null;
	    		try {

	    			TChatMessage s = new TChatMessage(TChatRole.SYSTEM, sysMsg);
	    			TChatMessage m = new TChatMessage(TChatRole.USER, userMsg);
	    			TChatRequest req = new TChatRequest();
	    			req.addMessage(s);
	    			req.addMessage(m);
	    			req.setTemperature(0.0D);
	    			req.setMaxTokens(2000);
	    			
	    			TAiChatCompletionController serv = loc.lookup(TAiChatCompletionController.JNDI_NAME);
	    			res = serv.chat(TedrosContext.getLoggedUser().getAccessToken(), req);
	    			
	    			
	    		}catch(Exception ex) {
	    			ex.printStackTrace();
	    		}finally {
	    			loc.close();
	    		}
	    		return res;
	    	}
	
			@Override
			public String getServiceNameInfo() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
}
