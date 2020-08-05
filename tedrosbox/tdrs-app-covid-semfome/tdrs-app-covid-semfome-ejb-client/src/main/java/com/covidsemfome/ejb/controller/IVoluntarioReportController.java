/**
 * 
 */
package com.covidsemfome.ejb.controller;

import javax.ejb.Remote;

import com.covidsemfome.report.model.VoluntarioReportModel;
import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IVoluntarioReportController extends ITEjbReportController<VoluntarioReportModel> {

}
