package com.tedros.tools.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class CommonsLabelBuilder {

	public CommonsLabelBuilder() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ResourceBundle r = ResourceBundle.getBundle("TToolsLabels", Locale.ENGLISH);
		r.keySet().stream().sorted((a,b)->{
			return a.compareToIgnoreCase(b);
		}).forEach(k->{
			String c = k.replaceAll("\\.", "_").toUpperCase().replaceFirst("LABEL_", "");
			System.out.println("static final String "+c+" = \"#{"+k+"}\";");
		});
	}

}
