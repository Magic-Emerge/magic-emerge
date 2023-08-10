package ai.magicemerge.bluebird.app.web.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

@Component
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {


	private Boolean openAccessLog = Boolean.FALSE;

	public void setOpenAccessLog(Boolean openAccessLog) {
		this.openAccessLog = openAccessLog;
	}

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal StartTime");


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			long beginTime = System.currentTimeMillis();// 1、开始时间
			startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
			log.debug("开始计时: {}  URI: {}", new SimpleDateFormat("HH:mm:ss.SSS").format(beginTime),
					request.getRequestURI());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			log.info("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (openAccessLog) {
			// 保存日志
//			LogUtils.saveLog(request, handler, ex, null);
			// 打印JVM信息。
			if (log.isDebugEnabled()) {
				long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
				long endTime = System.currentTimeMillis(); // 2、结束时间
				log.debug("计时结束：{}  耗时：{}ms  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
						new SimpleDateFormat("HH:mm:ss.SSS").format(endTime),
						(endTime - beginTime), request.getRequestURI(),
						Runtime.getRuntime().maxMemory() / 1024 / 1024,
						Runtime.getRuntime().totalMemory() / 1024 / 1024,
						Runtime.getRuntime().freeMemory() / 1024 / 1024,
						(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()
								+ Runtime.getRuntime().freeMemory()) / 1024 / 1024);
			}
		}

	}

}
