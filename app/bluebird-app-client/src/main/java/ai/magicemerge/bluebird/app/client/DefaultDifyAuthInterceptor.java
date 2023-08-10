package ai.magicemerge.bluebird.app.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class DefaultDifyAuthInterceptor extends DifyAuthInterceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();
		return chain.proceed(auth(super.getKey(), original));
	}

	@Override
	protected void noHaveActiveKeyWarring() {
		log.error("--------> [告警]  key 失效！！！");
		return;
	}
}
