/**
 * 
 */
package org.tedros.core.cdi.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.ai.model.TResponseFormat;
import org.tedros.core.ai.model.TUsage;
import org.tedros.core.ai.model.completion.TCompletionRequest;
import org.tedros.core.ai.model.completion.TCompletionResult;
import org.tedros.core.ai.model.completion.chat.TChatChoice;
import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;
import org.tedros.core.ai.model.image.TCreateImageRequest;
import org.tedros.core.ai.model.image.TImageResult;
import org.tedros.core.cdi.producer.Item;
import org.tedros.core.domain.DomainPropertie;
import org.tedros.server.exception.TBusinessException;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
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
			TImageResult tr = new TImageResult();
			r.getData().forEach(c->{
				String val = req.getResponseFormat().equals(TResponseFormat.URL) 
						? c.getUrl()
								: c.getB64Json();
				tr.addData(val, req.getResponseFormat());
			});
			return tr;
		}catch(Exception ex) {
			if(ex instanceof OpenAiHttpException) {
				OpenAiHttpException oex = (OpenAiHttpException) ex;
				return new TImageResult(ex.getMessage(), oex.statusCode+":"+oex.code);
			}else
				return new TImageResult(ex.getMessage(), false);
		}
	}
	
	public TChatResult chat(TChatRequest req){
		

		if(enabled==null || !enabled.get())
			throw new TBusinessException("Artificial Intelligence is off!");
		
		if(key==null || StringUtils.isBlank(key.get()))
			throw new TBusinessException("The OpenAi key is required, create it at https://openai.com");
		
		OpenAiService service = new OpenAiService(key.get());
		List<ChatMessage> lst = new ArrayList<>();
		req.getMessages().forEach(m->{
			ChatMessage m1 = new ChatMessage();
			m1.setRole(m.getRole().value());
			m1.setContent(m.getContent());
			lst.add(m1);
		});
		ChatCompletionRequest c = ChatCompletionRequest.builder()
				.model(req.getModel().value())
				.messages(lst)
				.n(req.getN())
				.stop(req.getStop())
				.stream(req.getStream())
		        .frequencyPenalty(req.getFrequencyPenalty())
		        .logitBias(req.getLogitBias())
		        .maxTokens(req.getMaxTokens())
		        .temperature(req.getTemperature())
		        .topP(req.getTopP())
		        .user(req.getUser())
				.build();
		TChatResult r;
		try {
			ChatCompletionResult cr = service.createChatCompletion(c);
			r = new TChatResult(cr.getId(), cr.getModel(), 
					new TUsage(cr.getUsage().getPromptTokens(), cr.getUsage().getCompletionTokens(), 
							cr.getUsage().getTotalTokens()));
			cr.getChoices().stream().sorted((a1, a2)->{
				return a1.getIndex().compareTo(a2.getIndex());
			}).forEach(ch->{
				r.addChoice(new TChatChoice(ch.getIndex(), 
						new TChatMessage(TChatRole.value(ch.getMessage().getRole()), 
								ch.getMessage().getContent()), ch.getFinishReason()));
			});
			return r;
		}catch(Exception ex) {
			if(ex instanceof OpenAiHttpException) {
				OpenAiHttpException oex = (OpenAiHttpException) ex;
				return new TChatResult(ex.getMessage(), oex.statusCode+":"+oex.code);
			}else
				return new TChatResult(ex.getMessage(), false);
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
			TCompletionResult tr = new TCompletionResult(r.getId(), r.getModel(), 
					new TUsage(r.getUsage().getPromptTokens(), r.getUsage().getCompletionTokens(), 
					r.getUsage().getTotalTokens()));
			r.getChoices().forEach(c->{
				tr.addChoice(c.getText(), c.getIndex(), c.getFinish_reason());
			});
			return tr;
		}catch(Exception ex) {
			if(ex instanceof OpenAiHttpException) {
				OpenAiHttpException oex = (OpenAiHttpException) ex;
				return new TCompletionResult(ex.getMessage(), oex.statusCode+":"+oex.code);
			}else
				return new TCompletionResult(ex.getMessage(), false);
		}
		
	}

}
