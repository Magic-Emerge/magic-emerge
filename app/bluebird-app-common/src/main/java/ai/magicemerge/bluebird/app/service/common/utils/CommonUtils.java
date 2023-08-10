package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.enums.ModelInputPriceEnum;
import ai.magicemerge.bluebird.app.service.common.enums.ModelOutputPriceEnum;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class CommonUtils {


	public static String getStarPassword(String accessToken, int start, int end) {
		int length = accessToken.length();
		StringBuilder newVal = new StringBuilder();
		for (int i = 0; i < length; i ++) {
			char ch = accessToken.charAt(i);
			if (i >= start && i <= end) {
				ch = '*';
			}
			newVal.append(ch);
		}
		return newVal.toString();
	}


	/**
	 * 随机生成四位数验证码
	 * @return
	 */
	public static int getRandomNum(){
		Random r = new Random();
		return r.nextInt(9000)+1000;//(int)(Math.random()*999999)
	}


	public static String addTokenPrefix(String token) {
		return String.format("Bearer %s", token);
	}



	public static long getTokens(String model, String message)  {
		if (ModelInputPriceEnum.GPT3_5.getModel().equals(model)) {
			EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
			Encoding encoding = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);
			return encoding.countTokens(message);
		}

		if (ModelInputPriceEnum.GPT3_5_16K.getModel().equals(model)) {
			EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
			Encoding encoding = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO_16K);
			return encoding.countTokens(message);
		}

		if (ModelInputPriceEnum.GPT4.getModel().equals(model)) {
			EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
			Encoding encoding = registry.getEncodingForModel(ModelType.GPT_4);
			return encoding.countTokens(message);
		}
		return 0L;
	}


	public static BigDecimal getInputFee(String model, long tokens) {

		BigDecimal divide = new BigDecimal(tokens).divide(BigDecimal.valueOf(1000),8, RoundingMode.HALF_EVEN);

		if (ModelInputPriceEnum.GPT3_5.getModel().equals(model)) {
			String price = ModelInputPriceEnum.GPT3_5.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}

		if (ModelInputPriceEnum.GPT3_5_16K.getModel().equals(model)) {
			String price = ModelInputPriceEnum.GPT3_5_16K.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}

		if (ModelInputPriceEnum.GPT4.getModel().equals(model)) {
			String price = ModelInputPriceEnum.GPT3_5.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}
		return BigDecimal.ZERO;
	}


	public static BigDecimal getOutputFee(String model, long tokens) {

		BigDecimal divide = new BigDecimal(tokens).divide(BigDecimal.valueOf(1000),8, RoundingMode.HALF_EVEN);

		if (ModelOutputPriceEnum.GPT3_5.getModel().equals(model)) {
			String price = ModelOutputPriceEnum.GPT3_5.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}

		if (ModelInputPriceEnum.GPT3_5_16K.getModel().equals(model)) {
			String price = ModelInputPriceEnum.GPT3_5_16K.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}

		if (ModelOutputPriceEnum.GPT4.getModel().equals(model)) {
			String price = ModelOutputPriceEnum.GPT3_5.getPrice();
			BigDecimal totalPrice = new BigDecimal(price).multiply(divide);
			return totalPrice;
		}
		return BigDecimal.ZERO;
	}


	public static void main(String[] args) {
		for (int i = 0; i < 100; i ++) {

			System.out.println(CommonUtils.getRandomNum());
		}
	}

}
