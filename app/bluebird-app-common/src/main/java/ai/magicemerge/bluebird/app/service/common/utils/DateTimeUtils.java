package ai.magicemerge.bluebird.app.service.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {


	public static final String DEFAULT_FORMAT = "yyyy-MM-dd hh:mm:ss";

	public static ThreadLocal<SimpleDateFormat> formatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_FORMAT));



	/**
	 * 获取今年最后一天
	 *
	 * @return
	 */
	public static LocalDateTime getThisYearLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		return LocalDateTime.of(calendar.get(Calendar.YEAR), 12, 31, 23, 59, 59);
	}


	/**
	 * LocalDateTime to Date
	 * @param dateTime
	 * @return
	 */
	public static Date localDateTime2Date(LocalDateTime dateTime, ZoneId zoneId) {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
		return Date.from(zonedDateTime.toInstant());
	}


	/**
	 * format LocalDateTime to String
	 *
	 * @param date
	 * @return
	 */
	public static String formatLocalDateTime2Str(LocalDateTime date) {
		return formatThreadLocal.get().format(localDateTime2Date(date, ZoneId.systemDefault()));
	}



//	public static void main(String[] args) {
//		System.out.println(getThisYearLastDay());
//	}

}
