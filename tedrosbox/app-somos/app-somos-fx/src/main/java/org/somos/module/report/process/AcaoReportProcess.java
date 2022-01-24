package org.somos.module.report.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.somos.report.model.AcaoReportModel;

import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;

public class AcaoReportProcess extends TReportProcess<AcaoReportModel> {

	public AcaoReportProcess() throws TProcessException {
		super("IAcaoReportControllerRemote", "CSF Acao");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream logoIs = getClass().getResourceAsStream("logo.png");
		params.put("logo", logoIs);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		InputStream inputStream = getClass().getResourceAsStream("acoes.jasper");
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
