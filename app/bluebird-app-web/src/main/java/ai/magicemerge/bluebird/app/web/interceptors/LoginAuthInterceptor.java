package ai.magicemerge.bluebird.app.web.interceptors;

import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.service.common.UserToken;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.utils.CopyUtils;
import ai.magicemerge.bluebird.app.service.common.utils.JwtTokenUtil;
import ai.magicemerge.bluebird.app.service.common.utils.LocalAssert;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 系统端登录拦截
 */
@Slf4j
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//如果不是映射到方法，直接通过
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		//如果是方法探测，直接通过
		if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		//如果方法有JwtIgnore注解，直接通过
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if (method.isAnnotationPresent(JwtScope.class)) {
			JwtScope jwtScope = method.getAnnotation(JwtScope.class);
			if (!jwtScope.ignore()) {
				final String token = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
				final String workspaceId = request.getHeader(JwtTokenUtil.WORKSPACE_ID);
				Assert.notNull(workspaceId, "用户未登录");
				LocalAssert.isTokenBlank(token, "用户未登录");
				//验证，并获取token内部信息
				String userTokenStr = JwtTokenUtil.verifyToken(token);
				User user = JSONObject.parseObject(userTokenStr, User.class);
				UserToken userToken = CopyUtils.copy(user, UserToken.class);
				userToken.setUserId(user.getId());
				userToken.setUserRole(user.getUserRole());
				userToken.setWorkspaceId(Long.valueOf(workspaceId));
				log.info("用户token信息: {}", JSON.toJSONString(userToken));
				WebContextUtil.setUserToken(JSON.toJSONString(userToken));
			}
			return true;
		}
		return true;
	}


	@Override
	public void afterCompletion( HttpServletRequest request,  HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//方法结束后，移除缓存的token
//		WebContextUtil.removeUserToken();
	}
}
