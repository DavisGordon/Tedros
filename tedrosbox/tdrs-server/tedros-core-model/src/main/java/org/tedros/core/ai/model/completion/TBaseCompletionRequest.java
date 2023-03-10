package org.tedros.core.ai.model.completion;

import java.util.List;
import java.util.Map;

public abstract class TBaseCompletionRequest extends TBaseRequest {

	private static final long serialVersionUID = 2810009994323422568L;
	
	/**
	 * The maximum number of tokens to generate.
	 * Requests can use up to 2048 tokens shared between prompt and completion.
	 * (One token is roughly 4 characters for normal English text)
	 */
	protected Integer maxTokens = 512;
	/**
	 * What sampling temperature to use. Higher values means the model will take more risks.
	 * Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
	 *
	 * We generally recommend using this or {@link CompletionRequest#topP} but not both.
	 */
	protected Double temperature = 0.4;
	/**
	 * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
	 * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are
	 * considered.
	 *
	 * We generally recommend using this or {@link CompletionRequest#temperature} but not both.
	 */
	protected Double topP;
	/**
	 * How many completions to generate for each prompt.
	 *
	 * Because this parameter generates many completions, it can quickly consume your token quota.
	 * Use carefully and ensure that you have reasonable settings for {@link CompletionRequest#maxTokens} and {@link CompletionRequest#stop}.
	 */
	protected Integer n;
	/**
	 * Whether to stream back partial progress.
	 * If set, tokens will be sent as data-only server-sent events as they become available,
	 * with the stream terminated by a data: DONE message.
	 */
	protected Boolean stream;
	/**
	 * Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens.
	 * For example, if logprobs is 10, the API will return a list of the 10 most likely tokens.
	 * The API will always return the logprob of the sampled token,
	 * so there may be up to logprobs+1 elements in the response.
	 */
	protected Integer logprobs;
	/**
	 * Number between 0 and 1 (default 0) that penalizes new tokens based on whether they appear in the text so far.
	 * Increases the model's likelihood to talk about new topics.
	 */
	protected Double presencePenalty;
	/**
	 * Number between 0 and 1 (default 0) that penalizes new tokens based on their existing frequency in the text so far.
	 * Decreases the model's likelihood to repeat the same line verbatim.
	 */
	protected Double frequencyPenalty;
	/**
	 * Generates best_of completions server-side and returns the "best"
	 * (the one with the lowest log probability per token).
	 * Results cannot be streamed.
	 *
	 * When used with {@link CompletionRequest#n}, best_of controls the number of candidate completions and n specifies how many to return,
	 * best_of must be greater than n.
	 */
	protected Integer bestOf;
	/**
	 * Modify the likelihood of specified tokens appearing in the completion.
	 *
	 * Maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value from -100 to 100.
	 *
	 * https://beta.openai.com/docs/api-reference/completions/create#completions/create-logit_bias
	 */
	protected Map<String, Integer> logitBias;

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * The returned text will not contain the stop sequence.
     */
    private List<String> stop;
	
	public TBaseCompletionRequest() {
		super();
	}

	/**
	 * @param prompt
	 * @param user
	 */
	public TBaseCompletionRequest(String prompt, String user) {
		super(prompt, user);
	}

	/**
	 * @param prompt
	 * @param user
	 * @param maxTokens
	 * @param temperature
	 */
	public TBaseCompletionRequest(String prompt, String user, Integer maxTokens, Double temperature) {
		super(prompt, user);
		this.maxTokens = maxTokens;
		this.temperature = temperature;
	}

	/**
	 * @return the maxTokens
	 */
	public Integer getMaxTokens() {
		return maxTokens;
	}

	/**
	 * @param maxTokens the maxTokens to set
	 */
	public void setMaxTokens(Integer maxTokens) {
		this.maxTokens = maxTokens;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the topP
	 */
	public Double getTopP() {
		return topP;
	}

	/**
	 * @param topP the topP to set
	 */
	public void setTopP(Double topP) {
		this.topP = topP;
	}

	/**
	 * @return the n
	 */
	public Integer getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(Integer n) {
		this.n = n;
	}

	/**
	 * @return the stream
	 */
	public Boolean getStream() {
		return stream;
	}

	/**
	 * @param stream the stream to set
	 */
	public void setStream(Boolean stream) {
		this.stream = stream;
	}

	/**
	 * @return the logprobs
	 */
	public Integer getLogprobs() {
		return logprobs;
	}

	/**
	 * @param logprobs the logprobs to set
	 */
	public void setLogprobs(Integer logprobs) {
		this.logprobs = logprobs;
	}

	/**
	 * @return the presencePenalty
	 */
	public Double getPresencePenalty() {
		return presencePenalty;
	}

	/**
	 * @param presencePenalty the presencePenalty to set
	 */
	public void setPresencePenalty(Double presencePenalty) {
		this.presencePenalty = presencePenalty;
	}

	/**
	 * @return the frequencyPenalty
	 */
	public Double getFrequencyPenalty() {
		return frequencyPenalty;
	}

	/**
	 * @param frequencyPenalty the frequencyPenalty to set
	 */
	public void setFrequencyPenalty(Double frequencyPenalty) {
		this.frequencyPenalty = frequencyPenalty;
	}

	/**
	 * @return the bestOf
	 */
	public Integer getBestOf() {
		return bestOf;
	}

	/**
	 * @param bestOf the bestOf to set
	 */
	public void setBestOf(Integer bestOf) {
		this.bestOf = bestOf;
	}

	/**
	 * @return the logitBias
	 */
	public Map<String, Integer> getLogitBias() {
		return logitBias;
	}

	/**
	 * @param logitBias the logitBias to set
	 */
	public void setLogitBias(Map<String, Integer> logitBias) {
		this.logitBias = logitBias;
	}

	/**
	 * @return the stop
	 */
	public List<String> getStop() {
		return stop;
	}

	/**
	 * @param stop the stop to set
	 */
	public void setStop(List<String> stop) {
		this.stop = stop;
	}

}