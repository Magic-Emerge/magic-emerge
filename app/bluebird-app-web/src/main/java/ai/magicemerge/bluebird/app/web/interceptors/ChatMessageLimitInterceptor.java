package ai.magicemerge.bluebird.app.web.interceptors;

import ai.magicemerge.bluebird.app.dal.model.Record;
import ai.magicemerge.bluebird.app.service.RecordService;
import ai.magicemerge.bluebird.app.service.common.UserToken;
import ai.magicemerge.bluebird.app.service.common.annotions.RateLimit;
import ai.magicemerge.bluebird.app.service.common.exceptions.AuthenticationException;
import ai.magicemerge.bluebird.app.service.common.utils.AuthUtils;
import ai.magicemerge.bluebird.app.service.common.utils.WebContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Component
public class ChatMessageLimitInterceptor implements HandlerInterceptor {


	@Autowired
	private RecordService recordService;


	@Override
	public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
		//如果不是映射到方法，直接通过
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		//如果是方法探测，直接通过
		if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
		// 超级管理员/管理员跳过
		if (AuthUtils.isSuperAdmin() || AuthUtils.isAdmin()) {
			return true;
		}
		//如果方法有JwtIgnore注解，直接通过
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if (method.isAnnotationPresent(RateLimit.class)) {
			UserToken userSession = WebContextUtil.getUserSession();
			if (Objects.isNull(userSession)) {
				throw new AuthenticationException("用户未登录");
			}
			String userId = userSession.getUserId();
			if (StringUtils.isBlank(userId)) {
				throw new AuthenticationException("用户未登录");
			}
			Record msgCountByUserId = recordService.getMsgCountByUserId(userId);
			if (Objects.nonNull(msgCountByUserId)) {
				if (0 == msgCountByUserId.getMessageCount()) {
					log.info("体验次数已经用完, {}, {}", userId, userSession.getUsername());
					return false;
				}
			}
		}
		return true;

	}
}
