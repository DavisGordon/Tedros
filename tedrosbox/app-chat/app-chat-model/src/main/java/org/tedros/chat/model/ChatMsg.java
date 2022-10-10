/**
 * 
 */
package org.tedros.chat.model;

import java.util.Date;
import java.util.List;

import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.server.model.ITModel;
import org.tedros.server.security.TAccessToken;

/**
 * @author Davis Gordon
 *
 */
@Deprecated
public class ChatMsg implements ITModel {
	
	private static final long serialVersionUID = 7036555405839477004L;
	
	private Long id;
	private String code;
	private TAccessToken token;
	
	private ChatUser from;
	private ChatUser to;
	
	private List<ChatUser> participants;
	
	private String content;
	private FileMsg file;
	
	private TStatus status;
	private Date dateTime;
	
	private ChatMsg repliedTo;
	
	/**
	 * 
	 */
	public ChatMsg() {
	}

	/**
	 * @param id
	 * @param code
	 * @param token
	 * @param from
	 * @param to
	 * @param file
	 * @param status
	 * @param dateTime
	 */
	public ChatMsg(Long id, String code, TAccessToken token, ChatUser from, ChatUser to, FileMsg file, TStatus status,
			Date dateTime) {
		this.id = id;
		this.code = code;
		this.token = token;
		this.from = from;
		this.to = to;
		this.file = file;
		this.status = status;
		this.dateTime = dateTime;
	}

	/**
	 * @param id
	 * @param code
	 * @param token
	 * @param from
	 * @param to
	 * @param content
	 * @param status
	 * @param dateTime
	 */
	public ChatMsg(Long id, String code, TAccessToken token, ChatUser from, ChatUser to, String content, TStatus status,
			Date dateTime) {
		this.id = id;
		this.code = code;
		this.token = token;
		this.from = from;
		this.to = to;
		this.content = content;
		this.status = status;
		this.dateTime = dateTime;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the token
	 */
	public TAccessToken getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(TAccessToken token) {
		this.token = token;
	}

	/**
	 * @return the from
	 */
	public ChatUser getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(ChatUser from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public ChatUser getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(ChatUser to) {
		this.to = to;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the file
	 */
	public FileMsg getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FileMsg file) {
		this.file = file;
	}

	/**
	 * @return the status
	 */
	public TStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TStatus status) {
		this.status = status;
	}

	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the repliedTo
	 */
	public ChatMsg getRepliedTo() {
		return repliedTo;
	}

	/**
	 * @param repliedTo the repliedTo to set
	 */
	public void setRepliedTo(ChatMsg repliedTo) {
		this.repliedTo = repliedTo;
	}

	/**
	 * @return the participants
	 */
	public List<ChatUser> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(List<ChatUser> participants) {
		this.participants = participants;
	}

}
