/**
 * 
 */
package com.tedros.ejb.base.bo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.RequestScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public abstract class TImportFileEntityBO<E extends ITEntity>  {

	protected abstract Class<E> getEntityClass();
	
	protected abstract ITGenericBO<E> getBusinessObject();
	
	public abstract Class<? extends ITImportModel> getImportModelClass();
	
	public ITImportModel getImportRules() throws Exception {
		
		Class<E> clazz = this.getEntityClass();
		
		TEntityImportRule eRule = clazz.getAnnotation(TEntityImportRule.class);
		
		if(eRule==null)
			throw new RuntimeException("The entity "+clazz.getSimpleName()+ 
					" must be setting with @TEntityImportRule annotation "
					+ "and the desired fields with @TFieldImportRule");
		
		TFileType[] types = eRule.fileType();
		int totalCols = eRule.totalColumn();
		String sheetName = eRule.xlsSheetName();
		boolean forceH = eRule.forceFirstRowAsColumnNameHeader();
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
		sb.append(forceH);
		sb.append("<br>");
		
		sb.append("<hr>");
		
		List<TFieldImportRule> lst = new ArrayList<>();
		for(Field f : clazz.getDeclaredFields()) {
			TFieldImportRule fRule = f.getAnnotation(TFieldImportRule.class);
			if(fRule!=null) {
				lst.add(fRule);
			}
		}
		
		if(lst.isEmpty())
			throw new RuntimeException("The entity "+clazz.getSimpleName()+ 
					" have none fields with @TFieldImportRule");
		
		StringBuilder sb2 = new StringBuilder();
		if(xls) {
			sb2.append("<style>");
			sb2.append("table { border-collapse: collapse;}");
			sb2.append("table, th, td { border: 1px solid black;}");
			sb2.append("</style>");
			sb2.append("<h2>Excel file exampe:</h2><br>");
			sb2.append("<table>");
			if(forceH) {
				sb2.append("<tr>");
				for(TFieldImportRule fRule : lst) {
					String column = fRule.column();
					sb2.append("<th>"+column+"</th>");
				}
				sb2.append("</tr>");
			}
			sb2.append("<tr>");
			for(TFieldImportRule fRule : lst) {
				
				String desc = fRule.description();
				String cDtEx = fRule.columnDateExample();
				
				sb2.append("<td>"+desc);
				if(StringUtils.isNotBlank(cDtEx))
					sb2.append(" (example: "+cDtEx+")");
				sb2.append("</td>");
			}
			sb2.append("</tr>");
			sb2.append("</table>");
			sb2.append("<hr>");
		}
	
		if(csv) {
			sb2.append("<h2>Csv file exampe:</h2><br>");
			if(forceH) {
				for(TFieldImportRule fRule : lst) {
					String column = fRule.column();
					sb2.append("<i>"+column+"</i><b>;</b>");
				}
				sb2.append("<br>");
			}
			for(TFieldImportRule fRule : lst) {
				
				String desc = fRule.description();
				String cDtEx = fRule.columnDateExample();
				
				sb2.append(desc);
				if(StringUtils.isNotBlank(cDtEx))
					sb2.append(" (example: "+cDtEx+")");
				sb2.append("<b>;</b>");
			}
			sb2.append("<hr>");
		}
		
		
		sb2.append("<b>The file columns definition:</b><br><br>");
		for(TFieldImportRule fRule : lst) {
			
			String desc = fRule.description();
			String column = fRule.column();
			String cDtEx = fRule.columnDateExample();
			int maxLen = fRule.maxLength();
			boolean req = fRule.required();

			sb2.append("[<b>"+desc+"</b>]<br>");
			sb2.append("Column index/name: "+column+"<br>");
			if(StringUtils.isNotBlank(cDtEx))
				sb2.append("Date example: "+cDtEx+"<br>");
			if(maxLen>0)
				sb2.append("Max length: "+maxLen+"<br>");
			sb2.append("Required: "+req+"<br>");
			sb2.append("<hr>");
			
		}
		
		sb.append(sb2);
		
		ITImportModel m = this.getImportModelClass().newInstance();
		m.setRules(sb.toString());
		return m;
	}
	
	
	public List<E> importFile(final ITFileEntity entity) {

		Class<E> clazz = this.getEntityClass();
		
		TEntityImportRule eRule = clazz.getAnnotation(TEntityImportRule.class);
		
		if(eRule==null)
			throw new RuntimeException("The entity "+clazz.getSimpleName()+ 
					" must be setting with @TEntityImportRule annotation "
					+ "and the desired fields with @TFieldImportRule");
		
		TFileType[] types = eRule.fileType();
		int totalCols = eRule.totalColumn();
		String sheetName = eRule.xlsSheetName();
		boolean hasHeader = eRule.forceFirstRowAsColumnNameHeader();
		
		List<E> res = new ArrayList<>();
		
		String ext = entity.getFileExtension();
		ByteArrayInputStream is = new ByteArrayInputStream(entity.getByteEntity().getBytes());

		try {
			
			
			if(ext.toLowerCase().equals("xls") || ext.toLowerCase().equals("xlsx")) {
				 
				Workbook wb = WorkbookFactory.create(is);
				Sheet sht;
				if(StringUtils.isNotBlank(sheetName)) {
					sht = NumberUtils.isParsable(sheetName) 
							? wb.getSheetAt(Integer.valueOf(sheetName))
									: wb.getSheet(sheetName);
				}else
					sht = wb.getSheetAt(0);
				
				Map<Integer, Field> columns = new HashMap<>();
				List<Field> reqFields = new ArrayList<>();
				int index = 0;
				for(Field f : clazz.getDeclaredFields()) {
					
					TFieldImportRule fRule = f.getAnnotation(TFieldImportRule.class);
					
					if(fRule!=null) {
						
						if(fRule.required())
							reqFields.add(f);
						
						if(hasHeader) {
							String column = StringUtils.isBlank(fRule.column()) 
									?  f.getName()
											: fRule.column();
							sht.getRow(0).forEach(cell ->{
								if(column.toLowerCase().trim()
										.equals(cell.getStringCellValue().toLowerCase().trim())) {
									columns.put(cell.getColumnIndex(), f);
								}
						       
						    });
						}else {
							try {
								int column = StringUtils.isBlank(fRule.column()) 
										?  index
												: Integer.valueOf(fRule.column());
								columns.put(column, f);
							}catch(NumberFormatException e) {
								throw new RuntimeException("Cannot import the file, the field "+f.getName()+" in the entity "+clazz.getSimpleName()
								+" have the property column as "+fRule.column()+" in the TFieldImportRule but expected a number as the file index column."
										+ " If the file have a header column set to true the forceFirstRowAsColumnNameHeader property in the TEntityImportRule",e);
							}
						}
						index++;
					}
				}
				
				if(hasHeader)
					for(Field f : reqFields) {
						if(!columns.containsValue(f)) {
							TFieldImportRule r = f.getAnnotation(TFieldImportRule.class);
							throw new TBusinessException("Cannot import the file the required column "+r.column()+" not found!");
						}
					}
				
				sht.forEach(r -> {
					if(!hasHeader || (hasHeader && r.getRowNum()>0)) {
						try {
							
							E modelProcess = processXlsRow(r);
							
							if(modelProcess==null) {
								final E model = clazz.newInstance();
								
								columns.forEach((idx, f) -> {
									Cell cell = r.getCell(idx);
									TFieldImportRule rule = f.getAnnotation(TFieldImportRule.class);
									Class<? extends Number> numClass = rule.numberType();
									try {
										Method setter = clazz.getMethod("set"+StringUtils.capitalize(f.getName()), f.getType());
										
										if(isTypeOf(f.getType(), Number.class)) {
											
											String value = getCellDataAsString(cell);
											Object num = numClass == Number.class 
													? f.getType().getConstructor(String.class).newInstance(value)
															: numClass.getConstructor(String.class).newInstance(value);
											setter.invoke(model,  num);
											
										}else if(f.getType() == Date.class) {
											
											String value = getCellDataAsString(cell);
											String pattern = rule.datePattern();
											Date data = DateUtils.parseDate(value, pattern);
											setter.invoke(model, data);
											
										}else if(f.getType() == String.class) {
											String value = getCellDataAsString(cell);
											value = prepareNumberValue(numClass, value);
											setter.invoke(model, value);
										}else {
											throw new RuntimeException("Incompatible type");
										}
										
									} catch (NoSuchMethodException | SecurityException | 
											IllegalAccessException | IllegalArgumentException | 
											InvocationTargetException | InstantiationException e) {
										e.printStackTrace();
										throw new RuntimeException(e);
									} catch (ParseException e) {
										e.printStackTrace();
										throw new RuntimeException(e);
									} catch (Exception e) {
										e.printStackTrace();
										throw new RuntimeException(e);
									}
								});
								E saved = getBusinessObject().save(model);
								res.add(saved);
							}else {
								E saved = getBusinessObject().save(modelProcess);
								res.add(saved);
							}
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException(e);
						}
						
					}
				});
				
			}
		}catch(Exception e){
		    e.printStackTrace();
		    throw new RuntimeException(e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return res;
	
	}

	
	protected  String prepareNumberValue(Class<? extends Number> numClass, String value)
			throws RuntimeException {
		if(numClass!=Number.class && StringUtils.isNotBlank(value)) {
			if((numClass==Integer.class || numClass==Long.class || numClass==Short.class
					|| numClass==AtomicInteger.class || numClass==BigInteger.class) 
					&& (value.contains(".") || value.contains(","))) {
				if(value.contains(".") && !value.contains(","))
					value = StringUtils.substringBeforeLast(value, ".").replaceAll("\\.", "");
				else if(!value.contains(".") && value.contains(","))
					value = StringUtils.substringBeforeLast(value, ",").replaceAll(",", "");
				else if(value.contains(".") && value.contains(",")) {
					int d = value.lastIndexOf(".");
					int c = value.lastIndexOf(",");
					String t = (d>c) ? "." : ",";
					value = StringUtils.substringBeforeLast(value, t).replaceAll("\\.", "").replaceAll(",", "");
				}
			}else{
				if(value.contains(".") && !value.contains(",")) {
					int t  = StringUtils.countMatches(value, ".");
					if (t>1) 
						value = value.replaceAll("\\.", "");
				}else if(!value.contains(".") && value.contains(",")) {
					int t  = StringUtils.countMatches(value, ",");
					String r = (t>1) ? "" : ".";
					value = value.replaceAll(",", r);
				}else if(value.contains(".") && value.contains(",")) {
					int d = value.lastIndexOf(".");
					int c = value.lastIndexOf(",");
					if(d>c) 
						value = value.replaceAll(",", "");
					else
						value = value.replaceAll("\\.", "").replaceAll(",", ".");
				}
			}
			Number n;
			try {
				n = numClass.getConstructor(String.class).newInstance(value);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			value = n.toString();
		}
		return value;
	}
	
	protected E processXlsRow(Row r) {
		return null;
	}

	protected static boolean isTypeOf(Class<?> from, Class<?> type){
		while(from != type){
			if(from ==null || from == Object.class)
				return false;
			from = from.getSuperclass();
		}
		return true;
	}
	

	protected String getCellDataAsString(Cell cell){
		
	     switch(cell.getCellType()){
	        case STRING : return cell.getStringCellValue();
	        case NUMERIC : return String.valueOf(cell.getNumericCellValue());
	        case BOOLEAN :  return String.valueOf(cell.getBooleanCellValue());
	        default :return  "";
	    }
	}

}
