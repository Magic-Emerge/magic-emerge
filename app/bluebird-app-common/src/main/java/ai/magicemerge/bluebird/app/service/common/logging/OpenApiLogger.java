package ai.magicemerge.bluebird.app.service.common.logging;

import okhttp3.logging.HttpLoggingInterceptor.Logger;
import org.slf4j.LoggerFactory;

public class OpenApiLogger implements Logger {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(OpenApiLogger.class);

	public OpenApiLogger() {
	}

	public void log(String message) {
		log.info("OkHttp-------->:{}", message);
	}
}
