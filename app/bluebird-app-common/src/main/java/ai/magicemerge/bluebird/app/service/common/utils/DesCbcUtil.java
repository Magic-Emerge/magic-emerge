package ai.magicemerge.bluebird.app.service.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;

@Slf4j
public class DesCbcUtil {

	/**
	 * 3DES加密
	 *
	 * @param plainText 普通文本
	 * @return 加密后的文本，失败返回null
	 */
	public static String encode(String plainText, String secretKey, String iv) {
		String result = null;
		try {
			DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
			Key desKey = secretKeyFactory.generateSecret(deSedeKeySpec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			result = Base64Utils.encodeToString(encryptData);
		} catch (Exception e) {
			log.error("DesCbcUtil encode error : {}", ExceptionUtils.getMessage(e));
		}
		return result;
	}

	/**
	 * 3DES解密
	 *
	 * @param encryptText 加密文本
	 * @return 解密后明文，失败返回null
	 */
	public static String decode(String encryptText, String secretKey, String iv) {
		String result = null;
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
			Key desKey = secretKeyFactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
			byte[] decryptData = cipher.doFinal(Base64Utils.decodeFromString(encryptText));
			result = new String(decryptData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error("DesCbcUtil decode error : {}", ExceptionUtils.getMessage(e));
		}
		return result;
	}


	public static void main(String[] args) {

		String result = DesCbcUtil.encode("Q9S4Y8t5Y4", "gh2c23b44r2b1415343aab5a8286985cs", "45467671");
		System.out.println(result);

		System.out.println(LocalDateTime.now());


		String decode = DesCbcUtil.decode("xBDZ8qiR1aP24rkFBo4ncw==", "gh2c23b44r2b1415343aab5a8286985cs", "45467671");
		System.out.println(decode);
	}


}
