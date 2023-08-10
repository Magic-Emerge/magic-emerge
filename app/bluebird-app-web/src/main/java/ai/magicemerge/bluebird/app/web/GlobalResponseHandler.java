package ai.magicemerge.bluebird.app.web;

import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.exceptions.AuthenticationException;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static ai.magicemerge.bluebird.app.service.common.constant.ResCode.INTERNAL_SERVICE_ERROR;


@Slf4j
@Configuration
@RestControllerAdvice(basePackages = {"ai.magicemerge.bluebird.app.web.controller"})
@ResponseStatus(HttpStatus.OK)
public class GlobalResponseHandler implements ResponseBodyAdvice<ApiResponse<Object>> {



	@ExceptionHandler({ RuntimeException.class, BusinessException.class })
	public ApiResponse<Object> handleBusinessException(BusinessException e) {
		return ApiResponse.error(e.getCode(), e.getMsg());
	}


	@ExceptionHandler({ NullPointerException.class })
	public ApiResponse<Object> handleNullPointerException(NullPointerException e) {
		return ApiResponse.error(e.getMessage());
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
		return ApiResponse.error(ResCode.FAILED.getCode(), e.getMessage());
	}


	@ExceptionHandler({ Exception.class })
	public ApiResponse<Object> handleNullPointerException(Exception e) {
		return ApiResponse.error(INTERNAL_SERVICE_ERROR.getCode(), INTERNAL_SERVICE_ERROR.getMsg());
	}



	@ExceptionHandler({ AuthenticationException.class })
	public ApiResponse<Object> handleLoginException(AuthenticationException e) {
		return ApiResponse.error(e.getCode(), e.getMsg());
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		return true;
	}


	@Override
	public ApiResponse<Object> beforeBodyWrite(ApiResponse<Object> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		return body;
	}
}
