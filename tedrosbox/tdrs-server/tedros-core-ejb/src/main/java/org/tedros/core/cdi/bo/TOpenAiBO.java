/**
 * 
 */
package org.tedros.core.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.ai.model.TCompletionRequest;
import org.tedros.core.ai.model.TCompletionResult;
import org.tedros.core.ai.model.TCreateImageRequest;
import org.tedros.core.ai.model.TImageResult;
import org.tedros.core.ai.model.TResponseFormat;
import org.tedros.core.cdi.producer.Item;
import org.tedros.core.domain.DomainPropertie;
import org.tedros.server.exception.TBusinessException;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.ImageResult;
import com.theokanning.openai.service.OpenAiService;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TOpenAiBO {


	@Inject
	@Named(DomainPropertie.OPENAI_KEY)
	private Item<String> key;

	@Inject
	@Named(DomainPropertie.AI_ENABLED)
	private Item<Boolean> enabled;
	
	/**
	 * 
	 */
	public TOpenAiBO() {
	}
	

	public TImageResult createImage(TCreateImageRequest req) throws TBusinessException {
		
		if(enabled==null || !enabled.get())
			throw new TBusinessException("Artificial Intelligence is off!");
		
		if(key==null || StringUtils.isBlank(key.get()))
			throw new TBusinessException("The OpenAi key is required, create it at https://openai.com");
		
		OpenAiService service = new OpenAiService(key.get());
		CreateImageRequest request = CreateImageRequest.builder()
	                .prompt(req.getPrompt())
	                .n(req.getN())
	                .responseFormat(req.getResponseFormat().getValue())
	                .size(req.getSize().getValue())
	                .user(req.getUser())
	                .build();
		try {
			ImageResult r = service.createImage(request);
			TImageResult tr = new TImageResult(r.getCreatedAt());
			r.getData().forEach(c->{
				String val = req.getResponseFormat().equals(TResponseFormat.URL) 
						? c.getUrl()
								: c.getB64Json();
				tr.addImage(val, req.getResponseFormat());
			});
			return tr;
		}catch(Exception e) {
			return new TImageResult(e.getMessage(), false);
		}
	}
	
	public TCompletionResult completion(TCompletionRequest req)  {
		
		if(enabled==null || !enabled.get())
			throw new TBusinessException("Artificial Intelligence is off!");
		
		if(key==null || StringUtils.isBlank(key.get()))
			throw new TBusinessException("The OpenAi key is required, create it at https://openai.com");
		
		OpenAiService service = new OpenAiService(key.get());
		CompletionRequest completionRequest = CompletionRequest.builder()
		        .prompt(req.getPrompt())
		        .model(req.getModel().getValue())
		        .echo(req.getEcho())
		        .bestOf(req.getBestOf())
		        .frequencyPenalty(req.getFrequencyPenalty())
		        .logitBias(req.getLogitBias())
		        .logprobs(req.getLogprobs())
		        .maxTokens(req.getMaxTokens())
		        .temperature(req.getTemperature())
		        .topP(req.getTopP())
		        .user(req.getUser())
		        .build();
		try { 
			CompletionResult r = service.createCompletion(completionRequest);
			TCompletionResult tr = new TCompletionResult(r.getId(), r.getCreated(), r.getModel());
			r.getChoices().forEach(c->{
				tr.addChoice(c.getText(), c.getIndex(), c.getFinish_reason());
			});
			return tr;
		}catch(Exception e) {
			return new TCompletionResult(e.getMessage(), false);
		}
		
	}

}
