package com.covidsemfome.module.report.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.covidsemfome.report.model.PessoaReportModel;
import com.tedros.fxapi.exception.TProcessException;
import com.tedros.fxapi.process.TReportProcess;
import com.tedros.fxapi.process.TReportProcessEnum;

public class PessoaReportProcess extends TReportProcess<PessoaReportModel> {

	public PessoaReportProcess() throws TProcessException {
		super("IPessoaReportControllerRemote", "CSF Pessoas");
	}
	
	protected HashMap<String, Object> getReportParameters() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		InputStream logoIs = getClass().getResourceAsStream("logo.png");
		params.put("logo", logoIs);
		return params;
	}
	
	protected InputStream getJasperInputStream() {
		InputStream inputStream = super.getModel().isDetalhado() 
				? this.getAction().equals(TReportProcessEnum.EXPORT_XLS) 
						? getClass().getResourceAsStream("voluntarios-cad-full-xls.jasper")
								: getClass().getResourceAsStream("voluntarios-cad-full.jasper")
				: this.getAction().equals(TReportProcessEnum.EXPORT_XLS) 
				? getClass().getResourceAsStream("voluntarios-cad-xls.jasper")
						: getClass().getResourceAsStream("voluntarios-cad.jasper");
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
