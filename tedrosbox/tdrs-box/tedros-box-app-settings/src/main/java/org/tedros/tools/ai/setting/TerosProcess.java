/**
 * 
 */
package org.tedros.tools.ai.setting;

import org.tedros.ai.openai.OpenAITerosService;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.process.TProcess;
import org.tedros.fx.process.TTaskImpl;

/**
 * @author Davis Gordon
 *
 */
public class TerosProcess extends TProcess<String> {

	private String prompt;
	
	@Override
	protected TTaskImpl<String> createTask() {
		return new TTaskImpl<String>() {

			@Override
			public String getServiceNameInfo() {
				return null;
			}

			@Override
			protected String call() throws Exception {	
				String msg = TedrosContext.getLoggedUser().getName() + " does not currently have any system views open";
				TViewDescriptor vds = TedrosAppManager.getInstance().getCurrentViewDescriptor();
				if(vds!=null)
					msg = TedrosContext.getLoggedUser().getName()+" currently has the '"+vds.getTitle()+ "' view open";
				String resp = OpenAITerosService.getInstance().call(prompt, msg);
				return resp;
			}
			
		};
	}

	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
