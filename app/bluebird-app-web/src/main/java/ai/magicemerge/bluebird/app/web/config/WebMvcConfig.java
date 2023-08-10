package ai.magicemerge.bluebird.app.web.config;

import ai.magicemerge.bluebird.app.service.common.config.CommonProperties;
import ai.magicemerge.bluebird.app.service.config.StorageConfiguration;
import ai.magicemerge.bluebird.app.web.interceptors.ChatMessageLimitInterceptor;
import ai.magicemerge.bluebird.app.web.interceptors.LoginAuthInterceptor;
import ai.magicemerge.bluebird.app.web.interceptors.RequestLogInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * web mvc config
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private CommonProperties commonProperties;
	@Autowired
	private RequestLogInterceptor requestLogInterceptor;
	@Autowired
	private LoginAuthInterceptor loginAuthInterceptor;
	@Autowired
	private StorageConfiguration storageConfiguration;
	@Autowired
	private ChatMessageLimitInterceptor chatMessageLimitInterceptor;


	@Value(value = "${web.allowed.origins:*}")
	private String allowedOrigins;

	@Value(value = "${web.allow.credentials}")
	private Boolean allowCredentials;


	@Value(value = "${web.allow.methods}")
	private String[] allowMethods;

	@Value(value = "${web.allow.headers:*}")
	private String[] allowedHeaders;


	@Value(value = "${web.expose.headers}")
	private String[] exposeHeaders;

	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";


	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = converter.getObjectMapper();
		// 生成JSON时,将所有Long转换成String
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		// 时间格式化
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
		// 设置格式化内容
		converter.setObjectMapper(objectMapper);
		converters.add(0, converter);
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 添加映射路径
		registry.addMapping("/**")
				// 放行哪些原始域
				.allowedOrigins(allowedOrigins)
				// 是否发送Cookie信息
				.allowCredentials(true)
				// 放行哪些原始域(请求方式)
				.allowedMethods(allowMethods)
				// 放行哪些原始域(头部信息)
				.allowedHeaders(allowedHeaders)
				.allowCredentials(allowCredentials)
				// 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
				.exposedHeaders(exposeHeaders);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		requestLogInterceptor.setOpenAccessLog(commonProperties.getAccessLogOpen());
		registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
		registry.addInterceptor(loginAuthInterceptor)
				.addPathPatterns("/api/v1/**");
		registry.addInterceptor(chatMessageLimitInterceptor)
				.addPathPatterns("/api/v1/dify/**");
	}

	/**
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String customResourceLocation = "file:" + storageConfiguration.getUploadFilePath();
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/", "/resources/", "classpath:/static/",
						"classpath:/public/", customResourceLocation);
	}
}