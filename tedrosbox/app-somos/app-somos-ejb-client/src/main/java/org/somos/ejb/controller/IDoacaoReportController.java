/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.report.model.DoacaoReportModel;

import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IDoacaoReportController extends ITEjbReportController<DoacaoReportModel> {

}
