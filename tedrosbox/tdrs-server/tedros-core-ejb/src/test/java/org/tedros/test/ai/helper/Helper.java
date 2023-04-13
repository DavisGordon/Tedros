/**
 * 
 */
package org.tedros.test.ai.helper;

import org.tedros.stock.entity.EntryType;
import org.tedros.test.ai.model.EntryTypeJson;

/**
 * @author Davis Gordon
 *
 */
public class Helper {
	
	public static void testWriteJson() {
		EntryTypeJson obj = new EntryTypeJson();
		obj.setModel("insert");
		obj.addData(new EntryType());
		String json = JsonHelper.write(obj);
	}

	public static void testReadJson() {
		String m = "{\r\n" + 
				"  \"model\": \"insert\",\r\n" + 
				"  \"data\": [\r\n" + 
				"    { \"name\": \"UN\", \"description\": \"Unidade\" },\r\n" + 
				"    { \"name\": \"KG\", \"description\": \"Quilograma\" },\r\n" + 
				"    { \"name\": \"G\", \"description\": \"Grama\" },\r\n" + 
				"    { \"name\": \"L\", \"description\": \"Litro\" },\r\n" + 
				"    { \"name\": \"ML\", \"description\": \"Mililitro\" },\r\n" + 
				"    { \"name\": \"MT\", \"description\": \"Metro\" },\r\n" + 
				"    { \"name\": \"CM\", \"description\": \"Centímetro\" },\r\n" + 
				"    { \"name\": \"MM\", \"description\": \"Milímetro\" },\r\n" + 
				"    { \"name\": \"HR\", \"description\": \"Hora\" },\r\n" + 
				"    { \"name\": \"MIN\", \"description\": \"Minuto\" }\r\n" + 
				"  ]\r\n" + 
				"}";
		
		EntryTypeJson obj = JsonHelper.read(m, EntryTypeJson.class);
	}
}
