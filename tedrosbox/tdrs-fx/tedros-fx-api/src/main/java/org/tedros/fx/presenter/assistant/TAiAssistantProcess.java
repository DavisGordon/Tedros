/**
 * 
 */
package org.tedros.fx.presenter.assistant;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiChatMessage;
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
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class TAiAssistantProcess extends TProcess<TResult<TChatResult>> {
	
	private String sysMsg;
	private String userMsg;
	
	private TChatRequest req;
	
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
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		
		String json = JsonHelper.write(model);
		String jsonTarget = JsonHelper.write(target);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+title+jsonTarget+rule+compl;

		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, sysMsg);
		TChatMessage m = new TChatMessage(TChatRole.USER, userMsg);
		buildChatRequest(0.0D, 2000);
		addMessage(s, m);
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
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		
		String json = JsonHelper.write(model);
		String jsonTarget = JsonHelper.write(models);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+"\r\n"+jsonTarget+rule;
		
		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, sysMsg);
		TChatMessage m = new TChatMessage(TChatRole.USER, userMsg);
		buildChatRequest(0.0D, 2000);
		addMessage(s, m);
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
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		
		String json = JsonHelper.write(model);
		
		this.sysMsg = intro+detail+info+json;
		this.userMsg = prompt+rule;

		TChatMessage s = new TChatMessage(TChatRole.SYSTEM, sysMsg);
		TChatMessage m = new TChatMessage(TChatRole.USER, userMsg);
		buildChatRequest(0.0D, 2000);
		addMessage(s, m);
	}
	

	public void chat(List<TAiChatMessage> msgs) throws Exception {
		buildChatRequest(0.8D, 2047);
		msgs.forEach(e->{
			TChatMessage m = new TChatMessage();
			m.setContent(e.getContent());
			m.setRole(e.getRole());
			req.addMessage(m);
		});
	}

	@Override
	protected TTaskImpl<TResult<TChatResult>> createTask() {

		return new TTaskImpl<TResult<TChatResult>>() {
			
			protected TResult<TChatResult> call() throws IOException, MalformedURLException {
	    		ServiceLocator loc = ServiceLocator.getInstance();
	    		TResult<TChatResult> res = null;
	    		try {

	    			TAiChatCompletionController serv = loc.lookup(TAiChatCompletionController.JNDI_NAME);
	    			res = serv.chat(TedrosContext.getLoggedUser().getAccessToken(), req);
	    			
	    		}catch(Exception e) {
	    			TLoggerUtil.error(getClass(), e.getMessage(), e);
	    		}finally {
	    			loc.close();
	    		}
	    		return res;
	    	}
			
			@Override
			public String getServiceNameInfo() {
				return null;
			}
		};
	}

	private void buildChatRequest(double temperature, int maxTokens) {
		req = new TChatRequest();
		req.setTemperature(temperature);
		req.setMaxTokens(maxTokens);
	}

	private void addMessage(TChatMessage... arr) {
		for(TChatMessage m : arr)
			req.addMessage(m);
	}
}
