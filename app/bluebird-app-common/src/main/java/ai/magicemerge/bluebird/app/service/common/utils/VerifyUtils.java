package ai.magicemerge.bluebird.app.service.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {

	/**
	 * 校验邮箱是否合法
	 * @param email
	 * @return
	 */
	public static boolean verifyEmailAddress(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		/**
		 * 合法E-mail地址：
		 * 1. 必须包含一个并且只有一个符号“@”
		 * 2. 第一个字符不得是“@”或者“.”
		 * 3. 不允许出现“@.”或者.@
		 * 4. 结尾不得是字符“@”或者“.”
		 * 5. 允许“@”前的字符中出现“＋”
		 * 6. 不允许“＋”在最前面，或者“＋@”
		 */
		String reg =  "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern compile = Pattern.compile(reg);
		Matcher matcher = compile.matcher(email);
		return matcher.matches();
	}



	/**
	 * 校验手机号是否合法
	 * @param phone
	 * @return
	 */
	public static boolean verifyPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return false;
		}
		String reg =  "0?(13|14|15|18|16|17|19)[0-9]{9}";
		Pattern compile = Pattern.compile(reg);
		Matcher matcher = compile.matcher(phone);

		return matcher.matches();
	}


}
