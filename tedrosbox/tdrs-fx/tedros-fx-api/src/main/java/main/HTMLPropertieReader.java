package main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLPropertieReader {
	
	
	public static void main(String[] args) {
		
		Document doc = null;
		String linkType = null;
		try {
			String link = "https://docs.oracle.com/javafx/2/api/javafx/scene/control/ScrollPane.html";
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
			if(h4.size()==1 && h4.text().length()>3 && h4.text().contains("Property")){
				Elements pre = link.getElementsByTag("pre");
				Elements div = link.getElementsByTag("div");
				Elements dt = link.getElementsByTag("dt");
				//Elements dt_span = dt.getElementsByTag("span");
				Elements dd = link.getElementsByTag("dd");
				
				
				String methodName = h4.text();
				String propertyName = methodName.substring(0, methodName.length());
				propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1, propertyName.length());
				
				String methodAssign = pre.text();
				
				if(methodAssign.contains("protected"))
					continue;
				
				String parameter = StringUtils.substringBetween(methodAssign, "(", ")");
//				String desc1 = dt.text(); 
//				String desc2 = dd.text();
				String desc = StringUtils.substringAfter(link.text(), ")");
				desc = (desc.contains("Default value")) ? StringUtils.substringBefore(desc, "Default value") : desc;
				desc = (desc.contains(". See")) ? StringUtils.substringBefore(desc, ". See") : desc;
				
					
				
				String type = (methodAssign.contains("final")) 
						? StringUtils.substringBetween(methodAssign.trim(), "final", propertyName)
								: StringUtils.substringBetween(methodAssign.trim(), "public", propertyName); 
				if(type!=null){
					type = StringUtils.substring(type,1,type.length());
					type = StringUtils.substring(type,0,type.length()-1);
					type = type.contains("<") ? StringUtils.substringBefore(type, "<") : type;
				}
				
				String defaultValue = StringUtils.substringAfter(link.text(), "Default value:");
				String strDefault = (StringUtils.isNotBlank(defaultValue)) ? " default "+ defaultValue.trim() : "";
				
				
							
				/*System.out.println("=======================");
				System.out.println("methodName = " + methodName);
				System.out.println("propertyName = " + propertyName);*/

				
				if(strDefault.contains("See")){
					strDefault = StringUtils.substringBefore(strDefault, "See");
				}
				
				System.out.println("/**");
				System.out.println("* <pre>");
				System.out.println("* {@link "+linkType+"} Class");
				System.out.println("* ");
				System.out.println("* "+desc);
				System.out.println("* </pre>");
				System.out.println("**/");
				System.out.println("public T"+type.trim()+" "+propertyName.trim()+"() default @T"+ type.trim() + ";");
				System.out.println("");
				
				
				
				
			}
			
			
				
		}
	}
	
}
