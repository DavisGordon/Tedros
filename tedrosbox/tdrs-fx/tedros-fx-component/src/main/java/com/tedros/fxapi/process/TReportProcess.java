/**
 * 
 */
package com.tedros.fxapi.process;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.tedros.core.context.TedrosContext;
import com.tedros.core.security.model.TUser;
import com.tedros.core.service.remote.ServiceLocator;
import com.tedros.ejb.base.controller.ITEjbReportController;
import com.tedros.ejb.base.model.ITReportModel;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.util.TedrosFolderEnum;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public abstract class TReportProcess<M extends ITReportModel> extends TProcess<TResult<M>> {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private M model;
	private TReportProcessEnum action;
	private String reportName;
	private String folderPath;
	private String serviceJndiName;
	
	
	public TReportProcess(String serviceJndiName, String reportName) throws TProcessException {
		setAutoStart(true);
		this.reportName = reportName;
		this.serviceJndiName = serviceJndiName;
		LOGGER.setLevel(Level.ALL);
	}
	
	public void search(M model){
		this.model = model;
		this.action = TReportProcessEnum.SEARCH;
	}
	
	public void exportPDF(M model, String folderPath){
		this.model = model;
		this.action = TReportProcessEnum.EXPORT_PDF;
		this.folderPath =  folderPath!=null 
				? folderPath 
				: TedrosFolderEnum.EXPORT_FOLDER.getFullPath();
	}
	
	public void exportXLS(M model, String folderPath){
		this.model = model;
		this.action = TReportProcessEnum.EXPORT_XLS;
		this.folderPath =  folderPath!=null 
				? folderPath 
				: TedrosFolderEnum.EXPORT_FOLDER.getFullPath();
	}
	
	protected TTaskImpl<TResult<M>> createTask() {
		
		return new TTaskImpl<TResult<M>>() {
        	
        	@Override
			public String getServiceNameInfo() {
				return getProcessName();
			};
        	
			@SuppressWarnings("unchecked")
			protected TResult<M> call() throws IOException, MalformedURLException {
        		
        		TResult<M> resultado = null;
        		try {
        			switch(action) {
        			case SEARCH:
        				ServiceLocator loc = ServiceLocator.getInstance();
        				try {
        					TUser user = TedrosContext.getLoggedUser();
	        				ITEjbReportController<M> service = (ITEjbReportController<M>) loc.lookup(serviceJndiName);
	        				resultado = service.process(user.getAccessToken(), model);
        				} catch(NamingException e){
        	    			setException( new TProcessException(e, e.getMessage(), "The service is not available!"));
        	    			LOGGER.severe(e.toString());
        	    			e.printStackTrace();
        	    		}catch (Exception e) {
        					setException(e);
        					LOGGER.severe(e.toString());
        					e.printStackTrace();
        				}finally {
        					loc.close();
        				}
        				break;
        			case EXPORT_PDF:
        				resultado = runExportPdf();
        				break;
        			case EXPORT_XLS:
        				resultado = runExportXls();
        				break;
        			}
	    		}catch (Exception e) {
					setException(e);
					LOGGER.severe(e.toString());
					e.printStackTrace();
				} 
        	    return resultado;
        	}
		};
	}
	
	protected String getDestFile(){
		String pattern = "dd-MM-yyyy HH-mm-ss";
		DateFormat df = new SimpleDateFormat(pattern);
		String k = df.format(new Date());
		return folderPath + reportName+" "+ k +(action.equals(TReportProcessEnum.EXPORT_PDF) ? ".pdf" : ".xls");
	}

	protected TResult<M> runExportPdf() throws JRException {
		try{
			Map<String, Object> params = getReportParameters();
			InputStream inputStream = getJasperInputStream();
			String f = getDestFile();
			List dataList = model.getResult();
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			JasperPrint print = JasperFillManager.fillReport(inputStream, params, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(print, f);
			inputStream.close();
			runAfterExport(params);
			return new TResult<>(EnumResult.SUCESS, f, model);
		}catch(Exception e){
			LOGGER.severe(e.toString());
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}

	protected TResult<M> runExportXls() throws JRException {
		try{
			Map<String, Object> params = getReportParameters();
			InputStream inputStream = getJasperInputStream();
			String f = getDestFile();
			List dataList = model.getResult();
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
			inputStream.close();
			runAfterExport(params);
			return new TResult<>(EnumResult.SUCESS, f, model);
		}catch(Exception e){
			LOGGER.severe(e.toString());
			return new TResult<>(EnumResult.ERROR, e.getMessage());
		}
	}
	
	protected abstract void runAfterExport(Map<String, Object> params);

	/**
	 * @return
	 */
	protected abstract InputStream getJasperInputStream();

	/**
	 * @return
	 */
	protected abstract HashMap<String, Object> getReportParameters();

	/**
	 * @return the folderPath
	 */
	protected String getFolderPath() {
		return folderPath;
	}

	/**
	 * @param folderPath the folderPath to set
	 */
	protected void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	/**
	 * @return the model
	 */
	protected M getModel() {
		return model;
	}

	/**
	 * @return the action
	 */
	protected TReportProcessEnum getAction() {
		return action;
	}

	
}
