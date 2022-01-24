/**
 * 
 */
package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.report.model.AcaoReportModel;

import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAcaoReportController extends ITEjbReportController<AcaoReportModel> {

}
