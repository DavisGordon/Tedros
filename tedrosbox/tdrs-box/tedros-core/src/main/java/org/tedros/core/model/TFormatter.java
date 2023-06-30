/**
 * 
 */
package org.tedros.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.beans.value.ObservableValue;

/**
 * @author Davis Gordon
 *
 */
public class TFormatter {
	
	public class Item{
		public ObservableValue<?> ob;
		public String pattern;
		public Function<Object, String> convert;
	}
	
	public List<Item> items;
	/**
	 * 
	 */
	private TFormatter() {
		 items = new ArrayList<>();
	}
	
	public static TFormatter create() {
		return new TFormatter();
	}
	

	public TFormatter add(String pattern, ObservableValue<?> ob) {
		Item i = new Item();
		i.ob = ob;
		i.pattern = pattern;
		i.convert = null;
		items.add(i);
		return this;
	}
	
	public TFormatter add(ObservableValue<?> ob, Function<Object, String> convert) {
		Item i = new Item();
		i.ob = ob;
		i.pattern = null;
		i.convert = convert;
		items.add(i);
		return this;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		items.forEach(i->{
			Object v = i.ob.getValue();
			if(i.convert != null && v!=null)
				sb.append(i.convert.apply(v));
			else if(i.pattern!=null && v!=null)
				sb.append(String.format(i.pattern, v));
		});
		
		return sb.toString();
	}


}
