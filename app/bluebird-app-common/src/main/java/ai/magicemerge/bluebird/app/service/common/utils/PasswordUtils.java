package ai.magicemerge.bluebird.app.service.common.utils;

public class PasswordUtils {

	/**
	 * 随机生成10位密码
	 *
	 * @return
	 */
	public static String generatePassword() {
		String[] pa = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < 5; i++) {
			sb.append(pa[(Double.valueOf(Math.random() * pa.length).intValue())]);
			sb.append((int)(Math.random() * 10));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(PasswordUtils.generatePassword());
	}

}
