package ai.magicemerge.bluebird.app.service.common;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

public class LocalCache {

	/**
	 * 缓存分钟数
	 */
	public static final long TIMEOUT_MINUTE = 5;
	/**
	 * 缓存时长
	 */
	public static final long TIMEOUT = TIMEOUT_MINUTE * DateUnit.MINUTE.getMillis();
	/**
	 * 清理间隔
	 */
	private static final long CLEAN_TIMEOUT = TIMEOUT_MINUTE * DateUnit.MINUTE.getMillis();
	/**
	 * 缓存对象
	 */
	public static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(TIMEOUT);

	static {
		//启动定时任务
		CACHE.schedulePrune(CLEAN_TIMEOUT);
	}
}
