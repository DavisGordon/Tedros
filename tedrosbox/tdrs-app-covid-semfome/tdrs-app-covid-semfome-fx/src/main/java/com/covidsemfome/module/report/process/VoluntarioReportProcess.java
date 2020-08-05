package com.covidsemfome.module.report.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.covidsemfome.report.model.VoluntarioReportModel;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;

public class VoluntarioReportProcess extends TReportProcess<VoluntarioReportModel> {

	public VoluntarioReportProcess() throws TProcessException {
		super("IVoluntarioReportControllerRemote", "CSF Voluntarios");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream logoIs = getClass().getResourceAsStream("logo.png");
		params.put("logo", logoIs);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		InputStream inputStream = getClass().getResourceAsStream("voluntarios.jasper");
		return inputStream;
	}

	@Override
	protected void runAfterExport(Map<String, Object> params) {
		try {
			((InputStream)params.get("logo")).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
