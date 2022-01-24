package org.somos.module.report.process;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.somos.report.model.DoacaoItemModel;

import com.tedros.ejb.base.result.TResult;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		try{
			HashMap<String, Object> params = new HashMap<String, Object>();
			InputStream logoIs = Test.class.getResourceAsStream("logo.png");
			params.put("logo", logoIs);
			InputStream inputStream = Test.class.getResourceAsStream("doacoes.jasper");
			String f = "c:\\usr\\test.pdf";
			List dataList = Arrays.asList(new DoacaoItemModel(new Date(), f, 2L, null, f, f));
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			JasperPrint print = JasperFillManager.fillReport(inputStream, params, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(print, f);
			
			/*HashMap<String, Object> params = new HashMap<String, Object>();
			InputStream logoIs = Test.class.getResourceAsStream("logo.png");
			params.put("logo", logoIs);
			InputStream inputStream = Test.class.getResourceAsStream("doacoes.jasper");
			String f = "c:\\usr\\test.xls";
			List dataList = Arrays.asList(new DoacaoItemModel(new Date(), f, 2L, null, f, f));
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			JasperPrint print = JasperFillManager.fillReport(inputStream, params, beanColDataSource);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(f));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	//		configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			logoIs.close();
			inputStream.close();*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
