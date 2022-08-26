package main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLReader {
	
	
	public static void main(String[] args) {
		
		/*System.setProperty("http.proxyHost", "proxy.saude.gov");
		System.setProperty("http.proxyPort", "80");
		
		System.setProperty("htttps.proxyHost", "proxy.saude.gov");
		System.setProperty("https.proxyPort", "80");
		
		System.setProperty("socksProxyHost", "proxy.saude.gov");
		System.setProperty("socksProxyPort", "80");
		*/
		Document doc = null;
		String linkType = null;
		try {
			String link = "https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/SelectionModel.html";
			linkType = StringUtils.substringAfterLast(link, "/").replaceAll(".html", "");
			
			URL url = new URL(link);
			doc = Jsoup.parse(url, 10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Element content = doc.getElementsByTag("ul");
		Elements ulss = doc.getElementsByTag("li");
		
		List<String> types = new ArrayList<>();
		
		for (Element link : ulss) {
			
			Elements h4 = link.getElementsByTag("h4");
			if(h4.size()==1 && h4.text().length()>3 && h4.text().substring(0, 3).equals("set")){
				Elements pre = link.getElementsByTag("pre");
				Elements div = link.getElementsByTag("div");
				Elements dt = link.getElementsByTag("dt");
				//Elements dt_span = dt.getElementsByTag("span");
				Elements dd = link.getElementsByTag("dd");
				
				
				String methodName = h4.text();
				String propertyName = methodName.substring(3, methodName.length());
				propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1, propertyName.length());
				
				String methodAssign = pre.text();
				
				if(methodAssign.contains("protected"))
					continue;
				
				String parameter = StringUtils.substringBetween(methodAssign, "(", ")");
//				String desc1 = dt.text(); 
//				String desc2 = dd.text();
				String desc = StringUtils.substringAfter(link.text(), ")");
				
				String type = StringUtils.substringBetween(methodAssign, "(", ")"); 
				type = type.replaceAll("value", "").trim();
				
				String defaultValue = StringUtils.substringAfter(link.text(), "Default value:");
				String strDefault = (StringUtils.isNotBlank(defaultValue)) ? " default "+ defaultValue.trim() : "";
				
				String eventType = type.contains("EventHandler") ? StringUtils.substringBetween(type, "<", ">") : "";
				eventType = eventType.replaceAll("super", "");
				eventType = eventType.replaceAll("\\?", "");
				eventType = eventType.replaceAll(" ", "");
							
//				System.out.println("=======================");
//				System.out.println("methodName = " + methodName);
//				System.out.println("propertyName = " + propertyName);
				
				
				//System.out.println("parameter = " + parameter);
				
				//System.out.println("desc = " + desc);
				
//				System.out.println("__________________________");
//				System.out.println(link.text());
//				System.out.println("defaultValue = "+defaultValue);
//				System.out.println("__________________________");
				
				
				
				if(type.contains("EventHandler")){
					type = "Class<? extends ITEventHandlerBuilder<"+eventType.trim()+">> ";
					strDefault = " default ITEventHandlerBuilder.class";
				}
				
				System.out.println("/**");
				System.out.println("* <pre>");
				System.out.println("* {@link "+linkType+"} Class");
				System.out.println("* ");
				System.out.println("* "+desc);
				System.out.println("* </pre>");
				System.out.println("**/");
				System.out.println("public "+type.trim()+" "+propertyName.trim()+"()"+ strDefault+";");
				System.out.println("");
				
				
				/*if(!types.contains(type)){
					types.add(type);
					//System.out.println("methodAssign = " + methodAssign);
					System.out.println("type = " + type);
					
				}*/
				
				
			}
			
			
				
		}
	}
	
	
	/*public static void main(String[] args) {
		 
		URL url;
 
		try {
			// get URL content
			url = new URL("https://docs.oracle.com/javafx/2/api/javafx/scene/Node.html");
			URLConnection conn = url.openConnection();
 
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
 
			String inputLine;
 
			//save to this filename
			//String fileName = "/users/mkyong/test.html";
			//File file = new File(fileName);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			//use FileWriter to write file
			//FileWriter fw = new FileWriter(file.getAbsoluteFile());
			//BufferedWriter bw = new BufferedWriter(fw);
 
			while ((inputLine = br.readLine()) != null) {
				System.out.println(inputLine);
				//bw.write(inputLine);
			}
 
			//bw.close();
			br.close();
 
			System.out.println("Done");
 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}*/
}
