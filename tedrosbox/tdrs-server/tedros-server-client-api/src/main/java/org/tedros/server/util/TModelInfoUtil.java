/**
 * 
 */
package org.tedros.server.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.tedros.server.annotation.TField;
import org.tedros.server.annotation.TFileType;
import org.tedros.server.annotation.THeaderType;
import org.tedros.server.annotation.TImportInfo;
import org.tedros.server.annotation.TModelInfo;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class TModelInfoUtil {

	public static String getFieldsInfo(Class<? extends ITModel> modelType) throws Exception {
		
		TModelInfo info = modelType.getAnnotation(TModelInfo.class);
		
		List<Field> lst = getFields(modelType);
		
		if(info==null && lst.isEmpty())
			return "";
		
		StringBuilder sb2 = new StringBuilder();
		
		if(info!=null && StringUtils.isNotBlank(info.value())) {
			sb2.append("#{tedros.fxapi.model.responsable}")
			.append(": ").append(info.value()).append("\r\n");
		}
		
		if(lst.size()>0)
		sb2.append("#{tedros.fxapi.field.info.title}");
		for(Field f : lst) {
			TField fRule = f.getAnnotation(TField.class);
			String label = fRule.label();
			String column =  f.getName();
			String desc = fRule.description();
			String examp = fRule.example();
			String[] pVal = fRule.possibleValues();
			int maxLen = fRule.maxLength();
			boolean req = fRule.required();

			sb2.append("#{label.field}").append("='").append(column).append("'");
			if(StringUtils.isNotBlank(label))
				sb2.append(",").append("#{label.label}").append("='").append(label).append("'");
			if(StringUtils.isNotBlank(desc))
				sb2.append(",").append("#{label.description}").append("='").append(desc).append("'");
			
			if(StringUtils.isNotBlank(examp))
				sb2.append(",").append("#{label.example}").append("='").append(examp).append("'");
			
			if(maxLen>0)
				sb2.append(",").append("#{label.max.length}").append("='").append(maxLen).append("'");
			if(pVal.length>0)
				sb2.append(",").append("#{label.possible.values}").append("='").append(ArrayUtils.toString(pVal)).append("'");
			
			sb2.append(",").append("#{label.mandatory}").append("='").append(req).append("'");
			sb2.append("\r\n");
		}
		
		return sb2.toString();
	}

	@SuppressWarnings("rawtypes")
	private static List<Field> getFields(Class superClass){
		List<Field> lst = new ArrayList<>();
		while(superClass!=Object.class){
			for(Field f : superClass.getDeclaredFields())
				if(f.isAnnotationPresent(TField.class))
					lst.add(f);
			superClass = superClass.getSuperclass();
		}
		return lst;
	}
	
	public static String getImportRules(Class<? extends ITEntity> entityType) throws Exception {
		
		
		TImportInfo eRule = entityType.getAnnotation(TImportInfo.class);
		
		if(eRule==null)
			throw new RuntimeException("The entity "+entityType.getSimpleName()+ 
					" must be setting with @TImportInfo annotation "
					+ "and the desired fields with @TField");
		
		TFileType[] types = eRule.fileType();
		int totalCols = eRule.totalColumn();
		String sheetName = eRule.xlsSheetName();
		THeaderType header = eRule.header();
		boolean xls = false;
		boolean csv = false;
		StringBuilder sb = new StringBuilder();
		sb.append("<b>");
		sb.append(eRule.description());
		sb.append("</b><br>");
		 
		if(types!=null) {
			sb.append("<b>");
			sb.append("Acceptable file types: ");
			sb.append("</b>");
			for(TFileType t : types) {
				sb.append(t.name()+" ");
				if(t.equals(TFileType.XLS))
					xls = true;
				if(t.equals(TFileType.CSV))
					csv = true;
			}
			sb.append("<br>");
		}
		
		if(totalCols>0) {
			sb.append("<b>");
			sb.append("Total columns: ");
			sb.append("</b>");
			sb.append(totalCols);
			sb.append("<br>");
		}
		if(StringUtils.isNotBlank(sheetName)) {
			sb.append("<b>");
			sb.append("XLS sheet name: ");
			sb.append("</b>");
			sb.append(sheetName);
			sb.append("<br>");
		}
		
		sb.append("<b>");
		sb.append("Column Header required at first row: ");
		sb.append("</b>");
		sb.append(!header.equals(THeaderType.COLUMN_INDEX));
		sb.append("<br>");
		
		sb.append("<hr>");
		
		List<Field> lst = new ArrayList<>();
		for(Field f : entityType.getDeclaredFields())
			if(f.isAnnotationPresent(TField.class))
				lst.add(f);
		
		if(lst.isEmpty())
			throw new RuntimeException("The entity "+entityType.getSimpleName()+ 
					" have none fields with @TField");
		
		StringBuilder sb2 = new StringBuilder();
		if(xls) {
			sb2.append("<style>");
			sb2.append("table { border-collapse: collapse;}");
			sb2.append("table, th, td { border: 1px solid black;}");
			sb2.append("</style>");
			sb2.append("<h2>Excel file exampe:</h2>");
			sb2.append("<table>");
			if(!header.equals(THeaderType.COLUMN_INDEX)) {
				sb2.append("<tr>");
				for(Field f : lst) {
					TField fRule = f.getAnnotation(TField.class);
					String column = (header.equals(THeaderType.COLUMN_NAME)) 
							? StringUtils.isNotBlank(fRule.column()) ? fRule.column() : f.getName()
									: f.getName();
					sb2.append("<th>"+column+"</th>");
				}
				sb2.append("</tr>");
			}
			sb2.append("<tr>");
			for(Field f : lst) {
				TField fRule = f.getAnnotation(TField.class);
				
				String label = fRule.label();
				String cDtEx = fRule.example();
				
				sb2.append("<td>");
				if(StringUtils.isNotBlank(cDtEx))
					sb2.append(cDtEx);
				else
					sb2.append(label);
				sb2.append("</td>");
			}
			sb2.append("</tr>");
			sb2.append("</table>");
			sb2.append("<hr>");
		}
	
		if(csv) {
			sb2.append("<h2>Csv file exampe:</h2>");
			if(!header.equals(THeaderType.COLUMN_INDEX)) {
				for(Field f : lst) {
					TField fRule = f.getAnnotation(TField.class);
					
					String column = (header.equals(THeaderType.COLUMN_NAME)) 
							? StringUtils.isNotBlank(fRule.column()) ? fRule.column() : f.getName()
									: f.getName();
					sb2.append("<i>"+column+"</i><b>;</b>");
				}
				sb2.append("<br>");
			}
			for(Field f : lst) {
				TField fRule = f.getAnnotation(TField.class);
				
				String label = fRule.label();
				String cDtEx = fRule.example();
				
				if(StringUtils.isNotBlank(cDtEx))
					sb2.append(cDtEx);
				else
					sb2.append(label);
				
				sb2.append("<b>;</b>");
			}
			sb2.append("<hr>");
		}
		
		int index = 0;
		sb2.append("<b>The file columns definition:</b><br><br>");
		for(Field f : lst) {
			TField fRule = f.getAnnotation(TField.class);
			String label = fRule.label();
			String column = StringUtils.isNotBlank(fRule.column()) ? fRule.column() : f.getName();
			String cDtEx = fRule.example();
			String[] pVal = fRule.possibleValues();
			int maxLen = fRule.maxLength();
			boolean req = fRule.required();
	
			sb2.append("[<b>"+label+"</b>]<br>");
			
			if(header.equals(THeaderType.COLUMN_INDEX))
				sb2.append("Column index: "+(NumberUtils.isCreatable(column) 
						? column 
								: index)+"<br>");
			else
				sb2.append("Column Header Name: "+(header.equals(THeaderType.COLUMN_NAME)
						? StringUtils.isNotBlank(fRule.column()) ? fRule.column() : f.getName()
								: f.getName())+"<br>");
			
			if(StringUtils.isNotBlank(cDtEx))
				sb2.append("Example: "+cDtEx+"<br>");
			if(maxLen>0)
				sb2.append("Max length: "+maxLen+"<br>");
			if(pVal.length>0)
				sb2.append("Possible values: "+ArrayUtils.toString(pVal)+"<br>");
			sb2.append("Required: "+req+"<br>");
			sb2.append("<hr>");
			index++;
		}
		
		sb.append(sb2);
		return sb.toString();
	}

}
