/**
 * 
 */
package org.tedros.chat.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.tedros.chat.entity.ChatMessage;
import org.tedros.chat.entity.ChatUser;
import org.tedros.chat.entity.TStatus;
import org.tedros.common.model.TByteEntity;
import org.tedros.common.model.TFileEntity;
import org.tedros.fx.util.TFileBaseUtil;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.TFileModel;
import org.tedros.server.security.TAccessToken;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Davis Gordon
 *
 */
public class MessageBuilder {

	/**
	 * 
	 */
	public MessageBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] arr) {
		ChatUser u = new ChatUser();
		//testAuth(u);
		
		try {
			u.setId(22L);
			u.setName("Davis Dun");
			
			ChatUser to = new ChatUser();
			to.setId(44L);
			to.setName("Fulano de tal");
			TFileModel fm = new TFileModel(new File("C:\\Users\\Davis Gordon\\Documents\\Covid Sem Fome\\picpay.png"));
			ITFileEntity fe = TFileBaseUtil.convert(fm);
			ChatMessage m = new ChatMessage();
			m.setFile((TFileEntity) fe);
			m.setInsertDate(new Date());
			m.setFrom(u);
			m.setTo(to);
			
			MessageBuilder.writeMessage(m, Step.SEND_MSG, System.out);
			
			ByteArrayOutputStream ous = new ByteArrayOutputStream();
			MessageBuilder.writeMessage(m, Step.SEND_MSG,  ous);
			InputStream is = new ByteArrayInputStream(ous.toByteArray());
			Document doc = MessageBuilder.parse(is);
			ChatMessage msg = MessageBuilder.getMessage(doc);
			
			fm =  TFileBaseUtil.convert(msg.getFile());
			fm.setFilePath("C:\\tmp\\");
			File f = fm.getFile();
			
			MessageBuilder.writeMessage(msg, Step.SEND_MSG, System.out);
			
		
			
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param u
	 */
	private static void testAuth(ChatUser u) {
		TAccessToken t = new TAccessToken("fghkiytfgjjj");
		u.setToken(t);
		try {
			MessageBuilder.writeAuthentication(u,  System.out);
			ByteArrayOutputStream ous = new ByteArrayOutputStream();
			MessageBuilder.writeAuthentication(u,  ous);
			InputStream is = new ByteArrayInputStream(ous.toByteArray());
			Document doc = MessageBuilder.parse(is);
			String token = MessageBuilder.getToken(doc);
			Step step = MessageBuilder.getStep(doc);
			System.out.println("Token = "+token+", Step = "+step.name());
			
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param is
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder db = buildDocumentBuilder();
		Document doc = db.parse(is);
		doc.getDocumentElement().normalize();
		return doc;
	}

	public static void writeAuthentication(ChatUser user, OutputStream ous) throws ParserConfigurationException {
		 Document doc = buildDocument();
		 Element rootElement = doc.createElement("chat");
		 doc.appendChild(rootElement);

		 createTokenTag(user, doc, rootElement);
		 Step s = Step.AUTHENTICATION;
		 createStepTag(doc, rootElement, s);
		 
		 try {
			writeXml(doc,ous);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeMessage(ChatMessage msg, Step step, OutputStream ous) throws ParserConfigurationException {
		 Document doc = buildDocument();
		 Element rootElement = doc.createElement("chat");
		 doc.appendChild(rootElement);

		 createStepTag(doc, rootElement, step);
		 
		 Element from = doc.createElement("from");
		 rootElement.appendChild(from);
		 createElement(doc, from, "id", msg.getFrom().getId().toString());
		 createElement(doc, from, "name", msg.getFrom().getName());
		 
		 Element to = doc.createElement("to");
		 rootElement.appendChild(to);
		 createElement(doc, to, "id", msg.getTo().getId().toString());
		 createElement(doc, to, "name", msg.getTo().getName());
		 
		 if(msg.getContent()!=null)
			 createCDataElement(doc, rootElement, "content", msg.getContent());
		 
		 if(msg.getFile()!=null) {
			 TFileEntity tfe = msg.getFile();
			 Element file = doc.createElement("file");
			 rootElement.appendChild(file);
			 createElement(doc, file, "id", tfe.isNew() ? "" : tfe.getId().toString());
			 createElement(doc, file, "name", tfe.getFileName());
			 createElement(doc, file, "extension", tfe.getFileExtension());
			 createElement(doc, file, "size", tfe.getFileSize().toString());
			 
			 String encoded = Base64.getEncoder().encodeToString(tfe.getByteEntity().getBytes());
			 createCDataElement(doc, file, "base64", encoded);
		 }
		 createElement(doc, rootElement, "status", TStatus.SENT.name());
		 createElement(doc, rootElement, "dateTime", String.valueOf(msg.getInsertDate().getTime()));
		 
		 try {
			writeXml(doc,ous);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static Step getStep(Document doc) {
		String val = getTagContent(doc, "step");
		return Step.get(val);
	}

	public static String getToken(Document doc) {
		return getTagContent(doc, "token");
	}
	
	public static ChatMessage getMessage(Document doc) {
		ChatMessage msg = new ChatMessage();
		
		NodeList list = doc.getElementsByTagName("from");
		ChatUser f = geChatUser(list);
		msg.setFrom(f);
		
		list = doc.getElementsByTagName("to");
		ChatUser u = geChatUser(list);
		msg.setTo(u);
		
		list = doc.getElementsByTagName("file");
		Node node = list.item(0);
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String id = getTagContent(element, "id");
			String name = getTagContent(element, "name");
			String ext = getTagContent(element, "extension");
			String size = getTagContent(element, "size");
			String base64 = getTagContent(element, "base64");
			TFileEntity tfe = new TFileEntity();
			if(id!=null && !"".equals(id))
				tfe.setId(Long.valueOf(id));
			tfe.setFileName(name);
			tfe.setFileExtension(ext);
			tfe.setFileSize(Long.valueOf(size));
			
			TByteEntity tbe = new TByteEntity();
			tbe.setBytes(Base64.getDecoder().decode(base64));
			
			tfe.setByteEntity(tbe);
			msg.setFile(tfe);
		}
		String status = getTagContent(doc, "status");
		String dateTime = getTagContent(doc, "dateTime");

		msg.setStatus(TStatus.get(status));
		msg.setInsertDate(new Date(Long.valueOf(dateTime)));
		
		return msg;
	}

	/**
	 * @param list
	 * @return
	 */
	private static ChatUser geChatUser(NodeList list) {
		Node node = list.item(0);
		ChatUser u = new ChatUser();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			String id = getTagContent(element, "id");
			String name = getTagContent(element, "name");
			u.setId(Long.valueOf(id));
			u.setName(name);
		}
		return u;
	}

	/**
	 * @param element
	 * @param tag
	 * @return
	 */
	private static String getTagContent(Element element, String tag) {
		return element.getElementsByTagName(tag).item(0).getTextContent();
	}

	/**
	 * @param doc
	 * @param tag
	 * @return
	 */
	private static String getTagContent(Document doc, String tag) {
		try {
			String value = doc.getElementsByTagName(tag).item(0).getTextContent();
			return value;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param doc
	 * @param rootElement
	 * @param s
	 */
	private static void createStepTag(Document doc, Element rootElement, Step s) {
		String tag = "step";
		String val = s.name();
		createElement(doc, rootElement, tag, val);
	}

	/**
	 * @param user
	 * @param doc
	 * @param rootElement
	 */
	private static void createTokenTag(ChatUser user, Document doc, Element rootElement) {
		String tag = "token";
		String val = user.getToken().getToken();
		createCDataElement(doc, rootElement, tag, val);
	}

	/**
	 * @param doc
	 * @param targetElement
	 * @param tag
	 * @param val
	 */
	private static void createElement(Document doc, Element targetElement, String tag, String val) {
		Element el = doc.createElement(tag);
		el.setTextContent(val);
		targetElement.appendChild(el);
	}

	/**
	 * @param doc
	 * @param rootElement
	 * @param tag
	 * @param val
	 */
	private static void createCDataElement(Document doc, Element rootElement, String tag, String val) {
		Element el = doc.createElement(tag);
		rootElement.appendChild(el);
		CDATASection cdataSection = doc.createCDATASection(val);
		el.appendChild(cdataSection);
	}

	/**
	 * @return
	 * @throws ParserConfigurationException
	 */
	private static Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		 
		 Document doc = docBuilder.newDocument();
		return doc;
	}
	
	/**
	 * @return
	 * @throws ParserConfigurationException
	 */
	private static DocumentBuilder buildDocumentBuilder() throws ParserConfigurationException {
		// Instantiate the Factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// optional, but recommended
		// process XML securely, avoid attacks like XML External Entities (XXE)
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		
		// parse XML file
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db;
	}
	
	
	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		// pretty print
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);
		
		transformer.transform(source, result);

	}	

}
