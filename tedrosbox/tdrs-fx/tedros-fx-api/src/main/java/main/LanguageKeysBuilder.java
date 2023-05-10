package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class LanguageKeysBuilder {

	public LanguageKeysBuilder() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			
				System.out.println("Enter the resource name without locale: ");
				String s = br.readLine();
				if(StringUtils.isBlank(s)) {
					System.out.println("A non empty value is required! ");
					return;
				}
				
				try {
					ResourceBundle r = ResourceBundle.getBundle(s, Locale.ENGLISH);
					r.keySet().stream().sorted((a,b)->{
						return a.compareToIgnoreCase(b);
					}).forEach(k->{
						String c = fxcomp(k);
						System.out.println("static final String "+c+" = \"#{"+k+"}\";");
					});
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param k
	 * @return
	 */
	public static String fxcomp(String k) {
		String c = k.replaceAll("\\.", "_")
				.replaceAll("-", "_").toUpperCase()
				.replaceFirst("TEDROS_FXAPI_", "")
				.replaceFirst("TEDROS_", "")
				.replaceFirst("LABEL_", "");
		return c;
	}

}
