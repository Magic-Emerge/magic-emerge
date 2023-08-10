package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.OkHttpResponse;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

	private static final OkHttpClient okHttpClient = new OkHttpClient
			.Builder()
			.addInterceptor(new LoggingInterceptor())
			.connectTimeout(5, TimeUnit.SECONDS)
			.readTimeout(120, TimeUnit.SECONDS)
			.build();

	private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpUtils.class);
	private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
	/**
	 * get 请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static OkHttpResponse get(String url, Headers headers) throws IOException {
		Request request = new Request
				.Builder()
				.headers(headers)
				.url(url)
				.get()
				.build();
		Response response = okHttpClient
				.newCall(request)
				.execute();
		ResponseBody responseBody = response
				.body();
		return new OkHttpResponse(response.code(), responseBody.string());
	}


	/**
	 * post请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String post(String url) throws IOException {
		return post(url, new HashMap<String, String>());
	}

	/**
	 * post请求
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> params) throws IOException {
		FormBody.Builder builder = new FormBody
				.Builder();
		for (String s : params.keySet()) {
			builder.add(s, params.get(s));
		}
		FormBody formBody = builder.build();
		Request request = new Request
				.Builder()
				.url(url)
				.post(formBody)
				.build();
		Response response = okHttpClient
				.newCall(request)
				.execute();
		ResponseBody responseBody = response
				.body();
		return responseBody.string();
	}

	/**
	 * post请求
	 *
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static OkHttpResponse post(String url, String json) throws IOException {
		RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
		Request request = new Request
				.Builder()
				.url(url)
				.post(requestBody)
				.build();
		Response response = okHttpClient
				.newCall(request)
				.execute();
		ResponseBody responseBody = response
				.body();
		return new OkHttpResponse(response.code(), responseBody.string());
	}


	/**
	 * post请求 + headers
	 *
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static OkHttpResponse post(String url, String json, Headers headers) throws IOException {
		RequestBody requestBody = RequestBody.create(JSON_TYPE, json);
		Request request = new Request
				.Builder()
				.headers(headers)
				.url(url)
				.post(requestBody)
				.build();
		Response response = okHttpClient
				.newCall(request)
				.execute();
		ResponseBody responseBody = response
				.body();
		return new OkHttpResponse(response.code(), responseBody.string());
	}



	/**
	 * post 异步请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static void postAsync(String url, Map<String, String> params) throws IOException {
		FormBody.Builder builder = new FormBody
				.Builder();
		for (String s : params.keySet()) {
			builder.add(s, params.get(s));
		}
		FormBody formBody = builder.build();
		final Request request = new Request
				.Builder()
				.url(url)
				.post(formBody)
				.build();
		okHttpClient.newCall(request)
				.enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						String msg = MessageFormat.format("异步post请求错误,url:{0},exception-message:{1}", call.request().url(), e.getMessage());
						LOGGER.error(msg, e);
					}

					@Override
					public void onResponse(Call call, Response response) throws IOException {
						String msg = MessageFormat.format("异步post请求成功,url{0},http-code:{1},返回内容:{2}", call.request().url(), response.code(), request.body());
						LOGGER.debug(msg);
						response.body().close();
					}
				});
	}

	/**
	 * 异步请求
	 * @param url
	 * @param params
	 */
	public static void asyncPost(String url, Map<String, String> params) {
		OkHttpClient client = null;
		try {
			client = getConnection();
			RequestBody formBody = getFormEncodingBuilder(params).build();
			final Request request = new Request.Builder().url(url).post(formBody).build();
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.info(String.valueOf(request));
					throw new RuntimeException(request.toString(), e);
				}
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					if (!response.isSuccessful()) {
						LOGGER.info(response.body().string());
					}
					response.body().close();
				}
			});
		}catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	//
	//
	/**
	 * psot方法访问https证书(以get的url方式提交参数)
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String postByHttps(String url) {
		String result = "";
		Long start = System.currentTimeMillis();
		Response response = null;
		try {
			OkHttpClient client = getConnection();
			RequestBody formBody = new FormBody.Builder().build();
			Request request = new Request.Builder().url(url).post(formBody).build();
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				result = response.body().string();
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (Exception e) {
			String msg = MessageFormat.format("请求异常,url:{0},params:-,reponse:{1}", url, result);
			throw new RuntimeException(msg, e);
		} finally {
			LOGGER.debug("此次调用时长为" + (System.currentTimeMillis() - start));
		}
		return result;
	}
	//
	/**
	 * post方法提供参数
	 *
	 * @param param
	 * @return
	 */
	public static FormBody.Builder getFormEncodingBuilder(Map<String, String> param) {
		FormBody.Builder formEncodingBuilder = new FormBody.Builder();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			formEncodingBuilder.add(entry.getKey(), entry.getValue());
		}
		return formEncodingBuilder;
	}

	/**
	 * 访问https证书验证
	 *
	 * @return
	 * @throws Exception
	 */
	public static OkHttpClient getConnection() throws Exception {
		SSLContext ctx = SSLContext.getInstance("SSL");
		ctx.init(new KeyManager[0], new TrustManager[]{new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

		}}, new SecureRandom());
		okHttpClient.sslSocketFactory();
		okHttpClient.hostnameVerifier();
		return okHttpClient;
	}
}
