package ai.magicemerge.bluebird.app.service.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class CopyUtils {
	/**
	 * 复制对象
	 *
	 * @param src   原对象
	 * @param clazz 对象类
	 * @param <T>
	 * @return 新的对象
	 */
	public static <T> T copy(Object src, Class<T> clazz) {
		if (src == null) {
			return null;
		}
		return JSONObject.parseObject(JSON.toJSONString(src), clazz);
	}

	/**
	 * 复制对象list集合
	 *
	 * @param srclist 原对象集合
	 * @param clazz   对象类
	 * @param <T>
	 * @return 新的对象
	 */
	public static <T> List<T> copyList(Object srclist, Class<T> clazz) {
		if (srclist == null) {
			return null;
		}
		return JSON.parseArray(JSON.toJSONString(srclist), clazz);
	}
}
