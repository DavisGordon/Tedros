package org.tedros.fx.control.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.tedros.core.resource.TedrosCoreResource;
import org.tedros.fx.control.model.TImagesReportModel;
import org.tedros.fx.exception.TProcessException;
import org.tedros.fx.process.TReportProcess;
import org.tedros.util.TLoggerUtil;

public class TImageReportProcess extends TReportProcess<TImagesReportModel> {

	public TImageReportProcess() throws TProcessException {
		super(null, "images");
	}
	

	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("SUBREPORT_DIR", TedrosCoreResource.APP_MODULE_PATH);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		try {
			return TedrosCoreResource.getImagesJasperInputStream();
		} catch (FileNotFoundException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		TImagesReportModel m = super.getModel();
		if(m.getResult()!=null)
			m.getResult().forEach(e->{
				if(e.getValues()!=null)
					e.getValues().forEach(i->{
						try {
							i.getValue().close();;
						} catch (IOException e1) {
							TLoggerUtil.error(getClass(), e1.getMessage(), e1);
						}
					});
			});
	}

}
