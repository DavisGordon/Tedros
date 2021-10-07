/**
 * 
 */
package com.covidsemfome.rest.service;

import java.io.IOException;
import java.util.Base64;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.covidsemfome.ejb.controller.ITFileEntityController;
import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.controller.TFileEntityController;
import com.tedros.ejb.base.result.TResult;

import br.com.covidsemfome.bean.AppBean;
import br.com.covidsemfome.producer.Item;

/**
 * @author Davis Gordon
 *
 */
@Path("/f")
@RequestScoped
public class FileApi {

	@Inject
	@Named("errorMsg")
	private Item<String> error;
	
	@Inject
	private AppBean appBean;
	
	@EJB
	private TFileEntityController serv;
	
	@GET
    @Path( "/i/{id}" )
   // @Produces( MediaType.APPLICATION_OCTET_STREAM )
    public Response download( @PathParam( "id" ) final Long id )
    {
        try {
        	TFileEntity e = new TFileEntity();
        	e.setId(id);
            TResult<TFileEntity> res = serv.findByIdWithBytesLoaded(appBean.getToken(), e);
            e = res.getValue();
            // encode to base64
            byte[] data = e.getByteEntity().getBytes(); //Base64.getEncoder().encode(e.getByteEntity().getBytes());
            
            // prepare response
            return Response
                    .ok( data, "image/"+e.getFileExtension() )
                    .header( "Content-Disposition","inline; filename = \"" + e.getFileName() + "\"" )
                    .header("Content-Length", e.getFileSize())
                    .build();
        }
        catch ( Exception e ) {
            return Response.status( Response.Status.NOT_FOUND ).entity( e.getMessage() ).build();
        }
    }
	
}
