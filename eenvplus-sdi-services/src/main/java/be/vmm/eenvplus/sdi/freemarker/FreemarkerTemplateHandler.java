package be.vmm.eenvplus.sdi.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@ApplicationScoped
public class FreemarkerTemplateHandler {

	protected Configuration configuration;

	public FreemarkerTemplateHandler() {
		configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
	}

	@SuppressWarnings("resource")
	public String evaluate(String uri, Locale locale, Map<String, Object> params)
			throws IOException {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		InputStream in = locale != null ? classLoader.getResourceAsStream(uri
				.replace(".fmt", "_" + locale.getLanguage() + ".fmt")) : null;
		if (in == null)
			in = classLoader.getResourceAsStream(uri);

		if (in == null)
			throw new FileNotFoundException(uri);

		Reader reader = new InputStreamReader(in);
		try {
			Template template = new Template(uri, reader, configuration);

			StringWriter writer = new StringWriter();
			BeansWrapper wrapper = new BeansWrapper();
			wrapper.setSimpleMapWrapper(false);

			Environment environment = template.createProcessingEnvironment(
					params, writer, wrapper);
			environment.process();

			return writer.toString();
		} catch (TemplateException e) {
			throw new IOException(e);
		} finally {
			reader.close();
		}
	}
}
