package be.vmm.eenvplus.sdi.plugins.providers.jackson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.MediaTypeMap;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

/**
 * <p>
 * JSONP is an alternative to normal AJAX requests. Instead of using a
 * XMLHttpRequest a script tag is added to the DOM. The browser will call the
 * corresponding URL and download the JavaScript. The server creates a response
 * which looks like a method call. The parameter is the body of the request. The
 * name of the method to call is normally passed as query parameter. The method
 * has to be present in the current JavaScript environment.
 * </p>
 * <p>
 * Jackson JSON processor can produce such an response. This interceptor checks
 * if the media type is a JavaScript one if there is a query parameter with the
 * method name. The default name of this query parameter is "callback". So this
 * interceptor is compatible with <a
 * href="http://api.jquery.com/jQuery.ajax/">jQuery</a>.
 * </p>
 *
 * @author stefaan
 */
@Provider
@ServerInterceptor
@SuppressWarnings("deprecation")
public class JacksonJsonpInterceptor implements MessageBodyWriterInterceptor {

	/**
	 * "text/javascript" media type. Default media type of script tags.
	 */
	public static final MediaType TEXT_JAVASCRIPT_TYPE = new MediaType("text",
			"javascript");
	/**
	 * "application/javascript" media type.
	 */
	public static final MediaType APPLICATION_JAVASCRIPT_TYPE = new MediaType(
			"application", "javascript");
	/**
	 * "text/json" media type.
	 */
	public static final MediaType TEXT_JSON_TYPE = new MediaType("text", "json");
	/**
	 * "application/*+json" media type.
	 */
	public static final MediaType APPLICATION_PLUS_JSON_TYPE = new MediaType(
			"application", "*+json");

	/**
	 * The name of the query parameter with the method name.
	 */
	public static final String CALLBACK_QUERY_PARAMETER = "callback";

	/**
	 * If response media type is one of this jsonp response may be created.
	 */
	public static final MediaTypeMap<String> JSONP_COMPATIBLE_TYPES = new MediaTypeMap<String>();

	static {
		JSONP_COMPATIBLE_TYPES.add(TEXT_JAVASCRIPT_TYPE,
				TEXT_JAVASCRIPT_TYPE.toString());
		JSONP_COMPATIBLE_TYPES.add(APPLICATION_JAVASCRIPT_TYPE,
				APPLICATION_JAVASCRIPT_TYPE.toString());
		JSONP_COMPATIBLE_TYPES.add(TEXT_JSON_TYPE, TEXT_JSON_TYPE.toString());
		JSONP_COMPATIBLE_TYPES.add(MediaType.APPLICATION_JSON_TYPE,
				MediaType.APPLICATION_JSON_TYPE.toString());
		JSONP_COMPATIBLE_TYPES.add(APPLICATION_PLUS_JSON_TYPE,
				APPLICATION_PLUS_JSON_TYPE.toString());
	}

	private UriInfo uri;

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void write(MessageBodyWriterContext context) throws IOException,
			WebApplicationException {

		String callback = uri.getQueryParameters().getFirst(
				CALLBACK_QUERY_PARAMETER);
		if (callback != null
				&& !callback.trim().isEmpty()
				&& !JSONP_COMPATIBLE_TYPES.getPossible(context.getMediaType())
						.isEmpty()) {
			final OutputStream out = context.getOutputStream();
			context.setOutputStream(new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					out.write(b);
				}

				@Override
				public void flush() throws IOException {
					out.flush();
				}
			});
			Writer writer = new OutputStreamWriter(out);

			try {
				writer.write(callback + "(");
				writer.flush();
				context.proceed();
				writer.write(")");
				writer.flush();
			} finally {
				context.setOutputStream(out);
				out.close();
			}
		} else {
			context.proceed();
		}
	}

	/**
	 * Setter used by RESTeasy to provide the {@link UriInfo}.
	 * 
	 * @param uri
	 *            the URI to set
	 */
	@Context
	public void setUri(UriInfo uri) {
		this.uri = uri;
	}
}