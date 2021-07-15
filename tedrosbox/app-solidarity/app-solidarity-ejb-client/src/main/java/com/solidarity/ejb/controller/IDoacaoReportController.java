/**
 * 
 */
package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.report.model.DoacaoReportModel;
import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IDoacaoReportController extends ITEjbReportController<DoacaoReportModel> {

}
