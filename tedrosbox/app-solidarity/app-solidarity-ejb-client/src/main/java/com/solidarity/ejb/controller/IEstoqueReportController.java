/**
 * 
 */
package com.solidarity.ejb.controller;

import javax.ejb.Remote;

import com.solidarity.report.model.EstoqueReportModel;
import com.tedros.ejb.base.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IEstoqueReportController extends ITEjbReportController<EstoqueReportModel> {

}
