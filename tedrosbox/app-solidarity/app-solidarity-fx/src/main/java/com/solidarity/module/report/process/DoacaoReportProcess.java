package com.solidarity.module.report.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.solidarity.report.model.DoacaoReportModel;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;

public class DoacaoReportProcess extends TReportProcess<DoacaoReportModel> {

	public DoacaoReportProcess() throws TProcessException {
		super("IDoacaoReportControllerRemote", "CSFDoacao");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream logoIs = getClass().getResourceAsStream("logo.png");
		params.put("logo", logoIs);
		params.put("totalQtd", getModel().getTotalQuantidade());
		params.put("totalValor", getModel().getTotalValor());
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		InputStream inputStream = getClass().getResourceAsStream("doacoes.jasper");
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
