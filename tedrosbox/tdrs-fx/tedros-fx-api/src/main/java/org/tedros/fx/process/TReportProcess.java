/**
 * 
 */
package org.tedros.fx.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.exception.TProcessException;
import org.tedros.server.controller.ITEjbReportController;
import org.tedros.server.model.ITReportModel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TedrosFolder;

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

	protected static final String PARAM_SUBREPORT_DIR = "SUBREPORT_DIR";

	protected static final String PARAM_LOGO = "logo";

	protected static final String REPORT_ORG = "report_org";

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private M model;
	private TReportProcessEnum action;
	private String reportName;
	private String folderPath;
	private String serviceJndiName;
	
	private String organization;
	private String subReportDir;
	private InputStream logoInputStream;
	
	
	public TReportProcess(String serviceJndiName, String reportName) throws TProcessException {
		setAutoStart(true);
		this.reportName = reportName;
		this.serviceJndiName = serviceJndiName;
		LOGGER.setLevel(Level.ALL);
	}
	
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @param logoInputStream the logoInputStream to set
	 */
	public void setLogoInputStream(InputStream logoInputStream) {
		this.logoInputStream = logoInputStream;
	}

	/**
	 * @param subReportDir the subReportDir to set
	 */
	public void setSubReportDir(String subReportDir) {
		this.subReportDir = subReportDir;
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
				: TedrosFolder.EXPORT_FOLDER.getFullPath();
	}
	
	public void exportXLS(M model, String folderPath){
		this.model = model;
		this.action = TReportProcessEnum.EXPORT_XLS;
		this.folderPath =  folderPath!=null 
				? folderPath 
				: TedrosFolder.EXPORT_FOLDER.getFullPath();
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
			addSubReportDirParam(params);
			addCustomLogoAndOrgParams(params);
			InputStream inputStream = getJasperInputStream();
			String f = getDestFile();
			List dataList = model.getResult();
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			JasperPrint print = JasperFillManager.fillReport(inputStream, params, beanColDataSource);
			JasperExportManager.exportReportToPdfFile(print, f);
			inputStream.close();
			if(this.logoInputStream!=null)
				this.logoInputStream.close();
			runAfterExport(params);
			return new TResult<>(TState.SUCCESS, f, model);
		}catch(Exception e){
			LOGGER.severe(e.toString());
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	protected TResult<M> runExportXls() throws JRException {
		try{
			Map<String, Object> params = getReportParameters();
			addSubReportDirParam(params);
			addCustomLogoAndOrgParams(params);
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
			if(this.logoInputStream!=null)
				this.logoInputStream.close();
			runAfterExport(params);
			return new TResult<>(TState.SUCCESS, f, model);
		}catch(Exception e){
			LOGGER.severe(e.toString());
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	/**
	 * @param params
	 */
	protected void addSubReportDirParam(Map<String, Object> params) {
		if(this.subReportDir!=null && !params.containsKey(PARAM_SUBREPORT_DIR))
			params.put(PARAM_SUBREPORT_DIR, this.subReportDir);
	}
	
	/**
	 * @param params
	 */
	protected void addCustomLogoAndOrgParams(Map<String, Object> params) {
		if(!params.containsKey(PARAM_LOGO)) {
			if(this.logoInputStream==null) {
				try {
					this.logoInputStream = new FileInputStream(new File(TedrosFolder.IMAGES_FOLDER.getFullPath()+"logo-tedros-medium.png"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			params.put(PARAM_LOGO, this.logoInputStream);
		}
		if(!params.containsKey(REPORT_ORG) && this.organization!=null) {
			params.put(REPORT_ORG, this.organization);
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
