package ai.magicemerge.bluebird.app.service.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单流水号，订单号 = 时间戳 + 订单类型 + 随机数, 23位字符
 */
public class OrderNoGenUtils {


	public static final ThreadLocal<DateFormat> DATE_TIME_PLAIN = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));



	private static String timestamp() {
		return DATE_TIME_PLAIN.get().format(new Date());
	}

	/**
	 * 随机生成六位数验证码
	 * @return
	 */
	public static int getRandomNum(){
		Random r = new Random();
		return r.nextInt(900000)+100000;//(int)(Math.random()*999999)
	}


	/**
	 *   * 订单流水号，订单号 = 时间戳 + 订单类型 + 随机数, 23位字符
	 *
	 * @param orderType 订单类型
	 * @return
	 */
	private static String getOrderSeq(String orderType) {
		return String.format("%s%s%s", timestamp(), orderType, getRandomNum());
	}

//
//	public static void main(String[] args) {
//		for (int i = 0; i < 100; i++) {
//			System.out.println(OrderNoGenUtils.getOrderSeq("001"));
//			System.out.println();
//		}
//	}


}
