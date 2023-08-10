package ai.magicemerge.bluebird.app.service.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Objects;

@Slf4j
public class BeanUtils {


	/**
	 * 复制类
	 *
	 * @param src
	 * @param dect
	 * @param <T>
	 */
	public static<T> T copy(Object src, Class<T> dect) {
		T o = null;
		try {
			if (Objects.nonNull(dect)) {
				o = dect.newInstance();
				org.springframework.beans.BeanUtils.copyProperties(src, o);
				return o;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("类拷贝出错, {}", ExceptionUtils.getMessage(e));
		}
		return o;
	}


}
