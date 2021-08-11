package com.covidsemfome.module.report.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.covidsemfome.report.model.ProdutoReportModel;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;

public class ProdutoReportProcess extends TReportProcess<ProdutoReportModel> {

	public ProdutoReportProcess() throws TProcessException {
		super("IProdutoReportControllerRemote", "CSF Produtos");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream logoIs = getClass().getResourceAsStream("logo.png");
		params.put("logo", logoIs);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		InputStream inputStream = getClass().getResourceAsStream("produtos.jasper");
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
