package ai.magicemerge.bluebird.app.client;

import ai.magicemerge.bluebird.app.service.common.exceptions.BaseException;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

public abstract class DifyAuthInterceptor implements Interceptor {

	@Getter
	@Setter
	private String appKey;

	/**
	 * 所有的key都失效后，自定义预警配置
	 * 可以通过warringConfig配置参数实现飞书、钉钉、企业微信、邮箱预警等
	 */
	protected abstract void noHaveActiveKeyWarring();


	/**
	 * 获取请求key
	 *
	 * @return key
	 */
	public final String getKey() {
		if (StringUtils.isEmpty(appKey)) {
			this.noHaveActiveKeyWarring();
			throw new BaseException("无效的app key");
		}
		return this.appKey;
	}

	/**
	 * 默认的鉴权处理方法
	 *
	 * @param key      api key
	 * @param original 源请求体
	 * @return 请求体
	 */
	public Request auth(String key, Request original) {
		Request request = original.newBuilder()
				.header(Header.AUTHORIZATION.getValue(), "Bearer " + key)
				.header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
				.method(original.method(), original.body())
				.build();
		return request;
	}


}
