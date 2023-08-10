package ai.magicemerge.bluebird.app.client;

import ai.magicemerge.bluebird.app.service.common.ChatCompletion;
import ai.magicemerge.bluebird.app.service.common.enums.ResponseModeEnum;
import ai.magicemerge.bluebird.app.service.common.exceptions.BaseException;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OpenApiStreamClient {
	/**
	 * 自定义的okHttpClient
	 * 如果不自定义 ，就是用sdk默认的OkHttpClient实例
	 */
	@Getter
	private final OkHttpClient okHttpClient;


	@Getter
	private DifyAuthInterceptor authInterceptor;


	public OpenApiStreamClient(String appKey) {
		this.okHttpClient = okHttpClient(appKey);
	}

	public OpenApiStreamClient(OkHttpClient okHttpClient, String appKey) {
		if (Objects.nonNull(okHttpClient)) {
			this.okHttpClient = okHttpClient;
		} else {
			this.okHttpClient = okHttpClient(appKey);
		}
	}


	/**
	 * 创建默认的OkHttpClient
	 */
	private OkHttpClient okHttpClient(String appKey) {
		if (Objects.isNull(this.authInterceptor)) {
			this.authInterceptor = new DefaultDifyAuthInterceptor();
			this.authInterceptor.setAppKey(appKey);
		}
		OkHttpClient okHttpClient = new OkHttpClient
				.Builder()
				.addInterceptor(authInterceptor)
				.connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(50, TimeUnit.SECONDS)
				.readTimeout(50, TimeUnit.SECONDS)
				.build();
		return okHttpClient;
	}


	public void streamChatCompletion(ChatCompletion chatCompletion, EventSourceListener eventSourceListener) {
		if (Objects.isNull(eventSourceListener)) {
			log.error("参数异常：EventSourceListener不能为空");
			throw new BaseException("参数异常");
		}
		if (StringUtils.isBlank(chatCompletion.getResponse_mode())
		   || StringUtils.equalsIgnoreCase(chatCompletion.getResponse_mode(), ResponseModeEnum.BLOCKING.name()
				.toLowerCase())) {
			chatCompletion.setResponse_mode(ResponseModeEnum.STREAMING.name().toLowerCase());
		}
		try {
			EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);
			String requestBody = JSON.toJSONString(chatCompletion);
			Request request = new Request.Builder()
					.url(chatCompletion.getApiUrl())
					.post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), requestBody))
					.build();
			//创建事件
			EventSource eventSource = factory.newEventSource(request, eventSourceListener);
		} catch (Exception e) {
			log.error("请求参数解析异常：{}", ExceptionUtils.getMessage(e));
			e.printStackTrace();
		}
	}









}
