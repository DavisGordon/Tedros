/**
 * 
 */
package com.template.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbReportController;
import com.template.report.model.ProdutoReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoReportController extends ITEjbReportController<ProdutoReportModel> {

}
