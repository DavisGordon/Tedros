package com.tedros.tools.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class CommonsLabelBuilder {

	public CommonsLabelBuilder() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ResourceBundle r = ResourceBundle.getBundle("TCoreLabels", Locale.ENGLISH);
		r.keySet().stream().sorted((a,b)->{
			return a.compareToIgnoreCase(b);
		}).forEach(k->{
			String c = fxcomp(k);
			System.out.println("static final String "+c+" = \"#{"+k+"}\";");
		});
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
