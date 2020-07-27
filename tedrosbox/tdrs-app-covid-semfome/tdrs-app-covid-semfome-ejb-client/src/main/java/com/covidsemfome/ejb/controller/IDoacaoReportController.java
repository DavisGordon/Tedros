/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.report.model.DoacaoReportModel;
import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IDoacaoReportController extends ITEjbReportController<DoacaoReportModel> {

}
