package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import be.vmm.eenvplus.sdi.api.ExceptionResult;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	public Response toResponse(Exception exception) {

		if (exception instanceof EJBException)
			exception = ((EJBException) exception).getCausedByException();

		return Response
				.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new ExceptionResult(exception.getClass().toString(),
						exception.getMessage())).build();
	}
}