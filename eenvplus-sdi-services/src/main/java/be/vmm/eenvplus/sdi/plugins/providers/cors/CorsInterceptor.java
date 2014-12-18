package be.vmm.eenvplus.sdi.plugins.providers.cors;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

/**
 * This class is a Cross Origin Resource Sharing implementation,<br>
 * it adds to the headers map a set of predefined headers to allow to the client
 * that CORS mechanism come true. For more details see <a
 * href="http://www.w3.org/TR/access-control/">CORS specification</a>.
 */
@Provider
@ServerInterceptor
@SuppressWarnings("deprecation")
public class CorsInterceptor implements MessageBodyWriterInterceptor {

	/**
	 * The Origin header set by the browser at each request.
	 */
	protected static final String ORIGIN = "Origin";
	/**
	 * The Access-Control-Allow-Origin header indicates which origin a resource
	 * it is specified for can be shared with. ABNF: Access-Control-Allow-Origin
	 * = "Access-Control-Allow-Origin" ":" source origin string | "*"
	 */
	protected static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	/**
	 * The Access-Control-Allow-Methods header indicates, as part of the
	 * response to a preflight request, which methods can be used during the
	 * actual request. ABNF: Access-Control-Allow-Methods:
	 * "Access-Control-Allow-Methods" ":" #Method
	 */
	protected static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	/**
	 * The Access-Control-Allow-Headers header indicates, as part of the
	 * response to a preflight request, which header field names can be used
	 * during the actual request. ABNF: Access-Control-Allow-Headers:
	 * "Access-Control-Allow-Headers" ":" #field-name
	 */
	protected static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	/**
	 * The Access-Control-Max-Age header indicates how long the results of a
	 * preflight request can be cached in a preflight result cache. ABNF:
	 * Access-Control-Max-Age = "Access-Control-Max-Age" ":" delta-seconds
	 */
	protected static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

	@Override
	public void write(MessageBodyWriterContext context) throws IOException,
			WebApplicationException {

		context.getHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		context.getHeaders().add(ACCESS_CONTROL_ALLOW_METHODS,
				"GET, POST, PUT, DELETE, OPTIONS, HEAD");
		context.getHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS,
				"Origin, X-Requested-With, Content-Type, Accept, Authorization");

		context.proceed();
	}
}