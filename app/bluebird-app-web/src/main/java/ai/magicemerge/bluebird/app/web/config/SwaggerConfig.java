package ai.magicemerge.bluebird.app.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Value("${spring.profiles.active:dev}")
	private String active;

//访问地址： http://localhost:8000/doc.html

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30)
				.enable("dev".equalsIgnoreCase(active))
				.apiInfo(apiInfo())
				.host("http://127.0.0.1")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("ai.magicemerge.bluebird.app.web.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("bluebird接口文档")
				.description("")
				.version("v1.0.0")
				.build();
	}
	
}
