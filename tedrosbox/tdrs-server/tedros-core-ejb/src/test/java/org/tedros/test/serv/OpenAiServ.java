/**
 * 
 */
package org.tedros.test.serv;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.tedros.core.ai.model.TUsage;
import org.tedros.core.ai.model.completion.TCompletionRequest;
import org.tedros.core.ai.model.completion.TCompletionResult;
import org.tedros.core.ai.model.completion.chat.TChatChoice;
import org.tedros.core.ai.model.completion.chat.TChatMessage;
import org.tedros.core.ai.model.completion.chat.TChatRequest;
import org.tedros.core.ai.model.completion.chat.TChatResult;
import org.tedros.core.ai.model.completion.chat.TChatRole;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

/**
 * @author Davis Gordon
 *
 */
public class OpenAiServ {
	
	private static String key = "OPEN_AI_KEY";

	public static TChatResult chat(TChatRequest req){

		System.out.println("--------------------------");
		System.out.println("Chat Request: ");
		System.out.println("--------------------------");
		OpenAiService service = new OpenAiService(key, Duration.ofSeconds(80));
		
		List<ChatMessage> lst = new ArrayList<>();
		req.getMessages().forEach(m->{
			ChatMessage m1 = new ChatMessage();
			m1.setRole(m.getRole().value());
			m1.setContent(m.getContent());
			lst.add(m1);
			System.out.println("Role: "+m.getRole().value());
			System.out.println("Content: ");
			System.out.println(m.getContent());
			System.out.println("--------------------------");
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

			System.out.println("Request OpenAi service...");
			ChatCompletionResult cr = service.createChatCompletion(c);
			System.out.println("Response received.");
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
		
		OpenAiService service = new OpenAiService(key, Duration.ofSeconds(80));
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
